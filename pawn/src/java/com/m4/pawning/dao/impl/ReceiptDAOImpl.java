package com.m4.pawning.dao.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.m4.core.exception.CommonDataAccessException;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.RecordStatusEnum;
import com.m4.core.util.TransactionDAOSupport;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.ReceiptDAO;
import com.m4.pawning.domain.AuctionTicket;
import com.m4.pawning.domain.Branch;
import com.m4.pawning.domain.CancelDueReceipt;
import com.m4.pawning.domain.DueFrom;
import com.m4.pawning.domain.DueReceipt;
import com.m4.pawning.domain.DueType;
import com.m4.pawning.domain.Ledger;
import com.m4.pawning.domain.OverPayment;
import com.m4.pawning.domain.Receipt;
import com.m4.pawning.domain.Ticket;
import com.m4.pawning.dto.ReceiptDTO;
import com.m4.pawning.util.ReceiptStatusEnum;
import com.m4.pawning.util.TicketStatusEnum;

public class ReceiptDAOImpl extends TransactionDAOSupport implements ReceiptDAO {
	private static final Logger logger = Logger.getLogger(ReceiptDAOImpl.class);
	private ReceiptStatusEnum ticketEnum;

	public String createReceipt(UserConfig userConfig,Receipt receipt){
		logger.info("**** Enter in to createReceipt method ****");
		double receiptAmt = receipt.getReceiptAmt();
		String receiptType = "",recipetPrefix= "";

		Ticket ticket = (Ticket)getSession().get(Ticket.class, receipt.getTicketId());
		Branch branch=(Branch)getSession().get(Branch.class, userConfig.getBranchId());
		//Modified By Mahinda for Auction
		AuctionTicket auctionTicket=null;
		Double originalRecieptAmount = receiptAmt;
		
		if (ticket.getIsAuctioned()==1) {
			auctionTicket = (AuctionTicket) getSession().createCriteria(AuctionTicket.class)
						.add(Restrictions.eq("ticketId", receipt.getTicketId())).uniqueResult();
		}
		//Modified by Mahinda on 22-Nov-2011
		if (ticket.getTicketStatusId() == TicketStatusEnum.PENDING.getCode()) {
			throw new CommonDataAccessException("errors.ticketstatus");
		}

		List<DueType> dueTypeList = null;
		if(receipt.getReceiptType() == 1)
			dueTypeList = getSession().createCriteria(DueType.class)
										.add(Restrictions.eq("companyId",userConfig.getCompanyId()))
										.add(Restrictions.eq("recordStatus",RecordStatusEnum.ACTIVE.getCode()))
										.add(Restrictions.eq("isReceipt","R"))
										//.add(Restrictions.eq("productId", ticket.getProductId()))
										.addOrder(Order.asc("sequence"))
										.list();
		else
			dueTypeList = getSession().createCriteria(DueType.class)
										.add(Restrictions.eq("companyId",userConfig.getCompanyId()))
										.add(Restrictions.eq("recordStatus",RecordStatusEnum.ACTIVE.getCode()))
										.add(Restrictions.eq("isReceipt","R"))
										//.add(Restrictions.eq("productId", ticket.getProductId()))
										.add(Restrictions.eq("dueTypeId", 2))
										.addOrder(Order.asc("sequence"))
										.list();
		
		if (dueTypeList.isEmpty())
			throw new CommonDataAccessException("errors.noduetypedefine");

		List<DueFrom> dueFromList = getSession().createCriteria(DueFrom.class)
									.add(Restrictions.eq("ticketId", receipt.getTicketId()))
									.add(Restrictions.gt("balanceAmount", Double.valueOf(0.0)))
									.list();
		
		if (dueFromList.isEmpty()) {
			throw new CommonDataAccessException("errors.nodues");
		}
		
		

		//BranchCode + "YEAR" + "R" + SERIAL
		//Set the Serial Number to ticket And ticketNumber
		DecimalFormat decimalFormat = new DecimalFormat("000000");
		if(receipt.getReceiptType() == 1)
		{
			receiptType = "RCP";
			recipetPrefix = "R";
		}
		else
		{
			receiptType = "WAO";
			recipetPrefix = "W";
		}
		String serialNumber = decimalFormat.format(getSerialValue(userConfig, receiptType , ticket.getProductId()));
		boolean lauctionSettled = false;

		StringBuilder receiptNumber = new StringBuilder();
		receiptNumber.append(((Branch)getSession().get(Branch.class, userConfig.getBranchId())).getCode());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(userConfig.getLoginDate());
		receiptNumber.append(Integer.toString(calendar.get(Calendar.YEAR)).substring(2));
		receiptNumber.append(recipetPrefix);
		receiptNumber.append(serialNumber);

		initializeTransactionDomainObject(userConfig, receipt);
		receipt.setReceiptNo(receiptNumber.toString());
		receipt.setProductId(ticket.getProductId());
		receipt.setReceiptDate(userConfig.getLoginDate());
		receipt.setPawnerId(ticket.getPawner().getPawnerId());
		receipt.setReceiptEnteredUser(userConfig.getLoginName());
		getHibernateTemplate().save(receipt);

		//Update the Available amount when receipt settled
		if(receipt.getReceiptType() == 1){
			branch.setFundAvailable(branch.getFundAvailable()+receiptAmt);
			getHibernateTemplate().update(branch);
		}

		Map<Integer, DueFrom> dueMap = new HashMap<Integer, DueFrom>();
		double outstandingAmt = 0;
		for(DueFrom dueFrom:dueFromList){
			dueMap.put(dueFrom.getDueTypeId(), dueFrom);
			outstandingAmt+=dueFrom.getBalanceAmount();
		}

		for(DueType dueType:dueTypeList){
			if (dueType.getDueType().equals("OVP") && outstandingAmt < originalRecieptAmount) {
				OverPayment overPayment =new OverPayment();
				overPayment.setDueTypeId(dueType.getDueTypeId());
				overPayment.setOvpAmount(originalRecieptAmount - outstandingAmt);
				overPayment.setProductId(ticket.getProductId());
				overPayment.setReciptNo(receipt.getReceiptId());
				overPayment.setTicketId(ticket.getTicketId());
				initializeDomainObject(userConfig, overPayment);
				getHibernateTemplate().save(overPayment);
			}
			if (auctionTicket!=null) {
				auctionTicket.setExcessAmount(originalRecieptAmount - outstandingAmt);
				auctionTicket.setSoldAmount(originalRecieptAmount);
				auctionTicket.setSoldDate(userConfig.getLoginDate());
				lauctionSettled = true;
			}
			
			if(receiptAmt > 0 && dueMap.containsKey(dueType.getDueTypeId())){
				DueFrom dueFrom = dueMap.get(dueType.getDueTypeId());

				if(dueType.getDueType().equals("INT") && dueFrom.getBalanceAmount()>receiptAmt && receipt.getReceiptType() == 1)
					throw new CommonDataAccessException("errors.notenoughfunds");

				double settleAmt = 0;
				if(receiptAmt >= dueFrom.getBalanceAmount()){
					dueFrom.setPaidAmount(dueFrom.getPaidAmount() + dueFrom.getBalanceAmount());
					receiptAmt = receiptAmt - dueFrom.getBalanceAmount();
					settleAmt = dueFrom.getBalanceAmount();
					dueFrom.setBalanceAmount(0.0);
				}else{
					dueFrom.setPaidAmount(dueFrom.getPaidAmount() + receiptAmt);
					dueFrom.setBalanceAmount(dueFrom.getBalanceAmount() - receiptAmt);
					settleAmt = receiptAmt;
					receiptAmt = 0;
				}
				getHibernateTemplate().update(dueFrom);

				DueReceipt dueReceipt = new DueReceipt();
				dueReceipt.setReceiptId(receipt.getReceiptId());
				dueReceipt.setTicketId(receipt.getTicketId());
				dueReceipt.setDueTypeId(dueType.getDueTypeId());
				dueReceipt.setSettleAmount(settleAmt);
				dueReceipt.setSettledDate(userConfig.getLoginDate());
				dueReceipt.setProductId(ticket.getProductId());
				dueReceipt.setPownerId(ticket.getPawner().getPawnerId());
				//dueReceipt.setRefNumber(dueFrom)
				initializeTransactionDomainObject(userConfig, dueReceipt);
				getHibernateTemplate().save(dueReceipt);
				if (dueType.getDueType().equalsIgnoreCase("CAP")) {
					if (auctionTicket!=null) {
						auctionTicket.setCapitalSettled(settleAmt);					
					}
				}else if (dueType.getDueType().equalsIgnoreCase("INT")) {
					if (auctionTicket!=null) {
						auctionTicket.setInterestSettled(settleAmt);					
					}					
				}
				if (lauctionSettled){
					ticket.setTicketStatusId(TicketStatusEnum.CLOSSED.getCode());
					ticket.setTicketClosedRenewalDate(userConfig.getLoginDate());
					getHibernateTemplate().update(ticket);
				}
				
				if (settleAmt > 0){
					Ledger ledger = new Ledger();
					ledger.setDebitAmount(settleAmt);
					ledger.setDueType(dueType);
					ledger.setProductId(ticket.getProductId());
					ledger.setDate(userConfig.getLoginDate());
					ledger.setIsBranchExplycit(true);
					initializeDomainObject(userConfig, ledger);
					getHibernateTemplate().save(ledger);
					settleAmt=0;
				}
			}
		}

		logger.info("**** Leaving from createReceipt method ****");
		return receipt.getReceiptNo();
	}

	public ReceiptDTO getReceiptTicketData(UserConfig userConfig,int ticketId ,int receiptType){
		logger.debug("**** Enter in to getReceiptTicketData method ****");

		ReceiptDTO receiptDTO = new ReceiptDTO();
		Ticket ticket = (Ticket)getSession().get(Ticket.class, Integer.valueOf(ticketId));
		List<DueFrom> dueList = null;
		if(receiptType == 1){
			dueList = getSession().createCriteria(DueFrom.class)
									.add(Restrictions.eq("ticketId", ticketId))
									.add(Restrictions.gt("balanceAmount", Double.valueOf(0.0)))
									.list();
		}else{
			dueList = getSession().createCriteria(DueFrom.class)
			.add(Restrictions.eq("ticketId", ticketId))
			.add(Restrictions.gt("balanceAmount", Double.valueOf(0.0)))
			.add(Restrictions.eq("dueTypeId",2))
			.list();
		}

		receiptDTO.setClientId(ticket.getPawner().getPawnerId());
		receiptDTO.setClientCode(ticket.getPawner().getPawnerCode());
		receiptDTO.setClientName(ticket.getPawner().getPawnerName());
		receiptDTO.setAddress(new StringBuilder().append(ticket.getPawner().getAddressLine1())
							  .append(" ").append(ticket.getPawner().getAddressLine2())
							  .append(" ").append(ticket.getPawner().getAddressLine3())
							  .append(" ").append(ticket.getPawner().getAddressLine4()).toString());

		//getSession().evict(dueList);

		for(DueFrom dueFrom:dueList){
			dueFrom.setDueTypeDescriiption(((DueType)getSession().get(DueType.class, dueFrom.getDueTypeId())).getDescription());
		}

		receiptDTO.setDueFromList(dueList);
		logger.debug("**** Leaving from getReceiptTicketData method ****");
		return receiptDTO;
	}

	public List<Receipt> getAllReceiptByTicketId(UserConfig userConfig,int ticketId){
		logger.debug("**** Enter in to getAllReceiptByTicketId method ****");

		List<Receipt> list = null;
		Criteria criteria = getSession().createCriteria(Receipt.class);
		criteria.add(Restrictions.eq("ticketId", ticketId));
		list = criteria.list();

		logger.debug("**** Leaving from getAllReceiptByTicketId method ****");
		return list;
	}

	public DataBag getAllReceipt(UserConfig userConfig,List<QueryCriteria> criteriaList) {
		logger.debug("**** Enter in to getAllReceipt method ****");
		DataBag receiptBag = null;
		Criteria criteria  = getSession().createCriteria(Receipt.class);
		criteria.setFetchMode("dueReceipts", FetchMode.JOIN);
//		criteria.setFetchMode("overPayment", FetchMode.JOIN);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		receiptBag = getDataList(getHibernateTemplate(), criteriaList, criteria);
		logger.debug("**** Leaving from getAllReceipt method ****");
		return receiptBag;
	}


	public void updateReceiptPrint(UserConfig userConfig, String receiptNo ){
		logger.debug("**** Enter in to updateReceiptPrint method ****");
		String hqlUpdate = "UPDATE Receipt  SET prtNo=(prtNo + 1) , printDate=:prtDate WHERE receiptNo=:receiptno";

		getSession().createQuery(hqlUpdate) .setDate("prtDate", userConfig.getLoginDate())
											.setString("receiptno", receiptNo)
											.executeUpdate();

		logger.debug("**** Enter in to updateReceiptPrint method ****");
	}

	/**
	 * 	* Add new three fields (CancelUserId, Date&Time)
	 *  01 - check date whether the date is same as systemday
	 *  02 - check whether the ticket is close or check ACTIVE | LAP
	 *  03 - receipt status mark as cancel
	 *  04 - select * dueReceipt related to this receipt
	 *  05 - while going thought the loop select duefrom and - from paidAmount and + to the balanceAmount
	 *  06 - Save DueReceipt to another backup table(cancelDueReceipt) and delete DueReceipt
	 *
	 */
	public void cancelReceipt(UserConfig userConfig, int receiptId) {
		logger.debug("**** Enter in to cancelReceipt method ****");
		Receipt receipt = (Receipt)getHibernateTemplate().get(Receipt.class, receiptId);

		if(!receipt.getReceiptDate().equals(userConfig.getLoginDate()))
			throw new CommonDataAccessException("errors.receiptdate");

		Ticket ticket   = (Ticket)getHibernateTemplate().get(Ticket.class, receipt.getTicketId());

		TicketStatusEnum ticketEnum = TicketStatusEnum.getEnumByCode(ticket.getTicketStatusId());

		if(!(ticketEnum == TicketStatusEnum.ACTIVE || ticketEnum == TicketStatusEnum.LAPS))
			throw new CommonDataAccessException("errors.ticketstatus");

		List<DueFrom> dueFromList = getSession().createCriteria(DueFrom.class)
									.add(Restrictions.eq("ticketId", receipt.getTicketId()))
									.list();

		Map<Integer, DueFrom> dueMap = new HashMap<Integer, DueFrom>();
		for(DueFrom dueFrom:dueFromList)
			dueMap.put(dueFrom.getDueTypeId(), dueFrom);

		for(DueReceipt dueReceipt : receipt.getDueReceipts()){
			DueFrom dueFrom = dueMap.get(dueReceipt.getDueTypeId());
			dueFrom.setBalanceAmount(dueFrom.getBalanceAmount() + dueReceipt.getSettleAmount());
			dueFrom.setPaidAmount(dueFrom.getPaidAmount() - dueReceipt.getSettleAmount());
			getHibernateTemplate().update(dueFrom);

			CancelDueReceipt cancelDueReceipt = new CancelDueReceipt();
			cancelDueReceipt.setReceiptId(receipt.getReceiptId());
			cancelDueReceipt.setTicketId(ticket.getTicketId());
			cancelDueReceipt.setDueTypeId(dueReceipt.getDueTypeId());
			cancelDueReceipt.setSettleAmount(dueReceipt.getSettleAmount());
			cancelDueReceipt.setSettledDate(dueReceipt.getSettledDate());
			cancelDueReceipt.setProductId(ticket.getProductId());
			cancelDueReceipt.setPownerId(ticket.getPawner().getPawnerId());

			initializeTransactionDomainObject(userConfig, cancelDueReceipt);
			getHibernateTemplate().save(cancelDueReceipt);
			getHibernateTemplate().evict(dueReceipt);
			getHibernateTemplate().delete(dueReceipt);
			if (dueReceipt.getSettleAmount() > 0){
				Ledger ledger = new Ledger();
				ledger.setCreditAmount(dueReceipt.getSettleAmount());
				ledger.setDueType(((DueType)getSession().get(DueType.class, dueReceipt.getDueTypeId())) );
				ledger.setProductId(ticket.getProductId());
				ledger.setDate(userConfig.getLoginDate());
				ledger.setIsBranchExplycit(true);
				initializeDomainObject(userConfig, ledger);
				getHibernateTemplate().save(ledger);
			}
		}

		receipt.setStatus(ReceiptStatusEnum.CANCEL.getCode());
		receipt.setDueReceipts(new ArrayList<DueReceipt>());
		getHibernateTemplate().update(receipt);

		Branch branch = (Branch)getHibernateTemplate().get(Branch.class, receipt.getBranchId());
		branch.setFundAvailable(branch.getFundAvailable() - receipt.getReceiptAmt());
		getHibernateTemplate().update(branch);

		logger.debug("**** Leaving from cancelReceipt method ****");
	}
}

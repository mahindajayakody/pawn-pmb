package com.m4.pawning.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.m4.core.exception.CommonDataAccessException;
import com.m4.core.util.DataBag;
import com.m4.core.util.MasterDAOSupport;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.RecordStatusEnum;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.AuctionDAO;
import com.m4.pawning.domain.AuctionMaster;
import com.m4.pawning.domain.AuctionTicket;
import com.m4.pawning.domain.DueFrom;
import com.m4.pawning.domain.DueType;
import com.m4.pawning.domain.Ticket;

public class AuctionDAOImpl extends MasterDAOSupport implements AuctionDAO {

	private static final Logger logger= Logger.getLogger(AuctionDAOImpl.class);
	
	public void createAuction(UserConfig userConfig, AuctionMaster auctionMaster) {
		logger.info("**** Enter in to createAuction ****");
		Criteria criteria =getSession().createCriteria(AuctionMaster.class);
		criteria.add(Restrictions.eq("code", auctionMaster.getCode()));
		criteria.add(Restrictions.eq("branchId", auctionMaster.getBranchId()));
		criteria.add(Restrictions.eq("companyId", auctionMaster.getCompanyId()));
		criteria.setProjection(Projections.rowCount());
		int count = (Integer)criteria.uniqueResult();
		if (count!=0)
			throw new CommonDataAccessException("errors.recordexist");
		create(userConfig, auctionMaster);
		logger.info("**** Leaving createAuction ****");
	}

	public DataBag getAllAuction(UserConfig userConfig,List<QueryCriteria> criteriaList) {
		logger.info("**** Enter in to getAllAuction ****");
		DataBag auctionBag=null;
		Criteria criteria = getSession().createCriteria(AuctionMaster.class);
		//criteriaList.add(new QueryCriteria("status",Oparator.EQUAL,"1"));
		auctionBag = getDataList(getHibernateTemplate(), criteriaList, criteria);
		logger.info("**** Leaving getAllAuction ****");
		return auctionBag;
	}

	public AuctionMaster getAuctionByCode(UserConfig userConfig, String code) {
		logger.info("**** Enter in to getAuctionByCode ****");
		AuctionMaster auction=null;
		Criteria criteria= getSession().createCriteria(AuctionMaster.class);
		criteria.add(Restrictions.eq("code", code));
		criteria.setProjection(Projections.rowCount());
		auction=(AuctionMaster) criteria.uniqueResult();
		
		if(auction==null)
			throw new CommonDataAccessException("errors.recordnotfound");
		
		logger.info("**** Leaving getAuctionByCode ****");
		return auction;
	}

	public AuctionMaster getAuctionById(UserConfig userConfig, int recordId) {
		logger.info("**** Enter in to getAuctionById ****");
		AuctionMaster auction=null;
		auction = (AuctionMaster)getHibernateTemplate().get(AuctionMaster.class, Integer.valueOf(recordId));
		
		if (auction==null)
			throw new CommonDataAccessException("errors.recordnotfound");
		logger.info("**** Leaving getAuctionById ****");		
		return auction;
	}

	public void removeAuction(UserConfig userConfig, AuctionMaster auctionMaster) {
		logger.info("**** Enter in to removeAuction ****");
		AuctionMaster auction = (AuctionMaster) getHibernateTemplate().get(AuctionMaster.class, auctionMaster.getRecordId());
		auction.setVersion(auctionMaster.getVersion());
		remove(userConfig, auctionMaster);
		logger.info("**** Leaving removeAuction ****");
	}

	public void updateAuction(UserConfig userConfig, AuctionMaster auctionMaster) {
		logger.info("**** Enter in to updateAuction ****");
		update(userConfig, auctionMaster);
		logger.info("**** Leaving updateAuction ****");
	}

	public DataBag getAllActiveAuction(UserConfig userConfig,List<QueryCriteria> criteriaList) {
		logger.info("**** Enter in to getAllAuction ****");
		DataBag auctionBag=null;
		Criteria criteria = getSession().createCriteria(AuctionMaster.class);
		//criteriaList.add(new QueryCriteria("status",Oparator.EQUAL,"1"));
		auctionBag = getDataList(getHibernateTemplate(), criteriaList, criteria);
		logger.info("**** Leaving getAllAuction ****");
		return auctionBag;
	}
	
	public List getAllLapsTickets(UserConfig userConfig,String sql){
		logger.debug("**** Enter in to getSerchTicketData method ****");
		String hql = "SELECT t.ticketId,t.ticketNumber,t.pawner.title, t.pawner.initials , t.pawner.surName,t.pawner.pawnerCode,t.version " +
					 " FROM Ticket t INNER JOIN t.pawner WHERE t.companyId=:companyId " + sql;
		
		Query query = getSession().createQuery(hql);
		//query.setInteger("ticketStatusId", TicketStatusEnum.ACTIVE.getCode());
		query.setInteger("companyId", userConfig.getCompanyId());
		
		List list = query.list();		
		logger.debug("**** Leaving from getSerchTicketData method ****");
		return list;
	}
	
	
	public void createAuctionMark(UserConfig userConfig, int branchId,int productId,int auctionId ,Integer[] tikets,Double upsetValue) {
		logger.info("**** Enter in to create Auction Mark ****");
		Criteria dueFromCriteria = null;
		Criteria ticketCriteria = null;
		//Load all DueTypes from DueType
		Criteria dueTypeCriteria = getSession().createCriteria(DueType.class);
		dueTypeCriteria.add(Restrictions.eq("recordStatus", RecordStatusEnum.ACTIVE.getCode()));	
		List<DueType> dueTypeList = null;
		dueTypeList = dueTypeCriteria.list();	
		Map<String, DueType> dueTypeMap  = null;
		
		dueTypeMap = new HashMap<String, DueType>();
		for(DueType dueType:dueTypeList){
			dueTypeMap.put(dueType.getDueType(), dueType);
		}
		
		int capId = dueTypeMap.get("CAP").getDueTypeId();
		int intId = dueTypeMap.get("INT").getDueTypeId();
		int payId = dueTypeMap.get("PAY").getDueTypeId();
		
		Criteria auctionCriteria = null;
		
		double CAPTotal=0;
		double INTTotal=0;
		int RecordCount=0;
		
		for(int i=0;i<tikets.length;i++){
			dueFromCriteria = getSession().createCriteria(DueFrom.class);
			dueFromCriteria.add(Restrictions.eq("ticketId",tikets[i]));				
			List<DueFrom> dueFormList = dueFromCriteria.list();
			
			double CAPAmt=0;
			double INTAmt=0;
			
			for(DueFrom dueFrom:dueFormList){
				if(capId == dueFrom.getDueTypeId()){
					CAPAmt= round(dueFrom.getBalanceAmount(),2);
				}
				
				if(intId == dueFrom.getDueTypeId()){
					INTAmt= round(dueFrom.getBalanceAmount(),2);
					}
			}
			
			CAPTotal=CAPTotal+CAPAmt;
			INTTotal=INTTotal+INTAmt;
			RecordCount=RecordCount+1;
			
			
			//Update Isauction flag in the Ticket
			ticketCriteria = getSession().createCriteria(Ticket.class);
			ticketCriteria.add(Restrictions.eq("ticketId",tikets[i]));				
			List<Ticket> ticketList = ticketCriteria.list();
			for(Ticket ticket:ticketList){
				ticket.setIsAuctioned(1);
			}
			
			//Save Auction Ticket
			AuctionTicket auctionTicket = new AuctionTicket();
			auctionTicket.setCompanyId(userConfig.getCompanyId());
			auctionTicket.setBranchId(branchId);
			auctionTicket.setAuctionId(auctionId);
			auctionTicket.setTicketId(tikets[i]);
			auctionTicket.setAssignDate(userConfig.getLoginDate());
			
			auctionTicket.setMinimumCapital(CAPAmt);
			auctionTicket.setMinimumInterest(INTAmt);
			auctionTicket.setUpsetValue(upsetValue);
			initializeDomainObject(userConfig, auctionTicket);
			getHibernateTemplate().save(auctionTicket);			
			
		}
		
		auctionCriteria = getSession().createCriteria(AuctionMaster.class);
		auctionCriteria.add(Restrictions.eq("auctionId",auctionId));				
		List<AuctionMaster> auctionList = auctionCriteria.list();
		
		
		for(AuctionMaster auctionMater:auctionList){
			auctionMater.setNoOfTicket(RecordCount);
			auctionMater.setTotalCapital(CAPTotal);
			auctionMater.setTotalInterest(INTTotal);
		}
		logger.info("**** Leaving create Auction Mark ****");
	}
	public static double round(double d, int decimalPlace){
	    // see the Javadoc about why we use a String in the constructor
	    // http://java.sun.com/j2se/1.5.0/docs/api/java/math/BigDecimal.html#BigDecimal(double)
	    BigDecimal bd = new BigDecimal(Double.toString(d));
	    bd = bd.setScale(decimalPlace,BigDecimal.ROUND_HALF_UP);
	    return bd.doubleValue();
	}

	public List<AuctionMaster> getAllCommonAuction(UserConfig userConfig,
			int isCommon) {
		List<AuctionMaster> auctionMasters=null;
		Criteria criteria = getSession().createCriteria(AuctionMaster.class);
		criteria.add(Restrictions.eq("isCommon", isCommon));
		criteria.add(Restrictions.eq("status", 1));
		auctionMasters = criteria.list();
		return auctionMasters;
	}
}

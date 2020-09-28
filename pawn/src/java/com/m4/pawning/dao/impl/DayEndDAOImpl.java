package com.m4.pawning.dao.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;


import org.apache.log4j.Logger;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


import com.m4.core.bean.Serial;
import com.m4.core.bean.SerialMaster;
import com.m4.core.exception.CommonDataAccessException;
import com.m4.core.util.RecordStatusEnum;
import com.m4.core.util.TransactionDAOSupport;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.DayEndDAO;
import com.m4.pawning.dao.OfficerDAO;
import com.m4.pawning.domain.Branch;
import com.m4.pawning.domain.CribData;
import com.m4.pawning.domain.DailyInterst;
import com.m4.pawning.domain.DueFrom;
import com.m4.pawning.domain.DueReceipt;
import com.m4.pawning.domain.DueType;
import com.m4.pawning.domain.InterestSlab;
import com.m4.pawning.domain.Ledger;
import com.m4.pawning.domain.Officer;
import com.m4.pawning.domain.OfficerHistory;
import com.m4.pawning.domain.Product;
import com.m4.pawning.domain.Reminder;
import com.m4.pawning.domain.ReminderPara;
import com.m4.pawning.domain.Schemes;
import com.m4.pawning.domain.SystemDate;
import com.m4.pawning.domain.Ticket;
import com.m4.pawning.util.ReminderCodeEnum;
import com.m4.pawning.util.SendMail;
import com.m4.pawning.util.TicketStatusEnum;


public class DayEndDAOImpl extends TransactionDAOSupport implements DayEndDAO {
	private static final Logger logger = Logger.getLogger(DayEndDAOImpl.class);
	private static final SimpleDateFormat simpdate = new SimpleDateFormat("dd-MM-yyyy");
	private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:MM:SS");
	private static final SimpleDateFormat sqlpdate = new SimpleDateFormat("yyyy-MM-dd");
	long tenDays = 864000000; //10 Days in millis	
	long oneDay = 86400000; //1 Day in millis
	long dateDiff =0;
	int noOfDays = 0;
	SendMail sendMail=new SendMail();

	public void doDayEndProcess(UserConfig userConfig,Integer[] branchs){
		logger.debug("**** Enter in to doDayEndProcess method ****");
		List<Branch> branchList   = null;
		List<Ticket> ticketList   = null;
		List<Schemes> schemesList = null;
		List<DueType> dueTypeList = null;
		Map<String, DueType> dueTypeMap  = null;
		List<SerialMaster> seriaList = null;

		Criteria dueFromCriteria = null;
		Criteria ticketCriteria  = null;
		Criteria schemesCriteria = null;
		Criteria ReminderParaCriteria = null;
		Criteria dueReceiptCriteria = null;
		
		Criteria cribCriteria=null;


		Criteria serialCrt = getSession().createCriteria(SerialMaster.class);
        serialCrt.setLockMode(LockMode.READ);
        seriaList = serialCrt.list();

        File file = new File(System.getProperty("user.dir") + "\\output\\");
        
		//Create Calander Obejct from System Date
		Calendar sysDate = Calendar.getInstance();
		sysDate.setTime(userConfig.getLoginDate());
		sysDate.add(Calendar.DATE, 1);
		
		//Create Calander Obejct from Month End Date
		Calendar monthEnd = Calendar.getInstance();
		monthEnd.set(monthEnd.get(Calendar.YEAR), monthEnd.get(Calendar.MONTH), monthEnd.getActualMaximum(Calendar.DATE));
		monthEnd.add(Calendar.DATE, 1);
		

		//Load all DueTypes from DueType
		Criteria dueTypeCriteria = getSession().createCriteria(DueType.class);
		dueTypeCriteria.add(Restrictions.eq("recordStatus", RecordStatusEnum.ACTIVE.getCode()));
		dueTypeList = dueTypeCriteria.list();

		dueTypeMap = new HashMap<String, DueType>();
		for(DueType dueType:dueTypeList){
			dueTypeMap.put(dueType.getDueType(), dueType);
		}

		int capId = dueTypeMap.get("CAP").getDueTypeId();
		int intId = dueTypeMap.get("INT").getDueTypeId();
		int payId = dueTypeMap.get("PAY").getDueTypeId();

		DueType capType = dueTypeMap.get("CAP");
		DueType intType = dueTypeMap.get("INT");
		DueType payType = dueTypeMap.get("PAY");

		double totalInterest=0.00,interestRate = 0.00,capPaid=0.0,intPaid=0.0,othPaid=0.0,capitalOutsanding=0,interestOutstanding=0,otherOutstanding=0,intAccrued=0,otherDue=0;



		//Create Criteria for load Selected Brances
		Criteria branchCriteria = getSession().createCriteria(Branch.class);
		branchCriteria.add(Restrictions.eq("companyId",userConfig.getCompanyId()));
		branchCriteria.add(Restrictions.in("branchId",branchs));
		branchCriteria.setFetchMode("systemDate", FetchMode.JOIN);
		branchList = branchCriteria.list();
		
		List<Product>productList = getSession().createCriteria(Product.class)
				   .add(Restrictions.eq("companyId",userConfig.getCompanyId())).list();


		for(Branch branch:branchList){
			//Criteria for load all Schemes defiend to the Branch
			schemesCriteria = getSession().createCriteria(Schemes.class);
			schemesCriteria.add(Restrictions.eq("companyId",userConfig.getCompanyId()));
			schemesCriteria.add(Restrictions.eq("branchId",branch.getBranchId()));
			schemesCriteria.add(Restrictions.eq("recordStatus", RecordStatusEnum.ACTIVE.getCode()));
			schemesCriteria.setFetchMode("interestSlads", FetchMode.JOIN);
			schemesList = schemesCriteria.list();

			Map<Integer, Schemes> schemeMap = new HashMap<Integer, Schemes>();
			for(Schemes schemes:schemesList)
				schemeMap.put(schemes.getSchemeId(), schemes);
			//Load all tickets
			ticketCriteria = getSession().createCriteria(Ticket.class);
			ticketCriteria.add(Restrictions.eq("branchId", branch.getBranchId()));
			ticketCriteria.add(Restrictions.eq("companyId", branch.getCompanyId()));
			ticketCriteria.add(Restrictions.ne("ticketStatusId", TicketStatusEnum.CLOSSED.getCode()));
			ticketCriteria.add(Restrictions.eq("isAuctioned", 0));
			ticketCriteria.add(Restrictions.isNotNull("ticketExpiryDate"));
			ticketList = ticketCriteria.list();
			


			for(Ticket ticket:ticketList){
				
				dueFromCriteria = getSession().createCriteria(DueFrom.class);
				dueFromCriteria.add(Restrictions.eq("ticketId",ticket.getTicketId()));
				List<DueFrom> dueFormList = dueFromCriteria.list();
				
				Calendar ticketDate = Calendar.getInstance();
				ticketDate.setTime(ticket.getTicketDate());
				ticketDate.add(Calendar.DATE, ticket.getMinDays());

				//if(ticketDate.after(sysDate)){
				double interest = 0;

				//16-04-2011 Changed by Mahinda
				//Check whether the Ticket Lap or not
				if(simpdate.format(sysDate.getTime()).equals(simpdate.format(ticket.getTicketExpiryDate()))){

					//Ticket mark as Lap
					if (ticket.getTicketStatusId() == TicketStatusEnum.ACTIVE.getCode()){
						ticket.setTicketStatusId(TicketStatusEnum.LAPS.getCode());
						getHibernateTemplate().update(ticket);
					}				
				}

                if (ticket.getTicketStatusId() == TicketStatusEnum.ACTIVE.getCode() || 
                	ticket.getTicketStatusId() == TicketStatusEnum.LAPS.getCode())
                {
                	
					if(sysDate.compareTo(ticketDate)!=-1){
						
						List<InterestSlab> slabList = getSession().createCriteria(InterestSlab.class)
													  .add(Restrictions.eq("companyId" , userConfig.getCompanyId()))
													  .add(Restrictions.eq("branchId" , branch.getBranchId()))
													  .add(Restrictions.eq("recordStatus", RecordStatusEnum.ACTIVE.getCode()))
													  .add(Restrictions.eq("interestSlabCode", schemeMap.get(ticket.getSchemeId()).getInterestId()))
													  .list();

						ticketDate.add(Calendar.DATE, -ticket.getMinDays());
						for(DueFrom dueFrom:dueFormList){
							if(dueFrom.getDueTypeId()==capId){
								for(InterestSlab slab:slabList){
									//Changed By Mahinda on 22-05-2011
									int days =slab.getNoOfDaysTo() - slab.getNoOfDaysFrom();
									
									if (ticket.getTicketStatusId() == TicketStatusEnum.ACTIVE.getCode())
										days += 1;

									if(days > (sysDate.getTimeInMillis() - ticketDate.getTimeInMillis())/86400000){
										interest = dueFrom.getBalanceAmount() * slab.getRate()/36500 ;
										interestRate = slab.getRate()/36500 ;
										totalInterest+=interest;
										break;
									}
								}
							}
						}

						for(DueFrom dueFrom:dueFormList){
							if(intId == dueFrom.getDueTypeId()&& interest>0){
								dueFrom.setDueAmount(dueFrom.getDueAmount() + round(interest ,2));
								dueFrom.setBalanceAmount(dueFrom.getBalanceAmount() + round(interest,2));
							}
						}
					}
                	
				}
				logger.info("Start Creating Daily Interest : - " + ticket.getTicketId() + "  Interest Amount= " + interest);
				DailyInterst dailyInterest=new DailyInterst();
				dailyInterest.setInterestRate(round(interestRate, 2));
				dailyInterest.setInterstAmount(round(interest, 2));
				dailyInterest.setIsBranchExplycit(true);
				dailyInterest.setBranchId(branch.getBranchId());
				dailyInterest.setTicketId(ticket.getTicketId());
				dailyInterest.setDate(sysDate.getTime());
				initializeDomainObject(userConfig, dailyInterest);
				getHibernateTemplate().save(dailyInterest);
				logger.info("End Creating Daily Interest : - " + ticket.getTicketId() + "  Interest Amount= " + interest);
				
				logger.info("**** Start Generating Reminders ****");
				/*Commenting due to Dayend get Delay
//				if (ticket.getTicketStatusId()==TicketStatusEnum.LAPS.getCode()){
					Map<String,Reminder> reminderMap=ticket.getReminderMap();

					//Get Reminder Parameter List
					ReminderParaCriteria=getSession().createCriteria(ReminderPara.class);
//					ReminderParaCriteria.add(Restrictions.eq("schemeId", ticket.getSchemeId()));
					ReminderParaCriteria.add(Restrictions.eq("productId", ticket.getProductId()));
					ReminderParaCriteria.add(Restrictions.eq("companyId" , userConfig.getCompanyId()));

					List<ReminderPara> reminderParaList=ReminderParaCriteria.list();
					Calendar reminderDate=Calendar.getInstance();
					Calendar ticketExpireDate=Calendar.getInstance();
					reminderDate.setTime(userConfig.getLoginDate());
					
					interestOutstanding=0;
					intPaid=0;
					capitalOutsanding=0;
					capPaid=0;
					otherOutstanding=0;
					othPaid=0;
					intAccrued=0;
					otherDue=0;

					for (DueFrom dueFrom : dueFormList) {
						if(intId == dueFrom.getDueTypeId()){
							interestOutstanding += dueFrom.getBalanceAmount();
							intPaid+=dueFrom.getPaidAmount();
							intAccrued+=dueFrom.getDueAmount();
						}else if(capId == dueFrom.getDueTypeId()){
							capitalOutsanding += dueFrom.getBalanceAmount();
							capPaid+=dueFrom.getPaidAmount();
						}else{
							otherOutstanding += dueFrom.getBalanceAmount();
							othPaid+=dueFrom.getPaidAmount();
							otherDue+=dueFrom.getDueAmount();
						}
					}

					for (ReminderPara reminderPara : reminderParaList) {
						if (ReminderCodeEnum.REM1 == ReminderCodeEnum.getEnumByCode(reminderPara.getCode())){
							Reminder remi = reminderMap.get(reminderPara.getCode());
							reminderDate.add(Calendar.DATE, reminderPara.getNumberOfDays());
							ticketExpireDate.setTime(ticket.getTicketExpiryDate());
							if (simpdate.format(reminderDate.getTime()).equals(simpdate.format(ticketExpireDate.getTime()))){
								if (!reminderMap.containsKey(reminderPara.getCode())){
									//Creating First Reminder Record
									Reminder reminder = new Reminder();
									reminder.setBranchId(branch.getBranchId());
									reminder.setCapitalOutsanding(capitalOutsanding);
									reminder.setCompanyId(userConfig.getCompanyId());
									reminder.setDateGenerated(userConfig.getLoginDate());
									reminder.setInterestOutstanding(interestOutstanding);
									reminder.setIsPrinted(0);
									reminder.setOtherOutstanding(otherOutstanding);
									reminder.setProductId(ticket.getProductId());
									reminder.setReminderParaId(reminderPara.getReminderParaId());
									reminder.setSchemeId(ticket.getSchemeId());
									reminder.setTicketId(ticket.getTicketId());
									reminder.setReminderParaCode(reminderPara.getCode());
									reminder.setIsBranchExplycit(true);
									initializeDomainObject(userConfig, reminder);
									getHibernateTemplate().save(reminder);
								}
							}
						//Creating Second Reminder Record
						}else if (ReminderCodeEnum.REM2 == ReminderCodeEnum.getEnumByCode(reminderPara.getCode())){
							if (!reminderMap.containsKey(reminderPara.getCode())){
								if (reminderMap.containsKey(ReminderCodeEnum.REM1.getCode())){
									Reminder rem1 = reminderMap.get(ReminderCodeEnum.REM1.getCode());
									if (rem1.getIsPrinted() == 1 ){										
										reminderDate.setTime(ticketExpireDate.getTime());
										reminderDate.add(Calendar.DATE, reminderPara.getNumberOfDays());
										if (simpdate.format(reminderDate.getTime()).equals(simpdate.format(sysDate.getTime()))){
											Reminder reminder2 = new Reminder();
											reminder2.setBranchId(branch.getBranchId());
											reminder2.setCapitalOutsanding(capitalOutsanding);
											reminder2.setCompanyId(userConfig.getCompanyId());
											reminder2.setDateGenerated(userConfig.getLoginDate());
											reminder2.setInterestOutstanding(interestOutstanding);
											reminder2.setIsPrinted(0);
											reminder2.setOtherOutstanding(otherOutstanding);
											reminder2.setProductId(ticket.getProductId());
											reminder2.setReminderParaId(reminderPara.getReminderParaId());
											reminder2.setSchemeId(ticket.getSchemeId());
											reminder2.setTicketId(ticket.getTicketId());
											reminder2.setReminderParaCode(reminderPara.getCode());
											reminder2.setIsBranchExplycit(true);
											initializeDomainObject(userConfig, reminder2);
											getHibernateTemplate().save(reminder2);
										}
									}
								}
							}
						//Creating Third Reminder Record
						}else if (ReminderCodeEnum.REM3 == ReminderCodeEnum.getEnumByCode(reminderPara.getCode())){
							if (!reminderMap.containsKey(reminderPara.getCode())){
								if (reminderMap.containsKey(ReminderCodeEnum.REM2.getCode())){
									Reminder rem2=reminderMap.get(ReminderCodeEnum.REM2.getCode());
									if(rem2.getIsPrinted()==1){
										reminderDate.setTime(ticketExpireDate.getTime());
										reminderDate.add(Calendar.DATE, reminderPara.getNumberOfDays());
										if (simpdate.format(reminderDate.getTime()).equals(simpdate.format(sysDate.getTime()))){
											Reminder reminder3=new Reminder();
											reminder3.setCapitalOutsanding(capitalOutsanding);
											reminder3.setCompanyId(userConfig.getCompanyId());
											reminder3.setDateGenerated(userConfig.getLoginDate());
											reminder3.setInterestOutstanding(interestOutstanding);
											reminder3.setIsPrinted(0);
											reminder3.setOtherOutstanding(otherOutstanding);
											reminder3.setProductId(ticket.getProductId());
											reminder3.setReminderParaId(reminderPara.getReminderParaId());
											reminder3.setSchemeId(ticket.getSchemeId());
											reminder3.setTicketId(ticket.getTicketId());
											reminder3.setReminderParaCode(reminderPara.getCode());
											reminder3.setIsBranchExplycit(true);
											reminder3.setBranchId(branch.getBranchId());
											initializeDomainObject(userConfig, reminder3);
											getHibernateTemplate().save(reminder3);
										}
									}
								}
							}
						}
//					}
					logger.info("**** End Generating Reminders ****");
					end of Comment */
				}
				//CRIB Process	
				/*Commenting as the dayend get delay  	
				if (monthEnd.get(Calendar.DAY_OF_MONTH) == 1 && ticket.getProductId() == productList.get(1).getProductId()) {
					
					try {
					
						
						dueReceiptCriteria=getSession().createCriteria(DueReceipt.class);
						dueReceiptCriteria.add(Restrictions.eq("ticketId", ticket.getTicketId()));
						dueReceiptCriteria.addOrder(Order.desc("dueReceiptId"));
						List<DueReceipt> dueReceipts=dueReceiptCriteria.list();
						
						CribData cribData =new CribData();
						cribData.setTicketId(ticket.getTicketId());
						cribData.setBranchId(ticket.getBranchId());
						cribData.setCompanyId(ticket.getCompanyId());
						cribData.setProductId(ticket.getProductId());
	 					cribData.setPawner(ticket.getPawner());
						cribData.setClosureDate(ticket.getTicketClosedRenewalDate());
						cribData.setIsAuctioned(ticket.getIsAuctioned());
						cribData.setLastUpdateDate(sysDate.getTime());
						cribData.setTicketStatusId(ticket.getTicketStatusId());
						cribData.setBranchCode(branch.getCode());
						cribData.setTicketNo(ticket.getTicketNumber());
						cribData.setPeriod(ticket.getPeriod());
						cribData.setGrantDate(ticket.getTicketDate());
						cribData.setExpireDate(ticket.getTicketExpiryDate());
						cribData.setAmountGranted(ticket.getPawnAdvance());
						
						
						cribData.setTotalCapitalOutstanding(capitalOutsanding);
						cribData.setTotalCapitalPaid(capPaid);
						cribData.setTotalInterestAccrued(intAccrued);
						cribData.setTotalInterestPaid(intPaid);
						cribData.setTotalOther(otherDue);
						cribData.setTotalOtherPaid(othPaid);
						
						for (DueReceipt dueReceipt : dueReceipts) {
							cribData.setLastReceipId(dueReceipt.getReceiptId());
							cribData.setLastReceiptDate(dueReceipt.getSettledDate());
							break;
						}
						initializeDomainObject(userConfig, cribData);
						getHibernateTemplate().save(cribData);
					} catch (Exception e) {
						throw new CommonDataAccessException("errors.cannotcreateCRIB");
					}
				}
				//End Of CRIB Process
			}

			logger.info("**** Begins Ledger Posting ****");

			Calendar ledgerDate=Calendar.getInstance();
			ledgerDate.setTime(userConfig.getLoginDate());
			
			for (Product product : productList) {
				List<DueReceipt>dueReceiptList = getSession().createCriteria(DueReceipt.class)
												.add(Restrictions.eq("companyId",userConfig.getCompanyId()))
												.add(Restrictions.eq("branchId", branch.getBranchId()))
												.add(Restrictions.eq("settledDate", ledgerDate.getTime()))
												.add(Restrictions.eq("productId", product.getProductId())).list();

				if (totalInterest > 0){
					Ledger ledgerInt = new Ledger();
					ledgerInt.setDebitAmount(totalInterest);
					ledgerInt.setDueType(intType);
					ledgerInt.setProductId(product.getProductId());
					ledgerInt.setDate(userConfig.getLoginDate());
					ledgerInt.setIsBranchExplycit(true);
					ledgerInt.setBranchId(branch.getBranchId());
					initializeDomainObject(userConfig, ledgerInt);
					getHibernateTemplate().save(ledgerInt);
					totalInterest =0;
				}


				if (!dueReceiptList.isEmpty()){
					for(DueType dueType:dueTypeList){
						double selltedAmount=0.0;
						for (DueReceipt dueReceipt : dueReceiptList) {
							if (dueReceipt.getDueTypeId() == dueType.getDueTypeId()){
								selltedAmount += dueReceipt.getSettleAmount();
							}
						}
						if (selltedAmount > 0){
							Ledger ledger = new Ledger();
							ledger.setCreditAmount(selltedAmount);
							ledger.setDueType(dueType);
							ledger.setProductId(product.getProductId());
							ledger.setDate(userConfig.getLoginDate());
							ledger.setIsBranchExplycit(true);
							ledger.setBranchId(branch.getBranchId());
							initializeDomainObject(userConfig, ledger);
							getHibernateTemplate().save(ledger);
							selltedAmount=0;
						}
					}
				}

				double totalAdvance=0.0;
				ticketList = getSession().createCriteria(Ticket.class)
							.add(Restrictions.eq("companyId",userConfig.getCompanyId()))
						    .add(Restrictions.eq("branchId", branch.getBranchId()))
						    .add(Restrictions.eq("ticketDate", ledgerDate.getTime()))
						    .add(Restrictions.eq("productId", product.getProductId()))
						    .add(Restrictions.eq("ticketStatusId", TicketStatusEnum.ACTIVE.getCode())).list();

				for (Ticket ticket : ticketList) {
					totalAdvance += ticket.getPawnAdvance();
				}
				if (totalAdvance > 0){
					Ledger ledgerAdv = new Ledger();
					ledgerAdv.setDebitAmount(totalAdvance);
					ledgerAdv.setDueType(payType);
					ledgerAdv.setProductId(product.getProductId());
					ledgerAdv.setDate(userConfig.getLoginDate());
					ledgerAdv.setIsBranchExplycit(true);
					ledgerAdv.setBranchId(branch.getBranchId());
					initializeDomainObject(userConfig, ledgerAdv);
					getHibernateTemplate().save(ledgerAdv);
					totalAdvance=0;
				}
			}

			logger.info("**** End Ledger Posting ****");
end of commenting
*/
			//Create record for SystemDate
	        Calendar calendar = Calendar.getInstance();
			calendar.setTime(userConfig.getLoginDate());
			calendar.add(Calendar.DATE, 1);

			SystemDate systemDate = branch.getSystemDate();
			systemDate.setPreviousDate(systemDate.getCurrentDate());
			systemDate.setCurrentDate(calendar.getTime());
			calendar.add(Calendar.DATE, 1);
			systemDate.setNextDate(calendar.getTime());
			getHibernateTemplate().update(systemDate);


			Calendar current = Calendar.getInstance();
			current.setTime(systemDate.getCurrentDate());

			int month = current.get(Calendar.MONTH);
			int date  = current.get(Calendar.DATE);

			if (month == Calendar.JANUARY && date == 1){

		        int financeYear = current.get(Calendar.YEAR);

		        current.set(financeYear, Calendar.JANUARY, 1);
		        Date financeYearBegin = current.getTime();

		        current.set(financeYear, Calendar.DECEMBER, 31);
		        Date financeYearEnd = current.getTime();

		        for (SerialMaster master : seriaList){
		        	if (master.getIsAnnually() == 1){
		        		Serial serial = new Serial();
		        		serial.setCompanyId(userConfig.getCompanyId());
		        		serial.setFinanceYear(financeYear);
		        		serial.setFinanceYearBegin(financeYearBegin);
		        		serial.setFinanceYearEnd(financeYearEnd);
		        		serial.setSerialCode(master.getSerialCode());
		        		serial.setSerialValue(0);
		        		serial.setBranchId(branch.getBranchId());
		        		serial.setProductId(1);

		        		getHibernateTemplate().save(serial);
		        	}
		        }
			}
		}
		logger.info("**** Start Ledger Entry Printing ****");
		
		//TODO: Very Very Bad Design  - need to globalize all the reusable  date objects
		Calendar ledgerCalander = Calendar.getInstance();
		Calendar ledgerCalanderStart = Calendar.getInstance();
		Calendar ledgerCalanderEnd = Calendar.getInstance();
		
		
		ledgerCalander.setTime(userConfig.getLoginDate());
		ledgerCalanderStart.set(ledgerCalander.get(Calendar.YEAR), ledgerCalander.get(Calendar.MONTH), ledgerCalander.getActualMinimum(Calendar.DATE));
		ledgerCalanderEnd.set(ledgerCalander.get(Calendar.YEAR), ledgerCalander.get(Calendar.MONTH), ledgerCalander.getActualMaximum(Calendar.DATE));
		
		ledgerCalander.add(Calendar.DATE, 1);
		/* Commenting as DayEnd get Delay
		if (ledgerCalander.get(Calendar.DAY_OF_MONTH) == 1) {
		
			String hql = "select b.code as branchId ,d.CODE as duetypeId, sum(l.crAmount) as crAmount, sum(l.drAmount) as drAmount from tblledger as l " +
			            "inner join tblbranch b on b.branchid = l.branchid " +
			            "inner join tblduetype d on d.duetypeid = l.duetypeid " +
			            "where l.date >= '" + sqlpdate.format(ledgerCalanderStart.getTime()) + "' " +
			            "and l.date < '" + sqlpdate.format(ledgerCalanderEnd.getTime()) + "' " +
			            "group by l.branchid,l.duetypeid"; 

			Query query = getSession().createSQLQuery(hql)
							.addScalar("branchId", Hibernate.STRING)
							.addScalar("duetypeId", Hibernate.STRING)
	                        .addScalar("crAmount", Hibernate.DOUBLE)
	                        .addScalar("drAmount", Hibernate.DOUBLE);
						
			List<Object[]> list = query.list();

			StringBuffer stringBuffer =new StringBuffer();
			for (Object object[] : list) {
				stringBuffer.append(object[0]);
				stringBuffer.append("|");
				stringBuffer.append(object[1]);
				stringBuffer.append("|");
				stringBuffer.append(object[2]);
				stringBuffer.append("|");
				stringBuffer.append(object[3]);
				stringBuffer.append("|");
				stringBuffer.append(simpdate.format(ledgerCalanderEnd.getTime()));
				stringBuffer.append("\n");
			}	
			
			StringBuffer cribDataSB=new StringBuffer();
			cribCriteria = getSession().createCriteria(CribData.class);
			cribCriteria.add(Restrictions.eq("lastUpdateDate", userConfig.getLoginDate()));
			List<CribData> cribDataList=cribCriteria.list();
			
			cribDataSB.append("PAWNING CRIB OUTPUT FILE AS AT | ");
			cribDataSB.append(simpdate.format(sysDate.getTime()));
			cribDataSB.append("\n");
			cribDataSB.append("GENERATE DATE/TIME | ");
			cribDataSB.append(simpdate.format(Calendar.getInstance().getTime()));
			cribDataSB.append("|");
			cribDataSB.append(timeFormat.format(Calendar.getInstance().getTime()));
			cribDataSB.append("\n");
			cribDataSB.append("GENERATE USER :|");
			cribDataSB.append(userConfig.getLoginName());
			cribDataSB.append("\n");
			for (CribData cribData : cribDataList) {
				
				cribDataSB.append(cribData.getBranchCode());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getTicketNo());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getPeriod());
				cribDataSB.append("|");
				cribDataSB.append(simpdate.format(cribData.getGrantDate()));
				cribDataSB.append("|");
				cribDataSB.append(simpdate.format(cribData.getExpireDate()));
				cribDataSB.append("|");
				cribDataSB.append(cribData.getAmountGranted());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getTotalCapitalPaid());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getAmountGranted()-cribData.getTotalCapitalPaid());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getTotalInterestAccrued());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getTotalInterestPaid());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getTotalInterestAccrued() - cribData.getTotalInterestPaid());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getTotalOther());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getTotalOtherPaid());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getTotalOther() - cribData.getTotalOtherPaid());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getLastReceiptDate() == null ? "":simpdate.format(cribData.getLastReceiptDate()));
				cribDataSB.append("|");
				cribDataSB.append(cribData.getTicketStatusId());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getClosureDate() == null ? "":simpdate.format(cribData.getClosureDate()));
				cribDataSB.append("|");
				cribDataSB.append(cribData.getIsAuctioned() == 1 ? "YES":"NO");
				cribDataSB.append("|");
				cribDataSB.append(cribData.getPawner().getPawnerCode());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getPawner().getIdOrBrNo());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getPawner().getPassportNo() == null ? "":cribData.getPawner().getPassportNo());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getPawner().getDrivingLicenseNumber() == null ? "":cribData.getPawner().getDrivingLicenseNumber());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getPawner().getTitle());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getPawner().getInitials());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getPawner().getSurName());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getPawner().getInitialsInFull());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getPawner().getMaritalStatus());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getPawner().getSex());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getPawner().getAddressLine1());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getPawner().getAddressLine2());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getPawner().getAddressLine3());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getPawner().getAddressLine4());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getPawner().getHomeTelephoneNo());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getPawner().getMobileNo());
				cribDataSB.append("|");
				cribDataSB.append(cribData.getPawner().getEmailAddress());
				cribDataSB.append("\n");
				
			} 
			
			try{
				StringBuilder buffer = new StringBuilder();
				buffer.append(file.getCanonicalPath() + "\\gl\\");
				buffer.append(simpdate.format(ledgerCalanderEnd.getTime()));
				buffer.append(".txt");
				logger.info("Try to write to GL File Name : "+ buffer.toString());
				
				FileWriter fileWriter   = new FileWriter(buffer.toString(),true);
				BufferedWriter glOutput = new BufferedWriter(fileWriter);
				glOutput.write(stringBuffer.toString());
				glOutput.close();
				fileWriter.close();
				sendMail.sendMessage("it@pmb.lk,mahinda@modular4.com,itsupport@pmb.lk" , "Monthly GL Download", "", "",buffer.toString());
				
				
				ledgerCalanderEnd.add(Calendar.DATE, -1);
				StringBuilder CribBuffer = new StringBuilder();
				CribBuffer.append(file.getCanonicalPath() + "\\CRIB\\");
				CribBuffer.append("PAWN_CRIB_");
				CribBuffer.append(simpdate.format(ledgerCalanderEnd.getTime()));
				CribBuffer.append(".txt");
				logger.info("Try to write to CRIB File Name : "+ CribBuffer.toString());
				fileWriter   = new FileWriter(CribBuffer.toString(),true);
				BufferedWriter cribOutput = new BufferedWriter(fileWriter);
				cribOutput.write(cribDataSB.toString());
				cribOutput.close();
				fileWriter.close();
				sendMail.sendMessage("it@pmb.lk,itsupport@pmb.lk" , "CRIB Download", "", "",CribBuffer.toString());
				
			
				
			}catch (IOException ex){
				logger.error(ex.getMessage()+ file.getAbsolutePath());
				throw new CommonDataAccessException("error.cannotcreatefile");
			}
			catch (MessagingException e) {
				logger.error(e.getMessage());
				throw new CommonDataAccessException("errors.sendmailfail");
			}
			
		}

		logger.info("**** End Ledger Entry Printing ****");
*/
		logger.debug("**** Leaving from doDayEndProcess method ****");
	}

	public List<Branch> getInitialData(UserConfig userConfig){
		logger.debug("**** Enter in to getInitialData ****");
		Branch branch = (Branch)getSession().get(Branch.class, userConfig.getBranchId());
		List<Branch> branchList = null;

		if(branch.getIsDefault()==1){
			getSession().evict(branch);
			Criteria criteria = getSession().createCriteria(Branch.class);
			criteria.add(Restrictions.eq("companyId", userConfig.getCompanyId()));
			criteria.add(Restrictions.eq("recordStatus", RecordStatusEnum.ACTIVE.getCode()));
			criteria.setFetchMode("systemDate",FetchMode.JOIN);
			branchList = criteria.list();
		}else{
			Criteria criteria = getSession().createCriteria(Branch.class);
			criteria.add(Restrictions.eq("branchId", branch.getBranchId()));
			criteria.setFetchMode("systemDate",FetchMode.JOIN);
			branchList = criteria.list();
		}
		logger.debug("**** Leaving from getInitialData ****");
		return branchList;
	}

	public static double round(double d, int decimalPlace){
	    // see the Javadoc about why we use a String in the constructor
	    // http://java.sun.com/j2se/1.5.0/docs/api/java/math/BigDecimal.html#BigDecimal(double)
	    BigDecimal bd = new BigDecimal(Double.toString(d));
	    bd = bd.setScale(decimalPlace,BigDecimal.ROUND_HALF_UP);
	    return bd.doubleValue();
	}

	@Override 
	public void processPasswordExpire(UserConfig userConfig) {
		Calendar passwordNotify =Calendar.getInstance();
		passwordNotify.setTime(userConfig.getLoginDate());
		
		Calendar passwordExpire =Calendar.getInstance();		
		/*Commenting due to Dayend Get Delay
		List<Officer> officers =null;
		Criteria criteria=getSession().createCriteria(Officer.class);
		criteria.add(Restrictions.eq("isActive", 1));
		criteria.setFetchMode("pawner", FetchMode.JOIN);
		officers = criteria.list();
		
		
		for (Officer officer : officers) {
			passwordExpire.setTime(officer.getPasswordValidPeriod());
			dateDiff = (Long) passwordExpire.getTimeInMillis() - passwordNotify.getTimeInMillis();
			if((dateDiff - tenDays) <= tenDays){
				String message = "To Change Your Password \n Log in to Pawning System --> \n Navigate to Re-Set Password....";
				String recepientList = officer.getPawner().getEmailAddress();
				String subject = "Your Pawn Password Will be Expired in " + noOfDays + " Days";
				
				noOfDays = (int) ((tenDays + (dateDiff - tenDays)) / oneDay);
				try{
					if (noOfDays > 0){
						sendMail.sendMessage(recepientList ,subject , message, "");
					}
				}
				catch (MessagingException e) {
					logger.error(e.getMessage());
//					throw new CommonDataAccessException("errors.sendmailfail");
				}
			}
			
		}
		*/
		
	}
}

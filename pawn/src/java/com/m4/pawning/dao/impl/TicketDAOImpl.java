package com.m4.pawning.dao.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.m4.core.exception.CommonDataAccessException;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.RecordStatusEnum;
import com.m4.core.util.TransactionDAOSupport;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.TicketDAO;
import com.m4.pawning.domain.Branch;
import com.m4.pawning.domain.Cartage;
import com.m4.pawning.domain.DailyInterst;
import com.m4.pawning.domain.DueFrom;
import com.m4.pawning.domain.DueTo;
import com.m4.pawning.domain.DueType;
import com.m4.pawning.domain.InterestSlab;
import com.m4.pawning.domain.Ledger;
import com.m4.pawning.domain.Officer;
import com.m4.pawning.domain.ParameterValue;
import com.m4.pawning.domain.Pawner;
import com.m4.pawning.domain.Product;
import com.m4.pawning.domain.Schemes;
import com.m4.pawning.domain.Ticket;
import com.m4.pawning.domain.TicketArticle;
import com.m4.pawning.domain.UserGroup;
import com.m4.pawning.dto.AuthorizeTicketDTO;
import com.m4.pawning.util.EnglishNumberToWords;
import com.m4.pawning.util.ParameterEnum;
import com.m4.pawning.util.TicketStatusEnum;

public class TicketDAOImpl extends TransactionDAOSupport implements TicketDAO {
	private static final Logger logger = Logger.getLogger(TicketDAOImpl.class);

	public String createTicket(UserConfig userConfig, Ticket ticket, List<TicketArticle> list) {
		logger.debug("**** Enter in to createTicket method ****");

		//Set the Serial Number to ticket And ticketNumber
		final SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		DecimalFormat decimalFormat = new DecimalFormat("000000");
		String serialNumber = decimalFormat.format(getSerialValue(userConfig, "TKT" , ticket.getProductId()));
		String [] dueType={"INT","CAP","DOC"};

		StringBuilder ticketNumber = new StringBuilder();
		ticketNumber.append(((Branch)getSession().get(Branch.class, userConfig.getBranchId())).getCode());
		ticketNumber.append(((Product)getSession().get(Product.class, ticket.getProductId())).getCode());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(userConfig.getLoginDate());
		ticketNumber.append(Integer.toString(calendar.get(Calendar.YEAR)).substring(2));
		ticketNumber.append(serialNumber);

		ticket.setTicketSerialNumber(serialNumber);
		ticket.setTicketNumber(ticketNumber.toString());
		ticket.setPawnAdvInWord(EnglishNumberToWords.convert(Math.round(ticket.getPawnAdvance())));

		//Set the totalInterestAccrued to ticket
		int period = ticket.getPeriod() * 30;
		double interrest = 0.0;
		int tempInterestDays = 0;

		Criteria slabCriteria = getSession().createCriteria(InterestSlab.class);
		slabCriteria.add(Restrictions.eq("interestSlabCode", ticket.getInterestSlabId()));
		slabCriteria.add(Restrictions.eq("recordStatus", RecordStatusEnum.ACTIVE.getCode()));
		slabCriteria.add(Restrictions.eq("companyId", userConfig.getCompanyId()));
		slabCriteria.add(Restrictions.eq("branchId", userConfig.getBranchId()));
		slabCriteria.addOrder(Order.asc("slabNo"));
		List<InterestSlab> slabList = slabCriteria.list();

		//Get parameter value for MinIneterestDay
		Criteria paraCriteria = getSession().createCriteria(ParameterValue.class);
		paraCriteria.add(Restrictions.eq("parameterId", ParameterEnum.MININTERESTDAYS.getCode()));
		paraCriteria.add(Restrictions.le("effDate", userConfig.getLoginDate()));
		paraCriteria.addOrder(Order.desc("effDate"));
		List<ParameterValue> paraValList = paraCriteria.list();
		int minInterestDays = Integer.parseInt(paraValList.get(0).getParaValue());

		//Get parameter value for MinIneterestDay
		Criteria paraMinIntCriteria = getSession().createCriteria(ParameterValue.class);
		if(ticket.getProductId()==1)
			paraMinIntCriteria.add(Restrictions.eq("parameterId", ParameterEnum.MININTEREST.getCode()));
		else
			paraMinIntCriteria.add(Restrictions.eq("parameterId", ParameterEnum.MININTEREST1.getCode()));
		paraMinIntCriteria.add(Restrictions.le("effDate", userConfig.getLoginDate()));
		paraMinIntCriteria.addOrder(Order.desc("effDate"));
		List<ParameterValue> paraDocList = paraMinIntCriteria.list();
		
		
//		//Modified By Mahinda 29-03-2011
//		//Get parameter value for Maximum Advance Amount
//		Criteria paraMaxAdvCriteria = getSession().createCriteria(ParameterValue.class);
//		paraMaxAdvCriteria.add(Restrictions.eq("parameterId", ParameterEnum.MAXPAWNADDFORCUSTOMER.getCode()));
//		paraMaxAdvCriteria.add(Restrictions.le("effDate", userConfig.getLoginDate()));
//		paraMaxAdvCriteria.addOrder(Order.desc("effDate"));
//		List<ParameterValue> paraMaxAdvList = paraMaxAdvCriteria.list();


		if (paraDocList.size() == 0){
			throw new CommonDataAccessException("errors.mininterestnotdefined");
		}
		
//		if(paraMaxAdvList.size() == 0){
//			throw new CommonDataAccessException("errors.maxadvancenotdefined");
//		}
			

		double docCharges = Integer.parseInt(paraDocList.get(0).getParaValue());
		double dueFormInt = 0,interestRate=0.00;
		int tempDays = 0;
		tempDays = minInterestDays;
//		double maxAdvance= Integer.parseInt(paraMaxAdvList.get(0).getParaValue());
		//Calculate the Total Interest
		for(InterestSlab slab:slabList){
			int days = slab.getNoOfDaysTo() - slab.getNoOfDaysFrom() + 1;
			if(period > days)
			{
				interrest += ticket.getPawnAdvance() * slab.getRate() / 36500 * days;
				period -= days;
			}
			else{
				interrest += ticket.getPawnAdvance() * slab.getRate() / 36500 * period;
				interestRate = slab.getRate();
				break;
			}
		}

		//Save INT record
		
		for(InterestSlab slab:slabList){
			int days = slab.getNoOfDaysTo() - slab.getNoOfDaysFrom() + 1;
			if(tempDays > days){
				dueFormInt += ticket.getPawnAdvance() * slab.getRate() / 36500 * days;
				tempDays -= days;
			}else{
				dueFormInt += ticket.getPawnAdvance() * slab.getRate() / 36500 * tempDays;
				interestRate = slab.getRate();
				break;
			}
			interestRate = slab.getRate();
		}
//		//this should change
		//Issue Id 18 Remove Minimum Interest Calculation
		//Modified By Mahinda
		//Date 31/08/2012 
		
		//Modified By Mahinda
		//Date 20/12/2016
		double perDayInt = ticket.getPawnAdvance() * interestRate / 36500;
		//int minDays = (int) (minInterest / perDayInt);

		//Calendar calendar2=Calendar.getInstance();
		TimeZone timezone = TimeZone.getTimeZone("IST");
		//calendar2.setTimeZone(timezone);
		Date date=new Date();
		
		calendar.setTime(date);
		dateFormat.setTimeZone(timezone);
		
		//Set Pawner
		ticket.setPawner((Pawner)getSession().get(Pawner.class, ticket.getPawner().getPawnerId()));
		ticket.setOfficer((Officer)getSession().get(Officer.class, userConfig.getUserId()));
		ticket.setTicketDate(userConfig.getLoginDate());
		ticket.setMinDays(minInterestDays);
		ticket.setPrintTime(dateFormat.format(calendar.getTime()));
		
		//Save ticket
		ticket.setTotalInterestAccrued(interrest);
		//Modified By Mahinda 29-03-2011
//		if (maxAdvance < ticket.getPawnAdvance()){
			ticket.setTicketStatusId(TicketStatusEnum.PENDING.getCode());
//		}else {
//			ticket.setTicketStatusId(TicketStatusEnum.ACTIVE.getCode());
//		}
		//ticket.setMinDays(tempInterestDays);
		initializeDomainObject(userConfig, ticket);
		getHibernateTemplate().save(ticket);

		//Save ticket item breakdawn TicketArticle
		for(TicketArticle ticketArticle:list){
			initializeTransactionDomainObject(userConfig, ticketArticle);
			ticketArticle.setCartage((Cartage)getHibernateTemplate().get(Cartage.class, ticketArticle.getCartage().getCartageId()));
			ticketArticle.setTicketId(ticket.getTicketId());
			ticketArticle.setIsActive(1);
			getHibernateTemplate().save(ticketArticle);
		}

		//Save 2 records to the DueFrom table
		//Issue Id 18 get DueTypes
		//Modified By Mahinda
		//Date 31/08/2012		
		Criteria dueCriteria = getSession().createCriteria(DueType.class);
		dueCriteria.add(Restrictions.in("dueType", dueType));
		dueCriteria.addOrder(Order.asc("dueType"));
		dueCriteria.setLockMode(LockMode.READ);
		List<DueType> dueList = dueCriteria.list();

		//Save CAP record
		DueFrom dueFromCAP = new DueFrom();
		dueFromCAP.setProductId(ticket.getProductId());
		dueFromCAP.setTicketId(ticket.getTicketId());
		dueFromCAP.setDueTypeId(dueList.get(0).getDueTypeId());
		dueFromCAP.setDueAmount(ticket.getPawnAdvance());
		dueFromCAP.setPaidAmount(0);
		dueFromCAP.setBalanceAmount(ticket.getPawnAdvance());
		initializeTransactionDomainObject(userConfig, dueFromCAP);
		getHibernateTemplate().save(dueFromCAP);

		//Save INT Record
		DueFrom dueFromINT = new DueFrom();
		dueFromINT.setProductId(ticket.getProductId());
		dueFromINT.setTicketId(ticket.getTicketId());
		dueFromINT.setDueTypeId(dueList.get(2).getDueTypeId());
		dueFromINT.setDueAmount(dueFormInt);
		dueFromINT.setPaidAmount(0);
		dueFromINT.setBalanceAmount(dueFormInt);
		initializeTransactionDomainObject(userConfig, dueFromINT);
		getHibernateTemplate().save(dueFromINT);
		
		//Save Doc Charge Record
		DueFrom dueFromDOC = new DueFrom();
		dueFromDOC.setProductId(ticket.getProductId());
		dueFromDOC.setTicketId(ticket.getTicketId());
		dueFromDOC.setDueTypeId(dueList.get(1).getDueTypeId());
		dueFromDOC.setDueAmount(round(docCharges,2));
		dueFromDOC.setPaidAmount(0);
		dueFromDOC.setBalanceAmount(round(docCharges,2));
		initializeTransactionDomainObject(userConfig, dueFromDOC);
		getHibernateTemplate().save(dueFromDOC);		

		//Save data to DueTo table
		Criteria dueToCriteria = getSession().createCriteria(DueType.class);
		dueToCriteria.add(Restrictions.eq("dueType", "PAY"));
//		dueToCriteria.add(Restrictions.eq("branchId",userConfig.getBranchId()));
//		dueToCriteria.add(Restrictions.eq("companyId",userConfig.getCompanyId()));
		DueType dueTypePAY = (DueType)dueToCriteria.uniqueResult();

		DueTo dueTo = new DueTo();
		dueTo.setProductId(ticket.getProductId());
		dueTo.setTicketId(ticket.getTicketId());
		dueTo.setPawnerId(ticket.getPawner().getPawnerId());
		dueTo.setDueTypeId(dueTypePAY.getDueTypeId());
		dueTo.setDueAmount(ticket.getPawnAdvance());
		dueTo.setPaidAmount(0);
		dueTo.setBalanceAmount(ticket.getPawnAdvance());
		initializeTransactionDomainObject(userConfig, dueTo);
		getHibernateTemplate().save(dueTo);

		logger.info("Start Creating Daily Interest : - " + ticket.getTicketId() + "  Interest Amount= " + dueFormInt);
		DailyInterst dailyInterest=new DailyInterst();
		dailyInterest.setInterestRate(round(interestRate, 2));
		dailyInterest.setInterstAmount(round(dueFormInt, 2));
		dailyInterest.setTicketId(ticket.getTicketId());
		dailyInterest.setDate(userConfig.getLoginDate());

		initializeDomainObject(userConfig, dailyInterest);
		getHibernateTemplate().save(dailyInterest);
		logger.info("End Creating Daily Interest : - " + ticket.getTicketId() + "  Interest Amount= " + dueFormInt);

		logger.debug("**** Leaving from createTicket method ****");
		return ticket.getTicketNumber();
	}
	public String createTicket(UserConfig userConfig, Ticket ticket, List<TicketArticle> list,boolean redeem) {
		logger.debug("**** Enter in to createTicket method ****");

		//Set the Serial Number to ticket And ticketNumber
		DecimalFormat decimalFormat = new DecimalFormat("000000");
		String serialNumber = decimalFormat.format(getSerialValue(userConfig, "TKT" , ticket.getProductId()));
		String [] dueType={"INT","CAP","DOC"};

		StringBuilder ticketNumber = new StringBuilder();
		ticketNumber.append(((Branch)getSession().get(Branch.class, userConfig.getBranchId())).getCode());
		ticketNumber.append(((Product)getSession().get(Product.class, ticket.getProductId())).getCode());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(userConfig.getLoginDate());
		ticketNumber.append(Integer.toString(calendar.get(Calendar.YEAR)).substring(2));
		ticketNumber.append(serialNumber);

		ticket.setTicketSerialNumber(serialNumber);
		ticket.setTicketNumber(ticketNumber.toString());
		ticket.setPawnAdvInWord(EnglishNumberToWords.convert(Math.round(ticket.getPawnAdvance())));

		//Set the totalInterestAccrued to ticket
		int period = ticket.getPeriod() * 30;
		double interrest = 0.0;
		int tempInterestDays = 0;

		Criteria slabCriteria = getSession().createCriteria(InterestSlab.class);
		slabCriteria.add(Restrictions.eq("interestSlabCode", ticket.getInterestSlabId()));
		slabCriteria.add(Restrictions.eq("recordStatus", RecordStatusEnum.ACTIVE.getCode()));
		slabCriteria.add(Restrictions.eq("companyId", userConfig.getCompanyId()));
		slabCriteria.add(Restrictions.eq("branchId", userConfig.getBranchId()));
		slabCriteria.addOrder(Order.asc("slabNo"));
		List<InterestSlab> slabList = slabCriteria.list();

		//Get parameter value for MinIneterestDay
		Criteria paraCriteria = getSession().createCriteria(ParameterValue.class);
		paraCriteria.add(Restrictions.eq("parameterId", ParameterEnum.MININTERESTDAYS.getCode()));
		paraCriteria.add(Restrictions.le("effDate", userConfig.getLoginDate()));
		paraCriteria.addOrder(Order.desc("effDate"));
		List<ParameterValue> paraValList = paraCriteria.list();
		int minInterestDays = Integer.parseInt(paraValList.get(0).getParaValue());

		//Get parameter value for MinIneterestDay
		Criteria paraMinIntCriteria = getSession().createCriteria(ParameterValue.class);
		paraMinIntCriteria.add(Restrictions.eq("parameterId", ParameterEnum.MININTEREST.getCode()));
		paraMinIntCriteria.add(Restrictions.le("effDate", userConfig.getLoginDate()));
		paraMinIntCriteria.addOrder(Order.desc("effDate"));
		List<ParameterValue> paraDocList = paraMinIntCriteria.list();
		
		
//		//Modified By Mahinda 29-03-2011
//		//Get parameter value for Maximum Advance Amount
//		Criteria paraMaxAdvCriteria = getSession().createCriteria(ParameterValue.class);
//		paraMaxAdvCriteria.add(Restrictions.eq("parameterId", ParameterEnum.MAXPAWNADDFORCUSTOMER.getCode()));
//		paraMaxAdvCriteria.add(Restrictions.le("effDate", userConfig.getLoginDate()));
//		paraMaxAdvCriteria.addOrder(Order.desc("effDate"));
//		List<ParameterValue> paraMaxAdvList = paraMaxAdvCriteria.list();


		if (paraDocList.size() == 0){
			throw new CommonDataAccessException("errors.mininterestnotdefined");
		}
		
//		if(paraMaxAdvList.size() == 0){
//			throw new CommonDataAccessException("errors.maxadvancenotdefined");
//		}
			

		double minInterest = Integer.parseInt(paraDocList.get(0).getParaValue());
//		double maxAdvance= Integer.parseInt(paraMaxAdvList.get(0).getParaValue());
		//Calculate the Total Interest
		for(InterestSlab slab:slabList){
			int days = slab.getNoOfDaysTo() - slab.getNoOfDaysFrom() + 1;
			if(period > days)
			{
				interrest += ticket.getPawnAdvance() * slab.getRate()/36500 * days;
				period -= days;
			}
			else{
				interrest += ticket.getPawnAdvance() * slab.getRate()/36500 * period;
				break;
			}
		}

		//Save INT record
		double dueFormInt = 0,interestRate=0.00;
		for(InterestSlab slab:slabList){
			int days = slab.getNoOfDaysTo() - slab.getNoOfDaysFrom() + 1;
			if(minInterestDays > days){
				dueFormInt += ticket.getPawnAdvance() * slab.getRate()/36500 * days;
				minInterestDays -= days;
			}else{
				dueFormInt += ticket.getPawnAdvance() * slab.getRate()/36500 * minInterestDays;
				break;
			}
			interestRate = slab.getRate();
		}
//		//this should change
		//Issue Id 18 Remove Minimum Interest Calculation
		//Modified By Mahinda
		//Date 31/08/2012 


		//Set Pawner
		ticket.setPawner((Pawner)getSession().get(Pawner.class, ticket.getPawner().getPawnerId()));
		ticket.setOfficer((Officer)getSession().get(Officer.class, userConfig.getUserId()));
		ticket.setTicketDate(userConfig.getLoginDate());

		//Save ticket
		ticket.setTotalInterestAccrued(interrest);
		//Modified By Mahinda 29-03-2011
//		if (maxAdvance < ticket.getPawnAdvance()){
			ticket.setTicketStatusId(TicketStatusEnum.PENDING.getCode());
//		}else {
//			ticket.setTicketStatusId(TicketStatusEnum.ACTIVE.getCode());
//		}
		ticket.setMinDays(0);
		initializeDomainObject(userConfig, ticket);
		getHibernateTemplate().save(ticket);

		//Save ticket item breakdawn TicketArticle
		for(TicketArticle ticketArticle:list){
			initializeTransactionDomainObject(userConfig, ticketArticle);
			ticketArticle.setCartage((Cartage)getHibernateTemplate().get(Cartage.class, ticketArticle.getCartage().getCartageId()));
			ticketArticle.setTicketId(ticket.getTicketId());
			ticketArticle.setIsActive(1);
			getHibernateTemplate().save(ticketArticle);
		}

		//Save 2 records to the DueFrom table
		//Issue Id 18 get DueTypes
		//Modified By Mahinda
		//Date 31/08/2012		
		Criteria dueCriteria = getSession().createCriteria(DueType.class);
		dueCriteria.add(Restrictions.in("dueType", dueType));
		dueCriteria.addOrder(Order.asc("dueType"));
		dueCriteria.setLockMode(LockMode.READ);
		List<DueType> dueList = dueCriteria.list();

		//Save CAP record
		DueFrom dueFromCAP = new DueFrom();
		dueFromCAP.setProductId(ticket.getProductId());
		dueFromCAP.setTicketId(ticket.getTicketId());
		dueFromCAP.setDueTypeId(dueList.get(0).getDueTypeId());
		dueFromCAP.setDueAmount(ticket.getPawnAdvance());
		dueFromCAP.setPaidAmount(0);
		dueFromCAP.setBalanceAmount(ticket.getPawnAdvance());
		initializeTransactionDomainObject(userConfig, dueFromCAP);
		getHibernateTemplate().save(dueFromCAP);

		//Save INT Record
		DueFrom dueFromINT = new DueFrom();
		dueFromINT.setProductId(ticket.getProductId());
		dueFromINT.setTicketId(ticket.getTicketId());
		dueFromINT.setDueTypeId(dueList.get(2).getDueTypeId());
		dueFromINT.setDueAmount(round(dueFormInt,2));
		dueFromINT.setPaidAmount(0);
		dueFromINT.setBalanceAmount(round(dueFormInt,2));
		initializeTransactionDomainObject(userConfig, dueFromINT);
		getHibernateTemplate().save(dueFromINT);
		
		//Save Doc Charge Record
		if(!redeem){
			DueFrom dueFromDOC = new DueFrom();
			dueFromDOC.setProductId(ticket.getProductId());
			dueFromDOC.setTicketId(ticket.getTicketId());
			dueFromDOC.setDueTypeId(dueList.get(1).getDueTypeId());
			dueFromDOC.setDueAmount(round(minInterest,2));
			dueFromDOC.setPaidAmount(0);
			dueFromDOC.setBalanceAmount(round(minInterest,2));
			initializeTransactionDomainObject(userConfig, dueFromDOC);
			getHibernateTemplate().save(dueFromDOC);		
		}
		//Save data to DueTo table
		Criteria dueToCriteria = getSession().createCriteria(DueType.class);
		dueToCriteria.add(Restrictions.eq("dueType", "PAY"));
//		dueToCriteria.add(Restrictions.eq("branchId",userConfig.getBranchId()));
//		dueToCriteria.add(Restrictions.eq("companyId",userConfig.getCompanyId()));
		DueType dueTypePAY = (DueType)dueToCriteria.uniqueResult();

		DueTo dueTo = new DueTo();
		dueTo.setProductId(ticket.getProductId());
		dueTo.setTicketId(ticket.getTicketId());
		dueTo.setPawnerId(ticket.getPawner().getPawnerId());
		dueTo.setDueTypeId(dueTypePAY.getDueTypeId());
		dueTo.setDueAmount(ticket.getPawnAdvance());
		dueTo.setPaidAmount(0);
		dueTo.setBalanceAmount(ticket.getPawnAdvance());
		initializeTransactionDomainObject(userConfig, dueTo);
		getHibernateTemplate().save(dueTo);

		logger.info("Start Creating Daily Interest : - " + ticket.getTicketId() + "  Interest Amount= " + dueFormInt);
		DailyInterst dailyInterest=new DailyInterst();
		dailyInterest.setInterestRate(round(interestRate, 2));
		dailyInterest.setInterstAmount(round(dueFormInt, 2));
		dailyInterest.setTicketId(ticket.getTicketId());
		dailyInterest.setDate(userConfig.getLoginDate());

		initializeDomainObject(userConfig, dailyInterest);
		getHibernateTemplate().save(dailyInterest);
		logger.info("End Creating Daily Interest : - " + ticket.getTicketId() + "  Interest Amount= " + dueFormInt);

		logger.debug("**** Leaving from createTicket method ****");
		return ticket.getTicketNumber();
	}

	public DataBag getAllTicket(UserConfig userConfig,List<QueryCriteria> criteriaList) {
		logger.debug("**** Enter in to getAllTicket method ****");
		DataBag ticketBag = null;
		Criteria criteria = getSession().createCriteria(Ticket.class);
		criteria.setFetchMode("dueFromCollection", FetchMode.JOIN);
		criteria.setFetchMode("reminderMap", FetchMode.JOIN);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		//Changed By Mahinda 24th-May-2011
		criteria.addOrder(Order.asc("ticketNumber"));
		//criteria.setLockMode(LockMode.READ);
		ticketBag = getDataList(getHibernateTemplate(), criteriaList, criteria);
		logger.debug("**** Leaving from getAllTicket method ****");
		return ticketBag;
	}

	public DataBag getAllTicketWithOR(UserConfig userConfig,List<QueryCriteria> criteriaList) {
		logger.debug("**** Enter in to getAllTicketWithOR method ****");
		DataBag ticketBag = null;
		Criteria criteria = getSession().createCriteria(Ticket.class);
		criteria.setFetchMode("dueFromCollection", FetchMode.JOIN);
		criteria.setFetchMode("reminderMap", FetchMode.JOIN);
		criteria.setFetchMode("ticketArticleSet", FetchMode.JOIN);

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.or(Restrictions.eq("ticketStatusId", TicketStatusEnum.ACTIVE.getCode()), Restrictions.eq("ticketStatusId", TicketStatusEnum.LAPS.getCode())));
		ticketBag = getDataList(getHibernateTemplate(), criteriaList, criteria);
		logger.debug("**** Leaving from getAllTicketWithOR method ****");
		return ticketBag;
	}

	public List getSerchTicketData(UserConfig userConfig,String sql){
		logger.debug("**** Enter in to getSerchTicketData method ****");
		String hql = "SELECT t.ticketId,t.ticketNumber,t.pawner.title, t.pawner.initials , t.pawner.surName,t.pawner.pawnerCode,t.version " +
					 " FROM Ticket t INNER JOIN t.pawner WHERE t.companyId=:companyId AND t.branchId=:branchId " + sql;

		Query query = getSession().createQuery(hql);
		//query.setInteger("ticketStatusId", TicketStatusEnum.ACTIVE.getCode());
		query.setInteger("companyId", userConfig.getCompanyId());
		query.setInteger("branchId", userConfig.getBranchId());

		List list = query.list();
		logger.debug("**** Leaving from getSerchTicketData method ****");
		return list;
	}

	public Ticket getTicketById(UserConfig userConfig,int ticketId){
		logger.debug("**** Enter in to geTicketById method ****");
		Ticket ticket = null;
		Criteria criteria=getSession().createCriteria(Ticket.class);
		criteria.add(Restrictions.eq("ticketId", ticketId));
		criteria.setFetchMode("reminderMap", FetchMode.JOIN);
		ticket = (Ticket)criteria.uniqueResult();

		if(ticket == null){
			throw new CommonDataAccessException("errors.recordnotfound");
		}
		logger.debug("**** Leaving from geTicketById method ****");
		return ticket;
	}

	public Ticket getTicketByTicketNumber(UserConfig userConfig,String number){
		logger.debug("**** Enter in to geTicketById method ****");
		Ticket ticket = null;
		Criteria criteria = getSession().createCriteria(Ticket.class);
		criteria.add(Restrictions.eq("ticketNumber", number));
		criteria.setFetchMode("reminderMap", FetchMode.JOIN);
		criteria.add(Restrictions.ne("ticketStatusId", TicketStatusEnum.PENDING.getCode()));
		criteria.add(Restrictions.eq("companyId", userConfig.getCompanyId()));
		criteria.add(Restrictions.eq("branchId", userConfig.getBranchId()));

		ticket = (Ticket)criteria.uniqueResult();

		if(ticket == null){
			throw new CommonDataAccessException("errors.recordnotfound");
		}
		logger.debug("**** Leaving from geTicketById method ****");
		return ticket;
	}

	public Ticket getTicketWithoutStatus(UserConfig userConfig, String code) {
		logger.debug("**** Enter in to getTicketWithoutStatus method ****");
		Ticket ticket = null;
		Criteria criteria = getSession().createCriteria(Ticket.class);
		criteria.add(Restrictions.eq("ticketNumber", code));
		criteria.setFetchMode("reminderMap", FetchMode.JOIN);
		criteria.add(Restrictions.eq("companyId", userConfig.getCompanyId()));
		criteria.add(Restrictions.eq("branchId", userConfig.getBranchId()));

		ticket = (Ticket)criteria.uniqueResult();

		if(ticket == null){
			throw new CommonDataAccessException("errors.recordnotfound");
		}
		logger.debug("**** Leaving from getTicketWithoutStatus method ****");
		return ticket;
	}

	public AuthorizeTicketDTO getAuthorizeTicketData(UserConfig userConfig,int ticketId){
		logger.debug("**** Enter in to getAuthorizeTicketData method ****");

		Ticket ticket = (Ticket)getSession().createCriteria(Ticket.class)
											.add(Restrictions.eq("ticketId",ticketId))
											.setFetchMode("pawner", FetchMode.JOIN)
											.setFetchMode("dueFromCollection", FetchMode.JOIN)
											.uniqueResult();

		if(ticket == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		Product product = (Product)getSession().get(Product.class, ticket.getProductId());
		Schemes schemes = (Schemes)getSession().get(Schemes.class, ticket.getSchemeId());
		List<InterestSlab> slabList = getSession().createCriteria(InterestSlab.class)
									  .add(Restrictions.eq("companyId" , userConfig.getCompanyId()))
									  .add(Restrictions.eq("branchId" , userConfig.getBranchId()))
									  .add(Restrictions.eq("recordStatus", RecordStatusEnum.ACTIVE.getCode()))
									  .add(Restrictions.eq("interestSlabCode", schemes.getInterestId()))
									  .list();


		AuthorizeTicketDTO ticketDTO = new AuthorizeTicketDTO();
		ticketDTO.setTicketId(ticket.getTicketId());
		ticketDTO.setPawnerCode(ticket.getPawner().getPawnerCode());
		ticketDTO.setPawnerName(ticket.getPawner().getPawnerName());
		ticketDTO.setPeriod(ticket.getPeriod());
		ticketDTO.setInterestId(ticket.getInterestSlabId());
		ticketDTO.setInterestCode(schemes.getInterestCode());
		ticketDTO.setNoOfArticle(ticket.getTotalNoOfItems());
		ticketDTO.setGoldValue(ticket.getSystemAssedValue());
		ticketDTO.setPawnAdvance(ticket.getPawnAdvance());
		ticketDTO.setMarketValue(ticket.getTotalMarketValue());
		ticketDTO.setActualDisValue(ticket.getTotalAssedValue());
		ticketDTO.setTotalNetWeight(ticket.getTotalNetWeight());
		ticketDTO.setTotGrossWeight(ticket.getTotalGrossWeight());
		ticketDTO.setRemark(ticket.getRemark());
		ticketDTO.setVersion(ticket.getVersion());
//		ticketDTO.setInterestSlab(((List<InterestSlab>)schemes.getInterestSlads()).get(0).getRate()/12);

		List<String> intSlabList = new ArrayList<String>();

		for (InterestSlab slab : slabList){
//			intSlabList.add(slab.getNoOfDaysFrom() + " - " + slab.getNoOfDaysTo() + " : " + slab.getRate()/12);
			intSlabList.add(" " + slab.getRate());
		}

//		ticketDTO.setInterestSlab(slabList.get(0).getRate()/12);
		ticketDTO.setInterestSlab(intSlabList);
		ticketDTO.setTicketArticleList(getSession().createCriteria(TicketArticle.class)
				 .add(Restrictions.eq("ticketId",ticket.getTicketId()))
				 .list());

		ticketDTO.setAddress(new StringBuilder().append(ticket.getPawner().getAddressLine1())
				  .append(" ").append(ticket.getPawner().getAddressLine2())
				  .append(" ").append(ticket.getPawner().getAddressLine3())
				  .append(" ").append(ticket.getPawner().getAddressLine4()).toString());

		ticketDTO.setProductCode(product.getCode());
		ticketDTO.setProductDescription(product.getDescription());
		ticketDTO.setSchemCode(schemes.getCode());
		ticketDTO.setSchemDescription(schemes.getDescription());
		
		//modified By Mahinda on 24th-May-2011
		ticketDTO.setTicketDate(ticket.getTicketDate());
		ticketDTO.setExpireDtae(ticket.getTicketExpiryDate());
		// End of Modification

		logger.debug("**** Leaving from getAuthorizeTicketData method ****");
		return ticketDTO;
	}

	public void authorizeTicket(UserConfig userConfig, Ticket ticket, boolean authorize){
		logger.debug("**** Enter in to authorizeTicket method ****");

		Ticket loadTicket = (Ticket)getSession().load(Ticket.class, ticket.getTicketId());
		Branch branch = (Branch)getHibernateTemplate().get(Branch.class,userConfig.getBranchId());
		double maxAdvance=0,totAdvance = 0;
		
		//Modified By Mahinda 14-10-2011
		//Get parameter value for Maximum Advance Amount
		//Modified for Gold Loan
		List<ParameterValue> paraMaxAdvList = null;
		try {
			if (loadTicket.getProductId() == 1) {
				Criteria paraMaxAdvCriteria = getSession().createCriteria(ParameterValue.class);
				paraMaxAdvCriteria.add(Restrictions.eq("parameterId", ParameterEnum.MAXPAWNADDFORCUSTOMER.getCode()));
				paraMaxAdvCriteria.add(Restrictions.le("effDate", userConfig.getLoginDate()));
				paraMaxAdvCriteria.add(Restrictions.eq("productId", loadTicket.getProductId()));
				paraMaxAdvCriteria.addOrder(Order.desc("effDate"));
				paraMaxAdvList = paraMaxAdvCriteria.list();
			}else {
				Criteria paraMaxAdvCriteria = getSession().createCriteria(ParameterValue.class);
				paraMaxAdvCriteria.add(Restrictions.eq("parameterId", ParameterEnum.MAXPAWNADDFORCUSTOMER1.getCode()));
				paraMaxAdvCriteria.add(Restrictions.le("effDate", userConfig.getLoginDate()));
				paraMaxAdvCriteria.addOrder(Order.desc("effDate"));
				paraMaxAdvList = paraMaxAdvCriteria.list();
			}
			
		} catch (Exception pex) {
			throw new CommonDataAccessException("errors.maxadvancenotdefined");
		}
		if(paraMaxAdvList.size() == 0){
			throw new CommonDataAccessException("errors.maxadvancenotdefined");
		}
		
		maxAdvance= Integer.parseInt(paraMaxAdvList.get(0).getParaValue());
		
		List<List<Object>> list = this.getClientExposure(userConfig, loadTicket.getPawner().getPawnerId());
		for (Iterator<List<Object>> iterator = list.iterator(); iterator.hasNext();) {
			List<Object> row = iterator.next();
			totAdvance += Double.parseDouble(row.get(4).toString());						
		}
		if (maxAdvance < totAdvance + loadTicket.getPawnAdvance()) {
			UserGroup userGroup = (UserGroup) getSession().load(UserGroup.class, userConfig.getUserGroupId());
			if (!userGroup.isAdmin()) {
//				throw new CommonDataAccessException("errors.notauthorize");
			}
		}
		
		//End of Modification
		if(authorize){

			if(branch.getFundAvailable()<loadTicket.getPawnAdvance()){
				throw new CommonDataAccessException("errors.noenoughfunds");
			}

			Calendar expCal = Calendar.getInstance();
			expCal.setTime(loadTicket.getTicketDate());
			expCal.add(Calendar.MONTH, loadTicket.getPeriod());
			loadTicket.setTicketStatusId(TicketStatusEnum.ACTIVE.getCode());
			loadTicket.setTicketExpiryDate(expCal.getTime());
			loadTicket.setApprovedBy(userConfig.getUserId());
			branch.setFundAvailable(branch.getFundAvailable()-loadTicket.getPawnAdvance());

			Criteria dueCriteria = getSession().createCriteria(DueType.class);
			dueCriteria.add(Restrictions.eq("dueType", "INT"));
			dueCriteria.setLockMode(LockMode.READ);
			DueType dueType = (DueType)dueCriteria.uniqueResult();

			Criteria dueFromCrt = getSession().createCriteria(DueFrom.class);
			dueFromCrt.add(Restrictions.eq("ticketId", loadTicket.getTicketId()));
			dueFromCrt.add(Restrictions.eq("dueTypeId", dueType.getDueTypeId()));
			DueFrom dueFrom = (DueFrom)dueFromCrt.uniqueResult();

			if(dueFrom!=null){
				Ledger ledger = new Ledger();
				ledger.setBranchId(userConfig.getBranchId());
				ledger.setCompanyId(userConfig.getCompanyId());
				ledger.setCreditAmount(0);
				ledger.setDebitAmount(dueFrom.getDueAmount());
				ledger.setDueType(dueType);
				ledger.setLastUpdateDate(userConfig.getLoginDate());
				ledger.setUserId(userConfig.getUserId());
				initializeDomainObject(userConfig, ledger);
				getHibernateTemplate().save(ledger);
			}
			getHibernateTemplate().update(branch);
		}else{
			loadTicket.setTicketStatusId(TicketStatusEnum.REJECTED.getCode());
			loadTicket.setApprovedBy(userConfig.getUserId());
		}

		getHibernateTemplate().update(loadTicket);

		logger.debug("**** Leaving from authorizeTicket method ****");
	}

	public static double round(double d, int decimalPlace){
	    // see the Javadoc about why we use a String in the constructor
	    // http://java.sun.com/j2se/1.5.0/docs/api/java/math/BigDecimal.html#BigDecimal(double)
	    BigDecimal bd = new BigDecimal(Double.toString(d));
	    bd = bd.setScale(decimalPlace,BigDecimal.ROUND_HALF_UP);
	    return bd.doubleValue();
	}

	/* (non-Javadoc)
	 * @see com.m4.pawning.dao.TicketDAO#getClientExposure(com.m4.core.util.UserConfig, int)
	 * 
	 */
	public List getClientExposure(UserConfig userConfig, int pawnId) {
		logger.debug("**** Enter in to getClientExposure method ****");
		
		// Get pawner details
		Criteria pawnerCriteria = getSession().createCriteria(Pawner.class);
		pawnerCriteria.add(Restrictions.eq("pawnerId", pawnId));
		List<Pawner> pawnerList = pawnerCriteria.list();

		if(pawnerList.size() <= 0)
			return null;
		// Get Ticket Details
		Criteria ticketCriteria = getSession().createCriteria(Ticket.class);
		ticketCriteria.add(Restrictions.eq("pawner", pawnerList.get(0)));
		ticketCriteria.add(Restrictions.ne("ticketStatusId",5));
		ticketCriteria.add(Restrictions.ne("ticketStatusId",3));
		List<Ticket> ticketList = ticketCriteria.list();
		
		HashMap<String,List<Double>>clientExposure = new HashMap<String,List<Double>>();
		Criteria dueFromCriteria;
		String bracnhId;
		
		// Get dueForm details
		// TODO: better way of doing this is by native sql query as above comment block
		if (ticketList.size() <= 0)
			return null;
		for (Iterator<Ticket> iterator = ticketList.iterator(); iterator.hasNext();) {
			Ticket ticket =  iterator.next();
			dueFromCriteria = getSession().createCriteria(DueFrom.class);
			dueFromCriteria.add(Restrictions.eq("ticketId", ticket.getRecordId()));
			dueFromCriteria.add(Restrictions.eq("dueTypeId", 1));
			List<DueFrom> dueFromList = dueFromCriteria.list();
			
			// Calculate sum amounts
			for (Iterator<DueFrom> iterator2 = dueFromList.iterator(); iterator2.hasNext();) {
				DueFrom dueFrom = iterator2.next();
				
				List<Double> clientExposured = new ArrayList<Double>();
				bracnhId = ""+ticket.getBranchId();
				
				// Grouping of sums of entries
				if(clientExposure.containsKey(bracnhId)){
					List<Double> tempClientExposured = (clientExposure.get(bracnhId));
					
					clientExposured.add(Double.valueOf(bracnhId));	
					clientExposured.add(Double.valueOf(1) + tempClientExposured.get(1));
					clientExposured.add(Double.valueOf(ticket.getPawnAdvance()) + tempClientExposured.get(2));
					clientExposured.add(Double.valueOf(dueFrom.getDueAmount()) + tempClientExposured.get(3));
					clientExposured.add(Double.valueOf(dueFrom.getBalanceAmount()) + tempClientExposured.get(4));
					clientExposured.add(Double.valueOf(dueFrom.getPaidAmount()) + tempClientExposured.get(5));

					clientExposure.put(bracnhId, clientExposured);
				//If first entry just add the entry to list
				}else{
					
					clientExposured.add(Double.valueOf(bracnhId));
					clientExposured.add(Double.valueOf(1));
					clientExposured.add(Double.valueOf(ticket.getPawnAdvance()));
					clientExposured.add(Double.valueOf(dueFrom.getDueAmount()));
					clientExposured.add(Double.valueOf(dueFrom.getBalanceAmount()));
					clientExposured.add(Double.valueOf(dueFrom.getPaidAmount()));
					
					clientExposure.put(bracnhId,clientExposured);
				}
			}
			
		}
		
		//Flattern the map to list before sending to JasonArray
		List<List<Object>> expose = new ArrayList<List<Object>> ();
	
		for (Iterator<List<Double>> iterator = clientExposure.values().iterator(); iterator.hasNext();) {
			List<Double> clientExposured = iterator.next();

			// Get Branch Name Details
			Criteria branchCriteria = getSession().createCriteria(Branch.class);
			branchCriteria.add(Restrictions.eq("branchId", clientExposured.get(0).intValue()));
			List<Branch> branchList = branchCriteria.list();

			List<Object>tempList = new ArrayList<Object>();
			tempList.add(branchList.get(0).getBarnchName());
			
			for (int i = 1; i < clientExposured.size(); i++) {
				tempList.add(clientExposured.get(i));
			}
			
			expose.add(tempList);
			
		}
		
		logger.debug("**** Leaving from getClientExposure method ****");

		return expose;
	}
	public Ticket getBulkTicketById(UserConfig userConfig,int ticketId){
		logger.debug("**** Enter in to geTicketById method ****");
		Ticket ticket = null;
		Criteria criteria=getSession().createCriteria(Ticket.class);
		criteria.add(Restrictions.eq("ticketId", ticketId));
		criteria.setFetchMode("dueFromCollection", FetchMode.JOIN);
		criteria.setFetchMode("ticketArticleSet", FetchMode.JOIN);
		ticket = (Ticket)criteria.uniqueResult();

		if(ticket == null){
			throw new CommonDataAccessException("errors.recordnotfound");
		}
		logger.debug("**** Leaving from geTicketById method ****");
		return ticket;
	}
}

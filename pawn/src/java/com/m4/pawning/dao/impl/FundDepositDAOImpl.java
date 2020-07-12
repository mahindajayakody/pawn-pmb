package com.m4.pawning.dao.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.m4.core.exception.CommonDataAccessException;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.RecordStatusEnum;
import com.m4.core.util.TransactionDAOSupport;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.FundDepositDAO;
import com.m4.pawning.domain.Branch;
import com.m4.pawning.domain.DueType;
import com.m4.pawning.domain.FundDeposit;
import com.m4.pawning.domain.Ledger;
import com.m4.pawning.domain.Product;

public class FundDepositDAOImpl extends TransactionDAOSupport   implements	FundDepositDAO {
	private static final Logger logger= Logger.getLogger(FundDepositDAOImpl.class);
	private static final SimpleDateFormat simpdate = new SimpleDateFormat("yyyy-MM-dd");

	public void approveFundDeposit(UserConfig userConfig,FundDeposit fundDeposit) {
		logger.debug("***** Enter In to authorizeFundDeposit ****");
		//Load Due Types
		List<DueType> dueTypeList = null;
		Map<String, DueType> dueTypeMap  = null;
		int productID=0;
		DueType dueType = null;
		Criteria dueTypeCriteria = getSession().createCriteria(DueType.class);
		dueTypeCriteria.add(Restrictions.eq("recordStatus", RecordStatusEnum.ACTIVE.getCode()));
		dueTypeCriteria.add(Restrictions.eq("dueType","FDP" ));
		dueType = (DueType)dueTypeCriteria.uniqueResult();

		if (dueType == null)
            throw new CommonDataAccessException("errors.funddepositdutypenotdefined");

		Branch branch = (Branch)getHibernateTemplate().get(Branch.class, fundDeposit.getDepositeBranchId());
		fundDeposit.setApprovedBy(userConfig.getUserId());
		fundDeposit.setApprovedDate(userConfig.getLoginDate());
		initializeDomainObject(userConfig, fundDeposit);
		fundDeposit.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());

		branch.setFundAvailable(branch.getFundAvailable() - fundDeposit.getDepositAmount());
		getHibernateTemplate().update(branch);
		getHibernateTemplate().update(fundDeposit);

		//Get Product
		List<Product> productList = getSession().createCriteria(Product.class)
		   							.add(Restrictions.eq("companyId",userConfig.getCompanyId())).list();

		for (Product product : productList) {
			productID = product.getProductId();
		}

		//Ledger Details
		Ledger ledgerInt = new Ledger();
		ledgerInt.setDebitAmount(fundDeposit.getDepositAmount());
		ledgerInt.setDueType(dueType);
		ledgerInt.setProductId(productID);
		ledgerInt.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
		initializeDomainObject(userConfig, ledgerInt);
		getHibernateTemplate().save(ledgerInt);

		logger.debug("**** Leaving authorizeFundDeposit *****");
	}

	public void createFundDeposit(UserConfig userConfig, FundDeposit fundDeposit) {
		logger.debug("**** Enter In to createFundDeposit ****");

		DecimalFormat decimalFormat = new DecimalFormat("0000000");
		String serialNo = decimalFormat.format(getSerialValue(userConfig, "FDP", 1));
		StringBuilder depositNo = new StringBuilder();
		depositNo.append(((Branch) getSession().get(Branch.class, userConfig.getBranchId())).getCode());
		Calendar calander = Calendar.getInstance();
		calander.setTime(userConfig.getLoginDate());
		depositNo.append(Integer.toString(calander.get(Calendar.YEAR)).substring(2));
		depositNo.append(serialNo);

		if ( fundDeposit.getDepositAmount()<=0)
            throw new CommonDataAccessException("errors.noenoughfundstodeposit");



		String SQL_QUERY = "SELECT count(depositNo) FROM FundDeposit WHERE depositDate>=:dipositDate AND branchId=:branchId";
		Query query = getSession().createQuery(SQL_QUERY);
		query.setString("dipositDate", simpdate.format(userConfig.getLoginDate()));
		query.setInteger("branchId", userConfig.getBranchId());

		List list 		 = query.list() ;
		Object[] objects = list.toArray();
		if(Integer.parseInt(objects[0].toString()) > 0){
			 throw new CommonDataAccessException("errors.onlyonedepositcanadd");
		}

		fundDeposit.setDepositNo(depositNo.toString());
		initializeDomainObject(userConfig, fundDeposit);
		fundDeposit.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
		fundDeposit.setDepositeBranchId(userConfig.getBranchId());
		getHibernateTemplate().save(fundDeposit);

		logger.debug("***** Leaving createFundDeposit ****");
	}

	public DataBag getAllFundDeposit(UserConfig userConfig, List<QueryCriteria> queryCriteria) {
		logger.debug("**** Inter in to getAllFundDeposit ****");
		Criteria criteria = getSession().createCriteria(FundDeposit.class);
		DataBag fundDepositDataBag = getDataList(getHibernateTemplate(), queryCriteria, criteria);
		logger.debug("**** Leaving getAllFundDeposit ****");
		return fundDepositDataBag;
	}

	public FundDeposit getFundDepositById(UserConfig userConfig, int recordId) {
		logger.debug("**** Inter in to getFundDepositById ****");
		FundDeposit fundDeposit=null;
		fundDeposit= (FundDeposit)getHibernateTemplate().get(FundDeposit.class, Integer.valueOf(recordId));

		if (fundDeposit==null)
			throw new CommonDataAccessException("errors.recordnotfound");
		logger.debug("**** Leaving getFundDepositById ****");
		return fundDeposit;
	}

	public FundDeposit getFundDepositByDepositNo(UserConfig userConfig, String depositNo) {
		logger.debug("**** Inter in to getFundRequestByRequestNo ****");
		FundDeposit fundDeposit=null;
		Criteria criteria =getSession().createCriteria(FundDeposit.class);
		criteria.add(Restrictions.eq("requestNo", depositNo));
		fundDeposit=(FundDeposit) criteria.uniqueResult();

		if (fundDeposit==null)
			throw new CommonDataAccessException("errors.recordnotfound");
		logger.debug("**** Inter in to getFundRequestByRequestNo ****");
		return fundDeposit;
	}

	public List<Object[]> getTiketsForTheDay(UserConfig userConfig) {
		logger.debug("**** Enter in to getTiketsForTheDay method ****");
		DataBag dataBag = null;

		String SQL_QUERY = "SELECT COUNT(ticketNumber) ,SUM(pawnAdvance) FROM Ticket WHERE ticketStatusId=1 " +
		  		"AND companyId=:companyId AND branchId=:branchId  AND ticketDate>=:ticketDate";

		Query query = getSession().createQuery(SQL_QUERY);
		query.setInteger("companyId", userConfig.getCompanyId());
		query.setInteger("branchId", userConfig.getBranchId());
		query.setString("ticketDate", simpdate.format(userConfig.getLoginDate()));

		logger.debug("**** Leaving from getTiketsForTheDay method ****"  );
		return query.list();
	}

	public List<Object[]> getReceiptsForTheDay(UserConfig userConfig) {
		logger.debug("**** Enter in to getReceiptsForTheDay method ****");
		DataBag dataBag = null;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(userConfig.getLoginDate());

		String SQL_QUERY = "SELECT COUNT(receiptNo) ,SUM(receiptAmt) FROM Receipt WHERE status=1 " +
		  		"AND companyId=:companyId AND branchId=:branchId AND receiptDate>=:receiptDate AND receiptType = 1";

		Query query = getSession().createQuery(SQL_QUERY);
		query.setInteger("companyId", userConfig.getCompanyId());
		query.setInteger("branchId", userConfig.getBranchId());
		query.setString("receiptDate", simpdate.format(userConfig.getLoginDate()));

		logger.debug("**** Leaving from getReceiptsForTheDay method ****");
		return query.list();
	}

	public List<Object[]> getTiketAllForTheDay(UserConfig userConfig) {
		logger.debug("**** Enter in to getTiketAllForTheDay method ****");
		DataBag dataBag = null;
		//t.ticketStatusId=1 AND
		String SQL_QUERY = "SELECT t.ticketDate,t.ticketNumber ,CONCAT(t.pawner.title,' ',t.pawner.initials,' ',t.pawner.surName ) ,"
			 				+ " t.remark,t.pawnAdvance FROM Ticket AS t WHERE  t.companyId=:companyId AND "
			 				+ " t.branchId=:branchId  AND t.ticketDate>=:ticketDate";

		Query query = getSession().createQuery(SQL_QUERY);
		query.setInteger("companyId", userConfig.getCompanyId());
		query.setInteger("branchId", userConfig.getBranchId());
		query.setString("ticketDate", simpdate.format(userConfig.getLoginDate()));

		logger.debug("**** Leaving from getTiketAllForTheDay method ****"  );
		return query.list();
	}

	public List<Object[]> getReceiptAllForTheDay(UserConfig userConfig) {
		logger.debug("**** Enter in to getTiketAllForTheDay method ****");
		DataBag dataBag = null;

		String SQL_QUERY = "SELECT r.receiptDate,r.receiptNo ,CONCAT(p.title,' ',p.initials,' ',p.surName ) ," +
		 		" r.description,r.receiptAmt FROM Receipt AS r,Pawner AS p " +
		 		" WHERE r.status=1 AND r.receiptType=1 AND r.companyId=:companyId AND r.branchId=:branchId AND r.receiptDate>=:receiptDate AND p.pawnerId=r.pawnerId";
		Query query = getSession().createQuery(SQL_QUERY);
		query.setInteger("companyId", userConfig.getCompanyId());
		query.setInteger("branchId", userConfig.getBranchId());
		query.setString("receiptDate", simpdate.format(userConfig.getLoginDate()));

		logger.debug("**** Leaving from getTiketAllForTheDay method ****"  );
		return query.list();
	}

	public List<Object[]> getFundRequestAllForTheDay(UserConfig userConfig) {
		logger.debug("**** Enter in to getTiketAllForTheDay method ****");
		DataBag dataBag = null;

		String SQL_QUERY = "SELECT f.requestDate,f.requestAmount FROM FundRequest AS f " +
		 		" WHERE f.approvedBy<>0 AND f.companyId=:companyId AND f.requestedBranchId=:branchId  AND f.requestDate>=:requestDate";

		Query query = getSession().createQuery(SQL_QUERY);
		query.setInteger("companyId", userConfig.getCompanyId());
		query.setInteger("branchId", userConfig.getBranchId());
		query.setString("requestDate", simpdate.format(userConfig.getLoginDate()));

		logger.debug("**** Leaving from getTiketAllForTheDay method ****"  );
		return query.list();
	}

	public List<Object[]> getFundDepositAllForTheDay(UserConfig userConfig) {
		logger.debug("**** Enter in to getTiketAllForTheDay method ****");
		DataBag dataBag = null;

		String SQL_QUERY = "SELECT f.depositDate,f.depositAmount FROM FundDeposit AS f " +
		 		" WHERE f.approvedBy<>0 AND f.companyId=:companyId AND f.depositeBranchId=:branchId  AND f.depositDate>=:depositDate";

		Query query = getSession().createQuery(SQL_QUERY);
		query.setInteger("companyId", userConfig.getCompanyId());
		query.setInteger("branchId", userConfig.getBranchId());
		query.setString("depositDate", simpdate.format(userConfig.getLoginDate()));

		logger.debug("**** Leaving from getTiketAllForTheDay method ****"  );
		return query.list();
	}
}

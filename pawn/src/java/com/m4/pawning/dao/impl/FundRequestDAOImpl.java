package com.m4.pawning.dao.impl;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.m4.core.exception.CommonDataAccessException;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.RecordStatusEnum;
import com.m4.core.util.TransactionDAOSupport;
import com.m4.core.util.UserConfig;
import com.m4.core.util.QueryCriteria.Oparator;
import com.m4.pawning.dao.BranchDAO;
import com.m4.pawning.dao.FundRequestDAO;
import com.m4.pawning.domain.Branch;
import com.m4.pawning.domain.DueType;
import com.m4.pawning.domain.FundRequest;
import com.m4.pawning.domain.Ledger;
import com.m4.pawning.domain.Product;

//
public class FundRequestDAOImpl extends TransactionDAOSupport   implements	FundRequestDAO {
	private static final Logger logger= Logger.getLogger(FundRequestDAOImpl.class);

	public void approveFundRequest(UserConfig userConfig,FundRequest fundRequest) {
		logger.debug("***** Enter In to approveFundRequest ****");

		//Load Due Types
		List<DueType> dueTypeList = null;
		Map<String, DueType> dueTypeMap  = null;
		int productID=0;
		DueType dueType = null;
		Criteria dueTypeCriteria = getSession().createCriteria(DueType.class);
		dueTypeCriteria.add(Restrictions.eq("recordStatus", RecordStatusEnum.ACTIVE.getCode()));
		dueTypeCriteria.add(Restrictions.eq("dueType","FNR" ));
		dueType = (DueType)dueTypeCriteria.uniqueResult();

		if (dueType == null)
            throw new CommonDataAccessException("errors.funddepositdutypenotdefined");

		Branch branch = (Branch)getHibernateTemplate().get(Branch.class, fundRequest.getRequestedBranchId());
		fundRequest.setApprovedBy(userConfig.getUserId());
		fundRequest.setApprovedDate(userConfig.getLoginDate());
		initializeDomainObject(userConfig, fundRequest);
		fundRequest.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());


		if ( branch.getFundLimit() < (branch.getFundAvailable() + fundRequest.getRequestAmount()))
            throw new CommonDataAccessException("errors.fundlimitexceed");

		branch.setFundAvailable(branch.getFundAvailable() + fundRequest.getRequestAmount());

		getHibernateTemplate().update(branch);
		getHibernateTemplate().update(fundRequest);


		//Get Product

		List<Product> productList = getSession().createCriteria(Product.class)
		   							.add(Restrictions.eq("companyId",userConfig.getCompanyId())).list();

		for (Product product : productList) {
			productID=product.getProductId();
		}

		//Ledger Details
		Ledger ledgerInt = new Ledger();
		ledgerInt.setCreditAmount( fundRequest.getRequestAmount());
		ledgerInt.setDueType(dueType);
		ledgerInt.setProductId(productID);
		ledgerInt.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
		initializeDomainObject(userConfig, ledgerInt);

		getHibernateTemplate().save(ledgerInt);

		logger.debug("**** Leaving approveFundRequest *****");

	}

	public void createFundRequest(UserConfig userConfig, FundRequest fundRequest) {

		logger.debug("**** Enter In to createFundRequest ****");
		Branch branch = new Branch();
		branch = (Branch)getHibernateTemplate().get(Branch.class, userConfig.getBranchId());

		DecimalFormat decimalFormat=new DecimalFormat("0000000");
		String serialNo =decimalFormat.format(getSerialValue(userConfig, "FNR", 1));
		StringBuilder requestNo= new StringBuilder();
		requestNo.append(((Branch) getSession().get(Branch.class, userConfig.getBranchId())).getCode());
		Calendar calander=Calendar.getInstance();
		calander.setTime(userConfig.getLoginDate());
		requestNo.append(Integer.toString(calander.get(Calendar.YEAR)).substring(2));
		requestNo.append(serialNo);

		if ( branch.getFundLimit()< branch.getFundAvailable() + fundRequest.getRequestAmount())
            throw new CommonDataAccessException("errors.fundlimitexceed");


		fundRequest.setRequestNo(requestNo.toString());
		fundRequest.setRequestedBranchId(userConfig.getBranchId());
		initializeDomainObject(userConfig, fundRequest);
		fundRequest.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
		getHibernateTemplate().save(fundRequest);

		logger.debug("***** Leaving createFundRequest ****");
	}

	public DataBag getAllFundRequest(UserConfig userConfig,	List<QueryCriteria> queryCriteria) {
		logger.debug("**** Inter in to getAllFundRequest ****");
		Criteria criteria = getSession().createCriteria(FundRequest.class);
		queryCriteria.add(new QueryCriteria("companyId", Oparator.EQUAL, userConfig.getCompanyId()));
		queryCriteria.add(new QueryCriteria("branchId", Oparator.EQUAL, userConfig.getBranchId()));
		DataBag fundRequestDataBag = getDataList(getHibernateTemplate(), queryCriteria, criteria);
		logger.debug("**** Leaving getAllFundRequest ****");
		return fundRequestDataBag;
	}

	public FundRequest getFundRequestById(UserConfig userConfig, int recordId) {
		logger.debug("**** Inter in to getFundRequestById ****");
		FundRequest fundRequest=null;
		fundRequest= (FundRequest)getHibernateTemplate().get(FundRequest.class, Integer.valueOf(recordId));

		if (fundRequest==null)
			throw new CommonDataAccessException("errors.recordnotfound");
		logger.debug("**** Leaving getFundRequestById ****");
		return fundRequest;
	}

	public FundRequest getFundRequestByRequestNo(UserConfig userConfig,
			String requestNo) {
		logger.debug("**** Inter in to getFundRequestByRequestNo ****");
		FundRequest fundRequest=null;
		Criteria criteria =getSession().createCriteria(FundRequest.class);
		criteria.add(Restrictions.eq("requestNo", requestNo));
		fundRequest=(FundRequest) criteria.uniqueResult();

		if (fundRequest==null)
			throw new CommonDataAccessException("errors.recordnotfound");
		logger.debug("**** Inter in to getFundRequestByRequestNo ****");
		return fundRequest;
	}

	public void updateFundRequest(UserConfig userConfig, FundRequest fundRequest) {
		logger.debug("**** Enter in to updateFundRequest method ****");

		Branch branch = new Branch();
		branch = (Branch)getHibernateTemplate().get(Branch.class, userConfig.getBranchId());

		if ( branch.getFundLimit()< branch.getFundAvailable() + fundRequest.getRequestAmount())
            throw new CommonDataAccessException("errors.fundlimitexceed");


		initializeDomainObject(userConfig, fundRequest);
		fundRequest.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
		getHibernateTemplate().update(fundRequest);
		logger.debug("**** Leaving from updateFundRequest method ****");
	}

}

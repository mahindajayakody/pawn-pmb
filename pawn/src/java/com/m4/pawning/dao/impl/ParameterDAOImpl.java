package com.m4.pawning.dao.impl;

import java.util.ArrayList;
import java.util.List;

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
import com.m4.core.util.QueryCriteria.Oparator;
import com.m4.pawning.dao.ParameterDAO;
import com.m4.pawning.domain.Branch;
import com.m4.pawning.domain.Parameter;

public class ParameterDAOImpl extends MasterDAOSupport implements ParameterDAO {
	private static final Logger logger = Logger.getLogger(ParameterDAOImpl.class);
	
	public void createParameter(UserConfig userConfig, Parameter parameter) {
		logger.debug("**** Enter in to createParameter method ****");
		Criteria criteria = getSession().createCriteria(Parameter.class);
		criteria.add(Restrictions.eq("code", parameter.getCode()));
		criteria.add(Restrictions.eq("companyId", userConfig.getCompanyId()));
		criteria.add(Restrictions.eq("productId", parameter.getProductId()));
		criteria.setProjection(Projections.rowCount());
		
		
		int count = ((Integer) criteria.uniqueResult()).intValue();
        if (count !=0)
            throw new CommonDataAccessException("errors.recordexist");
		
        Branch branch = (Branch)getHibernateTemplate().get(Branch.class, userConfig.getBranchId());
        if(branch.getIsDefault()==1){
        	//getSession().evict(branch);
        	String branchHQL = "SELECT branchId FROM Branch";// WHERE companyId=:companyId";
        	Query branchQuery = getSession().createQuery(branchHQL);
        	//branchQuery.setInteger("companyId", userConfig.getCompanyId());
        	List<Integer> ids = (List<Integer>)branchQuery.list();
        	
        	List<Parameter> paraList = new ArrayList<Parameter>();
        	for(int branchId : ids){
        		Parameter para = new Parameter();
        		initializeDomainObject(userConfig, para);
        		para.setBranchId(branchId);
        		para.setProductId(parameter.getProductId());
        		para.setCode(parameter.getCode());
        		para.setDescription(parameter.getDescription());
        		para.setIsActive(parameter.getIsActive());
        		para.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
        		para.setDataType(parameter.getDataType());        		        		
        		getHibernateTemplate().save(para);
        	}
        }else{
        	parameter.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
        	initializeDomainObject(userConfig, parameter);
        	getHibernateTemplate().save(parameter);
        }
        
		logger.debug("**** Leaving from createParameter method ****");
	}

	public DataBag getAllParameter(UserConfig userConfig,List<QueryCriteria> criteriaList) {
		logger.debug("**** Enter in to getAllParameter method ****");
		DataBag parameterBag = null;		
		Criteria criteria = getSession().createCriteria(Parameter.class);
		criteriaList.add(new QueryCriteria("companyId",Oparator.EQUAL,userConfig.getCompanyId()));
		//criteriaList.add(new QueryCriteria("branchId",Oparator.EQUAL,userConfig.getBranchId()));
		parameterBag = getDataList(getHibernateTemplate(), criteriaList,criteria );
		logger.debug("**** Leaving from getAllParameter method ****");
		return parameterBag;
	}

	public Parameter getParameterByCode(UserConfig userConfig, String code) {
		logger.debug("**** Enter in to getParameterByCode method ****");
		Parameter parameter = null;
		Criteria criteria = getSession().createCriteria(Parameter.class);
		//criteria.add(Restrictions.eq("branchId", userConfig.getBranchId()));
		criteria.add(Restrictions.eq("companyId", userConfig.getCompanyId()));
		parameter.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
		criteria.add(Restrictions.eq("code", code));
		parameter = (Parameter)criteria.uniqueResult();
		
		if(parameter == null)
			throw new CommonDataAccessException("errors.recordnotfound");
		
		logger.debug("**** Leaving from getParameterByCode method ****");
		return parameter;
	}

	public Parameter getParameterById(UserConfig userConfig, int recordId) {
		logger.debug("**** Enter in to getParameterById method ****");
		Parameter parameter = null;
		parameter = (Parameter)getHibernateTemplate().get(Parameter.class, Integer.valueOf(recordId));
		
		if(parameter == null)
			throw new CommonDataAccessException("errors.recordnotfound");
		
		logger.debug("**** Leaving from getParameterById method ****");
		return parameter;
	}

	public void removeParameter(UserConfig userConfig, Parameter parameter) {
		logger.debug("**** Enter in to removeParameter method ****");
		Parameter para = (Parameter)getHibernateTemplate().get(Parameter.class, Integer.valueOf(parameter.getRecordId()));
		para.setVersion(parameter.getVersion());
		getHibernateTemplate().delete(para);
		logger.debug("**** Leaving from removeParameter method ****");
	}

	public void updateParameter(UserConfig userConfig, Parameter parameter) {
		logger.debug("**** Enter in to updateParameter method ****");
		initializeDomainObject(userConfig, parameter);
		parameter.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
		getHibernateTemplate().update(parameter);
		logger.debug("**** Leaving from updateParameter method ****");		
	}

}

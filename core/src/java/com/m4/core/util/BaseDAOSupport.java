package com.m4.core.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.m4.core.bean.Consolidate;
import com.m4.core.bean.Trace;

public abstract class BaseDAOSupport extends HibernateDaoSupport{
	private static Logger logger = Logger.getLogger(BaseDAOSupport.class);

	public DataBag getDataList(HibernateTemplate hibernateTemplate,List<QueryCriteria> criteriaList,Criteria criteria ){
		return getDataList(hibernateTemplate, criteriaList, criteria, 0, -1);
	}

    public DataBag getDataList(HibernateTemplate hibernateTemplate,List<QueryCriteria> criteriaList,Criteria criteria ,int start, int noOfRecords){
    	logger.info("Entered to get paged data");
    	DataBag dataBag = null;
    	if(criteriaList==null)criteriaList = new ArrayList<QueryCriteria>();

    	int count = 0;
    	for(QueryCriteria crt:criteriaList){
     		logger.info(crt.getFieldName() + " " + crt.getOparator() + " " + crt.getFieldValue());
     		int crtField = crt.getFieldName().split("\\.").length;
     		switch(crt.getOparator()){
				case EQUAL:{
					if(crt.isAssociation()==true){
						if(crtField==2)
							criteria.createCriteria(crt.getFieldName().split("\\.")[0]).add(Expression.eq(crt.getFieldName().split("\\.")[1],crt.getFieldValue()));
						else
							criteria.createCriteria(crt.getFieldName().split("\\.")[0]).createCriteria(crt.getFieldName().split("\\.")[1]).add(Expression.eq(crt.getFieldName().split("\\.")[2],crt.getFieldValue()));
					}else{
						criteria.add(Restrictions.eq(crt.getFieldName(), crt.getFieldValue()));
					}
					break;
				}
				case NOT_EQUAL:{
					if(crt.isAssociation()==true){
						if(crtField==2)
							criteria.createCriteria(crt.getFieldName().split("\\.")[0]).add(Expression.ne(crt.getFieldName().split("\\.")[1],crt.getFieldValue()));
						else
							criteria.createCriteria(crt.getFieldName().split("\\.")[0]).createCriteria(crt.getFieldName().split("\\.")[1]).add(Expression.ne(crt.getFieldName().split("\\.")[2],crt.getFieldValue()));

						//criteria.createCriteria(crt.getFieldName().split("\\.")[0]).add(Expression.ne(crt.getFieldName().split("\\.")[1],crt.getFieldValue()));
					}else{
						criteria.add(Restrictions.ne(crt.getFieldName(), crt.getFieldValue()));
					}
					break;
				}
				case GRATERTHAN:{
					if(crt.isAssociation()==true){
	   					if(crtField==2)
	   						criteria.createCriteria(crt.getFieldName().split("\\.")[0]).add(Expression.gt(crt.getFieldName().split("\\.")[1],crt.getFieldValue()));
						else
							criteria.createCriteria(crt.getFieldName().split("\\.")[0]).createCriteria(crt.getFieldName().split("\\.")[1]).add(Expression.gt(crt.getFieldName().split("\\.")[2],crt.getFieldValue()));

						//criteria.createCriteria(crt.getFieldName().split("\\.")[0]).add(Expression.gt(crt.getFieldName().split("\\.")[1],crt.getFieldValue()));
					}else{
						criteria.add(Restrictions.gt(crt.getFieldName(), crt.getFieldValue()));
					}
					break;
				}
				case LESSTHAN:{
					if(crt.isAssociation()==true){
						if(crtField==2)
	   						criteria.createCriteria(crt.getFieldName().split("\\.")[0]).add(Expression.lt(crt.getFieldName().split("\\.")[1],crt.getFieldValue()));
						else
							criteria.createCriteria(crt.getFieldName().split("\\.")[0]).createCriteria(crt.getFieldName().split("\\.")[1]).add(Expression.lt(crt.getFieldName().split("\\.")[2],crt.getFieldValue()));

						//criteria.createCriteria(crt.getFieldName().split("\\.")[0]).add(Expression.lt(crt.getFieldName().split("\\.")[1],crt.getFieldValue()));
					}else{
						criteria.add(Restrictions.lt(crt.getFieldName(), crt.getFieldValue()));
					}
					break;
				}
				case GRATERTHAN_OR_EQUAL:{
					if(crt.isAssociation()==true){
						if(crtField==2)
	   						criteria.createCriteria(crt.getFieldName().split("\\.")[0]).add(Expression.ge(crt.getFieldName().split("\\.")[1],crt.getFieldValue()));
						else
							criteria.createCriteria(crt.getFieldName().split("\\.")[0]).createCriteria(crt.getFieldName().split("\\.")[1]).add(Expression.ge(crt.getFieldName().split("\\.")[2],crt.getFieldValue()));

						//criteria.createCriteria(crt.getFieldName().split("\\.")[0]).add(Expression.ge(crt.getFieldName().split("\\.")[1],crt.getFieldValue()));
					}else{
						criteria.add(Restrictions.ge(crt.getFieldName(), crt.getFieldValue()));
					}
					break;
				}
				case LESSTHAN_OR_EQUAL:{
					if(crt.isAssociation()==true){
						if(crtField==2)
	   						criteria.createCriteria(crt.getFieldName().split("\\.")[0]).add(Expression.le(crt.getFieldName().split("\\.")[1],crt.getFieldValue()));
						else
							criteria.createCriteria(crt.getFieldName().split("\\.")[0]).createCriteria(crt.getFieldName().split("\\.")[1]).add(Expression.le(crt.getFieldName().split("\\.")[2],crt.getFieldValue()));

						//criteria.createCriteria(crt.getFieldName().split("\\.")[0]).add(Expression.le(crt.getFieldName().split("\\.")[1],crt.getFieldValue()));
					}else{
						criteria.add(Restrictions.le(crt.getFieldName(), crt.getFieldValue()));
					}
					break;
				}
				case LIKE:{
					if(crt.isAssociation()==true){
						if(crtField==2)
	   						criteria.createCriteria(crt.getFieldName().split("\\.")[0]).add(Expression.like(crt.getFieldName().split("\\.")[1],crt.getFieldValue()+"%"));
						else
							criteria.createCriteria(crt.getFieldName().split("\\.")[0]).createCriteria(crt.getFieldName().split("\\.")[1]).add(Expression.like(crt.getFieldName().split("\\.")[2],crt.getFieldValue()+"%"));

						//criteria.createCriteria(crt.getFieldName().split("\\.")[0]).add(Expression.like(crt.getFieldName().split("\\.")[1],crt.getFieldValue()+"%"));
					}else{
						criteria.add(Restrictions.like(crt.getFieldName(), crt.getFieldValue()+"%"));
					}
					break;
				}
     		}
		}

    	dataBag = new DataBag();

    	if (noOfRecords!= -1){
    		criteria.setFirstResult(start);
    		criteria.setMaxResults(noOfRecords);
    	}

        dataBag.setDataList(criteria.list());

        //Get total no of result
        criteria.setFirstResult(0);
    	criteria.setProjection(Projections.rowCount());

    	Object returnValue = criteria.uniqueResult();

    	if (returnValue !=null){
    		dataBag.setCount((Integer)returnValue);
    	}
        return dataBag;
    }

    public void initializeDomainObject(UserConfig userConfig,Trace trace){
        trace.setUserId(userConfig.getUserId());
        trace.setLastUpdateDate((userConfig).getLoginDate());
        trace.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
        trace.setTransactionId(userConfig.getUserLogId());
        
        if(trace instanceof Consolidate){
        	Consolidate consolidate = (Consolidate)trace;
        	consolidate.setCompanyId(userConfig.getCompanyId());
        	
        	if (!consolidate.getIsBranchExplycit()) {
        		consolidate.setBranchId(userConfig.getBranchId());	
			}        	
        }
    }
}

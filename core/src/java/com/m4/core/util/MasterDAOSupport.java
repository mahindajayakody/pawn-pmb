package com.m4.core.util;

import org.apache.log4j.Logger;
import com.m4.core.bean.Trace;
import com.m4.core.exception.CommonDataAccessException;



public abstract class MasterDAOSupport extends BaseDAOSupport{
	private static Logger logger = Logger.getLogger(MasterDAOSupport.class);

	public void create(UserConfig userConfig,Trace trace) {
        logger.debug("Start create in MasterDAOSupport >" + trace.getClass().getSimpleName());
        initializeDomainObject(userConfig,trace);
        trace.setOriginalRecordId(0);

        if (userConfig.getAuthorizeMode()==0){
            logger.debug("Single authorization mode.");
            trace.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
        }else{
            logger.debug("Dual authorization mode.");
            trace.setRecordStatus(RecordStatusEnum.CREATEPENDING.getCode());
        }
		getHibernateTemplate().save(trace);
		logger.debug(trace.getRecordId());
        logger.debug("Finish create in MasterDAOSupport > " + trace.getClass().getSimpleName());
	}

	public void update(UserConfig userConfig,Trace trace) {
        logger.debug("Start update in MasterDAOSupport > " + trace.getClass().getSimpleName());

        
        if (userConfig.getAuthorizeMode()==0){
            logger.debug("Single authorization mode.");
            //Update the status of the record as a 'active'
            trace.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());

            initializeDomainObject(userConfig,trace);

            //Synchronize with database
            logger.debug("Start update record.");
            getHibernateTemplate().update(trace);
            logger.debug("Finish update record.");

        }else{
            logger.debug("Dual authorization mode.");

            //Load persistent object from the database
            Trace originalObj = (Trace)getHibernateTemplate().get(trace.getClass(),Integer.valueOf(trace.getRecordId()));
            if (originalObj==null){
                logger.error("Record not found for update,Record Id " + trace.getRecordId());
                throw new CommonDataAccessException("errors.recordnotfound");
            }
            //Set version of the object as a original loaded version

            originalObj.setVersion(trace.getVersion());

            //Update the status of the record as a 'pending'
            originalObj.setRecordStatus(RecordStatusEnum.PENDING.getCode());

            //Synchronize with database
            logger.debug("Start flaging updated record as pending record");
            getHibernateTemplate().update(originalObj);
            logger.debug("Finish flaging updated record as pending record");

            //Update the status of the record as a 'pending updation' and original record id
            logger.debug("Start creating new record as pending updation record");
            trace.setRecordStatus(RecordStatusEnum.UPDATEPENDING.getCode());
            trace.setOriginalRecordId(originalObj.getRecordId());
            initializeDomainObject(userConfig,trace);
            //Synchronize with database
            getHibernateTemplate().save(trace);
            logger.debug("Finish update in MasterDAOSupport > " + trace.getClass().getSimpleName());
        }
	}

	public void remove(UserConfig userConfig,Trace trace) {
        logger.debug("Start remove in MasterDAOSupport > " + trace.getClass().getSimpleName());

        Integer recordId=Integer.valueOf(trace.getRecordId());
        Class clazz=trace.getClass();

        //Load persistent object from the database
        getHibernateTemplate().refresh(trace);
        getHibernateTemplate().evict(trace);
        Trace traceableObj = (Trace)getHibernateTemplate().get(clazz,recordId);

        if (traceableObj==null){
            logger.debug("Record not found for remove");
            throw new CommonDataAccessException("errors.recordnotfound");
        }

        if (userConfig.getAuthorizeMode()==0){
            logger.debug("Single authorization mode.");
            getHibernateTemplate().delete(traceableObj);
        }else{
            logger.debug("Dual authorization mode.");

    		//Set version of the object as a original loaded version
    		//Update the status of the record as a 'pending'
    		traceableObj.setVersion(trace.getVersion());
    		traceableObj.setRecordStatus(RecordStatusEnum.PENDING.getCode());
    		getHibernateTemplate().update(traceableObj);


            //Update the status of the record as a 'pending deletion' and original record id
            trace.setRecordStatus(RecordStatusEnum.DELETEPENDING.getCode());
            trace.setOriginalRecordId(traceableObj.getRecordId());
            initializeDomainObject(userConfig,trace);
    		getHibernateTemplate().save(trace);
        }
        logger.debug("Finish remove in MasterDAOSupport > " + trace.getClass().getSimpleName());
	}

    //Implementation of TraceableDAO


	public void authorizeCreation(UserConfig userConfig, Trace trace, boolean authorize) {
        logger.debug("Start authorizeCreation in MasterDAOSupport > " + trace.getClass().getSimpleName());

        if (authorize== true){
            logger.debug("Create authorization mode for entity " + trace.getRecordId());
    		Trace traceableObj = (Trace)getHibernateTemplate().get(trace.getClass(),Integer.valueOf(trace.getRecordId()));
            if (traceableObj==null){
                logger.debug("record not found.");
                throw new CommonDataAccessException("errors.recordnotfound");
            }
    		traceableObj.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
    		getHibernateTemplate().update(traceableObj);
        }else{
            logger.debug("Create reject mode.");
            getHibernateTemplate().delete(getHibernateTemplate().get(trace.getClass(), trace.getRecordId()));
        }
        logger.debug("Finish authorizeCreation in MasterDAOSupport > " + trace.getClass().getSimpleName());
	}

	public void authorizeUpdation(UserConfig userConfig, Trace trace, boolean authorize) {
        logger.debug("Start authorizeUpdation in MasterDAOSupport > " + trace.getClass().getSimpleName());

        //Load persistent current object(updated) from the database
        Trace currentObj = (Trace)getHibernateTemplate().get(trace.getClass(),Integer.valueOf(trace.getRecordId()));
        if (currentObj==null){
            logger.debug("Current record not found for authorize updation");
            throw new CommonDataAccessException("errors.recordnotfound");
        }

        //Load persistent original object(updated) from the database
        Trace originalObj = (Trace)getHibernateTemplate().get(trace.getClass(),Integer.valueOf(currentObj.getOriginalRecordId()));
        if (originalObj==null){
            logger.error("Original record not found for authorize updation");
            throw new CommonDataAccessException("errors.recordnotfound");
        }
        //logger.debug(originalObj.toString());

        if (authorize== true){
            logger.debug("Update authorization mode.");

            //Remove this instance from the session cache. Changes to the instance will not be synchronized with the database
            getHibernateTemplate().evict(currentObj);

    		//Prepare current object to merge with original object
            currentObj.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
    		currentObj.setRecordId(originalObj.getRecordId());
    		currentObj.setVersion(originalObj.getVersion());
    		currentObj.setOriginalRecordId(0);

    		//Synchronize with database(Copy current object values to original object without PK)
    		getHibernateTemplate().merge(currentObj);

    		//Load persistent original object from the database **Proxy**
    		Trace temp = (Trace)getHibernateTemplate().load(trace.getClass(),Integer.valueOf(trace.getRecordId()));
    		//Synchronize with database(delete)
    		getHibernateTemplate().delete(temp);
            logger.debug("Finish deletion of current object.");
        }else{
            logger.debug("Update reject mode.");
            originalObj.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
            getHibernateTemplate().update(originalObj);
            getHibernateTemplate().delete(currentObj);
        }
        logger.info("Finish authorizeUpdation in MasterDAOSupport > " + trace.getClass().getSimpleName());
	}

    public void authorizeDeletion(UserConfig userConfig, Trace trace, boolean authorize) {
        logger.debug("Start authorizeDeletion in MasterDAOSupport > " + trace.getClass().getSimpleName());

        Trace currentObj = (Trace)getHibernateTemplate().get(trace.getClass(),Integer.valueOf(trace.getRecordId()));
        if (currentObj==null){
            logger.error("Current record not found for authorize deletion");
            throw new CommonDataAccessException("errors.recordnotfound");
        }

        Trace originalObj = (Trace)getHibernateTemplate().get(trace.getClass(),Integer.valueOf(currentObj.getOriginalRecordId()));
        if (originalObj==null){
            logger.error("Original record not found for authorize deletion");
            throw new CommonDataAccessException("errors.recordnotfound");
        }

        if (authorize== true){
            logger.debug("Delete authorization mode.");
            getHibernateTemplate().delete(originalObj);
            getHibernateTemplate().delete(currentObj);

        }else{
            logger.debug("Delete reject mode.");
            originalObj.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
            logger.debug("Start updation of original object.");
            getHibernateTemplate().update(originalObj);
            logger.debug("Finish updation of original object.");
            logger.debug("Start deletion of current object.");
            getHibernateTemplate().delete(currentObj);
            logger.debug("Finish deletion of current object.");
        }
        logger.info("Finish authorizeDeletion in MasterDAOSupport > " + trace.getClass().getSimpleName());
    }
}

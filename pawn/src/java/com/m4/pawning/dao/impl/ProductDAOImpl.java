package com.m4.pawning.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.m4.core.exception.CommonDataAccessException;
import com.m4.core.util.DataBag;
import com.m4.core.util.MasterDAOSupport;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.RecordStatusEnum;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.ProductDAO;
import com.m4.pawning.domain.Product;

public class ProductDAOImpl extends MasterDAOSupport implements ProductDAO {
	private static final Logger logger = Logger.getLogger(ProductDAO.class);

	public void createProduct(UserConfig userConfig, Product product) {
		logger.debug("**** Enter in to createProduct ****");
		Criteria criteria = getSession().createCriteria(Product.class);
		criteria.add(Restrictions.eq("code", product.getCode()));
		criteria.setProjection(Projections.rowCount());

		int count = ((Integer) criteria.uniqueResult()).intValue();
        if (count !=0)
            throw new CommonDataAccessException("errors.recordexist");

//        initializeDomainObject(userConfig, product);
//        product.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
//        getHibernateTemplate().save(product);
        create(userConfig, product);
        logger.debug("**** Enter in to createProduct ****");
	}

	public void removeProduct(UserConfig userConfig, Product product) {
		logger.debug("**** Enter in to removeProduct method ****");		
//		Product prd = (Product)getHibernateTemplate().get(Product.class, Integer.valueOf(product.getRecordId()));
//		prd.setVersion(product.getVersion());
//		getHibernateTemplate().delete(prd);
		remove(userConfig, product);
		logger.debug("**** Leaving from removeProduct method ****");
	}

	public void updateProduct(UserConfig userConfig, Product product) {
		logger.debug("**** Enter in to updateProduct method ****");
//		initializeDomainObject(userConfig, product);
//		product.setRecordStatus(RecordStatusEnum.ACTIVE.getCode());
//		getHibernateTemplate().update(product);
		update(userConfig, product);
		logger.debug("**** Leaving from updateProduct method ****");
	}

	public DataBag getAllProduct(UserConfig userConfig,List<QueryCriteria> criteriaList) {
		logger.debug("**** Enter in to getAllProduct method ****");
		DataBag productBag = null;
		Criteria criteria = getSession().createCriteria(Product.class);
		productBag = getDataList(getHibernateTemplate(), criteriaList,criteria );
		logger.debug("**** Leaving from getAllProduct method ****");
		return productBag;
	}

	public Product getProductByCode(UserConfig userConfig, String code) {
		logger.debug("**** Enter in to getProductByCode method ****");
		Product product = null;
		Criteria criteria = getSession().createCriteria(Product.class);
		criteria.add(Restrictions.eq("code", code));
		product = (Product)criteria.uniqueResult();

		if(product == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from getProductByCode method ****");
		return product;
	}

	public Product getProductById(UserConfig userConfig, int recordId) {
		logger.debug("**** Enter in to getDueTypeById method ****");
		Product product = null;
		product = (Product)getHibernateTemplate().get(Product.class, Integer.valueOf(recordId));

		if(product == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from getDueTypeById method ****");
		return product;
	}

}

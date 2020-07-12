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
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.CartageDAO;
import com.m4.pawning.domain.Cartage;


public class CartageDAOImpl extends MasterDAOSupport implements CartageDAO {
	private static final Logger logger =Logger.getLogger(CartageDAOImpl.class);

	public void createCartage(UserConfig userConfig, Cartage cartage) {
		logger.debug("**** Enter in to createCartage ****");
		Criteria criteria = getSession().createCriteria(Cartage.class);
		criteria.add(Restrictions.eq("code", cartage.getCode()));
		criteria.add(Restrictions.eq("productId", cartage.getProductId()));
		criteria.add(Restrictions.eq("masterCartageId", cartage.getMasterCartageId()));
		criteria.setProjection(Projections.rowCount());

		int count = ((Integer) criteria.uniqueResult()).intValue();
		if (count!=0)
            throw new CommonDataAccessException("errors.recordexist");

//		initializeDomainObject(userConfig, cartage);
//		getHibernateTemplate().save(cartage);
		create(userConfig, cartage);
		logger.debug("**** Leaving createCartage ****");
	}

	public void removeCartage(UserConfig userConfig, Cartage cartage) {
		logger.debug("**** Enter in to getCartageById ****");
//		Cartage cart = (Cartage) getHibernateTemplate().get(Cartage.class, Integer.valueOf(cartage.getRecordId()));
//		cart.setVersion(cartage.getVersion());
//		getHibernateTemplate().delete(cart);
		remove(userConfig, cartage);
		logger.debug("**** Leaving removeCartage ****");
	}

	public void updateCartage(UserConfig userConfig, Cartage cartage) {
		logger.debug("**** Enter in to updateCartage ****");
//		initializeDomainObject(userConfig, cartage);
//		getHibernateTemplate().update(cartage);
		update(userConfig, cartage);
		logger.debug("**** Leaving updateCartage ****");
	}

	public DataBag getAllCartage(UserConfig userConfig,	List<QueryCriteria> queryCriteria) {
		logger.debug("**** Enter in to getAllCartage ****");
		DataBag cartageBag =null;
		Criteria criteria=getSession().createCriteria(Cartage.class);
		cartageBag= getDataList(getHibernateTemplate(), queryCriteria, criteria);
		logger.debug("**** Leaving getAllCartage ****");
		return cartageBag;
	}

	public Cartage getCartageByCode(UserConfig userConfig, String code) {
		logger.debug("**** Enter in to getCartageByCode ****");
		Cartage cartage=null;
		Criteria criteria =getSession().createCriteria(Cartage.class);
		criteria.add(Restrictions.eq("code", code));

		cartage =(Cartage) criteria.uniqueResult();
		if (cartage==null)
			throw new CommonDataAccessException("errors.recordnotfound");
		logger.debug("**** Leaving getCartageByCode ****");
		return cartage;
	}

	public Cartage getCartageById(UserConfig userConfig, int recordId) {
		logger.debug("**** Enter in to getCartageById ****");
		Cartage cartage=null;
		cartage=(Cartage) getHibernateTemplate().get(Cartage.class, Integer.valueOf(recordId));
		if (cartage==null)
			throw new CommonDataAccessException("errors.recordnotfound");
		logger.debug("**** Leaving getCartageById ****");
		return cartage;
	}

	public Cartage getCartageByCodeAndMasterId(UserConfig userConfig, String code,int cartageMasterId) {
		logger.debug("**** Enter in to getCartageByCode ****");
		Cartage cartage=null;
		Criteria criteria =getSession().createCriteria(Cartage.class);
		criteria.add(Restrictions.eq("code", code));
		criteria.add(Restrictions.eq("masterCartageId", cartageMasterId));

		cartage =(Cartage) criteria.uniqueResult();
		if (cartage==null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving getCartageByCode ****");
		return cartage;
	}

}

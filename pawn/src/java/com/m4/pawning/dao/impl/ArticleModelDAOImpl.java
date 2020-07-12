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
import com.m4.pawning.dao.ArticleModelDAO;
import com.m4.pawning.domain.ArticleModel;


public class ArticleModelDAOImpl extends MasterDAOSupport implements ArticleModelDAO {
	private static final Logger logger = Logger.getLogger(ArticleModelDAOImpl.class);

	public void createArticaleModel(UserConfig userConfig,ArticleModel articleModel) {
		System.out.println(articleModel.getCode());
		logger.debug("**** Enter in to createArticaleModel method ****");
		Criteria criteria = getSession().createCriteria(ArticleModel.class);
		criteria.add(Restrictions.eq("code", articleModel.getCode()));
		criteria.setProjection(Projections.rowCount());
		System.out.println(criteria.toString());

		int count = ((Integer) criteria.uniqueResult()).intValue();
        if (count !=0)
            throw new CommonDataAccessException("errors.recordexist");

//        initializeDomainObject(userConfig, articleModel);
//        getHibernateTemplate().save(articleModel);
        create(userConfig, articleModel);
		logger.debug("**** Leaving from createArticaleModel method ****");
	}

	public void removeArticaleModel(UserConfig userConfig,ArticleModel articleModel) {
		logger.debug("**** Enter in to createArticaleModel method ****");
//		ArticleModel model = (ArticleModel)getHibernateTemplate().get(ArticleModel.class, Integer.valueOf(articleModel.getRecordId()));
//		model.setVersion(articleModel.getVersion());
//		getHibernateTemplate().delete(model);
		remove(userConfig, articleModel);
		logger.debug("**** Leaving from createArticaleModel method ****");
	}

	public void updateArticaleModel(UserConfig userConfig,ArticleModel articleModel) {
		logger.debug("**** Enter in to createArticaleModel method ****");
//		initializeDomainObject(userConfig, articleModel);
//		getHibernateTemplate().update(articleModel);
		update(userConfig, articleModel);
		logger.debug("**** Leaving from createArticaleModel method ****");
	}

	public DataBag getAllArticaleModel(UserConfig userConfig,List<QueryCriteria> criteriaList) {
		logger.debug("**** Enter in to getAllTicketStatus method ****");
		DataBag articaleModelBag = null;
		Criteria criteria = getSession().createCriteria(ArticleModel.class);
		articaleModelBag = getDataList(getHibernateTemplate(), criteriaList,criteria );
		logger.debug("**** Leaving from createArticaleModel method ****");
		return articaleModelBag;
	}

	public ArticleModel getArticaleModelByCode(UserConfig userConfig,String code) {
		logger.debug("**** Enter in to createArticaleModel method ****");
		ArticleModel articleModel = null;
		Criteria criteria = getSession().createCriteria(ArticleModel.class);
		criteria.add(Restrictions.eq("code", code));
		articleModel = (ArticleModel)criteria.uniqueResult();

		if(articleModel == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from createArticaleModel method ****");
		return articleModel;
	}

	public ArticleModel getArticaleModelById(UserConfig userConfig,int recordId) {
		logger.debug("**** Enter in to getArticaleModelById method ****");
		ArticleModel articleModel = null;
		articleModel = (ArticleModel)getHibernateTemplate().get(ArticleModel.class, Integer.valueOf(recordId));

		if(articleModel == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from getArticaleModelById method ****");
		return articleModel;
	}

	public ArticleModel getArticaleModelByCodeAndProductId(UserConfig userConfig,String code,int productId) {
		logger.debug("**** Enter in to getArticaleModelByCodeAndProduct method ****");
		ArticleModel articleModel = null;
		Criteria criteria = getSession().createCriteria(ArticleModel.class);
		criteria.add(Restrictions.eq("code", code));
		criteria.add(Restrictions.eq("productId", productId));
		articleModel = (ArticleModel)criteria.uniqueResult();

		if(articleModel == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from getArticaleModelByCodeAndProduct method ****");
		return articleModel;
	}
}

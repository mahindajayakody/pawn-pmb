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
import com.m4.pawning.dao.ArticleDAO;
import com.m4.pawning.domain.Article;
import com.m4.pawning.domain.Location;

public class ArticleDAOImpl extends MasterDAOSupport implements ArticleDAO {
	private static final Logger logger = Logger.getLogger(ArticleDAOImpl.class);

	public void createArticle(UserConfig userConfig, Article article) {
		logger.debug("**** Enter in to createArticle method ****");
		Criteria criteria = getSession().createCriteria(Article.class);
		criteria.add(Restrictions.eq("code", article.getCode()));
		criteria.add(Restrictions.eq("productId", article.getProductId()));
		criteria.add(Restrictions.eq("articleModelId", article.getArticleModelId()));
		criteria.setProjection(Projections.rowCount());

		int count = ((Integer) criteria.uniqueResult()).intValue();
        if (count !=0)
            throw new CommonDataAccessException("errors.recordexist");

//        initializeDomainObject(userConfig, article);
//        getHibernateTemplate().save(article);
        create(userConfig, article);
		logger.debug("**** Leaving from createArticle method ****");
	}

	public void removeArticle(UserConfig userConfig, Article article) {
		logger.debug("**** Enter in to removeArticle method ****");
//		Article status = (Article)getHibernateTemplate().get(Article.class, Integer.valueOf(article.getRecordId()));
//		status.setVersion(article.getVersion());
//		getHibernateTemplate().delete(status);
		remove(userConfig, article);
		logger.debug("**** Leaving from removeArticle method ****");
	}

	public void updateArticle(UserConfig userConfig, Article article) {
		logger.debug("**** Enter in to updateArticle method ****");
		update(userConfig, article);
		logger.debug("**** Leaving from updateArticle method ****");
	}

	public DataBag getAllArticle(UserConfig userConfig,List<QueryCriteria> criteriaList) {
		logger.debug("**** Enter in to getAllArticle method ****");
		DataBag articleBag = null;
		Criteria criteria = getSession().createCriteria(Article.class);
		articleBag = getDataList(getHibernateTemplate(), criteriaList,criteria );
		logger.debug("**** Leaving from getAllArticle method ****");
		return articleBag;
	}

	public Article getArticleByCode(UserConfig userConfig, String code) {
		logger.debug("**** Enter in to getArticleByCode method ****");
		Article article = null;
		Criteria criteria = getSession().createCriteria(Article.class);
		criteria.add(Restrictions.eq("code", code));
		article = (Article)criteria.uniqueResult();

		if(article == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from getArticleByCode method ****");
		return article;
	}

	public Article getArticleById(UserConfig userConfig, int recordId) {
		logger.debug("**** Enter in to getArticleById method ****");
		Article article = null;
		article = (Article)getHibernateTemplate().get(Article.class, Integer.valueOf(recordId));

		if(article == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from getArticleById method ****");
		return article;
	}

	public Article getArticleByCodeAndArticleModel(UserConfig userConfig, String code,int articleModelId) {
		logger.debug("**** Enter in to getArticleByCode method ****");
		Article article = null;
		Criteria criteria = getSession().createCriteria(Article.class);
		criteria.add(Restrictions.eq("code", code));
		criteria.add(Restrictions.eq("articleModelId", articleModelId));
		article = (Article)criteria.uniqueResult();

		if(article == null)
			throw new CommonDataAccessException("errors.recordnotfound");

		logger.debug("**** Leaving from getArticleByCode method ****");
		return article;
	}
}

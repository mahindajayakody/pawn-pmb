package com.m4.pawning.service.impl;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableServiceImpl;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.ArticleModelDAO;
import com.m4.pawning.domain.ArticleModel;
import com.m4.pawning.service.ArticleModelService;

public class ArticleModelServiceImpl extends AuthorizableServiceImpl implements ArticleModelService {
	private ArticleModelDAO articleModelDAO;

	public void setArticaleModelDAO(ArticleModelDAO articleModelDAO) {
		this.articleModelDAO = articleModelDAO;
	}

	public void createArticleModel(UserConfig userConfig,ArticleModel articleModel) throws PawnException {
		articleModelDAO.createArticaleModel(userConfig, articleModel);
	}

	public DataBag getAllArticaleModel(UserConfig userConfig,List<QueryCriteria> criteriaList) throws PawnException {
		return articleModelDAO.getAllArticaleModel(userConfig, criteriaList);
	}

	public ArticleModel getArticleModelByCode(UserConfig userConfig, String code)throws PawnException {
		return articleModelDAO.getArticaleModelByCode(userConfig, code);
	}

	public ArticleModel getArticleModelById(UserConfig userConfig, int recordId)throws PawnException {
		return articleModelDAO.getArticaleModelById(userConfig, recordId);
	}

	public void removeArticleModel(UserConfig userConfig,ArticleModel articleModel) throws PawnException {
		articleModelDAO.removeArticaleModel(userConfig, articleModel);
	}

	public void updateArticleModel(UserConfig userConfig,ArticleModel articleModel) throws PawnException {
		articleModelDAO.updateArticaleModel(userConfig, articleModel);
	}

	public ArticleModel getArticaleModelByCodeAndProductId(UserConfig userConfig, String code, int productId) throws PawnException {
		return articleModelDAO.getArticaleModelByCodeAndProductId(userConfig, code, productId);
	}
}

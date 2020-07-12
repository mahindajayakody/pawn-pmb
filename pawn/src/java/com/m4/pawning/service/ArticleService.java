package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableService;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.ArticleModel;
import com.m4.pawning.domain.Article;


public interface ArticleService extends AuthorizableService{
	public void createArticle(UserConfig userConfig,Article article)throws PawnException;
	public void updateArticle(UserConfig userConfig,Article article)throws PawnException;
	public void removeArticle(UserConfig userConfig,Article article)throws PawnException;

	public Article getArticleById(UserConfig userConfig,int recordId)throws PawnException;
	public Article getArticleByCode(UserConfig userConfig,String code)throws PawnException;
	public DataBag getAllArticle(UserConfig userConfig,List<QueryCriteria> criteriaList)throws PawnException;
	public Article getArticleByCodeAndArticleModel(UserConfig userConfig, String code,int articleModelId) throws PawnException;
}

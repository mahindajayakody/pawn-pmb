package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.AuthorizableDAO;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Article;

public interface ArticleDAO extends AuthorizableDAO{
	public void createArticle(UserConfig userConfig,Article article);
	public void updateArticle(UserConfig userConfig,Article article);
	public void removeArticle(UserConfig userConfig,Article article);

	public Article getArticleById(UserConfig userConfig,int recordId);
	public Article getArticleByCode(UserConfig userConfig,String code);
	public DataBag getAllArticle(UserConfig userConfig,List<QueryCriteria> criteriaList);
	public Article getArticleByCodeAndArticleModel(UserConfig userConfig, String code,int articleModelId);
}

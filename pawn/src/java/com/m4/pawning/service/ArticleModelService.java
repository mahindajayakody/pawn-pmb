package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableService;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.ArticleModel;


public interface ArticleModelService extends AuthorizableService{
	public void createArticleModel(UserConfig userConfig,ArticleModel articleModel)throws PawnException;
	public void updateArticleModel(UserConfig userConfig,ArticleModel articleModel)throws PawnException;
	public void removeArticleModel(UserConfig userConfig,ArticleModel articleModel)throws PawnException;

	public ArticleModel getArticleModelById(UserConfig userConfig,int recordId)throws PawnException;
	public ArticleModel getArticleModelByCode(UserConfig userConfig,String code)throws PawnException;
	public DataBag getAllArticaleModel(UserConfig userConfig,List<QueryCriteria> criteriaList)throws PawnException;
	public ArticleModel getArticaleModelByCodeAndProductId(UserConfig userConfig,String code,int productId) throws PawnException;
}

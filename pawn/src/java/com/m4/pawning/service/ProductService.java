package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableService;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Product;


public interface ProductService extends AuthorizableService{
	public void createProduct(UserConfig userConfig,Product product)throws PawnException;
	public void updateProduct(UserConfig userConfig,Product product)throws PawnException;
	public void removeProduct(UserConfig userConfig,Product product)throws PawnException;

	public Product getProductById(UserConfig userConfig,int recordId)throws PawnException;
	public Product getProductByCode(UserConfig userConfig,String code)throws PawnException;
	public DataBag getAllProduct(UserConfig userConfig,List<QueryCriteria> criteriaList)throws PawnException;

}

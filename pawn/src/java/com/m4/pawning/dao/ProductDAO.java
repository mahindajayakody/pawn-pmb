package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.AuthorizableDAO;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Product;


public interface ProductDAO extends AuthorizableDAO{
	public void createProduct(UserConfig userConfig,Product product);
	public void updateProduct(UserConfig userConfig,Product product);
	public void removeProduct(UserConfig userConfig,Product product);

	public Product getProductById(UserConfig userConfig,int recordId);
	public Product getProductByCode(UserConfig userConfig,String code);
	public DataBag getAllProduct(UserConfig userConfig,List<QueryCriteria> criteriaList);

}

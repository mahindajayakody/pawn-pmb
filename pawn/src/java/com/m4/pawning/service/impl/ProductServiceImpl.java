package com.m4.pawning.service.impl;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableServiceImpl;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.ProductDAO;
import com.m4.pawning.domain.Product;
import com.m4.pawning.service.ProductService;

public class ProductServiceImpl extends AuthorizableServiceImpl implements ProductService {
	private ProductDAO productDAO;

	public void setProductDAO(ProductDAO productDAO){
		this.productDAO = productDAO;
	}

	public void createProduct(UserConfig userConfig, Product product)throws PawnException {
		productDAO.createProduct(userConfig, product);
	}

	public void removeProduct(UserConfig userConfig, Product product)throws PawnException {
		productDAO.removeProduct(userConfig, product);
	}

	public void updateProduct(UserConfig userConfig, Product product)throws PawnException {
		productDAO.updateProduct(userConfig, product);
	}

	public DataBag getAllProduct(UserConfig userConfig,List<QueryCriteria> criteriaList) throws PawnException {
		return productDAO.getAllProduct(userConfig, criteriaList);
	}

	public Product getProductByCode(UserConfig userConfig, String code)throws PawnException {
		return productDAO.getProductByCode(userConfig, code);
	}

	public Product getProductById(UserConfig userConfig, int recordId)throws PawnException {
		return productDAO.getProductById(userConfig, recordId);
	}
}

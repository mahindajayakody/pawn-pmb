package com.m4.pawning.service.impl;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableServiceImpl;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.CartageMarsterDAO;
import com.m4.pawning.domain.CartageMarster;
import com.m4.pawning.service.CartageMarsterService;

public class CartageMarsterServiceImpl extends AuthorizableServiceImpl implements CartageMarsterService {
	private CartageMarsterDAO cartageMarsterDAO;

	public void setCartageMarsterDAO(CartageMarsterDAO cartageMarsterDAO) {
		this.cartageMarsterDAO = cartageMarsterDAO;
	}

	public void createCartageMarster(UserConfig userConfig, CartageMarster cartageMarster) throws PawnException {
		cartageMarsterDAO.createCartageMarster(userConfig, cartageMarster);
	}

	public void removeCartageMarster(UserConfig userConfig, CartageMarster cartageMarster) throws PawnException {
		cartageMarsterDAO.removeCartageMarster(userConfig, cartageMarster);
	}

	public void updateCartageMarster(UserConfig userConfig, CartageMarster cartageMarster) throws PawnException {
		cartageMarsterDAO.updateCartageMarster(userConfig, cartageMarster);
	}

	public DataBag getAllCartageMarster(UserConfig userConfig, List<QueryCriteria> criteriaList) throws PawnException {
		return cartageMarsterDAO.getAllCartageMarster(userConfig, criteriaList);
	}

	public CartageMarster getCartageMarsterByCode(UserConfig userConfig, String code) throws PawnException {
		return cartageMarsterDAO.getCartageMarsterByCode(userConfig, code);
	}

	public CartageMarster getCartageMarsterById(UserConfig userConfig, int recordId) throws PawnException {
		return cartageMarsterDAO.getCartageMarsterById(userConfig, recordId);
	}
}

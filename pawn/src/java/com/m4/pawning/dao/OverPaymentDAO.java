package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.AuthorizableDAO;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.OverPayment;

public interface OverPaymentDAO extends AuthorizableDAO{
	public void createOverPayment(UserConfig userConfig,OverPayment overPayment);

	public OverPayment getOverPaymentById(UserConfig userConfig,int recordId);
	public DataBag getAllOverPayment(UserConfig userConfig,List<QueryCriteria> criteriaList);
	public DataBag getAllOverPaymentByTicket(UserConfig userConfig,int ticketId);
	public DataBag getAllOverPaymentByReceipt(UserConfig userConfig,int receiptId);
}

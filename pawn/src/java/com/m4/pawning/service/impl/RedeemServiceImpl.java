package com.m4.pawning.service.impl;

import com.m4.core.exception.PawnException;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.RedeemDAO;
import com.m4.pawning.service.RedeemService;

public class RedeemServiceImpl implements RedeemService {
	private RedeemDAO redeemDAO;
	
	public void setRedeemDAO(RedeemDAO redeemDAO) {
		this.redeemDAO = redeemDAO;
	}
	
	public void redeem(UserConfig userConfig, int ticketId)throws PawnException {
		redeemDAO.redeem(userConfig, ticketId);
	}
	public void renew(UserConfig userConfig, int ticketId)throws PawnException {
		redeemDAO.renew(userConfig, ticketId);
	}
}

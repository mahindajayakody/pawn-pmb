package com.m4.pawning.service;

import com.m4.core.exception.PawnException;
import com.m4.core.util.UserConfig;

public interface RedeemService {
	public void redeem(UserConfig userConfig,int ticketId)throws PawnException;
	public void renew(UserConfig userConfig,int ticketId)throws PawnException;
}

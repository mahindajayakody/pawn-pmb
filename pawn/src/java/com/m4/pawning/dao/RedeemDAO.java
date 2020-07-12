package com.m4.pawning.dao;

import com.m4.core.util.UserConfig;

public interface RedeemDAO {
	public void redeem(UserConfig userConfig,int ticketId);
	public void renew(UserConfig userConfig,int ticketId);
}

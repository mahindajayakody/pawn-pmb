package com.m4.core.util;

import com.m4.core.bean.Trace;

public interface AuthorizableDAO {
    public void authorizeCreation(UserConfig userConfig,Trace trace,boolean authorize);
	public void authorizeDeletion(UserConfig userConfig,Trace trace,boolean authorize);
	public void authorizeUpdation(UserConfig userConfig,Trace trace,boolean authorize);
}

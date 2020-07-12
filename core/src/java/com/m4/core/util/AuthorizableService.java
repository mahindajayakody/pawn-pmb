package com.m4.core.util;

import com.m4.core.bean.Trace;
import com.m4.core.exception.PawnException;

public interface AuthorizableService {
    public void authorizeCreation(UserConfig userConfig,Trace trace,boolean authorize)throws PawnException;
	public void authorizeDeletion(UserConfig userConfig,Trace trace,boolean authorize)throws PawnException;
	public void authorizeUpdation(UserConfig userConfig,Trace trace,boolean authorize)throws PawnException;
}

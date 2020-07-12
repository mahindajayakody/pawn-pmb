package com.m4.pawning.service;

import com.m4.core.exception.PawnException;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dto.InfoConsoleDTO;

public interface InfoconsoleService {
	public InfoConsoleDTO getInfoConsoleData(UserConfig userConfig,int ticketId)throws PawnException;
}

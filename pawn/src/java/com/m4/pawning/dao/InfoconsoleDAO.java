package com.m4.pawning.dao;

import com.m4.core.util.UserConfig;
import com.m4.pawning.dto.InfoConsoleDTO;

public interface InfoconsoleDAO {
	public InfoConsoleDTO getInfoConsoleData(UserConfig userConfig,int ticketId);
}

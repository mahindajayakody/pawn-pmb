package com.m4.pawning.service.impl;

import com.m4.core.exception.PawnException;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.InfoconsoleDAO;
import com.m4.pawning.dto.InfoConsoleDTO;
import com.m4.pawning.service.InfoconsoleService;

public class InfoconsoleServiceImpl implements InfoconsoleService {
	private InfoconsoleDAO infoconsoleDAO;

	public void setInfoconsoleDAO(InfoconsoleDAO infoconsoleDAO) {
		this.infoconsoleDAO = infoconsoleDAO;
	}

	public InfoConsoleDTO getInfoConsoleData(UserConfig userConfig, int ticketId) throws PawnException {
		return infoconsoleDAO.getInfoConsoleData(userConfig, ticketId);
	}
}

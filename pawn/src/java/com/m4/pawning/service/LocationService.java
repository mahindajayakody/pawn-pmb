package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableService;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Location;

public interface LocationService extends AuthorizableService{
	public void createLocation(UserConfig userConfig,Location ticketStatus)throws PawnException;
	public void updateLocation(UserConfig userConfig,Location ticketStatus)throws PawnException;
	public void removeLocation(UserConfig userConfig,Location ticketStatus)throws PawnException;

	public Location getLocationById(UserConfig userConfig,int recordId)throws PawnException;
	public Location getLocationByCode(UserConfig userConfig,String code)throws PawnException;
	public DataBag getAllLocation(UserConfig userConfig,List<QueryCriteria> criteriaList)throws PawnException;

}

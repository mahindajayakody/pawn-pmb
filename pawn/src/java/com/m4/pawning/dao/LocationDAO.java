package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.AuthorizableDAO;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Location;

public interface LocationDAO extends AuthorizableDAO{
	public void createLocation(UserConfig userConfig,Location ticketStatus);
	public void updateLocation(UserConfig userConfig,Location ticketStatus);
	public void removeLocation(UserConfig userConfig,Location ticketStatus);

	public Location getLocationById(UserConfig userConfig,int recordId);
	public Location getLocationByCode(UserConfig userConfig,String code);
	public DataBag getAllLocation(UserConfig userConfig,List<QueryCriteria> criteriaList);

}

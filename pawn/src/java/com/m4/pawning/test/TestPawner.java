package com.m4.pawning.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.BaseUnitTest;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.SessionUtil;
import com.m4.core.util.UserConfig;
import com.m4.core.util.QueryCriteria.Oparator;
import com.m4.pawning.service.PawnerService;

public class TestPawner extends BaseUnitTest {
	private PawnerService pawnerService;
	private UserConfig userConfig;
	
	protected void setUp() throws Exception {
		super.setUp();	
		userConfig = new UserConfig();
		userConfig.setAuthorizeMode(1);
		userConfig.setCompanyId(1);
		userConfig.setBranchId(1);
		userConfig.setLoginDate(new Date());
		userConfig.setUserId(1);	
		pawnerService = (PawnerService)getContext().getBean("pawnerProxy");
	}
	
//	public void testGetAllPawner(){
//		List<QueryCriteria>  list = new ArrayList<QueryCriteria>();
//		
//		String nicOrBrn = "";
//		String surname  = "";
//		String town     = "";
//		String userType = "OFF";
//		
//		String sql = "";
//		
//		if(nicOrBrn!=null && nicOrBrn!="")
//			sql = " AND map.pawner.idOrBrNo LIKE '" + nicOrBrn + "%' ";
//		if(surname!=null && surname!="")
//			sql = " AND map.pawner.surName LIKE '" + surname + "%' ";
//		if(town!=null && town!="")
//			sql = " AND map.pawner.addressLine4 LIKE '" + town + "%' ";
//		if(userType!=null && userType!="")
//			sql = " AND map.pawnerType.code='" + userType + "' ";
//		
//		try {
//			pawnerService.getAllPawnerBySQL(userConfig, sql);
//		} catch (PawnException e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println("");
//	}
	
	public void testGetAllPawner(){
		try {
			pawnerService.getAllPawner(userConfig, null);
		} catch (PawnException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
//	public void testGetPawnerById(){
//		try {
//			pawnerService.getPawnerById(userConfig, 1);
//		} catch (PawnException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}

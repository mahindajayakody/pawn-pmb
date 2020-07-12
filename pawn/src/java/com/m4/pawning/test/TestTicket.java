//package com.m4.pawning.test;
//
//import java.util.Date;
//import java.util.List;
//
//import com.m4.core.exception.PawnException;
//import com.m4.core.util.BaseUnitTest;
//import com.m4.core.util.UserConfig;
//import com.m4.pawning.service.TicketService;
//
//public class TestTicket extends BaseUnitTest {
//	private TicketService ticketService;
//	private UserConfig userConfig;
//	
//	protected void setUp() throws Exception {
//		super.setUp();	
//		userConfig = new UserConfig();
//		userConfig.setAuthorizeMode(1);
//		userConfig.setCompanyId(1);
//		userConfig.setBranchId(1);
//		userConfig.setLoginDate(new Date());
//		userConfig.setUserId(1);	
//		ticketService = (TicketService)getContext().getBean("ticketProxy");
//	}
//	
////	public void testGetAllTickets(){
////		try {
////			ticketService.getAllTicket(userConfig, null);
////		} catch (PawnException e) {			
////			e.printStackTrace();
////		}
////	}
//	
//	public void testGetSerchData(){
//		try {
//			List<Object[]> list = ticketService.getSerchTicketData(userConfig);
//			for(Object object[]:list){
//				System.out.println(object[0] + "  " + object[1]);
//			}
//		} catch (PawnException e) {			
//			e.printStackTrace();
//		}
//	}
//}

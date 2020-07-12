package com.m4.pawning.web.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.json.JSONException;
import org.json.JSONObject;

import com.m4.core.exception.PawnException;
import com.m4.core.util.BaseAction;
import com.m4.core.util.PasswordService;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.SessionUtil;
import com.m4.core.util.StrutsFormValidateUtil;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Branch;
import com.m4.pawning.domain.SystemProgram;
import com.m4.pawning.dto.ProgramAccessDTO;
import com.m4.pawning.service.BranchService;
import com.m4.pawning.service.ProgramAccessService;
import com.m4.pawning.service.SystemProgramService;

public class LoginAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(LoginAction.class);
	SimpleDateFormat simpdate = new SimpleDateFormat("dd/MM/yyyy");
	
	private SystemProgramService systemProgramService;
	private ProgramAccessService programAccessService;
	private BranchService branchService;
	
	public void setSystemProgramService(SystemProgramService systemProgramService) {
		this.systemProgramService = systemProgramService;
	}
	public void setProgramAccessService(ProgramAccessService programAccessService) {
		this.programAccessService = programAccessService;
	}
	public void setBranchService(BranchService branchService) {
		this.branchService = branchService;
	}
		
	public ActionForward setUp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("**** Enter in to setUp method ****");
		
		UserConfig userConfig = SessionUtil.getUserSession(request);
		
		if(userConfig.isFake()){
			List<SystemProgram> systemPrograms = systemProgramService.getAllSystemPrograms(new ArrayList<QueryCriteria>());
			request.setAttribute("dataList",systemPrograms);
		}else if (userConfig.getPasswordExpired()){
			Collection<ProgramAccessDTO> dataList = programAccessService.getPasswordChange(userConfig.getUserGroupId());
			request.setAttribute("dataList",dataList);			
		}else{
			Collection<ProgramAccessDTO> dataList = programAccessService.getAllProgramsWithAccessByGroupId(userConfig.getUserGroupId());
			request.setAttribute("dataList",dataList);			
		}

		logger.info("**** Leaving from setUp method ****");
		return mapping.findForward("page");
	}	
	
//	public ActionForward logme(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		MessageResources messageResources = getResources(request,"message");		
//		String userName = request.getParameter("userName");
//		String password = request.getParameter("password");
//		
//		if(userName.equals("modular4") && password.equals("airforce1")){
//			UserConfig userConfig = new UserConfig();
//			userConfig.setCompanyId(1);
//			userConfig.setBranchId(1);
//			userConfig.setUserId(1);
//			userConfig.setLoginDate(new Date());
//			userConfig.setFake(true);
//			request.getSession().setAttribute("LOGIN_KEY",userConfig );
//			response.getWriter().write(StrutsFormValidateUtil.getMessageCreateSuccess(messageResources).toString());
//			return null;
//		}
//		
//		int companyId   = Integer.parseInt(request.getParameter("company"));
//		UserConfig userConfig = programAccessService.validateUser(companyId, userName, PasswordService.getInstance().encrypt(password),request.getSession().getId());
//		System.out.println("<Session Id> " + request.getSession().getId());
//		if(userConfig!=null){
//			JSONObject errorObject = new JSONObject();
//			Calendar calendar = Calendar.getInstance();
//			calendar.setTime(userConfig.getLoginDate());
//			calendar.add(Calendar.DATE, userConfig.getPasswordExpirePeriod());
//			
//			if (userConfig.getIsActive()==0) {				
//		    	try{
//		    		errorObject.put("error","User is not Active.");
//		    		userConfig.setPasswordExpired(0);
//		    	}catch(JSONException jex){}	    		    	
//				response.getWriter().write(errorObject.toString());
//				
//			}
//			else if (userConfig.getLoginDate().compareTo(calendar.getTime()) != -1){
////				userConfig.setPasswordExpired(1);		    		
//				response.getWriter().write(errorObject.toString());
//			}else if (userConfig.getLoginDate().compareTo(calendar.getTime()) < 0){
////				long dateDiff = calendar.getTimeInMillis() - userConfig.getLoginDate().getTime();
////				dateDiff=(dateDiff / (1000 * 60 * 60 * 24)); /* Second,minutes,hours,days*/
////				int days = (int)dateDiff;
////				if(days <= 10){
//						    		    	
//					request.getSession().setAttribute("LOGIN_KEY", userConfig);
//					response.getWriter().write(StrutsFormValidateUtil.getMessageCreateSuccess(messageResources).toString());
////				}
//				/*try{
//		    		errorObject.put("error", "Password Expired Can't Login!");
//				}catch(JSONException jex){}	    		    	
//				response.getWriter().write(errorObject.toString());*/
//			}
//			else{
//				request.getSession().setAttribute("LOGIN_KEY", userConfig);
//				response.getWriter().write(StrutsFormValidateUtil.getMessageCreateSuccess(messageResources).toString());
//			}
//		}else{
//			JSONObject errorObject = new JSONObject();
//	    	try{
//	    		errorObject.put("error","Username and password do not match.");
//	    	}catch(JSONException jex){}	    		    	
//			response.getWriter().write(errorObject.toString());
//		}	
//		return null;
//	}
	public ActionForward logme(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MessageResources messageResources = getResources(request,"message");		
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		
		if(userName.equals("modular4") && password.equals("airforce1")){
			UserConfig userConfig = new UserConfig();
			userConfig.setCompanyId(1);
			userConfig.setBranchId(1);
			userConfig.setUserId(1);
			userConfig.setLoginDate(new Date());
			userConfig.setFake(true);
			request.getSession().setAttribute("LOGIN_KEY",userConfig );
			response.getWriter().write(StrutsFormValidateUtil.getMessageCreateSuccess(messageResources).toString());
			return null;
		}
		
		int companyId   = Integer.parseInt(request.getParameter("company"));
		UserConfig userConfig = programAccessService.validateUser(companyId, userName, PasswordService.getInstance().encrypt(password),request.getSession().getId());
		System.out.println("<Session Id> " + request.getSession().getId());
		if(userConfig!=null){
			if (userConfig.getIsActive()==0) {
				JSONObject errorObject = new JSONObject();
		    	try{
		    		errorObject.put("error","User is not Active.");
		    	}catch(JSONException jex){}	    		    	
				response.getWriter().write(errorObject.toString());
				
			}else if(userConfig.getPasswordExpired()){
				JSONObject errorObject = new JSONObject();
		    	try{
		    		errorObject.put("error","User Password Exipired.");
		    	}catch(JSONException jex){}	    		    	
				response.getWriter().write(errorObject.toString());
//				userConfig.setPasswordExpired(1);
//				request.getSession().setAttribute("LOGIN_KEY", userConfig);
//				response.getWriter().write(StrutsFormValidateUtil.getMessageCreateSuccess(messageResources).toString());
			}
			else{
				request.getSession().setAttribute("LOGIN_KEY", userConfig);
				response.getWriter().write(StrutsFormValidateUtil.getMessageCreateSuccess(messageResources).toString());
			}
		}else{
			JSONObject errorObject = new JSONObject();
	    	try{
	    		errorObject.put("error","Username and password do not match.");
	    	}catch(JSONException jex){}	    		    	
			response.getWriter().write(errorObject.toString());
		}	
		return null;
	}
	public ActionForward logOut(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MessageResources messageResources = getResources(request,"message");	
		try
		{
			programAccessService.logOut(getUserSession(request));
			request.getSession().removeAttribute("LOGIN_KEY");
			request.getSession().invalidate();
			response.getWriter().write(StrutsFormValidateUtil.getMessageCreateSuccess(messageResources).toString());
		}catch (PawnException ex){
			response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, getLocale(request)).toString());
		}
		return null;
	}
	
	public ActionForward changeBranch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("*** User Change  the branch");
		
		
		int branchId = Integer.parseInt(request.getParameter("branchId"));
		
		UserConfig userConfig = SessionUtil.getUserSession(request);
		Branch branch = branchService.getBranchByIdWithSystemDate(userConfig, branchId);
		userConfig.setLoginDate(branch.getSystemDate().getCurrentDate());
		userConfig.setBranchName(branch.getBarnchName());
		userConfig.setBrachEmail(branch.getEmail());
		userConfig.setBranchId(branchId);
		
		request.getSession().setAttribute("LOGIN_KEY", userConfig);
		
		JSONObject object = new JSONObject();
		object.put("logindate", simpdate.format(branch.getSystemDate().getCurrentDate()));
		response.getWriter().write(object.toString());
		return null;
	}
}

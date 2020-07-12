package com.m4.pawning.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.m4.core.exception.CommonDataAccessException;
import com.m4.core.exception.PawnException;
import com.m4.core.util.MasterAction;
import com.m4.core.util.PasswordService;
import com.m4.core.util.SessionUtil;
import com.m4.core.util.StrutsFormValidateUtil;
import com.m4.pawning.domain.Officer;
import com.m4.pawning.service.OfficerService;
import com.m4.pawning.service.ParameterValueService;

public class ChangePasswordAction extends MasterAction {
	private static final Logger logger = Logger.getLogger(ChangePasswordAction.class);
	private OfficerService officerService;
	private ParameterValueService parameterValueService;
	
	
	public void setParameterValueService(ParameterValueService parameterValueService) {
		this.parameterValueService = parameterValueService;
	}

	public void setOfficerService(OfficerService officerService) {
		this.officerService = officerService;
	}
	
	public ActionForward resetPassword(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to changePassword method ****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if(!validateForm.isEmpty()){
        	response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
        }else{
        	try{
        		Officer officer = officerService.getOfficerById(SessionUtil.getUserSession(request), SessionUtil.getUserSession(request).getUserId());
        		if(!officer.getPassword().equals(PasswordService.getInstance().encrypt(request.getParameter("oldPassword"))))
        			throw new PawnException("errors.oldpasswordnotmatch");
        		
        		//officer.setPassword("");
        		officerService.resetPassword(SessionUtil.getUserSession(request), officer,request.getParameter("newPassword"));
        		response.getWriter().write(StrutsFormValidateUtil.getMessageUpdateSuccess(messageResources).toString());
        	}catch(PawnException ex){
        		response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
        	}
        }
		logger.debug("**** Leaving from changePassword method ****");
		return null;
	}
	
	@Override
	protected ActionForward getAuthorizeData(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {		
		return null;
	}
	public ActionForward update(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to update method ****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");
		int officerId   = Integer.parseInt(request.getParameter("recordId"));
		boolean changepassword=request.getParameter(("change")).equals("Yes");
		String confirmPassword = request.getParameter("confirmPassword");

		if(!validateForm.isEmpty()){
        	response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
        }else{
        	try{
        		if(!changepassword && PasswordService.getInstance().encrypt(request.getParameter("oldPassword")).equals(PasswordService.getInstance().encrypt(request.getParameter("confirmPassword"))))
        			throw new PawnException("errors.oldequlnew");       		
        		
        		Officer officer = officerService.getOfficerById(SessionUtil.getUserSession(request), officerId);
        		if(!changepassword){
        			officer.setPassword(request.getParameter("oldPassword"));
        		}else{
        			officer.setPassword(confirmPassword);
        		}
        		officerService.resetPassword(SessionUtil.getUserSession(request), officer, confirmPassword);
        		response.getWriter().write(StrutsFormValidateUtil.getMessageUpdateSuccess(messageResources).toString());
        	}catch(PawnException ex){
        		response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
        	}
        }
		logger.debug("**** Leaving from update method ****");
		return null;
	}

}

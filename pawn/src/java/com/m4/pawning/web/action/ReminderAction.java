package com.m4.pawning.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.m4.core.util.MasterAction;
import com.m4.core.util.StrutsFormValidateUtil;
import com.m4.pawning.domain.Reminder;


public class ReminderAction extends MasterAction {

	private static final Logger logger=Logger.getLogger(ReminderAction.class);
	public ActionForward update(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.info("**** Enter in to update ****");
		ActionMessages validateForm=form.validate(mapping, request);
		MessageResources messageResources=getResources(request,"message");
		
		if(!validateForm.isEmpty()){
			response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm, messageResources, getLocale(request), null).toString());
		}
		else{
			Reminder reminder= new Reminder();
			reminder.setTicketId(Integer.parseInt(request.getParameter("ticketId")));
			//reminder.setReminderId(reminderId)
			
		}
			
		
		logger.info("**** Leaving update ****");
		return null;
	}
	

	@Override
	protected ActionForward getAuthorizeData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

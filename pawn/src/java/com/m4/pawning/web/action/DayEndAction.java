package com.m4.pawning.web.action;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.m4.core.exception.PawnException;
import com.m4.core.util.MasterAction;
import com.m4.core.util.SessionUtil;
import com.m4.core.util.StrutsFormValidateUtil;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Branch;
import com.m4.pawning.service.DayEndService;

public class DayEndAction extends MasterAction {
	private static final Logger logger = Logger.getLogger(DayEndAction.class);

	private DayEndService dayEndService;

	public void setDayEndService(DayEndService dayEndService) {
		this.dayEndService = dayEndService;
	}

	public ActionForward doProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to getInitialData method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		String brances[] = request.getParameter("brances").split("<@>");
		Integer[] intBranches = new Integer[brances.length];

		for(int i=0;i<brances.length;i++)
			intBranches[i] = Integer.parseInt(brances[i]);

		try {
    		dayEndService.doDayEndProcess(SessionUtil.getUserSession(request),intBranches );
    		UserConfig userConfig = (UserConfig) request.getSession().getAttribute("LOGIN_KEY");
    		Calendar calendar = Calendar.getInstance();
			calendar.setTime(userConfig.getLoginDate());
			calendar.add(Calendar.DATE, 1);
        	userConfig.setLoginDate(calendar.getTime());
        	request.getSession().setAttribute("LOGIN_KEY", userConfig);
    		response.getWriter().write(getMessageCreateSuccess(messageResources).toString());
    	} catch (PawnException ex) {
			response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
		}
		return null;
	}

	public ActionForward getInitialData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to getInitialData method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");
		List<Branch> branchList = null;

    	try {
    		branchList = dayEndService.getInitialData(SessionUtil.getUserSession(request));
    	} catch (PawnException ex) {
			response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
		}
    	Branch loginBranch = null;
        JSONArray mainArray = new JSONArray();
        JSONObject object = new JSONObject();

        if(branchList!=null){
	        for(Branch branch:branchList){
	        	JSONArray array = new JSONArray();
	        	array.put(branch.getCode());
	        	array.put(branch.getBarnchName());
	        	array.put(StrutsFormValidateUtil.parseString(branch.getSystemDate().getCurrentDate()));
	        	array.put("");
	        	array.put("Active");
	        	array.put(branch.getBranchId());
	        	mainArray.put(array);

	        	if(branch.getBranchId() == SessionUtil.getUserSession(request).getBranchId())
	        		loginBranch = branch;
	        }

	        object.put("branchId", loginBranch.getBranchId());
	        object.put("branchCode", loginBranch.getCode());
	        object.put("branchName", loginBranch.getBarnchName());
	        object.put("systemDate", StrutsFormValidateUtil.parseString(loginBranch.getSystemDate().getCurrentDate()));
	        object.put("processingDate", StrutsFormValidateUtil.parseString(loginBranch.getSystemDate().getCurrentDate()));
	        object.put("endDate", StrutsFormValidateUtil.parseString(loginBranch.getSystemDate().getNextDate()));
        }

        response.getWriter().write(mainArray.toString()+"<@>"+object.toString());
		logger.debug("**** Leaving from getInitialData method *****");
		return null;
	}

	public static JSONObject getMessageCreateSuccess(MessageResources messageResources){
    	JSONObject messageObject = new JSONObject();
    	try{
    		messageObject.put("success",messageResources.getMessage("msg.dayendsuccess"));
    	}catch(JSONException jex){}

    	return messageObject;
    }

	@Override
	protected ActionForward getAuthorizeData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}
}

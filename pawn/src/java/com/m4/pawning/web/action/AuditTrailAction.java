package com.m4.pawning.web.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;

import com.m4.core.bean.AuditTrail;
import com.m4.core.bean.UserLog;
import com.m4.core.util.DateUtil;
import com.m4.core.util.MasterAction;
import com.m4.core.util.SessionUtil;
import com.m4.core.util.StrutsFormValidateUtil;
import com.m4.pawning.service.AuditTrailService;

public class AuditTrailAction extends MasterAction {
	private static final Logger logger = Logger.getLogger(AuditTrailAction.class);

	private AuditTrailService auditTrailService;

	public void setAuditTrailService(AuditTrailService auditTrailService) {
		this.auditTrailService = auditTrailService;
	}

	public void populateAjaxData(JSONArray mainArray, List<AuditTrail> list) {
		for (AuditTrail auditTrail : list) {
			JSONArray subArray = new JSONArray();
			subArray.put(auditTrail.getAction());
			subArray.put(auditTrail.getTableName());
			subArray.put(auditTrail.getTrandate());
			subArray.put(auditTrail.getBeforeSaveData());
			subArray.put(auditTrail.getAfterSaveData());
			subArray.put(auditTrail.getTrnNo());

			mainArray.put(subArray);
		}
	}

	public ActionForward getAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.debug("**** Enter in to getAuthorizeData method *****");

		AuditTrail auditTrail = new AuditTrail();
		UserLog userLog = new UserLog();
		
		String userLogId = request.getParameter("userLogId");
		if (userLogId != null && userLogId.trim().length() > 0 ) {
			userLog.setUserLogId(Integer.parseInt(userLogId));
		}
		
		String action = request.getParameter("action");
		if (action != null && action.trim().length() > 0 ) {
			auditTrail.setAction(action);
		}

		String tableName = request.getParameter("type");
		if (tableName != null && tableName.trim().length() > 0 ) {
			auditTrail.setTableName(tableName);
		}

		Date fromDate = StrutsFormValidateUtil.parseDate(request.getParameter("fromDate"));
		if (fromDate != null) {
			auditTrail.setFromDate(fromDate);
		}

		Date toDate = StrutsFormValidateUtil.parseDate(request.getParameter("toDate"));
		if (fromDate != null && toDate != null) {
			auditTrail.setToDate(toDate);
		} else if(auditTrail.getFromDate() != null){
			auditTrail.setToDate(DateUtil.addDays(auditTrail.getFromDate(), 1));
		}

		List<AuditTrail> list = auditTrailService.getAuditTrail(SessionUtil.getUserSession(request),
				userLog, auditTrail).getDataList();
		
		JSONArray mainArray = new JSONArray();
		populateAjaxData(mainArray, list);
		response.getWriter().write(mainArray.toString());

		logger.debug("**** Leaving from getAuthorizeData method *****");
		return null;
	}

	@Override
	protected ActionForward getAuthorizeData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO : ??????
		return null;
	}
	
	@Override
	protected void setOtherData(ActionForm form, HttpServletRequest request) {
		request.setAttribute("auditTrailTypes", auditTrailService.getAuditTrailTypes());
	}
}

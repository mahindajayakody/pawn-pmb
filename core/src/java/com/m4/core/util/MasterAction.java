package com.m4.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.m4.core.bean.EventLog;
import com.m4.core.exception.PawnException;
import com.m4.core.util.QueryCriteria.Oparator;

public abstract class MasterAction extends BaseAction {

	public static final String CREATE = "create";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	public static final String VIEW = "view";
	public static final String AUTHORIZE = "authorize";
	public static final String APPROVE = "approve";
    public static final DateFormat DATEFORMAT = new SimpleDateFormat("dd/MM/yyyy");
    public static final DateFormat TIMESTAMPFORMAT = new SimpleDateFormat("dd/MM/yyyy H:mm:ss");
    
	private SystemAuditService systemAuditService;

	public void setSystemAuditService(SystemAuditService systemAuditService) {
		this.systemAuditService = systemAuditService;
	}

	@Override
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!isValidSession(request)) {
			return mapping.findForward("sessionError");
		}
		System.out.println(mapping.getName());
		System.out.println(mapping.getInputForward().getName());
		String events = request.getParameter("evn").trim();

		if (events != null) {
			String[] programEvents = events.split(":");

			if (programEvents.length != 0) {
				if (programEvents[0].equals("1"))// create
					return createSetup(mapping, form, request, response);
				else if (programEvents[0].equals("2"))// update
					return updateSetup(mapping, form, request, response);
				else if (programEvents[0].equals("3"))// delete
					return deleteSetup(mapping, form, request, response);
				else if (programEvents[0].equals("4"))// authorize
					return authorizeSetup(mapping, form, request, response);
				else if (programEvents[0].equals("5"))// approve
					return approveSetup(mapping, form, request, response);
			}
		} else {
			return createSetup(mapping, form, request, response);
		}
		return null;
	}

	public ActionForward createSetup(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaActionForm frm = (DynaActionForm) form;
		frm.initialize(mapping);
		frm.set("action", CREATE);
		setOtherData(form, request);
		createEventLog(mapping, request, EventLogStatusEnum.CREATE.getCode());
		return mapping.getInputForward();
	}

	public ActionForward updateSetup(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaActionForm frm = (DynaActionForm) form;
		frm.initialize(mapping);
		frm.set("action", UPDATE);
		setOtherData(form, request);
		createEventLog(mapping, request, EventLogStatusEnum.UPDATE.getCode());
		return mapping.getInputForward();
	}

	public ActionForward deleteSetup(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaActionForm frm = (DynaActionForm) form;
		frm.initialize(mapping);
		frm.set("action", DELETE);
		setOtherData(form, request);
		createEventLog(mapping, request, EventLogStatusEnum.DELETE.getCode());
		return mapping.getInputForward();
	}

	public ActionForward authorizeSetup(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaActionForm frm = (DynaActionForm) form;
		frm.initialize(mapping);
		frm.set("action", AUTHORIZE);
		setOtherData(form, request);
		createEventLog(mapping, request, EventLogStatusEnum.AUTHORIZED.getCode());
		return mapping.getInputForward();
	}

	public ActionForward approveSetup(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaActionForm frm = (DynaActionForm) form;
		frm.initialize(mapping);
		frm.set("action", APPROVE);
		setOtherData(form, request);
		createEventLog(mapping, request, EventLogStatusEnum.APPROVE.getCode());
		return mapping.getInputForward();
	}

	public ActionForward toolbar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaActionForm frm = (DynaActionForm) form;
		frm.initialize(mapping);
		frm.set("action", "toolbar");
		return mapping.getInputForward();
	}
	
	public List<QueryCriteria> getQueryCriteriaList(HttpServletRequest request, boolean isIncludeRecordStatus) {
		List<QueryCriteria> queryCriteriaList = new ArrayList<QueryCriteria>();
		String condition = request.getParameter("conditions");
		String value = request.getParameter("value");
		String[] conditions = new String[0];
		String[] values = new String[0];

		if ((condition != null && condition != "") && (value != null && value != "")) {
			conditions = condition.split("<next>");
			values = value.split("<next>");

			for (int i = 0; i < conditions.length; i++) {
				if (conditions[i] != null && !conditions[i].equals("")) {
					try {
						queryCriteriaList.add(new QueryCriteria(conditions[i], Oparator.EQUAL, Integer
								.parseInt(values[i])));
					} catch (NumberFormatException e) {
						queryCriteriaList.add(new QueryCriteria(conditions[i], Oparator.EQUAL, values[i]));
					}
				}
			}
		}
		if(isIncludeRecordStatus)
			queryCriteriaList.add(new QueryCriteria("recordStatus", Oparator.EQUAL, RecordStatusEnum.ACTIVE.getCode()));
		return queryCriteriaList;
	}

	public List<QueryCriteria> getQueryCriteriaList(HttpServletRequest request) {
		return getQueryCriteriaList(request, true);
	}

	public List<QueryCriteria> getAuthorizeQueryCriteriaList(HttpServletRequest request) {
		List<QueryCriteria> queryCriteriaList = new ArrayList<QueryCriteria>();
		String condition = request.getParameter("conditions");
		String value = request.getParameter("value");
		String[] conditions = new String[0];
		String[] values = new String[0];

		if ((condition != null && condition != "") && (value != null && value != "")) {
			conditions = condition.split("<next>");
			values = value.split("<next>");

			for (int i = 0; i < conditions.length; i++) {
				if (conditions[i] != null && !conditions[i].equals("")) {
					try {
						queryCriteriaList.add(new QueryCriteria(conditions[i], Oparator.EQUAL, Integer
								.parseInt(values[i])));
					} catch (NumberFormatException e) {
						queryCriteriaList.add(new QueryCriteria(conditions[i], Oparator.EQUAL, values[i]));
					}
				}
			}
		}

		return queryCriteriaList;
	}

	public List<QueryCriteria> getQueryCriteriaListWithoutNumbers(HttpServletRequest request) {
		List<QueryCriteria> queryCriteriaList = new ArrayList<QueryCriteria>();
		String condition = request.getParameter("conditions");
		String value = request.getParameter("value");
		String[] conditions = new String[0];
		String[] values = new String[0];

		if ((condition != null && condition != "") && (value != null && value != "")) {
			conditions = condition.split("<next>");
			values = value.split("<next>");

			for (int i = 0; i < conditions.length; i++) {
				if (conditions[i] != null && !conditions[i].equals("")) {
					queryCriteriaList.add(new QueryCriteria(conditions[i], Oparator.EQUAL, values[i]));
				}
			}
		}
		//queryCriteriaList.add(new QueryCriteria("recordStatus", Oparator.EQUAL, RecordStatusEnum.ACTIVE.getCode()));
		return queryCriteriaList;
	}

	/**
	 * Provides a way to pass params-value pairs to criteria with operators
	 * TO-DO: Not genaralized enough to support various data types
	 * 
	 * @param request
	 * @return List
	 */
	public List<QueryCriteria> getQueryCriteriaListWithoutAnything(HttpServletRequest request) {
		List<QueryCriteria> queryCriteriaList = new ArrayList<QueryCriteria>();
		String condition = request.getParameter("conditions");
		String value = request.getParameter("value");
		String operate = request.getParameter("operates");
		String[] conditions = new String[0];
		String[] values = new String[0];
		String[] operates = new String[0];

		if ((condition != null && condition != "") && (value != null && value != "")) {
			conditions = condition.split("<next>");
			values = value.split("<next>");
			operates = operate.split("<next>");

			for (int i = 0; i < conditions.length; i++) {
				if (conditions[i] != null && !conditions[i].equals("")) {
					if (operates.length > i) {
						if (operates[i].equals("nt")) {
							queryCriteriaList.add(new QueryCriteria(conditions[i], Oparator.NOT_EQUAL, values[i]));
						} else if (operates[i].equals("gr")) {
							queryCriteriaList.add(new QueryCriteria(conditions[i], Oparator.GRATERTHAN_OR_EQUAL,
									values[i]));
						} else if (operates[i].equals("le")) {
							queryCriteriaList.add(new QueryCriteria(conditions[i], Oparator.LESSTHAN_OR_EQUAL,
									new Date(values[i])));
						} else if (operates[i].equals("eq")) {
							queryCriteriaList.add(new QueryCriteria(conditions[i], Oparator.EQUAL, Integer
									.parseInt(values[i])));
						} else if (operates[i].equals("like")) {
								queryCriteriaList.add(new QueryCriteria(conditions[i], Oparator.LIKE, '%' +  values[i] + '%'));
						}
					} else {
						queryCriteriaList.add(new QueryCriteria(conditions[i], Oparator.EQUAL, Integer
								.parseInt(values[i])));
					}
				}
			}
		}
		queryCriteriaList.add(new QueryCriteria("recordStatus", Oparator.EQUAL, RecordStatusEnum.ACTIVE.getCode()));
		return queryCriteriaList;
	}

	protected void setOtherData(ActionForm form, HttpServletRequest request) {
	}

	protected abstract ActionForward getAuthorizeData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception;

	protected String getRecordStatusString(int status) {
		switch (status) {
		case 2:
			return "Create";
		case 3:
			return "Update";
		case 4:
			return "Delete";
		}
		return null;
	}

	private void createEventLog(ActionMapping mapping, HttpServletRequest request, int eventId) {
		UserConfig userConfig = SessionUtil.getUserSession(request);
		EventLog eventLog = new EventLog();
		eventLog.setEventId(eventId);
		eventLog.setProgrameName(mapping.getName());
		eventLog.setProductId(1);
		eventLog.setTransactionId(userConfig.getUserLogId());
		try {
			systemAuditService.createEventLog(userConfig, eventLog);
		} catch (PawnException ex) {
			System.out.println(ex.getMessage());
		}
	}
}

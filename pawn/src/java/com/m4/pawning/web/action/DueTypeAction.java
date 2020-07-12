package com.m4.pawning.web.action;

import java.util.ArrayList;
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
import com.m4.core.exception.PawnException;
import com.m4.core.util.MasterAction;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.RecordStatusEnum;
import com.m4.core.util.SessionUtil;
import com.m4.core.util.StrutsFormValidateUtil;
import com.m4.core.util.QueryCriteria.Oparator;
import com.m4.pawning.domain.DueType;
import com.m4.pawning.service.DueTypeService;

public class DueTypeAction extends MasterAction {
	private static final Logger logger = Logger.getLogger(DueTypeAction.class);
	private DueTypeService dueTypeService;

	public void setDueTypeService(DueTypeService dueTypeService) {
		this.dueTypeService = dueTypeService;
	}

	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to create method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if(!validateForm.isEmpty()){
        	response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
        }else{
        	DueType dueType = new DueType();
        	dueType.setDueType(request.getParameter("code"));
        	dueType.setDescription(request.getParameter("description"));
        	dueType.setSequence(Integer.parseInt(request.getParameter("sequenceno")));
        	dueType.setIsReceipt(request.getParameter("receiptpayment"));
        	dueType.setIsODChargable(request.getParameter("odinterest"));
        	dueType.setIsInternal(request.getParameter("interexter"));
        	dueType.setAccountNumber(request.getParameter("accountno"));
        	dueType.setProductId(1);

        	try {
        		dueTypeService.createDueType(SessionUtil.getUserSession(request), dueType);
        		response.getWriter().write(StrutsFormValidateUtil.getMessageCreateSuccess(messageResources).toString());
			} catch (PawnException ex) {
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
			}
        }

		logger.debug("**** Leaving from create method *****");
		return null;
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to update method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if(!validateForm.isEmpty()){
        	response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
        }else{
        	DueType dueType = new DueType();
        	dueType.setDueType(request.getParameter("code"));
        	dueType.setDescription(request.getParameter("description"));
        	dueType.setSequence(Integer.parseInt(request.getParameter("sequenceno")));
        	dueType.setIsReceipt(request.getParameter("receiptpayment"));
        	dueType.setIsODChargable(request.getParameter("odinterest"));
        	dueType.setIsInternal(request.getParameter("interexter"));
        	dueType.setAccountNumber(request.getParameter("accountno"));
        	dueType.setRecordId(Integer.parseInt(request.getParameter("recordId")));
        	dueType.setVersion(Integer.parseInt(request.getParameter("version")));

        	try {
        		dueTypeService.updateDueType(SessionUtil.getUserSession(request), dueType);
        		response.getWriter().write(StrutsFormValidateUtil.getMessageUpdateSuccess(messageResources).toString());
			} catch (PawnException ex) {
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
			}
        }

		logger.debug("**** Leaving from update method *****");
		return null;
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to remove method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if(!validateForm.isEmpty()){
        	response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
        }else{
        	DueType dueType = new DueType();
        	dueType.setRecordId(Integer.parseInt(request.getParameter("recordId")));
        	dueType.setVersion(Integer.parseInt(request.getParameter("version")));

        	try {
        		dueTypeService.removeDueType(SessionUtil.getUserSession(request), dueType);
        		response.getWriter().write(StrutsFormValidateUtil.getMessageDeleteSuccess(messageResources).toString());
			} catch (PawnException ex) {
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
			}
        }

		logger.debug("**** Leaving from remove method *****");
		return null;
	}

	public ActionForward authorize(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to authorize method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		String recordId = request.getParameter("recordId");
        String version  = request.getParameter("version");
        String authorizeType   = request.getParameter("authorizeMode");
        boolean authorize      = Boolean.parseBoolean(request.getParameter("authorizeValue"));

        if((recordId==null)||(version==null)||(recordId.equals(""))||(version.equals(""))){
        	response.getWriter().write(StrutsFormValidateUtil.getMessageNotFound(messageResources).toString());
        	return null;
        }

        DueType dueType = new DueType();
    	dueType.setRecordId(Integer.parseInt(request.getParameter("recordId")));
    	dueType.setVersion(Integer.parseInt(request.getParameter("version")));

        try{
        	if(authorizeType.equals("Create") ){
        		dueTypeService.authorizeCreation(SessionUtil.getUserSession(request), dueType, authorize);
        	}else if( authorizeType.equals("Update") ){
        		dueTypeService.authorizeUpdation(SessionUtil.getUserSession(request), dueType, authorize);
        	}else if( authorizeType.equals("Delete") ){
        		dueTypeService.authorizeDeletion(SessionUtil.getUserSession(request), dueType, authorize);
	    	}
        }catch(PawnException ex){
	    	logger.error("exception in authorization" + ex.getExceptionCode());
	    	response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, getLocale(request)).toString());
	    	return null;
        }

        if(authorize==true){
        	response.getWriter().write(StrutsFormValidateUtil.getMessageAuthorizeSuccess(messageResources).toString());
        }else{
        	response.getWriter().write(StrutsFormValidateUtil.getMessageRejectSuccess(messageResources).toString());
        }

        logger.debug("**** Leaving from authorize method *****");
		return null;
	}

	public ActionForward getAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to getGridData method *****");
		String recordId = request.getParameter("recordId");

		JSONArray mainArray = new JSONArray();

		if(recordId!=null && recordId!=""){
			DueType dueType = dueTypeService.getDueTypeById(SessionUtil.getUserSession(request), Integer.parseInt(recordId));
			mainArray.put(dueType.getDueType());
			mainArray.put(dueType.getDescription());
			mainArray.put(dueType.getSequence());
			mainArray.put(dueType.getRecordId());
			mainArray.put(dueType.getVersion());			
			mainArray.put(dueType.getIsReceipt());
			mainArray.put(dueType.getIsODChargable());
			mainArray.put(dueType.getIsInternal());
			mainArray.put(dueType.getAccountNumber());
		}else{
			List<DueType> list = (List<DueType>)dueTypeService.getAllDueType(SessionUtil.getUserSession(request), getQueryCriteriaList(request)).getDataList();
			for(DueType dueType:list){
				JSONArray subArray = new JSONArray();
				subArray.put(dueType.getDueType());
				subArray.put(dueType.getDescription());
				subArray.put(dueType.getSequence());
				subArray.put(dueType.getRecordId());
				subArray.put(dueType.getVersion());				
				subArray.put(dueType.getIsReceipt());
				subArray.put(dueType.getIsODChargable());
				subArray.put(dueType.getIsInternal());
				subArray.put(dueType.getAccountNumber());

				mainArray.put(subArray);
			}
		}

		response.getWriter().write(mainArray.toString());
		logger.debug("**** Leaving from getGridData method *****");
		return null;
	}

	@Override
	public ActionForward getAuthorizeData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONArray mainArray = new JSONArray();

		List<QueryCriteria> criteriaList = getAuthorizeQueryCriteriaList(request);
		criteriaList.add(new QueryCriteria("recordStatus",Oparator.GRATERTHAN,RecordStatusEnum.ACTIVE.getCode()));
		List<DueType> list = (List<DueType>)dueTypeService.getAllDueType(SessionUtil.getUserSession(request), criteriaList).getDataList();

		for(DueType dueType:list){
			JSONArray subArray = new JSONArray();
			
			subArray.put(dueType.getDueType());
			subArray.put(dueType.getDescription());
			subArray.put(dueType.getSequence());
			subArray.put(getRecordStatusString(dueType.getRecordStatus()));
			subArray.put(dueType.getRecordId());
			subArray.put(dueType.getVersion());				
			subArray.put(dueType.getIsReceipt());
			subArray.put(dueType.getIsODChargable());
			subArray.put(dueType.getIsInternal());
			subArray.put(dueType.getAccountNumber());

			mainArray.put(subArray);
		}

		response.getWriter().write(mainArray.toString());
		return null;
	}
}

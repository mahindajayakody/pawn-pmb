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
import com.m4.pawning.domain.CartageMarster;
import com.m4.pawning.service.CartageMarsterService;

public class CartageMarsterAction extends MasterAction {
	private static final Logger logger = Logger.getLogger(CartageMarsterAction.class);
	private CartageMarsterService cartageMarsterService;

	public void setCartageMarsterService(CartageMarsterService cartageMarsterService) {
		this.cartageMarsterService = cartageMarsterService;
	}

	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to create method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if(!validateForm.isEmpty()){
        	response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
        }else{
        	CartageMarster cartageMarster = new CartageMarster();
        	cartageMarster.setCode(request.getParameter("code"));
        	cartageMarster.setDescription(request.getParameter("description"));

        	try {
        		cartageMarsterService.createCartageMarster(SessionUtil.getUserSession(request), cartageMarster);
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
        	CartageMarster cartageMarster = new CartageMarster();
        	cartageMarster.setCode(request.getParameter("code"));
        	cartageMarster.setDescription(request.getParameter("description"));
        	cartageMarster.setRecordId(Integer.parseInt(request.getParameter("recordId")));
        	cartageMarster.setVersion(Integer.parseInt(request.getParameter("version")));

        	try {
        		cartageMarsterService.updateCartageMarster(SessionUtil.getUserSession(request), cartageMarster);
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
        	CartageMarster cartageMarster = new CartageMarster();
        	cartageMarster.setRecordId(Integer.parseInt(request.getParameter("recordId")));
        	cartageMarster.setVersion(Integer.parseInt(request.getParameter("version")));

        	try {
        		cartageMarsterService.removeCartageMarster(SessionUtil.getUserSession(request), cartageMarster);
        		response.getWriter().write(StrutsFormValidateUtil.getMessageDeleteSuccess(messageResources).toString());
			} catch (PawnException ex) {
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
			}
        }

		logger.debug("**** Leaving from remove method *****");
		return null;
	}

	public ActionForward authorize(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to remove method *****");
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

        CartageMarster cartageMarster = new CartageMarster();
    	cartageMarster.setRecordId(Integer.parseInt(request.getParameter("recordId")));
    	cartageMarster.setVersion(Integer.parseInt(request.getParameter("version")));

        try{
        	if(authorizeType.equals("Create") ){
        		cartageMarsterService.authorizeCreation(SessionUtil.getUserSession(request), cartageMarster, authorize);
        	}else if( authorizeType.equals("Update") ){
        		cartageMarsterService.authorizeUpdation(SessionUtil.getUserSession(request), cartageMarster, authorize);
        	}else if( authorizeType.equals("Delete") ){
        		cartageMarsterService.authorizeDeletion(SessionUtil.getUserSession(request), cartageMarster, authorize);
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

		return null;
	}

	public ActionForward getAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to getGridData method *****");
		String recordId = request.getParameter("recordId");

		JSONArray mainArray = new JSONArray();

		if(recordId!=null && recordId!=""){
			CartageMarster cartageMarster = cartageMarsterService.getCartageMarsterById(SessionUtil.getUserSession(request), Integer.parseInt(recordId));
			mainArray.put(cartageMarster.getCode());
			mainArray.put(cartageMarster.getDescription());
			mainArray.put(cartageMarster.getRecordId());
			mainArray.put(cartageMarster.getVersion());
		}else{
			List<CartageMarster> list = (List<CartageMarster>)cartageMarsterService.getAllCartageMarster(SessionUtil.getUserSession(request), getQueryCriteriaList(request)).getDataList();
			for(CartageMarster status:list){
				JSONArray subArray = new JSONArray();
				subArray.put(status.getCode());
				subArray.put(status.getDescription());
				subArray.put(status.getRecordId());
				subArray.put(status.getVersion());

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
		List<CartageMarster> list = (List<CartageMarster>)cartageMarsterService.getAllCartageMarster(SessionUtil.getUserSession(request),criteriaList).getDataList();

		for(CartageMarster status:list){
			JSONArray subArray = new JSONArray();
			subArray.put(status.getCode());
			subArray.put(status.getDescription());
			subArray.put(getRecordStatusString(status.getRecordStatus()));
			subArray.put(status.getRecordId());
			subArray.put(status.getVersion());

			mainArray.put(subArray);
		}

		response.getWriter().write(mainArray.toString());
		return null;
	}

}

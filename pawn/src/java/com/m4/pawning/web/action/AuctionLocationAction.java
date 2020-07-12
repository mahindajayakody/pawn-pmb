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
import com.m4.pawning.domain.AuctionLocation;
import com.m4.pawning.domain.GlAccount;
import com.m4.pawning.service.AuctionLocationService;

public class AuctionLocationAction extends MasterAction {
	private static final Logger logger=Logger.getLogger(AuctionLocationService.class);
	private AuctionLocationService auctionLocationService;

	public void setAuctionLocationService(AuctionLocationService auctionLocationService){
		this.auctionLocationService = auctionLocationService;
	}

	public ActionForward create (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to create method *****");
		ActionMessages validateForm=form.validate(mapping, request);
		MessageResources messageResources=getResources(request, "message");

		if(!validateForm.isEmpty()){
			response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm, messageResources, request.getLocale(), null).toString());
		}else{
			AuctionLocation auctionLocation=new AuctionLocation();
			auctionLocation.setCode(request.getParameter("code"));
			auctionLocation.setDescription(request.getParameter("description"));

			try{
				auctionLocationService.createAuctionLocation(SessionUtil.getUserSession(request), auctionLocation);
				response.getWriter().write(StrutsFormValidateUtil.getMessageCreateSuccess(messageResources).toString());
			}catch(PawnException ex){
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
			}
		}

		logger.debug("**** Leaving create method *****");
		return null;
	}

	public ActionForward update (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to update method *****");
		ActionMessages validateForm=form.validate(mapping, request);
		MessageResources messageResources=getResources(request, "message");

		if(!validateForm.isEmpty()){
			response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm, messageResources, request.getLocale(), null).toString());
		}else{
			AuctionLocation auctionLocation=new AuctionLocation();
			auctionLocation.setCode(request.getParameter("code"));
			auctionLocation.setDescription(request.getParameter("description"));
			auctionLocation.setRecordId(Integer.parseInt(request.getParameter("recordId")));
			auctionLocation.setVersion(Integer.parseInt(request.getParameter("version")));
			try{
				auctionLocationService.updateAuctionLocation(SessionUtil.getUserSession(request), auctionLocation);
				response.getWriter().write(StrutsFormValidateUtil.getMessageUpdateSuccess(messageResources).toString());
			}catch(PawnException ex){
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
			}
		}
		logger.debug("**** Leaving update method *****");
		return null;
	}

	public ActionForward remove(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to remove method *****");
		ActionMessages validateForm=form.validate(mapping, request);
		MessageResources messageResources=getResources(request, "message");

		if(!validateForm.isEmpty()){
			response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm, messageResources, request.getLocale(), null).toString());
		}else{
			AuctionLocation auctionLocation=new AuctionLocation();
			auctionLocation.setRecordId(Integer.parseInt(request.getParameter("recordId")));
			auctionLocation.setVersion(Integer.parseInt(request.getParameter("version")));
			try{
				auctionLocationService.removeAuctionLocation(SessionUtil.getUserSession(request), auctionLocation);
				response.getWriter().write(StrutsFormValidateUtil.getMessageDeleteSuccess(messageResources).toString());
			}catch(PawnException ex){
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
			}
		}
		logger.debug("**** Leaving remove method *****");
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

        AuctionLocation auctionLocation = new AuctionLocation();
		auctionLocation.setRecordId(Integer.parseInt(request.getParameter("recordId")));
		auctionLocation.setVersion(Integer.parseInt(request.getParameter("version")));

        try{
        	if(authorizeType.equals("Create") ){
        		auctionLocationService.authorizeCreation(SessionUtil.getUserSession(request), auctionLocation, authorize);
        	}else if( authorizeType.equals("Update") ){
        		auctionLocationService.authorizeUpdation(SessionUtil.getUserSession(request), auctionLocation, authorize);
        	}else if( authorizeType.equals("Delete") ){
        		auctionLocationService.authorizeDeletion(SessionUtil.getUserSession(request), auctionLocation, authorize);
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
		String recordId=request.getParameter("recordId");

		JSONArray mainArray = new JSONArray();

		if(recordId!=null && recordId!=""){
			AuctionLocation auctionLocation = auctionLocationService.getAuctionLocationById(SessionUtil.getUserSession(request), Integer.parseInt(recordId));
			mainArray.put(auctionLocation.getCode());
			mainArray.put(auctionLocation.getDescription());
			mainArray.put(auctionLocation.getRecordId());
			mainArray.put(auctionLocation.getVersion());
		}else{
			List<AuctionLocation> list=(List<AuctionLocation>) auctionLocationService.getAllAuctionLocation(SessionUtil.getUserSession(request), getQueryCriteriaList(request)).getDataList();

			for (AuctionLocation auctionLocation:list){
				JSONArray subArray=new JSONArray();
				subArray.put(auctionLocation.getCode());
				subArray.put(auctionLocation.getDescription());
				subArray.put(auctionLocation.getRecordId());
				subArray.put(auctionLocation.getVersion());
				mainArray.put(subArray);
			}
		}

		response.getWriter().write(mainArray.toString());
		logger.debug("**** Leaving getGridData method *****");
		return null;
	}

	@Override
	public ActionForward getAuthorizeData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONArray mainArray = new JSONArray();
		List<QueryCriteria> criteriaList = getAuthorizeQueryCriteriaList(request);
		criteriaList.add(new QueryCriteria("recordStatus",Oparator.GRATERTHAN,RecordStatusEnum.ACTIVE.getCode()));
		List<AuctionLocation> list=(List<AuctionLocation>) auctionLocationService.getAllAuctionLocation(SessionUtil.getUserSession(request), criteriaList).getDataList();

		for (AuctionLocation auctionLocation:list){
			JSONArray subArray=new JSONArray();
			subArray.put(auctionLocation.getCode());
			subArray.put(auctionLocation.getDescription());
			subArray.put(getRecordStatusString(auctionLocation.getRecordStatus()));
			subArray.put(auctionLocation.getRecordId());
			subArray.put(auctionLocation.getVersion());
			mainArray.put(subArray);
		}

		response.getWriter().write(mainArray.toString());
		return null;
	}
}

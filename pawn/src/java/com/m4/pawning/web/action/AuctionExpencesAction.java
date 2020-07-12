package com.m4.pawning.web.action;


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
import com.m4.pawning.domain.AuctionExpences;
import com.m4.pawning.domain.DueType;
import com.m4.pawning.domain.Product;
import com.m4.pawning.service.AuctionExpencesService;
import com.m4.pawning.service.AuctionService;
import com.m4.pawning.service.DueTypeService;
import com.m4.pawning.service.ProductService;

public class AuctionExpencesAction extends MasterAction {
	private static final Logger logger= Logger.getLogger(AuctionExpences.class);
	private AuctionExpencesService auctionExpencesService;
	private ProductService productService;
	private DueTypeService dueTypeService;
	private AuctionService auctionService;	
	
	public void setAuctionService(AuctionService auctionService) {
		this.auctionService = auctionService;
	}

	public void setDueTypeService(DueTypeService dueTypeService) {
		this.dueTypeService = dueTypeService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public void setAuctionExpencesService(
			AuctionExpencesService auctionExpencesService) {
		this.auctionExpencesService = auctionExpencesService;
	}
	
	public ActionForward create(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to create method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if (!validateForm.isEmpty())
			response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm, messageResources, getLocale(request), null).toString());
		else{
			AuctionExpences auctionExpences=new AuctionExpences();
			auctionExpences.setAuctionId(Integer.parseInt(request.getParameter("auctionId")));
			//auctionExpences.setProductId(Integer.parseInt(request.getParameter("productId")));
			auctionExpences.setDuetypeId(Integer.parseInt(request.getParameter("dueTypeId")));
			auctionExpences.setAmount(Double.parseDouble(request.getParameter("amount")));	
		
			try{
				auctionExpencesService.createAuctionExpences(getUserSession(request), auctionExpences);
				response.getWriter().write(StrutsFormValidateUtil.getMessageCreateSuccess(messageResources).toString());
				
			}catch (PawnException ex){
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, getLocale(request)).toString());
			}
		}
		logger.debug("**** Leaving create method *****");
		return null;
	}
	
	public ActionForward update(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to update method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if (!validateForm.isEmpty())
			response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm, messageResources, getLocale(request), null).toString());
		else{
			AuctionExpences auctionExpences=new AuctionExpences();
			auctionExpences.setAuctionId(Integer.parseInt(request.getParameter("auctionId")));
			auctionExpences.setDuetypeId(Integer.parseInt(request.getParameter("dueTypeId")));
			auctionExpences.setAmount(Double.parseDouble(request.getParameter("amount")));	
			auctionExpences.setVersion(Integer.parseInt(request.getParameter("version")));
			auctionExpences.setRecordId(Integer.parseInt(request.getParameter("recordId")));

		
			try{
				auctionExpencesService.updateAuctionExpences(getUserSession(request), auctionExpences);
				response.getWriter().write(StrutsFormValidateUtil.getMessageUpdateSuccess(messageResources).toString());
				
			}catch (PawnException ex){
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, getLocale(request)).toString());
			}
		}
		logger.debug("**** Leaving update method *****");
		return null;
	}
	public ActionForward remove(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to remove method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if (!validateForm.isEmpty())
			response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm, messageResources, getLocale(request), null).toString());
		else{
			AuctionExpences auctionExpences=new AuctionExpences();
			auctionExpences.setAmount(Double.parseDouble(request.getParameter("amount")));
			auctionExpences.setVersion(Integer.parseInt(request.getParameter("version")));
			auctionExpences.setRecordId(Integer.parseInt(request.getParameter("recordId")));
			try{
				auctionExpencesService.removeAuctionExpences(getUserSession(request), auctionExpences);
				response.getWriter().write(StrutsFormValidateUtil.getMessageDeleteSuccess(messageResources).toString());
				
			}catch (PawnException ex){
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, getLocale(request)).toString());
			}
		}
		logger.debug("**** Leaving remove method *****");
		return null;
	}
	
	public ActionForward authorize(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to authorize method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		String recordId = request.getParameter("recordId");
        String version  = request.getParameter("version");
        String authorizeType   = request.getParameter("authorizeMode");
        boolean authorize = Boolean.parseBoolean(request.getParameter("authorizeValue"));

        if((recordId==null)||(version==null)||(recordId.equals(""))||(version.equals(""))){
        	response.getWriter().write(StrutsFormValidateUtil.getMessageNotFound(messageResources).toString());
        	return null;
        }

		AuctionExpences auctionExpences=new AuctionExpences();
		auctionExpences.setVersion(Integer.parseInt(request.getParameter("version")));
		auctionExpences.setRecordId(Integer.parseInt(request.getParameter("recordId")));

        try{
        	if(authorizeType.equals("Create") ){
        		auctionExpencesService.authorizeCreation(SessionUtil.getUserSession(request), auctionExpences, authorize);
        	}else if( authorizeType.equals("Update") ){
        		auctionExpencesService.authorizeUpdation(SessionUtil.getUserSession(request), auctionExpences, authorize);
        	}else if( authorizeType.equals("Delete") ){
        		auctionExpencesService.authorizeDeletion(SessionUtil.getUserSession(request), auctionExpences, authorize);
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
		logger.debug("**** Enter in to getAjaxData method *****");
		String recordId = request.getParameter("recordId");

		JSONArray mainArray = new JSONArray();

		if(recordId!=null && recordId!="" && recordId!="0"){
			AuctionExpences auctionExpences=auctionExpencesService.getAuctionExpencesByAuctionId(SessionUtil.getUserSession(request), Integer.parseInt(recordId));
			//Product product = productService.getProductById(getUserSession(request), auctionExpences.getProductId());
			DueType dueType=dueTypeService.getDueTypeById(getUserSession(request),auctionExpences.getDuetypeId());

			mainArray.put(dueType.getDueType());
			mainArray.put(dueType.getDescription());
			mainArray.put(auctionExpences.getAmount());
			mainArray.put(dueType.getDueTypeId());
			mainArray.put(auctionExpences.getAuctionExcpenceId());
			mainArray.put(auctionExpences.getBranchId());
			mainArray.put(auctionExpences.getRecordId());
			mainArray.put(auctionExpences.getVersion());
		}else{
			List<AuctionExpences> auctionExpencesList = (List<AuctionExpences>)auctionExpencesService.getAllAuctionExpences(getUserSession(request), getQueryCriteriaList(request)).getDataList();
			for(AuctionExpences expence:auctionExpencesList){
				JSONArray subArray = new JSONArray();
				AuctionExpences auctionExpences=auctionExpencesService.getAuctionExpencesById(SessionUtil.getUserSession(request), expence.getAuctionExcpenceId());
				DueType dueType=dueTypeService.getDueTypeById(getUserSession(request),auctionExpences.getDuetypeId());

				subArray.put(dueType.getDueType());
				subArray.put(dueType.getDescription());
				subArray.put(expence.getAmount());
				subArray.put(dueType.getDueTypeId());
				subArray.put(expence.getAuctionExcpenceId());
				subArray.put(expence.getBranchId());
				subArray.put(expence.getRecordId());
				subArray.put(expence.getVersion());
				mainArray.put(subArray);
			}
		}

		response.getWriter().write(mainArray.toString());
		logger.debug("**** Leaving from getAjaxData method *****");
		return null;

	}

	public ActionForward getAuthorizeData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONArray mainArray = new JSONArray();

		List<QueryCriteria> criteriaList = getAuthorizeQueryCriteriaList(request);
		criteriaList.add(new QueryCriteria("recordStatus",Oparator.GRATERTHAN,RecordStatusEnum.ACTIVE.getCode()));
		List<AuctionExpences> auctionExpencesList = (List<AuctionExpences>)auctionExpencesService.getAllAuctionExpences(getUserSession(request), criteriaList).getDataList();
		for(AuctionExpences expence:auctionExpencesList){
			JSONArray subArray = new JSONArray();
			
			subArray.put(expence.getAmount());
			subArray.put(expence.getAuctionExcpenceId());
			subArray.put(getRecordStatusString(expence.getRecordStatus()));
			subArray.put(expence.getBranchId());
			subArray.put(expence.getRecordId());
			subArray.put(expence.getVersion());
			mainArray.put(subArray);
		}
		response.getWriter().write(mainArray.toString());
		return null;
	}
	

}

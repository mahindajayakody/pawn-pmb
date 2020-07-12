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
import com.m4.pawning.domain.Cartage;
import com.m4.pawning.domain.CartageMarster;
import com.m4.pawning.domain.Product;
import com.m4.pawning.service.CartageMarsterService;
import com.m4.pawning.service.CartageService;
import com.m4.pawning.service.ProductService;

public class CartageAction extends MasterAction {
	Logger logger =Logger.getLogger(CartageAction.class);
	private CartageMarsterService cartageMarsterService;
	private ProductService productService;
	private CartageService cartageService;

	public void setCartageMarsterService(CartageMarsterService cartageMarsterService) {
		this.cartageMarsterService = cartageMarsterService;
	}
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	public void setCartageService(CartageService cartageService) {
		this.cartageService = cartageService;
	}

	public ActionForward create(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.debug("**** Enter In to create method ****");

		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if(!validateForm.isEmpty()){
        	response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
        }else{
        	Cartage cartage=new Cartage();
        	cartage.setCode(request.getParameter("code"));
        	cartage.setDescription(request.getParameter("description"));
        	cartage.setDisbursePercentage(Double.parseDouble(request.getParameter("disbursePercentage")));
        	cartage.setDisburseValue(Double.parseDouble(request.getParameter("disburseValue")));
        	cartage.setMarcketValue(Double.parseDouble(request.getParameter("marcketValue")));
        	cartage.setMasterCartageId(Integer.parseInt(request.getParameter("masterCartageId")));
        	cartage.setDisplayValue(request.getParameter("displayValue"));
        	cartage.setProductId(Integer.parseInt(request.getParameter("productId")));
        	cartage.setIsActive(request.getParameter("isActive"));

        	try{
        		cartageService.createCartage(SessionUtil.getUserSession(request), cartage);
        		response.getWriter().write(StrutsFormValidateUtil.getMessageCreateSuccess(messageResources).toString());
        	}catch(PawnException ex){
        		response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
        	}
        }
		logger.debug("**** Leaving from create method ****");
		return null;
	}
	public ActionForward update(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.debug("**** Enter In to update method ****");

		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if(!validateForm.isEmpty()){
        	response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
        }else{
        	Cartage cartage=new Cartage();
        	cartage.setCode(request.getParameter("code"));
        	cartage.setDescription(request.getParameter("description"));
        	cartage.setDisbursePercentage(Double.parseDouble(request.getParameter("disbursePercentage")));
        	cartage.setDisburseValue(Double.parseDouble(request.getParameter("disburseValue")));
        	cartage.setMarcketValue(Double.parseDouble(request.getParameter("marcketValue")));
        	cartage.setMasterCartageId(Integer.parseInt(request.getParameter("masterCartageId")));
        	cartage.setIsActive(request.getParameter("isActive"));
        	cartage.setDisplayValue(request.getParameter("displayValue"));
        	cartage.setRecordId(Integer.parseInt(request.getParameter("recordId")));
        	cartage.setVersion(Integer.parseInt(request.getParameter("version")));
        	cartage.setProductId(Integer.parseInt(request.getParameter("productId")));
        	//cartage.setCompanyId(Integer.parseInt(request.getParameter("companyId")));

        	try{
        		cartageService.updateCartage(SessionUtil.getUserSession(request), cartage);
        		response.getWriter().write(StrutsFormValidateUtil.getMessageUpdateSuccess(messageResources).toString());
        	}catch(PawnException ex){
        		response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
        	}
        }
		logger.debug("**** Leaving from update method ****");
		return null;
	}
	public ActionForward remove(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.debug("**** Enter In to remove method ****");

		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if(!validateForm.isEmpty()){
        	response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
        }else{
        	Cartage cartage=new Cartage();
        	cartage.setVersion(Integer.parseInt(request.getParameter("version")));
        	cartage.setRecordId(Integer.parseInt(request.getParameter("recordId")));

        	try {
        		cartageService.removeCartage(SessionUtil.getUserSession(request), cartage);
        		response.getWriter().write(StrutsFormValidateUtil.getMessageDeleteSuccess(messageResources).toString());
        	}catch(PawnException ex){
        		response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
        	}
        }
		logger.debug("**** Leaving from remove method ****");
		return null;
	}

	public ActionForward authorize(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to remove method ****");
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

        Cartage cartage = new Cartage();
    	cartage.setVersion(Integer.parseInt(request.getParameter("version")));
    	cartage.setRecordId(Integer.parseInt(request.getParameter("recordId")));

        try{
        	if(authorizeType.equals("Create") ){
        		cartageService.authorizeCreation(SessionUtil.getUserSession(request), cartage, authorize);
        	}else if( authorizeType.equals("Update") ){
        		cartageService.authorizeUpdation(SessionUtil.getUserSession(request), cartage, authorize);
        	}else if( authorizeType.equals("Delete") ){
        		cartageService.authorizeDeletion(SessionUtil.getUserSession(request), cartage, authorize);
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

        logger.debug("**** Leaving from authorize method ****");
		return null;
	}

	public ActionForward getAjaxData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.debug("***** Enter In to getAjaxData method *****");

		String recordId = request.getParameter("recordId");

		JSONArray mainArray=new JSONArray();

		if(recordId!=null && !recordId.equals("") && !recordId.equals("0")){
			Cartage cartage=cartageService.getCartageById(SessionUtil.getUserSession(request), Integer.parseInt(recordId));
			Product product= (Product) productService.getProductById(SessionUtil.getUserSession(request), cartage.getProductId());
			CartageMarster cartageMaster=cartageMarsterService.getCartageMarsterById(SessionUtil.getUserSession(request), cartage.getMasterCartageId());

			mainArray.put(cartage.getCode());
			mainArray.put(cartage.getDescription());
			mainArray.put(cartage.getDisburseValue());
			mainArray.put(cartage.getDisbursePercentage());
			
			mainArray.put(cartage.getDisplayValue());
			mainArray.put(cartage.getMarcketValue());
			mainArray.put(cartage.getIsActive());
			mainArray.put(product.getCode());
			mainArray.put(product.getDescription());
			mainArray.put(cartageMaster.getCode());
			mainArray.put(cartageMaster.getDescription());
			mainArray.put(cartage.getVersion());
			mainArray.put(cartage.getRecordId());
			mainArray.put(cartage.getProductId());
			mainArray.put(cartage.getMasterCartageId());
		}
		else{
			try{
				List<Cartage> list=(List<Cartage>) cartageService.getAllCartage(SessionUtil.getUserSession(request), getQueryCriteriaList(request)).getDataList();

				for (Cartage cart:list){
					Product product = productService.getProductById(SessionUtil.getUserSession(request), cart.getProductId());
					CartageMarster cartageMaster=cartageMarsterService.getCartageMarsterById(SessionUtil.getUserSession(request), cart.getMasterCartageId());
					JSONArray subArray=new JSONArray();
					subArray.put(cart.getCode());
					subArray.put(cart.getDescription());
					subArray.put(cart.getDisburseValue());
					subArray.put(cart.getDisbursePercentage());
					
					subArray.put(cart.getDisplayValue());
					subArray.put(cart.getMarcketValue());
					subArray.put(cart.getIsActive());
					subArray.put(product.getCode());
					subArray.put(product.getDescription());
					subArray.put(cartageMaster.getCode());
					subArray.put(cartageMaster.getDescription());
					subArray.put(cart.getVersion());
					subArray.put(cart.getRecordId());
					subArray.put(cart.getProductId());
					subArray.put(cart.getMasterCartageId());

					mainArray.put(subArray);
				}
			}catch(Exception ex){
				System.out.println(ex.getMessage());
			}
		}
		response.getWriter().write(mainArray.toString());
		logger.debug("**** Leaving from getAjaxData method ****");
		return null;
	}

	@Override
	public ActionForward getAuthorizeData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to getAuthorizeData method ****");
		JSONArray mainArray = new JSONArray();

		List<QueryCriteria> criteriaList = getAuthorizeQueryCriteriaList(request);
		criteriaList.add(new QueryCriteria("recordStatus",Oparator.GRATERTHAN,RecordStatusEnum.ACTIVE.getCode()));
		List<Cartage> list=(List<Cartage>) cartageService.getAllCartage(SessionUtil.getUserSession(request), criteriaList).getDataList();

		for (Cartage cart:list){
			Product product = productService.getProductById(SessionUtil.getUserSession(request), cart.getProductId());
			CartageMarster cartageMaster=cartageMarsterService.getCartageMarsterById(SessionUtil.getUserSession(request), cart.getMasterCartageId());
			JSONArray subArray=new JSONArray();

		//	subArray.put(cart.getCode());
		//	subArray.put(cart.getDescription());
		//	subArray.put(product.getCode());
		//	subArray.put(cartageMaster.getCode());
		//	subArray.put(getRecordStatusString(cart.getRecordStatus()));
		//	subArray.put(cart.getVersion());
		//	subArray.put(cart.getRecordId());
			
			
			subArray.put(cart.getCode());
			subArray.put(cart.getDescription());
			subArray.put(cart.getDisburseValue());
			subArray.put(cart.getDisbursePercentage());
			subArray.put(cart.getDisplayValue());
			subArray.put(cart.getMarcketValue());
			subArray.put(getRecordStatusString(cart.getRecordStatus()));
			subArray.put(cart.getIsActive());
			subArray.put(product.getCode());
			subArray.put(product.getDescription());
			subArray.put(cartageMaster.getCode());
			subArray.put(cartageMaster.getDescription());
			subArray.put(cart.getVersion());
			subArray.put(cart.getRecordId());
			subArray.put(cart.getProductId());
			subArray.put(cart.getMasterCartageId());			
			mainArray.put(subArray);
		}

		response.getWriter().write(mainArray.toString());
		logger.debug("**** Leaving from getAuthorizeData method ****");
		return null;
	}
	
	public ActionForward getProduct(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		MessageResources messageResources = getResources(request,"message");
    	Product product = null;
 		String code   = request.getParameter("code");

 		try {
 			product = productService.getProductByCode(SessionUtil.getUserSession(request), code);
 		}catch(PawnException ex){
			response.getWriter().write(StrutsFormValidateUtil.getAJAXErrorMessage(ex, messageResources, request.getLocale()));
			return null;
 		}
 		response.getWriter().write(StrutsFormValidateUtil.getAJAXReferenceData(product.getRecordId(),product.getDescription()));
		return null;
	}
	
	public ActionForward getCartageMaster(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		MessageResources messageResources = getResources(request,"message");
    	CartageMarster marster = null;
 		String code   = request.getParameter("code");

 		try {
 			marster = cartageMarsterService.getCartageMarsterByCode(SessionUtil.getUserSession(request), code);
 		}catch(PawnException ex){
			response.getWriter().write(StrutsFormValidateUtil.getAJAXErrorMessage(ex, messageResources, request.getLocale()));
			return null;
 		}
 		response.getWriter().write(StrutsFormValidateUtil.getAJAXReferenceData(marster.getRecordId(),marster.getDescription()));
		return null;
	}
}

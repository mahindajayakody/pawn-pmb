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
import com.m4.pawning.domain.CartageMarster;
import com.m4.pawning.domain.InterestRates;
import com.m4.pawning.domain.Product;
import com.m4.pawning.domain.Schemes;
import com.m4.pawning.service.InterestRatesService;
import com.m4.pawning.service.ProductService;
import com.m4.pawning.service.CartageMarsterService;
import com.m4.pawning.service.SchemeService;


public class SchemeAction extends MasterAction {
	private static final Logger logger = Logger.getLogger(SchemeAction.class);
	private SchemeService schemeService;
	private ProductService productService;
	private CartageMarsterService cartageMarsterService;
	private InterestRatesService interestRatesService;

	public void setSchemeService(SchemeService schemeService) {
		this.schemeService = schemeService;
	}
	public void setCartageMarsterService(CartageMarsterService cartageMarsterService) {
		this.cartageMarsterService = cartageMarsterService;
	}
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	public void setInterestRatesService(InterestRatesService interestRatesService) {
		this.interestRatesService = interestRatesService;
	}

	public ActionForward create(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to create method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if(!validateForm.isEmpty()){
        	response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
        }else{
        	Schemes schemes = new Schemes();
        	schemes.setCode(request.getParameter("code"));
        	schemes.setDescription(request.getParameter("description"));
        	schemes.setProductId(Integer.parseInt(request.getParameter("productId")));
        	schemes.setCartageMarsterId(Integer.parseInt(request.getParameter("cartageMarsterId")));
        	schemes.setInterestId(Integer.parseInt(request.getParameter("interestId")));
        	schemes.setInterestCode(request.getParameter("interestCode"));
        	schemes.setPeriod(Integer.parseInt(request.getParameter("period")));
        	schemes.setIsActive(Integer.parseInt(request.getParameter("isActive")));
        	schemes.setBranchId(SessionUtil.getUserSession(request).getBranchId());
        	schemes.setCompanyId(SessionUtil.getUserSession(request).getCompanyId());
        	try {
        		schemeService.createScheme(SessionUtil.getUserSession(request), schemes);
        		response.getWriter().write(StrutsFormValidateUtil.getMessageCreateSuccess(messageResources).toString());
			} catch (PawnException ex) {
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
			}
        }

		logger.debug("**** Leaving from create method *****");

		return null;

	}

	public ActionForward update (ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to update method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if(!validateForm.isEmpty()){
        	response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
        }else{
        	Schemes schemes = new Schemes();
        	schemes.setCode(request.getParameter("code"));
        	schemes.setDescription(request.getParameter("description"));
        	schemes.setProductId(Integer.parseInt(request.getParameter("productId")));
        	schemes.setCartageMarsterId(Integer.parseInt(request.getParameter("cartageMarsterId")));
        	schemes.setInterestId(Integer.parseInt(request.getParameter("interestId")));
        	schemes.setInterestCode(request.getParameter("interestCode"));
        	schemes.setPeriod(Integer.parseInt(request.getParameter("period")));
        	schemes.setIsActive(Integer.parseInt(request.getParameter("isActive")));
        	schemes.setRecordId(Integer.parseInt(request.getParameter("recordId")));
        	schemes.setVersion(Integer.parseInt(request.getParameter("version")));
        	try {
        		schemeService.updateScheme(SessionUtil.getUserSession(request), schemes);
        		response.getWriter().write(StrutsFormValidateUtil.getMessageUpdateSuccess(messageResources).toString());
			} catch (PawnException ex) {
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
			}
        }
		logger.debug("**** Leaving from update method *****");
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

        Schemes schemes = new Schemes();
    	schemes.setRecordId(Integer.parseInt(request.getParameter("recordId")));
    	schemes.setVersion(Integer.parseInt(request.getParameter("version")));

        try{
        	if(authorizeType.equals("Create") ){
        		schemeService.authorizeCreation(SessionUtil.getUserSession(request), schemes, authorize);
        	}else if( authorizeType.equals("Update") ){
        		schemeService.authorizeUpdation(SessionUtil.getUserSession(request), schemes, authorize);
        	}else if( authorizeType.equals("Delete") ){
        		schemeService.authorizeDeletion(SessionUtil.getUserSession(request), schemes, authorize);
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
		if(recordId!=null && recordId!="" && recordId!="0"){
			Schemes schemes = schemeService.getSchemeById(SessionUtil.getUserSession(request), Integer.parseInt(recordId));
			Product product = productService.getProductById(SessionUtil.getUserSession(request), schemes.getProductId());
			CartageMarster cartageMarster= cartageMarsterService.getCartageMarsterById(SessionUtil.getUserSession(request), schemes.getCartageMarsterId());
			InterestRates  interestRates = interestRatesService.getInterestRateById(SessionUtil.getUserSession(request), schemes.getInterestId());

			mainArray.put(schemes.getCode());
			mainArray.put(schemes.getDescription());

			mainArray.put(product.getProductId());
			mainArray.put(product.getCode());
			mainArray.put(product.getDescription());

			mainArray.put(cartageMarster.getCartageMarsterId());
			mainArray.put(cartageMarster.getCode());
			mainArray.put(cartageMarster.getDescription());

			mainArray.put(schemes.getIsActive());
			mainArray.put(schemes.getRecordId());
			mainArray.put(schemes.getVersion());

			mainArray.put(interestRates.getInterestRateId());
			mainArray.put(interestRates.getCode());
			mainArray.put(interestRates.getDescription());

			mainArray.put(schemes.getPeriod());
			mainArray.put(schemes.getInterestCode());
			mainArray.put(schemes.getInterestId());
			mainArray.put(schemes.getCartageMarsterId());
		}else{
			List<Schemes> list = (List<Schemes>)schemeService.getAllScheme(SessionUtil.getUserSession(request), getQueryCriteriaList(request)).getDataList();
			for(Schemes status:list){
				JSONArray subArray = new JSONArray();
				Product product = productService.getProductById(SessionUtil.getUserSession(request), status.getProductId());
				CartageMarster cartageMarster = cartageMarsterService.getCartageMarsterById(SessionUtil.getUserSession(request), status.getCartageMarsterId());
				InterestRates  interestRates = interestRatesService.getInterestRateById(SessionUtil.getUserSession(request), status.getInterestId());

				subArray.put(status.getCode());
				subArray.put(status.getDescription());

				subArray.put(product.getProductId());
				subArray.put(product.getCode());
				subArray.put(product.getDescription());

				subArray.put(cartageMarster.getCartageMarsterId());
				subArray.put(cartageMarster.getCode());
				subArray.put(cartageMarster.getDescription());

				subArray.put(status.getIsActive());
				subArray.put(status.getRecordId());
				subArray.put(status.getVersion());

				subArray.put(interestRates.getInterestRateId());
				subArray.put(interestRates.getCode());
				subArray.put(interestRates.getDescription());

				subArray.put(status.getPeriod());
				subArray.put(status.getInterestCode());
				subArray.put(status.getInterestId());
				subArray.put(status.getCartageMarsterId());

				mainArray.put(subArray);
			}
		}

		response.getWriter().write(mainArray.toString());
		logger.debug("**** Leaving from getGridData method *****");
		return null;
	}

	public ActionForward getProduct(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	MessageResources messageResources = getResources(request,"message");
    	Product product = null;
    	String code = null;

 		if (request.getParameter("code")!=null)
 			code =  request.getParameter("code");

 		try {
 			product = productService.getProductByCode(SessionUtil.getUserSession(request), code);
 		}catch(PawnException ex){
			response.getWriter().write(StrutsFormValidateUtil.getAJAXErrorMessage(ex, messageResources, request.getLocale()));
			return null;
 		}
 		response.getWriter().write(StrutsFormValidateUtil.getAJAXReferenceData(product.getRecordId(),product.getDescription()));
 		return null;
	}

	public ActionForward getCartageMarster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	MessageResources messageResources = getResources(request,"message");
    	CartageMarster cartageMarster = null;
    	String code = null;

 		if (request.getParameter("code")!=null)
 			code =  request.getParameter("code");

 		try {
 			cartageMarster = cartageMarsterService.getCartageMarsterByCode(SessionUtil.getUserSession(request), code);
 		}catch(PawnException ex){
			response.getWriter().write(StrutsFormValidateUtil.getAJAXErrorMessage(ex, messageResources, request.getLocale()));
			return null;
 		}
 		response.getWriter().write(StrutsFormValidateUtil.getAJAXReferenceData(cartageMarster.getRecordId(),cartageMarster.getDescription()));
 		return null;
	}

	public ActionForward getInterestRates(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	MessageResources messageResources = getResources(request,"message");
    	InterestRates interestRates = null;
    	String code = null;

 		if (request.getParameter("code")!=null)
 			code =  request.getParameter("code");

 		try {
 			interestRates = interestRatesService.getInterestRateByCode(SessionUtil.getUserSession(request), code);
 		}catch(PawnException ex){
			response.getWriter().write(StrutsFormValidateUtil.getAJAXErrorMessage(ex, messageResources, request.getLocale()));
			return null;
 		}
 		response.getWriter().write(StrutsFormValidateUtil.getAJAXReferenceData(interestRates.getRecordId(),interestRates.getDescription()));
 		return null;
	}

	@Override
	public ActionForward getAuthorizeData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONArray mainArray = new JSONArray();

		List<QueryCriteria> criteriaList = getAuthorizeQueryCriteriaList(request);
		criteriaList.add(new QueryCriteria("recordStatus",Oparator.GRATERTHAN,RecordStatusEnum.ACTIVE.getCode()));
		List<Schemes> list = (List<Schemes>)schemeService.getAllScheme(SessionUtil.getUserSession(request), criteriaList).getDataList();

		for(Schemes status:list){
			JSONArray subArray = new JSONArray();
			Product product = productService.getProductById(SessionUtil.getUserSession(request), status.getProductId());
			CartageMarster cartageMarster = cartageMarsterService.getCartageMarsterById(SessionUtil.getUserSession(request), status.getCartageMarsterId());

			subArray.put(status.getCode());
			subArray.put(status.getDescription());
			subArray.put(product.getCode());
			subArray.put(cartageMarster.getCode());
			subArray.put(getRecordStatusString(status.getRecordStatus()));
			subArray.put(status.getRecordId());
			subArray.put(status.getVersion());

			mainArray.put(subArray);
		}

		response.getWriter().write(mainArray.toString());
		return null;
	}
}

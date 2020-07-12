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
import com.m4.pawning.domain.Product;
import com.m4.pawning.service.ProductService;

public class ProductAction extends MasterAction {
	private static final Logger logger = Logger.getLogger(ProductAction.class);
	private ProductService productService;

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to create method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if(!validateForm.isEmpty()){
        	response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
        }else{
        	Product product = new Product();
        	product.setCode(request.getParameter("code"));
        	product.setDescription(request.getParameter("description"));
        	product.setScheme(Integer.parseInt(request.getParameter("scheme")));

        	try {
        		productService.createProduct(SessionUtil.getUserSession(request), product);
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
        	Product product = new Product();
        	product.setCode(request.getParameter("code"));
        	product.setDescription(request.getParameter("description"));
        	product.setScheme(Integer.parseInt(request.getParameter("scheme")));
        	product.setRecordId(Integer.parseInt(request.getParameter("recordId")));
        	product.setVersion(Integer.parseInt(request.getParameter("version")));

        	try {
        		productService.updateProduct(SessionUtil.getUserSession(request), product);
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
        	Product product = new Product();
        	product.setRecordId(Integer.parseInt(request.getParameter("recordId")));
        	product.setVersion(Integer.parseInt(request.getParameter("version")));

        	try {
        		productService.removeProduct(SessionUtil.getUserSession(request), product);
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

        Product product = new Product();
    	product.setRecordId(Integer.parseInt(request.getParameter("recordId")));
    	product.setVersion(Integer.parseInt(request.getParameter("version")));

        try{
        	if(authorizeType.equals("Create") ){
        		productService.authorizeCreation(SessionUtil.getUserSession(request), product, authorize);
        	}else if( authorizeType.equals("Update") ){
        		productService.authorizeUpdation(SessionUtil.getUserSession(request), product, authorize);
        	}else if( authorizeType.equals("Delete") ){
        		productService.authorizeDeletion(SessionUtil.getUserSession(request), product, authorize);
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
			Product product = productService.getProductById(SessionUtil.getUserSession(request), Integer.parseInt(recordId));
			mainArray.put(product.getCode());
			mainArray.put(product.getDescription());
			mainArray.put(product.getScheme());
			mainArray.put(product.getRecordId());
			mainArray.put(product.getVersion());

		}else{
			List<Product> list = (List<Product>)productService.getAllProduct(SessionUtil.getUserSession(request), getQueryCriteriaList(request)).getDataList();
			for(Product product:list){
				JSONArray subArray = new JSONArray();
				subArray.put(product.getCode());
				subArray.put(product.getDescription());
				subArray.put(product.getScheme());
				subArray.put(product.getRecordId());
				subArray.put(product.getVersion());

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
		List<Product> list = (List<Product>)productService.getAllProduct(SessionUtil.getUserSession(request), criteriaList).getDataList();

		for(Product product:list){
			JSONArray subArray = new JSONArray();
			subArray.put(product.getCode());
			subArray.put(product.getDescription());			
			subArray.put(getRecordStatusString(product.getRecordStatus()));
			subArray.put(product.getScheme());
			subArray.put(product.getRecordId());
			subArray.put(product.getVersion());

			mainArray.put(subArray);
		}

		response.getWriter().write(mainArray.toString());
		return null;
	}
}


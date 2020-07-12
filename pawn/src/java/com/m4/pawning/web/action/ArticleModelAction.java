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
import com.m4.pawning.domain.ArticleModel;
import com.m4.pawning.domain.Product;
import com.m4.pawning.service.ArticleModelService;
import com.m4.pawning.service.ProductService;



public class ArticleModelAction extends MasterAction {
	private static final Logger logger = Logger.getLogger(ArticleModelAction.class);
	private ArticleModelService articleModelService;
	private ProductService productService;

	public void setArticaleModelService(ArticleModelService articleModelService) {
		this.articleModelService = articleModelService;
	}
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public ActionForward create(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to create method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if(!validateForm.isEmpty()){
        	response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
        }else{
        	ArticleModel articleModel = new ArticleModel();
        	articleModel.setCode(request.getParameter("code"));
        	articleModel.setDescription(request.getParameter("description"));
        	articleModel.setProductId(Integer.parseInt(request.getParameter("productId")));

        	try {
        		articleModelService.createArticleModel(SessionUtil.getUserSession(request), articleModel);
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
        	ArticleModel articleModel = new ArticleModel();
        	articleModel.setCode(request.getParameter("code"));
        	articleModel.setDescription(request.getParameter("description"));
        	articleModel.setProductId(Integer.parseInt(request.getParameter("productId")));
        	articleModel.setRecordId(Integer.parseInt(request.getParameter("recordId")));
        	articleModel.setVersion(Integer.parseInt(request.getParameter("version")));

        	try {
        		articleModelService.updateArticleModel(SessionUtil.getUserSession(request), articleModel);
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
        	ArticleModel articleModel = new ArticleModel();
        	articleModel.setRecordId(Integer.parseInt(request.getParameter("recordId")));
        	articleModel.setVersion(Integer.parseInt(request.getParameter("version")));

        	try {
        		articleModelService.removeArticleModel(SessionUtil.getUserSession(request), articleModel);
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

        ArticleModel articleModel = new ArticleModel();
    	articleModel.setRecordId(Integer.parseInt(request.getParameter("recordId")));
    	articleModel.setVersion(Integer.parseInt(request.getParameter("version")));

        try{
        	if(authorizeType.equals("Create") ){
        		articleModelService.authorizeCreation(SessionUtil.getUserSession(request), articleModel, authorize);
        	}else if( authorizeType.equals("Update") ){
        		articleModelService.authorizeUpdation(SessionUtil.getUserSession(request), articleModel, authorize);
        	}else if( authorizeType.equals("Delete") ){
        		articleModelService.authorizeDeletion(SessionUtil.getUserSession(request), articleModel, authorize);
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
			ArticleModel articleModel = articleModelService.getArticleModelById(SessionUtil.getUserSession(request), Integer.parseInt(recordId));
			Product product = productService.getProductById(SessionUtil.getUserSession(request), articleModel.getProductId());
			mainArray.put(articleModel.getCode());
			mainArray.put(articleModel.getDescription());
			mainArray.put(product.getProductId());
			mainArray.put(product.getCode());
			mainArray.put(product.getDescription());
			mainArray.put(articleModel.getRecordId());
			mainArray.put(articleModel.getVersion());
		}else{
			List<ArticleModel> list = (List<ArticleModel>)articleModelService.getAllArticaleModel(SessionUtil.getUserSession(request), getQueryCriteriaList(request)).getDataList();

			for(ArticleModel status:list){
				JSONArray subArray = new JSONArray();
				Product product = productService.getProductById(SessionUtil.getUserSession(request), status.getProductId());
				subArray.put(status.getCode());
				subArray.put(status.getDescription());
				subArray.put(product.getProductId());
				subArray.put(product.getCode());
				subArray.put(product.getDescription());
				subArray.put(status.getRecordId());
				subArray.put(status.getVersion());

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

	@Override
	public ActionForward getAuthorizeData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONArray mainArray = new JSONArray();
		List<QueryCriteria> criteriaList = getAuthorizeQueryCriteriaList(request);
		criteriaList.add(new QueryCriteria("recordStatus",Oparator.GRATERTHAN,RecordStatusEnum.ACTIVE.getCode()));

		List<ArticleModel> list = (List<ArticleModel>)articleModelService.getAllArticaleModel(SessionUtil.getUserSession(request),criteriaList).getDataList();

		for(ArticleModel status:list){
			JSONArray subArray = new JSONArray();
			Product product = productService.getProductById(SessionUtil.getUserSession(request), status.getProductId());
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

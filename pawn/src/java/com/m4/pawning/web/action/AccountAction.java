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
import com.m4.pawning.domain.GlAccount;
import com.m4.pawning.domain.Product;
import com.m4.pawning.service.AccountService;
import com.m4.pawning.service.ProductService;

public class AccountAction extends MasterAction {
	private static final Logger logger = Logger.getLogger(AccountAction.class);
	private ProductService productService;
	private AccountService accountService;

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}


	public ActionForward create(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to create method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if (!validateForm.isEmpty())
			response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm, messageResources, getLocale(request), null).toString());
		else{
			GlAccount account=new GlAccount();
			account.setCode(request.getParameter("code"));
			account.setDescription(request.getParameter("description"));
			account.setAccountCode(request.getParameter("accountCode"));
			account.setDrCr(request.getParameter("drCr"));
			//account.setAccountId(Integer.parseInt(request.getParameter("accountId")));
			account.setProductId(Integer.parseInt(request.getParameter("productId")));

			try{
				accountService.createAccount(getUserSession(request), account);
				response.getWriter().write(StrutsFormValidateUtil.getMessageCreateSuccess(messageResources).toString());
			}catch(PawnException ex){
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, getLocale(request)).toString());
			}
		}

		logger.debug("**** Leaving create method *****");
		return null;
	}

	public ActionForward update (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to update method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if (!validateForm.isEmpty())
			response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm, messageResources, getLocale(request), null).toString());
		else{
			GlAccount account = new GlAccount();
			account.setCode(request.getParameter("code"));
			account.setDescription(request.getParameter("description"));
			account.setAccountCode(request.getParameter("accountCode"));
			account.setDrCr(request.getParameter("drCr"));
			//account.setAccountId(Integer.parseInt(request.getParameter("accountId")));
			account.setProductId(Integer.parseInt(request.getParameter("productId")));
			account.setVersion(Integer.parseInt(request.getParameter("version")));
			account.setRecordId(Integer.parseInt(request.getParameter("recordId")));

			try{
				accountService.updateAccount(getUserSession(request), account);
				response.getWriter().write(StrutsFormValidateUtil.getMessageUpdateSuccess(messageResources).toString());
			}catch(PawnException ex){
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
			GlAccount account = new GlAccount();
			account.setVersion(Integer.parseInt(request.getParameter("version")));
			account.setRecordId(Integer.parseInt(request.getParameter("recordId")));

			try{
				accountService.removeAccount(getUserSession(request), account);
				response.getWriter().write(StrutsFormValidateUtil.getMessageDeleteSuccess(messageResources).toString());
			}catch(PawnException ex){
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

        GlAccount account = new GlAccount();
		account.setVersion(Integer.parseInt(request.getParameter("version")));
		account.setRecordId(Integer.parseInt(request.getParameter("recordId")));

        try{
        	if(authorizeType.equals("Create") ){
        		accountService.authorizeCreation(SessionUtil.getUserSession(request), account, authorize);
        	}else if( authorizeType.equals("Update") ){
        		accountService.authorizeUpdation(SessionUtil.getUserSession(request), account, authorize);
        	}else if( authorizeType.equals("Delete") ){
        		accountService.authorizeDeletion(SessionUtil.getUserSession(request), account, authorize);
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

		if(recordId!=null && recordId!="" && recordId!="0"){
			GlAccount account = accountService.getAccountById(getUserSession(request), Integer.parseInt(recordId));
			Product product = productService.getProductById(getUserSession(request), account.getProductId());

			mainArray.put(account.getCode());
			mainArray.put(account.getDescription());
			mainArray.put(account.getAccountCode());
			mainArray.put(account.getDrCr());
			mainArray.put(product.getProductId());
			mainArray.put(product.getCode());
			mainArray.put(product.getDescription());
			mainArray.put(account.getAccountId());
			mainArray.put(account.getBranchId());
			mainArray.put(account.getRecordId());
			mainArray.put(account.getVersion());
		}else{
			List<GlAccount> accountList = (List<GlAccount>)accountService.getAllAccount(getUserSession(request), getQueryCriteriaList(request)).getDataList();
			for(GlAccount acc:accountList){
				JSONArray subArray = new JSONArray();
				Product product = productService.getProductById(getUserSession(request), acc.getProductId());
				//GlAccount account = accountService.getAccountById(SessionUtil.getUserSession(request), acc.getAccountId());

				subArray.put(acc.getCode());
				subArray.put(acc.getDescription());
				subArray.put(acc.getAccountCode());
				subArray.put(acc.getDrCr());
				subArray.put(acc.getAccountId());
				subArray.put(product.getProductId());
				subArray.put(product.getCode());
				subArray.put(product.getDescription());
				subArray.put(acc.getRecordId());
				subArray.put(acc.getVersion());

				mainArray.put(subArray);
			}
		}

		response.getWriter().write(mainArray.toString());
		logger.debug("**** Leaving from getGridData method *****");
		return null;

	}

	public ActionForward getAuthorizeData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONArray mainArray = new JSONArray();

		List<QueryCriteria> criteriaList = getAuthorizeQueryCriteriaList(request);
		criteriaList.add(new QueryCriteria("recordStatus",Oparator.GRATERTHAN,RecordStatusEnum.ACTIVE.getCode()));
		List<GlAccount> accountList = (List<GlAccount>)accountService.getAllAccount(getUserSession(request), criteriaList).getDataList();
		for(GlAccount acc:accountList){
			JSONArray subArray = new JSONArray();
//			Product product = productService.getProductById(getUserSession(request), acc.getProductId());
//			//GlAccount account = accountService.getAccountById(SessionUtil.getUserSession(request), acc.getAccountId());

			subArray.put(acc.getCode());
			subArray.put(acc.getDescription());
			subArray.put(acc.getAccountCode());
			subArray.put(getRecordStatusString(acc.getRecordStatus()));
			subArray.put(acc.getDrCr());
			subArray.put(acc.getRecordId());
			subArray.put(acc.getVersion());

			mainArray.put(subArray);
		}
		response.getWriter().write(mainArray.toString());
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
}

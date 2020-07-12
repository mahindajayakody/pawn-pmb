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
import com.m4.core.util.StrutsFormValidateUtil;
import com.m4.core.util.QueryCriteria.Oparator;
import com.m4.pawning.domain.Product;
import com.m4.pawning.domain.ReminderPara;
import com.m4.pawning.domain.Schemes;
import com.m4.pawning.service.ProductService;
import com.m4.pawning.service.ReminderParaService;
import com.m4.pawning.service.SchemeService;

public class ReminderParaAction extends MasterAction {

	private static final Logger logger=Logger.getLogger(ReminderParaAction.class);
	
	private ProductService productService;
	private SchemeService schemeService;
	private ReminderParaService reminderParaService;
	
		
	public void setReminderParaService(ReminderParaService reminderParaService) {
		this.reminderParaService = reminderParaService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public void setSchemeService(SchemeService schemeService) {
		this.schemeService = schemeService;
	}

	public ActionForward create(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to create method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if (!validateForm.isEmpty())
			response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm, messageResources, getLocale(request), null).toString());
		else{
			ReminderPara reminderPara=new ReminderPara();
			reminderPara.setCode(request.getParameter("code"));
			reminderPara.setDescription(request.getParameter("description"));
			reminderPara.setIsSendNominee(request.getParameter("isNominee"));
			reminderPara.setNumberOfDays(Integer.valueOf(request.getParameter("noOfDays")));
			/*reminderPara.setSchemeId(Integer.valueOf(request.getParameter("schemeId")));*/
			reminderPara.setProductId(Integer.valueOf(request.getParameter("productId")));
			reminderPara.setIsBranchExplycit(true);
			
			try {
				reminderParaService.createReminderPara(getUserSession(request), reminderPara);
				response.getWriter().write(StrutsFormValidateUtil.getMessageCreateSuccess(messageResources).toString());
				
			} catch (PawnException ex) {
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
			ReminderPara reminderPara=new ReminderPara();
			reminderPara.setCode(request.getParameter("code"));
			reminderPara.setDescription(request.getParameter("description"));
			reminderPara.setIsSendNominee(request.getParameter("isNominee"));
			reminderPara.setNumberOfDays(Integer.valueOf(request.getParameter("noOfDays")));
			reminderPara.setSchemeId(Integer.valueOf(request.getParameter("schemeId")));
			reminderPara.setProductId(Integer.valueOf(request.getParameter("productId")));
			reminderPara.setVersion(Integer.valueOf(request.getParameter("version")));
			reminderPara.setRecordId(Integer.valueOf(request.getParameter("recordId")));
			reminderPara.setIsBranchExplycit(true);
			
			try {
				reminderParaService.updateReminderPara(getUserSession(request), reminderPara);
				response.getWriter().write(StrutsFormValidateUtil.getMessageUpdateSuccess(messageResources).toString());
				
			} catch (PawnException ex) {
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
			ReminderPara reminderPara=new ReminderPara();
			reminderPara.setVersion(Integer.valueOf(request.getParameter("version")));
			reminderPara.setRecordId(Integer.valueOf(request.getParameter("recordId")));
			
			try {
				reminderParaService.removeReminderPara(getUserSession(request), reminderPara);
				response.getWriter().write(StrutsFormValidateUtil.getMessageDeleteSuccess(messageResources).toString());
				
			} catch (PawnException ex) {
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
		ReminderPara reminderPara=new ReminderPara();
		reminderPara.setVersion(Integer.valueOf(request.getParameter("version")));
		reminderPara.setRecordId(Integer.valueOf(request.getParameter("recordId")));

        try {
			if (authorizeType.equals("Create")) {
				reminderParaService.authorizeCreation(getUserSession(request), reminderPara, authorize);
			} else if (authorizeType.equals("Update")) {
				reminderParaService.authorizeUpdation(getUserSession(request), reminderPara, authorize);
			}else if (authorizeType.equals("Remove")) {
				reminderParaService.authorizeDeletion(getUserSession(request), reminderPara, authorize);
			}
		} catch (PawnException ex) {
			logger.error("exception in authorization" + ex.getExceptionCode());
			response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, getLocale(request)).toString());
			return null;
		}
		if (authorize){
			response.getWriter().write(StrutsFormValidateUtil.getMessageAuthorizeSuccess(messageResources).toString());
		}else{
			response.getWriter().write(StrutsFormValidateUtil.getMessageRejectSuccess(messageResources).toString());
		}
		
		return null;
	}
	
	public ActionForward getAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		logger.debug("**** Enter in to getAjaxData method *****");
		String recordId = request.getParameter("recordId");

		JSONArray mainArray = new JSONArray();

		if(recordId!=null && recordId!="" && recordId!="0"){
			ReminderPara reminderPara =  reminderParaService.getReminderParaById(getUserSession(request), Integer.valueOf(Integer.parseInt(recordId)));
			Product product= productService.getProductById(getUserSession(request), reminderPara.getRecordId());
			//Schemes scheme=schemeService.getSchemeById(getUserSession(request), reminderPara.getRecordId());
			mainArray.put(reminderPara.getCode());
			mainArray.put(reminderPara.getDescription());
			mainArray.put(reminderPara.getIsSendNominee());
			mainArray.put(reminderPara.getNumberOfDays());
			mainArray.put(product.getProductId());
			mainArray.put(product.getCode());
			mainArray.put(product.getDescription());
			/*mainArray.put(scheme.getSchemeId());
			mainArray.put(scheme.getCode());
			mainArray.put(scheme.getDescription());*/
			mainArray.put(reminderPara.getVersion());
			mainArray.put(reminderPara.getRecordId());
		}else{
			List<ReminderPara> reminderParaList= (List<ReminderPara>)reminderParaService.getAllReminderPara(getUserSession(request), getQueryCriteriaList(request)).getDataList();
			for (ReminderPara reminderPara : reminderParaList) {
				Product product=productService.getProductById(getUserSession(request), reminderPara.getProductId());
				//Schemes scheme=schemeService.getSchemeById(getUserSession(request), reminderPara.getSchemeId());
				JSONArray subArray=new JSONArray();
				
				subArray.put(reminderPara.getCode());
				subArray.put(reminderPara.getDescription());
				subArray.put(reminderPara.getIsSendNominee());
				subArray.put(reminderPara.getNumberOfDays());
				subArray.put(product.getProductId());
				subArray.put(product.getCode());
				subArray.put(product.getDescription());
				/*subArray.put(scheme.getSchemeId());
				subArray.put(scheme.getCode());
				subArray.put(scheme.getDescription());*/
				subArray.put(reminderPara.getVersion());
				subArray.put(reminderPara.getRecordId());
				
				mainArray.put(subArray);				
			}
		}
		response.getWriter().write(mainArray.toString());
		logger.debug("**** Leaving getAjaxData method *****");

		return null;
	}
	@Override
	protected ActionForward getAuthorizeData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONArray mainArray = new JSONArray();
		
		List<QueryCriteria> criteriaList=getAuthorizeQueryCriteriaList(request);
		
		criteriaList.add(new QueryCriteria("recordStatus",Oparator.GRATERTHAN,RecordStatusEnum.ACTIVE.getCode()));
		
		List<ReminderPara> remParaList=(List<ReminderPara>)reminderParaService.getAllReminderPara(getUserSession(request), criteriaList).getDataList();
		
		for (ReminderPara reminderPara : remParaList) {
			Product product=productService.getProductById(getUserSession(request), reminderPara.getRecordId());
			Schemes scheme=schemeService.getSchemeById(getUserSession(request), reminderPara.getRecordId());
			JSONArray subArray=new JSONArray();
			
			subArray.put(reminderPara.getCode());
			subArray.put(reminderPara.getDescription());
			subArray.put(reminderPara.getIsSendNominee());
			subArray.put(reminderPara.getNumberOfDays());
			subArray.put(product.getProductId());
			subArray.put(product.getCode());
			subArray.put(product.getDescription());
			subArray.put(scheme.getSchemeId());
			subArray.put(scheme.getCode());
			subArray.put(scheme.getDescription());
			subArray.put(reminderPara.getVersion());
			subArray.put(reminderPara.getRecordId());
			
			mainArray.put(subArray);			
		}
		response.getWriter().write(mainArray.toString());		
		return null;
	}
}

package com.m4.pawning.web.action;

import java.util.Date;
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
import com.m4.core.util.SessionUtil;
import com.m4.core.util.StrutsFormValidateUtil;
import com.m4.pawning.domain.Parameter;
import com.m4.pawning.domain.ParameterValue;
import com.m4.pawning.domain.Product;
import com.m4.pawning.service.ParameterService;
import com.m4.pawning.service.ParameterValueService;
import com.m4.pawning.service.ProductService;

public class ParameterValueAction extends MasterAction {
	private static final Logger logger=Logger.getLogger(ParameterValueAction.class);
	private ParameterValueService parameterValueService;
	private ProductService productService;
	private ParameterService parameterService;

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}
	public void setParameterValueService(
			ParameterValueService parameterValueService) {
		this.parameterValueService = parameterValueService;
	}
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public ActionForward create (ActionMapping mapping ,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 logger.debug("************** Enter in to create Method **************");
		 System.out.println("1");
		 ActionMessages validateForm = form.validate(mapping, request);
		 MessageResources messageResources=getResources(request,"message");
		 System.out.println(mapping.getPath().toString());
		 if (!validateForm.isEmpty())
			 response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
		 else{
			 System.out.println("3");
			 ParameterValue paravalue = new ParameterValue();
			 paravalue.setProductId(Integer.parseInt(request.getParameter("productId")));
			 paravalue.setEffDate(StrutsFormValidateUtil.parseDate(request.getParameter("effDate")));
			 paravalue.setParaValue(request.getParameter("paraValue"));
			 paravalue.setParameterId(Integer.parseInt(request.getParameter("parameterId")));
			 paravalue.setBranchId(SessionUtil.getUserSession(request).getBranchId());
			 paravalue.setCompanyId(SessionUtil.getUserSession(request).getCompanyId());
			 try{
				 System.out.println("4");
				 parameterValueService.createParameterValue(SessionUtil.getUserSession(request), paravalue);
				 response.getWriter().write(StrutsFormValidateUtil.getMessageCreateSuccess(messageResources).toString());
			 }catch(PawnException ex){
				 System.out.println("5");
				 response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, getLocale(request)).toString());
			 }
		 }
		 System.out.println("6");
		 logger.debug("************** Leaving create Method **************");
		 return null;
	}

	public ActionForward update(ActionMapping mapping ,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 logger.debug("************** Enter in to update Method **************");
		 ActionMessages validateForm = form.validate(mapping, request);
		 MessageResources messageResources=getResources(request,"message");

		 if (!validateForm.isEmpty())
			 response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm, messageResources, getLocale(request),null).toString());
		 else{
			 ParameterValue paravalue=new ParameterValue();
			 paravalue.setProductId(Integer.parseInt(request.getParameter("productId")));
			 //paravalue.setParameterValueId(Integer.parseInt(request.getParameter("parameterValueId")));
			 paravalue.setEffDate(StrutsFormValidateUtil.parseDate(request.getParameter("effDate")));
			 paravalue.setParaValue(request.getParameter("paraValue"));
			 paravalue.setParameterId(Integer.parseInt(request.getParameter("parameterId")));
			 paravalue.setRecordId(Integer.parseInt(request.getParameter("recordId")));
			 paravalue.setVersion(Integer.parseInt(request.getParameter("version")));
			 paravalue.setBranchId(SessionUtil.getUserSession(request).getBranchId());
			 paravalue.setCompanyId(SessionUtil.getUserSession(request).getCompanyId());

			 try{
				 parameterValueService.updateParameterValue(SessionUtil.getUserSession(request), paravalue);
				 response.getWriter().write(StrutsFormValidateUtil.getMessageUpdateSuccess(messageResources).toString());
			 }catch(PawnException ex){
				 response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, getLocale(request)).toString());
			 }
		 }
		 logger.debug("************** Leaving update Method **************");
		 return null;
	}
	public ActionForward remove(ActionMapping mapping ,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 logger.debug("************** Enter in to remove Method **************");
		 //ActionMessages validateForm = form.validate(mapping, request);
		 //MessageResources messageResources=getResources(request,"message");

		 //if (!validateForm.isEmpty())
		//	 response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm, messageResources, request.getLocale(),null).toString());
		 //else{
		//	 ParameterValue paravalue=new ParameterValue();
		//	 paravalue.setRecordId(Integer.parseInt(request.getParameter("recordId")));
		//	 paravalue.setVersion(Integer.parseInt(request.getParameter("version")));

			 //try{
				// parameterValueService.removeParameterValue(SessionUtil.getUserSession(request), paravalue);
				 response.getWriter().write("Not implemented");
			 //}catch(PawnException ex){
				 //response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, getLocale(request)).toString());
			 //}
		 //}
		 logger.debug("************** Leaving remove Method **************");
		 return null;
	}
	public ActionForward getAjaxData(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to getGridData method *****");
		String recordId = request.getParameter("recordId");

		JSONArray mainArray = new JSONArray();

		if(recordId!=null && recordId!=""){
			ParameterValue paraValue = parameterValueService.getParameterValueById(SessionUtil.getUserSession(request), Integer.parseInt(recordId));
			Product product=productService.getProductById(SessionUtil.getUserSession(request),Integer.parseInt("recordId"));
			Parameter parameter=parameterService.getParameterById(SessionUtil.getUserSession(request), Integer.parseInt("parameterId"));
			mainArray.put(StrutsFormValidateUtil.parseString(paraValue.getEffDate()));
			mainArray.put(paraValue.getParaValue());
			mainArray.put(product.getProductId());
			mainArray.put(paraValue.getParameterId());
			mainArray.put(parameter.getDataType());
			mainArray.put(paraValue.getRecordId());
			mainArray.put(paraValue.getVersion());
		}else{
			List<ParameterValue> list = (List<ParameterValue>)parameterValueService.getAllParameterValue(SessionUtil.getUserSession(request),getQueryCriteriaList(request)).getDataList();
			for(ParameterValue paraValue:list){
				Parameter parameter=parameterService.getParameterById(SessionUtil.getUserSession(request), paraValue.getParameterId());
				JSONArray subArray = new JSONArray();
				subArray.put(StrutsFormValidateUtil.parseString(paraValue.getEffDate()));
				subArray.put(paraValue.getParaValue());
				subArray.put(paraValue.getProductId());
				subArray.put(paraValue.getParameterId());
				subArray.put(parameter.getDataType());
				subArray.put(paraValue.getRecordId());
				subArray.put(paraValue.getVersion());
				mainArray.put(subArray);
			}
		}
		response.getWriter().write(mainArray.toString());
		logger.debug("**** Leaving from getGridData method *****");
		return null;
	}

	@Override
	protected ActionForward getAuthorizeData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}
}

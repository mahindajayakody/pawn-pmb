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
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.util.MessageResources;
import org.json.JSONArray;

import com.m4.core.exception.PawnException;
import com.m4.core.util.MasterAction;
import com.m4.core.util.SessionUtil;
import com.m4.core.util.StrutsFormValidateUtil;
import com.m4.pawning.domain.Parameter;
import com.m4.pawning.domain.Product;
import com.m4.pawning.service.ParameterService;
import com.m4.pawning.service.ProductService;
import com.m4.pawning.util.ParameterValueTypeEnum;

public class ParameterAction extends MasterAction {
	private static final Logger logger= Logger.getLogger(ParameterAction.class);

	private ParameterService parameterService;
	private ProductService productService;

	public void setProductService(ProductService productService){
		this.productService=productService;
	}
	public void setParameterService(ParameterService paraService){
		this.parameterService=paraService;
	}
	public ActionForward create(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to create method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if(!validateForm.isEmpty()){
        	response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
        }else{
        	Parameter parameter = new Parameter();
        	parameter.setCode(request.getParameter("code"));
        	parameter.setDescription(request.getParameter("description"));
        	parameter.setProductId(Integer.parseInt(request.getParameter("productId")));
        	parameter.setIsActive(request.getParameter("isActive"));
        	parameter.setDataType(request.getParameter("dataType"));

        	try {
        		parameterService.createParameter(SessionUtil.getUserSession(request), parameter);
        		response.getWriter().write(StrutsFormValidateUtil.getMessageCreateSuccess(messageResources).toString());
			} catch (PawnException ex) {
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
			}
        }
		logger.debug("**** Leaving from create method *****");
		return null;
	}
	public ActionForward update(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to update method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if(!validateForm.isEmpty()){
        	response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
        }else{
        	Parameter parameter = new Parameter();
        	parameter.setCode(request.getParameter("code"));
        	parameter.setDescription(request.getParameter("description"));
        	parameter.setProductId(Integer.parseInt(request.getParameter("productId")));
        	parameter.setRecordId(Integer.parseInt(request.getParameter("recordId")));
        	parameter.setVersion(Integer.parseInt(request.getParameter("version")));
        	parameter.setIsActive(request.getParameter("isActive"));
        	parameter.setDataType(request.getParameter("dataType"));


        	try {
        		parameterService.updateParameter(SessionUtil.getUserSession(request), parameter);
        		response.getWriter().write(StrutsFormValidateUtil.getMessageUpdateSuccess(messageResources).toString());
			} catch (PawnException ex) {
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
			}
        }

		logger.debug("**** Leaving from update method *****");
		return null;
	}
	public ActionForward remove(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to remove method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if(!validateForm.isEmpty()){
        	response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
        }else{
        	Parameter parameter = new Parameter();
        	parameter.setRecordId(Integer.parseInt(request.getParameter("recordId")));
        	parameter.setVersion(Integer.parseInt(request.getParameter("version")));

        	try {
        		parameterService.removeParameter(SessionUtil.getUserSession(request), parameter);
        		response.getWriter().write(StrutsFormValidateUtil.getMessageDeleteSuccess(messageResources).toString());
			} catch (PawnException ex) {
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
			}
        }
		logger.debug("**** Leaving from remove method *****");
		return null;
	}
	public ActionForward getAjaxData(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to getGridData method *****");
		String recordId = request.getParameter("recordId");

		JSONArray mainArray = new JSONArray();

		if(recordId!=null && recordId!=""){
			Parameter parameter = parameterService.getParameterById(SessionUtil.getUserSession(request), Integer.parseInt(recordId));
			Product product=productService.getProductById(SessionUtil.getUserSession(request),Integer.parseInt("recordId"));
			mainArray.put(parameter.getCode());
			mainArray.put(parameter.getDescription());
			mainArray.put(parameter.getDataType());
			mainArray.put(parameter.getIsActive());
			mainArray.put(parameter.getRecordId());
			mainArray.put(parameter.getVersion());
			mainArray.put(product.getProductId());
		}else{
			List<Parameter> list = (List<Parameter>)parameterService.getAllParameter(SessionUtil.getUserSession(request),getQueryCriteriaList(request)).getDataList();
			for(Parameter pro:list){
				JSONArray subArray = new JSONArray();
				subArray.put(pro.getCode());
				subArray.put(pro.getDescription());
				subArray.put(pro.getDataType());
				subArray.put(pro.getIsActive());
				subArray.put(pro.getRecordId());
				subArray.put(pro.getVersion());
				subArray.put(pro.getProductId());
				mainArray.put(subArray);
			}
		}

		response.getWriter().write(mainArray.toString());
		logger.debug("**** Leaving from getGridData method *****");
		return null;
	}

	@Override
	protected void setOtherData(ActionForm form, HttpServletRequest request) {
		DynaActionForm frm = (DynaActionForm) form;
		ParameterValueTypeEnum[] list = ParameterValueTypeEnum.values();
    	List<String> dataTypes = new ArrayList<String>();
    	for(ParameterValueTypeEnum enum1:list){
    		dataTypes.add(enum1.getCode());
    	}
        frm.set("datalist",dataTypes);
	}

	@Override
	protected ActionForward getAuthorizeData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}
}

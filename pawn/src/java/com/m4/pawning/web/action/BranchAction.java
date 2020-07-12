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
import com.m4.pawning.domain.Branch;
import com.m4.pawning.domain.Company;
import com.m4.pawning.service.BranchService;
import com.m4.pawning.service.CompanyService;

public class BranchAction extends MasterAction {
	private static final Logger logger = Logger.getLogger(BranchAction.class);
	private BranchService branchService;
	private CompanyService companyService;

	public void setBranchService(BranchService branchService) {
		this.branchService = branchService;
	}
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to create method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if(!validateForm.isEmpty()){
        	response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
        }else{
        	Branch branch = new Branch();
        	branch.setCode(request.getParameter("code"));
        	branch.setCompanyId(Integer.parseInt(request.getParameter("companyId")));
        	branch.setAddressline1(request.getParameter("addLine1"));
        	branch.setAddressline2(request.getParameter("addLine2"));
        	branch.setAddressline3(request.getParameter("addLine3"));
        	branch.setAddressline4(request.getParameter("addLine4"));
        	branch.setTelephoneNo(request.getParameter("telephoneNo"));
        	branch.setFaxNo(request.getParameter("faxNo"));
        	branch.setTaxNo(request.getParameter("taxNo"));
        	//branch.setIsDefault(Integer.parseInt(request.getParameter("isDefault")));
        	branch.setIsDefault(0);
        	branch.setReceiptAccount(request.getParameter("receiptAccount"));
        	branch.setPaymentAccount(request.getParameter("paymentAccount"));
        	branch.setDateInstalled(StrutsFormValidateUtil.parseDate(request.getParameter("dateInstalled")));
        	branch.setBarnchName(request.getParameter("branchName"));
        	branch.setFundAvailable(Double.parseDouble(request.getParameter("fundavailable")));
        	branch.setFundLimit(Double.parseDouble(request.getParameter("fundLimit")));
        	branch.setEmail(request.getParameter("email"));


        	try {
        		branchService.createBranch(SessionUtil.getUserSession(request), branch);
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
        	Branch branch = new Branch();
        	branch.setCode(request.getParameter("code"));
        	branch.setCompanyId(Integer.parseInt(request.getParameter("companyId")));
        	branch.setAddressline1(request.getParameter("addLine1"));
        	branch.setAddressline2(request.getParameter("addLine2"));
        	branch.setAddressline3(request.getParameter("addLine3"));
        	branch.setAddressline4(request.getParameter("addLine4"));
        	branch.setTelephoneNo(request.getParameter("telephoneNo"));
        	branch.setFaxNo(request.getParameter("faxNo"));
        	branch.setTaxNo(request.getParameter("taxNo"));
        	//branch.setIsDefault(Integer.parseInt(request.getParameter("isDefault")));
        	branch.setIsDefault(0);
        	branch.setReceiptAccount(request.getParameter("receiptAccount"));
        	branch.setPaymentAccount(request.getParameter("paymentAccount"));
        	branch.setDateInstalled(StrutsFormValidateUtil.parseDate(request.getParameter("dateInstalled")));
        	branch.setBarnchName(request.getParameter("branchName"));
        	branch.setRecordId(Integer.parseInt(request.getParameter("recordId")));
        	branch.setVersion(Integer.parseInt(request.getParameter("version")));
        	branch.setFundAvailable(Double.parseDouble(request.getParameter("fundavailable")));
        	branch.setFundLimit(Double.parseDouble(request.getParameter("fundLimit")));
        	branch.setEmail(request.getParameter("email"));

        	try {
        		branchService.updateBranch(SessionUtil.getUserSession(request), branch);
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
        	Branch branch = new Branch();
        	branch.setRecordId(Integer.parseInt(request.getParameter("recordId")));
        	branch.setVersion(Integer.parseInt(request.getParameter("version")));

        	try {
        		branchService.removeBranch(SessionUtil.getUserSession(request), branch);
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

        Branch branch = new Branch();
    	branch.setRecordId(Integer.parseInt(request.getParameter("recordId")));
    	branch.setVersion(Integer.parseInt(request.getParameter("version")));

//        try{
//        	if(authorizeType.equals("create") ){
//        		branchService.authorizeCreation(SessionUtil.getUserSession(request), branch, authorize);
//        	}else if( authorizeType.equals("update") ){
//        		branchService.authorizeUpdation(SessionUtil.getUserSession(request), branch, authorize);
//        	}else if( authorizeType.equals("delete") ){
//        		branchService.authorizeDeletion(SessionUtil.getUserSession(request), branch, authorize);
//	    	}
//        }catch(PawnException ex){
//	    	logger.error("exception in authorization" + ex.getExceptionCode());
//	    	response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, getLocale(request)).toString());
//	    	return null;
//        }

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

		if(recordId!=null && recordId!=""){
			Branch branch = branchService.getBranchById(SessionUtil.getUserSession(request), Integer.parseInt(recordId));
			Company company = companyService.getCompanyById(SessionUtil.getUserSession(request), branch.getCompanyId());

			mainArray.put(branch.getCode());
			//mainArray.put(getAddress(branch));
			//mainArray.put(branch.getBarnchName());
			mainArray.put(branch.getBarnchName());
			mainArray.put(company.getCode());
			mainArray.put(company.getCompanyName());
			mainArray.put(branch.getAddressline1());
			mainArray.put(branch.getAddressline2());
			mainArray.put(branch.getAddressline3());
			mainArray.put(branch.getAddressline4());
			mainArray.put(branch.getTelephoneNo());
			mainArray.put(branch.getFaxNo());
			mainArray.put(branch.getTaxNo());
			mainArray.put(branch.getIsDefault());
			mainArray.put(branch.getReceiptAccount());
			mainArray.put(branch.getPaymentAccount());
			mainArray.put(StrutsFormValidateUtil.parseString(branch.getDateInstalled()));
			mainArray.put(company.getCompanyId());
			mainArray.put(branch.getFundLimit());
			mainArray.put(branch.getFundAvailable());
			mainArray.put(branch.getBranchId());
			mainArray.put(branch.getVersion());
			mainArray.put(branch.getBarnchName());
			mainArray.put(branch.getEmail());
		}else{
			List<Branch> list = (List<Branch>)branchService.getAllBranch(SessionUtil.getUserSession(request), getQueryCriteriaList(request)).getDataList();
			for(Branch branch:list){
				Company company = companyService.getCompanyById(SessionUtil.getUserSession(request), branch.getCompanyId());
				JSONArray subArray = new JSONArray();

				subArray.put(branch.getCode());
				//subArray.put(getAddress(branch));
				subArray.put(branch.getBarnchName());
				subArray.put(company.getCode());
				subArray.put(company.getCompanyName());
				subArray.put(branch.getAddressline1());
				subArray.put(branch.getAddressline2());
				subArray.put(branch.getAddressline3());
				subArray.put(branch.getAddressline4());
				subArray.put(branch.getTelephoneNo());
				subArray.put(branch.getFaxNo());
				subArray.put(branch.getTaxNo());
				subArray.put(branch.getIsDefault());
				subArray.put(branch.getReceiptAccount());
				subArray.put(branch.getPaymentAccount());
				subArray.put(StrutsFormValidateUtil.parseString(branch.getDateInstalled()));
				subArray.put(company.getCompanyId());
				subArray.put(branch.getFundLimit());
				subArray.put(branch.getFundAvailable());
				subArray.put(branch.getBranchId());
				subArray.put(branch.getVersion());
				subArray.put(branch.getBarnchName());
				subArray.put(branch.getEmail());

				mainArray.put(subArray);
			}
		}

		response.getWriter().write(mainArray.toString());
		logger.debug("**** Leaving from getGridData method *****");
		return null;
	}
	
	public ActionForward getBranchSelectedData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to getBranchSelectedData method *****");

		JSONArray mainArray = new JSONArray();
		
		List<Branch> list = (List<Branch>)branchService.getAllBranch(SessionUtil.getUserSession(request), getQueryCriteriaList(request)).getDataList();
		
		for(Branch branch:list){
			Company company = companyService.getCompanyById(SessionUtil.getUserSession(request), branch.getCompanyId());
			JSONArray subArray = new JSONArray();

			subArray.put(branch.getCode());			
			subArray.put(branch.getBarnchName());
			subArray.put("");
			subArray.put(branch.getBranchId());
			subArray.put(branch.getVersion());			

			mainArray.put(subArray);
		}		

		response.getWriter().write(mainArray.toString());
		logger.debug("**** Leaving from getBranchSelectedData method *****");
		return null;
	}

	public ActionForward getCompany(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	MessageResources messageResources = getResources(request,"message");
    	Company company = null;
 		String code = null;

 		if (request.getParameter("code")!=null)
 			code =  request.getParameter("code");

 		try {
 			company = companyService.getCompanyByCode(SessionUtil.getUserSession(request), code);
 		}catch(PawnException ex){
			response.getWriter().write(StrutsFormValidateUtil.getAJAXErrorMessage(ex, messageResources, request.getLocale()));
			return null;
 		}
 		response.getWriter().write(StrutsFormValidateUtil.getAJAXReferenceData(company.getCompanyId(),company.getCompanyName()));
 		return null;
	}

	private String getAddress(Branch branch){
		StringBuilder builder = new StringBuilder();
		builder.append(branch.getAddressline1());
		builder.append(", ");
		builder.append(branch.getAddressline2());
		builder.append(branch.getAddressline3()!=null?", ":".");
		builder.append(branch.getAddressline3()!=null?branch.getAddressline3():"");
		builder.append(branch.getAddressline3()!=null && branch.getAddressline4()!=null?", ":"");
		builder.append(branch.getAddressline3()!=null && branch.getAddressline4()!=null?branch.getAddressline4():"");
		return builder.toString();
	}

	
	
	@Override
	public ActionForward getAuthorizeData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONArray mainArray = new JSONArray();

		List<QueryCriteria> criteriaList = getAuthorizeQueryCriteriaList(request);
		criteriaList.add(new QueryCriteria("recordStatus",Oparator.GRATERTHAN,RecordStatusEnum.ACTIVE.getCode()));
		List<Branch> list = (List<Branch>)branchService.getAllBranch(SessionUtil.getUserSession(request), criteriaList).getDataList();

		for(Branch branch:list){
			Company company = companyService.getCompanyById(SessionUtil.getUserSession(request), branch.getCompanyId());
			JSONArray subArray = new JSONArray();

			subArray.put(branch.getCode());
			subArray.put(branch.getBarnchName());
			subArray.put(company.getCode());
			subArray.put(getRecordStatusString(branch.getRecordStatus()));
			subArray.put(branch.getBranchId());
			subArray.put(branch.getVersion());

			mainArray.put(subArray);
		}

		response.getWriter().write(mainArray.toString());
		return null;
	}
}

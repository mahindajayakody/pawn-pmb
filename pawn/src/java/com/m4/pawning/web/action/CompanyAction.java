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
import com.m4.core.util.SessionUtil;
import com.m4.core.util.StrutsFormValidateUtil;
import com.m4.pawning.domain.Company;
import com.m4.pawning.domain.Location;
import com.m4.pawning.service.CompanyService;
import com.m4.pawning.service.LocationService;

public class CompanyAction extends MasterAction {
	private static final Logger logger = Logger.getLogger(CompanyAction.class);
	private CompanyService companyService;

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
        	Company company = new Company();
        	company.setCode(request.getParameter("code"));
        	company.setCompanyName(request.getParameter("name"));
        	company.setAddressline1(request.getParameter("addLine1"));
        	company.setAddressline2(request.getParameter("addLine2"));
        	company.setAddressline3(request.getParameter("addLine3"));
        	company.setAddressline4(request.getParameter("addLine4"));
        	company.setTelephoneNo(request.getParameter("telephoneNo"));
        	company.setFaxNo(request.getParameter("faxNo"));
        	company.setTaxNo(request.getParameter("taxNo"));
        	company.setDateInstalled(StrutsFormValidateUtil.parseDate(request.getParameter("dateInstalled")));
        	company.setFinanceBeginDate(StrutsFormValidateUtil.parseDate(request.getParameter("finBeginDate")));
        	company.setFinanceEndDate(StrutsFormValidateUtil.parseDate(request.getParameter("finEndDate")));
        	company.setAuthorizeMode(Integer.parseInt(request.getParameter("authmode")));

        	try {
        		companyService.createCompany(SessionUtil.getUserSession(request), company);
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
        	Company company = new Company();
        	company.setCode(request.getParameter("code"));
        	company.setCompanyName(request.getParameter("name"));
        	company.setAddressline1(request.getParameter("addLine1"));
        	company.setAddressline2(request.getParameter("addLine2"));
        	company.setAddressline3(request.getParameter("addLine3"));
        	company.setAddressline4(request.getParameter("addLine4"));
        	company.setTelephoneNo(request.getParameter("telephoneNo"));
        	company.setFaxNo(request.getParameter("faxNo"));
        	company.setTaxNo(request.getParameter("taxNo"));
        	company.setDateInstalled(StrutsFormValidateUtil.parseDate(request.getParameter("dateInstalled")));
        	company.setFinanceBeginDate(StrutsFormValidateUtil.parseDate(request.getParameter("finBeginDate")));
        	company.setFinanceEndDate(StrutsFormValidateUtil.parseDate(request.getParameter("finEndDate")));
        	company.setRecordId(Integer.parseInt(request.getParameter("recordId")));
        	company.setVersion(Integer.parseInt(request.getParameter("version")));
        	company.setAuthorizeMode(Integer.parseInt(request.getParameter("authmode")));

        	try {
        		companyService.updateCompany(SessionUtil.getUserSession(request), company);
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
        	Company company = new Company();
        	company.setRecordId(Integer.parseInt(request.getParameter("recordId")));
        	company.setVersion(Integer.parseInt(request.getParameter("version")));

        	try {
        		companyService.removeCompany(SessionUtil.getUserSession(request), company);
        		response.getWriter().write(StrutsFormValidateUtil.getMessageDeleteSuccess(messageResources).toString());
			} catch (PawnException ex) {
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
			}
        }

		logger.debug("**** Leaving from remove method *****");
		return null;
	}

	public ActionForward getAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to getGridData method *****");
		String recordId = request.getParameter("recordId");

		JSONArray mainArray = new JSONArray();

		if(recordId!=null && recordId!=""){
			Company company = companyService.getCompanyById(SessionUtil.getUserSession(request), Integer.parseInt(recordId));
			mainArray.put(company.getCode());
			mainArray.put(company.getCompanyName());
			mainArray.put(company.getAddressline1());
			mainArray.put(company.getAddressline2());
			mainArray.put(company.getAddressline3());
			mainArray.put(company.getAddressline4());
			mainArray.put(company.getTelephoneNo());
			mainArray.put(company.getFaxNo());
			mainArray.put(company.getTaxNo());
			mainArray.put(StrutsFormValidateUtil.parseString(company.getDateInstalled()));
			mainArray.put(StrutsFormValidateUtil.parseString(company.getFinanceBeginDate()));
			mainArray.put(StrutsFormValidateUtil.parseString(company.getFinanceEndDate()));
			mainArray.put(company.getAuthorizeMode());
			mainArray.put(company.getRecordId());
			mainArray.put(company.getVersion());
		}else{
			List<Company> list = (List<Company>)companyService.getAllCompany(SessionUtil.getUserSession(request), getQueryCriteriaList(request)).getDataList();
			for(Company status:list){
				JSONArray subArray = new JSONArray();
				subArray.put(status.getCode());
				subArray.put(status.getCompanyName());
				subArray.put(status.getAddressline1());
				subArray.put(status.getAddressline2());
				subArray.put(status.getAddressline3());
				subArray.put(status.getAddressline4());
				subArray.put(status.getTelephoneNo());
				subArray.put(status.getFaxNo());
				subArray.put(status.getTaxNo());
				subArray.put(StrutsFormValidateUtil.parseString(status.getDateInstalled()));
				subArray.put(StrutsFormValidateUtil.parseString(status.getFinanceBeginDate()));
				subArray.put(StrutsFormValidateUtil.parseString(status.getFinanceEndDate()));
				subArray.put(status.getAuthorizeMode());
				subArray.put(status.getRecordId());
				subArray.put(status.getVersion());

				mainArray.put(subArray);
			}
		}

		response.getWriter().write(mainArray.toString());
		logger.debug("**** Leaving from getGridData method *****");
		return null;
	}

	public ActionForward getLoginCompanies(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to getGridData method *****");
		String recordId = request.getParameter("recordId");

		JSONArray mainArray = new JSONArray();
		List<Company> list = (List<Company>)companyService.getAllCompany(SessionUtil.getUserSession(request), null).getDataList();
		for(Company status:list){
			JSONArray subArray = new JSONArray();
			subArray.put(status.getCode());
			subArray.put(status.getRecordId());

			mainArray.put(subArray);
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

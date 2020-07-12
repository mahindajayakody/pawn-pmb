package com.m4.pawning.web.action;


import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.NDC;
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
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Branch;
import com.m4.pawning.domain.FundRequest;
import com.m4.pawning.domain.Officer;
import com.m4.pawning.service.BranchService;
import com.m4.pawning.service.FundRequestService;
import com.m4.pawning.service.OfficerService;
import com.m4.pawning.service.PawnerService;

public class FundRequestAction extends MasterAction {
	private static final Logger logger=Logger.getLogger(FundRequestAction.class);
	private static DecimalFormat decimalFormat = new DecimalFormat();
	static{
		decimalFormat.setMaximumFractionDigits(2);
		decimalFormat.setMinimumFractionDigits(2);
	}


	private BranchService branchService;
	private FundRequestService fundRequestService;
	private OfficerService officerService;
	private PawnerService pawnerService;

	public void setPawnerService(PawnerService pawnerService) {
		this.pawnerService = pawnerService;
	}

	public void setOfficerService(OfficerService officerService) {
		this.officerService = officerService;
	}

	public void setFundRequestService(FundRequestService fundRequestService) {
		this.fundRequestService = fundRequestService;
	}

	public void setBranchService(BranchService branchService) {
		this.branchService = branchService;
	}

	public ActionForward create (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		logger.debug("***** Enter in to create method ****");
		ActionMessages validateForm=form.validate(mapping, request);
		MessageResources messageResources=getResources(request,"message");

		if (!validateForm.isEmpty()){
			response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm, messageResources, getLocale(request), null).toString());
		}else{
			FundRequest fundRequest=new FundRequest();
			fundRequest.setRequestAmount(Double.parseDouble(request.getParameter("requestAmount")));
			fundRequest.setRequestDate(StrutsFormValidateUtil.parseDate(request.getParameter("requestDate")));
//			fundRequest.setOfficer(officerService.getOfficerByPawnerId(getUserSession(request), Integer.parseInt(request.getParameter("pawnerId"))));
			fundRequest.setMail(getUserSession(request).getBrachEmail());
			//System.out.println(getUserSession(request).getPawnerId());
			//System.out.println(pawnerService.getPawnerById(getUserSession(request), getUserSession(request).getPawnerId()).getEmailAddress().toString());
			fundRequest.setMail(getUserSession(request).getUserEmail());

			logger.debug("################################ " + getUserSession(request).getUserEmail());

			try{
				fundRequestService.createFundRequest(getUserSession(request), fundRequest);
				response.getWriter().write(StrutsFormValidateUtil.getMessageCreateSuccess(messageResources).toString());
			}catch (PawnException ex){
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, getLocale(request)).toString());
			}
		}
		logger.debug("**** Leaving create method ****");
		return null;
	}
	public ActionForward update(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{

		logger.debug("**** Enter in to update method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if(!validateForm.isEmpty()){
        	response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
        }else{


        	FundRequest fundRequest = new FundRequest();

        	//fundRequest.setBranchId(Integer.parseInt(request.getParameter("branchId")));
        	fundRequest.setRequestNo(request.getParameter("requestNo"));
        	fundRequest.setRequestDate(StrutsFormValidateUtil.parseDate(request.getParameter("requestDate")));
        	fundRequest.setRequestAmount(Double.parseDouble(request.getParameter("requestAmount")));
        	fundRequest.setRecordId(Integer.parseInt(request.getParameter("recordId")));
        	fundRequest.setVersion(Integer.parseInt(request.getParameter("version")));
        	fundRequest.setRequestedBranchId(Integer.parseInt(request.getParameter("requestBranchId")));
//        	fundRequest.setOfficer(officerService.getOfficerById(getUserSession(request), Integer.parseInt(request.getParameter("pawnerId"))));

        	try {
        		fundRequestService.updateFundRequest(SessionUtil.getUserSession(request), fundRequest);
        		response.getWriter().write(StrutsFormValidateUtil.getMessageUpdateSuccess(messageResources).toString());
			} catch (PawnException ex) {
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
			}
        }

		logger.debug("**** Leaving from update method *****");
		return null;

	}
	public ActionForward approve(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{

		logger.debug("**** Enter in to authorize method ****");
		ActionMessages validateForm=form.validate(mapping, request);
		MessageResources messageResources=getResources(request,"message");

		if (!validateForm.isEmpty()){
			response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm, messageResources, getLocale(request), null).toString());
		}else{
//			FundRequest fundRequest=new FundRequest();
			//fundRequest.setApprovedBy(Integer.parseInt(request.getParameter("approvedBy")));
//			fundRequest.setRequestNo(request.getParameter("requestNo"));
//			fundRequest.setRequestDate(StrutsFormValidateUtil.parseDate(request.getParameter("requestDate")));
//        	fundRequest.setApprovedDate(StrutsFormValidateUtil.parseDate(request.getParameter("approvedDate")));
//			fundRequest.setRequestAmount(Double.parseDouble(request.getParameter("requestAmount")));
//			fundRequest.setRecordId(Integer.parseInt(request.getParameter("recordId")));
			FundRequest fundRequest = fundRequestService.getFundRequestById(getUserSession(request), Integer.parseInt(request.getParameter("recordId")));
        	fundRequest.setVersion(Integer.parseInt(request.getParameter("version")));

			try {
				fundRequestService.approveFundRequest(getUserSession(request), fundRequest);
				response.getWriter().write(StrutsFormValidateUtil.getMessageApproveSuccess(messageResources).toString());
			} catch (PawnException ex) {
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, getLocale(request)).toString());
			}
		}
		logger.debug("****Leaving authorize method****");
		return null;
	}
	public ActionForward getAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to getGridData method *****");
		String recordId = request.getParameter("recordId");

		JSONArray mainArray = new JSONArray();

		if(recordId!=null && recordId!="" && recordId!="0"){
			FundRequest fundRequest= fundRequestService.getFundRequestById(getUserSession(request), Integer.valueOf(recordId));
			Branch branch=branchService.getBranchById(getUserSession(request), fundRequest.getBranchId());
			Officer officer=null;
			if (Integer.valueOf(fundRequest.getApprovedBy())!=0)
				officer=officerService.getOfficerById(getUserSession(request), Integer.valueOf(fundRequest.getApprovedBy()));

			mainArray.put(fundRequest.getRequestNo());
			mainArray.put(fundRequest.getRequestDate());
			mainArray.put(fundRequest.getRequestAmount());
			/*if (officer!=null)
				mainArray.put(officer.getUserName());
			else
				mainArray.put("Not Approved");
			mainArray.put(fundRequest.getApprovedDate());*/

			mainArray.put(branch.getBarnchName());
			mainArray.put(branch.getCode());
			mainArray.put(fundRequest.getRecordId());
			mainArray.put(fundRequest.getVersion());
			mainArray.put(fundRequest.getApprovedBy());
			mainArray.put(fundRequest.getRequestedBranchId());

		}else{
			List<FundRequest> fundRequestList= (List<FundRequest>) fundRequestService.getAllFundRequest(getUserSession(request), getQueryCriteriaList(request)).getDataList();
			for (FundRequest req:fundRequestList){
				Branch branch=branchService.getBranchById(getUserSession(request), req.getBranchId());
				Officer officer=null;
				if(Integer.valueOf(req.getApprovedBy())!=0)
					officer=officerService.getOfficerById(getUserSession(request), Integer.valueOf(req.getApprovedBy()));

				JSONArray subArray=new JSONArray();
				if(Integer.valueOf(req.getApprovedBy())==0){
					subArray.put(req.getRequestNo());
					subArray.put(StrutsFormValidateUtil.parseString(req.getRequestDate()));
					subArray.put(decimalFormat.format(req.getRequestAmount()));
	
					subArray.put(req.getApprovedBy()==0?"Not Approved":officer.getUserName());
	
					subArray.put(req.getApprovedDate()!=null?StrutsFormValidateUtil.parseString(req.getApprovedDate()): "");
					subArray.put(branch.getBarnchName());
					subArray.put(branch.getCode());
					subArray.put(req.getRecordId());
					subArray.put(req.getVersion());
					subArray.put(req.getApprovedBy());
					subArray.put(req.getRequestedBranchId());
	
					mainArray.put(subArray);
				}
			}
		}
		response.getWriter().write(mainArray.toString());
		logger.debug("**** Leaving getGridData method *****");
		return null;
	}

	public ActionForward getAuthorizeData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONArray mainArray = new JSONArray();

		List<FundRequest> fundRequestList= (List<FundRequest>) fundRequestService.getAllFundRequest(getUserSession(request), getAuthorizeQueryCriteriaList(request)).getDataList();

		for(FundRequest req:fundRequestList){

			//Branch branch=branchService.getBranchById(getUserSession(request), req.getBranchId());
			Officer officer=null;
			if(Integer.valueOf(req.getApprovedBy())!=0)
				officer=officerService.getOfficerById(getUserSession(request), Integer.valueOf(req.getApprovedBy()));

			JSONArray subArray = new JSONArray();

			subArray.put(req.getRequestNo());
			subArray.put(StrutsFormValidateUtil.parseString(req.getRequestDate()));
			subArray.put(decimalFormat.format(req.getRequestAmount()));

			subArray.put(req.getApprovedBy()==0?"Not Approved":officer.getUserName());
			subArray.put(req.getApprovedDate());
			//subArray.put(branch.getBarnchName());
			//subArray.put(branch.getCode());
			subArray.put(req.getRecordId());
			subArray.put(req.getVersion());
			subArray.put(req.getApprovedBy());
			subArray.put(req.getRequestedBranchId());

			mainArray.put(subArray);
		}
		response.getWriter().write(mainArray.toString());

		return null;
	}

	public ActionForward getAjaxMasterData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to getGMasterData( method *****");
		UserConfig userConfig = SessionUtil.getUserSession(request);

		JSONArray mainArray = new JSONArray();

		Branch branch=branchService.getBranchById(getUserSession(request), Integer.valueOf(userConfig.getBranchId()));

		mainArray.put(decimalFormat.format(branch.getFundLimit()));
		mainArray.put(decimalFormat.format(branch.getFundAvailable()));
		mainArray.put(decimalFormat.format(branch.getFundAvailable()- branch.getFundLimit()));

		response.getWriter().write(mainArray.toString());
		logger.debug("**** Leaving getGMasterData method *****");
		return null;
	}


}


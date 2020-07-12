package com.m4.pawning.web.action;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Branch;
import com.m4.pawning.domain.FundDeposit;
import com.m4.pawning.domain.FundRequest;
import com.m4.pawning.domain.Officer;
import com.m4.pawning.service.BranchService;
import com.m4.pawning.service.FundDepositService;
import com.m4.pawning.service.OfficerService;

public class FundDepositAction extends MasterAction {
	private static final Logger logger=Logger.getLogger(FundRequest.class);
	private static DecimalFormat decimalFormat = new DecimalFormat();
	private static final SimpleDateFormat simpdate = new SimpleDateFormat("dd/MM/yyyy");

	static{
		decimalFormat.setMaximumFractionDigits(2);
		decimalFormat.setMinimumFractionDigits(2);
	}


	private BranchService branchService;
	private FundDepositService fundDepositService;
	private OfficerService officerService;

	public void setOfficerService(OfficerService officerService) {
		this.officerService = officerService;
	}

	public void setFundDepositService(FundDepositService fundDepositService) {
		this.fundDepositService = fundDepositService;
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

			FundDeposit fundDeposit=new FundDeposit();
			fundDeposit.setDepositAmount(Double.parseDouble(request.getParameter("depositamount")));
			fundDeposit.setDepositDate(StrutsFormValidateUtil.parseDate(request.getParameter("depositdate")));
			fundDeposit.setTicketCount(Integer.parseInt(request.getParameter("totaltickts")));
			fundDeposit.setReceiptCount(Integer.parseInt(request.getParameter("totalreceipts")));
			fundDeposit.setTicketedAmount(Double.parseDouble(request.getParameter("totalticktsamt")));
			fundDeposit.setReceiptedAmount(Double.parseDouble(request.getParameter("totalreceiptsamt")));
			fundDeposit.setMail(getUserSession(request).getUserEmail());
			try{
				fundDepositService.createFundDeposit(getUserSession(request), fundDeposit);
				response.getWriter().write(StrutsFormValidateUtil.getMessageCreateSuccess(messageResources).toString());
			}catch (PawnException ex){
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, getLocale(request)).toString());
			}
		}
		logger.debug("**** Leaving create method ****");
		return null;
	}
	public ActionForward approve(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{

		logger.debug("**** Enter in to authorize method ****");
		ActionMessages validateForm=form.validate(mapping, request);
		MessageResources messageResources=getResources(request,"message");

		if (!validateForm.isEmpty()){
			response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm, messageResources, getLocale(request), null).toString());
		}else{
			FundDeposit fundDeposit = fundDepositService.getFundDepositById(getUserSession(request), Integer.parseInt(request.getParameter("recordId")));
			fundDeposit.setRecordId(Integer.parseInt(request.getParameter("recordId")));
			fundDeposit.setVersion(Integer.parseInt(request.getParameter("version")));

			try {
				fundDepositService.approveFundDeposit(getUserSession(request), fundDeposit);
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
			FundDeposit fundDeposit= fundDepositService.getFundDepositById(getUserSession(request), Integer.valueOf(recordId));
			Branch branch=branchService.getBranchById(getUserSession(request),fundDeposit.getBranchId());
			Officer officer=null;
			if (Integer.valueOf(fundDeposit.getApprovedBy())!=0)
				officer=officerService.getOfficerById(getUserSession(request), Integer.valueOf(fundDeposit.getApprovedBy()));

			mainArray.put(fundDeposit.getDepositNo());
			mainArray.put(fundDeposit.getDepositDate());
			mainArray.put(fundDeposit.getDepositAmount());

			mainArray.put(branch.getBarnchName());
			mainArray.put(branch.getCode());
			mainArray.put(fundDeposit.getRecordId());
			mainArray.put(fundDeposit.getVersion());
			mainArray.put(fundDeposit.getApprovedBy());

		}else{
			List<FundDeposit>fundDepositList= (List<FundDeposit>) fundDepositService.getAllFundDeposit(getUserSession(request), getQueryCriteriaList(request)).getDataList();
			for (FundDeposit req:fundDepositList){
				Branch branch=branchService.getBranchById(getUserSession(request), req.getBranchId());
				Officer officer=null;
				
				if(Integer.valueOf(req.getApprovedBy())!=0)
					officer=officerService.getOfficerById(getUserSession(request), Integer.valueOf(req.getApprovedBy()));
				
				JSONArray subArray=new JSONArray();
				if(Integer.valueOf(req.getApprovedBy())==0){
					subArray.put(req.getDepositNo());
					subArray.put(StrutsFormValidateUtil.parseString(req.getDepositDate()));
					subArray.put(decimalFormat.format(req.getDepositAmount()));
	
					subArray.put(req.getApprovedBy()==0?"Not Approved":officer.getUserName());
					subArray.put(StrutsFormValidateUtil.parseString(req.getApprovedDate()));
	
					subArray.put(req.getTicketCount());
					subArray.put(decimalFormat.format(req.getTicketedAmount()));
	
					subArray.put(req.getReceiptCount());
					subArray.put(decimalFormat.format(req.getReceiptedAmount()));
	
					subArray.put(branch.getBarnchName());
					subArray.put(branch.getCode());
					subArray.put(req.getRecordId());
					subArray.put(req.getVersion());
					subArray.put(req.getApprovedBy());
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

		List<FundDeposit> fundDepositList= (List<FundDeposit>) fundDepositService.getAllFundDeposit(getUserSession(request), getAuthorizeQueryCriteriaList(request)).getDataList();

		for(FundDeposit req:fundDepositList){

			//Branch branch=branchService.getBranchById(getUserSession(request), req.getBranchId());
			Officer officer=null;
			if(Integer.valueOf(req.getApprovedBy())!=0)
				officer=officerService.getOfficerById(getUserSession(request), Integer.valueOf(req.getApprovedBy()));

			JSONArray subArray = new JSONArray();

			subArray.put(req.getDepositNo());
			subArray.put(StrutsFormValidateUtil.parseString(req.getDepositDate()));
			subArray.put(decimalFormat.format(req.getDepositAmount()));

			subArray.put(req.getApprovedBy()==0?"Not Approved":officer.getUserName());
			subArray.put(req.getApprovedDate());
			//subArray.put(branch.getBarnchName());
			//subArray.put(branch.getCode());
			subArray.put(req.getRecordId());
			subArray.put(req.getVersion());
			subArray.put(req.getApprovedBy());

			mainArray.put(subArray);
		}
		response.getWriter().write(mainArray.toString());

		return null;
	}

	public ActionForward getAjaxMasterData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to getGMasterData( method *****");
		UserConfig userConfig = SessionUtil.getUserSession(request);

		JSONArray mainArray = new JSONArray();

			List<Object[]> list = fundDepositService.getTiketsForTheDay(userConfig);
			List<Object[]> listReceipt = fundDepositService.getReceiptsForTheDay(userConfig);
			Branch branch=branchService.getBranchById(getUserSession(request), Integer.valueOf(userConfig.getBranchId()));
			Officer officer=null;

			if(list.size()!=0){
				for (Object[] objects : list) {
					if(objects[1]!=null){
						mainArray.put(objects[0].toString());
						mainArray.put(decimalFormat.format(objects[1]));
					}
					else
					{
						mainArray.put("0");
						mainArray.put("0.00");
					}
				}
			}
			else{
				mainArray.put("0");
				mainArray.put("0.00");
			}

			if(listReceipt.size()!=0){
				for (Object[] objects : listReceipt) {
					if(objects[1]!=null){
						mainArray.put(objects[0].toString());
						mainArray.put(decimalFormat.format(objects[1]));
					}
					else
					{
						mainArray.put("0");
						mainArray.put("0.00");
					}
				}
			}
			else{
				mainArray.put("0");
				mainArray.put("0.00");
			}

			mainArray.put(decimalFormat.format(branch.getFundLimit()));
			mainArray.put(decimalFormat.format(branch.getFundAvailable()));
			mainArray.put(simpdate.format(userConfig.getLoginDate()));
			mainArray.put(decimalFormat.format(branch.getFundAvailable()- branch.getFundLimit()));


		response.getWriter().write(mainArray.toString());
		logger.debug("**** Leaving getGMasterData method *****");
		return null;
	}


}


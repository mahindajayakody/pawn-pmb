package com.m4.pawning.web.action;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.json.JSONArray;
import org.json.JSONObject;

import com.m4.core.exception.PawnException;
import com.m4.core.util.MasterAction;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.QueryCriteria.Oparator;
import com.m4.core.util.SessionUtil;
import com.m4.core.util.StrutsFormValidateUtil;
import com.m4.pawning.domain.DueFrom;
import com.m4.pawning.domain.DueReceipt;
import com.m4.pawning.domain.OverPayment;
import com.m4.pawning.domain.Receipt;
import com.m4.pawning.domain.Ticket;
import com.m4.pawning.domain.TicketArticle;
import com.m4.pawning.dto.InfoConsoleDTO;
import com.m4.pawning.service.DueTypeService;
import com.m4.pawning.service.InfoconsoleService;
import com.m4.pawning.service.ReceiptService;
import com.m4.pawning.service.TicketService;
import com.m4.pawning.util.ReceiptStatusEnum;
import com.m4.pawning.util.TicketStatusEnum;


public class InfoconsoleAction extends MasterAction {
	private static final Logger logger = Logger.getLogger(InfoconsoleAction.class);
	private static DecimalFormat decimalFormat = new DecimalFormat();
	static{
		decimalFormat.setMaximumFractionDigits(2);
		decimalFormat.setMinimumFractionDigits(2);
	}
	private InfoconsoleService infoconsoleService;
	private DueTypeService dueTypeService;
	private TicketService ticketService;
	private ReceiptService receiptService;


	public void setInfoconsoleService(InfoconsoleService infoconsoleService) {
		this.infoconsoleService = infoconsoleService;
	}
	public void setDueTypeService(DueTypeService dueTypeService) {
		this.dueTypeService = dueTypeService;
	}
	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}
	public void setReceiptService(ReceiptService receiptService) {
		this.receiptService = receiptService;
	}

	public ActionForward getInfoData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MessageResources messageResources = getResources(request,"message");
		InfoConsoleDTO consoleDTO = null;
		int ticketId = Integer.parseInt(request.getParameter("ticketId"));

		try {
			consoleDTO = infoconsoleService.getInfoConsoleData(SessionUtil.getUserSession(request), ticketId);
		} catch (PawnException e) {
			response.getWriter().write(StrutsFormValidateUtil.getAJAXErrorMessage(e, messageResources, request.getLocale()));
			return null;
		}

		JSONObject object = new JSONObject();
		object.put("pawnerCode", consoleDTO.getPawnerCode());
		object.put("pawnerName", consoleDTO.getPawnerName());
		object.put("address", consoleDTO.getAddress());
		object.put("pawnAdvance", decimalFormat.format(consoleDTO.getPawnAdvance()));
		object.put("marketValue", decimalFormat.format(consoleDTO.getMarketValue()));
		object.put("actualDisValue", decimalFormat.format(consoleDTO.getActualDisValue()));
		object.put("totalNetWeight", consoleDTO.getTotalNetWeight());
		object.put("ticketDate", StrutsFormValidateUtil.parseString(consoleDTO.getTicketDate()));
		//object.put("authorizeDate", StrutsFormValidateUtil.parseString(consoleDTO.getTicketDate()));
		object.put("expiraryDate", StrutsFormValidateUtil.parseString(consoleDTO.getExpiraryDate()));
		//object.put("printedDate", StrutsFormValidateUtil.parseString(consoleDTO.getTicketDate()));
		object.put("schemeCode", consoleDTO.getSchemeCode());
		object.put("interestCode", consoleDTO.getInterestCode());
		object.put("schemeDesc", consoleDTO.getSchemeDescription());
		object.put("interestId", consoleDTO.getInterestId());
		object.put("totalreceipts", decimalFormat.format(consoleDTO.getTotalReceiptAmount()));
		object.put("ticketStatus", TicketStatusEnum.getEnumByCode(consoleDTO.getTicketStatusId()).toString());
//		object.put("closureDate", consoleDTO.getTicketCloseDate() == null ? "":consoleDTO.getTicketCloseDate());
//		object.put("closureDate", consoleDTO.getTicketCloseDate());

		JSONArray mainArray = new JSONArray();
		for(TicketArticle article:consoleDTO.getTicketArticleList()){
			JSONArray array = new JSONArray();
			array.put(article.getArticleDescription());
			array.put(article.getNetWeight());
			array.put(decimalFormat.format(article.getAssessedValue()));
			array.put(article.getNoOfItem());
			mainArray.put(array);
		}

		object.put("ticketArticleList", mainArray.toString());

		mainArray = new JSONArray();
		for(DueFrom dueFrom:consoleDTO.getDueFromList()){
			JSONArray array = new JSONArray();
			array.put(dueTypeService.getDueTypeById(SessionUtil.getUserSession(request), dueFrom.getDueTypeId()).getDescription());
			array.put(decimalFormat.format(dueFrom.getDueAmount()));
			array.put(decimalFormat.format(dueFrom.getPaidAmount()));
			array.put(decimalFormat.format(dueFrom.getBalanceAmount()));
			mainArray.put(array);
		}
		object.put("dueFromList", mainArray.toString());

		response.getWriter().write(object.toString());
		return null;
	}

	public ActionForward getInforDataForConsolNew(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MessageResources messageResources = getResources(request,"message");
		InfoConsoleDTO consoleDTO = null;
		int ticketId = Integer.parseInt(request.getParameter("ticketId"));

		try {
			consoleDTO = infoconsoleService.getInfoConsoleData(SessionUtil.getUserSession(request), ticketId);
		} catch (PawnException e) {
			response.getWriter().write(StrutsFormValidateUtil.getAJAXErrorMessage(e, messageResources, request.getLocale()));
			return null;
		}

		JSONObject object = new JSONObject();
		object.put("pawnerCode", consoleDTO.getPawnerCode());
		object.put("pawnerName", consoleDTO.getPawnerName());
		object.put("address", consoleDTO.getAddress());
		object.put("pawnAdvance", decimalFormat.format(consoleDTO.getPawnAdvance()));
		object.put("marketValue", decimalFormat.format(consoleDTO.getMarketValue()));
		object.put("actualDisValue", decimalFormat.format(consoleDTO.getActualDisValue()));
		object.put("totalNetWeight", consoleDTO.getTotalNetWeight());
		object.put("ticketDate", StrutsFormValidateUtil.parseString(consoleDTO.getTicketDate()));
		//object.put("authorizeDate", StrutsFormValidateUtil.parseString(consoleDTO.getTicketDate()));
		object.put("expiraryDate", StrutsFormValidateUtil.parseString(consoleDTO.getExpiraryDate()));
		//object.put("printedDate", StrutsFormValidateUtil.parseString(consoleDTO.getTicketDate()));
		object.put("schemeCode", consoleDTO.getSchemeCode());
		object.put("interestCode", consoleDTO.getInterestCode());
		object.put("schemeDesc", consoleDTO.getSchemeDescription());
		object.put("interestId", consoleDTO.getInterestId());
		object.put("totalreceipts", decimalFormat.format(consoleDTO.getTotalReceiptAmount()));
		object.put("ticketStatus", TicketStatusEnum.getEnumByCode(consoleDTO.getTicketStatusId()).toString());
		object.put("officerName", consoleDTO.getOfficerName());
		object.put("approveName", consoleDTO.getApproveUserName() != null ? consoleDTO.getApproveUserName() : "");
		object.put("closureDate", consoleDTO.getTicketCloseDate() == null?"":consoleDTO.getTicketCloseDate());
		object.put("auctionStatus", consoleDTO.getIsAuctioned() == 1 ? "Auctioned" : "");

		JSONArray mainArray = new JSONArray();
		for(TicketArticle article:consoleDTO.getTicketArticleList()){
			JSONArray array = new JSONArray();
			array.put(article.getArticleDescription());
			array.put(article.getNetWeight());
			array.put(decimalFormat.format(article.getAssessedValue()));
			array.put(article.getNoOfItem());
			array.put(article.getCartage().getDescription());
			array.put(decimalFormat.format(article.getCartage().getMarcketValue()));
			mainArray.put(array);
		}

		object.put("ticketArticleList", mainArray.toString());

		mainArray = new JSONArray();
		for(DueFrom dueFrom:consoleDTO.getDueFromList()){
			JSONArray array = new JSONArray();
			array.put(dueTypeService.getDueTypeById(SessionUtil.getUserSession(request), dueFrom.getDueTypeId()).getDescription());
			array.put(decimalFormat.format(dueFrom.getDueAmount()));
			array.put(decimalFormat.format(dueFrom.getPaidAmount()));
			array.put(decimalFormat.format(dueFrom.getBalanceAmount()));
			mainArray.put(array);
		}
		object.put("dueFromList", mainArray.toString());

		response.getWriter().write(object.toString());
		return null;
	}

	public ActionForward getReceiptDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		String ticketId = request.getParameter("ticketId");

		JSONArray mainArray = new JSONArray();
		if(ticketId==null || ticketId.equals("")){
			response.getWriter().write(mainArray.toString());
			return null;
		}

		List<QueryCriteria> queryList = new ArrayList<QueryCriteria>();
		queryList.add(new QueryCriteria("ticketId",Oparator.EQUAL,Integer.parseInt(ticketId)));
		queryList.add(new QueryCriteria("status", Oparator.EQUAL, ReceiptStatusEnum.ACTIVE.getCode()));
		List<Receipt> list = receiptService.getAllReceipt(SessionUtil.getUserSession(request), queryList).getDataList();

		for(Receipt receipt:list){
			JSONArray array = new JSONArray();
			array.put(receipt.getReceiptNo());
			array.put(StrutsFormValidateUtil.parseString(receipt.getReceiptDate()));
			array.put(decimalFormat.format(receipt.getReceiptAmt()));
			array.put(receipt.getReceiptEnteredUser());
			array.put(receipt.getDescription());
			array.put(receipt.getReceiptId());

			JSONArray mainDueArray = new JSONArray();
			for(DueReceipt dueReceipt:receipt.getDueReceipts()){
				JSONArray subDueArray = new JSONArray();
				subDueArray.put(dueTypeService.getDueTypeById(SessionUtil.getUserSession(request), dueReceipt.getDueTypeId()).getDescription());
				subDueArray.put(dueReceipt.getRefNumber());
				subDueArray.put(decimalFormat.format(dueReceipt.getSettleAmount()));
				mainDueArray.put(subDueArray);
			}
//			if(receipt.getOverpayments()!=null){
//				for (OverPayment overPayment : receipt.getOverpayments()) {
//					JSONArray subDueArray = new JSONArray();
//					subDueArray.put(dueTypeService.getDueTypeById(SessionUtil.getUserSession(request), overPayment.getDueTypeId()).getDescription());
//					subDueArray.put("");
//					subDueArray.put(decimalFormat.format(overPayment.getOvpAmount()));
//					mainDueArray.put(subDueArray);
//				}
//			}
			array.put(mainDueArray);
			mainArray.put(array);
		}

		response.getWriter().write(mainArray.toString());
		return null;
	}

	public ActionForward getTicket(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		MessageResources messageResources = getResources(request,"message");
    	Ticket ticket = null;
 		String code   = request.getParameter("code");

 		try {
 			ticket = ticketService.getTicketByTicketNumber(SessionUtil.getUserSession(request), code);
 		}catch(PawnException ex){
			response.getWriter().write(StrutsFormValidateUtil.getAJAXErrorMessage(ex, messageResources, request.getLocale()));
			return null;
 		}
 		response.getWriter().write(StrutsFormValidateUtil.getAJAXReferenceData(ticket.getTicketId(),""+ticket.getTicketId()));
		return null;
	}
	public ActionForward getTicketWithoutStatus(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		MessageResources messageResources = getResources(request,"message");
    	Ticket ticket = null;
 		String code   = request.getParameter("code");

 		try {
 			ticket = ticketService.getTicketWithoutStatus(SessionUtil.getUserSession(request), code);
 		}catch(PawnException ex){
			response.getWriter().write(StrutsFormValidateUtil.getAJAXErrorMessage(ex, messageResources, request.getLocale()));
			return null;
 		}
 		response.getWriter().write(StrutsFormValidateUtil.getAJAXReferenceData(ticket.getTicketId(),""+ticket.getTicketId()));
		return null;
	}

	@Override
	protected ActionForward getAuthorizeData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}
}

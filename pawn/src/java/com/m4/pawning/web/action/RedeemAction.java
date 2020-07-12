package com.m4.pawning.web.action;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.m4.core.exception.PawnException;
import com.m4.core.util.MasterAction;
import com.m4.core.util.SessionUtil;
import com.m4.core.util.StrutsFormValidateUtil;
import com.m4.pawning.domain.DueFrom;
import com.m4.pawning.domain.TicketArticle;
import com.m4.pawning.dto.InfoConsoleDTO;
import com.m4.pawning.service.DueTypeService;
import com.m4.pawning.service.InfoconsoleService;
import com.m4.pawning.service.RedeemService;

public class RedeemAction extends MasterAction {
	private final static Logger logger = Logger.getLogger(RedeemAction.class);	
	private static DecimalFormat decimalFormat = new DecimalFormat();
	static{
		decimalFormat.setMaximumFractionDigits(2);
		decimalFormat.setMinimumFractionDigits(2);
	}
	
	private RedeemService redeemService;
	private InfoconsoleService infoconsoleService;
	private DueTypeService dueTypeService;
	
	public void setRedeemService(RedeemService redeemService) {
		this.redeemService = redeemService;
	}
	public void setInfoconsoleService(InfoconsoleService infoconsoleService) {
		this.infoconsoleService = infoconsoleService;
	}
	public void setDueTypeService(DueTypeService dueTypeService) {
		this.dueTypeService = dueTypeService;
	}
	
	public ActionForward redeemTicket(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to redeemTicket method ****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");
		String ticketId = request.getParameter("ticketId");

		if(!validateForm.isEmpty()){
        	response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
        }else{
        	try {
        		redeemService.redeem(SessionUtil.getUserSession(request), Integer.parseInt(ticketId));
        		JSONObject messageObject = new JSONObject();
            	try{
            		messageObject.put("success",messageResources.getMessage("msg.redeemsuccess"));
            	}catch(JSONException jex){}
        		response.getWriter().write(messageObject.toString());
			} catch (PawnException ex) {
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
			}
        }

		logger.debug("**** Leaving from redeemTicket method ****");
		return null;
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
	
	protected ActionForward getAuthorizeData(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		return null;
	}
}

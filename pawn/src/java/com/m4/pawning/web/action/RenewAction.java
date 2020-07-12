package com.m4.pawning.web.action;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.m4.pawning.domain.DueType;
import com.m4.pawning.domain.Schemes;
import com.m4.pawning.domain.Ticket;
import com.m4.pawning.domain.TicketArticle;
import com.m4.pawning.dto.InfoConsoleDTO;
import com.m4.pawning.service.DueTypeService;
import com.m4.pawning.service.InfoconsoleService;
import com.m4.pawning.service.RedeemService;
import com.m4.pawning.service.SchemeService;
import com.m4.pawning.service.TicketService;

public class RenewAction extends MasterAction {
	private final static Logger logger = Logger.getLogger(RenewAction.class);	
	private static DecimalFormat decimalFormat = new DecimalFormat();
	static{
		decimalFormat.setMaximumFractionDigits(2);
		decimalFormat.setMinimumFractionDigits(2);
	}
	
	private RedeemService redeemService;
	private InfoconsoleService infoconsoleService;
	private DueTypeService dueTypeService;
	private TicketService ticketService;
	private SchemeService schemeService;
	
	public TicketService getTicketService() {
		return ticketService;
	}
	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}
	public void setRedeemService(RedeemService redeemService) {
		this.redeemService = redeemService;
	}
	public void setInfoconsoleService(InfoconsoleService infoconsoleService) {
		this.infoconsoleService = infoconsoleService;
	}
	public void setDueTypeService(DueTypeService dueTypeService) {
		this.dueTypeService = dueTypeService;
	}
	
	/**
	 * @param schemeService the schemeService to set
	 */
	public void setSchemeService(SchemeService schemeService) {
		this.schemeService = schemeService;
	}
	// TODO: add previous ticket ID to renewed ticket
	public ActionForward renewTicket(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.debug("**** Entering to renewicket method ****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");
		String ticketIdS = request.getParameter("ticketId");
		String itemData = request.getParameter("renewArticles");
		String schemaId = request.getParameter("schemaId");
		String period = request.getParameter("period");
		

		if(!validateForm.isEmpty()){
        	response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
        }else{
        	try {
        		int ticketId = Integer.parseInt(ticketIdS);
        		// Retrieve old ticket information
        		// InfoConsoleDTO consoleDTO = infoconsoleService.getInfoConsoleData(SessionUtil.getUserSession(request), ticketId);	
        		Ticket ticket = ticketService.getBulkTicketById(SessionUtil.getUserSession(request), ticketId);
        		
        		// Set new Schema Id to old ticket
        		// After authorization interestSlab will be genarated
        		ticket.setSchemeId(Integer.parseInt(schemaId));
        		ticket.setPeriod(Integer.parseInt(period));
        		
        		// Set Old ticketId as RenewedTicketNumber for tracking
        		ticket.setRenewedTicketNumber(ticketId);

        		// Reedem the existing ticket
        		//Modofied By Mahinda on 16-Sept-2011
        		redeemService.renew(SessionUtil.getUserSession(request), ticketId);
        		
        		Schemes schemes= schemeService.getSchemeById(SessionUtil.getUserSession(request), ticket.getSchemeId());
                	
               	List<TicketArticle> list = new ArrayList<TicketArticle>();
               	
               	// Get the article information from old ticket
           		if(itemData!=null && !itemData.equals("")){
            		
            		// Populate article list

               		Set<TicketArticle>  articleSet = ticket.getTicketArticleSet();
               		double ticketArticleId;
               		double totalNetWeight = 0,totalGrossWeight = 0,totalAssedValue = 0,totalMarketValue = 0,pawnAdvance= 0;
                 	TicketArticle ticketArticle;
                   	String[] rows = itemData.split("<row>");
                   	
                   	//Modified By Mahinda on 02-Oct-2011
                   	List<DueFrom> dueFromList =(List<DueFrom>)ticket.getDueFromCollection();
            		//List<DueFrom> dueFromList =(List<DueFrom>)ticketService.getTicketById(SessionUtil.getUserSession(request), ticketId).getDueFromCollection();
            		for (DueFrom dueFrom : dueFromList) {
            			if (dueFrom.getDueTypeId() ==1) {
            				pawnAdvance = dueFrom.getBalanceAmount();
            			}
            		}

                     	
               		for(String row:rows){
            			ticketArticleId = Double.parseDouble(row);
            			
            			for (Iterator<TicketArticle> iterator = articleSet.iterator(); iterator.hasNext();) {
            				TicketArticle ticketArticleOriginal = (TicketArticle) iterator.next();
            				
            				// Add only the selected articles from renew UI 
            				if(ticketArticleOriginal.getTicketArticleId() == ticketArticleId){
            					ticketArticle = new TicketArticle();
                				ticketArticle.setArticleId(ticketArticleOriginal.getArticleId());
                				ticketArticle.setArticleModelId(ticketArticleOriginal.getArticleModelId());
                				ticketArticle.setMarcketValue(ticketArticleOriginal.getMarcketValue());
                				totalMarketValue = totalMarketValue + ticketArticleOriginal.getMarcketValue();
                				ticketArticle.setAssessedValue(ticketArticleOriginal.getAssessedValue());
                				totalAssedValue  = totalAssedValue + ticketArticleOriginal.getAssessedValue();
                				ticketArticle.setNetWeight(ticketArticleOriginal.getNetWeight());
                				totalNetWeight = totalNetWeight + ticketArticleOriginal.getNetWeight();
                				ticketArticle.setGrossWeight(ticketArticleOriginal.getGrossWeight());
                				totalGrossWeight = totalGrossWeight + ticketArticleOriginal.getGrossWeight();
                				ticketArticle.setArticleDescription(ticketArticleOriginal.getArticleDescription());
                				ticketArticle.setNoOfItem(ticketArticleOriginal.getNoOfItem());
                				ticketArticle.setCartage(ticketArticleOriginal.getCartage());
                       			ticketArticle.setProductId(ticket.getProductId());
                       			
                     			list.add(ticketArticle);

                     			// make sure we remove used article
                     			articleSet.remove(ticketArticleOriginal);
                     			break;
                			}

						}
               		}
               		ticket.setInterestSlabId(schemes.getInterestId());
                   	ticket.setTotalNoOfItems(list.size());
                	ticket.setTotalNetWeight(totalNetWeight);
                	ticket.setTotalGrossWeight(totalGrossWeight);
                	ticket.setTotalAssedValue(totalAssedValue);
                	ticket.setTotalMarketValue(totalMarketValue);
                	ticket.setSystemAssedValue(totalMarketValue);
                	ticket.setPawnAdvance(pawnAdvance);
                	ticket.setTotalCapitalOutstanding(pawnAdvance);
            		ticket.setTicketClosedRenewalDate(null);
            	}else{
            		throw new PawnException("errors.articalsnotfound");
            	}
        		
        		// Renew selected articles to a new ticket
        		String number = ticketService.createTicket(SessionUtil.getUserSession(request), ticket , list,true);
        		
        		// Populate return ajax message
             	JSONObject object = StrutsFormValidateUtil.getMessageCreateSuccess(messageResources);
             	
             	object.put("success",messageResources.getMessage("msg.renewsuccess") + "with ticket number :" +number);
            	response.getWriter().write(object.toString());
  			} catch (PawnException pawnException) {
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(pawnException, messageResources, request.getLocale()).toString());
			} catch (NumberFormatException numberFormatException) {
				response.getWriter().write(messageResources.getMessage("errors.numberformaterror"));
			}catch(JSONException jex){
				logger.debug("JSON Exception Occureed " + jex);
			}
        }

		logger.debug("**** Leaving from redeemTicket method ****");
		return null;
	}
	
	// TODO : Re-factor -> ticket info, reedem info, renew info
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
		object.put("period", consoleDTO.getPeriod());
		
		JSONArray mainArray = new JSONArray();
		for(TicketArticle article:consoleDTO.getTicketArticleList()){
			JSONArray array = new JSONArray();
			array.put("");
			array.put(article.getArticleDescription());
			array.put(article.getNetWeight());
			array.put(decimalFormat.format(article.getAssessedValue()));
			array.put(article.getNoOfItem());
			array.put(article.getRecordId());
			mainArray.put(array);
		}
		
		object.put("ticketArticleList", mainArray.toString());
		
		List<DueType> dueTypeList = dueTypeService.getAllDueType(SessionUtil.getUserSession(request), null).getDataList();
		Map<Integer, DueType> dueMap = new HashMap<Integer, DueType>();
		
		for(DueType dueType:dueTypeList)
			dueMap.put(dueType.getDueTypeId(), dueType);
		
		mainArray = new JSONArray();
		for(DueFrom dueFrom:consoleDTO.getDueFromList()){
			JSONArray array = new JSONArray();
			//array.put(dueTypeService.getDueTypeById(SessionUtil.getUserSession(request), dueFrom.getDueTypeId()).getDescription());
			DueType dueType = dueMap.get(dueFrom.getDueTypeId());
			array.put(dueType.getDescription());
			array.put(decimalFormat.format(dueFrom.getDueAmount()));
			array.put(decimalFormat.format(dueFrom.getPaidAmount()));
			array.put(decimalFormat.format(dueFrom.getBalanceAmount()));
			array.put(dueType.getDueType());
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

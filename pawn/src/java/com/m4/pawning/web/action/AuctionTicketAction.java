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
import org.json.JSONObject;

import com.m4.core.exception.PawnException;
import com.m4.core.util.MasterAction;
import com.m4.core.util.SessionUtil;
import com.m4.core.util.StrutsFormValidateUtil;
import com.m4.pawning.domain.AuctionLocation;
import com.m4.pawning.domain.AuctionMaster;
import com.m4.pawning.domain.AuctionTicket;
import com.m4.pawning.domain.Pawner;
import com.m4.pawning.domain.Product;
import com.m4.pawning.domain.Ticket;
import com.m4.pawning.domain.TicketArticle;
import com.m4.pawning.dto.InfoConsoleDTO;
import com.m4.pawning.service.AuctionLocationService;
import com.m4.pawning.service.AuctionService;
import com.m4.pawning.service.AuctionTicketService;
import com.m4.pawning.service.BranchService;
import com.m4.pawning.service.InfoconsoleService;
import com.m4.pawning.service.PawnerService;
import com.m4.pawning.service.ProductService;
import com.m4.pawning.service.TicketService;

public class AuctionTicketAction extends MasterAction {
	private static final Logger logger =Logger.getLogger(AuctionTicketAction.class);
	private static final SimpleDateFormat simpdate = new SimpleDateFormat("dd/MM/yyyy");
	
	private static DecimalFormat decimalFormat = new DecimalFormat();
	static{
		decimalFormat.setMaximumFractionDigits(2);
		decimalFormat.setMinimumFractionDigits(2);
	}
	private InfoconsoleService infoconsoleService;
	private ProductService productService;
	private AuctionLocationService locationService;
	private PawnerService pawnerService;	
	private BranchService branchService;
	private AuctionTicketService auctionTicketService;
	private TicketService ticketService;	
	private AuctionService auctionService;
	
	
	public void setAuctionService(AuctionService auctionService) {
		this.auctionService = auctionService;
	}
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	public void setAuctionLocation(AuctionLocationService locationService) {
		this.locationService = locationService;
	}
	public void setPawnerService(PawnerService pawnerService) {
		this.pawnerService = pawnerService;
	}
	
	public void setBranchService(BranchService branchService) {
		this.branchService = branchService;
	}
	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}
	
	public void setInfoconsoleService(InfoconsoleService infoconsoleService) {
		this.infoconsoleService = infoconsoleService;
	}

	
	public void setAuctionTicketService(AuctionTicketService auctionTicketService) {
		this.auctionTicketService = auctionTicketService;
	}
	public ActionForward allocate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to allocate method *****");
		//ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");
		int auctionId = Integer.parseInt(request.getParameter("auctionId"));
		System.out.println(auctionId);
		String tickets[] = request.getParameter("tickets").split("<@>");
		Integer[] intTickets = new Integer[tickets.length];

		for(int i=0;i<tickets.length;i++)
			intTickets[i] = Integer.parseInt(tickets[i]);

		try {
    		auctionTicketService.allocate(SessionUtil.getUserSession(request),intTickets,getQueryCriteriaList(request),auctionId );
    		response.getWriter().write(StrutsFormValidateUtil.getMessageCreateSuccess(messageResources).toString());
    	} catch (PawnException ex) {
			response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
		}
		logger.debug("**** Leaving allocate method *****");
		return null;
	}
	
	
	@Override
	protected ActionForward getAuthorizeData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public ActionForward getAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to getAjaxData method *****");
		String recordId = request.getParameter("auctionId");

		JSONArray mainArray = new JSONArray();

		if(recordId!=null && recordId!="" && recordId!="0"){
			AuctionMaster auction = (AuctionMaster)auctionService.getAuctionById(getUserSession(request), Integer.valueOf(recordId));
			Product product	= (Product)productService.getProductById(getUserSession(request), auction.getProductId());
			Pawner pawner   = (Pawner)pawnerService.getPawnerById(getUserSession(request), auction.getAuctioneeId());
			Pawner resperson=(Pawner)pawnerService.getPawnerById(getUserSession(request), auction.getResposiblePerson());
			AuctionLocation location = (AuctionLocation)locationService.getAuctionLocationById(getUserSession(request), auction.getLocationId());
			
						
			mainArray.put(auction.getCode());
			mainArray.put(auction.getDescription());
			mainArray.put(Integer.parseInt(auction.getStatus())==2?"Inactive":"Active");
			
			mainArray.put(auction.getAuctionExpences());
			mainArray.put(auction.getNoOfTicket());
			mainArray.put(auction.getProfit());
			mainArray.put(auction.getTax());
			mainArray.put(auction.getTotalCapital());
			mainArray.put(auction.getTotalEarned());
			mainArray.put(auction.getTotalInterest());
			mainArray.put(simpdate.format(auction.getAuctionDate()));
			
			mainArray.put(auction.getRecordId());
			mainArray.put(auction.getVersion());
			
			mainArray.put(product.getCode());
			mainArray.put(product.getDescription());
			
			//Office Details
			mainArray.put(resperson.getRecordId());
			mainArray.put(resperson.getPawnerCode());
			mainArray.put(resperson.getPawnerName());
			
			//Actioneer
			mainArray.put(pawner.getPawnerCode());
			mainArray.put(pawner.getRecordId());
			mainArray.put(pawner.getPawnerName());
			
			mainArray.put(location.getCode());
			mainArray.put(location.getRecordId());
			mainArray.put(location.getDescription());	
			mainArray.put(auction.getStatus());
			
			
		}else{

			List<AuctionMaster> auctionList = (List<AuctionMaster>) auctionService.getAllAuction(SessionUtil.getUserSession(request), getQueryCriteriaList(request)).getDataList();
			for (AuctionMaster auct:auctionList){
				Pawner pawner   = (Pawner)pawnerService.getPawnerById(getUserSession(request), auct.getAuctioneeId());
				Product product = (Product)productService.getProductById (getUserSession(request), auct.getProductId());
				
				Pawner resperson=(Pawner)pawnerService.getPawnerById(getUserSession(request), auct.getResposiblePerson());
				AuctionLocation location=(AuctionLocation) locationService.getAuctionLocationById(getUserSession(request), auct.getLocationId());

				JSONArray subArray=new JSONArray();
				
				subArray.put(auct.getCode());
				subArray.put(auct.getDescription());
				
				subArray.put(Integer.parseInt(auct.getStatus())==2?"Inactive":"Active");
				
				subArray.put(auct.getAuctionExpences());
				subArray.put(auct.getNoOfTicket());
				subArray.put(auct.getProfit());
				subArray.put(auct.getTax());
				subArray.put(auct.getTotalCapital());
				subArray.put(auct.getTotalEarned());
				subArray.put(auct.getTotalInterest());
				subArray.put(simpdate.format(auct.getAuctionDate()));
				
				subArray.put(auct.getRecordId());
				subArray.put(auct.getVersion());
				
				subArray.put(product.getCode());
				subArray.put(product.getDescription());
				//Office Details
				subArray.put(resperson.getRecordId());
				subArray.put(resperson.getPawnerCode());
				subArray.put(resperson.getPawnerName());
				
				//Actioneer
				subArray.put(pawner.getRecordId());
				subArray.put(pawner.getPawnerCode());
				subArray.put(pawner.getPawnerName());
				
				
				subArray.put(location.getRecordId());
				subArray.put(location.getCode());
				subArray.put(location.getDescription());	
				subArray.put(auct.getStatus());
				
				mainArray.put(subArray);				
			}
		}
		response.getWriter().write(mainArray.toString());
		logger.debug("**** Leaving getAjaxData method *****");
		return null;
	}	
	
	
	public ActionForward getActiveAuction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to getAjaxData method *****");
		String recordId = request.getParameter("recordId");
//		int isCommon = Integer.parseInt(request.getParameter("isCommon"));

		JSONArray mainArray = new JSONArray();

		if(recordId!=null && recordId!="" && recordId!="0"){
			AuctionMaster auction = (AuctionMaster)auctionService.getAuctionById(getUserSession(request), Integer.valueOf(recordId));
			
						
			mainArray.put(auction.getCode());
			mainArray.put(auction.getDescription());
		
			mainArray.put(Integer.parseInt(auction.getStatus())==2?"Inactive":"Active");
			mainArray.put(auction.getAuctionId());
			
			mainArray.put(auction.getAuctionExpences());
			mainArray.put(auction.getNoOfTicket());
			mainArray.put(auction.getProfit());
			mainArray.put(auction.getTax());
			mainArray.put(auction.getTotalCapital());
			mainArray.put(auction.getTotalEarned());
			mainArray.put(auction.getTotalInterest());
			mainArray.put(simpdate.format(auction.getAuctionDate()));
			
			mainArray.put(auction.getRecordId());
			mainArray.put(auction.getVersion());
			
			
			
		}else{
			List<AuctionMaster> auctionList =null;
//			if(isCommon == 1){
//				auctionList = (List<AuctionMaster>) auctionService.getAllCommonAuction(SessionUtil.getUserSession(request), isCommon);
//			}else{
				auctionList = (List<AuctionMaster>) auctionService.getAllActiveAuction(SessionUtil.getUserSession(request), getQueryCriteriaList(request)).getDataList();
//			}
			for (AuctionMaster auct:auctionList){
				
				JSONArray subArray=new JSONArray();
				
				subArray.put(auct.getCode());
				subArray.put(auct.getDescription());
				
				subArray.put(Integer.parseInt(auct.getStatus())==2?"Inactive":"Active");
				subArray.put(auct.getAuctionId());
				subArray.put(auct.getAuctionExpences());
				subArray.put(auct.getNoOfTicket());
				subArray.put(auct.getProfit());
				subArray.put(auct.getTax());
				subArray.put(auct.getTotalCapital());
				subArray.put(auct.getTotalEarned());
				subArray.put(auct.getTotalInterest());
				subArray.put(simpdate.format(auct.getAuctionDate()));
				
				subArray.put(auct.getRecordId());
				subArray.put(auct.getVersion());
				
					
				subArray.put(auct.getStatus());
				
				mainArray.put(subArray);				
			}
		}
		response.getWriter().write(mainArray.toString());
		logger.debug("**** Leaving getAjaxData method *****");
		return null;
	}	

	public ActionForward getAuctionTickets(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to getAjaxData method *****");
		String recordId = request.getParameter("recordId");
		String branchid= request.getParameter("branchId");
		String sql = "";
		List<Object[]> list = null;
		JSONArray mainArray = new JSONArray();
	
		sql += " AND t.ticketStatusId=4 AND t.isAuctioned=0 AND t.branchId = "+branchid;
		
			list = auctionService.getAllLapsTickets(SessionUtil.getUserSession(request),sql);
			for(Object object[]:list){
				JSONArray array = new JSONArray();
				array.put("");
				array.put(object[1]);
				array.put(object[2] + " " + object[3] + " " + object[4]);
				array.put(object[0]);
				array.put(object[5]);	
				array.put(object[6]);
				mainArray.put(array);
			}
			
		
		
		response.getWriter().write(mainArray.toString());
		logger.debug("**** Leaving getAjaxData method *****");
		return null;
	}	

	public ActionForward getArticle(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MessageResources messageResources = getResources(request,"message");
		int ticketId = Integer.parseInt(request.getParameter("ticketId"));
		JSONObject object = new JSONObject();
		InfoConsoleDTO consoleDTO = null;
		
		
		try {
			consoleDTO = infoconsoleService.getInfoConsoleData(SessionUtil.getUserSession(request), ticketId);			
		} catch (PawnException e) {
			response.getWriter().write(StrutsFormValidateUtil.getAJAXErrorMessage(e, messageResources, request.getLocale()));
			return null;
		}
		JSONArray mainArray = new JSONArray();
		for(TicketArticle article:consoleDTO.getTicketArticleList()){
			JSONArray array = new JSONArray();
			array.put(article.getArticleDescription());
			array.put(article.getNetWeight());
			array.put(decimalFormat.format(article.getAssessedValue()));
			array.put(article.getNoOfItem());
			mainArray.put(array);
		}
				
		response.getWriter().write(mainArray.toString());
		return null;
	}
	
	public ActionForward create(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("**** Enter in to create Method ****");
		ActionMessages validateForm=form.validate(mapping, request);
		MessageResources messageResources= getResources(request, "message");
		
		int auctionId= Integer.parseInt(request.getParameter("auctionId"));
		int branchId = Integer.parseInt(request.getParameter("branchId"));
		int productId= Integer.parseInt(request.getParameter("productId"));
		Double upsetValue = Double.parseDouble(request.getParameter("upsetvalue"));
		
		String tikets[] = request.getParameter("tikets").split("<@>");
		Integer[] intTikets = new Integer[tikets.length];

		for(int i=0;i<tikets.length;i++)
			intTikets[i] = Integer.parseInt(tikets[i]);
		
		
		if(!validateForm.isEmpty())
			response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm, messageResources, getLocale(request), null).toString());
		else if(intTikets.length>0)
			{	try {
					auctionService.createAuctionMark(SessionUtil.getUserSession(request), branchId, productId, auctionId, intTikets,upsetValue);
					response.getWriter().write(StrutsFormValidateUtil.getMessageCreateSuccess(messageResources).toString());
				} catch (PawnException ex) {
					response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, getLocale(request)).toString());
				}			
		}
		logger.info("**** Leaving create Method ****");
		return null;
	}
	
	public ActionForward getAllAuctionMarkTicket(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("**** Enter in to getAllAuctionMarkTicket Method ****");
		
		List<AuctionTicket> listAuctionTicket = auctionTicketService.getAllAuctionMarkTicket(getUserSession(request), getQueryCriteriaList(request)).getDataList();
		JSONArray mainArray = new JSONArray();
		for (AuctionTicket auctionTicket : listAuctionTicket) {
			JSONArray subArray = new JSONArray();
			Ticket ticket = ticketService.getTicketById(getUserSession(request), auctionTicket.getTicketId());
			Pawner pawner = ticket.getPawner();
			subArray.put("");
			subArray.put(ticket.getTicketNumber());
			subArray.put(pawner.getTitle() + " " + pawner.getInitials() + " " + pawner.getSurName());
			subArray.put(auctionTicket.getAuctionExpences());
			subArray.put(auctionTicket.getMinimumCapital());
			subArray.put(auctionTicket.getMinimumInterest());
			subArray.put(auctionTicket.getExcessAmount());
			subArray.put(auctionTicket.getAssignDate());
			subArray.put(auctionTicket.getAuctionId());
			subArray.put(auctionTicket.getTicketId());
			mainArray.put(subArray);
		}
		response.getWriter().write(mainArray.toString());	
		logger.info("**** Leaving getAllAuctionMarkTicket Method ****");
		
		return null;
	}
	
	
	
	
	
}

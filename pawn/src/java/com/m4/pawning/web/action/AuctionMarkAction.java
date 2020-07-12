package com.m4.pawning.web.action;

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
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.RecordStatusEnum;
import com.m4.core.util.SessionUtil;
import com.m4.core.util.StrutsFormValidateUtil;
import com.m4.core.util.QueryCriteria.Oparator;
import com.m4.pawning.domain.AuctionLocation;
import com.m4.pawning.domain.AuctionMaster;
import com.m4.pawning.domain.Branch;
import com.m4.pawning.domain.Pawner;
import com.m4.pawning.domain.Product;
import com.m4.pawning.service.AuctionLocationService;
import com.m4.pawning.service.AuctionService;
import com.m4.pawning.service.BranchService;
import com.m4.pawning.service.PawnerService;
import com.m4.pawning.service.ProductService;

public class AuctionMarkAction extends MasterAction {
	private static final Logger logger=Logger.getLogger(AuctionMarkAction.class);
	private ProductService productService;
	private AuctionLocationService locationService;
	private PawnerService pawnerService;	
	private AuctionService auctionService;
	private BranchService branchService;
	
	private static final SimpleDateFormat simpdate = new SimpleDateFormat("dd/MM/yyyy");
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	public void setAuctionLocation(AuctionLocationService locationService) {
		this.locationService = locationService;
	}
	public void setPawnerService(PawnerService pawnerService) {
		this.pawnerService = pawnerService;
	}
	public void setAuctionService(AuctionService auctionService) {
		this.auctionService = auctionService;
	}
	public void setBranchService(BranchService branchService) {
		this.branchService = branchService;
	}
	public ActionForward create(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("**** Enter in to create Method ****");
		ActionMessages validateForm=form.validate(mapping, request);
		MessageResources messageResources= getResources(request, "message");
		
		if(!validateForm.isEmpty())
			response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm, messageResources, getLocale(request), null).toString());
		else{
			AuctionMaster auction = new AuctionMaster();
			auction.setProductId(Integer.parseInt(request.getParameter("productId")));
			auction.setAuctioneeId(Integer.parseInt(request.getParameter("actioneeId")));
			auction.setLocationId(Integer.parseInt(request.getParameter("locationId")));
			auction.setCode(request.getParameter("code"));
			auction.setDescription(request.getParameter("description"));
			auction.setResposiblePerson(Integer.parseInt(request.getParameter("officeId")));
			auction.setStatus("1");
			auction.setAuctionDate(StrutsFormValidateUtil.parseDate(request.getParameter("auctionDate")));
			
			try {
				auctionService.createAuction(getUserSession(request), auction);
				response.getWriter().write(StrutsFormValidateUtil.getMessageCreateSuccess(messageResources).toString());
			} catch (PawnException ex) {
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, getLocale(request)).toString());
			}			
		}
		logger.info("**** Leaving create Method ****");
		return null;
	}

	public ActionForward update(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("**** Enter in to update Method ****");
		ActionMessages validateForm=form.validate(mapping, request);
		MessageResources messageResources= getResources(request, "message");
		
		if(!validateForm.isEmpty())
			response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm, messageResources, getLocale(request), null).toString());
		else{
			AuctionMaster auction=new AuctionMaster();
			
			auction.setProductId(Integer.parseInt(request.getParameter("productId")));
			auction.setAuctioneeId(Integer.parseInt(request.getParameter("actioneeId")));
			auction.setLocationId(Integer.parseInt(request.getParameter("locationId")));
			auction.setCode(request.getParameter("code"));
			auction.setDescription(request.getParameter("description"));
			auction.setResposiblePerson(Integer.parseInt(request.getParameter("officeId")));
			auction.setProductId(Integer.parseInt(request.getParameter("productId")));
			auction.setAuctionDate(StrutsFormValidateUtil.parseDate(request.getParameter("auctionDate")));
			auction.setVersion(Integer.parseInt(request.getParameter("version")));
			auction.setAuctionId(Integer.parseInt(request.getParameter("recordId")));
			auction.setStatus(request.getParameter("isActive"));
		
			try {
				auctionService.updateAuction(getUserSession(request), auction);
				response.getWriter().write(StrutsFormValidateUtil.getMessageUpdateSuccess(messageResources).toString());
			} catch (PawnException ex) {
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, getLocale(request)).toString());
			}			
		}
		logger.info("**** Leaving update Method ****");
		
		return null;
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("**** Enter in to remove Method ****");
		ActionMessages validateForm=form.validate(mapping, request);
		MessageResources messageResources= getResources(request, "message");
		
		if(!validateForm.isEmpty())
			response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm, messageResources, getLocale(request), null).toString());
		else{
			AuctionMaster auction=new AuctionMaster();
			auction.setRecordId(Integer.parseInt(request.getParameter("recordId")));
			auction.setVersion(Integer.parseInt(request.getParameter("version")));
			
			try {
				auctionService.createAuction(getUserSession(request), auction);
				response.getWriter().write(StrutsFormValidateUtil.getMessageDeleteSuccess(messageResources).toString());
			} catch (PawnException ex) {
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, getLocale(request)).toString());
			}			
		}
		logger.info("**** Leaving remove Method ****");
		
		return null;
	}
	public ActionForward authorize(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.debug("**** Enter in to authorize method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		String recordId = request.getParameter("recordId");
        String version  = request.getParameter("version");
        String authorizeType   = request.getParameter("authorizeMode");
        boolean authorize = Boolean.parseBoolean(request.getParameter("authorizeValue"));

        if((recordId==null)||(version==null)||(recordId.equals(""))||(version.equals(""))){
        	response.getWriter().write(StrutsFormValidateUtil.getMessageNotFound(messageResources).toString());
        	return null;
        }
        AuctionMaster auction=new AuctionMaster();
        auction.setVersion(Integer.parseInt(request.getParameter("version")));
        auction.setRecordId(Integer.parseInt(request.getParameter("recordId")));
        
        try{
        	if(authorizeType.equals("Create") ){
        		auctionService.authorizeCreation(SessionUtil.getUserSession(request), auction, authorize);
        	}else if( authorizeType.equals("Update") ){
        		auctionService.authorizeUpdation(SessionUtil.getUserSession(request), auction, authorize);
        	}else if( authorizeType.equals("Delete") ){
        		auctionService.authorizeDeletion(SessionUtil.getUserSession(request), auction, authorize);
	    	}
        }catch(PawnException ex){
	    	logger.error("exception in authorization" + ex.getExceptionCode());
	    	response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, getLocale(request)).toString());
	    	return null;
        }

        if(authorize==true){
        	response.getWriter().write(StrutsFormValidateUtil.getMessageAuthorizeSuccess(messageResources).toString());
        }else{
        	response.getWriter().write(StrutsFormValidateUtil.getMessageRejectSuccess(messageResources).toString());
        }
        logger.debug("**** Leaving authorize method *****");
		return null;
	}
	
	public ActionForward getAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to getAjaxData method *****");
		String recordId = request.getParameter("recordId");

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
	
	protected ActionForward getAuthorizeData(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		JSONArray mainArray=new JSONArray();
		List<QueryCriteria>criteriaList=getQueryCriteriaList(request);
		criteriaList.add(new QueryCriteria("recordStatus",Oparator.GRATERTHAN,RecordStatusEnum.ACTIVE.getCode()));
		List<AuctionMaster> auctionList=(List<AuctionMaster>)auctionService.getAllAuction(getUserSession(request), criteriaList).getDataList();
		for (AuctionMaster auct:auctionList){
			Product product=(Product)productService.getProductById(getUserSession(request), auct.getProductId());
			Pawner pawner=(Pawner)pawnerService.getPawnerById(getUserSession(request), auct.getAuctioneeId());
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
			
		}
		response.getWriter().write(mainArray.toString());
		logger.debug("**** Leaving getAjaxData method *****");
		return null;
	}
	
	
	public ActionForward getProduct(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	MessageResources messageResources = getResources(request,"message");
    	Product product = null;
 		String code = null;

 		if (request.getParameter("code")!=null)
 			code =  request.getParameter("code");

 		try {
 			product = productService.getProductByCode(SessionUtil.getUserSession(request), code);
 		}catch(PawnException ex){
			response.getWriter().write(StrutsFormValidateUtil.getAJAXErrorMessage(ex, messageResources, request.getLocale()));
			return null;
 		}
 		response.getWriter().write(StrutsFormValidateUtil.getAJAXReferenceData(product.getRecordId(),product.getDescription()));
 		return null;
	}
	
	public ActionForward getBranch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	MessageResources messageResources = getResources(request,"message");
    	Branch branch = null;
 		String code = null;

 		if (request.getParameter("code")!=null)
 			code =  request.getParameter("code");

 		try {
 			branch = branchService.getBranchByCode(SessionUtil.getUserSession(request), code);
 		}catch(PawnException ex){
			response.getWriter().write(StrutsFormValidateUtil.getAJAXErrorMessage(ex, messageResources, request.getLocale()));
			return null;
 		}
 		response.getWriter().write(StrutsFormValidateUtil.getAJAXReferenceData(branch.getRecordId(),branch.getBarnchName()));
 		return null;
	}
	
}

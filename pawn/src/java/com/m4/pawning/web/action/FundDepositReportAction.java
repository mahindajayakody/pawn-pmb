package com.m4.pawning.web.action;


import java.awt.Color;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.json.JSONArray;

import com.m4.core.util.MasterAction;
import com.m4.core.util.SessionUtil;
import com.m4.core.util.StrutsFormValidateUtil;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Branch;
import com.m4.pawning.domain.Company;
import com.m4.pawning.domain.FundDeposit;
import com.m4.pawning.domain.FundRequest;
import com.m4.pawning.domain.Officer;
import com.m4.pawning.domain.Pawner;
import com.m4.pawning.service.BranchService;
import com.m4.pawning.service.CompanyService;
import com.m4.pawning.service.FundDepositService;
import com.m4.pawning.service.OfficerService;
import com.m4.pawning.service.PawnerService;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class FundDepositReportAction extends MasterAction {
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
	private CompanyService companyService;
	private PawnerService pawnerService;
	
	
	public void setOfficerService(OfficerService officerService) {
		this.officerService = officerService;
	}

	public void setFundDepositService(FundDepositService fundDepositService) {
		this.fundDepositService = fundDepositService;
	}

	public void setBranchService(BranchService branchService) {
		this.branchService = branchService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public void setPawnerService(PawnerService pawnerService) {
		this.pawnerService = pawnerService;
	}

	public ActionForward getAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to getGridData method *****");
		String recordId = request.getParameter("recordId");

		JSONArray mainArray = new JSONArray();

		if(recordId!=null && recordId!="" && recordId!="0"){
			FundDeposit fundDeposit= fundDepositService.getFundDepositById(getUserSession(request), Integer.valueOf(recordId));
			Branch branch=branchService.getBranchById(getUserSession(request), Integer.valueOf(recordId));
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
		response.getWriter().write(mainArray.toString());
		logger.debug("**** Leaving getGridData method *****");
		return null;
	}
	
	@Override
	protected ActionForward getAuthorizeData(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
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
	public ActionForward print(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.debug("######### Enter In To Fund Deposit Print Method  ####################");

		UserConfig userConfig=SessionUtil.getUserSession(request);
		
		double fundavailable	  	= 0.00 ;
		double fundlimit	  		= 0.00;
		double totalreceiptsamt	  	= 0.00 ;
		double totalticktsamt	  	= 0.00 ;
		double openingBal			=0.0;
		double totalFundRequest		=0.00;
		double totalFunddeposit		=0.00;
		
		
		MessageResources messageResources = getResources(request,"message");
		java.util.Date receiptDate=new java.util.Date();

		Branch branch 	= branchService.getBranchById(userConfig , userConfig.getBranchId());
		Company company = companyService.getCompanyById(userConfig , userConfig.getCompanyId());
		Pawner pawner 	= pawnerService.getPawnerById(userConfig , userConfig.getPawnerId());
		
		List<Object[]> listTickets = fundDepositService.getTiketAllForTheDay(userConfig);
		List<Object[]> listReceipt = fundDepositService.getReceiptAllForTheDay(userConfig);
		List<Object[]> listRequest = fundDepositService.getFundRequestAllForTheDay(userConfig);
		List<Object[]> listDeposit = fundDepositService.getFundDepositAllForTheDay(userConfig);
		
		Officer officer=null;
		
		fundlimit=branch.getFundLimit();
		fundavailable=branch.getFundAvailable();
		
		if(listTickets.size()!=0){
			for (Object[] objects : listTickets) {
				if(objects[1]!=null){
					totalticktsamt=totalticktsamt+ Double.parseDouble(objects[4].toString()) ;
				}
				
			}
		}
	
		
		if(listReceipt.size()!=0){
			for (Object[] objects : listReceipt) {
				if(objects[1]!=null){
					totalreceiptsamt=totalreceiptsamt+ Double.parseDouble(objects[4].toString()) ;
				}
				
			}
		}
		
		
		if(listRequest.size()!=0){
			for (Object[] objects : listRequest) {
				if(objects[1]!=null){
					totalFundRequest=totalFundRequest+ Double.parseDouble(objects[1].toString()) ;
				}
				
			}
		}
		
		
		if(listDeposit.size()!=0){
			for (Object[] objects : listDeposit) {
				if(objects[1]!=null){
					totalFunddeposit=totalFunddeposit+ Double.parseDouble(objects[1].toString()) ;
				}
				
			}
		}
		
		 
		
		openingBal=(fundavailable+totalticktsamt+totalFunddeposit)-(totalreceiptsamt+totalFundRequest);
		
		Document document = new Document(PageSize.LEDGER);
		document.setMargins(document.leftMargin(), document.rightMargin(), document.topMargin()-20, 0);
        PdfPCell cell = null;
        PdfPCell subCell = null;  
        
        Font lableFont = FontFactory.getFont("Verdana",10, Font.NORMAL ,Color.black);        
        
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());                      
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition","inline; filename=Ticket.pdf" );
		
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
        document.open();
       
        PdfPTable headerTable = new PdfPTable(new float[]{1,1,1,2,2,1,1});
      
        
        cell = new PdfPCell(new Paragraph(company.getCompanyName()+" - Pawning system",lableFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(7);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Daily Balancing Report" ,lableFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(7);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        
        cell = new PdfPCell(new Paragraph("Branch Code   : " + branch.getCode(),lableFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(7);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Branch Name  : " + branch.getBarnchName() ,lableFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(7);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        
        
        cell = new PdfPCell(new Paragraph("Printed By       : " + pawner.getPawnerName() ,lableFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(7);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Printed Date    : " + StrutsFormValidateUtil.parseString(userConfig.getLoginDate()) ,lableFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(7);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        
        
        cell = new PdfPCell(new Paragraph(""));
        cell.setBorder(Rectangle.BOTTOM);
        cell.setColspan(7);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        
        cell = new PdfPCell(new Paragraph("",lableFont));
        cell.setBorder(Rectangle.BOTTOM);
        cell.setColspan(6);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Opening Balance",lableFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Tickets details",lableFont));
        cell.setBorder(Rectangle.NO_BORDER );
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Date",lableFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Ticket No",lableFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Client Name",lableFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Description",lableFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Advance Amount",lableFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph(decimalFormat.format(openingBal),lableFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        headerTable.addCell(cell);

        
        //Ticket loop
        
        int tktLast=0;
        if(listTickets.size()!=0){
			for (Object[] objects : listTickets) {
				tktLast=tktLast+1;
				if(objects[1]!=null){
					
					
					 	cell = new PdfPCell(new Paragraph("",lableFont));
				        cell.setBorder(Rectangle.NO_BORDER );
				        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				        headerTable.addCell(cell);
				        
				        cell = new PdfPCell(new Paragraph(simpdate.format(objects[0]),lableFont));
				        cell.setBorder(Rectangle.LEFT);
				        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				        headerTable.addCell(cell);
				        
				        cell = new PdfPCell(new Paragraph(objects[1].toString(),lableFont));
				        cell.setBorder(Rectangle.NO_BORDER);
				        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				        headerTable.addCell(cell);
				        
				        cell = new PdfPCell(new Paragraph(objects[2].toString(),lableFont));
				        cell.setBorder(Rectangle.NO_BORDER);
				        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				        headerTable.addCell(cell);
				        
				        cell = new PdfPCell(new Paragraph(objects[3].toString(),lableFont));
				        cell.setBorder(Rectangle.NO_BORDER);
				        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				        headerTable.addCell(cell);
				        
				        cell = new PdfPCell(new Paragraph(decimalFormat.format(objects[4]),lableFont));
				        cell.setBorder(Rectangle.RIGHT);
				        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				        headerTable.addCell(cell);
				        
				        if(tktLast==listTickets.size()){
				        	cell = new PdfPCell(new Paragraph("("+decimalFormat.format(totalticktsamt)+")",lableFont));
					        cell.setBorder(Rectangle.RIGHT);
					        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					        headerTable.addCell(cell);
				        }
				        else{
				        	cell = new PdfPCell(new Paragraph("",lableFont));
					        cell.setBorder(Rectangle.RIGHT);
					        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					        headerTable.addCell(cell);
				        }
				     
				}
				
			}
		}
        else
        {
        	 cell = new PdfPCell(new Paragraph("",lableFont));
             cell.setBorder(Rectangle.RIGHT);
             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
             headerTable.addCell(cell);
             
             cell = new PdfPCell(new Paragraph("",lableFont));
             cell.setBorder(Rectangle.RIGHT);
             cell.setColspan(5);
             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
             headerTable.addCell(cell);
             
             cell = new PdfPCell(new Paragraph("(0.00)",lableFont));
             cell.setBorder(Rectangle.RIGHT);
             cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
             headerTable.addCell(cell);
        }
       //////////////////////////////// 
        
	        
       
        
       
                
        cell = new PdfPCell(new Paragraph("Receipts Details",lableFont));
        cell.setBorder(Rectangle.TOP);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
       
        cell = new PdfPCell(new Paragraph("Date",lableFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Receipt No",lableFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Client Name",lableFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Description",lableFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Receipt Amount",lableFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        headerTable.addCell(cell);

        
        cell = new PdfPCell(new Paragraph("",lableFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        
        
        
        //Receipt loop
        
        int recptLast=0;
        if(listReceipt.size()!=0){
			for (Object[] objects : listReceipt) {
				recptLast=recptLast+1;
				if(objects[1]!=null){
					
					
					 	cell = new PdfPCell(new Paragraph("",lableFont));
				        cell.setBorder(Rectangle.NO_BORDER );
				        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				        headerTable.addCell(cell);
				        
				        cell = new PdfPCell(new Paragraph(simpdate.format(objects[0]),lableFont));
				        cell.setBorder(Rectangle.LEFT);
				        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				        headerTable.addCell(cell);
				        
				        cell = new PdfPCell(new Paragraph(objects[1].toString(),lableFont));
				        cell.setBorder(Rectangle.NO_BORDER);
				        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				        headerTable.addCell(cell);
				        
				        cell = new PdfPCell(new Paragraph(objects[2].toString(),lableFont));
				        cell.setBorder(Rectangle.NO_BORDER);
				        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				        headerTable.addCell(cell);
				        
				        cell = new PdfPCell(new Paragraph(objects[3].toString(),lableFont));
				        cell.setBorder(Rectangle.NO_BORDER);
				        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				        headerTable.addCell(cell);
				        
				        cell = new PdfPCell(new Paragraph(decimalFormat.format(objects[4]),lableFont));
				        cell.setBorder(Rectangle.RIGHT);
				        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				        headerTable.addCell(cell);
				        
				        if(recptLast==listReceipt.size()){
				        	cell = new PdfPCell(new Paragraph(decimalFormat.format(totalreceiptsamt),lableFont));
					        cell.setBorder(Rectangle.RIGHT);
					        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					        headerTable.addCell(cell);
				        }
				        else{
				        	cell = new PdfPCell(new Paragraph("",lableFont));
					        cell.setBorder(Rectangle.RIGHT);
					        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					        headerTable.addCell(cell);
				        }
				     
				}
				
			}
		}
        else
        {
        	 cell = new PdfPCell(new Paragraph("",lableFont));
             cell.setBorder(Rectangle.RIGHT);
             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
             headerTable.addCell(cell);
             
             cell = new PdfPCell(new Paragraph("",lableFont));
             cell.setBorder(Rectangle.RIGHT);
             cell.setColspan(5);
             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
             headerTable.addCell(cell);
             
             cell = new PdfPCell(new Paragraph("0.00",lableFont));
             cell.setBorder(Rectangle.RIGHT);
             cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
             headerTable.addCell(cell);
        }
       //////////////////////////////// 
        
        // Fund Request 
        
        
        
        cell = new PdfPCell(new Paragraph("Total Fund Requested",lableFont));
        cell.setBorder(Rectangle.TOP);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
       
        cell = new PdfPCell(new Paragraph("Date",lableFont));
        cell.setBorder(Rectangle.BOX);
        cell.setColspan(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Requested Amount",lableFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        headerTable.addCell(cell);

        
        cell = new PdfPCell(new Paragraph("",lableFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        
        //Fund Request loop
        
        int reqLast=0;
        if(listRequest.size()!=0){
			for (Object[] objects : listRequest) {
				reqLast=reqLast+1;
				if(objects[1]!=null){
					
						cell = new PdfPCell(new Paragraph("",lableFont));
				        cell.setBorder(Rectangle.NO_BORDER );
				        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				        headerTable.addCell(cell);
				        
				        cell = new PdfPCell(new Paragraph(simpdate.format(objects[0]),lableFont));
				        cell.setBorder(Rectangle.LEFT);
				        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				        headerTable.addCell(cell);
				        
				        cell = new PdfPCell(new Paragraph(decimalFormat.format(objects[1]),lableFont));
				        cell.setBorder(Rectangle.RIGHT);
				        cell.setColspan(4);
				        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				        headerTable.addCell(cell);
				        
				       		        
				        if(reqLast==listRequest.size()){
				        	cell = new PdfPCell(new Paragraph(decimalFormat.format(totalFundRequest),lableFont));
					        cell.setBorder(Rectangle.RIGHT);
					        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					        headerTable.addCell(cell);
				        }
				        else{
				        	cell = new PdfPCell(new Paragraph("",lableFont));
					        cell.setBorder(Rectangle.RIGHT);
					        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					        headerTable.addCell(cell);
				        }
				     
				}
				
			}
		}
        else
        {
        	 cell = new PdfPCell(new Paragraph("",lableFont));
             cell.setBorder(Rectangle.RIGHT);
             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
             headerTable.addCell(cell);
             
             cell = new PdfPCell(new Paragraph("",lableFont));
             cell.setBorder(Rectangle.RIGHT);
             cell.setColspan(5);
             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
             headerTable.addCell(cell);
             
             cell = new PdfPCell(new Paragraph("0.00",lableFont));
             cell.setBorder(Rectangle.RIGHT);
             cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
             headerTable.addCell(cell);
        }
       //////////////////////////////// 
        
        
        	// Fund Deposit 
        
        
        
        cell = new PdfPCell(new Paragraph("Total Fund Deposited",lableFont));
        cell.setBorder(Rectangle.TOP);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
       
        cell = new PdfPCell(new Paragraph("Date",lableFont));
        cell.setBorder(Rectangle.BOX);
        cell.setColspan(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Deposited Amount",lableFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        headerTable.addCell(cell);

        
        cell = new PdfPCell(new Paragraph("",lableFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        
        //Fund Deposit loop
        
        int deptLast=0;
        if(listDeposit.size()!=0){
			for (Object[] objects : listDeposit) {
				deptLast=deptLast+1;
				if(objects[1]!=null){
					
						cell = new PdfPCell(new Paragraph("",lableFont));
				        cell.setBorder(Rectangle.NO_BORDER );
				        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				        headerTable.addCell(cell);
				        
				        cell = new PdfPCell(new Paragraph(simpdate.format(objects[0]),lableFont));
				        cell.setBorder(Rectangle.LEFT);
				        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				        headerTable.addCell(cell);
				        
				        cell = new PdfPCell(new Paragraph(decimalFormat.format(objects[1]),lableFont));
				        cell.setBorder(Rectangle.RIGHT);
				        cell.setColspan(4);
				        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				        headerTable.addCell(cell);
				        
				       		        
				        if(deptLast==listDeposit.size()){
				        	cell = new PdfPCell(new Paragraph("("+decimalFormat.format(totalFunddeposit)+")",lableFont));
					        cell.setBorder(Rectangle.RIGHT);
					        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					        headerTable.addCell(cell);
				        }
				        else{
				        	cell = new PdfPCell(new Paragraph("",lableFont));
					        cell.setBorder(Rectangle.RIGHT);
					        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					        headerTable.addCell(cell);
				        }
				     
				}
				
			}
		}
        else
        {
        	 cell = new PdfPCell(new Paragraph("",lableFont));
             cell.setBorder(Rectangle.RIGHT);
             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
             headerTable.addCell(cell);
             
             cell = new PdfPCell(new Paragraph("",lableFont));
             cell.setBorder(Rectangle.RIGHT);
             cell.setColspan(5);
             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
             headerTable.addCell(cell);
             
             cell = new PdfPCell(new Paragraph("(0.00)",lableFont));
             cell.setBorder(Rectangle.RIGHT);
             cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
             headerTable.addCell(cell);
        }
       //////////////////////////////// 
        
        
        
        
        
        cell = new PdfPCell(new Paragraph(" Fund Available  ",lableFont));
        cell.setBorder(Rectangle.TOP);
        cell.setColspan(6);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph(decimalFormat.format(fundavailable),lableFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        headerTable.addCell(cell);
        
        
        
        //
        cell = new PdfPCell(new Paragraph("",lableFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(6);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("",lableFont));
        cell.setBorder(Rectangle.BOTTOM);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerTable.addCell(cell);
        
        //    
        cell = new PdfPCell(new Paragraph("",lableFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(7);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(headerTable);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        document.add(headerTable);
        
        document.close();
        logger.debug("######### End Of The  Fund Deposit Print Method  ####################");
		
		return null;
		
	}
	
	
}


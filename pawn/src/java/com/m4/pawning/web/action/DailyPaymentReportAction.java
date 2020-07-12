package com.m4.pawning.web.action;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.m4.core.util.MasterAction;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.SessionUtil;
import com.m4.core.util.StrutsFormValidateUtil;
import com.m4.core.util.UserConfig;
import com.m4.core.util.QueryCriteria.Oparator;
import com.m4.pawning.domain.Branch;
import com.m4.pawning.domain.Company;
import com.m4.pawning.domain.DueReceipt;
import com.m4.pawning.domain.DueType;
import com.m4.pawning.domain.Pawner;
import com.m4.pawning.domain.Receipt;
import com.m4.pawning.domain.Ticket;
import com.m4.pawning.service.BranchService;
import com.m4.pawning.service.CompanyService;
import com.m4.pawning.service.DueTypeService;
import com.m4.pawning.service.OfficerService;
import com.m4.pawning.service.PawnerService;
import com.m4.pawning.service.ReceiptService;
import com.m4.pawning.service.TicketService;
import com.m4.pawning.util.ReceiptStatusEnum;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

public class DailyPaymentReportAction extends MasterAction {
	static DecimalFormat points2decimalFormat = new DecimalFormat();
	static {
		points2decimalFormat.setMinimumFractionDigits(2);
		points2decimalFormat.setMaximumFractionDigits(2);
		points2decimalFormat.setGroupingSize(3);
	}
	
	private BranchService branchService;
	private PawnerService pawnerService; 
	private ReceiptService receiptService;
	private CompanyService companyService;
	private TicketService ticketService;
	private OfficerService officerService;
	private DueTypeService dueTypeService;
	 
	public void setOfficerService(OfficerService officerService) {
		this.officerService = officerService;
	}

	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	public void setReceiptService(ReceiptService receiptService) {
		this.receiptService = receiptService;
	}
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	public void setBranchService(BranchService branchService) {
		this.branchService = branchService;
	}
	public void setPawnerService(PawnerService pawnerService) {
		this.pawnerService = pawnerService;
	}
	public void setDueTypeService(DueTypeService dueTypeService) {
		this.dueTypeService = dueTypeService;
	}
	
	private BaseFont helv;
	private PdfTemplate tpl;
	
	public ActionForward print(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		new PrintUtil().print(request, response);
		return null;
	}
	

	public ActionForward createSetup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionForm frm = (DynaActionForm) form;
	    frm.initialize(mapping);
        frm.set("action",CREATE);
        frm.set("beginDate", StrutsFormValidateUtil.parseString(SessionUtil.getUserSession(request).getLoginDate()));
        frm.set("endDate", StrutsFormValidateUtil.parseString(SessionUtil.getUserSession(request).getLoginDate()));
        setOtherData(form, request);
        return mapping.getInputForward();
	}
	
	
	@Override
	protected ActionForward getAuthorizeData(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		return null;
	}
    
    class PrintUtil extends PdfPageEventHelper{
    	public void print(HttpServletRequest request,HttpServletResponse response)throws Exception{
    		UserConfig config = SessionUtil.getUserSession(request);
    		
    		String beginDate = request.getParameter("beginDate");
    		String endDate   = request.getParameter("endDate");
    		String branchId  = request.getParameter("branchId");
    		
    		float cellWeidth[]  = new float[]{2,2,2,4,6,2,2,2,2,2};
    		String[] cellTitles = new String[]{ "Receipt No",
    											"Ticket No",
    											"Payment Date",
    											"Client Name",
    											"Address",
    											"Total Paid (Rs.)",
    											"Capital Recoverd (Rs.)",
    											"Interest Recoverd Rs.",
    											"Default Recoverd Rs.",
    											"Cashier"};
    		
    		Date beginDateDate = null;
    		Date endDateDate   = null;
    		
    		if(endDate==null)
    			endDateDate = config.getLoginDate();
    		else
    			endDateDate = StrutsFormValidateUtil.parseDate(endDate);
    		
    		Calendar calendar = Calendar.getInstance();
    		calendar.setTime(endDateDate);
    		calendar.set(Calendar.HOUR, 23);		
    		
    		beginDateDate = StrutsFormValidateUtil.parseDate(beginDate);
 
    		//Changed By Mahinda on 24th-May-2011
    		Branch branch = branchService.getBranchById(config , Integer.parseInt(request.getParameter("branchId")));
    		
    		List<QueryCriteria> queryList = new ArrayList<QueryCriteria>();
    		queryList.add(new QueryCriteria("receiptDate",Oparator.GRATERTHAN_OR_EQUAL,beginDateDate));
    		queryList.add(new QueryCriteria("receiptDate",Oparator.LESSTHAN_OR_EQUAL,calendar.getTime()));
    		queryList.add(new QueryCriteria("status",Oparator.EQUAL,ReceiptStatusEnum.ACTIVE.getCode()));
    		
    		//Changed By Mahinda on 24th-May-2011
    		if (!branch.getCode().equals("***"))
    			queryList.add(new QueryCriteria("branchId",Oparator.EQUAL,Integer.parseInt(branchId)));
    		
    		queryList.add(new QueryCriteria("companyId",Oparator.EQUAL,config.getCompanyId()));
    		
    		Pawner pawner = pawnerService.getPawnerById(config , config.getPawnerId());
    		Company company = companyService.getCompanyById(config , config.getCompanyId());
    	
    		List<Receipt> receiptList = receiptService.getAllReceipt(config , queryList ).getDataList();
    		List<DueType> dueTypeList = dueTypeService.getAllDueType(config , null).getDataList();
    		Map<Integer, DueType> dueMap = new HashMap<Integer, DueType>();
    		for(DueType dueType:dueTypeList)
    			dueMap.put(dueType.getDueTypeId(), dueType);
     		
    		/** 
    		 *  Start building the ticket.pdf
    		*/  
    		//Rectangle pageSize = new Rectangle(720, 720);
    		Document document = new Document(PageSize._11X17.rotate());
    		document.setMargins(document.leftMargin(), document.rightMargin(), document.topMargin()-20, 0);
            PdfPCell cell    = null;
            
            Font lableFont   = FontFactory.getFont("Verdana",10, Font.NORMAL ,Color.black);        
            
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());                      
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition","inline; filename=Ticket.pdf" );
    		
    		response.setHeader("Expires", "0");
    		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
    		response.setHeader("Pragma", "public");
            document.open();
            
            PdfPTable headTable = new PdfPTable(1);        
            headTable.setWidthPercentage(100);
            
            //Print header details
            PdfPTable headerTable = new PdfPTable(1);
            
            
            cell = new PdfPCell(new Paragraph(company.getCompanyName()+" - Pawning system",lableFont));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerTable.addCell(cell);
            
            cell = new PdfPCell(new Paragraph("Daily Receipt Report : From  " + beginDate + "   To   " + endDate,lableFont));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerTable.addCell(cell);
            
            
            cell = new PdfPCell(new Paragraph("Branch Code   : " + branch.getCode(),lableFont));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerTable.addCell(cell);
            
            cell = new PdfPCell(new Paragraph("Branch Name  : " + branch.getBarnchName() ,lableFont));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerTable.addCell(cell);
            
            
            
            cell = new PdfPCell(new Paragraph("Printed By       : " + pawner.getPawnerName() ,lableFont));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerTable.addCell(cell);
            
            cell = new PdfPCell(new Paragraph("Printed Date    : " + StrutsFormValidateUtil.parseString(config.getLoginDate()) ,lableFont));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerTable.addCell(cell);
            
            cell = new PdfPCell(new Paragraph(" "));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerTable.addCell(cell);
            
            cell = new PdfPCell(headerTable);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            headTable.addCell(cell);
            document.add(headTable);
            
            //Creating data table headings
    	    PdfPTable dataTable = new PdfPTable(10);
            dataTable.setWidthPercentage(100);        
            dataTable.setHeaderRows(1);
            
            for(String lable:cellTitles){
            	cell = new PdfPCell(new Paragraph(lable,lableFont));
                cell.setGrayFill(0.9f);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                dataTable.addCell(cell);
                dataTable.setWidths(cellWeidth);
            }
            
            
            int noItems=1;
            double TotPaymentAmt=0.00;
            double dblTotCapitalRecoverd=0.00;
            double dblTotInterestRecoverd=0.00;
            double dblTotDefaultlRecoverd=0.00;
            
            for(Receipt receipt:receiptList){
            	
            	
            	Ticket ticket = ticketService.getTicketById(config , receipt.getTicketId());
            	//Officer officer= officerService.getOfficerById(config, receipt.getUserId());
                  
            	
//            	cell = new PdfPCell(new Paragraph(String.valueOf(noItems),lableFont));            
            	cell = new PdfPCell(new Paragraph(receipt.getReceiptNo(),lableFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.NO_BORDER);
                dataTable.addCell(cell);
                 
                cell = new PdfPCell(new Paragraph(ticket.getTicketNumber(),lableFont));            
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(Rectangle.NO_BORDER);
                dataTable.addCell(cell);
                
                cell = new PdfPCell(new Paragraph(StrutsFormValidateUtil.parseString(receipt.getReceiptDate()),lableFont));            
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.NO_BORDER);
                dataTable.addCell(cell);
                
                cell = new PdfPCell(new Paragraph(ticket.getPawner().getPawnerName(),lableFont));            
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(Rectangle.NO_BORDER);
                dataTable.addCell(cell);
                
                cell = new PdfPCell(new Paragraph(new StringBuilder().append(ticket.getPawner().getAddressLine1())
                					.append(" ").append(ticket.getPawner().getAddressLine2())
                					.append(" ").append(ticket.getPawner().getAddressLine3())
                					.append(" ").append(ticket.getPawner().getAddressLine4()).toString(),lableFont));            
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(Rectangle.NO_BORDER);
                dataTable.addCell(cell);
                
                if (receipt.getReceiptType() == 1){
	                cell = new PdfPCell(new Paragraph(points2decimalFormat.format(receipt.getReceiptAmt()),lableFont));            
	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                cell.setBorder(Rectangle.NO_BORDER);
	                dataTable.addCell(cell);
                }else{
	                cell = new PdfPCell(new Paragraph(points2decimalFormat.format(receipt.getReceiptAmt()*-1),lableFont));            
	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                cell.setBorder(Rectangle.NO_BORDER);
	                dataTable.addCell(cell);
                	
                }
                
                double dblCapitalRecoverd=0.00;
                double dblInterestRecoverd=0.00;
                double dblDefaultlRecoverd=0.00;
                
                for(DueReceipt dueReceipt:receipt.getDueReceipts()){
                	DueType dueType = dueMap.get(dueReceipt.getDueTypeId());//dueTypeService.getDueTypeById(config,dueReceipt.getDueTypeId());
                	               	
                	if(dueType.getDueType().equals("INT") && receipt.getReceiptType() == 1){
                		dblInterestRecoverd=dueReceipt.getSettleAmount();
                	}
                	else if(dueType.getDueType().equals("CAP")){
                		dblCapitalRecoverd=dueReceipt.getSettleAmount();
                	}
                	
                }
                
                cell = new PdfPCell(new Paragraph(" "+points2decimalFormat.format(dblCapitalRecoverd),lableFont));            
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                dataTable.addCell(cell);
                
                cell = new PdfPCell(new Paragraph(" "+points2decimalFormat.format(dblInterestRecoverd),lableFont));            
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                dataTable.addCell(cell);
                
                cell = new PdfPCell(new Paragraph(" "+points2decimalFormat.format(dblDefaultlRecoverd),lableFont));            
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                dataTable.addCell(cell);
                
                cell = new PdfPCell(new Paragraph(" "+receipt.getReceiptEnteredUser(),lableFont));            
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.NO_BORDER);
                dataTable.addCell(cell);
                
                
                
                noItems+=1;
                TotPaymentAmt+=receipt.getReceiptAmt();
                dblTotCapitalRecoverd+=dblCapitalRecoverd;
                dblTotInterestRecoverd+=dblInterestRecoverd;
                dblTotDefaultlRecoverd+=dblDefaultlRecoverd;
            }
                    
            
            document.add(dataTable);    
            
            //Creating data table GranTotal
    	    PdfPTable SpaceTable = new PdfPTable(1);
    	    SpaceTable.setWidthPercentage(100);  
            
    	    cell = new PdfPCell(new Paragraph(""));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            SpaceTable.addCell(cell);
            
            cell = new PdfPCell(new Paragraph(""));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            SpaceTable.addCell(cell);
            
            //SpaceTable.setWidths(totcellWeidth);
            document.add(SpaceTable);
    	    
    	    
    	    
    	    
            
            //Creating data table GranTotal
            
            float totcellWeidth[]  = new float[]{1,2,2,4,6,2,2,2,2,2};
            
    	    PdfPTable GranddataTable = new PdfPTable(10);
    	    GranddataTable.setWidthPercentage(100);  
    	    
          // GranddataTable.setHeaderRows(1);
            
    	    
            cell = new PdfPCell(new Paragraph(""));
            cell.setBorder(Rectangle.TOP);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            GranddataTable.addCell(cell);
            
            cell = new PdfPCell(new Paragraph(""));
            cell.setBorder(Rectangle.TOP);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            GranddataTable.addCell(cell);
        
            cell = new PdfPCell(new Paragraph(""));
            cell.setBorder(Rectangle.TOP);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            GranddataTable.addCell(cell);
            
            cell = new PdfPCell(new Paragraph(""));
            cell.setBorder(Rectangle.TOP);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            GranddataTable.addCell(cell);
            
            cell = new PdfPCell(new Paragraph("Grand Totals    : "));
            cell.setBorder(Rectangle.TOP);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            GranddataTable.addCell(cell);
            
            cell = new PdfPCell(new Paragraph("" + points2decimalFormat.format(TotPaymentAmt),lableFont));
            cell.setBorder(Rectangle.TOP);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            GranddataTable.addCell(cell);
            
            cell = new PdfPCell(new Paragraph("" + points2decimalFormat.format(dblTotCapitalRecoverd),lableFont));
            cell.setBorder(Rectangle.TOP);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            GranddataTable.addCell(cell);
            
            cell = new PdfPCell(new Paragraph("" + points2decimalFormat.format(dblTotInterestRecoverd),lableFont));
            cell.setBorder(Rectangle.TOP);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            GranddataTable.addCell(cell);
            
            cell = new PdfPCell(new Paragraph("" + points2decimalFormat.format(dblTotDefaultlRecoverd),lableFont));
            cell.setBorder(Rectangle.TOP);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            GranddataTable.addCell(cell);
            
            
            cell = new PdfPCell(new Paragraph(""));
            cell.setBorder(Rectangle.TOP);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            GranddataTable.addCell(cell);
            
            GranddataTable.setWidths(totcellWeidth);
            document.add(GranddataTable);
            
           
            document.close();
    	}
    	
        public void onOpenDocument(PdfWriter writer, Document document) {
            try {
                // initialization of the template
                tpl = writer.getDirectContent().createTemplate(100, 100);
                helv = BaseFont.createFont("Helvetica", BaseFont.WINANSI, false);
            }
            catch(Exception e) {
                throw new ExceptionConverter(e);
            }
        } 
    	
        public void onEndPage(PdfWriter writer, Document document){
            PdfContentByte cb = writer.getDirectContent();
            cb.saveState();
            String text = "Page " + writer.getPageNumber() + " of ";
            
            float adjust = helv.getWidthPoint("0", 8);
            float textBase = document.bottom() - 20;
            float textSize = helv.getWidthPoint(text, 8);
            
            cb.beginText();
            cb.setFontAndSize(helv, 8);
            cb.setTextMatrix(document.right() - textSize - adjust, textBase);
            cb.showText(text);
            cb.endText();
            
            cb.addTemplate(tpl, document.right() - adjust, textBase);        
            cb.saveState();
            cb.restoreState();
        }
        
        public void onCloseDocument(PdfWriter writer, Document document) {
           tpl.beginText();
           tpl.setFontAndSize(helv, 8);
           tpl.setTextMatrix(0, 0);
           tpl.showText("" + (writer.getPageNumber() - 1));
           tpl.endText();
        }
    }
}

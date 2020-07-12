package com.m4.pawning.web.action;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.criterion.Restrictions;
import com.m4.pawning.util.TicketStatusEnum;
import com.m4.core.util.MasterAction;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.RecordStatusEnum;
import com.m4.core.util.SessionUtil;
import com.m4.core.util.StrutsFormValidateUtil;
import com.m4.core.util.UserConfig;
import com.m4.core.util.QueryCriteria.Oparator;
import com.m4.pawning.domain.Branch;
import com.m4.pawning.domain.Company;
import com.m4.pawning.domain.Pawner;
import com.m4.pawning.domain.Ticket;
import com.m4.pawning.service.BranchService;
import com.m4.pawning.service.CompanyService;
import com.m4.pawning.service.PawnerService;
import com.m4.pawning.service.TicketService;
import com.m4.pawning.service.impl.CompanyServiceImpl;
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

public class DailyPawningReportAction extends MasterAction {
	static DecimalFormat points2decimalFormat = new DecimalFormat();
	static {
		points2decimalFormat.setMinimumFractionDigits(2);
		points2decimalFormat.setMaximumFractionDigits(2);
		points2decimalFormat.setGroupingSize(3);
	}
	
	private BranchService branchService;
	private PawnerService pawnerService; 
	private TicketService ticketService;
	private CompanyService companyService;
	
	
	
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	public void setBranchService(BranchService branchService) {
		this.branchService = branchService;
	}
	public void setPawnerService(PawnerService pawnerService) {
		this.pawnerService = pawnerService;
	}
	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	private BaseFont helv;
	private PdfTemplate tpl;
	
	public ActionForward print(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		new PrintUtil().print(request, response);
		return null;
	}
	
	@Override
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
    		
    		float cellWeidth[]  = new float[]{1,3,1,3,5,2,1,1,1};
    		String[] cellTitles = new String[]{ "Serial No",
    											"Ticket No/Status",
    											"Pawned Date",
    											"Client Name",
    											"Address",
    											"Pawn Advance (Rs.)",
    											"Gross Weight",
    											"Net Weight",
    											"No Of Items"};
    		
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
    		queryList.add(new QueryCriteria("ticketDate",Oparator.GRATERTHAN_OR_EQUAL,beginDateDate));
    		queryList.add(new QueryCriteria("ticketDate",Oparator.LESSTHAN_OR_EQUAL,calendar.getTime()));
    		queryList.add(new QueryCriteria("ticketStatusId",Oparator.NOT_EQUAL,TicketStatusEnum.REJECTED.getCode()));
//    		queryList.add(new QueryCriteria("ticketStatusId",Oparator.EQUAL,TicketStatusEnum.LAPS.getCode()));
    		//Changed By Mahinda on 24th-May-2011
    		if (!branch.getCode().equals("***"))
    			queryList.add(new QueryCriteria("branchId",Oparator.EQUAL,Integer.parseInt(branchId)));
    		queryList.add(new QueryCriteria("companyId",Oparator.EQUAL,config.getCompanyId()));
    		
    		
    		Pawner pawner = pawnerService.getPawnerById(config , config.getPawnerId());
    		Company company = companyService.getCompanyById(config , config.getCompanyId());
    	
    		List<Ticket> ticketList = ticketService.getAllTicket(config,queryList ).getDataList();
    		
    		/** 
    		 *  Start building the DailyPawningReport.pdf
    		*/  
    		Document document = new Document(PageSize._11X17.rotate());
    		document.setMargins(document.leftMargin(), document.rightMargin(), document.topMargin()-20, 0);
            PdfPCell cell    = null;
            
            Font lableFont   = FontFactory.getFont("Verdana",10, Font.NORMAL ,Color.black);        
            
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());                      
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition","inline; filename=DailyPawningReport.pdf" );
    		
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
            
            cell = new PdfPCell(new Paragraph("Daily Pawning Statement : From  " + beginDate + "   To   " + endDate,lableFont));
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
    	    PdfPTable dataTable = new PdfPTable(9);
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
            double TotPowanadvance=0;
            String strTicketStatus=""; 
            for(Ticket ticket:ticketList){
            	//cell = new PdfPCell(new Paragraph(ticket.getTicketSerialNumber(),lableFont));   
            	cell = new PdfPCell(new Paragraph(String.valueOf(noItems),lableFont)); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.NO_BORDER);
                dataTable.addCell(cell);
                
                switch (ticket.getTicketStatusId()) {
				case 1:
					strTicketStatus=" - ACTIVE";
					break;
				case 2:
					strTicketStatus=" - PENDING";
					break;
				case 3:
					strTicketStatus=" - REJECTED";
					break;
				case 4:
					strTicketStatus=" - LAPS";
					break;
				case 5:
					strTicketStatus=" - CLOSSED";
					break;

				default:
					strTicketStatus="";
					break;
				}
                
                
                cell = new PdfPCell(new Paragraph(ticket.getTicketNumber()+strTicketStatus,lableFont));            
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(Rectangle.NO_BORDER);
                dataTable.addCell(cell);
                
                cell = new PdfPCell(new Paragraph(StrutsFormValidateUtil.parseString(ticket.getTicketDate()),lableFont));            
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
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
                
                cell = new PdfPCell(new Paragraph(points2decimalFormat.format(ticket.getPawnAdvance()),lableFont));            
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                dataTable.addCell(cell);
                
                cell = new PdfPCell(new Paragraph(""+ticket.getTotalGrossWeight(),lableFont));            
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.NO_BORDER);
                dataTable.addCell(cell);
                
                cell = new PdfPCell(new Paragraph(""+ticket.getTotalNetWeight(),lableFont));            
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.NO_BORDER);
                dataTable.addCell(cell);
                
                cell = new PdfPCell(new Paragraph(""+ticket.getTotalNoOfItems(),lableFont));            
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.NO_BORDER);
                dataTable.addCell(cell);
                noItems+=1;
                TotPowanadvance+=ticket.getPawnAdvance();
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
            
            float totcellWeidth[]  = new float[]{1,3,1,3,5,2,1,1,1};
            
    	    PdfPTable GranddataTable = new PdfPTable(9);
    	    GranddataTable.setWidthPercentage(100);  
    	    
         
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
            
            cell = new PdfPCell(new Paragraph(""));
            cell.setBorder(Rectangle.TOP);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            GranddataTable.addCell(cell);
            
            
            cell = new PdfPCell(new Paragraph("" + points2decimalFormat.format(TotPowanadvance),lableFont));
            cell.setBorder(Rectangle.TOP);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            GranddataTable.addCell(cell);
            
            cell = new PdfPCell(new Paragraph("" ,lableFont));
            cell.setBorder(Rectangle.TOP);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            GranddataTable.addCell(cell);
            
            cell = new PdfPCell(new Paragraph("" ,lableFont));
            cell.setBorder(Rectangle.TOP);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            GranddataTable.addCell(cell);
            
            cell = new PdfPCell(new Paragraph("" ,lableFont));
            cell.setBorder(Rectangle.TOP);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
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

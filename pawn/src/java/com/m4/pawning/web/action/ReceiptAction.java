package com.m4.pawning.web.action;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


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
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.DueFrom;
import com.m4.pawning.domain.Pawner;
import com.m4.pawning.domain.Product;
import com.m4.pawning.domain.Receipt;
import com.m4.pawning.dto.ReceiptDTO;
import com.m4.pawning.service.PawnerService;
import com.m4.pawning.service.ProductService;
import com.m4.pawning.service.ReceiptService;
import com.m4.pawning.util.EnglishNumberToWords;
import com.m4.pawning.util.ReceiptStatusEnum;
import com.m4.pawning.util.ReceiptTypeEnum;
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


public class ReceiptAction extends MasterAction {
	private static final Logger logger = Logger.getLogger(ReceiptAction.class);
	private static DecimalFormat decimalFormat = new DecimalFormat();
	private static final SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
	private static final SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
	static{
		decimalFormat.setMaximumFractionDigits(2);
		decimalFormat.setMinimumFractionDigits(2);
	}
	private ProductService productService;
	private ReceiptService receiptService;
	private PawnerService  pawnerService;
	
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	public void setReceiptService(ReceiptService receiptService) {
		this.receiptService = receiptService;
	}
	
	public void setPawnerService(PawnerService pawnerService) {
		this.pawnerService = pawnerService;
	}
	public ActionForward createReceipt(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to createReceipt method ****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if(!validateForm.isEmpty()){
        	response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
        }else{
        	Receipt receipt = new Receipt();
        	receipt.setTicketId(Integer.parseInt(request.getParameter("ticketId")));
        	receipt.setDescription(request.getParameter("remark"));
        	receipt.setReceiptAmt(Double.parseDouble(request.getParameter("receiptamt")));
        	receipt.setReceiptType(ReceiptTypeEnum.CASH.getCode());
        	receipt.setStatus(ReceiptStatusEnum.ACTIVE.getCode());
        	receipt.setReceiptType(Integer.parseInt(request.getParameter("receiptType")));
        	
        	try {
        		String number = receiptService.createReceipt(SessionUtil.getUserSession(request), receipt);
        		JSONObject object = StrutsFormValidateUtil.getMessageCreateSuccess(messageResources);
        		object.put("receiptNo",number);
        		response.getWriter().write(object.toString());
			} catch (PawnException ex) {
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
			}        	
        }		
		
		logger.debug("**** Leaving from createReceipt method ****");
		return null;
	}

	public ActionForward getProduct(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to getProduct method ****");
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
 		logger.debug("**** Leaving from getProduct method ****");
 		return null;
	}

	public ActionForward getAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to getAjaxData method ****");
		MessageResources messageResources = getResources(request,"message");
		int ticketId = Integer.parseInt(request.getParameter("ticketId"));
		int receiptType =Integer.parseInt(request.getParameter("receiptType"));
		
		ReceiptDTO receiptDTO = receiptService.getReceiptTicketData(SessionUtil.getUserSession(request), ticketId,receiptType);
		JSONObject object = new JSONObject();
		object.put("clientId", receiptDTO.getClientId());
		object.put("clientCode", receiptDTO.getClientCode());
		object.put("clientName", receiptDTO.getClientName());
		object.put("address", receiptDTO.getAddress());
		object.put("totalDueAmount", "");
		object.put("receiptDate", StrutsFormValidateUtil.parseString(SessionUtil.getUserSession(request).getLoginDate()));
		
		JSONArray mainArray = new JSONArray();
		for(DueFrom dueFrom:receiptDTO.getDueFromList()){
			JSONArray array = new JSONArray();
			array.put(dueFrom.getDueTypeDescriiption());
			array.put(decimalFormat.format(dueFrom.getDueAmount()));
			array.put(decimalFormat.format(dueFrom.getPaidAmount()));
			array.put(decimalFormat.format(dueFrom.getBalanceAmount()));
			mainArray.put(array);
		}
		
		object.put("dueFromList", mainArray);
		
		response.getWriter().write(object.toString());
		logger.debug("**** Leaving from getAjaxData method ****");
		return null;
	}
	
	@Override
	protected ActionForward getAuthorizeData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}
	
	public ActionForward print(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		
		logger.debug("######### Enter In To Receipt Print Method  ####################");

		UserConfig userConfig=SessionUtil.getUserSession(request);;
		String ticketNo     = request.getParameter("ticketNo");
		String receiptNo    = request.getParameter("receiptNo");
		int clientId        = Integer.parseInt(request.getParameter("clientId"));
		String  amount		=request.getParameter("amount");
		MessageResources messageResources = getResources(request,"message");
		java.util.Date receiptDate=new java.util.Date();

		
		Pawner pawner=new Pawner();
		Receipt receipt =new Receipt();
		try{
			pawner= pawnerService.getPawnerById(SessionUtil.getUserSession(request), clientId);
			receiptService.updateReceiptPrint(SessionUtil.getUserSession(request), receiptNo);
		}
		catch(PawnException ex){
			response.getWriter().write(StrutsFormValidateUtil.getAJAXErrorMessage(ex, messageResources, request.getLocale()));
			return null;
		}
	
		Document document = new Document(PageSize.LETTER);
		document.setMargins(document.leftMargin(), document.rightMargin(), document.topMargin(), 0);
        PdfPCell cell = null;
        PdfPCell subCell = null;  
        
        Font lableFont = FontFactory.getFont("Verdana",8, Font.NORMAL ,Color.black);        
        
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());                      
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition","inline; filename=Receipt.pdf" );
		
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
        document.open();
       
        PdfPTable headerTable = new PdfPTable(new float[]{2,1,1});
      
        
        String strName=pawner.getTitle()+' '+pawner.getInitials()+' '+pawner.getSurName();
        
        
        cell = new PdfPCell(new Paragraph(""));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        cell.setFixedHeight(40);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
//        
//        cell = new PdfPCell(new Paragraph(""));
//        cell.setBorder(Rectangle.NO_BORDER);
//        cell.setColspan(3);
//        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//        headerTable.addCell(cell);
        
        
        cell = new PdfPCell(new Paragraph(strName,lableFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);

        
        cell = new PdfPCell(new Paragraph(pawner.getAddressLine1(),lableFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph(""));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("               "+sdfDate.format(userConfig.getLoginDate())+" "+sdfTime.format(receiptDate),lableFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);

        
        cell = new PdfPCell(new Paragraph(pawner.getAddressLine2()+" "+pawner.getAddressLine3(),lableFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph(""));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
//        cell = new PdfPCell(new Paragraph("",lableFont));
//        cell.setBorder(Rectangle.NO_BORDER);
//        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//        headerTable.addCell(cell);
        cell = new PdfPCell(new Paragraph("               "+receiptNo,lableFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);        
        
        cell = new PdfPCell(new Paragraph(pawner.getAddressLine4(),lableFont));//addrs 3
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph(""));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("               "+pawner.getPawnerCode(),lableFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        
        cell = new PdfPCell(new Paragraph("",lableFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph(""));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
//        cell = new PdfPCell(new Paragraph("                           "+sdfDate.format(userConfig.getLoginDate())+" "+sdfTime.format(receiptDate),lableFont));
//        cell.setBorder(Rectangle.NO_BORDER);
//        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//        headerTable.addCell(cell);
        
            
        cell = new PdfPCell(new Paragraph(""));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        cell.setFixedHeight(40);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        
        cell = new PdfPCell(new Paragraph("                                   "+strName,lableFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);       
        
        
        cell = new PdfPCell(new Paragraph("              "+EnglishNumberToWords.convertString(amount) + " only.",lableFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);

        cell = new PdfPCell(new Paragraph(""));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        cell.setFixedHeight(10);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);

        cell = new PdfPCell(new Paragraph(""));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        cell.setFixedHeight(10);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);

        cell = new PdfPCell(new Paragraph(""));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        cell.setFixedHeight(10);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);

        cell = new PdfPCell(new Paragraph(""));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);

        cell = new PdfPCell(new Paragraph(ticketNo+"                                                                 "+amount,lableFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(30);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("",lableFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);

        cell = new PdfPCell(new Paragraph("",lableFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);

        
        cell = new PdfPCell(new Paragraph(""));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        cell.setFixedHeight(40);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);       

        cell = new PdfPCell(new Paragraph(""));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph(""));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);

        cell = new PdfPCell(new Paragraph(""));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph(""));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("       **"+amount+"**",lableFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(30);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);

        cell = new PdfPCell(new Paragraph(""));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(30);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);

        cell = new PdfPCell(new Paragraph(""));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(30);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);

        cell = new PdfPCell(headerTable);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        document.add(headerTable);
        
        document.close();
        logger.debug("######### End Of The Receipt Print Method  ####################");
		
		return null;
		
	}
	
}

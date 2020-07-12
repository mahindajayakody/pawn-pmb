package com.m4.pawning.util;

import java.awt.Color;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.m4.core.util.SessionUtil;
import com.m4.core.util.UserConfig;
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

public class PrintTicketUtil {
	public static void print(HttpServletRequest request, HttpServletResponse response) throws Exception{
		UserConfig userConfig = SessionUtil.getUserSession(request);
		
		Document document = new Document(PageSize.LETTER);
        PdfPCell cell = null;
        PdfPCell subCell = null;  
        
        Font descriptionFont = FontFactory.getFont("VERDANA" , 13, Font.BOLD,Color.black);
        Font lableFont = FontFactory.getFont("VERDANA" , 11, Font.NORMAL,Color.black);
        Font subTableFont = FontFactory.getFont("VERDANA" , 10, Font.NORMAL,Color.black);
        
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());                      
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition","inline; filename=Ticket.pdf" );
		
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
        document.open();
        
        PdfPTable headTable = new PdfPTable(1);        
        headTable.setWidthPercentage(100);        
        cell = new PdfPCell(new Paragraph("sss",descriptionFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("ss",subTableFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headTable.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("ss",subTableFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headTable.addCell(cell);
        
        document.add(headTable);
        document.close();
	}
}

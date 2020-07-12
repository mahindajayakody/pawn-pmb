package com.m4.pawning.test;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;

import com.m4.core.util.PasswordService;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class Temp {


	public static void main(String[] args) {
		try {
			System.out.println(PasswordService.getInstance().encrypt("admin"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void test() throws FileNotFoundException, DocumentException{
		Document document = new Document(PageSize.LEDGER.rotate());
		document.setMargins(0, document.rightMargin()-25, document.topMargin()-20, 0);
        PdfPCell cell    = null;
        
        Font lableFont   = FontFactory.getFont("ARIAL",12, Font.NORMAL ,Color.black);
        Font lableBig    = FontFactory.getFont("ARIAL",14, Font.BOLD ,Color.black);
                   
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("D:/test.pdf"));                      
        
        document.open();
        
        PdfPTable headerTable = new PdfPTable(new float[]{1});

        cell = new PdfPCell(new Paragraph(" WWWWWWWWWWW"));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(20);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
           
        cell = new PdfPCell(new Paragraph(" AAAAAAAAA"));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        document.add(headerTable);
        
        document.newPage();
        
        cell = new PdfPCell(new Paragraph(" SSSSSSSSSS "));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(cell);
        
//        cell = new PdfPCell(headerTable);
//        cell.setBorder(Rectangle.NO_BORDER);
//        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//        headerTable.addCell(cell);
        document.add(headerTable);
        document.close();
	}

}

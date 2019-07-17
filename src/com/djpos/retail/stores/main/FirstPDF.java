package com.djpos.retail.stores.main;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Date;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class FirstPDF extends MainActivity{
  private static String FILE = "c:/temp/FirstPdf.pdf";
  private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
      Font.BOLD);
  private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
      Font.NORMAL, BaseColor.RED);
  private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
      Font.BOLD);
  private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
      Font.BOLD);

  public static void createPDF(Transaction txn, String path, Store storeData) {
    
	  if(txn!=null)
	  {
	  try {
      Document document = new Document();
      String path1 = path + txn.getTransactionID() + ".pdf";
      String filename = txn.getTransactionID() + ".pdf";
      File f = new File(path);
      if(!(txn.getTransactionID().equals("1")))
      {
    	  //delete the previous file if exists
    	  int previousTxnID = Integer.parseInt(txn.getTransactionID());
    	  String prevTxnID = Integer.toString(previousTxnID-1);
    	  String previousFileName = prevTxnID  + ".pdf";
    	  
          File previousFile = new File(path, previousFileName);
          if(previousFile.exists())
          {
        	  previousFile.delete();
        	  System.out.println("Previous File Deleted");
          }
      }
    	  
    	  
     
      if(!f.mkdirs())
      {
    	  System.out.println("Receipt  Directory not Exists");
    	  Log.e("LOG_TAG", "Directory not created");

      }
     /* else
      {
    	  System.out.println("Receipt Directory not Exists");
    	  System.out.println(Environment.getExternalStorageDirectory().toString());
    	  f.mkdirs();
    	  if(f.exists())
    	  {
    		  System.out.println("Could create directory");
    	  }
    	  else
    	  {
    		  System.out.println("Could not create directory");
    	  }
      }*/
      System.out.println(f.getAbsolutePath());
      File outputFile = new File(f, filename);
      FileOutputStream fos = new FileOutputStream(outputFile);
      PdfWriter.getInstance(document, fos);
      document.open();
      addMetaData(document);
      addTitlePage(document, txn, storeData);
    //  addContent(document);
      document.close();
    
      
      
    } catch (Exception e) {
      e.printStackTrace();
    }
	  }
  }

  // iText allows to add metadata to the PDF which can be viewed in your Adobe
  // Reader
  // under File -> Properties
  private static void addMetaData(Document document) {
    document.addTitle("DJPOS");
    document.addSubject("eReceipt");
    document.addKeywords("eReceipt");
    document.addAuthor("Dhan");
    document.addCreator("DJPOS");
  }

  private static void addTitlePage(Document document, Transaction txn, Store storeData)
      throws DocumentException {
	  Paragraph preface = new Paragraph();
	    // We add one empty line
	    addEmptyLine(preface, 1);
	    // Lets write a big header
	    Paragraph store = new Paragraph(storeData.getStoreName(), catFont);
	    store.setAlignment(Element.ALIGN_CENTER);
	    
	    preface.add(store);
	    
	    Paragraph store1 = new Paragraph(storeData.getstoreaddress1(), catFont);
	    store1.setAlignment(Element.ALIGN_CENTER);
	    
	    preface.add(store1);
	    
	    Paragraph store2 = new Paragraph(storeData.getstoreaddress2(), catFont);
	    store2.setAlignment(Element.ALIGN_CENTER);
	    
	    preface.add(store2);
	    
	   
	    
	    Paragraph store3 = new Paragraph("Zip : " + storeData.getstoreaddress3(), catFont);
	    store3.setAlignment(Element.ALIGN_CENTER);
	    
	    preface.add(store3);
	    
	    Paragraph store4 = new Paragraph("Tel : " + storeData.getstorephone(), catFont);
	    store4.setAlignment(Element.ALIGN_CENTER);
	    
	    preface.add(store4);
	    
	    Paragraph divide = new Paragraph("----------------------------------------------------------------" +
	    		"-----------------------", catFont);
	 
	    
	    preface.add(divide);
	    
	  //  addEmptyLine(preface, 1);
	    
	    Paragraph storeID = new Paragraph("Store ID :" + storeData.getStoreID()
	    	    , subFont);
	    storeID.setAlignment(Element.ALIGN_LEFT);
	    	    
	    preface.add(storeID);
	    
	    Paragraph Transaction = new Paragraph("Transaction ID :" + txn.getTransactionID()
	    , subFont);
	    Transaction.setAlignment(Element.ALIGN_LEFT);
	    
	    preface.add(Transaction);
	    
	    Paragraph Transaction1 = new Paragraph("Transaction Time :" + new Date() , subFont);
	    Transaction1.setAlignment(Element.ALIGN_LEFT);
	    	    
	    preface.add(Transaction1);
	    
	    Paragraph Transaction2 = new Paragraph("User ID :" + "Dhan" , subFont);
	    Transaction2.setAlignment(Element.ALIGN_LEFT);
	    	    
	    preface.add(Transaction2);
	  //  addEmptyLine(preface, 1);

	    preface.add(divide);
	   
	    PdfPTable table = createTable(txn);
	    addEmptyLine(preface, 1);
	    preface.add(table);

	    
	    addEmptyLine(preface, 2);
	    PdfPTable totalTable = createTotalTable(txn);
	    preface.add(totalTable);
	    
	    addEmptyLine(preface, 12);
	    
	     
	    Paragraph footer = new Paragraph();
	    divide.setAlignment(Element.ALIGN_BOTTOM);
	    footer.add(divide);
	    Paragraph footermsg = new Paragraph("Thank you for shopping with " + storeData.getStoreName() + "!");
	    footermsg.setAlignment(Element.ALIGN_CENTER);
	    footer.add(footermsg);
	    Paragraph footermsg2 = new Paragraph("Visit us online at www.dj.com");
	    footermsg2.setAlignment(Element.ALIGN_CENTER);
	    footer.add(footermsg2);
	    footer.setAlignment(Element.ALIGN_BOTTOM);
	    preface.add(footer);
	    
	    document.add(preface);
	 //   document.add(footer);
	    // Start a new page
	    document.newPage();
  }

  private static void addContent(Document document) throws DocumentException {
	  Anchor anchor = new Anchor("First Chapter", catFont);
	    anchor.setName("First Chapter");

	    // Second parameter is the number of the chapter
	    Chapter catPart = new Chapter(new Paragraph(anchor), 1);

	    Paragraph subPara = new Paragraph("Subcategory 1", subFont);
	    Section subCatPart = catPart.addSection(subPara);
	    subCatPart.add(new Paragraph("Hello"));

	    subPara = new Paragraph("Subcategory 2", subFont);
	    subCatPart = catPart.addSection(subPara);
	    subCatPart.add(new Paragraph("Paragraph 1"));
	    subCatPart.add(new Paragraph("Paragraph 2"));
	    subCatPart.add(new Paragraph("Paragraph 3"));

	    // add a list
	    createList(subCatPart);
	    Paragraph paragraph = new Paragraph();
	    addEmptyLine(paragraph, 5);
	    subCatPart.add(paragraph);

	    // add a table
	  //  createTable(subCatPart);

	    // now add all this to the document
	    document.add(catPart);

	    // Next section
	    anchor = new Anchor("Second Chapter", catFont);
	    anchor.setName("Second Chapter");

	    // Second parameter is the number of the chapter
	    catPart = new Chapter(new Paragraph(anchor), 1);

	    subPara = new Paragraph("Subcategory", subFont);
	    subCatPart = catPart.addSection(subPara);
	    subCatPart.add(new Paragraph("This is a very important message"));

	    // now add all this to the document
	    document.add(catPart);

  }

  private static PdfPTable createTable(Transaction txn)
	      throws BadElementException {
	  
	 
	  	PdfPTable table = new PdfPTable(4);
	    table.setWidthPercentage(100);
	    table.setSpacingAfter(3);
	    table.setSpacingBefore(4);
	    // t.setBorderColor(BaseColor.GRAY);
	    // t.setPadding(4);
	    // t.setSpacing(4);
	    // t.setBorderWidth(1);

	    PdfPCell c1 = new PdfPCell(new Phrase("Item ID"));
	    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    c1.setBackgroundColor(BaseColor.GREEN.darker());
	    table.addCell(c1);

	    c1 = new PdfPCell(new Phrase("Item Description"));
	    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    c1.setBackgroundColor(BaseColor.GREEN.darker());
	    table.addCell(c1);

	    c1 = new PdfPCell(new Phrase("Quantity"));
	    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    c1.setBackgroundColor(BaseColor.GREEN.darker());
	    table.addCell(c1);
	    

	    c1 = new PdfPCell(new Phrase("Price"));
	    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    c1.setBackgroundColor(BaseColor.GREEN.darker());
	    table.addCell(c1);
	    table.setHeaderRows(1);
	   
	    Item[] products = txn.getLineItem();

	    for(int i=0; i<products.length; i++)
	    {
	    	
	    table.addCell(products[i].getItemID());
	    table.addCell(products[i].getItemDesc());
	    table.addCell(products[i].getQuantity());
	    table.addCell(products[i].getItemPrice());
	    
	    }
	    return table;

	  }
	  
	  
	  private static PdfPTable createTotalTable(Transaction txn)
		      throws BadElementException {
		    PdfPTable totaltable = new PdfPTable(2);
		    totaltable.setWidthPercentage(50);
		    totaltable.setHorizontalAlignment(Element.ALIGN_RIGHT);
		    // t.setBorderColor(BaseColor.GRAY);
		    // t.setPadding(4);
		    // t.setSpacing(4);
		    // t.setBorderWidth(1);

		    PdfPCell c1 = new PdfPCell(new Phrase("TOTAL excl Tax"));
		    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		   c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		    totaltable.addCell(c1);

		    Item[] products = txn.getLineItem();
		    float lineItemTotal = 0.0f;
		    
		    for(int i=0; i<products.length; i++)
		    {
		    	
		
		    	float lineItem = Float.parseFloat(products[i].getItemPrice());
		    	lineItemTotal = lineItemTotal+ lineItem;
		    
		    }
		    
		    BigDecimal bdTotal = round(lineItemTotal, 2);
		    c1 = new PdfPCell(new Phrase(bdTotal.toString()));
		    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		    totaltable.addCell(c1);
		
		    c1 = new PdfPCell(new Phrase("TOTAL TAX PAYABLE"));
		    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		    totaltable.addCell(c1);
		    

		    String taxPayable = String.valueOf(txn.getTransactionTax());
		    c1 = new PdfPCell(new Phrase(taxPayable));
		    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		   c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		    totaltable.addCell(c1);
		    
		    c1 = new PdfPCell(new Phrase("DISCOUNT"));
		    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		    totaltable.addCell(c1);
		    

		    c1 = new PdfPCell(new Phrase("0.00"));
		    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		    totaltable.addCell(c1);
		    
		    Tender[] tender = txn.getTenderLineItem();
		    c1 = new PdfPCell(new Phrase("PAID BY"));
		    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		    totaltable.addCell(c1);
		    
		    String tenderType = tender[0].getTenderType();
		    c1 = new PdfPCell(new Phrase(tenderType));
		    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		    totaltable.addCell(c1);
		    
		    
		    c1 = new PdfPCell(new Phrase("TOTAL AMOUNT PAID"));
		    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    c1.setBackgroundColor(BaseColor.GREEN.darker());
		    totaltable.addCell(c1);
		    
		   
		    String total = Float.toString(tender[0].getTotal());
		    c1 = new PdfPCell(new Phrase(total));
		    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    c1.setBackgroundColor(BaseColor.GREEN.darker());
		    totaltable.addCell(c1);
		  

		    return totaltable;

		  }

  private static void createList(Section subCatPart) {
    List list = new List(true, false, 10);
    list.add(new ListItem("First point"));
    list.add(new ListItem("Second point"));
    list.add(new ListItem("Third point"));
    subCatPart.add(list);
  }

  public static BigDecimal round(float d, int decimalPlace) {
      BigDecimal bd = new BigDecimal(Float.toString(d));
      bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);       
      return bd;
  }
	
  
  private static void addEmptyLine(Paragraph paragraph, int number) {
    for (int i = 0; i < number; i++) {
      paragraph.add(new Paragraph(" "));
    }
  }
}


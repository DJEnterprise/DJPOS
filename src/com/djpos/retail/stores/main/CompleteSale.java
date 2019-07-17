package com.djpos.retail.stores.main;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;

public class CompleteSale extends DJPOSConstants{
	 String txnID = null;

	public String insertTransaction(Transaction txn, DJPOSDatabase storeDB, Store storeData)
	{
		retriveTransactionNumber(storeDB);
		insertTransactionTable(txn, storeDB, storeData);
		return txnID;
	}

	
	private void insertTransactionTable(Transaction txn, DJPOSDatabase storeDB, Store storeData) {
		
		if((txn!=null) && (txnID!=null) && (storeData!=null))
		{
			
			 ContentValues localContentValues = new ContentValues();
		  	 localContentValues.put(STORE_ID, storeData.getStoreID());
		  	 localContentValues.put(REG_ID, "001");
		  	 localContentValues.put(TRANSACTION_ID, txnID);
		  	 localContentValues.put(BUSINESS_DATE, getCurrentBusinessDate());
		  	 localContentValues.put(TRANSACTION_TYPE, "Sale");
		  	 localContentValues.put(TRANSACTION_STATUS, "Completed");
		  	 
		  	 if(txn.getCustomerTxn()!=null)
		  	 {
		  		 if(txn.getCustomerTxn().getCustomerID()!=null)
		  		 {
		  			 localContentValues.put(CUST_ID, txn.getCustomerTxn().getCustomerID());
		  		 }
		  	 }
		  	 
		  	 storeDB.getDb().insert(TABLE_TRANSACTION, null, localContentValues);
		  	 
		  	
		  	 insertSaleLineItemTable(txn, storeDB, storeData);
		  	 insertTenderLineItemTable(txn, storeDB, storeData);
		  	 insertTransactionTaxLineItemTable(txn, storeDB, storeData);
		  	 
		}
		
	}
	

	private void insertSaleLineItemTable(Transaction txn, DJPOSDatabase storeDB, Store storeData) {
		
		if((txn!=null) && (txn.getLineItem()!=null))
		{
		 
			int l = (txn.getLineItem().length);
		 
		 for(int j =0; j<l; j++)
		 {
		 
		 Item[] prd = txn.getLineItem(); 
		 ContentValues localContentValues = new ContentValues();
	  	 localContentValues.put(STORE_ID, storeData.getStoreID());
	  	 localContentValues.put(REG_ID, "001");
	  	 localContentValues.put(TRANSACTION_ID, txnID);
	  	 localContentValues.put(BUSINESS_DATE, getCurrentBusinessDate());
	  	 localContentValues.put(ITEM_ID, prd[j].getItemID());
	  	 localContentValues.put(ITEM_DESC, prd[j].getItemDesc());
	  	 localContentValues.put(ITEM_PRICE, prd[j].getItemPrice());
	  	 
		 storeDB.getDb().insert(TABLE_SALE_LINE_ITEM, null, localContentValues);
		 }
		}
	}

	private void insertTenderLineItemTable(Transaction txn,
			DJPOSDatabase storeDB, Store storeData) {
	
		if((txn!=null) && (txn.getLineItem()!=null))
		{
		
			Tender[] tender = txn.getTenderLineItem();
			
			 ContentValues localContentValues = new ContentValues();
		  	 localContentValues.put(STORE_ID, storeData.getStoreID());
		  	 localContentValues.put(REG_ID, "001");
		  	 localContentValues.put(TRANSACTION_ID, txnID);
		  	 localContentValues.put(BUSINESS_DATE, getCurrentBusinessDate());
		  	 localContentValues.put(TENDER_TYPE, "CASH");
		  	 localContentValues.put(TRANSACTION_AMOUNT, tender[0].getTotal());
		  	 
		  	 storeDB.getDb().insert(TABLE_TENDER_LINE_ITEM, null, localContentValues);
		}
		
		
	}


	private void insertTransactionTaxLineItemTable(Transaction txn, DJPOSDatabase storeDB, Store storeData) {
		
		if((txn!=null) && (txn.getLineItem()!=null))
		{
		 
			int l = (txn.getLineItem().length);
		 
		 for(int j =0; j<l; j++)
		 {
		 
		 Item[] prd = txn.getLineItem(); 
		 ContentValues localContentValues = new ContentValues();
	  	 localContentValues.put(STORE_ID, storeData.getStoreID());
	  	 localContentValues.put(REG_ID, "001");
	  	 localContentValues.put(TRANSACTION_ID, txnID);
	  	 localContentValues.put(BUSINESS_DATE, getCurrentBusinessDate());
	  	 localContentValues.put(TAX_AMOUNT_TOTAL, String.valueOf(txn.getTransactionTax()));
	  	
	  	 
		 storeDB.getDb().insert(TABLE_TRANSACTION_TAX, null, localContentValues);
		 }
		}
	}


	private String getCurrentBusinessDate() {
		
		 SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");//dd/MM/yyyy
		    Date now = new Date();
		    String strDate = sdfDate.format(now);
		    return strDate;
		
	}


	private void retriveTransactionNumber(DJPOSDatabase storeDB) {
		
		String[] arrayOfString = { "NM_CNT_GEN", "ID_CNT_GEN"};
        Cursor localCursor = storeDB.getDb().query("CO_ID_GEN", arrayOfString, "NM_CNT_GEN = ?", new String[] { "TransactionID" }, null, null, null);
       
        localCursor.moveToFirst();
        while (true)
        {
          if (localCursor.isAfterLast())
          {
            localCursor.close();
           break;
          }
        txnID = localCursor.getString(localCursor.getColumnIndex("ID_CNT_GEN"));
        localCursor.moveToNext();
        }
      
       if (txnID==null){
    	   
    	   ContentValues localContentValues = new ContentValues();
	  	   localContentValues.put("NM_CNT_GEN", "TransactionID");
	  	 localContentValues.put("ID_CNT_GEN", "0001");
	  	   
	  	  /*  if (db.rawQuery("select * from AS_ITM", null).getCount() > 0)
	  	      getDb().update("taxes", localContentValues, "_id=1", null);*/
	  	    
	  	 storeDB.getDb().insert("CO_ID_GEN", null, localContentValues);
	  	txnID="0000";
       }
       else{
    	   ContentValues localContentValues = new ContentValues();
    	   String qualifer = "NM_CNT_GEN = 'TransactionID'";
    	   int trnID = Integer.parseInt(txnID);
    	   trnID = trnID + 1;
  	  	 String trxnID = Integer.toString(trnID);
	  	   localContentValues.put("ID_CNT_GEN", trxnID);
	  	    
	  	   
	  	  /*  if (db.rawQuery("select * from AS_ITM", null).getCount() > 0)
	  	      getDb().update("taxes", localContentValues, "_id=1", null);*/
	  	    
	  	 storeDB.getDb().update("CO_ID_GEN", localContentValues, qualifer, null);
    	   
	  	
       }
       
       int trnID = Integer.parseInt(txnID);
	  	 trnID = trnID + 1;
	  	 txnID = Integer.toString(trnID);
		
	}
	
	
	
}

package com.djpos.retail.stores.main;

import java.util.Calendar;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ReportGeneraterActivity extends  Activity {
	

	protected TextView mEdit;
	protected boolean toDateFlag;
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		setContentView(R.layout.activity_reportheader);
		
		
	}

	
		public void selectDate(View view) {
			toDateFlag=false;
			DialogFragment newFragment = new SelectDateFragment();
			newFragment.show(getFragmentManager(), "DatePicker");
			}
	
		public void selectToDate(View view) {
			toDateFlag = true;
			DialogFragment newFragment = new SelectDateFragment();
			newFragment.show(getFragmentManager(), "DatePicker");
			}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product, menu);
		return true;
	}
	
	public void populateSetDate(int year, int month, int day) {
		if(toDateFlag)
		{
			mEdit = (TextView)findViewById(R.id.ToDate);
			mEdit.setText(day+"-"+month+"-"+year);
		}
		else
		{
			mEdit = (TextView)findViewById(R.id.editText1);
			mEdit.setText(day+"-"+month+"-"+year);
		}
	}
	

	public void fetchReport(View view)
	{
		 mEdit = (TextView)findViewById(R.id.editText1);
		 System.out.println("From Date:"+  mEdit.getText().toString() );
		 String fromDate = mEdit.getText().toString();
		 mEdit = (TextView)findViewById(R.id.ToDate);
		 System.out.println("To Date:"+  mEdit.getText().toString() );
		 String toDate = mEdit.getText().toString();
		 
		 if((fromDate.length()>0) && (toDate.length()>0))
		 {
		 Intent inten = getIntent();
		 Bundle extras = getIntent().getExtras();
		 DJPOSDatabase storeDB =  (DJPOSDatabase) extras.get("djposdb");
		 
		 String[] arrayOfString = { DJPOSConstants.TRANSACTION_ID, DJPOSConstants.TRANSACTION_TYPE};
		// Cursor localCursor = storeDB.getDb().query(DJPOSConstants.TABLE_TRANSACTION, arrayOfString, "DC_DY_BSN = '" +fromDate+ "'", new String[] { "TransactionID" }, null, null, null);
	       /* Cursor localCursor = storeDB.getDb().query(DJPOSConstants.TABLE_TRANSACTION, arrayOfString, "DC_DY_BSN BETWEEN '" + fromDate + "' AND '"+
	        		toDate+ "'", new String[] { "TransactionID" }, null, null, null);*/
		 String sql = "select AI_TRN, TY_TRN, DC_DY_BSN from TR_TRN where DC_DY_BSN BETWEEN ? AND ?";
		 
		 String verifiedFromDate = checkDate(fromDate);
		 String verifiedToDate = checkDate(toDate);
		 
		 
		 String[] qualifiers = new String[] {verifiedFromDate, verifiedToDate};
		 Cursor localCursor = storeDB.getDb().rawQuery(sql, qualifiers);
		 int length = localCursor.getCount();
		 Transaction[] retrievedTxnList = new Transaction[length];
	        localCursor.moveToFirst();
	        int i =0;
	        while (true)
	        {
	        	
	          if (localCursor.isAfterLast())
	          {
	            localCursor.close();
	           break;
	          }
	      String  txnID = localCursor.getString(localCursor.getColumnIndex(DJPOSConstants.TRANSACTION_ID));
	      
	      if(retrievedTxnList[i]==null)
	    	  retrievedTxnList[i] = new Transaction();
	  		
	      retrievedTxnList[i].setTransactionID(txnID);
	      String businessDate = localCursor.getString(localCursor.getColumnIndex(DJPOSConstants.BUSINESS_DATE));
	      retrievedTxnList[i].setBusinessDate(businessDate);
	      i++;
	        localCursor.moveToNext();
	        }
	        
	    if(length>0)
	    {
	    	retrievedTxnList = getTransactionLineItems(retrievedTxnList, storeDB);
	    	retrievedTxnList = getTenderLineItems(retrievedTxnList , storeDB);
	    	retrievedTxnList = getTaxLineItems(retrievedTxnList , storeDB);
	       
	        
	        Intent intent = new Intent(this, ReportListDisplayActivity.class);
	    	intent.putExtra("txnList", retrievedTxnList);
    		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	startActivity(intent);
	    }
		 }
		 else
		 {
			 Toast.makeText(this, R.string.choose_date, Toast.LENGTH_LONG).show();
		 }
		
	}
	
	private Transaction[] getTenderLineItems(Transaction[] retrievedTxnList, DJPOSDatabase storeDB) {
		 for(int j=0; j<retrievedTxnList.length; j++)
	        {
			 String sql = "select TY_TND, MO_ITM_LN_TND from TR_LTM_TND where AI_TRN = ?";
			 
			 String[] qualifiers = new String[] {retrievedTxnList[j].getTransactionID()};
			 Cursor localCursor = storeDB.getDb().rawQuery(sql, qualifiers);
			 int length = localCursor.getCount();
			 Tender[] tenderList = new Tender[length];
		        localCursor.moveToFirst();
		        int i=0;
		        while(true)
		        {

			          if (localCursor.isAfterLast())
			          {
			        	  retrievedTxnList[j].setTenderLineItem(tenderList);
			            localCursor.close();
			           break;
			          }
			      String  tenderType = localCursor.getString(localCursor.getColumnIndex(DJPOSConstants.TENDER_TYPE));
			      String  tenderAmount = localCursor.getString(localCursor.getColumnIndex(DJPOSConstants.TRANSACTION_AMOUNT));
			      
			      if(tenderList[i]==null)
			    	  tenderList[i] = new Tender();
			      tenderList[i].setTenderType(tenderType);
			      tenderList[i].setTotal(Float.parseFloat(tenderAmount));
			     tenderList[i].setSubTotal(Float.parseFloat(tenderAmount));
			      
			      i++;
			        localCursor.moveToNext();
			        
		        }
		        
	        }
		return retrievedTxnList;
	}


	private Transaction[] getTransactionLineItems(Transaction[] retrievedTxnList, DJPOSDatabase storeDB) {
		
		 for(int j=0; j<retrievedTxnList.length; j++)
	        {
			 String sql = "select ID_ITM, DE_ITM, PRC_ITM from TR_LTM_SLS_RTN where AI_TRN = ?";
			 
			 String[] qualifiers = new String[] {retrievedTxnList[j].getTransactionID()};
			 Cursor localCursor = storeDB.getDb().rawQuery(sql, qualifiers);
			 int length = localCursor.getCount();
			 Item[] itemList = new Item[length];
		        localCursor.moveToFirst();
		        int i=0;
		        while(true)
		        {

			          if (localCursor.isAfterLast())
			          {
			        	  retrievedTxnList[j].setLineItem(itemList);
			            localCursor.close();
			           break;
			          }
			      String  itemID = localCursor.getString(localCursor.getColumnIndex(DJPOSConstants.ITEM_ID));
			      String  itemDesc = localCursor.getString(localCursor.getColumnIndex(DJPOSConstants.ITEM_DESC));
			      String  itemPrice = localCursor.getString(localCursor.getColumnIndex(DJPOSConstants.ITEM_PRICE));
			      if(itemList[i]==null)
			    	  itemList[i] = new Item();
			      itemList[i].setItemID(itemID);
			      itemList[i].setItemDesc(itemDesc);
			      itemList[i].setItemPrice(itemPrice);
			      
			      i++;
			        localCursor.moveToNext();
			        
		        }
		        
	        }
	        
		 
		return retrievedTxnList;
	}


	private Transaction[] getTaxLineItems(Transaction[] retrievedTxnList, DJPOSDatabase storeDB) {
		 for(int j=0; j<retrievedTxnList.length; j++)
	        {
			 String sql = "select MO_TX_RTN_SLS_TOT from TR_TX_LN_ITM where AI_TRN = ?";
			 
			 String[] qualifiers = new String[] {retrievedTxnList[j].getTransactionID()};
			 Cursor localCursor = storeDB.getDb().rawQuery(sql, qualifiers);
			 int length = localCursor.getCount();
		        localCursor.moveToFirst();
		     
		        while(true)
		        {

			          if (localCursor.isAfterLast())
			          {
			        	 
			            localCursor.close();
			           break;
			          }
			      String  transactionTax = localCursor.getString(localCursor.getColumnIndex(DJPOSConstants.TAX_AMOUNT_TOTAL));
			      retrievedTxnList[j].setTransactionTax(Float.parseFloat(transactionTax));
			     
			        localCursor.moveToNext();
			        
		        }
		        
	        }
		return retrievedTxnList;
	}

	
	private String checkDate(String fromDate) {
		
		 String finalDate ="";
		 if(fromDate.length()!=10)
		 {
			 String check1 = fromDate.substring(1, 2);
			 if(check1.equals("-"))
			 {
				 fromDate = "0"+fromDate;
				 finalDate = fromDate;
			 }
			 if(fromDate.length()!=10)
			 {
				 String check2 = fromDate.substring(4, 5);
				 if(check2.equals("-"))
				 {
					 String sb1 = fromDate.substring(0, 3);
					 String sb2 = fromDate.substring(3, 4);
					 String sb3 = fromDate.substring(4);
					 sb2 = "0"+sb2;
					 
					 finalDate = sb1+sb2+sb3;
					 System.out.println("FINAL DATE :" + finalDate);
				 }
			 }
				 
		 }
		 else
		 {
			 finalDate = fromDate;
		 }
		return finalDate;
	}

	public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar calendar = Calendar.getInstance();
		int yy = calendar.get(Calendar.YEAR);
		int mm = calendar.get(Calendar.MONTH);
		int dd = calendar.get(Calendar.DAY_OF_MONTH);
		return new DatePickerDialog(getActivity(), this, yy, mm, dd);
		}

		@Override
		public void onDateSet(DatePicker arg0, int yy, int mm, int dd) {
			
			
			populateSetDate(yy, mm+1, dd);
			
			
		}
	}
	
}

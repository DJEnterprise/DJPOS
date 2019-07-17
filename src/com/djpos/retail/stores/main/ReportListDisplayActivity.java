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
import android.widget.ListView;
import android.widget.TextView;

public class ReportListDisplayActivity extends  Activity {
	
	
	protected TextView prodID; 
	protected TextView prodDesc; 
	protected TextView prodPrice; 
	protected EditText mEdit;
	protected boolean toDateFlag;
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		setContentView(R.layout.activity_reportlist);
		
		 Intent inten = getIntent();
		 Bundle extras = getIntent().getExtras();
		 Object[] retrievedTxnListObject =  (Object[]) extras.get("txnList");
		int length = retrievedTxnListObject.length;
		
		Transaction[] retrievedTxnList = new Transaction[length];
		float total = 0.0f;
		for(int i=0; i<length; i++)
		{
			if(retrievedTxnList[i]==null)
			{
				retrievedTxnList[i] = new Transaction();
			}
			retrievedTxnList[i] = (Transaction) retrievedTxnListObject[i];
			
			if(retrievedTxnList[i].getTenderLineItem()!=null)
			{
				Tender[] tender = retrievedTxnList[i].getTenderLineItem();
				for(int j=0;j<tender.length; j++)
				{
					total = total + tender[j].getTotal();
				}
			}
		}
		
		
		 ListView listView ;
	      
         listView = (ListView) findViewById(R.id.reportList);
         
         ReportListViewAdapter adapter;
       
       
  
         
         adapter = new ReportListViewAdapter(this,
        		 retrievedTxnList);
         
         listView.setAdapter(adapter);
         
         TextView textView = (TextView) findViewById(R.id.totalSalesAmount);
         
         textView.setText(Float.toString(total));
         
	}

	
	
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product, menu);
		return true;
	}
	
		
}

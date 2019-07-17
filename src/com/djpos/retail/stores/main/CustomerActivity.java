package com.djpos.retail.stores.main;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerActivity extends FragmentActivity implements CustomerFragment.OnHeadlineSelectedListener {
	
	
	protected TextView custID; 
	protected TextView custName; 
	protected TextView custAddress; 
	protected TextView custCity; 
	protected TextView custZipCode; 
	protected TextView custEmail; 
	protected TextView custPhone; 
	protected TextView  customerViewID ;
	protected Customer customerSearched;
	protected TextView custIDDisplay; 
	protected TextView custNameDisplay; 
	protected TextView custAddressDisplay; 
	protected TextView custCityDisplay; 
	protected TextView custZipCodeDisplay; 
	protected TextView custEmailDisplay; 
	protected TextView custPhoneDisplay; 
	private String customerID;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		setContentView(R.layout.activity_customer);
		
		 // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if ((findViewById(R.id.customer_container) != null)){

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of ExampleFragment
            CustomerFragment firstFragment = new CustomerFragment();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.customer_container, firstFragment).commit();
        }
       
        
       
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product, menu);
		return true;
	}
	
	
	public void onArticleSelected(int position) {
        // The user selected the headline of an article from the HeadlinesFragment

		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		
		if(position == 0)
		{
			 View localView = getLayoutInflater().inflate(R.layout.add_customer, (ViewGroup)findViewById(R.id.customer_Layout));
			 
			 Intent inten = getIntent();
	    	  Bundle extras = getIntent().getExtras();
			 DJPOSDatabase customerDB =  (DJPOSDatabase) extras.get("djposdb");
	    	  System.out.println("DB Received Succesfully");
	    	
	    	  
			 String nextCustomerID = retriveCustomerID(customerDB);
			 
			 custID = (TextView) localView.findViewById(R.id.cusIDEdit);
			 custID.setText(nextCustomerID);
		  		custName = (TextView) localView.findViewById(R.id.cusNameEdit);
		  		custAddress = (TextView) localView.findViewById(R.id.cusAddEdit);
		  		custCity = (TextView) localView.findViewById(R.id.cusCityEdit);
		  		custZipCode = (TextView) localView.findViewById(R.id.cusZipEdit);
		  		custEmail = (TextView) localView.findViewById(R.id.cusEmailEdit);
		  		custPhone = (TextView) localView.findViewById(R.id.cusPhoneEdit);
		  		
		alertDialog.setTitle("Add Customer");
		alertDialog.setView(localView);
		alertDialog.setPositiveButton("Add", new DialogInterface.OnClickListener()
		{
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
    	  Intent inten = getIntent();
    	  Bundle extras = getIntent().getExtras();
		 DJPOSDatabase storeDB =  (DJPOSDatabase) extras.get("djposdb");
    	  
    	  SQLiteDatabase sf = storeDB.getDb();
  		
    	  String str1 = custID.getText().toString();
    	  String str2 =  custName.getText().toString();
    	 String str3 = custAddress.getText().toString();
    	 String str4 = custCity.getText().toString();
    	 String str5 = custZipCode.getText().toString();
    	 String str6 = custEmail.getText().toString();
    	 String str7 = custPhone.getText().toString();
    	//  float int4 = Integer.parseInt(str3);
    	  
    	 // insertItem(sf, str1, str2, int4);
    	  
    	ContentValues localContentValues = new ContentValues();
  	    localContentValues.put(DJPOSConstants.CUST_ID, str1);
  	    localContentValues.put(DJPOSConstants.CUST_NAME, str2);
  	    localContentValues.put(DJPOSConstants.CUST_ADDRESS, str3);
  	  localContentValues.put(DJPOSConstants.CUST_CITY, str4);
  	 localContentValues.put(DJPOSConstants.CUST_ZIPCODE, str5);
  	 localContentValues.put(DJPOSConstants.CUST_EMAIL, str6);
  	 localContentValues.put(DJPOSConstants.CUST_PHONE, str7);
  	   
  	  /*  if (db.rawQuery("select * from AS_ITM", null).getCount() > 0)
  	      getDb().update("taxes", localContentValues, "_id=1", null);*/
  	    
  	 try{
  		 sf.insert(DJPOSConstants.TABLE_CUSTOMER, null, localContentValues);
  	  // 	Toast.makeText(this, R.string.data_save, Toast.LENGTH_LONG).show();
  	 }
  	 catch(Exception e)
  	 {
  		 e.printStackTrace();
  	 }
        //insertCustomer();
      }
    });
		alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
		{
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        paramDialogInterface.cancel();
      }
    });
		alertDialog.show();
		
		}
		//for product insert
		else if(position == 1)
		{
	  	    
			AlertDialog.Builder aboutAlertDialog = new AlertDialog.Builder(this);
    		aboutAlertDialog.setTitle("Enter Customer ID");
			 View aboutView = getLayoutInflater().inflate(R.layout.customer_link, (ViewGroup)findViewById(R.id.customerlink_Layout));
			 customerViewID =  (TextView) aboutView.findViewById(R.id.customerIDLinkEdit );
			  aboutAlertDialog.setView(aboutView);
			  
    		aboutAlertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener()
			{
	      public void onClick(DialogInterface paramDialogInterface, int paramInt)
	      {
	        String cusLinkID = customerViewID.getText().toString();
	        Intent inten = getIntent();
	    	  Bundle extras = getIntent().getExtras();
	    	  
	        if((cusLinkID!=null) && (!(cusLinkID.equalsIgnoreCase(""))))
	        {
	        	  DJPOSDatabase storeDB =  (DJPOSDatabase) extras.get("djposdb");
	        	  System.out.println("DB Received Succesfully");
	        	  SQLiteDatabase sf = storeDB.getDb();
	        	  
	        String[] arrayOfString = { DJPOSConstants.CUST_ID, DJPOSConstants.CUST_NAME, DJPOSConstants.CUST_ADDRESS, 
	        		DJPOSConstants.CUST_CITY,DJPOSConstants.CUST_ZIPCODE,DJPOSConstants.CUST_PHONE, DJPOSConstants.CUST_EMAIL};
	        Cursor localCursor = sf.query(DJPOSConstants.TABLE_CUSTOMER, arrayOfString, "ID_CUST = ?", new String[] { cusLinkID }, null, null, null);
	        Customer customerDetails = new Customer();
	        localCursor.moveToFirst();
	        while (true)
	        {
	          if (localCursor.isAfterLast())
	          {
	            localCursor.close();
	           break;
	          }
	          customerDetails.setCustomerID(localCursor.getString(localCursor.getColumnIndex(DJPOSConstants.CUST_ID)));
	          customerDetails.setCustomerName(localCursor.getString(localCursor.getColumnIndex(DJPOSConstants.CUST_NAME)));
	          customerDetails.setCustomerAddress(localCursor.getString(localCursor.getColumnIndex(DJPOSConstants.CUST_ADDRESS)));
	          customerDetails.setCustomerCity(localCursor.getString(localCursor.getColumnIndex(DJPOSConstants.CUST_CITY)));
	          customerDetails.setCustomerZipCode(localCursor.getString(localCursor.getColumnIndex(DJPOSConstants.CUST_ZIPCODE)));
	          customerDetails.setCustomerPhone(localCursor.getString(localCursor.getColumnIndex(DJPOSConstants.CUST_PHONE)));
	          customerDetails.setCustomerEmail(localCursor.getString(localCursor.getColumnIndex(DJPOSConstants.CUST_EMAIL)));
	          localCursor.moveToNext();
	          customerSearched = customerDetails;
	        }
	        
	        
	    	if(customerSearched!=null)
    			showCustomer();
	       
	        }
	    	
	       
	      }

	
			 }
    		
	    );
    		aboutAlertDialog.show();
    		
    	
		}
		

	
    }

	private void showCustomer() {
		
		AlertDialog.Builder customerShowDialog = new AlertDialog.Builder(this);
		customerShowDialog.setTitle("Customer Details");
		 View aboutView = getLayoutInflater().inflate(R.layout.customer_show, (ViewGroup)findViewById(R.id.customershow_Layout));
		 custIDDisplay =  (TextView) aboutView.findViewById(R.id.customerIDDisplay );
		 custIDDisplay.setText(customerSearched.getCustomerID());
		 custNameDisplay =  (TextView) aboutView.findViewById(R.id.customerNameDisplay );
		 custNameDisplay.setText(customerSearched.getCustomerName());
		 custAddressDisplay =  (TextView) aboutView.findViewById(R.id.customerAddressDisplay );
		 custAddressDisplay.setText(customerSearched.getCustomerAddress());
		 custCityDisplay =  (TextView) aboutView.findViewById(R.id.customerCityDisplay );
		 custCityDisplay.setText(customerSearched.getCustomerCity());
		 custZipCodeDisplay =  (TextView) aboutView.findViewById(R.id.customerZipDisplay );
		 custZipCodeDisplay.setText(customerSearched.getCustomerZipCode());
		 custPhoneDisplay =  (TextView) aboutView.findViewById(R.id.customerPhoneDisplay );
		 custPhoneDisplay.setText(customerSearched.getCustomerPhone());
		 custEmailDisplay =  (TextView) aboutView.findViewById(R.id.customerEmailDisplay );
		 custEmailDisplay.setText(customerSearched.getCustomerEmail());
	
		 customerShowDialog.setView(aboutView);
		  
		 customerShowDialog.setNeutralButton("OK", new DialogInterface.OnClickListener()
		{
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
    	  paramDialogInterface.cancel();
      }
		});
		
		 customerShowDialog.show();
	}
	
	
	protected void insertCustomer() {
		
		View custView = getLayoutInflater().inflate(R.layout.add_customer, (ViewGroup)findViewById(R.id.customer_Layout));
		custID = (TextView) custView.findViewById(R.id.cusIDEdit);
		custName = (TextView) custView.findViewById(R.id.cusNameEdit);
		custAddress = (TextView) custView.findViewById(R.id.cusAddEdit);
		custCity = (TextView) custView.findViewById(R.id.cusCityEdit);
		custZipCode = (TextView) custView.findViewById(R.id.cusZipEdit);
		custEmail = (TextView) custView.findViewById(R.id.cusEmailEdit);
		custPhone = (TextView) custView.findViewById(R.id.cusPhoneEdit);

		
    	  Intent inten = getIntent();
    	  Bundle extras = getIntent().getExtras();
    	  
    	  String str1 = custID.getText().toString();
    	  String str2 =  custName.getText().toString();
    	 String str3 = custAddress.getText().toString();
    	 String str4 = custCity.getText().toString();
    	 String str5 = custZipCode.getText().toString();
    	 String str6 = custEmail.getText().toString();
    	 String str7 = custPhone.getText().toString();
    	//  float int4 = Integer.parseInt(str3);
    	  DJPOSDatabase storeDB =  (DJPOSDatabase) extras.get("djposdb");
    	  System.out.println("DB Received Succesfully");
    	  SQLiteDatabase sf = storeDB.getDb();
    	 // insertItem(sf, str1, str2, int4);
    	  
    	ContentValues localContentValues = new ContentValues();
  	    localContentValues.put(DJPOSConstants.CUST_ID, str1);
  	    localContentValues.put(DJPOSConstants.CUST_NAME, str2);
  	    localContentValues.put(DJPOSConstants.CUST_ADDRESS, str3);
  	  localContentValues.put(DJPOSConstants.CUST_CITY, str4);
  	 localContentValues.put(DJPOSConstants.CUST_ZIPCODE, str5);
  	 localContentValues.put(DJPOSConstants.CUST_EMAIL, str6);
  	 localContentValues.put(DJPOSConstants.CUST_PHONE, str7);
  	   
  	  /*  if (db.rawQuery("select * from AS_ITM", null).getCount() > 0)
  	      getDb().update("taxes", localContentValues, "_id=1", null);*/
  	    
  	 try{
  		 sf.insert(DJPOSConstants.TABLE_CUSTOMER, null, localContentValues);
  	   	Toast.makeText(this, R.string.data_save, Toast.LENGTH_LONG).show();
  	 }
  	 catch(Exception e)
  	 {
  		 e.printStackTrace();
  	 }
  	     
	    
	}
	
	

	private String retriveCustomerID(DJPOSDatabase storeDB) {
		
		String[] arrayOfString = { "NM_CNT_GEN", "ID_CNT_GEN"};
        Cursor localCursor = storeDB.getDb().query("CO_ID_GEN", arrayOfString, "NM_CNT_GEN = ?", new String[] { "CustomerID" }, null, null, null);
       
        localCursor.moveToFirst();
        while (true)
        {
          if (localCursor.isAfterLast())
          {
            localCursor.close();
           break;
          }
          customerID= localCursor.getString(localCursor.getColumnIndex("ID_CNT_GEN"));
        localCursor.moveToNext();
        }
      
       if (customerID==null){
    	   
    	   ContentValues localContentValues = new ContentValues();
	  	   localContentValues.put("NM_CNT_GEN", "CustomerID");
	  	 localContentValues.put("ID_CNT_GEN", "10000");
	  	   
	  	 storeDB.getDb().insert("CO_ID_GEN", null, localContentValues);
	  	customerID="10000";
       }
       else{
    	   ContentValues localContentValues = new ContentValues();
    	   String qualifer = "NM_CNT_GEN = 'CustomerID'";
    	   int cusID = Integer.parseInt(customerID);
    	   cusID = cusID + 1;
  	  	 String newCustID = Integer.toString(cusID);
	  	   localContentValues.put("ID_CNT_GEN", newCustID);
	  	    
	  	   
	  	  
	  	 storeDB.getDb().update("CO_ID_GEN", localContentValues, qualifer, null);
    	   
	  	 customerID=newCustID;
	  	
       }
       
      return customerID;
		
	}
	

}

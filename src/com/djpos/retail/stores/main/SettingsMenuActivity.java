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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsMenuActivity extends  Activity {
	

	protected TextView mEdit;
	protected boolean toDateFlag;
	protected ListView settingsMenuList;
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		setContentView(R.layout.activity_settings);

		String[] settingMenu = getResources().getStringArray(R.array.settings_menu);
		settingsMenuList =  (ListView) findViewById(R.id.settingsList);
		settingsMenuList.setAdapter(new ArrayAdapter<String>(this,
	                R.layout.settings_list_item, settingMenu));
		settingsMenuList.setOnItemClickListener(new SettingsItemClickListner());
		
		showStoreSettings(settingsMenuList);
		
	}
	
	  private class SettingsItemClickListner implements ListView.OnItemClickListener {
	        @Override
	  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          selectItem(view, position);
      }
	  }
	  
	  private void selectItem(View view, int position) {
	    	
	    	
	    	switch(position){
	    	case 0:
	    		System.out.println("Store Settings Selected");
	    		showStoreSettings(view);
	    	       
	    		break;
	    		
	    	case 1:
	    		System.out.println("Tax Settings Selected");
	    		showTaxSettings(view);
	    		break;
	    		default:
	    			break;
	    	}
	    	}

	  
	  public void showStoreSettings(View view)
	  {
		  
		  setContentView(R.layout.activity_settings);

			String[] settingMenu = getResources().getStringArray(R.array.settings_menu);
			settingsMenuList =  (ListView) findViewById(R.id.settingsList);
			settingsMenuList.setAdapter(new ArrayAdapter<String>(this,
		                R.layout.settings_list_item, settingMenu));
			settingsMenuList.setOnItemClickListener(new SettingsItemClickListner());
			
			
		 ViewGroup grp = (ViewGroup)findViewById(R.id.layout_settingsdetail);
  		 View storeSettingView = getLayoutInflater().inflate(R.layout.activity_storesettings, (ViewGroup)findViewById(R.id.layout_storesettings));
  		 Intent storeIntent = getIntent();
  		 Bundle extras = getIntent().getExtras();
  		 DJPOSDatabase storeDB =  (DJPOSDatabase) extras.get("djposdb");
  		 Store storeData = (Store) extras.get("storedata");
  		
  		 if((storeData!=null) && (!(storeData.getStoreName().isEmpty())))
  		 {
  			 EditText storeID = (EditText) storeSettingView.findViewById(R.id.storeIDEdit);
  			 EditText storeName = (EditText) storeSettingView.findViewById(R.id.storeNameEdit);
  			 EditText	storeAddress = (EditText) storeSettingView.findViewById(R.id.storeADDEdit);
  			 EditText	storeCity = (EditText) storeSettingView.findViewById(R.id.storeCityEdit);
  			 EditText	storeZip = (EditText) storeSettingView.findViewById(R.id.storeZipCodeEdit);

  			 EditText	storePhone = (EditText) storeSettingView.findViewById(R.id.storeTelephoneEdit);
  			 
  			 if(storeData.getStoreID()!=null)
  				 storeID.setText(storeData.getStoreID());
  			 
  			 if(storeData.getStoreName()!=null)
  				 storeName.setText(storeData.getStoreName());
  			 
  			 if(storeData.getstoreaddress1()!=null)
  				 storeAddress.setText(storeData.getstoreaddress1());
  			 
  			 if(storeData.getstoreaddress2()!=null)
  				 storeCity.setText(storeData.getstoreaddress2());
  			 
  			 if(storeData.getstoreaddress3()!=null)
  				 storeZip.setText(storeData.getstoreaddress3());
  			 
  			 if(storeData.getstorephone()!=null)
  				 storePhone.setText(storeData.getstorephone());
  			 grp.removeAllViews();
  			 grp.addView(storeSettingView); 
  			
  		 }
  		 else{
  			grp.removeAllViews();
  			 grp.addView(storeSettingView); 
  			 
  		 }
	  }
	  
	public void insertStoreData(View view)
	{
		View storeSettingView = getLayoutInflater().inflate(R.layout.activity_storesettings, (ViewGroup)findViewById(R.id.layout_storesettings));
		 Intent storeIntent = getIntent();
		 Bundle extras = getIntent().getExtras();
		 DJPOSDatabase storeDB =  (DJPOSDatabase) extras.get("djposdb");
		 
		 
		 TextView storeID = (TextView) storeSettingView.findViewById(R.id.storeIDEdit);
		 EditText storeName = (EditText) storeSettingView.findViewById(R.id.storeNameEdit);
		 EditText	storeAddress = (EditText) storeSettingView.findViewById(R.id.storeADDEdit);
		 EditText	storeCity = (EditText) storeSettingView.findViewById(R.id.storeCityEdit);
		 EditText	storeZip = (EditText) storeSettingView.findViewById(R.id.storeZipCodeEdit);

		 TextView	storePhone = (TextView) storeSettingView.findViewById(R.id.storeTelephoneEdit);
		 
		 if((storeID.getText().toString().isEmpty()) || (storeName.getText().toString().isEmpty()) || (storeAddress.getText().toString().isEmpty()) ||
				 (storeCity.getText().toString().isEmpty()) || (storeZip.getText().toString().isEmpty()) || (storePhone.getText().toString().isEmpty()))
		 {
			  Toast.makeText(this, R.string.store_details_error, Toast.LENGTH_LONG).show();
			  showStoreSettings(view);
		 }
		
		 		else{
		 String retrivedStoreID = null ;
		 

		 String[] arrayOfString = { DJPOSConstants.STORE_ID,DJPOSConstants.STORE_NAME, DJPOSConstants.STORE_ADDRESS, DJPOSConstants.STORE_CITY, 
				 DJPOSConstants.STORE_ZIPCODE, DJPOSConstants.STORE_PHONE};
	        Cursor localCursor = storeDB.getDb().query(DJPOSConstants.TABLE_STORE, arrayOfString, null, null , null, null, null);
	        Store storeDetails = new Store();
	        localCursor.moveToFirst();
	        while (true)
	        {
	          if (localCursor.isAfterLast())
	          {
	            localCursor.close();
	           break;
	          }
	          
	          retrivedStoreID=  localCursor.getString(localCursor.getColumnIndex(DJPOSConstants.STORE_ID));
	          
	          localCursor.moveToNext();
	        }
	      
	       if (retrivedStoreID==null){
	    	   
	    	   ContentValues localContentValues = new ContentValues();
		  	   localContentValues.put(DJPOSConstants.STORE_ID, storeID.getText().toString());
		  	 localContentValues.put(DJPOSConstants.STORE_NAME, storeName.getText().toString());
		  	localContentValues.put(DJPOSConstants.STORE_ADDRESS, storeAddress.getText().toString());
		  	localContentValues.put(DJPOSConstants.STORE_CITY, storeCity.getText().toString());
		  	localContentValues.put(DJPOSConstants.STORE_ZIPCODE, storeZip.getText().toString());
		  	localContentValues.put(DJPOSConstants.STORE_PHONE, storePhone.getText().toString());
		  	
		  	    
		  	 storeDB.getDb().insert(DJPOSConstants.TABLE_STORE, null, localContentValues);
		  
	       }
	       else{
	    	   ContentValues localContentValues = new ContentValues();
	    	   String qualifer = "ID_STR_RT = '" + retrivedStoreID.toString() + "'"; 
		  	   localContentValues.put(DJPOSConstants.STORE_ID, storeID.getText().toString());
		  	 localContentValues.put(DJPOSConstants.STORE_NAME, storeName.getText().toString());
		  	localContentValues.put(DJPOSConstants.STORE_ADDRESS, storeAddress.getText().toString());
		  	localContentValues.put(DJPOSConstants.STORE_CITY, storeCity.getText().toString());
		  	localContentValues.put(DJPOSConstants.STORE_ZIPCODE, storeZip.getText().toString());
		  	localContentValues.put(DJPOSConstants.STORE_PHONE, storePhone.getText().toString());
		  	    
		  	 storeDB.getDb().update(DJPOSConstants.TABLE_STORE, localContentValues, qualifer, null);
	    	   
	       }
	       
	       	Toast.makeText(this, R.string.data_save, Toast.LENGTH_LONG).show();
	       
	       	 Store insertedStoreData = new Store();
			 insertedStoreData.setStoreID(storeID.getText().toString());
			 insertedStoreData.setStoreName(storeName.getText().toString());
			 insertedStoreData.setstoreaddress1(storeAddress.getText().toString());
			 insertedStoreData.setstoreaddress2(storeCity.getText().toString());
			 insertedStoreData.setstoreaddress3(storeZip.getText().toString());
			 insertedStoreData.setstorephone(storePhone.getText().toString());
			 
			 showStoreSettings(view, insertedStoreData);
			 
	       }
	       
		
		 
		 
		
	}
	
	
	
	 public void showStoreSettings(View view, Store insertedStoreData)
	  {
		  
		  setContentView(R.layout.activity_settings);

			String[] settingMenu = getResources().getStringArray(R.array.settings_menu);
			settingsMenuList =  (ListView) findViewById(R.id.settingsList);
			settingsMenuList.setAdapter(new ArrayAdapter<String>(this,
		                R.layout.settings_list_item, settingMenu));
			settingsMenuList.setOnItemClickListener(new SettingsItemClickListner());
			
			
		 ViewGroup grp = (ViewGroup)findViewById(R.id.layout_settingsdetail);
 		 View storeSettingView = getLayoutInflater().inflate(R.layout.activity_storesettings, (ViewGroup)findViewById(R.id.layout_storesettings));
 		 Intent storeIntent = getIntent();
 		 Bundle extras = getIntent().getExtras();
 		 DJPOSDatabase storeDB =  (DJPOSDatabase) extras.get("djposdb");
 		
 		
 		 if((insertedStoreData!=null) && (!(insertedStoreData.getStoreName().isEmpty())))
 		 {
 			 EditText 	storeID = (EditText) storeSettingView.findViewById(R.id.storeIDEdit);
 			 EditText 	storeName = (EditText) storeSettingView.findViewById(R.id.storeNameEdit);
 			 EditText	storeAddress = (EditText) storeSettingView.findViewById(R.id.storeADDEdit);
 			 EditText	storeCity = (EditText) storeSettingView.findViewById(R.id.storeCityEdit);
 			 EditText	storeZip = (EditText) storeSettingView.findViewById(R.id.storeZipCodeEdit);

 			 EditText	storePhone = (EditText) storeSettingView.findViewById(R.id.storeTelephoneEdit);
 			 
 			 if(insertedStoreData.getStoreID()!=null)
 				 storeID.setText(insertedStoreData.getStoreID());
 			 
 			 if(insertedStoreData.getStoreName()!=null)
 				 storeName.setText(insertedStoreData.getStoreName());
 			 
 			 if(insertedStoreData.getstoreaddress1()!=null)
 				 storeAddress.setText(insertedStoreData.getstoreaddress1());
 			 
 			 if(insertedStoreData.getstoreaddress2()!=null)
 				 storeCity.setText(insertedStoreData.getstoreaddress2());
 			 
 			 if(insertedStoreData.getstoreaddress3()!=null)
 				 storeZip.setText(insertedStoreData.getstoreaddress3());
 			 
 			 if(insertedStoreData.getstorephone()!=null)
 				 storePhone.setText(insertedStoreData.getstorephone());
 			 grp.removeAllViews();
 			 grp.addView(storeSettingView); 
 			
 		 }
 		 else{
 			grp.removeAllViews();
 			 grp.addView(storeSettingView); 
 			 
 		 }
	  }
	 
	 
	 
	 public void showTaxSettings(View view)
	  {
		  
		  setContentView(R.layout.activity_settings);

			String[] settingMenu = getResources().getStringArray(R.array.settings_menu);
			settingsMenuList =  (ListView) findViewById(R.id.settingsList);
			settingsMenuList.setAdapter(new ArrayAdapter<String>(this,
		                R.layout.settings_list_item, settingMenu));
			settingsMenuList.setOnItemClickListener(new SettingsItemClickListner());
			
			
		 ViewGroup grp = (ViewGroup)findViewById(R.id.layout_settingsdetail);
 		 View taxSettingView = getLayoutInflater().inflate(R.layout.activity_taxsettings, (ViewGroup)findViewById(R.id.layout_taxsettings));
 		 Intent storeIntent = getIntent();
 		 Bundle extras = getIntent().getExtras();
 		 DJPOSDatabase storeDB =  (DJPOSDatabase) extras.get("djposdb");
 		 Store storeData = (Store) extras.get("storedata");
 		 Tax taxData = (Tax) extras.get("taxdata");
 		
 		 if((taxData!=null) && (!(taxData.getTaxName().isEmpty())))
 		 {
 			 EditText taxName = (EditText) taxSettingView.findViewById(R.id.TaxNameEdit);
  			 EditText taxPercentage = (EditText) taxSettingView.findViewById(R.id.TaxPercentageEdit);
 			 
  			 if(taxData.getTaxName()!=null)
  			 {
  				taxName.setText(taxData.getTaxName());  			 
  				taxPercentage.setText(Float.toString(taxData.getTaxPercentage()));
  			 }
  			
  			grp.removeAllViews();
			grp.addView(taxSettingView); 
			 
 		 }
 		 else
 		 {
 			grp.removeAllViews();
 			 grp.addView(taxSettingView); 
 		 } 
 		 
	  }
	 
	 
	 public void insertTaxData(View view)
		{
			View storeSettingView = getLayoutInflater().inflate(R.layout.activity_taxsettings, (ViewGroup)findViewById(R.id.layout_taxsettings));
			 Intent storeIntent = getIntent();
			 Bundle extras = getIntent().getExtras();
			 DJPOSDatabase storeDB =  (DJPOSDatabase) extras.get("djposdb");
			 
			 
			 EditText taxName = (EditText) storeSettingView.findViewById(R.id.TaxNameEdit);
			 TextView taxPercentage = (TextView) storeSettingView.findViewById(R.id.TaxPercentageEdit);


			 
			 if((taxName.getText().toString().isEmpty()) || (taxPercentage.getText().toString().isEmpty()))
			 {
				  Toast.makeText(this, R.string.store_details_error, Toast.LENGTH_LONG).show();
				  showTaxSettings(view);
			 }
			
			 		else{
			 String retrivedTaxName = null ;
			 

			 String[] arrayOfString = { DJPOSConstants.TAX_NAME,DJPOSConstants.TAX_PERCENTAGE};
			 
		        Cursor localCursor = storeDB.getDb().query(DJPOSConstants.TABLE_TAX, arrayOfString, null, null , null, null, null);
		        Tax taxDetails = new Tax();
		        localCursor.moveToFirst();
		        while (true)
		        {
		          if (localCursor.isAfterLast())
		          {
		            localCursor.close();
		           break;
		          }
		          
		          retrivedTaxName=  localCursor.getString(localCursor.getColumnIndex(DJPOSConstants.TAX_NAME));
		          
		          localCursor.moveToNext();
		        }
		      
		       if (retrivedTaxName==null){
		    	   
		    	   ContentValues localContentValues = new ContentValues();
			  	   localContentValues.put(DJPOSConstants.TAX_NAME, taxName.getText().toString());
			  	 localContentValues.put(DJPOSConstants.TAX_PERCENTAGE, taxPercentage.getText().toString());
			 
			  	
			  	    
			  	 storeDB.getDb().insert(DJPOSConstants.TABLE_TAX, null, localContentValues);
			  
		       }
		       else{
		    	   ContentValues localContentValues = new ContentValues();
		    	   String qualifer = "NM_TX = '" + retrivedTaxName.toString() + "'"; 
			  	   localContentValues.put(DJPOSConstants.TAX_NAME, taxName.getText().toString());
			  	 localContentValues.put(DJPOSConstants.TAX_PERCENTAGE, taxPercentage.getText().toString());
			  	    
			  	 storeDB.getDb().update(DJPOSConstants.TABLE_TAX, localContentValues, qualifer, null);
		    	   
		       }
		       
		    	Toast.makeText(this, R.string.data_save, Toast.LENGTH_LONG).show();
		       	 Tax insertedTaxData = new Tax();
		       	insertedTaxData.setTaxName(taxName.getText().toString());
		       	insertedTaxData.setTaxPercentage(Float.valueOf(taxPercentage.getText().toString()));
				
		       	showTaxSettings(view, insertedTaxData);
				 
		       }
		}
			
	 
	 
			 public void showTaxSettings(View view, Tax insertedTaxData)
			  {
				  
				  setContentView(R.layout.activity_settings);

					String[] settingMenu = getResources().getStringArray(R.array.settings_menu);
					settingsMenuList =  (ListView) findViewById(R.id.settingsList);
					settingsMenuList.setAdapter(new ArrayAdapter<String>(this,
				                R.layout.settings_list_item, settingMenu));
					settingsMenuList.setOnItemClickListener(new SettingsItemClickListner());
					
					
				 ViewGroup grp = (ViewGroup)findViewById(R.id.layout_settingsdetail);
		 		 View taxSettingView = getLayoutInflater().inflate(R.layout.activity_taxsettings, (ViewGroup)findViewById(R.id.layout_taxsettings));
		 		 Intent storeIntent = getIntent();
		 		 Bundle extras = getIntent().getExtras();
		 		 DJPOSDatabase storeDB =  (DJPOSDatabase) extras.get("djposdb");
		 		 Store storeData = (Store) extras.get("storedata");
		 		 Tax taxData = (Tax) extras.get("taxdata");
		 		
		 		 if((insertedTaxData!=null) && (!(insertedTaxData.getTaxName().isEmpty())))
		 		 {
		 			 EditText taxName = (EditText) taxSettingView.findViewById(R.id.TaxNameEdit);
		  			 EditText taxPercentage = (EditText) taxSettingView.findViewById(R.id.TaxPercentageEdit);
		 			 
		  			 if(insertedTaxData.getTaxName()!=null)
		  			 {
		  				taxName.setText(insertedTaxData.getTaxName());  			 
		  				taxPercentage.setText(Float.toString(insertedTaxData.getTaxPercentage()));
		  			 }
		  			
		  			grp.removeAllViews();
					grp.addView(taxSettingView); 
					 
		 		 }
		 		 else
		 		 {
		 			grp.removeAllViews();
		 			 grp.addView(taxSettingView); 
		 		 } 
		 		 
			  }	 
			 
		
		
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product, menu);
		return true;
	}
	
	
	
	
}

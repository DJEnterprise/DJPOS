package com.djpos.retail.stores.main;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ProductActivity extends FragmentActivity implements HeadlinesFragment.OnHeadlineSelectedListener {
	
	
	protected TextView prodID; 
	protected TextView prodDesc; 
	protected TextView prodPrice; 
	protected TextView deptName;
	private   Spinner departmentChooser;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		setContentView(R.layout.activity_product);
		
		 // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.product_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of ExampleFragment
            HeadlinesFragment firstFragment = new HeadlinesFragment();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.product_container, firstFragment).commit();
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
			 View localView = getLayoutInflater().inflate(R.layout.add_department, (ViewGroup)findViewById(R.id.department_Layout));
			 deptName = (TextView) localView.findViewById(R.id.depnameEdit); 
		alertDialog.setTitle("Add Department");
		alertDialog.setView(localView);
		alertDialog.setPositiveButton("Add", new DialogInterface.OnClickListener()
		{
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
    	  Intent inten = getIntent();
    	  Bundle extras = getIntent().getExtras();
    	  
    	  String str1 = deptName.getText().toString();
    	
    	  DJPOSDatabase storeDB =  (DJPOSDatabase) extras.get("djposdb");
    	  System.out.println("DB Received Succesfully");
    	  SQLiteDatabase sf = storeDB.getDb();
    	 // insertItem(sf, str1, str2, int4);
    	  
    	  ContentValues localContentValues = new ContentValues();
  	    localContentValues.put(DJPOSConstants.DEPARTMENT_NAME, str1);
  	  
  	   
  	  /*  if (db.rawQuery("select * from AS_ITM", null).getCount() > 0)
  	      getDb().update("taxes", localContentValues, "_id=1", null);*/
  	    
  	      sf.insert(DJPOSConstants.TABLE_DEPARTMENT, null, localContentValues);
  	    Toast.makeText(getApplicationContext(), R.string.data_save, Toast.LENGTH_LONG).show();
      }
    });
		alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
		{
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        paramDialogInterface.cancel();
      }
    });
		
		}
		//for product insert
		else
		{
			View localView = getLayoutInflater().inflate(R.layout.add_product, (ViewGroup)findViewById(R.id.product_Layout));
			prodID = (TextView) localView.findViewById(R.id.productIDEdit);
			prodDesc = (TextView) localView.findViewById(R.id.prodDescEdit);
			prodPrice = (TextView) localView.findViewById(R.id.prodPriceEdit);
			departmentChooser = (Spinner) localView.findViewById(R.id.DepartmentChooser);
			 Intent inten = getIntent();
	    	  Bundle extras = getIntent().getExtras();
	    	  DJPOSDatabase depDB =  (DJPOSDatabase) extras.get("djposdb");
			List deplist = chooseDepartment(depDB);
					    
			    ArrayAdapter dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, deplist);
				    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				    departmentChooser.setAdapter(dataAdapter);
			
			alertDialog.setTitle("Add Product");
			alertDialog.setView(localView);
			alertDialog.setPositiveButton("Add", new DialogInterface.OnClickListener()
			{
	      public void onClick(DialogInterface paramDialogInterface, int paramInt)
	      {
	    	  Intent inten = getIntent();
	    	  Bundle extras = getIntent().getExtras();
	    	  
	    	  String str1 = prodID.getText().toString();
	    	  String str2 =  prodDesc.getText().toString();
	    	 String str3 = prodPrice.getText().toString();
	    	String str4 = String.valueOf(departmentChooser.getSelectedItem());
	    		
	    	  DJPOSDatabase storeDB =  (DJPOSDatabase) extras.get("djposdb");
	    	  System.out.println("DB Received Succesfully");
	    	  SQLiteDatabase sf = storeDB.getDb();
	    	 // insertItem(sf, str1, str2, int4);
	    	  
	    	  ContentValues localContentValues = new ContentValues();
	  	    localContentValues.put(DJPOSConstants.ITEM_ID, str1);
	  	    localContentValues.put(DJPOSConstants.ITEM_DESC, str2);
	  	    localContentValues.put(DJPOSConstants.ITEM_PRICE, str3);
	  	    localContentValues.put(DJPOSConstants.DEPARTMENT_NAME, str4);
	  	  /*  if (db.rawQuery("select * from AS_ITM", null).getCount() > 0)
	  	      getDb().update("taxes", localContentValues, "_id=1", null);*/
	  	    
	  	      sf.insert(DJPOSConstants.TABLE_ITEM, null, localContentValues);
	  	    Toast.makeText(getApplicationContext(), R.string.data_save, Toast.LENGTH_LONG).show();
	    	
	      }
	    });
			alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
			{
	      public void onClick(DialogInterface paramDialogInterface, int paramInt)
	      {
	        paramDialogInterface.cancel();
	      }
	    });
		}
		
		alertDialog.show();

		
		// Capture the article fragment from the activity layout
     /*   ArticleFragment articleFrag = (ArticleFragment)
                getSupportFragmentManager().findFragmentById(R.id.article_fragment);

        if (articleFrag != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content
            articleFrag.updateArticleView(position);

        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            ArticleFragment newFragment = new ArticleFragment();
            Bundle args = new Bundle();
            args.putInt(ArticleFragment.ARG_POSITION, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }*/
    }

	private List chooseDepartment(DJPOSDatabase depDB) {
		
		String[] arrayOfString = { DJPOSConstants.DEPARTMENT_NAME};
        Cursor localCursor = depDB.getDb().query(DJPOSConstants.TABLE_DEPARTMENT, arrayOfString, "", new String[] { }, null, null, null);
        ArrayList depList = new ArrayList();
        String depName;
        localCursor.moveToFirst();
        while (true)
        {
          if (localCursor.isAfterLast())
          {
            localCursor.close();
           break;
          }
          depName= localCursor.getString(localCursor.getColumnIndex(DJPOSConstants.DEPARTMENT_NAME));
          depList.add(depName);
        localCursor.moveToNext();
        }
      
    
       
      return depList;
		
		
	
	}
	
	
	
	/*public boolean insertItem(SQLiteDatabase db, String str1, String str2, int int4)
	  {
	    ContentValues localContentValues = new ContentValues();
	    localContentValues.put("ID_ITM", str1);
	    localContentValues.put("DE_ITM", str2);
	    localContentValues.put("PRC_ITM", Integer.valueOf(int4));
	   
	    if (db.rawQuery("select * from AS_ITM", null).getCount() > 0)
	      getDb().update("taxes", localContentValues, "_id=1", null);
	    while (true)
	    {
	      return true;
	      db.insert("AS_ITM", null, localContentValues);
	    }
	  }*/

}

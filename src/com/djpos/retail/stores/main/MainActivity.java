package com.djpos.retail.stores.main;





import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;




import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class fMainActivity extends Activity {
	
	private String[] mPOSContent;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    private DJPOSDatabase storeDB;
    
    protected TextView prodID; 
	protected TextView prodDesc; 
	protected TextView prodPrice; 
	protected TextView tenderedAmount;
	protected TextView tenderTxnAmount;
	protected Spinner tenderType;
	
	protected TextView subTotal;
	protected TextView Total;
	protected TextView Tax;
	protected Store storeData;
	protected Tax taxData;
	protected TextView customerLinkID;
	protected Customer txnCustomer;
	public Store getStoreData() {
		return storeData;
	}

	public void setStoreData(Store storeData) {
		this.storeData = storeData;
	}

	public void setTaxData(Tax taxData) {
		this.taxData = taxData;
	}
	
	public Tax getTaxData() {
		return taxData;
	}

	
	protected Transaction txn;
	public DJPOSDatabase getStoreDB() {
		return this.storeDB;
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("ON CREATE METHOD of MAIN ACTIVITY");
		setstoreDB(new DJPOSDatabase(this));
		readStoreData();
		
		/*if(this.storeData == null)
		{
			AlertDialog.Builder settingsAlertDialog = new AlertDialog.Builder(this);
			settingsAlertDialog.setTitle("Set Store Details");
			
			 settingsAlertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener()
			{
	      public void onClick(DialogInterface paramDialogInterface, int paramInt)
	      {
	    	showSettings();   }

		
	    });
			 settingsAlertDialog.show();
			
		}*/
		readTaxData();
		setContentView(R.layout.activity_main);
	
		if(txn==null)
		{
			 txn = new Transaction();
			 System.out.println("NEW TRANSACTION INITIALIZED");
		}
		
		
		
		this.getActionBar().setDisplayShowCustomEnabled(true);
		this.getActionBar().setDisplayShowTitleEnabled(false);

		LayoutInflater inflator = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.action_customerview, null);

		if(txnCustomer!=null){
			
			if(this.txn!=null)
				this.txn.setCustomerTxn(txnCustomer);
			
		//if you need to customize anything else about the text, do it here.
		//I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
		((TextView)v.findViewById(R.id.customerName)).setText(txnCustomer.getCustomerName());

		//assign the view to the actionbar
		this.getActionBar().setCustomView(v);
		}
		else 
		{
			((TextView)v.findViewById(R.id.customerName)).setText("");
			this.getActionBar().setCustomView(v);
		}
		
		mTitle = mDrawerTitle = getTitle();
		mPOSContent = getResources().getStringArray(R.array.mposcontent_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        
       
     // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerLayout.setDrawingCacheBackgroundColor(TRIM_MEMORY_RUNNING_CRITICAL);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPOSContent));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        
        // enable ActionBar app icon to behave as action to toggle nav drawer
     //   getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
     // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
        
         if((this.txn!=null) && (this.txn.getLineItem()!=null))
		{
			System.out.println("Show ITEM INVOKED");
        	 showItems();
		}
         else{
        	 System.out.println("Show ITEM NOT INVOKED");
         }
        
	}
	
	/*  @Override
	    protected void onResume() {
		  System.out.println("ON RESUME INITIALIZED");
		   if((this.txn!=null) && (this.txn.getLineItem()!=null))
			{
				System.out.println("Show ITEM INVOKED");
	        	// showItems();
			}
	         else{
	        	 System.out.println("Show ITEM NOT INVOKED");
	         }
	        super.onResume();
	     
	    }*/

	protected void showSettings() {
		
		Intent settingsIntent = new Intent(this, SettingsMenuActivity.class);
  		settingsIntent.putExtra("djposdb", getStoreDB());
  		settingsIntent.putExtra("storedata", getStoreData());
  		settingsIntent.putExtra("taxdata", getTaxData());
  		startActivity(settingsIntent);	   
		
	}

	private void readStoreData() {
		 String[] arrayOfString = { DJPOSConstants.STORE_ID,DJPOSConstants.STORE_NAME, DJPOSConstants.STORE_ADDRESS, DJPOSConstants.STORE_CITY, 
				 DJPOSConstants.STORE_ZIPCODE, DJPOSConstants.STORE_PHONE};
	        Cursor localCursor = getStoreDB().getDb().query(DJPOSConstants.TABLE_STORE, arrayOfString, null, null , null, null, null);
	        Store storeDetails = new Store();
	        localCursor.moveToFirst();
	        while (true)
	        {
	          if (localCursor.isAfterLast())
	          {
	            localCursor.close();
	           break;
	          }
	          
	          storeDetails.setStoreID(localCursor.getString(localCursor.getColumnIndex(DJPOSConstants.STORE_ID)));
	          storeDetails.setStoreName(localCursor.getString(localCursor.getColumnIndex(DJPOSConstants.STORE_NAME)));
	          storeDetails.setstoreaddress1(localCursor.getString(localCursor.getColumnIndex(DJPOSConstants.STORE_ADDRESS)));
	          storeDetails.setstoreaddress2(localCursor.getString(localCursor.getColumnIndex(DJPOSConstants.STORE_CITY)));
	          storeDetails.setstoreaddress3(localCursor.getString(localCursor.getColumnIndex(DJPOSConstants.STORE_ZIPCODE)));
	          storeDetails.setstorephone(localCursor.getString(localCursor.getColumnIndex(DJPOSConstants.STORE_PHONE)));

	          setStoreData(storeDetails);
	          localCursor.moveToNext();
	        }
	        
	}

	private void setstoreDB(DJPOSDatabase djposDatabase) {
		// TODO Auto-generated method stub
		this.storeDB = djposDatabase;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		 MenuInflater inflater = getMenuInflater();
		 inflater.inflate(R.menu.main, menu);
		 return super.onCreateOptionsMenu(menu);
	}
	
	
	
	/* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    
    

    private void selectItem(int position) {
    	
    	
    	switch(position){
    	case 0:
    		 // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            setTitle(mPOSContent[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
            break;
    	case 1:
    		Intent intent = new Intent(this, ProductActivity.class);
    		intent.putExtra("djposdb", getStoreDB());
    		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	startActivity(intent);
        	 // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            setTitle(mPOSContent[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
            break;
    	case 2:
    		
	    	  Intent customerIntent = new Intent(this, CustomerActivity.class);
	    	  customerIntent.putExtra("djposdb", getStoreDB());
	    	  customerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        	startActivity(customerIntent);
	        	
            mDrawerList.setItemChecked(position, true);
            setTitle(mPOSContent[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
            break;
        case 3:
        	Intent intent2 = new Intent(this, ReportGeneraterActivity.class);
        	intent2.putExtra("djposdb", getStoreDB());
        	intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	startActivity(intent2);
        	
    		 // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            setTitle(mPOSContent[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
            break;
    	case 4:
    		Intent settingsIntent = new Intent(this, SettingsMenuActivity.class);
    		settingsIntent.putExtra("djposdb", getStoreDB());
    		settingsIntent.putExtra("storedata", getStoreData());
    		settingsIntent.putExtra("taxdata", getTaxData());
    		startActivity(settingsIntent);
    		 // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            setTitle(mPOSContent[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
            break;
    	case 5:
    		AlertDialog.Builder aboutAlertDialog = new AlertDialog.Builder(this);
    		aboutAlertDialog.setTitle("About DJPOS");
			 View aboutView = getLayoutInflater().inflate(R.layout.about, (ViewGroup)findViewById(R.id.about));
			 aboutAlertDialog.setView(aboutView);
    		aboutAlertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener()
			{
	      public void onClick(DialogInterface paramDialogInterface, int paramInt)
	      {
	        paramDialogInterface.cancel();
	      }
	    });
    		aboutAlertDialog.show();
    		 // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
       //     setTitle(mPOSContent[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
            break;

    	default:
    		 // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            setTitle(mPOSContent[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
            break;
    		
    	}
    	
    	
        // update the main content by replacing fragments
/*     Fragment fragment = new MPOSContentFragment();
        Bundle args = new Bundle();
        args.putInt(MPOSContentFragment.MPOS_CONTENT_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.linear_layout, fragment).commit();
*/
       
    }
    
   
    public  class MPOSContentFragment extends Fragment {
        public static final String MPOS_CONTENT_NUMBER = "mposcontent_number";

        public MPOSContentFragment() {
            // Empty constructor required for fragment subclasses
        }
        
     
        }


    
    public  class MPOSItemFragment extends Fragment {
        public static final String MPOS_ITEM_NUMBER = "mpositem_number";

        @SuppressLint("ValidFragment")
		public MPOSItemFragment() {
            // Empty constructor required for fragment subclasses
        }
        
      
        @Override
       /* public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.add_listitem, container, false);
            String i = getArguments().getString(MPOS_ITEM_NUMBER);
            Item prodItem = retriveItem(i);
             
            ListView listView ;
            rootView.se
            listView = (ListView) findViewById(R.id.listView2);
            ((TextView)rootView.findViewById(R.id.itemID)).setText(prodItem.getItemID());
            ((TextView)rootView.findViewById(R.id.itemDescLabel)).setText(prodItem.getItemDesc());
            ((TextView)rootView.findViewById(R.id.itempriceLabel)).setText(prodItem.getItemPrice());
            
       
      //      String planet = getResources().getStringArray(R.array.planets_array)[i];

            int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                            "drawable", getActivity().getPackageName());
            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
            getActivity().setTitle(planet);
            
            return rootView;
        }*/
        
        public ListView onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_main, container, false);
            String i = getArguments().getString(MPOS_ITEM_NUMBER);
            Item prodItem = retriveItem(i);

            ListView listView ;
         
            listView = (ListView) findViewById(R.id.listView1);
            String[] values = new String[] { "Android List View", 
                    "Adapter implementation"};
            
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, android.R.id.text1, values);
            
            listView.setAdapter(adapter);
            
        //    ((TextView)rootView.findViewById(R.id.itemID)).setText(prodItem.getItemID());
        //    ((TextView)rootView.findViewById(R.id.itemDescLabel)).setText(prodItem.getItemDesc());
         //   ((TextView)rootView.findViewById(R.id.itempriceLabel)).setText(prodItem.getItemPrice());
            
       
      //      String planet = getResources().getStringArray(R.array.planets_array)[i];

         /*   int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                            "drawable", getActivity().getPackageName());
            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
            getActivity().setTitle(planet);*/
            
            return listView;
        }
        
       
}
    
    
    public Item retriveItem(String i) {
		

        String[] arrayOfString = { "ID_ITM", "DE_ITM", "PRC_ITM"};
        Cursor localCursor = getStoreDB().getDb().query("AS_ITM", arrayOfString, "ID_ITM = ?", new String[] { i }, null, null, null);
        Item prodItem = new Item();
        localCursor.moveToFirst();
        while (true)
        {
          if (localCursor.isAfterLast())
          {
            localCursor.close();
           break;
          }
          prodItem.setItemID(localCursor.getString(localCursor.getColumnIndex("ID_ITM")));
          prodItem.setItemDesc(localCursor.getString(localCursor.getColumnIndex("DE_ITM")));
          prodItem.setItemPrice(localCursor.getString(localCursor.getColumnIndex("PRC_ITM")));
          
          localCursor.moveToNext();
        }
        return prodItem;
        
		
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
        case R.id.action_settings:
            // create intent to perform web search for this planet
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
            // catch event that there's no activity to handle intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
            }
            return true;
        case R.id.action_barcodescan:
        	IntentIntegrator sdd = new IntentIntegrator(this);
        	sdd.initiateScan();
        	// IntentResult localIntentResult = IntentIntegrator.parseActivityResult(0, 0, intent);
        	 
        	 Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
        	return true;
        case R.id.action_linkcustomer:
        	AlertDialog.Builder aboutAlertDialog = new AlertDialog.Builder(this);
    		aboutAlertDialog.setTitle("Enter Customer ID");
			 View aboutView = getLayoutInflater().inflate(R.layout.customer_link, (ViewGroup)findViewById(R.id.customerlink_Layout));
			  customerLinkID =  (TextView) aboutView.findViewById(R.id.customerIDLinkEdit );
			  aboutAlertDialog.setView(aboutView);
			  
    		aboutAlertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener()
			{
	      public void onClick(DialogInterface paramDialogInterface, int paramInt)
	      {
	        String cusLinkID = customerLinkID.getText().toString();
	        
	        if((cusLinkID!=null) && (!(cusLinkID.equalsIgnoreCase(""))))
	        {
	        String[] arrayOfString = { DJPOSConstants.CUST_ID, DJPOSConstants.CUST_NAME, DJPOSConstants.CUST_ADDRESS, 
	        		DJPOSConstants.CUST_CITY,DJPOSConstants.CUST_ZIPCODE,DJPOSConstants.CUST_PHONE, DJPOSConstants.CUST_EMAIL};
	        Cursor localCursor = getStoreDB().getDb().query(DJPOSConstants.TABLE_CUSTOMER, arrayOfString, "ID_CUST = ?", new String[] { cusLinkID }, null, null, null);
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
	        }
	        
	        txnCustomer = customerDetails;
	        onCreate(new Bundle()); 
	        }
	      }
	    });
    		aboutAlertDialog.show();
    		return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
    {
    	if (paramInt1 == 114460)
        {
          if (paramInt2 == RESULT_OK)
          {
            String str1 = paramIntent.getStringExtra("SCAN_RESULT");
            String str2 = paramIntent.getStringExtra("SCAN_RESULT_FORMAT");
            byte[] arrayOfByte = paramIntent.getByteArrayExtra("SCAN_RESULT_BYTES");
            int i = paramIntent.getIntExtra("SCAN_RESULT_ORIENTATION", -2147483648);
            Integer localInteger = null;
            if (i == -2147483648)
            {
            
            	displayItemList(str1);
            	//setContentView(R.layout.activity_main);
           //   return new IntentResult(str1, str2, arrayOfByte, localInteger, paramIntent.getStringExtra("SCAN_RESULT_ERROR_CORRECTION_LEVEL"));
          //    localInteger = Integer.valueOf(i);
            }
          }
         
        }
       
    	//displayItemList(((IntentResult) paramIntent).getContents());
    }
    

	@Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
    
		
    public void searchItem(View view)
    {
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	String itemID = editText.getText().toString();
    	//displayItem(itemID);
    	displayItemList(itemID);
    }
    
    public void cancelSale(View view)
    {
    	 this.txn.setLineItem(null);
    	 this.txn.setTenderLineItem(null);
    	 this.txn.setCustomerTxn(null);
    	 txnCustomer =null;
    	 onCreate(null);
    	 /* ListView listView ;
    	  Item[] prodItem = new Item[1];
          listView = (ListView) findViewById(R.id.listView1);
          ((ItemViewAdapter)listView.getAdapter()).notifyDataSetChanged();
          ItemViewAdapter adapter;
        
          adapter = new ItemViewAdapter(this,
        		  prodItem);
          
          listView.setAdapter(adapter);*/
          
        //  updateTotals(this.txn.getLineItem());    	
    	 Toast.makeText(this, R.string.transaction_cancelled, Toast.LENGTH_LONG).show();
    }
    
    
    public void completeSale(View view)
    {
    	
    	if((this.txn!=null) && (this.txn.getLineItem()!=null))
       	{
    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		View localView = getLayoutInflater().inflate(R.layout.add_tender, (ViewGroup)findViewById(R.id.tender_Layout));
		 tenderedAmount = (TextView) localView.findViewById(R.id.tenderAmountEdit);
		 tenderTxnAmount = (TextView) localView.findViewById(R.id.tendertransAmount);
		 tenderType = (Spinner) localView.findViewById(R.id.spinner1);
		  if((txn!=null) && (txn.getLineItem()!=null))
       	{
  		 Tender[] ten = txn.getTenderLineItem();
  		 
  		 float total = ten[0].getTotal();
		 tenderTxnAmount.setText(Float.toString(total));
       	}

		alertDialog.setTitle("Tender Details");
		alertDialog.setView(localView);
		alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener()
		{
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
    	  String str1 = tenderedAmount.getText().toString();
    	  if(str1.isEmpty()){
    		  Toast.makeText(getApplicationContext(), R.string.add_tendererror, Toast.LENGTH_LONG).show();
 			 paramDialogInterface.cancel();
    	  }
    	  else{
    		  
    	 
    	  float enteredTender = Float.parseFloat(str1);
    	String tenderTypeSTR = String.valueOf(tenderType.getSelectedItem());
    	  if((txn!=null) && (txn.getLineItem()!=null))
         	{
    		 Tender[] ten = txn.getTenderLineItem();
    		 
    		 float total = ten[0].getTotal();
    		 if(total==enteredTender)
    		 {
    			 ten[0].setTenderType(tenderTypeSTR);
    			 compSale();
    		 }
    		 else if(enteredTender>total)
    		 {
    			 ten[0].setTenderType(tenderTypeSTR); ten[0].setTenderType(tenderTypeSTR);
    			 showChange(total, enteredTender);
    			
    			      
    		 }
    		 else
    		 {
    			 Toast.makeText(getApplicationContext(), R.string.add_tendererror, Toast.LENGTH_LONG).show();
    			 paramDialogInterface.cancel();
    		 }
         	 }
         	}
    	 
  	    
    	
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
    	else
    	{
    		Toast.makeText(this, R.string.add_itemerror, Toast.LENGTH_LONG).show();
    	}
    	
    	
    }
    
    private void showChange(float total, float enteredTender)
    {
    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
    	alertDialog.setTitle("Issue Change");
    	float change = enteredTender - total;
    	BigDecimal roundedChange = round(change, 2);
    	alertDialog.setMessage("Issue Customer Change: " + roundedChange.toString());
    	alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener()
		{
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
  	    
    	  compSale();
      }
    });
    	alertDialog.show();
    }
    
    private void compSale()
    {
    	 if((this.txn!=null) && (this.txn.getLineItem()!=null))
       	{
       	CompleteSale sale = new CompleteSale();
       	String txnID = sale.insertTransaction(this.txn, getStoreDB(), this.storeData);
       	this.txn.setTransactionID(txnID);
       	sendEReceipt();
       	this.txn.setLineItem(null);
      	this.txn.setTenderLineItem(null);
      	this.txn.setCustomerTxn(null);
     	this.txnCustomer =null;
       	onCreate(null);
       	
       	}
       	else
       	{
       		Toast.makeText(this, R.string.add_itemerror, Toast.LENGTH_LONG).show();
       	}
    }
    
    
    private void sendEReceipt() {
    		try {
    		
    		//	String path = getDataDir(getApplicationContext());
    			String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
    			System.out.println(path);
				FirstPDF pdf = new FirstPDF();
				String path1 = path;
				path = path + "/";
				pdf.createPDF(this.txn, path, this.storeData);
				System.out.println(path);
				
				   Uri uriLog = null;
				   String filename = txn.getTransactionID() + ".pdf";
	                uriLog = Uri.parse("file://" + path + filename);

		          /*  try {
		            //	BufferedReader br = new BufferedReader(new FileReader(path+filename));
		            	
		              //  File attachment = new File(path, filename);
		                FileOutputStream out = new FileOutputStream(attachment);
		                out.write(attachment.toString().getBytes());
		                out.close();
		            }
		            catch (IOException e) {
		                e.printStackTrace();
		            }
*/
		            
				
				//  File file = new File(path, filename);
			      Intent intent = new Intent(Intent.ACTION_SEND); // it's not ACTION_SEND
			      intent.setType("message/rfc822");
			      if(txn.getCustomerTxn()!=null)
				  {
				  		 if(txn.getCustomerTxn().getCustomerID()!=null)
				  		 {
				  			intent.putExtra(Intent.EXTRA_EMAIL, new String[]{txn.getCustomerTxn().getCustomerID()});
				  		 }
				  		 else
				  		 {
				  		     intent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
				  		 }
				  			 
				  	 }
			      else
			      {
			  		     intent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});

			      }
			      
			 
			      intent.putExtra(Intent.EXTRA_SUBJECT, "eReceipt");
			      intent.putExtra(Intent.EXTRA_TEXT, "This is your receipt from " + getStoreData().getStoreName() + "!" );
			      intent.putExtra(Intent.EXTRA_STREAM,uriLog);
			      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
			     
			      startActivity(intent);
			      
			      
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
    
    public String getDataDir(Context context) throws Exception 
	{
	    return context.getPackageManager().getPackageInfo(getPackageName(), 0).applicationInfo.dataDir;
	}

	private void displayItemList(String itemID) {
    	// View rootView = inflater.inflate(R.layout.activity_main, container, false);
         String i = itemID;
         Item prodItem = retriveItem(i);
         if(prodItem.getItemID()!=null)
         {
         if(this.txn.getLineItem()==null)
         {
        	 Item prd[] = new Item[1];
        	 prd[0]=prodItem;
        	 prd[0].setQuantity("1");
        	 this.txn.setLineItem(prd);
         }
         else
         {
        	 int l = (this.txn.getLineItem().length);
        	 Item prd[] = new Item[l+1];
        	 Item prd2[] = this.txn.getLineItem();
        	 for(int j=0; j<l; j++)
        	 {
        		 prd[j] = prd2[j]; 
        		 prd[j].setQuantity("1");
        	 }
        	
        	 prd[l] = prodItem;
        	 prd[l].setQuantity("1");
        	 this.txn.setLineItem(prd);
         }
        	 
         
        
         ListView listView ;
      
         listView = (ListView) findViewById(R.id.listView1);
         
         ItemViewAdapter adapter;
       
       
         String[] values = new String[] { "Android List View", 
                 "Adapter implementation"};
         
         adapter = new ItemViewAdapter(this,
        		 this.txn.getLineItem());
         
         listView.setAdapter(adapter);
         
         updateTotals(this.txn.getLineItem());
         }
         else
         {
        	 Toast.makeText(this, R.string.product_not_available, Toast.LENGTH_LONG).show();
         }
		
	}

	private void showItems()
	{
		  ItemViewAdapter adapter;
		  
		  ListView listView ;
	      
	         listView = (ListView) findViewById(R.id.listView1);
		  
		  adapter = new ItemViewAdapter(this,
	        		 this.txn.getLineItem());
	         
	         listView.setAdapter(adapter);
	         
	         updateTotals(this.txn.getLineItem());
		
	}
	private void updateTotals(Item[] lineItem) {
		
	//	View localView = getLayoutInflater().inflate(R.layout.activity_main, (ViewGroup)findViewById(R.id.totalscroll_Layout));
	//	setContentView( (ViewGroup)findViewById(R.layout.activity_main));
	//	subTotal = (TextView) findViewById(R.id.subTotal);
		
		Tax taxData = getTaxData();
		
		Total = (TextView) findViewById(R.id.Total);
		 float total = 0.0f;
		
		
		if(lineItem==null)
        {
			subTotal.setText("0.00");
			Total.setText("0.00");
			
        }
        else
        {
       	 int l = (lineItem.length);
       	 float prd[] = new float[l+1];
       	 for(int j=0; j<l; j++)
       	 {
       		 prd[j] = Float.parseFloat(lineItem[j].getItemPrice());
       		 //prd[j] = Integer.parseInt(lineItem[j].getItemPrice()); 
       	 }
       	
       	
       	 for(int k=0; k<prd.length; k++)
       	 {
       		 total =  total + prd[k];
       	 }
    //   	subTotal.setText(Integer.toString(total)+ ".00");
       	 
       	 if((taxData!=null) && ((!taxData.getTaxName().isEmpty())))
       	 {
       		 float taxRate = taxData.getTaxPercentage();
       		 String taxRateStr = Float.toString(taxRate);
       		 if((!(taxRateStr.isEmpty()) && (!(taxRateStr.startsWith("00")))))
       				 {
       			Tax = (TextView) findViewById(R.id.TaxTotal);
       			 float taxFloat = total * taxRate /100;
       			 total = total + taxFloat;
       			BigDecimal roundedTax = round(taxFloat, 2);
       			
       			 Tax.setText(roundedTax.toString());
       			 this.txn.setTransactionTax(roundedTax.floatValue());
       				 }
       	 }
       	 BigDecimal roundedTotal = round(total,2);
		Total.setText(roundedTotal.toString());
		Tender tenderLineItem = new Tender();
		tenderLineItem.setSubTotal(roundedTotal.floatValue());
		tenderLineItem.setTotal(roundedTotal.floatValue());
		
		tenderLineItem.setTenderType("Cash");
		Tender[] tender = new Tender[1];
		tender[0]=tenderLineItem;
		this.txn.setTenderLineItem(tender);
        }
		
		
		
	}

	public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);       
        return bd;
    }
	
	private void displayItem(String itemID) {
        // update the main content by replacing fragments
        Fragment fragment = new MPOSItemFragment();
        Bundle args = new Bundle();
        args.putString(MPOSItemFragment.MPOS_ITEM_NUMBER, itemID);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().remove(fragment);
        fragmentManager.beginTransaction().replace(R.id.linear_layout, fragment).commit();

        // update selected item and title, then close the drawer
    //    mDrawerList.setItemChecked(position, true);
  //      setTitle(mPOSContent[position]);
   //     mDrawerLayout.closeDrawer(mDrawerList);
    }
	
	
	private void readTaxData() {
		 String[] arrayOfString = { DJPOSConstants.TAX_NAME,DJPOSConstants.TAX_PERCENTAGE};
	        Cursor localCursor = getStoreDB().getDb().query(DJPOSConstants.TABLE_TAX, arrayOfString, null, null , null, null, null);
	        Tax taxDetails = new Tax();
	        localCursor.moveToFirst();
	        while (true)
	        {
	          if (localCursor.isAfterLast())
	          {
	            localCursor.close();
	           break;
	          }
	          
	          taxDetails.setTaxName(localCursor.getString(localCursor.getColumnIndex(DJPOSConstants.TAX_NAME)));
	          taxDetails.setTaxPercentage(Float.valueOf(localCursor.getString(localCursor.getColumnIndex(DJPOSConstants.TAX_PERCENTAGE))));
	          
	          setTaxData(taxDetails);
	          localCursor.moveToNext();
	        }
	        
	}
    
}

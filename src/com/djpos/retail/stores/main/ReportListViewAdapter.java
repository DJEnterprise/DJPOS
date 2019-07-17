package com.djpos.retail.stores.main;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ReportListViewAdapter extends BaseAdapter{

	 private Activity activity;
	    private Transaction[] data;
	    private static LayoutInflater inflater=null;
	 
	    public ReportListViewAdapter(Activity a, Transaction[] d) {
	    	 
	    	activity = a;
	        data=d;
	        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    }
	 
	    public int getCount() {
	        return data.length;
	    }
	 
	    public Object getItem(int position) {
	        return position;
	    }
	 
	    public long getItemId(int position) {
	        return position;
	    }
	 
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View vi=convertView;
	        if(convertView==null)
	            vi = inflater.inflate(R.layout.add_reportlist, null);
	 
	        TextView transactionID = (TextView)vi.findViewById(R.id.TransactionID);
	        TextView transactionDate = (TextView)vi.findViewById(R.id.TransactionDate); 
	        TextView transactionTotal = (TextView)vi.findViewById(R.id.TransactionTotal); 
	        TextView transactionItems = (TextView)vi.findViewById(R.id.TransactionItems); 
	        TextView transactionTax = (TextView)vi.findViewById(R.id.TransactionTax); 
	        
	        Transaction currentItem = new Transaction();
	        currentItem = data[position];
	 
	        // Setting all values in listview
	        transactionID.setText("Transaction ID:" + currentItem.getTransactionID());
	        transactionDate.setText("Transaction Date:" + currentItem.getBusinessDate());
	        Tender[] tenderList = currentItem.getTenderLineItem();
	        if(tenderList!=null)
	        transactionTotal.setText("Transaction Total: " + tenderList[0].getTotal());
	        int length = currentItem.getLineItem().length;
	        Item[] itemList = currentItem.getLineItem();
	        String itemText ="";
	        for(int i=0; i<length; i++)
	        {
	        	itemText = itemText + "Item: " + itemList[i].getItemID() + "\t";
	        	itemText = itemText + itemList[i].getItemDesc()+ " @1" +"\t";
	        	itemText = itemText + " Price: " +itemList[i].getItemPrice() + "\n";
	        }
	        transactionItems.setText(itemText);
	        transactionTax.setText("Transaction Tax :" + currentItem.getTransactionTax());
	        return vi;
	    }

}

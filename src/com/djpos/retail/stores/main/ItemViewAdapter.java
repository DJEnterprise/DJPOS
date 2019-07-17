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

public class ItemViewAdapter extends BaseAdapter{

	 private Activity activity;
	    private Item[] data;
	    private static LayoutInflater inflater=null;
	 
	    public ItemViewAdapter(Activity a, Item[] d) {
	    	 
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
	            vi = inflater.inflate(R.layout.add_listitem, null);
	 
	        TextView itemID = (TextView)vi.findViewById(R.id.itemIDList); // title
	        TextView itemDesc = (TextView)vi.findViewById(R.id.itemDescList); // artist name
	        TextView itemPrice = (TextView)vi.findViewById(R.id.itemPriceList); 
	        TextView itemQuantity = (TextView)vi.findViewById(R.id.itemQuantityList); // duration
	        
	        Item currentItem = new Item();
	        currentItem = data[position];
	 
	        // Setting all values in listview
	        itemID.setText(currentItem.getItemID());
	        itemDesc.setText(currentItem.getItemDesc());
	        itemPrice.setText(currentItem.getItemPrice());
	        itemQuantity.setText("@1");
	        return vi;
	    }

}

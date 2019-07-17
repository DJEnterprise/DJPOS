package com.djpos.retail.stores.main;

import java.io.Serializable;

public class Store implements Serializable{

		private String storeID;
		public String getStoreID() {
			return storeID;
		}

		public void setStoreID(String storeID) {
			this.storeID = storeID;
		}

		private String storename;
		private String storeaddress1;
		private String storeaddress2;
		private String storeaddress3;
		private String storephone;
		
		public String getStoreName() {
			return storename;
		}
	 
		public void setStoreName(String name) {
			this.storename = name;
		}
		public String getstoreaddress1() {
			return storeaddress1;
		}
	 
		public void setstoreaddress1(String storeaddress1) {
			this.storeaddress1 = storeaddress1;
		}
		public String getstoreaddress2() {
			return storeaddress2;
		}
	 
		public void setstoreaddress2(String storeaddress2) {
			this.storeaddress2 = storeaddress2;
		}
		
		public String getstoreaddress3() {
			return storeaddress3;
		}
	 
		public void setstoreaddress3(String storeaddress3) {
			this.storeaddress3 = storeaddress3;
		}
		
		public String getstorephone() {
			return storephone;
		}
	 
		public void setstorephone(String storephone) {
			this.storephone = storephone;
		}
}
	
	


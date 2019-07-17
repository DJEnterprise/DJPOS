package com.djpos.retail.stores.main;

import java.io.Serializable;

public class Customer implements Serializable{
	
	protected String customerID;
	protected String customerName;
	protected String customerAddress;
	protected String customerCity;
	protected String customerZipCode;
	protected String customerPhone;
	protected String customerEmail;
	
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public String getCustomerCity() {
		return customerCity;
	}
	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}
	public String getCustomerZipCode() {
		return customerZipCode;
	}
	public void setCustomerZipCode(String customerZipCode) {
		this.customerZipCode = customerZipCode;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

}

package com.djpos.retail.stores.main;

import java.io.Serializable;

public class Transaction implements Serializable{

	public String transactionID;
	
	public String businessDate;
	
	public float transactionTax = 0.0f;
	
	
	public float getTransactionTax() {
		return transactionTax;
	}

	public void setTransactionTax(float transactionTax) {
		this.transactionTax = transactionTax;
	}

	public String getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public  Item[] lineItem;
	
	public  Item[] getLineItem() {
		return lineItem;
	}

	public  void setLineItem(Item[] lineItm) {
		lineItem = lineItm;
	}

	public  Tender[] getTenderLineItem() {
		return tenderLineItem;
	}

	public  void setTenderLineItem(Tender[] tenderLineItm) {
		tenderLineItem = tenderLineItm;
	}

	public  Tender[] tenderLineItem;
	
	public Customer customerTxn;


	public Customer getCustomerTxn() {
		return customerTxn;
	}

	public void setCustomerTxn(Customer customerTxn) {
		this.customerTxn = customerTxn;
	}
	
}

package com.djpos.retail.stores.main;

import java.io.Serializable;

public class Tender implements Serializable {

	public float subTotal;
	public float total;
	public String tenderType;

	
	public float getSubTotal() {
		return subTotal;
	}
	
	public void setSubTotal(float subTotal) {
		this.subTotal = subTotal;
	}
	
	public float getTotal() {
		return total;
	}
	
	public void setTotal(float total) {
		this.total = total;
	}
	
	public String getTenderType() {
		return tenderType;
	}
	
	public void setTenderType(String tenderType) {
		this.tenderType = tenderType;
	}
	}

package com.djpos.retail.stores.main;

import java.io.Serializable;

public class Tax implements Serializable{

		private String taxName;
		
		public String getTaxName() {
			return taxName;
		}

		public void setTaxName(String taxName) {
			this.taxName = taxName;
		}

		private float taxPercentage;

		public float getTaxPercentage() {
			return taxPercentage;
		}

		public void setTaxPercentage(float taxPercentage) {
			this.taxPercentage = taxPercentage;
		}
		
		
	
}
	
	


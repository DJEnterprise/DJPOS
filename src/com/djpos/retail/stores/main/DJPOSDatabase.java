package com.djpos.retail.stores.main;

import java.io.Serializable;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DJPOSDatabase extends DJPOSConstants implements Serializable{

	
	 private static Context dbContext;
	 static DJPOSDatabaseHelper dbHelper;
	 private static SQLiteDatabase djposdb;
	 
	 
	public  DJPOSDatabase(Context paramContext) {
		
		dbContext = paramContext;
		
		//paramContext.deleteDatabase("djpos01.db");
		

		dbHelper = new DJPOSDatabaseHelper(paramContext);
		if (getDb() == null)
		      setDb(dbHelper.getWritableDatabase());
		
		 if (getDb().isOpen())
			 setDb(dbHelper.getWritableDatabase());

	}

	
	 public static SQLiteDatabase getDb()
	  {
	    return djposdb;
	  }
	
	public static void setDb(SQLiteDatabase paramSQLiteDatabase)
	  {
		djposdb = paramSQLiteDatabase;
	  }
	
	
	
	static class DJPOSDatabaseHelper extends SQLiteOpenHelper {
	
		public DJPOSDatabaseHelper(Context paramContext) {
			
			  super(paramContext, "djpos01.db", null, 9);
		}

		@Override
		public void onCreate(SQLiteDatabase djSQLLiteDatabase) {
			
			StringBuilder djposCreateItemTable = new StringBuilder();
			djposCreateItemTable.append("create table ").append(TABLE_ITEM).append(" (  ").append(ITEM_ID).append(" integer primary key, ").
			append(ITEM_UPC).append(" text, ").append(ITEM_DESC).append(" text, ").append(DEPARTMENT_NAME).append(" text, ").append(ITEM_PRICE).append(" currency").append(")  ");
			
			djSQLLiteDatabase.execSQL(djposCreateItemTable.toString());
			
			StringBuilder djposCreateIDGenTable = new StringBuilder();
			djposCreateIDGenTable.append("create table ").append(TABLE_ID_GENERATION).append(" (  ").append(COUNTER_NAME).append(" text, ").
			append(COUNTER_ID).append(" integer").append(")  ");
			
			djSQLLiteDatabase.execSQL(djposCreateIDGenTable.toString());
			
		/*	StringBuilder djposUpdateIDGenTable = new StringBuilder();
			djposUpdateIDGenTable.append("UPDATE SQLITE_SEQUENCE SET seq = 0000 WHERE name = 'CO_ID_GEN'");
			
			djSQLLiteDatabase.execSQL(djposUpdateIDGenTable.toString());*/
			
			StringBuilder djposCreateTransactionTable = new StringBuilder();
			djposCreateTransactionTable.append("create table ").append(TABLE_TRANSACTION).append(" (  ").append(STORE_ID).append(" integer not null, ").
			append(REG_ID).append(" integer not null, ").append(TRANSACTION_ID).append(" integer primary key, ").append(BUSINESS_DATE).append(" text,").
			append(TRANSACTION_TYPE).append(" text,").append(TRANSACTION_STATUS).append(" text").append(CUST_ID).append(" text").append(")  ");
			
			djSQLLiteDatabase.execSQL(djposCreateTransactionTable.toString());
			
			
			StringBuilder djposCreateSaleLineItemTable = new StringBuilder();
			djposCreateSaleLineItemTable.append("create table ").append(TABLE_SALE_LINE_ITEM).append(" (  ").append(STORE_ID).append(" integer not null, ").
			append(REG_ID).append(" integer not null, ").append(TRANSACTION_ID).append(" integer not null, ").append(BUSINESS_DATE).append(" text,").
			append(ITEM_ID).append(" text, ").append(ITEM_DESC).append(" text, ").append(ITEM_PRICE).append(" currency").append(")  ");
			
			djSQLLiteDatabase.execSQL(djposCreateSaleLineItemTable.toString());
			
			StringBuilder djposCreateStoreTable = new StringBuilder();
			djposCreateStoreTable.append("create table ").append(TABLE_STORE).append(" (  ").append(STORE_ID).append(" integer not null, ").append(STORE_NAME)
			.append(" text,").append(STORE_ADDRESS).append(" text, ").append(STORE_CITY).append(" text, ").append(STORE_ZIPCODE).append(" text,").
			append(STORE_PHONE).append(" text ").append(")  ");
			
			djSQLLiteDatabase.execSQL(djposCreateStoreTable.toString());
			
			
			StringBuilder djposCreateTenderLineItemTable = new StringBuilder();
			djposCreateTenderLineItemTable.append("create table ").append(TABLE_TENDER_LINE_ITEM).append(" (  ").append(STORE_ID).append(" integer not null, ").
			append(REG_ID).append(" integer not null, ").append(TRANSACTION_ID).append(" integer not null, ").append(BUSINESS_DATE).append(" text,").
			append(TENDER_TYPE).append(" text, ").append(TRANSACTION_AMOUNT).append(" currency ").append(")  ");
			
			djSQLLiteDatabase.execSQL(djposCreateTenderLineItemTable.toString());
			
			StringBuilder djposCreateTaxLineItemTable = new StringBuilder();
			djposCreateTaxLineItemTable.append("create table ").append(TABLE_TAX).append(" (  ").append(TAX_NAME).append(" text, ").
			append(TAX_PERCENTAGE).append(" integer").append(")  ");
			
			djSQLLiteDatabase.execSQL(djposCreateTaxLineItemTable.toString());
			
			
			StringBuilder djposCreateTransactionTaxTable = new StringBuilder();
			djposCreateTransactionTaxTable.append("create table ").append(TABLE_TRANSACTION_TAX).append(" (  ").append(STORE_ID).append(" integer not null, ").
			append(REG_ID).append(" integer not null, ").append(TRANSACTION_ID).append(" integer primary key, ").append(BUSINESS_DATE).append(" text,").
			append(TAX_AMOUNT_TOTAL).append(" currency").append(")  ");
			
			djSQLLiteDatabase.execSQL(djposCreateTransactionTaxTable.toString());
			
			StringBuilder djposCreateCustomerTable = new StringBuilder();
			djposCreateCustomerTable.append("create table ").append(TABLE_CUSTOMER).append(" (  ").append(CUST_ID).append(" integer primary key, ").
			append(CUST_NAME).append(" text, ").append(CUST_ADDRESS).append(" text, ").append(CUST_CITY).append(" text,").
			append(CUST_ZIPCODE).append(" text,").append(CUST_PHONE).append(" text, ").append(CUST_EMAIL).append(" text ").append(")  ");
			
			djSQLLiteDatabase.execSQL(djposCreateCustomerTable.toString());
			
			StringBuilder djposDepartmentTable = new StringBuilder();
			djposDepartmentTable.append("create table ").append(TABLE_DEPARTMENT).append(" (  ").append(DEPARTMENT_ID).append(" integer primary key autoincrement, ").
			append(DEPARTMENT_NAME).append(" text unique").append(")  ");
			
			djSQLLiteDatabase.execSQL(djposDepartmentTable.toString());
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			
			StringBuilder djposDropItemTable = new StringBuilder();
			djposDropItemTable.append("DROP TABLE IF EXISTS ").append(TABLE_ITEM);
			arg0.execSQL(djposDropItemTable.toString());
			
			StringBuilder djposDropIDGenTable = new StringBuilder();
			djposDropIDGenTable.append("DROP TABLE IF EXISTS ").append(TABLE_ID_GENERATION);
			arg0.execSQL(djposDropIDGenTable.toString());
			
			StringBuilder djposDropTxnTable = new StringBuilder();
			djposDropTxnTable.append("DROP TABLE IF EXISTS ").append(TABLE_TRANSACTION);
			arg0.execSQL(djposDropTxnTable.toString());
			
			
		}

	}
}

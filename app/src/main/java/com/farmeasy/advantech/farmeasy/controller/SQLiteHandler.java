package com.farmeasy.advantech.farmeasy.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.farmeasy.advantech.farmeasy.model.ExpensesModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by Mugo James on 1/27/2016.
 */

public class SQLiteHandler extends SQLiteOpenHelper {

    private final String TAG = SQLiteHandler.class.getSimpleName();


    //define all static variables

    //DataBase version
    private static final int DATABASE_VERSION = 1;

    //DB NAME
    private static final String DATABASE_NAME = "farmeasy_droidapp";

    //Login table name
    private static final String TABLE_LOGIN = "login";
    private static final String TABLE_ECATEGORY = "expense_category";
    private static final String TABLE_EXPENSES = "expenses";
    private static final String TABLE_ACCOUNTS = "accounts";
    private static final String TABLE_PCATEGORY = "product_category";
    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_SALES = "sales";
    public static final String TABLE_TOTALSALES="totalsales";




    //Login table columns
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";

    //expense_category table columns
    private static final String KEY_CATID = "expense_cat_id";
    private static final String KEY_CATNAME = "expense_cat_name";
    private static final String KEY_STATUS = "updateStatus";

    //expense table column

    private static final String KEY_EID = "expense_id";
    private static final String KEY_ECATEGORY = "cat_id";
    private static final String KEY_PAYEE = "expense_payee";
    private static final String KEY_AMOUNT = "expense_amount";
    private static final String KEY_ACCOUNT = "expense_account";
    private static final String KEY_DATE = "expense_date";
    private static final String KEY_COMMENT = "expense_comment";

    //Accounts Table
    private static final String KEY_ACCOUNTID = "account_id";
    private static final String KEY_ACCOUNTNAME= "account_name";
    private static final String KEY_OPENINGBAL = "openingbal";
    private static final String KEY_ACCDATE ="date";

    //product category table
    private static final String KEY_PCATID = "pcat_id";
    private static final String KEY_PCATNAME = "pcat_name";
    private static final String KEY_PSTATUS = "pcat_status";

    //products table
    private static final String KEY_PID = "pid";
    private static final String KEY_PNAME = "pname";
    private static final String KEY_PCATEGORY = "pcat";
    private static final String KEY_PQTY = "pqty";
    private static final String KEY_PRICE = "price";
    private static final String KEY_PDATE = "pdate";
    private static final String KEY_PCOMMENT = "pcoment";


    //sales table
    private static final String KEY_SALEPRODUCT = "saleproduct";
    private static final String KEY_SALESQTY = "saleqty";
    private static final String KEY_UNITPRICE = "sup";
    private static final String KEY_TOTAL = "saletotal";
    private static final String KEY_AMOUNTPAID = "paidamount";
    private static final String KEY_BALANCEPAYMENT = "bal";
    private static final String KEY_PAYMENTMETHOD = "salemethod";
    private static final String KEY_SALECUSTOMER = "salecustomer";

    //total sales
    public static final String KEY_TOTALSALEID = "totalsaleid";
    public static final String KEY_TOTALSALEAMOUNT = "totalsaleamount";
    public static final String KEY_TOTALSALESTATUS = "status";
    public static final String KEY_TOTALSALEDATE = "tdate";


    //private static final String KEY_STOCK = "stock";
    private static final String KEY_SALEDATE = "saledate";
    private static final String KEY_SALECOMMENT = "salecomment";
   private static final String KEY_SALEID = "saleid";





    //constructor
    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO~~ CREATE LOGIN TABLE
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_UID + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";

        //TODO ~~create table expense_category
        String CREATE_ECATEGORY_TABLE = "CREATE TABLE " + TABLE_ECATEGORY + "("
                + KEY_CATID + " INTEGER PRIMARY KEY," + KEY_CATNAME + " TEXT,"
                + KEY_STATUS + " TEXT" + ")";

        //TODO~~CREATE EXPENSE TABLE
        String CREATE_EXPENSE_TABLE = "CREATE TABLE " + TABLE_EXPENSES + "("
                + KEY_EID + " INTEGER PRIMARY KEY, " + KEY_ECATEGORY + " INTEGER, "
                + KEY_PAYEE + " TEXT, " + KEY_AMOUNT + " NUMERIC, "
                + KEY_ACCOUNT + " INTEGER, " + KEY_DATE + " NUMERIC,"
                + KEY_COMMENT + " TEXT " + ")";

        //TODO~~ Create Table ACCOUNTS
        String CREATE_ACCOUNTS_TABLE = "CREATE TABLE " + TABLE_ACCOUNTS + "("
                + KEY_ACCOUNTID + " INTEGER PRIMARY KEY, "+ KEY_ACCOUNTNAME + " TEXT, "
                + KEY_OPENINGBAL + " NUMBER, " + KEY_ACCDATE + " NUMERIC " + ")";

        //TODO ~~ create product cATEGORY tABLE
        String CREATE_PRODUCT_CAT_TABLE = " CREATE TABLE " + TABLE_PCATEGORY +"("
                + KEY_CATID + " INTEGER PRIMARY KEY, " + KEY_PCATNAME + " TEXT, "
                + KEY_PSTATUS + " TEXT " + ")";

        //todo ~~ Create table productS
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + KEY_PID + " INTEGER PRIMARY KEY, "
                + KEY_PNAME + " INTEGER, " + KEY_PCATEGORY + " NUMBER, "
                + KEY_PQTY + " NUMBER, " + KEY_PRICE + " NUMBER, "
                + KEY_PDATE + " NUMBER, " + KEY_PCOMMENT + " TEXT " + ")";

        //todo ~~ Create sale Table
        String CREATE_SALE_TABLE  = "CREATE TABLE " + TABLE_SALES + "("
                + KEY_SALEID + " INTEGER PRIMARY KEY, " + KEY_SALEPRODUCT + " TEXT,"
                + KEY_SALESQTY + " NUMBER, "
                + KEY_UNITPRICE + " NUMBER, "
                +KEY_TOTAL + " NUMBER, " + KEY_AMOUNTPAID + " NUMBER, "
                + KEY_BALANCEPAYMENT + " NUMBER, " +  KEY_PAYMENTMETHOD + " NUMBER, "
                + KEY_SALECUSTOMER + " TEXT, "
                + KEY_SALEDATE + " TEXT," + KEY_SALECOMMENT + " TEXT " + ")";

        //todo~~Create total sale Table
        String CREATE_TOTAL_SALE_TABLE = " CREATE TABLE " + TABLE_TOTALSALES + "("
                + KEY_TOTALSALEID + " INTEGER PRIMARY KEY, " + KEY_TOTALSALEAMOUNT + " NUMBER, "
                + KEY_TOTALSALESTATUS + " TEXT, " + KEY_TOTALSALEDATE + " DATETIME " + ")";




        db.execSQL(CREATE_LOGIN_TABLE);
        Log.d(TAG, "Database LOGIN tables created");
        db.execSQL(CREATE_ECATEGORY_TABLE);
        Log.d(TAG, "Database EXPENSE CATEGORY tables created");
        db.execSQL(CREATE_EXPENSE_TABLE);
        Log.d(TAG, "Database EXPENSE  tables created");
        db.execSQL(CREATE_ACCOUNTS_TABLE);
        Log.d(TAG, "Database Accounts  tables created");
        db.execSQL(CREATE_PRODUCT_CAT_TABLE);
        db.execSQL(CREATE_PRODUCTS_TABLE);
        db.execSQL(CREATE_SALE_TABLE);
        db.execSQL(CREATE_TOTAL_SALE_TABLE);


        //insert Default values to table_Expense categoty
        db.execSQL(" INSERT INTO " + TABLE_ECATEGORY + " VALUES(1, 'Rent' , 'no')" );
        db.execSQL(" INSERT INTO " + TABLE_ECATEGORY + " VALUES(2, 'Harvesting' , 'no')" );
        db.execSQL(" INSERT INTO " + TABLE_ECATEGORY + " VALUES(3, 'Transport' , 'no')" );
        db.execSQL(" INSERT INTO " + TABLE_ECATEGORY + " VALUES(4, 'Land Preparation' , 'no')" );
        db.execSQL(" INSERT INTO " + TABLE_ECATEGORY + " VALUES(5, 'Weed Spraying' , 'no')" );
        db.execSQL(" INSERT INTO " + TABLE_ECATEGORY + " VALUES(6, 'Plant Spraying' , 'no')" );
        db.execSQL(" INSERT INTO " + TABLE_ECATEGORY + " VALUES(7, 'Fuel' , 'no')" );

        //insert default value into product category
        db.execSQL(" INSERT INTO " + TABLE_PCATEGORY + " VALUES(1, 'Cereals' , 'no')" );
        db.execSQL(" INSERT INTO " + TABLE_PCATEGORY + " VALUES(2, 'Fruits' , 'no')" );
        db.execSQL(" INSERT INTO " + TABLE_PCATEGORY + " VALUES(3, 'Herbs_Medicinal' , 'no')" );
        db.execSQL(" INSERT INTO " + TABLE_PCATEGORY + " VALUES(4, 'Nuts' , 'no')" );
        db.execSQL(" INSERT INTO " + TABLE_PCATEGORY + " VALUES(5, 'Oil Seed' , 'no')" );
        db.execSQL(" INSERT INTO " + TABLE_PCATEGORY + " VALUES(6, 'Pulses' , 'no')" );
        db.execSQL(" INSERT INTO " + TABLE_PCATEGORY + " VALUES(7, 'Spices' , 'no')" );
        db.execSQL(" INSERT INTO " + TABLE_PCATEGORY + " VALUES(8, 'Roots and Tubers' , 'no')" );

        //insrt default value to Accounts Table
        db.execSQL(" INSERT INTO " + TABLE_ACCOUNTS + " VALUES(1, 'Cash' , 0.0 , null)" );
        db.execSQL(" INSERT INTO " + TABLE_ACCOUNTS + " VALUES(2, 'Bank' , 0.0, null)" );
        db.execSQL(" INSERT INTO " + TABLE_ACCOUNTS + " VALUES(3, 'Mobile' , 0.0, null)" );

        //Insert Default product into product tables
        db.execSQL(" INSERT INTO " + TABLE_PRODUCTS + " VALUES(1, 'Wheat' , 1 , 200 , 100 ,null , null)" );
        db.execSQL(" INSERT INTO " + TABLE_PRODUCTS + " VALUES(2, 'Soya Beans' , 6 , 200 , 100 ,null , null)" );
        db.execSQL(" INSERT INTO " + TABLE_PRODUCTS + " VALUES(3, 'Peanuts' , 6 , 200 , 100 ,null , null)" );

        //insert default for totalSaleAmount Table
        db.execSQL(" INSERT INTO " + TABLE_TOTALSALES + " VALUES(1,  150, 'no', null)" );


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ECATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PCATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOTALSALES);
        // Create tables again
        onCreate(db);

    }

    /*Storing user details in the database*/
    public void addUser(String name, String email, String uid, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);
        cv.put(KEY_EMAIL, email);
        cv.put(KEY_UID, uid);
        cv.put(KEY_CREATED_AT, created_at);

        //insert y=the row
        long id = db.insert(TABLE_LOGIN, null, cv);
        db.close();

        Log.d(TAG, "New User Inserted into SQLite database: " + id);

    }//end method add user

    //Getting users data from database
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT * FROM " + TABLE_LOGIN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //move to the first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("uid", cursor.getString(3));
            user.put("created_at", cursor.getString(4));
        }//end if statement

        cursor.close();
        db.close();

        //log to logcat
        Log.d(TAG, "Fetching user from SQLite" + user.toString());

        //return user
        return user;

    }//end getUserDetails Function


    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();

        //delete all rows
        db.delete(TABLE_LOGIN, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

    //TODO insert EXPENSE_CATEGORY into mysqlite db
    public void insert_Ecategory(HashMap<String, String> queryValues) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CATNAME, queryValues.get("expense_cat_name"));
        values.put("updateStatus", "no");
        database.insert("expense_category", null, values);
        database.close();

}

    //TODO get list of expense_category as arraylist
        public ArrayList<HashMap<String, String>> getAll_Ecategory() {
            ArrayList<HashMap<String, String>> wordList;
            wordList = new ArrayList<HashMap<String, String>>();

            String selectquery = "SELECT * FROM " + TABLE_ECATEGORY;
            SQLiteDatabase database = this.getReadableDatabase();
            Cursor cursor = database.rawQuery(selectquery, null);

            if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("expense_cat_id", cursor.getString(0));
                map.put("expense_cat_name", cursor.getString(1));
                wordList.add(map);
            } while (cursor.moveToNext());
        }

        database.close();
        Log.d(TAG, "Fetching user from SQLite" + wordList.toString());
        return wordList;

    }//end method

    //TODO compose Json OUT OF sqlite Records
    public String composeJSONfromSQLite() {
        ArrayList<HashMap<String, String>> wordlist;
        wordlist = new ArrayList<HashMap<String, String>>();

        String sqlquery = "SELECT * FROM expense_category WHERE updateStatus = '" + "no" + "'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(sqlquery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("expense_cat_id", cursor.getString(0));
                map.put("expense_cat_name", cursor.getString(1));
                wordlist.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        Gson gson = new GsonBuilder().create();

        //use Gson to serialize Array List to JSON
        return gson.toJson(wordlist);

    }

    //TODO get sync status of sqlite

    public String getSyncStatus() {
        String msg = null;
        if (this.dbSyncCount() == 0) {
            msg = "SQLite and Remote MysQL DBs in Sync!";
        } else {
            msg = "DB Sync Needed";
        }
        return msg;
    }


    //get Sqlite records that are yet to be synced
    public int dbSyncCount() {
        int count = 0;
        String selectQuery = "SELECT  * FROM expense_category where updateStatus = '" + "no" + "'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        count = cursor.getCount();
        database.close();
        return count;
    }

    //update Sync status against each User ID
    public void updateSyncStatus(String id, String status) {
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "UPDATE expense_category set updateStatus = '" + status + "' where catId=" + "'" + id + "'";
        Log.d("query", updateQuery);
        database.execSQL(updateQuery);
        database.close();

    }


    public void open() throws SQLException {
        SQLiteDatabase database = this.getWritableDatabase();
        database.isOpen();

    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //select all from ExpenseManager
    public HashMap<String, String> getExpenses() {
        HashMap<String, String> expenses = new HashMap<String, String>();
        String selectQuery = "SELECT * FROM " + TABLE_EXPENSES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //move to the first row
        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            do {

                expenses.put("cat_id", cursor.getString(1));
                expenses.put("expense_payee", cursor.getString(2));
                expenses.put("expense_amount", cursor.getString(3));
                expenses.put("expense_acount", cursor.getString(4));
                expenses.put("expense_date", cursor.getString(5));
                expenses.put("expense_comment", cursor.getString(6));
            } while (cursor.moveToNext());
        }//end if statement

        cursor.close();
        db.close();

        //log to logcat
        Log.d(TAG, "Fetching user from SQLite" + expenses.toString());

        //return user
        return expenses;

    }//end getUserDetails Function
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//TODO Model Definition
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //TODO create an Expense TABLE
    public void insertNewExpense(HashMap<String, String> queryValues) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put("cat_id", queryValues.get("cat_id"));
        values.put("expense_payee", queryValues.get("expense_payee"));
        values.put("expense_amount", queryValues.get("expense_amount"));
        values.put("expense_account", queryValues.get("expense_account"));
        values.put("expense_date", queryValues.get("expense_date"));
        values.put("expense_comment", queryValues.get("expense_comment"));

        //insert row
        db.insert(TABLE_EXPENSES, null, values);
        //Log.d("Inserting Expenses!!", ));


    }

    //TODO method to get the time and date
    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    //TODO method to get the time and date
    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


//get All Expenses Spinner
public List<String> getAllExpenses2() {
    List<String> expensescat = new ArrayList<>();

    //query
    String Query = "SELECT * FROM " + TABLE_ECATEGORY;
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(Query, null);

    if (cursor.moveToFirst()) {
        do {
            expensescat.add(cursor.getString(1));
        } while (cursor.moveToNext());

    }
    cursor.close();
    db.close();

    return expensescat;

}
    //TODO`~~ ADD/Insert  DATa To account table
    public void insertAccount(HashMap<String,String> queryValues){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("account_id",queryValues.get("account_id"));
        values.put("account_name",queryValues.get("account_name"));
        values.put("openingbal",queryValues.get("openingbal"));
        values.put("date", queryValues.get("date"));

        //insert rows
        db.insert(TABLE_ACCOUNTS, null, values);

       // Log.d("Creating New Account!!: ","");
    }

    //// TODO: 2/8/2016 ~~ getAll Acounts
    public  ArrayList <HashMap<String,String>> getAccounts(){
        ArrayList<HashMap<String,String>> accountList;
        accountList = new ArrayList<HashMap<String, String>>();

        String query = "SELECT * FROM " + TABLE_ACCOUNTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("account_id",cursor.getString(0));
                map.put("account_name",cursor.getString(1));
                map.put("openingbal",cursor.getString(2));
                map.put("date",cursor.getString(3));

                accountList.add(map);

            }while(cursor.moveToNext());

        }
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching Acounts!!" + accountList.toString());

        return accountList;

    }//end method

//todo ~~ insert data in product category table
    public  void insert_ProductCategory(HashMap<String,String> queryvalues){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_PCATNAME,queryvalues.get("pcat_name"));
        values.put("pcat_status", "no");
        db.insert(TABLE_PCATEGORY, null, values);

        db.close();

    }
    //Get the List of Product Category
    //TODO get list of expense_category as arraylist
    public ArrayList<HashMap<String, String>> getAll_PCategory() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();

        String selectquery = "SELECT * FROM " + TABLE_PCATEGORY;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectquery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("pcat_id", cursor.getString(0));
                map.put("pcat_name", cursor.getString(1));
                wordList.add(map);
            } while (cursor.moveToNext());
        }

        database.close();
        Log.d(TAG, "Fetching user from SQLite" + wordList.toString());
        return wordList;

    }//end method

    public void insertNewProduct(HashMap<String,String> queryvalues){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("pname",queryvalues.get("pname"));
        values.put("pcat",queryvalues.get("pcat"));
        values.put("pqty",queryvalues.get("pqty"));
        values.put("price",queryvalues.get("price"));
        values.put("pdate",queryvalues.get("pdate"));
        values.put("pcoment",queryvalues.get("pcoment"));

        //insert row
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();

    }
    public List<String> getAllProductCat2(){
        List<String> category = new ArrayList<>();
        //Query
        String selectQuery = "SELECT * FROM " + TABLE_PCATEGORY;

        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        //loping through all rowas
        if(cursor.moveToFirst()){
            do{
                category.add(cursor.getString(1));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return  category;



    }
    //get All Expenses Spinner
    public List<String> getAllProducts() {
        List<String> products = new ArrayList<>();

        //query
        String Query = "SELECT * FROM " + TABLE_PRODUCTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);

        if (cursor.moveToFirst()) {
            do {
                products.add(cursor.getString(1));
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();

        return products;

    }
    //todo ~~ insert data in sales table
    public void insertSale(HashMap<String,String> queryvalues){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("saleproduct",queryvalues.get("saleproduct"));
        values.put("saleqty",queryvalues.get("saleqty"));
        values.put("sup",queryvalues.get("sup"));
        values.put("saletotal",queryvalues.get("saletotal"));
        values.put("paidamount",queryvalues.get("paidamount"));
        values.put("bal", queryvalues.get("bal"));
        values.put("salemethod",queryvalues.get("salemethod"));
        values.put("salecustomer", queryvalues.get("salecustomer"));
        //values.put("stock",queryvalues.get("stock"));
        values.put("saledate",queryvalues.get("saledate"));
        values.put("salecomment", queryvalues.get("salecomment"));

        //insert row
        db.insert(TABLE_SALES, null, values);
        Log.d(TAG, "Inserting: "+ queryvalues.toString());


    }

    //fetch the sale List~~ get all sales

    //get All Accounts Spinner
    public List<String> getAllAccounts() {
        List<String> accounts = new ArrayList<>();

        //query
        String Query = "SELECT * FROM " + TABLE_ACCOUNTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);

        if (cursor.moveToFirst()) {
            do {
                accounts.add(cursor.getString(1));
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();

        return accounts;

    }
    //todo ~~ insert into table totalsale
public void insertsaletotals(HashMap<String,String> queryvalues){
    SQLiteDatabase db  = this.getWritableDatabase();
    ContentValues values = new ContentValues();

    values.put("totalsaleamount",queryvalues.get("totalsaleamount"));
    values.put("status",queryvalues.get("status"));
    values.put("tdate",queryvalues.get("tdate"));

    //insert row
    db.insert(TABLE_TOTALSALES, null, values);
    Log.d(TAG, "Inserting: " + queryvalues.toString());

}

    //Todo ~~ lets get all totalsales stored in the db
    public ArrayList<HashMap<String, String>> getTotals() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();

        String selectquery = "SELECT * FROM " + TABLE_TOTALSALES;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectquery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("totalsaleamount", cursor.getString(1));
                map.put("tdate", cursor.getString(3));
                wordList.add(map);
            } while (cursor.moveToNext());
        }

        database.close();
        Log.d(TAG, "Fetching user from SQLite" + wordList.toString());
        return wordList;

    }//end method




}


//end class

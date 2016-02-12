package com.farmeasy.advantech.farmeasy.view;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.farmeasy.advantech.farmeasy.R;
import com.farmeasy.advantech.farmeasy.controller.SQLiteHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalesFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Spinner productSpinner,paymethodSpinner,customernamespinner;
    EditText txtQty, txtunitprice,txttotal,txtdbtotal,txtpaid,txtbal,txtcustomer;
    EditText txtstock,txtdate,txtcomment,txtsaleproduct;
    Button btnSaleSave;
    private Context context;

    //sqlite handler
    SQLiteHandler handler;



    public SalesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = this.getActivity();
        SQLiteHandler handler = new SQLiteHandler(context);
        View v =  inflater.inflate(R.layout.fragment_sales, container, false);
        handler = new SQLiteHandler(getActivity());

        productSpinner = (Spinner) v.findViewById(R.id.spinner_sale);
        paymethodSpinner = (Spinner) v.findViewById(R.id.spinner_method);
       // customernamespinner = (Spinner) v.findViewById(R.id.salecustomerspinner);

        txtsaleproduct = (EditText) v.findViewById(R.id.txt_sale_product);
        txtQty = (EditText) v.findViewById(R.id.txt_saleQty);
        txtunitprice = (EditText) v.findViewById(R.id.txt_sp);
        txttotal = (EditText) v.findViewById(R.id.txt_total);
        txtpaid = (EditText) v.findViewById(R.id.txt_saleamountreceived);
       // txtstock = (EditText) v.findViewById(R.id.txt_stockQty);
        txtdate = (EditText) v.findViewById(R.id.txt_saledate);
        txtcustomer = (EditText) v.findViewById(R.id.txt_salecustomer);
        txtbal = (EditText) v.findViewById(R.id.txt_receivable);
        txtdbtotal = (EditText) v.findViewById(R.id.txt_db_totalAmount);
        txtcomment = (EditText) v.findViewById(R.id.txtsalecomment);

        btnSaleSave = (Button) v.findViewById(R.id.btn_savesale);
        //add btn listener
        btnSaleSave.setOnClickListener(this);

        //add spinner listener
        productSpinner.setOnItemSelectedListener(this);
        paymethodSpinner.setOnItemSelectedListener(this);

        txtdate.setText(handler.getDateTime().toString());//call method and set date to current date
       // customernamespinner.setOnItemSelectedListener(this);



        loadProductSpinner();
        loadAccountSpinner();
        return  v;
    }
    //todo~~method to load list of product to listbox from mysqlite database
    private void loadProductSpinner() {

        SQLiteHandler db = new SQLiteHandler(getActivity().getApplicationContext());

        //spiner dropdown
        List<String> products = db.getAllProducts();

        //create spinner adapter
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,
                products);

        //attach adapter to spiner
        productSpinner.setAdapter(dataAdapter);
    }//end loadproductSpinner()

    //todo~~method to load list of product to listbox from mysqlite database
    private void loadAccountSpinner() {

        SQLiteHandler db = new SQLiteHandler(getActivity().getApplicationContext());

        //spiner dropdown
        List<String> accounts = db.getAllAccounts();

        //create spinner adapter
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,
                accounts);

        //attach adapter to spiner
        paymethodSpinner.setAdapter(dataAdapter);
    }//end loadAccountSpinner)


    private  void insertSale() {
        String updatestatus = "no";
        SQLiteHandler handler = new SQLiteHandler(context);
        //get from the database the TotalSale Amount
       SQLiteDatabase db =  handler.getReadableDatabase();//open database

        //start transaction
        db.beginTransaction();
        try{
            String query = "SELECT * FROM " + SQLiteHandler.TABLE_TOTALSALES;
            Cursor cursor = db.rawQuery(query,null);
            if (cursor.getCount() > 0){
                while(cursor.moveToNext()){
                    //read column data
                   String  totalamount =  cursor.getString(cursor.getColumnIndex("totalsaleamount"));



                           txtdbtotal.setText(totalamount);//set the text field txtdbtotal to value from database

                }
            }
            db.setTransactionSuccessful();

        }catch (SQLiteException e){e.printStackTrace();}
        finally {
            db.endTransaction();
            db.close();
        }

        //check if the quantity and unit price is provided
        if(txtQty.getText().toString().isEmpty() || txtunitprice.getText().toString().isEmpty() || txtpaid.getText().toString().isEmpty()){
            Toast.makeText(getActivity().getApplicationContext(),"All fields required",Toast.LENGTH_LONG).show();
        }else {
            //we calculate the totals and balances on payment
            float quantity;//total quantity sold
            float unitprice;//price of good per unit price
            float amountpaid;// amount received from the customer
            float total = 0; //totalsale
            float bal; // debt
           // float total_sale;//totalsale

      float totalOverall = Float.parseFloat(txtdbtotal.getText().toString());
            //totalOverall += total;

                    //getting textfield values
            quantity = Float.parseFloat(txtQty.getText().toString());
            unitprice = Float.parseFloat(txtunitprice.getText().toString());
            amountpaid = Float.parseFloat(txtpaid.getText().toString());

            //now to some arithemetics
            total += (unitprice * quantity);
            totalOverall += total;//value to be store in TABLE_TOTALSALES

            //Unpaid balance! debtors amount ! overdraft
            bal = total - amountpaid;
            //  txttotal.setText();

            //let pass the data to SQLITEHandler Class as an array

            HashMap<String, String> queryValues = new HashMap<String, String>();
            HashMap<String, String> totalsaleQuery = new HashMap<String, String>();
            handler = new SQLiteHandler(this.getActivity());
            try {
                handler.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //Text field validation
            //ensure the user fills all text fields
            if (txtQty.getText().toString().isEmpty() || txtunitprice.getText().toString().isEmpty() ||
                    txtpaid.getText().toString().isEmpty() || txtdate.getText().toString().isEmpty()) {
                Toast.makeText(getActivity().getApplicationContext(), "Fill in all fields", Toast.LENGTH_LONG).show();
            } else {
                queryValues.put("saleproduct", String.valueOf(productSpinner.getSelectedItem()));
                queryValues.put("saleqty", txtQty.getText().toString().toString());
                queryValues.put("sup", txtunitprice.getText().toString());
                queryValues.put("saletotal", String.valueOf(total));
                queryValues.put("paidamount", txtpaid.getText().toString());
                queryValues.put("bal", String.valueOf(bal));
                queryValues.put("salemethod", String.valueOf(paymethodSpinner.getSelectedItem()));
                queryValues.put("salecustomer", txtcustomer.getText().toString());
                // queryValues.put("stock",txtcustomer.getText().toString());
                queryValues.put("date", txtdate.getText().toString());
                queryValues.put("salecomment", txtcomment.getText().toString());

                //<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                totalsaleQuery.put("totalsaleamount", String.valueOf( totalOverall));
                totalsaleQuery.put("status", updatestatus);
                totalsaleQuery.put("tdate",handler.getDate());
               // <<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

                handler = new SQLiteHandler(getActivity());
                handler.insertSale(queryValues);//insert sales data in the database
                handler.insertsaletotals(totalsaleQuery);// insert the total sale details in the data base

               // String payme = String.valueOf(paymethodSpinner.getSelectedItemId());
                //String saleprod = txtsaleproduct.getText().toString();


               // Toast.makeText(getActivity().getApplicationContext(), "Payment Method is: " + payme, Toast.LENGTH_LONG).show();

                //todo ~~ Now that we have fetched total sale amount from our dataBase, lets now
                //todo~~ Update our total or we may
              Toast.makeText(getActivity().getApplicationContext(), "Overall Total is product is: " + totalOverall, Toast.LENGTH_LONG).show();

                Toast.makeText(getActivity().getApplicationContext(), "Data Inserted Suceessfully!", Toast.LENGTH_LONG).show();
                clearTextFields();



            }
        }
}
    //todo~~ Method to clear text fields
    private  void clearTextFields(){
        SQLiteHandler handler = new SQLiteHandler(getActivity());


        txtQty.setText("");
        txtunitprice.setText("");
        txttotal.setText("");
        txtpaid.setText("");
        txtbal.setText("");
        txtcustomer.setText("");
        txtdate.setText(handler.getDateTime());
        txtcomment.setText("");

    }
    @Override
    public void onClick(View v) {
        insertSale();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

       // String saleprod = parent.getItemAtPosition(position).toString();


        //show selected spinner item
       // Toast.makeText(parent.getContext(), "You've selected: " + saleprod,
              //  Toast.LENGTH_LONG).show();
     //   txtsaleproduct.setText(saleprod.toString());

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

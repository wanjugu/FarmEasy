package com.farmeasy.advantech.farmeasy.view;


import android.database.SQLException;
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

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    EditText txtName,txtcategory;
    EditText txtQty;
    EditText txtprice,txtdate,txtcomment;
    Button btnSave;

    Spinner spinner;
    SQLiteHandler handler;



    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_product, container, false);
        txtName = (EditText)v.findViewById(R.id.txt_product_name);
        txtcategory = (EditText) v.findViewById(R.id.txtcategory);
        spinner = (Spinner) v.findViewById(R.id.pcategory);
        txtQty = (EditText) v.findViewById(R.id.txt_product_qty);
        txtprice = (EditText) v.findViewById(R.id.txt_unitPrice);
        txtdate = (EditText) v.findViewById(R.id.txt_pdate);
        txtcomment = (EditText) v.findViewById(R.id.txt_pcomment);
        btnSave = (Button) v.findViewById(R.id.btn_product_save);

        handler = new SQLiteHandler(this.getActivity());

        //set txtdate to current date~time
        String date = handler.getDateTime().toString();
        txtdate.setText(date);

        btnSave.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);
        loadSpinner();

        return  v;


    }

    private void loadSpinner() {

            SQLiteHandler db = new SQLiteHandler(getActivity().getApplicationContext());

            //spiner dropdown
            List<String> products = db.getAllProductCat2();

            //create spinner adapter
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,
                    products);

            //attach adapter to spiner
            spinner.setAdapter(dataAdapter);


        }
    private void insertNewProductToDB(){
        HashMap<String,String> queryValues = new HashMap<String,String>();
        handler= new SQLiteHandler(this.getActivity());
        try {
            handler.open();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        //pass the textfield values to the SQLITEHandler ~~ and insert them to the sqlite Database
        if(txtName.getText().toString().isEmpty() || txtQty.getText().toString().isEmpty() ||
                txtprice.getText().toString().isEmpty() || txtdate.getText().toString().isEmpty() ){

            Toast.makeText(getActivity().getApplicationContext(),"Please Provide All fields",Toast.LENGTH_LONG).show();

        }else {
            queryValues.put("pname",txtName.getText().toString());
            queryValues.put("pcat",txtcategory.getText().toString());
            queryValues.put("pqty",txtQty.getText().toString());
            queryValues.put("price",txtprice.getText().toString());
            queryValues.put("pdate",txtdate.getText().toString());
            queryValues.put("pcomment",txtcomment.getText().toString());

            //insert the rows
            handler = new SQLiteHandler(getActivity());
            handler.insertNewProduct(queryValues);

            Toast.makeText(getActivity().getApplicationContext(),"Product Inserted",Toast.LENGTH_LONG).show();


            //clear the textfields
            txtName.setText("");
            txtcategory.setText("");
            txtQty.setText("");
            txtprice.setText("");
            txtdate.setText("");
            txtcomment.setText("");
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_product_save:
                insertNewProductToDB();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String pcategory = parent.getItemAtPosition(position).toString();


        //show selected spinner item
        Toast.makeText(parent.getContext(), "You've selected: " + pcategory,
                Toast.LENGTH_LONG).show();
        //set the selected item as to txtcategory text

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

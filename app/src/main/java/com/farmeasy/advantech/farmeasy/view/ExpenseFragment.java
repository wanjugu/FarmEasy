package com.farmeasy.advantech.farmeasy.view;


import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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
import com.farmeasy.advantech.farmeasy.controller.ExpenseController;
import com.farmeasy.advantech.farmeasy.controller.SQLiteHandler;
import com.farmeasy.advantech.farmeasy.model.ExpensesModel;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseFragment extends Fragment implements
        View.OnClickListener,AdapterView.OnItemSelectedListener {
    EditText txtCategory,txtpayee,txtamount,txtaccount,txtdate,txtcomment;
    Button btnsave,btnexpenseview;
    Spinner spinner;


    SQLiteHandler handler = new SQLiteHandler(getActivity());
    //ExpensesModel expensesModel = new ExpensesModel();


    public ExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_expense, container, false);
        txtCategory = (EditText) v.findViewById(R.id.txtcategory);
        spinner = (Spinner) v.findViewById(R.id.spinner);
        txtpayee = (EditText)v.findViewById(R.id.txtpayee);
        txtamount = (EditText) v.findViewById(R.id.txtquantity);
        txtaccount = (EditText) v.findViewById(R.id.txtaccount);
        txtdate = (EditText) v.findViewById(R.id.txtdate);
        txtcomment = (EditText) v.findViewById(R.id.txtcomment);
        btnsave = (Button) v.findViewById(R.id.btn_expense_cat);
        btnexpenseview = (Button) v.findViewById(R.id.btn_view_Expenses);

        //set the Date text field to current date
        String date = handler.getDateTime().toString();
        txtdate.setText(date);

        //add onclick listener for button save
        btnsave.setOnClickListener(this);
        btnexpenseview.setOnClickListener(this);

        //set listener for spiner
        spinner.setOnItemSelectedListener(this);
        loadSpinner();


       /* Bundle bundle = this.getArguments();
        if(bundle != null){
            String i = bundle.getString("expensecategory");
            Log.d("Bundled Arguments: ",i);
        }
        txtCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getActivity().getApplicationContext(),"Clickable EditText",Toast.LENGTH_LONG).show();

                loadExpenseCategory();
            }
        });*/


        return v;
    }

    private void loadSpinner() {
        SQLiteHandler db = new SQLiteHandler(getActivity().getApplicationContext());

        //spiner dropdown
        List<String> expenses = db.getAllExpenses2();

        //create spinner adapter
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,
                expenses);

        //attach adapter to spiner

        spinner.setAdapter(dataAdapter);

    }
    //spiner onitemSelected listener
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String ecategory = parent.getItemAtPosition(position).toString();


        //show selected spinner item
        Toast.makeText(parent.getContext(),"You've selected: "+ ecategory,
                Toast.LENGTH_LONG).show();
        //set the selected item as to txtcategory text
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void loadExpenseCategory(){
        ExpenseCategoryFragment expenseCategoryFragment = new ExpenseCategoryFragment();
        android.support.v4.app.FragmentTransaction ft1 = getActivity().getSupportFragmentManager().beginTransaction();

        ft1.replace(R.id.frame,expenseCategoryFragment);
        ft1.commit();
    }


    public void addNewExpense(){
        HashMap<String,String> queryValues = new HashMap<String,String>();
        handler= new SQLiteHandler(this.getActivity());
        try {
            handler.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //pass the textfield values to the SQLITEHandler ~~ and insert them to the sqlite Database


        queryValues.put("cat_id",txtCategory.getText().toString());
        queryValues.put("expense_payee",txtpayee.getText().toString());
        queryValues.put("expense_amount",txtamount.getText().toString());
        queryValues.put("expense_account",txtaccount.getText().toString());
        queryValues.put("expense_date",txtdate.getText().toString());
        queryValues.put("expense_comment",txtcomment.getText().toString());

        //DAtabase handler
        handler.insertNewExpense(queryValues);
        //txtCategory.setText("");
        txtpayee.setText("");
        txtaccount.setText("");
        txtamount.setText("");
        txtdate.setText(handler.getDateTime());
        txtcomment.setText("");

        Toast.makeText(getActivity().getApplicationContext(),"Data Inserted",Toast.LENGTH_LONG).show();


    }
    @Override
    public void onClick(View v) {
        SQLiteHandler handler = new SQLiteHandler(getActivity());

        switch(v.getId()) {
            case R.id.btn_expense_cat:
               addNewExpense();
                break;
            case R.id.btn_view_Expenses:
                handler.getExpenses();
                break;
        }

    }


}

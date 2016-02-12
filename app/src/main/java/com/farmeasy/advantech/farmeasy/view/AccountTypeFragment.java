package com.farmeasy.advantech.farmeasy.view;


import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.farmeasy.advantech.farmeasy.R;
import com.farmeasy.advantech.farmeasy.controller.SQLiteHandler;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountTypeFragment extends Fragment implements View.OnClickListener {
    EditText txtacc_name,txtopeningBal,txtdate;
    Button btnsave;

    //sqlite helper instance
    SQLiteHandler handler;


    public AccountTypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        handler = new SQLiteHandler(this.getActivity());
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_account_type, container, false);
        txtacc_name = (EditText) v.findViewById(R.id.txt_accname);
        txtopeningBal = (EditText)v.findViewById(R.id.txt_acc_bal);
        txtdate = (EditText) v.findViewById(R.id.txt_acc_date);
        //set the date to current date and time
        txtdate.setText(handler.getDateTime().toString());
        btnsave = (Button) v.findViewById(R.id.btn_acc_save);
         //add button onclick listener
        btnsave.setOnClickListener(this);

        //ListView

        handler = new SQLiteHandler(getActivity());
        try {
            handler.open();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        handler= new SQLiteHandler(this.getActivity());


        final ArrayList<HashMap<String, String>> category_list =  handler.getAccounts();
        //
        if(category_list.size() !=0 ) {
            //Set the User Array list in ListView
            final ListAdapter adapter = new SimpleAdapter(getActivity(),
                    category_list, (R.layout.view_category_entries), new String[]{"account_name"}, new int[]{R.id.listCatName});
            final ListView myList = (ListView) v.findViewById(R.id.accountList);
            myList.setAdapter(adapter);
        }

        return  v;

    }
    private  void btnSaveClick(){

        HashMap<String,String> queryvalues = new HashMap<String,String>();
        handler = new SQLiteHandler(this.getActivity());

        try {
            handler.open();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        //todo~~ get the user input
        if (txtacc_name.getText().toString().isEmpty() || txtopeningBal.getText().toString().isEmpty() ||
                txtdate.getText().toString().isEmpty()){Toast.makeText(getActivity().getApplicationContext(),
                "All fields Required!",Toast.LENGTH_LONG).show();}else {
            queryvalues.put("account_name", txtacc_name.getText().toString());
            queryvalues.put("openingbal", txtopeningBal.getText().toString());
            queryvalues.put("date", txtdate.getText().toString());

            //insert data to the db
            handler.insertAccount(queryvalues);
            Toast.makeText(getActivity().getApplicationContext(), "Account Created Successfully",
                    Toast.LENGTH_LONG).show();
            txtacc_name.setText("");
            txtopeningBal.setText("");
            txtdate.setText(handler.getDateTime().toString());
        }

           }//end method btnsave

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_acc_save:
                btnSaveClick();
                break;
        }
    }
}

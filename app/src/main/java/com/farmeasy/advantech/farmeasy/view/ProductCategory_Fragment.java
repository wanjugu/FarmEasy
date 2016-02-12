package com.farmeasy.advantech.farmeasy.view;


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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductCategory_Fragment extends Fragment implements View.OnClickListener {
    EditText txtname;
    Button btnsave;
    SQLiteHandler handler;


    public ProductCategory_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_product_category_, container, false);
        txtname = (EditText) v.findViewById(R.id.txt_product_cat);
        btnsave = (Button) v.findViewById(R.id.btn_product_cat);

        btnsave.setOnClickListener(this);

        handler = new SQLiteHandler(getActivity());
        try {
            handler.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        handler= new SQLiteHandler(this.getActivity());


        final ArrayList<HashMap<String, String>> category_list =  handler.getAll_PCategory();
        //
        if(category_list.size() !=0 ) {
            //Set the User Array list in ListView
            final ListAdapter adapter = new SimpleAdapter(getActivity(),
                    category_list, (R.layout.view_category_entries), new String[]{"pcat_name"}, new int[]{R.id.listCatName});
            final ListView myList = (ListView) v.findViewById(android.R.id.list);
            myList.setAdapter(adapter);
        }

            return v;

    }

    //Method to add product category
    private void buttonAddProductCategory(){
        HashMap<String,String> queryvalues = new HashMap<String,String>();
        queryvalues.put("pcat_name",txtname.getText().toString());
        if(txtname.getText().toString() != null &&
                txtname.getText().toString().trim().length() != 0){
            handler= new SQLiteHandler(getActivity());
            handler.insert_ProductCategory(queryvalues);

            txtname.setText("");

        }else{
            Toast.makeText(getContext(), "Please enter Category name",
                    Toast.LENGTH_LONG).show();


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_product_cat:
                buttonAddProductCategory();
                break;
        }
    }
}

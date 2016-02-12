package com.farmeasy.advantech.farmeasy.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.farmeasy.advantech.farmeasy.R;
import com.farmeasy.advantech.farmeasy.view.ExpenseCategoryFragment;

import java.util.HashMap;

/**
 * Created by Advantech Ltd on 2/3/2016.
 */
public class NewExpenseCategory extends Activity{
    EditText Ecategory;
    SQLiteHandler handler = new SQLiteHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expensecategory);
        Ecategory = (EditText) findViewById(R.id.catName);

    }
    /**
     * Called when Save button is clicked
     * @param view
     */
    public void addNewCategory(View view) {
        HashMap<String, String> queryValues = new HashMap<String, String>();
        queryValues.put("expense_cat_name", Ecategory.getText().toString());
        if (Ecategory.getText().toString() != null
                && Ecategory.getText().toString().trim().length() != 0) {
            handler.insert_Ecategory(queryValues);
            this.callHomeActivity(view);
        } else {
            Toast.makeText(getApplicationContext(), "Please enter Category name",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Navigate to Home Screen
     * @param view
     */
    public void callHomeActivity(View view) {
        Intent objIntent = new Intent(getApplicationContext(),
                ExpenseCategoryFragment.class);
        startActivity(objIntent);
    }

    /**
     * Called when Cancel button is clicked
     * @param view
     */
    public void cancelAddUser(View view) {
        this.callHomeActivity(view);
    }
}



package com.farmeasy.advantech.farmeasy.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.farmeasy.advantech.farmeasy.model.ExpensesModel;

/**
 * Created by Advantech Ltd on 2/4/2016.
 */
public class ExpenseController {
    private SQLiteHandler dbHelper;

    public ExpenseController(Context context) {
        dbHelper = new SQLiteHandler(context);
    }

    public int insertExpenses() {


        return 0;
    }


}





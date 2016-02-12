package com.farmeasy.advantech.farmeasy.view;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.farmeasy.advantech.farmeasy.R;
import com.farmeasy.advantech.farmeasy.controller.ReportCardAdapter;
import com.farmeasy.advantech.farmeasy.controller.SQLiteHandler;

import java.lang.reflect.Field;
import java.sql.SQLException;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalesReportFragment extends Fragment {
    Context context;
   // TableLayout tableLayout;
    SQLiteHandler handler;
    ReportCardAdapter reportCardAdapter;


    public SalesReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
     View v = inflater.inflate(R.layout.fragment_sales_report, container, false);
       // reportCardAdapter = new ReportCardAdapter(this);
        context = this.getActivity();
        handler = new SQLiteHandler(this.getActivity());
        handler.getTotals();

        TableLayout tableLayout = (TableLayout) v.findViewById(R.id.saleReportLayout);

        //Add row header
        TableRow rowHeader = new TableRow(context);
        rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        String[] headerText = {"DATE","TOTAL"};
        for (String c:headerText){
            TextView tv = new TextView(this.getActivity());
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(18);
            tv.setPadding(5, 5, 5, 5);
            tv.setText(c);

            rowHeader.addView(tv);
        }
       tableLayout.addView(rowHeader);

        //Get the data from SQLite database and add them to the table
        SQLiteDatabase db = handler.getReadableDatabase();

        //start transaction
        db.beginTransaction();

        try{
            //Query database
            String selectQuery = "SELECT * FROM " + handler.TABLE_TOTALSALES;
            Cursor cursor = db.rawQuery(selectQuery,null);
            if(cursor.getCount() > 0){
                while(cursor.moveToNext()){
                    //Reda column data
                    int ids = cursor.getInt(cursor.getColumnIndex("totalsaleid"));
                    float total = cursor.getFloat(cursor.getColumnIndex("totalsaleamount"));
                    String updatestatus = cursor.getString(cursor.getColumnIndex("status"));
                    String date = cursor.getString(cursor.getColumnIndex("tdate"));


                    int tt = (int) total;
                   String dte = date;
                    //Toast.makeText(getActivity().getApplicationContext(),"total is: "+ tt,Toast.LENGTH_LONG).show();
                   // Toast.makeText(getActivity().getApplicationContext(),"date is: " + dte,Toast.LENGTH_LONG).show();

                    //data row
                    TableRow row = new TableRow(context);
                    row.setLayoutParams(new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));

                    String[] colText = {date,total + ""};
                    for(String text:colText){
                        TextView tv = new TextView(this.getActivity());
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));

                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(18);
                        tv.setTextColor(Color.parseColor("#030232"));
                        tv.setPadding(5, 5, 5, 5);
                        tv.setText(text);
                        row.addView(tv);
                    }
                    tableLayout.addView(row);

                }

            }
            db.setTransactionSuccessful();
        }catch (SQLiteException e){e.printStackTrace();}

        finally {
            db.endTransaction();
            db.close();
        }







        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

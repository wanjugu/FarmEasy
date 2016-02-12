package com.farmeasy.advantech.farmeasy.view;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
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
public class ExpenseCategoryFragment extends Fragment implements View.OnClickListener{
    //instance of SQLIteHandller clas
    private SQLiteHandler handler = new SQLiteHandler(getActivity());
    //Progress dialog
    ProgressDialog progressDialog;
    Button btnadd,btnCancel;
    EditText Ecategory;






    public ExpenseCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_expensecategory,container,false);
        btnadd = (Button) v.findViewById(R.id.btnadd);
        btnCancel = (Button) v.findViewById(R.id.btnCancel);
        btnadd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        Ecategory = (EditText) v.findViewById(R.id.catName);


        handler = new SQLiteHandler(getActivity());
        try {
            handler.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        handler= new SQLiteHandler(this.getActivity());


        final ArrayList<HashMap<String, String>> category_list =  handler.getAll_Ecategory();
        //
        if(category_list.size() !=0 ) {
            //Set the User Array list in ListView
            final ListAdapter adapter= new SimpleAdapter(getActivity(),
                    category_list, (R.layout.view_category_entries), new String[]{"expense_cat_id", "expense_cat_name"}, new int[]{R.id.catId, R.id.listCatName});
            final ListView myList = (ListView) v.findViewById(android.R.id.list);
            myList.setAdapter(adapter);

            //list on click listener
           myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                   String name = parent.getItemAtPosition(position).toString();


                   //  String name = (String) adapter.getItem((int) id).toString();
                   //String val = String.valueOf(position);
                   Log.d("You Clicked", name);
                   //loadExpenseClass();

                   //Toast.makeText(getActivity().getApplicationContext(),
                   //    "You clicked"+name,
                   //    "you clickded"+adapter.getItem(position).toString(),
                   //   Toast.LENGTH_LONG).show();

                   //TODO Bundle the results AND PAass them to the ExpenseActivity class

                   /*ExpenseCategoryFragment fragment = new ExpenseCategoryFragment();
                  Bundle category = new Bundle();
                   category.putString("expenseCategory", name);
                    fragment.setArguments(category);*/


               }
           });

            //Display Sync status of SQLite DB
            Toast.makeText(getActivity().getApplicationContext(), handler.getSyncStatus(), Toast.LENGTH_LONG).show();
       }
        //Initialize Progress Dialog properties
      /*  progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Synching SQLite Data with Remote MySQL DB. Please wait...");
        progressDialog.setCancelable(false);*/

        return v;
    }
    //TODO `` This method will take the user back once they
    // TODO``have done a expense_category selection




   /* public void syncSQLiteMySQLDB(){
        //Create AsycHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        ArrayList<HashMap<String, String>> categoryList =  handler.getAll_Ecategory();
        if(categoryList.size()!=0){
            if(handler.dbSyncCount() != 0){
                progressDialog.show();
                params.put("ecategoryJSON", handler.composeJSONfromSQLite());
                client.post(AppConfig.URL_ECATEGORY,params ,new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        System.out.println(response);
                        progressDialog.hide();
                        try {
                            JSONArray arr = new JSONArray(response);
                            System.out.println(arr.length());
                            for(int i=0; i<arr.length();i++){
                                JSONObject obj = (JSONObject)arr.get(i);
                                System.out.println(obj.get("id"));
                                System.out.println(obj.get("status"));
                                handler.updateSyncStatus(obj.get("id").toString(),obj.get("status").toString());
                            }
                            Toast.makeText(getActivity(), "DB Sync completed!", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            Toast.makeText(getActivity(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {
                        // TODO Auto-generated method stub
                        progressDialog.hide();
                        if(statusCode == 404){
                            Toast.makeText(getActivity(), "Requested resource not found", Toast.LENGTH_LONG).show();
                        }else if(statusCode == 500){
                            Toast.makeText(getActivity(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getActivity(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }else{
                Toast.makeText(getActivity(), "SQLite and Remote MySQL DBs are in Sync!", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getActivity(), "No data in SQLite DB, please do enter User name to perform Sync action", Toast.LENGTH_LONG).show();
        }
    }*/

    public void cancelAddCategory(View view) {
        this.callHomeActivity(view);
    }

    /**
     * Navigate to Home Screen
     * @param view
     */
    public void callHomeActivity(View view) {
        Intent objIntent = new Intent(getActivity(),
                ExpenseCategoryFragment.class);
        startActivity(objIntent);
    }

    @Override
    public void onClick(View v) {
        SQLiteHandler handler = new SQLiteHandler(getActivity());
       switch(v.getId()){
           case R.id.btnadd:
               buttonAddMethod();

               break;
           case R.id.btnCancel:
               Intent intent = new Intent(getContext(),MainActivity.class);
               startActivity(intent);
               break;


       }
    }
    //method to add data to expense category list
    private  void  buttonAddMethod(){
        HashMap<String, String> queryValues = new HashMap<String, String>();
        queryValues.put("expense_cat_name", Ecategory.getText().toString());
        if (Ecategory.getText().toString() != null
                && Ecategory.getText().toString().trim().length() != 0) {
            handler.insert_Ecategory(queryValues);

            Ecategory.setText("");
        } else {
            Toast.makeText(getContext(), "Please enter Category name",
                    Toast.LENGTH_LONG).show();
        }
    }
}//endclass

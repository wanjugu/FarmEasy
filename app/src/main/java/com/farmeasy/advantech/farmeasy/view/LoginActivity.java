package com.farmeasy.advantech.farmeasy.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.farmeasy.advantech.farmeasy.R;
import com.farmeasy.advantech.farmeasy.controller.AppConfig;
import com.farmeasy.advantech.farmeasy.controller.AppController;
import com.farmeasy.advantech.farmeasy.controller.SQLiteHandler;
import com.farmeasy.advantech.farmeasy.controller.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mugo-James on 1/27/2016.
 */
public class LoginActivity extends Activity{
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText txtEmail,txtpass;
    private ProgressDialog pdialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = (EditText) findViewById(R.id.email);
        txtpass = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        //progress dialog
        pdialog = new ProgressDialog(this);
        pdialog.setCancelable(false);

        //SQLite database Handler
        db = new SQLiteHandler(getApplicationContext());

        //session Manager
        session = new SessionManager(getApplicationContext());

        //check if the users is already logged in
        if(session.isLoggedIn()){
            //if user is logged in take him to the main screen
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        //Loggin button Click event
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String password = txtpass.getText().toString();

                //check for empty fields in the form
                if(!email.isEmpty() && !password.isEmpty()){
                    //login users
                    checkLogin(email,password);

                }else{
                    //prompt user for credentials
                    Toast.makeText(getApplicationContext(),"Please Provide all  Credentials",
                            Toast.LENGTH_LONG).show();

                }
            }
        });//end btnLogin listener

        //Link To Register screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(in);
                finish();
            }
        });
    }//end onCreate method

//Function to verify login details in MysqlDatabase

    private void checkLogin(final String email, final String password) {

        //Tag used to cancel the request
        String tag_req = "req_login";

        pdialog.setMessage("logging in....");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response; " + response.toString());
                hideDialog();

                try {
                    JSONObject jobj = new JSONObject(response);
                    boolean error = jobj.getBoolean("error");

                    //check for error node in JSON
                    if (!error) {
                        //user successfully logged in
                        //create login session
                        session.setLogin(true);


                        //let store the data now in the local data base
                        String uid = jobj.getString("uid");

                        JSONObject user = jobj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user.getString("created_at");

                        //inserting row in user table
                        db.addUser(name, email, uid, created_at);

                        //Launch main activity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }//end if
                    else {
                        //Error in login. get the error message
                        String errorMsg = jobj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Json error" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }){
            protected Map<String,String> getParams(){
                //posting parameters to login url

                Map<String,String> params = new HashMap<String,String>();
                params.put("email",email);
                params.put("password",password);

                return  params;

            }
        };
        //add request to request queue
        AppController.getInstance().addToRequestQueue(strReq,tag_req);
        }

        private void showDialog(){
            if (!pdialog.isShowing())
                pdialog.show();
        }
    private void hideDialog() {
        if (pdialog.isShowing())
            pdialog.dismiss();
    }
    }


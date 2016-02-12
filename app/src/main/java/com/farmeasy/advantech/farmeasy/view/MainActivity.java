package com.farmeasy.advantech.farmeasy.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.farmeasy.advantech.farmeasy.R;
import com.farmeasy.advantech.farmeasy.controller.SQLiteHandler;
import com.farmeasy.advantech.farmeasy.controller.SessionManager;

public class MainActivity extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private SessionManager session;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //database handler
        db = new SQLiteHandler(getApplicationContext());

        //session manager
        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()){
            logoutUser();
        }




        //initialize Navigation view
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //set navigationview listener
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // check if item state selected is true
                if(item.isChecked())
                    item.setChecked(false);
                else item.setChecked(true);

                //closing the drawer on item click
                drawerLayout.closeDrawers();

                //check to see what item is clicked and perform the appropriate action
                switch(item.getItemId()){

                    case R.id.managesale:
                        SalesFragment salesFragment = new SalesFragment();
                        android.support.v4.app.FragmentTransaction sft = getSupportFragmentManager().beginTransaction();
                        sft.replace(R.id.frame,salesFragment);
                        sft.commit();
                        return true;

                    case R.id.manageaccounts:
                        AccountTypeFragment accountTypeFragment = new AccountTypeFragment();
                        android.support.v4.app.FragmentTransaction aft = getSupportFragmentManager().beginTransaction();
                        aft.replace(R.id.frame,accountTypeFragment);
                        aft.commit();
                        return true;


                    case R.id.manageproduct:
                        ProductFragment productFragment = new ProductFragment();
                        android.support.v4.
                                app.FragmentTransaction pft = getSupportFragmentManager().beginTransaction();
                        pft.replace(R.id.frame, productFragment);
                        pft.commit();
                        return true;

                    case R.id.productcategory:
                        ProductCategory_Fragment productCategory_fragment = new ProductCategory_Fragment();
                        android.support.v4.app.FragmentTransaction pcft = getSupportFragmentManager().beginTransaction();

                        pcft.replace(R.id.frame, productCategory_fragment);
                        pcft.commit();
                        return true;


                    case R.id.manageexpense:
                        ExpenseFragment expenseFragment = new ExpenseFragment();
                        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                        ft.replace(R.id.frame,expenseFragment);
                        ft.commit();
                        return true;

                    case R.id.expensecategory:
                        ExpenseCategoryFragment expenseCategoryFragment = new ExpenseCategoryFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();

                        fragmentTransaction1.replace(R.id.frame,expenseCategoryFragment);
                        fragmentTransaction1.commit();

                        return true;

                    case R.id.salesreport:
                        Intent intent = new Intent(MainActivity.this,ReportActivity.class);
                        startActivity(intent);

                      /* SalesReportFragment salesReportFragment = new SalesReportFragment();
                        android.support.v4.app.FragmentTransaction sft1 = getSupportFragmentManager().beginTransaction();

                        sft1.replace(R.id.frame,salesReportFragment);
                        sft1.commit();*/
                        return true;

                    case R.id.settings:
                        Toast.makeText(getApplicationContext(),"Settings",
                                Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.logout:
                        logoutUser();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext()," Check out Later!!Module  Under Construction!",
                                Toast.LENGTH_SHORT).show();
                        return  true;
                }
            }
        });//end switch statement

        //initialize drawer layout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,toolbar,R.string.opendrawer,R.string.closedrawer){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        //setting the actionbar toogle to draw layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //call sync to show icon
        actionBarDrawerToggle.syncState();
    }
    //TODO method to logout users
    public void logoutUser(){
        session.setLogin(false);
        //db.deleteUsers();


        //launch the login activity
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

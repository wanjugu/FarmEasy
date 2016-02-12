package com.farmeasy.advantech.farmeasy.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.farmeasy.advantech.farmeasy.R;
import com.farmeasy.advantech.farmeasy.controller.ReportCardAdapter;
import com.farmeasy.advantech.farmeasy.model.ReportCardModel;

import java.util.ArrayList;


/**
 * Created by james-mugo on 2/10/2016.
 */
public class ReportActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private CardView cardView;
    private ArrayList<ReportCardModel> reporttype;
    ReportCardAdapter reportCardAdapter;

    private  RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initControlls();


    }

    public void openMe(){
        Intent in = new Intent(ReportActivity.this,SalesReportFragment.class);
        startActivity(in);
    }

    private void initControlls() {
        recyclerView = (RecyclerView) findViewById(R.id.rv);

        final String[] reports = {"Total Sales Report", "Product Sale Report","Payment Reports"};
        final int[] icons = {
                R.drawable.sales,
                R.drawable.product,
                R.drawable.paid};

        reporttype = new ArrayList<ReportCardModel>();

        for(int i = 0; i < reports.length; i++){
            ReportCardModel reportCardModel = new ReportCardModel();

            reportCardModel.setTitle(reports[i]);
            reportCardModel.setPhoto(icons[i]);
            reporttype.add(reportCardModel);
        }//endfor loop

        recyclerView.setHasFixedSize(true);//recycler view size not changing
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new ReportCardAdapter(reporttype);

        //set the adapter
        recyclerView.setAdapter(mAdapter);
    }

    public void loadFragment(){
        reportCardAdapter = new ReportCardAdapter(this);
        SalesReportFragment salesFragment = new SalesReportFragment();
        FragmentTransaction sft = getSupportFragmentManager().beginTransaction();
        sft.replace(R.id.fragment_container, salesFragment);
        sft.commit();

    }

}

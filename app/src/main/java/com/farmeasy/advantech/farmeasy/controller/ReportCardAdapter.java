package com.farmeasy.advantech.farmeasy.controller;



import android.app.*;
import android.os.Bundle;
import android.content.Context;

import android.content.Intent;
import android.support.v4.app.*;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.farmeasy.advantech.farmeasy.R;
import com.farmeasy.advantech.farmeasy.model.ReportCardModel;
import com.farmeasy.advantech.farmeasy.view.MainActivity;
import com.farmeasy.advantech.farmeasy.view.ReportActivity;
import com.farmeasy.advantech.farmeasy.view.SalesReportFragment;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Advantech Ltd on 2/10/2016.
 */
public class ReportCardAdapter extends RecyclerView.Adapter<ReportCardAdapter.ViewHolder> {
    private static ArrayList<ReportCardModel> dataset;
    private AdapterView.OnItemClickListener listener;
    //ReportCardAdapter reportCardAdapter = new ReportCardAdapter(this);

    private Context context;
    private Fragment fragment;

   ReportActivity reportActivity = new ReportActivity();


    SalesReportFragment salefragment;



    public ReportCardAdapter(Context context,ArrayList<ReportCardModel> report_type,ReportActivity reportActivity) {
        dataset = report_type;
        this.context =context;
        this.reportActivity = reportActivity;
    }

    public ReportCardAdapter(Context context) {
        this.context= context;

        this.reportActivity = reportActivity;

    }
    public ReportCardAdapter(Fragment fragment) {
        this.fragment = fragment;
    }



    public ReportCardAdapter(ArrayList<ReportCardModel> reporttype) {
        dataset = reporttype;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemLayoutView = LayoutInflater.from(
                viewGroup.getContext()).inflate(R.layout.activity_report_card, viewGroup, false);

        salefragment = new SalesReportFragment();
        //create view holder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ReportCardModel rcm = dataset.get(position);

        viewHolder.tvreporttype.setText(rcm.getTitle());
        viewHolder.iconview.setImageResource(rcm.getPhoto());
        viewHolder.reportCardModel = rcm;



    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder {

        private FragmentManager manager;
        public ViewHolder(View v,FragmentManager manger){
            super(v);
            this.manager = manger;

        }
        public TextView tvreporttype;
        public ImageView iconview;

        public ReportCardModel reportCardModel;
        public ViewHolder(final View itemView) {
            super(itemView);

            tvreporttype = (TextView) itemView.findViewById(R.id.tvReportType);
            iconview = (ImageView) itemView.findViewById(R.id.iconId);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                                  String Tittle = reportCardModel.getTitle().toString();
                   String salereport = "total sales report";
                   Toast.makeText(v.getContext(), "View Report!! " + Tittle, Toast.LENGTH_LONG).show();

                    reportActivity.loadFragment();



                }
            });

        }
    }

}

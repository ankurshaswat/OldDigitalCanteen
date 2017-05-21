package com.example.jsr268client;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jsr268client.R;

import java.util.List;

/**
 * Created by abhijeet anand on 8/8/2016.
 */

public class AccountsDisplay extends BaseAdapter{


    private Context context;
    private List<sale> fooditems;
    public static Double tot=0.0;



    public AccountsDisplay(Context context, List<sale> fooditems) {
        this.context = context;
        this.fooditems = fooditems;
    }

    @Override
    public int getCount() {
        return fooditems.size();
    }

    @Override
    public Object getItem(int position) {
        return fooditems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //View v=View.inflate(context, R.layout.men,null);



        View v=View.inflate(context, R.layout.sal,null);
        TextView v1=(TextView)v.findViewById(R.id.t1);
        TextView v2=(TextView)v.findViewById(R.id.t2);
        TextView v3=(TextView)v.findViewById(R.id.t3);
        TextView v4=(TextView)v.findViewById(R.id.t4);
        v1.setText(this.fooditems.get(position).getName());
        Double cp=this.fooditems.get(position).getCpi();
        int q=this.fooditems.get(position).getQuan();
        v2.setText(Double.toString(cp));
        v3.setText(Integer.toString(q));
         Double total=cp*q;
        tot=total;
        v4.setText(Double.toString(tot));

        return v;
    }

    public void calculate()
    {
        Double xx=0.0;

        for(int i=0;i<MainPage.sizeof;i++)
        {
            xx+=fooditems.get(i).getCpi()*MainPage.selection[i];
        }
        MainPage.money=xx;
        MainPage.moneyButton.setText("Rs." +Double.toString(MainPage.money));
    }

}

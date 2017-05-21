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

public class menucreater extends BaseAdapter{


    private Context context;
    private List<items> fooditems;
    private MenuItems mii;


    public menucreater(Context context, List<items> fooditems) {
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

        View v = convertView;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.men, null);
            ((Button)v.findViewById(R.id.button8)).setText("0");

        }
        else
        {
//            String ss="";
//            for(int i=0;i<MainPage.selection.length;i++)
//            {
//                ss+=Integer.toString(MainPage.selection[position]);
//            }
//            Toast.makeText(context,ss,Toast.LENGTH_SHORT).show();
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.men, null);
            Button xx=((Button)v.findViewById(R.id.button8));
            xx.setText(Integer.toString(MainPage.selection[position]));
            Button menn=(Button)v.findViewById(R.id.m1);
            Integer xxx=MainPage.selection[position];
            //Toast.makeText(context,Integer.toString(position)+"=>" +Integer.toString(MainPage.selection[position]),Toast.LENGTH_SHORT).show();
            if(MainPage.selection[position]>0)
            {
                menn.setBackgroundResource(R.drawable.style3);
            }
            else
            {
                menn.setBackgroundResource(R.drawable.style5);
            }


        }


        final Button b1=(Button)v.findViewById(R.id.m1);
        final Button b2=(Button)v.findViewById(R.id.button8);
        b1.setText(fooditems.get(position).getName() +" "+Double.toString(fooditems.get(position).getCpi()) );
        //b2.setText(String.valueOf(0));
        Button MenBtn = (Button)v.findViewById(R.id.m1);

        Button AddBtn=(Button)v.findViewById(R.id.add1);
        Button SubBtn=(Button)v.findViewById(R.id.sub2);
       // b2.setText("5");

        //int size=this.getCount();

//        sel=new int[size];
//        int i;
//        for(i=0;i<size;i++)
//        {
//            sel[i]=0;
//        }

       // b2.setText(Integer.toString(sel[position]));
        MenBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {





                //do something


//                String s1;
//
//                 s1 =b2.getText().toString();
//
//                Integer x1=Integer.parseInt(s1);
                Integer x1=MainPage.selection[position];

                if(x1==0)
                {
                    //sel[position]++;
                    MainPage.selection[position]++;
                    b2.setText("1");
                    view.setBackgroundResource(R.drawable.style3);
                }
                else
                {
                    //sel[position]=0;
                    MainPage.selection[position]=0;
                    b2.setText("0");
                    view.setBackgroundResource(R.drawable.style5);
                }
               // Toast.makeText(context,Integer.toString(MainPage.selection[position]),Toast.LENGTH_SHORT).show();
               calculate();
            }
        });


        AddBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {


                //do something


//                String s1;
//                if(b2!=null){
//                    s1 =b2.getText().toString();}
//                else
//                    s1="45";
               // Integer x1=Integer.parseInt(s1);
                Integer x1=MainPage.selection[position];
                if(x1==0)
                {
                    //sel[position]++;
                    b2.setText("1");
                    MainPage.selection[position]++;
                    b1.setBackgroundResource(R.drawable.style3);
                }
                else
                {
                    //sel[position]++;
                    MainPage.selection[position]++;
                    String sf=Integer.toString(x1+1);
                    b2.setText(sf);

                }
                calculate();
               // Toast.makeText(context,Integer.toString(MainPage.selection[position]),Toast.LENGTH_SHORT).show();

            }
        });

        SubBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {


                //do something


//                String s1;
//                if(b2!=null){
//                    s1 =b2.getText().toString();}
//                else
//                    s1="45";
//                Integer x1=Integer.parseInt(s1);
                Integer x1=MainPage.selection[position];
                if(x1==0)
                {

                }
                else if(x1==1)
                {
                    //sel[position]--;
                    b2.setText("0");
                    MainPage.selection[position]--;
                    b1.setBackgroundResource(R.drawable.style5);
                }
                else
                {
                    //sel[position]--;
                    MainPage.selection[position]--;
                    String sf1=Integer.toString(x1-1);
                    b2.setText(sf1);
                }
                calculate();
                //Toast.makeText(context,Integer.toString(MainPage.selection[position]),Toast.LENGTH_SHORT).show();

            }
        });




        return v;
    }

    public void calculate()
    {
        double xx=0;

        for(int i=0;i<MainPage.sizeof;i++)
        {
            xx+=fooditems.get(i).getCpi()*MainPage.selection[i];
        }
        MainPage.money=xx;
        MainPage.moneyButton.setText("Rs." +Double.toString(MainPage.money));
    }

}

package com.example.jsr268client;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

public class mycustomadapter extends BaseAdapter{


    private Context context;
    private List<items> fooditems;
    private MenuItems mii;

    public mycustomadapter(Context context, List<items> fooditems) {
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

        View v=View.inflate(context, R.layout.test,null);
        TextView tx1=(TextView)v.findViewById(R.id.it_name);
        TextView tx2=(TextView)v.findViewById(R.id.it_cpi);
        tx1.setText(fooditems.get(position).getName());
        tx2.setText(String.valueOf(fooditems.get(position).getCpi()));

        Button deleteBtn = (Button)v.findViewById(R.id.rmv);

        deleteBtn.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {




                //do something
                final Integer pos=position;
                mii=new MenuItems(context);
                boolean y=mii.deleteItem(fooditems.get(pos).getName());
                if(y==true) {
                    Toast.makeText(context, "Item Successfully Deleted", Toast.LENGTH_SHORT).show();
                    fooditems.remove(fooditems.get(position));
                    notifyDataSetChanged();
                }
                else
                    Toast.makeText(context,"Error: Item could not be deleted",Toast.LENGTH_SHORT).show();



                return true;
            }
        });



        return v;
    }

}

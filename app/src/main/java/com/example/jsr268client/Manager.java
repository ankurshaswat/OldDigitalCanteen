package com.example.jsr268client;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.database.Cursor;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Manager extends Activity {

    private MenuItems mi;
    private mycustomadapter dataAdapter;
    private ListView lists;
    private List<items> itemlist;

    private EditText input1;
    private EditText input2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        mi = new MenuItems(this);
        //mi.deleteItem("tea");
        //boolean x =mi.emptytable();
        //Toast.makeText(getApplicationContext(),"bingo",Toast.LENGTH_SHORT).show();

        Cursor cur=mi.getAllData();
        String s=Integer.toString(cur.getCount());
       // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
        String s1=Integer.toString(cur.getCount());

        //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
//        String[] names={"abhijeet","adarsh"};
//
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
//                Manager.this,
//                android.R.layout.simple_list_item_1,
//                names );


        itemlist=new ArrayList<>();
        while(cur.moveToNext())
        {
            Integer iid=cur.getInt(0);
            String st1=cur.getString(1);
            Double st2=cur.getDouble(2);
            itemlist.add(new items(iid,st1,st2));
        }


        dataAdapter=new mycustomadapter(getApplicationContext(),itemlist);


        ListView listView = (ListView) findViewById(R.id.listView1);
// Assign adapter to ListView
        listView.setAdapter(dataAdapter);


    }


    public void AddItem(View view)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Item");



// Set up the input
        input1 = new EditText(this);
        input1.setHint("Enter Name of Item" );

        input1.setHeight(100);
        input2= new EditText(this);
        input2.setHint("Enter Cost per Item" );
        input1.setHeight(100);


// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        //input2.setInputType(InputType.TYPE_CLASS_NUMBER);
        input2.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(input1);

        input2.setPadding(0,50,0,0);

        layout.addView(input2);

        builder.setView(layout);
        //builder.setView(input1);
        //builder.setView(input2);






// Set up the buttons


        builder.setPositiveButton("Authenticate", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String x=input1.getText().toString();
                Double y=Double.parseDouble(input2.getText().toString());

                //userdata.updateinfo(fin,"nochange",code);
                boolean xx=mi.InsertItem(x,y);

                if(xx==true)
                {
                    Toast.makeText(getApplicationContext(),"Item added successfully",Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Error: Item not add",Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });






        builder.show();

    }



}

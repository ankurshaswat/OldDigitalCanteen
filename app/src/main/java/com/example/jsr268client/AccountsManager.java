package com.example.jsr268client;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by abhijeet anand on 7/5/2016.
 */
public class AccountsManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="account.db";
    public final String USERS_TABLE_NAME="sale";






    public AccountsManager(Context context)
    {
        super(context,DATABASE_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1="create table if not exists sale(_ID integer,name text, cpi real,quantity integer,dateof date, backup integer )";

        db.execSQL(query1);



    }

    @Override
    public void onOpen(SQLiteDatabase db)
    {
        String query1="create table if not exists sale(_ID integer ,name text, cpi real,quantity integer, dateof Date, backup integer )";

        db.execSQL(query1);

        // String query2="create table if not exists Transactions(id integer primary key autoincrement,emp_code text,Transaction text,Previous integer,Amount integer,Final integer,Date text)";
        //db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists sale");
        //db.execSQL("Drop table if exists Transactions");
        onCreate(db);

    }
    public boolean insert(Integer id,String name, double cpi,int quan, String date )
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues newcontent=new ContentValues();
        newcontent.put("_ID",id);
        newcontent.put("name",name);
        newcontent.put("cpi",cpi);
        newcontent.put("quantity",quan);
        newcontent.put("backup",0);
//        Date dateObject=new Date();
//        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String dd=sdf.format(dateObject);

        Date d=new Date();

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String dd=sdf.format(d);

       // String dd=sdf.format(d);

        newcontent.put("dateof", dd);

        long result=db.insert("sale",null,newcontent);
        if(result==-1)
        {
            return false;
        }
        else{

            return true;}
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        try{Cursor cur=db.rawQuery("Select * from sale",null);
            return cur;}
        catch(SQLiteException e)
        {
            return null;
        }
    }


    public Cursor getAllData2()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        try{Cursor cur=db.rawQuery("Select * from sale where backup=0",null);
            return cur;}
        catch(SQLiteException e)
        {
            return null;
        }
    }

    public Cursor getAllData2(String st,String en)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        Date d1=new Date();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
             d1=sdf.parse(st);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dd1=sdf.format(d1);

        Date d2=new Date();
        try {
            d2=sdf.parse(en);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dd2=sdf.format(d2);

        Cursor cur=db.rawQuery("Select * from sale where dateof <= ? and dateof >= ?",new String[]{dd1,dd2} );
            return cur;

    }





    public void deleteall()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("delete from sale");
    }

    public void updatebackup()
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues newValues = new ContentValues();
        newValues.put("backup", 1);



        String[] args = new String[]{"0"};
        db.update("sale", newValues, "backup=?", args);
    }








}

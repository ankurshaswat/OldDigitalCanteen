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
import java.util.ArrayList;

/**
 * Created by abhijeet anand on 7/5/2016.
 */
public class MenuItems extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="Menu.db";
    public final String TABLE_NAME="menu";







    public MenuItems(Context context)
    {
        super(context,DATABASE_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1="create table if not exists menu(id integer primary key autoincrement,item text, cpi real)";

        db.execSQL(query1);



    }

    @Override
    public void onOpen(SQLiteDatabase db)
    {
        String query1="create table if not exists menu(id integer primary key autoincrement,item text, cpi real)";

        db.execSQL(query1);

        // String query2="create table if not exists Transactions(id integer primary key autoincrement,emp_code text,Transaction text,Previous integer,Amount integer,Final integer,Date text)";
        //db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists Users");
        //db.execSQL("Drop table if exists Transactions");
        onCreate(db);

    }
    public boolean InsertItem(String name,Double cpi)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues newcontent=new ContentValues();
        newcontent.put("item",name);
        newcontent.put("cpi",cpi);


        long result=db.insert("menu",null,newcontent);
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
        try{Cursor cur=db.rawQuery("Select * from menu",null);
            return cur;}
        catch(SQLiteException e)
        {
            return null;
        }
    }


   
    public void updateinfo(String name,Double cpi)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues newValues = new ContentValues();
        newValues.put("item", name);
        newValues.put("cpi",cpi);


        String[] args = new String[]{name};
        db.update("Users", newValues, "item=?", args);

    }


    public boolean emptytable()
    {
        int res=0;
        SQLiteDatabase db=this.getWritableDatabase();
        res = db.delete(TABLE_NAME, null , null);
        return res>0;

    }
    public boolean deleteItem(String name)
    {
        int res=0;
        SQLiteDatabase db=this.getWritableDatabase();
        res=db.delete(TABLE_NAME, "item" + "=?",
                new String[] { name }); // KEY_ID= id of row and third parameter is argument.

        if(res==0) return false;
        else return true;

    }





}

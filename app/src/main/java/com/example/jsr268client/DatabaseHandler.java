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
public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="Digita.db";
    public final String USERS_TABLE_NAME="Users";

    public final String USERS_COLUMN_ID="_ID";
    public final String USERS_COLUMN_ATR="ATR";
    public final String USERS_COLUMN_EMPLOYEE_CODE="Employee_code";
    public final String USERS_COLUMN_NAME="Name";
    public final String USERS_COLUMN_BALANCE="Balance";
    public final String USERS_COLUMN_NEXTDATE="NextDate";


    public final String TRANSACTIONS_TABLE_NAME="Transactions";

    public final String TRANSACTIONS_COLUMN_ID="id";
    public final String TRANSACTIONS_COLUMN_EMPLOYEE_CODE="emp_code";
    public final String TRANSACTIONS_COLUMN_TRANSACTION="Transaction";
    public final String TRANSACTIONS_COLUMN_PREVIOUS="Previous";
    public final String TRANSACTIONS_COLUMN_AMOUNT="Amount";
    public final String TRANSACTIONS_COLUMN_FINAL="Final";
    public final String TRANSACTIONS_COLUMN_DATE="Date";

    public DatabaseHandler(Context context)
    {
        super(context,DATABASE_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1="create table if not exists Users(_ID integer primary key autoincrement,ATR text, Employee_code text,Name text,Balance real,NextDate text )";

        db.execSQL(query1);



    }

    @Override
    public void onOpen(SQLiteDatabase db)
    {
        String query1="create table if not exists Users(_ID integer primary key autoincrement,ATR text, Employee_code text,Name text,Balance real,NextDate text )";

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
    public boolean InsertUser(String atr,String code,String name,double balance, String nextdate )
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues newcontent=new ContentValues();
        newcontent.put("ATR",atr);
        newcontent.put("Employee_code",code);
        newcontent.put("Name",name);
        newcontent.put("Balance",balance);
        newcontent.put("NextDate",nextdate);

        long result=db.insert("Users",null,newcontent);
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
        try{Cursor cur=db.rawQuery("Select * from Users",null);
        return cur;}
        catch(SQLiteException e)
        {
            return null;
        }
    }
    public Cursor checkUserwithcode(String code)
    {
        SQLiteDatabase db=this.getWritableDatabase();

            Cursor cur = db.rawQuery("Select * from Users where Employee_code=?", new String[]{code});
            return cur;

    }

    public Cursor checkUserwithATR(String atr)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor cur = db.rawQuery("Select * from Users where ATR=?", new String[]{atr});
        return cur;

    }
    public void updateinfo(double finale,String dat,String empcode)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues newValues = new ContentValues();
        newValues.put("Balance", finale);
        newValues.put("NextDate",dat);


        String[] args = new String[]{empcode};
        db.update("Users", newValues, "Employee_code=?", args);

    }

    public int findpeople(String date)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cur = db.rawQuery("Select * from Users where NextDate=?", new String[]{date});
        return (cur.getCount());



    }
    public int addBalance(double money ,double pre_money,String empcode)
    {


        double final_money=money+pre_money;
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues newValues = new ContentValues();

        newValues.put("Balance", final_money);



        String[] args = new String[]{empcode};
        int xa=db.update("Users", newValues, "Employee_code=?", args);
        return xa;
    }





}

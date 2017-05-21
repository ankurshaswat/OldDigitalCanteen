package com.example.jsr268client;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by abhijeet anand on 7/5/2016.
 */
public class DataBaseHandler1 extends SQLiteOpenHelper{

    public static final String DATABASE_NAME="Digita2.db";

    public DataBaseHandler1(Context context)

    {
        super(context,DATABASE_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query2="create table Transactions(id integer primary key autoincrement,emp_code text,details text,Previous real,Amount real,Final real,Date text)";
        db.execSQL(query2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists Transactions");

        onCreate(db);

    }
    public boolean InsertTrans(String emp_code,String details,double prev,double amnt,double final_amnt ,String datetime)
    {

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues newcontent=new ContentValues();
        newcontent.put("emp_code",emp_code);
        newcontent.put("details",details);
        newcontent.put("Previous",prev);
        newcontent.put("Amount",amnt);
        newcontent.put("Final",final_amnt);
        newcontent.put("Date",datetime);
        db.insert("Transactions",null,newcontent);
        return true;
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cur=db.rawQuery("Select * from Transactions",null);
        return cur;
    }

}

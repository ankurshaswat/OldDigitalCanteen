package com.example.jsr268client;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Accounts extends Activity {

    Button dateselector;
    Button dateselector2;
    static final int DIALOG_ID = 0;
    int year_x, month_x, date_x, newdate, newmonth;
    TextView strt;
    TextView end;
    Button gen;
    int x=0;
    AccountsManager acc;
    AccountsDisplay datapter;
    String stdate;
    String enddate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        //showDialogOnButtonClick1();
        //showDialogOnButtonClick2();
        strt=(TextView)findViewById(R.id.strd);
        end=(TextView)findViewById(R.id.endd);
        dateselector2 = (Button) findViewById(R.id.endbutton);
        dateselector = (Button) findViewById(R.id.startbutton);
        acc=new AccountsManager(this);
        Date dd=new Date();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        stdate=sdf.format(dd);

        Date referenceDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(referenceDate);
        c.add(Calendar.MONTH, -3);
        Date datee=c.getTime();
        enddate=sdf.format(datee);

        strt.setText(stdate);
        end.setText(enddate);


    }



    public void showDialogOnButtonClick1(View view) {

        x=1;



                        showDialog(DIALOG_ID);



    }
    public void showDialogOnButtonClick2(View view) {
        x=2;




                        showDialog(DIALOG_ID);

    }



    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID)
            return new DatePickerDialog(this, dpickerlistener, year_x, month_x, date_x);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerlistener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear + 1;
            date_x = dayOfMonth;

           // Toast.makeText(Accounts.this, "You have selected  " + year_x + "-" + month_x + "-" + date_x, Toast.LENGTH_SHORT).show();
            String nedate = year_x + "-" + month_x + "-" + date_x;
            Toast.makeText(getApplicationContext(),Integer.toString(x),Toast.LENGTH_SHORT).show();
            if(x==1)
            {strt.setText(nedate);
            stdate=year_x+"-"+month_x+"-"+date_x;
            }
            else if(x==2) {
                end.setText(nedate);
                enddate=year_x+"-"+month_x+"-"+date_x;
            }


        }
    };


    public void gen(View view)
    {
        Map<Integer, sale> dict = new HashMap<>();
        Integer x=0;
        Cursor a=acc.getAllData2(stdate,enddate);
        while(a.moveToNext())
        {
            x++;
            Integer id=a.getInt(0);
            String what=a.getString(1);
            Double per=a.getDouble(2);
            Integer qu=a.getInt(3);
            String dat=a.getString(4);
            if(dict.containsKey(id))
            {
                dict.get(id).incr(qu);
            }
            else
            {
                sale s=new sale(what,per,qu);
                dict.put(id,s);
            }

        }

       List<sale> sa=new ArrayList<>();
        for(Integer key:dict.keySet())
        {
            sa.add(dict.get(key));
        }
        datapter=new AccountsDisplay(getApplicationContext(),sa);
        Toast.makeText(getApplicationContext(),Integer.toString(dict.size())+"Success",Toast.LENGTH_SHORT).show();
        ListView listView = (ListView) findViewById(R.id.listView4);
// Assign adapter to ListView
        listView.setAdapter(datapter);


    Button totalButton=(Button)findViewById(R.id.button10);
       Double tot=0.0;
        for(int i=0;i<sa.size();i++)
        {
            tot+=(sa.get(i).getCpi()*sa.get(i).getQuan());
        }
        String ss=Double.toString(tot);
        totalButton.setText(ss);

    }

}

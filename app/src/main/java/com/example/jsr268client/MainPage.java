package com.example.jsr268client;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainPage extends Activity {

    private TextView tex;
    private String atr1;
    private TextView next_date;
    private TextView accountbalance;
    Button dateselector;

    Button add_amount;
    int year_x, month_x, date_x, newdate, newmonth;
    static final int DIALOG_ID = 0;
    static final int NEWDIALOG_ID = 0;
    private String name;
    private String code;
    private int b1 = 0;
    private int b2 = 0;
    private int b3 = 0;
    private int b4=0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button sub;
    public static Double money;
    public static Button moneyButton;
    private String bal;
    private ProgressDialog pDialog;
    private DatabaseHandler userdata;
    private DataBaseHandler1 transdata;
    private String nextdate;
    private EditText input;

    private int quantity1;
    private int quantity2;
    private int quantity3;
    private int quantity4;
    private Button quan1;
    private Button quan2;
    private Button quan3;
    private Button quan4;

    private AccountsManager a;
    private MenuItems mi;
    private menucreater dataAdapter;
    private ListView lists;
    private List<items> itemlist;
    public static int[] selection;
        public static int sizeof;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        tex = (TextView) findViewById(R.id.co);
        atr1 = getIntent().getExtras().getString("atr");
        tex.setText(atr1);
        pDialog = new ProgressDialog(this);
        name = getIntent().getExtras().getString("name").toString();
        code = getIntent().getExtras().getString("code").toString();
        bal = getIntent().getExtras().getString("bal").toString();
        nextdate = getIntent().getExtras().getString("date").toString();
        next_date = (TextView) findViewById(R.id.visiting_date);
        accountbalance = (TextView) findViewById(R.id.account_balance);

        accountbalance.setText( "Rs. " + bal );
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        date_x = cal.get(Calendar.DATE);
        newdate = cal.get(Calendar.DATE) + 1;
        newmonth = cal.get(Calendar.MONTH) + 1;
        next_date.setText(nextdate);
        tex.setText(name);
        moneyButton=(Button)findViewById(R.id.money);
        moneyButton.setText("Rs. 0");
        money=0.0;
        a=new AccountsManager(this);

        showDialogOnButtonClick();

        mi = new MenuItems(this);
        Cursor cur=mi.getAllData();
        itemlist=new ArrayList<>();
        while(cur.moveToNext())
        {
            Integer x=cur.getInt(0);
            String st1=cur.getString(1);
            Double st2=(cur.getDouble(2));
            itemlist.add(new items(x,st1,st2));
        }
         sizeof=itemlist.size();
        selection=new int[sizeof];
        for(int i=0;i<sizeof;i++)
        {
            selection[i]=0;
        }


        dataAdapter=new menucreater(getApplicationContext(),itemlist);


         lists = (ListView) findViewById(R.id.listView2);
// Assign adapter to ListView
        lists.setAdapter(dataAdapter);
        lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"fa",Toast.LENGTH_SHORT).show();
                moneyButton.setText("0");
            }
        });





        sub = (Button) findViewById(R.id.exit);
      // money = 0;
//

      //  moneyButton = (Button) findViewById(R.id.money);
        //moneyButton.setText("Rs." + money.toString());
        userdata = new DatabaseHandler(this);
        transdata = new DataBaseHandler1(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void showDialogOnButtonClick() {
        dateselector = (Button) findViewById(R.id.dateselector);

        dateselector.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(DIALOG_ID);
                    }
                }
        );
    }

    public void shownewDialogOnButtonClick(View view){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Amount");


// Set up the input
        input = new EditText(this);
        input.setHint("Enter Amount Here" );
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);





        builder.setSingleChoiceItems(R.array.testArray,-1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog , int which)
            {
                switch (which) {
                    case 0:
                        input.setText("100");

                        break;
                    case 1: input.setText("200");
                        break;
                    case 2: // Edit
                        input.setText("500");
                        break;
                    case 3:
                        input.setText("1000");
                        break;
                    default:
                        break;
                }

            }
                }
        );

// Set up the buttons


        builder.setPositiveButton("Authenticate", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String x=input.getText().toString();
                Double mon=Double.parseDouble(x);
                String[] y=accountbalance.getText().toString().split(" ");
                Double prev=Double.parseDouble(y[1]);

                Double fin=mon+prev;
                //userdata.updateinfo(fin,"nochange",code);
                userdata.addBalance(mon,prev,code);

                bal=Double.toString(fin);
                accountbalance.setText("Rs. "+bal);


                //Toast.makeText(getApplicationContext(),code,Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),"Rs. "+x+" added to your account",Toast.LENGTH_SHORT).show();
                Date date=new Date();
                transdata.InsertTrans(code,"Money added : "+"Rs. "+x,prev,mon,fin,date.toString());

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



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent1 = new Intent(MainPage.this, JSR268Client.class);
        startActivity(intent1);
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
            Toast.makeText(MainPage.this, "You have selected  " + date_x + "/" + month_x + "/" + year_x, Toast.LENGTH_SHORT).show();
            String nedate = date_x + "/" + month_x + "/" + year_x;
            next_date.setText(nedate);


        }
    };

    public void logout(View view) {
        Intent st1 = new Intent(MainPage.this, JSR268Client.class);
        startActivity(st1);
        finish();
    }

    public void submit(View view)
    {

        Double x=0.0;
        for(int i=0;i<sizeof;i++)
        {
            String date=new Date().toString();
            items ii=itemlist.get(i);
            double cp=ii.getCpi();
            x=x+cp*selection[i];
            if(selection[i]>0) {
                a.insert(ii.getid(), ii.getName(), ii.getCpi(), selection[i],date );
            }

        }

        Double prev = Double.parseDouble(bal);
        Double final_balance = prev - x;
        String s="Current Balanace :   "+Double.toString(prev)+"\n"
                +"Cost    :  "+Double.toString(x)+"\n"
                +"Final Balance   :  "+Double.toString(final_balance);










        new AlertDialog.Builder(this)
                .setTitle("Confirm")
                .setMessage("Do you really want to continue ?"+"\n\n"+s)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        submit1();
                    }})
                .setNegativeButton("No", null).show();
    }





    public void submit1() {

        View v1;
      Double x=0.0;
        for(int i=0;i<sizeof;i++)
        {
            String date=new Date().toString();
            items ii=itemlist.get(i);
            double cp=ii.getCpi();
            x=x+cp*selection[i];
            if(selection[i]>0) {
                a.insert(ii.getid(), ii.getName(), ii.getCpi(), selection[i],date );
            }

        }

        Toast.makeText(getApplicationContext(),Double.toString(x),Toast.LENGTH_SHORT).show();
        Double prev = Double.parseDouble(bal);
        String datenext = next_date.getText().toString();
       Double final_balance = prev - x;
        userdata.updateinfo(final_balance, datenext, code);

        Date date=new Date();
        Toast.makeText(getApplicationContext(),"Order Successful",Toast.LENGTH_SHORT).show();
        String transaction="" ;
        //transdata.InsertTrans(code,transaction,prev,money,final_balance,date.toString());
        Intent int1 = new Intent(MainPage.this, JSR268Client.class);
        startActivity(int1);
        cancelPDialog();
        finish();


//        String transaction = "";
//        if (quantity1>0) {
//
//            transaction += Integer.toString(quantity1)+"lunch";
//        }
//        if (quantity2>0) {
//
//            transaction += Integer.toString(quantity2)+"lunchwithfruits";
//        }
//        if (quantity3>0) {
//
//            transaction += Integer.toString(quantity3)+"tea";
//        }
//        if (quantity4>0) {
//
//            transaction += Integer.toString(quantity4)+"coffee";
//        }
//
//
//
//        showpDialog();
//        transaction.replaceAll(" ", "_");
//        String datenext = next_date.getText().toString();
//
//        int prev = Integer.parseInt(bal);
//        int final_balance = prev - money;
//        userdata.updateinfo(final_balance, datenext, code);
//        Date date=new Date();
//        Toast.makeText(getApplicationContext(),"Order Successful",Toast.LENGTH_SHORT).show();
//        //transdata.InsertTrans(code,transaction,prev,money,final_balance,date.toString());
//        Intent int1 = new Intent(MainPage.this, JSR268Client.class);
//        startActivity(int1);
//        cancelPDialog();
//        finish();

//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = getString(R.string.domain)+"/digita/makePurchase.php?empcode="+code+"&price="+value+"&trans="+transaction+"&next="+datenext;
//        //Log.d(TAG, url);
//        StringRequest myReq = new StringRequest(Request.Method.GET,
//                url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        cancelPDialog();
//                        try {
//
//                            //parsing the json response from the request
//                            JSONObject response1 = new JSONObject(response);
//                            String Success = response1.getString("success");
//                            if (Success.equals("1")){
//                                Intent main = new Intent(MainPage.this,JSR268Client.class);
//                                Toast.makeText(getApplicationContext(),"Order Successful",Toast.LENGTH_SHORT).show();
//                                startActivity(main);
//                                finish();
//                            }
//
//
//                            else
//                            {
//                                AlertDialog.Builder alert = new AlertDialog.Builder(MainPage.this);
//                                alert.setTitle("Unable to connect!!");
//
//                                final String MessageToSubmit="Please enter the details again...";
//                                alert.setMessage(MessageToSubmit);
//
//                                alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int whichButton) {
//                                        dialog.cancel();
//                                    }
//                                });
//                                alert.show();
//
//                            }
//                            //exception handling
//                        } catch (JSONException ex) {
//                            //Log.e(TAG, "Cannot parse response!!");
//                        }
//                    }
//
//                }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                cancelPDialog();
//                //VolleyLog.d(TAG, "Error: " + error.getMessage());
//                // hide the progress dialog
//
//                Toast.makeText(getApplicationContext(), "Unable to Access Server!",Toast.LENGTH_LONG).show();
//
//            }
//        });
//        queue.add(myReq);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    //hide progress bar
    private void cancelPDialog() {
        if (pDialog.isShowing())
            pDialog.cancel();
    }


//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "MainPage Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app URL is correct.
//                Uri.parse("android-app://com.example.jsr268client/http/host/path")
//        );
//        AppIndex.AppIndexApi.start(client, viewAction);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "MainPage Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app URL is correct.
//                Uri.parse("android-app://com.example.jsr268client/http/host/path")
//        );
//        AppIndex.AppIndexApi.end(client, viewAction);
//        client.disconnect();
 //   }
}

package com.example.jsr268client;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class register extends Activity {

    private EditText enter_name;
    private EditText enter_code;
    private String st_name;
    private String st_code;
    private String st_atr;
    private ProgressDialog pDialog;
    DatabaseHandler mydatabase;
    int year_x, month_x, date_x, newdate, newmonth;
    String dat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        enter_name=(EditText)findViewById(R.id.emp_name);
        enter_code=(EditText)findViewById(R.id.emp_code);
        st_atr = getIntent().getExtras().getString("atr");
        pDialog = new ProgressDialog(this);
        mydatabase=new DatabaseHandler(this);
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        date_x = cal.get(Calendar.DATE);
        newdate = cal.get(Calendar.DATE) + 1;
        newmonth = cal.get(Calendar.MONTH) + 1;
        dat=newdate + "/" + newmonth + "/" + year_x;
        //Toast.makeText(getApplicationContext(),st_atr,Toast.LENGTH_LONG).show();


    }
    public void sub(View view)
    {


        st_name=enter_name.getText().toString();
        st_code=enter_code.getText().toString();
        if(st_name.trim().isEmpty() || st_code.trim().isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Enter valid credentials",Toast.LENGTH_SHORT).show();
        }
        else {
            showpDialog();
        //getApplicationContext().deleteDatabase("Digita.db");
          // boolean check= mydatabase.InsertUser(st_atr,st_code,st_name,0,"");
            Cursor res=mydatabase.checkUserwithcode(st_code);


                if (res.moveToFirst()==false) {
                    boolean check = mydatabase.InsertUser(st_atr, st_code, st_name, 0, dat);
                    if(check==true)
                    {
                        Toast.makeText(getApplicationContext(),"Registration Successful",Toast.LENGTH_SHORT).show();
                        Intent gotohome=new Intent(register.this,MainPage.class);
                        gotohome.putExtra("code",st_code);
                        gotohome.putExtra("name",st_name);
                        gotohome.putExtra("bal","0");
                        gotohome.putExtra("date",dat);

                        startActivity(gotohome);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Connection Error!! Please Try Again",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    String name=res.getString(3);
                    AlertDialog.Builder alert = new AlertDialog.Builder(register.this);
                                    alert.setTitle("User already registered");

                                    final String MessageToSubmit = "Name : " + name + "\n";
                                    alert.setMessage(MessageToSubmit);

                                    alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            dialog.cancel();
                                            cancelPDialog();
                                        }
                                    });
                                    alert.show();




                }









            //boolean check2=mydatabase.InsertTrans("a","aa",45,5,50,"ads");
//            if(check==true)
//            {
//                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
//                Cursor res=mydatabase.getAllData();
//                if(res.getCount()==0)
//                {
//                    return;
//                }
//                StringBuffer buff=new StringBuffer();
//                while(res.moveToNext())
//                {
//                    buff.append(res.getString(0));
//                    buff.append(res.getString(1));
//                    buff.append(res.getString(2));
//                    buff.append(res.getString(3));
//                    Toast.makeText(getApplicationContext(),buff.toString(),Toast.LENGTH_SHORT).show();
//
//
//                }
//            }
//            else
//            {
//                Toast.makeText(getApplicationContext(),"Failure",Toast.LENGTH_SHORT).show();
//            }
//            RequestQueue queue = Volley.newRequestQueue(this);
//            String url = getString(R.string.domain) + "/digita/register.php?empname=" + st_name + "&empcode=" + st_code + "&empatr=" + st_atr;
//            //Log.d(TAG, url);
//            StringRequest myReq = new StringRequest(Request.Method.GET,
//                    url,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            cancelPDialog();
//                            try {
//                                //parsing the json response from the request
//                                JSONObject response1 = new JSONObject(response);
//                                String Success = response1.getString("success");
//                                if (Success.equals("1")) {
//                                    Intent main = new Intent(register.this, MainPage.class);
//                                    String person_name = response1.getString("username");
//                                    String person_code = response1.getString("usercode");
//
//
//                                    //passing the data to next activity
//
//                                    main.putExtra("code", person_code);
//                                    main.putExtra("name", person_name);
//                                    main.putExtra("bal", "0");
////                                    mydatabase.InsertUser(st_atr,person_code,person_name,0,"");
////                                    mydatabase.InsertTrans("aa","aaa",45,5,50,"bi");
//                                    //getApplicationContext().deleteDatabase("Digita.db");
//                                    Toast.makeText(getApplicationContext(), "You have been successfully registered.", Toast.LENGTH_SHORT).show();
//                                    startActivity(main);
//                                    finish();
//                                } else if (Success.equals("2")) {
//                                    AlertDialog.Builder alert = new AlertDialog.Builder(register.this);
//                                    alert.setTitle("User already registered");
//
//                                    final String MessageToSubmit = "Name : " + response1.getString("username") + "\n";
//                                    alert.setMessage(MessageToSubmit);
//
//                                    alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int whichButton) {
//                                            dialog.cancel();
//                                        }
//                                    });
//                                    alert.show();
//
//                                } else {
//                                    AlertDialog.Builder alert = new AlertDialog.Builder(register.this);
//                                    alert.setTitle("Unable to connect!!");
//
//                                    final String MessageToSubmit = "Please enter the details again...";
//                                    alert.setMessage(MessageToSubmit);
//
//                                    alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int whichButton) {
//                                            dialog.cancel();
//                                        }
//                                    });
//                                    alert.show();
//
//                                }
//                                //exception handling
//                            } catch (JSONException ex) {
//                                //Log.e(TAG, "Cannot parse response!!");
//                            }
//                        }
//
//                    }, new Response.ErrorListener() {
//
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    cancelPDialog();
//                    //VolleyLog.d(TAG, "Error: " + error.getMessage());
//                    // hide the progress dialog
//
//                    Toast.makeText(getApplicationContext(), "Unable to Access Server!", Toast.LENGTH_LONG).show();
//
//                }
//            });
//            queue.add(myReq);

        }

    }
    public void cancel(View view)
    {
        Intent intent1=new Intent(register.this,JSR268Client.class);
        startActivity(intent1);
        finish();
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


}

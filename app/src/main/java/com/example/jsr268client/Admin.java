package com.example.jsr268client;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Admin extends Activity {

    Button dateselector;
    int year_x, month_x, date_x, newdate, newmonth;
    static final int DIALOG_ID = 0;
    private TextView date;
    private TextView number_people;
    private ProgressDialog pDialog;
    private Integer numb;
    private DatabaseHandler data;
    Logger logs;
    private DataBaseHandler1 da;
    private AccountsManager ac;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        date_x = cal.get(Calendar.DATE);
        newdate = cal.get(Calendar.DATE) + 1;
        newmonth = cal.get(Calendar.MONTH) + 1;
        date = (TextView) findViewById(R.id.date_1);
        number_people = (TextView) findViewById(R.id.number_1);
        date.setText(newdate + "/" + newmonth + "/" + year_x);
        pDialog = new ProgressDialog(this);
        numb = getIntent().getExtras().getInt("num");
        number_people.setText(Integer.toString(numb));
        showDialogOnButtonClick();

       // getApplicationContext().deleteDatabase("account.db");


        data = new DatabaseHandler(this);
        logs=new Logger();
        da=new DataBaseHandler1(this);
        ac=new AccountsManager(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void showDialogOnButtonClick() {
        dateselector = (Button) findViewById(R.id.change_1);

        dateselector.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(DIALOG_ID);
                    }
                }
        );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent1 = new Intent(Admin.this, JSR268Client.class);
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
            String da = date_x + "/" + month_x + "/" + year_x;
            Toast.makeText(Admin.this, "You have selected  " + date_x + "/" + month_x + "/" + year_x, Toast.LENGTH_SHORT).show();
            date.setText(da);


            showpDialog();

            int numberpeople = data.findpeople(da);
            number_people.setText(Integer.toString(numberpeople));
            cancelPDialog();


//            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//            String url = getString(R.string.domain) + "/digita/number.php?date="+da;
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
//
//
//                                    String person = response1.getString("number");
//
//
//                                    //passing the data to next activity
//                                    number_people.setText(person);
//
//                                } else {
//                                    AlertDialog.Builder alert = new AlertDialog.Builder(Admin.this);
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
//


        }
    };

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    //hide progress bar
    private void cancelPDialog() {
        if (pDialog.isShowing())
            pDialog.cancel();
    }


    public void home(View view) {
        Intent newint = new Intent(Admin.this, JSR268Client.class);
        startActivity(newint);
        finish();
    }


    public void sending(View view) throws JSONException {

        Cursor ne=da.getAllData();
        while(ne.moveToNext())
        {
            String buffer=ne.getString(1)+ne.getString(2)+Integer.toString(ne.getInt(3))+Integer.toString(ne.getInt(4))+Integer.toString(ne.getInt(5))+ne.getString(6);
            logs.addRecordToLog(buffer);
        }

//
//       RequestQueue queue= Volley.newRequestQueue(getApplicationContext(), new proxycheck());
//        Map<String, String> postParam = new HashMap<String, String>();
//        postParam.put("abhi", "xyz@gmail.com");
//        postParam.put("bingo", "somepasswordhere");
//        HttpsTrustManager.allowAllSSL();
//
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
//                "https://hostelcomp.iitd.ac.in/storage.php", new JSONObject(postParam),
//                new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        try {
//                            String response1 =response.getString("data") ;
//                            Toast.makeText(getApplicationContext(),response1,Toast.LENGTH_SHORT).show();
//                        } catch (JSONException e) {
//                            Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
//                            e.printStackTrace();
//                        }
//
////                                String Success = response1.getString("success");
//                        //Log.d(TAG, response.toString());
//                        //msgResponse.setText(response.toString());
//                        //hideProgressDialog();
//                    }
//                }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
//
//            }
//        }) {
//
//            /**
//             * Passing some request headers
//             * */
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json; charset=utf-8");
//                return headers;
//            }
//
//
//        };
//queue.add(jsonObjReq);

        // Adding request to request queue
        JSONArray totalData=new JSONArray();
        //JSONObject js = new JSONObject();
        Cursor cr=ac.getAllData2();
        while(cr.moveToNext())
        {
            Integer id=cr.getInt(0);
            String nm=cr.getString(1);
            Double cpi=cr.getDouble(2);
            Integer qu=cr.getInt(3);
            String dat=cr.getString(4);
            //Toast.makeText(getApplicationContext(),dat,Toast.LENGTH_SHORT).show();

            try {
                JSONObject jsonobject_one = new JSONObject();

                jsonobject_one.put("id", id);
                jsonobject_one.put("name",nm);
                jsonobject_one.put("cpi",cpi);
                jsonobject_one.put("quantity",qu);
                jsonobject_one.put("date",dat);

                totalData.put(jsonobject_one);

            }catch (JSONException e) {
                e.printStackTrace();
            }




        }
        JSONObject js=new JSONObject();
        js.put("data",totalData);

//        JSONObject js = new JSONObject();
//        try {
//            JSONObject jsonobject_one = new JSONObject();
//
//            jsonobject_one.put("name", "Abhijeet");
//            jsonobject_one.put("cpi", "10");
//
//
//            //JSONObject jsonobject = new JSONObject();
//
//            //jsonobject.put("requestinfo", jsonobject_TWO);
//            //jsonobject.put("request", jsonobject_one);
//
//
//            js.put("data", jsonobject_one.toString());
//
//        }catch (JSONException e) {
//            e.printStackTrace();
//        }



       final RequestQueue queue= Volley.newRequestQueue(getApplicationContext(), new proxycheck());
        String url = "https://hostelcomp.iitd.ac.in/storage.php";
        HttpsTrustManager.allowAllSSL();
//        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>()
//                {
//
//                    @Override
//                    public void onResponse(String response) {
//                        // response
//                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // error
//                        Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_SHORT).show();
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams()
//            {
//                Map<String, String>  params = new HashMap<String, String>();
//                params.put("abhi", "Alif");
//                params.put("bingo", "http://itsalif.info");
//
//                return params;
//            }
//        };

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,url, js,
                new Response.Listener<JSONObject>() {
                    @Override

                    public void onResponse(JSONObject response) {

                        try {
                            String s=response.getString("success");
                            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                            ac.updatebackup();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_SHORT).show();
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };
            queue.add(jsonObjReq);









    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Admin Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.jsr268client/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Admin Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.jsr268client/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public void open(View view)
    {
        Intent in=new Intent(Admin.this,Manager.class);
        startActivity(in);

    }

    public void openacc(View view)
    {
        Intent in=new Intent(Admin.this,Accounts.class);
        startActivity(in);
    }
}

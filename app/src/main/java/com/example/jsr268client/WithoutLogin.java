package com.example.jsr268client;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Calendar;
import java.util.Date;

public class WithoutLogin extends Activity {



    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;

    private Integer money;


    private ProgressDialog pDialog;
    private Button moneyButton;

    private DataBaseHandler1 transdata;



    private int quantity1;
    private int quantity2;
    private int quantity3;
    private int quantity4;
    private Button quan1;
    private Button quan2;
    private Button quan3;
    private Button quan4;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_without_login);


        pDialog = new ProgressDialog(this);





        button1 = (Button) findViewById(R.id.menu11);
        button2 = (Button) findViewById(R.id.menu22);
        button3 = (Button) findViewById(R.id.menu33);
        button4 = (Button) findViewById(R.id.menu44);

        money = 0;
        quantity1=0;
        quantity2=0;
        quantity3=0;
        quantity4=0;
        quan1=(Button)findViewById(R.id.qu1);
        quan2=(Button)findViewById(R.id.qu2);
        quan3=(Button)findViewById(R.id.qu3);
        quan4=(Button)findViewById(R.id.qu4);

        quan1.setText(Integer.toString(quantity1));
        quan2.setText(Integer.toString(quantity2));
        quan3.setText(Integer.toString(quantity3));
        quan4.setText(Integer.toString(quantity4));

        moneyButton = (Button) findViewById(R.id.money1);
        moneyButton.setText("Rs." + Integer.toString(money));

        transdata = new DataBaseHandler1(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }






// Set up the input





// Set up the buttons









    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent1 = new Intent(WithoutLogin.this, JSR268Client.class);
        startActivity(intent1);
    }





    public void logout(View view) {
        Intent st1 = new Intent(WithoutLogin.this, JSR268Client.class);
        startActivity(st1);
        finish();
    }

    public void clicked1(View view) {
        if (quantity1 == 0) {
            quantity1 = 1;
            button1.setBackgroundResource(R.drawable.style3);
            money += 55;
            moneyButton.setText("Rs. " + money.toString());
            quan1.setText(Integer.toString(quantity1));

        } else {

            button1.setBackgroundResource(R.drawable.style5);
            money -= quantity1*55;
            quantity1 = 0;
            moneyButton.setText("Rs. " + money.toString());
            quan1.setText(Integer.toString(quantity1));
        }
    }

    public void clicked2(View view) {
        if (quantity2 == 0) {
            quantity2 = 1;
            button2.setBackgroundResource(R.drawable.style3);
            money += 65;
            moneyButton.setText("Rs. " + money.toString());
            quan2.setText(Integer.toString(quantity2));

        } else {

            button2.setBackgroundResource(R.drawable.style5);
            money -= quantity2*65;
            quantity2 = 0;
            moneyButton.setText("Rs. " + money.toString());
            quan2.setText(Integer.toString(quantity2));
        }
    }

    public void clicked3(View view) {
        if (quantity3 == 0) {
            quantity3 = 1;
            button3.setBackgroundResource(R.drawable.style3);
            money += 15;
            moneyButton.setText("Rs. " + money.toString());
            quan3.setText(Integer.toString(quantity3));

        } else {

            button3.setBackgroundResource(R.drawable.style5);
            money -= quantity3*15;
            quantity3 = 0;
            moneyButton.setText("Rs. " + money.toString());
            quan3.setText(Integer.toString(quantity3));
        }
    }

    public void clicked4(View view) {
        if (quantity4 == 0) {
            quantity4 = 1;
            button4.setBackgroundResource(R.drawable.style3);
            money += 15;
            moneyButton.setText("Rs. " + money.toString());
            quan4.setText(Integer.toString(quantity4));

        } else {

            button4.setBackgroundResource(R.drawable.style5);
            money -= quantity4*15;
            quantity4 = 0;
            moneyButton.setText("Rs. " + money.toString());
            quan4.setText(Integer.toString(quantity4));
        }
    }

//    public void clicked2(View view) {
//        if (b2 == 0) {
//            b2 = 1;
//            button2.setBackgroundResource(R.drawable.style3);
//            money += 65;
//            moneyButton.setText("Rs. " + money.toString());
//
//
//        } else {
//            b2 = 0;
//            button2.setBackgroundResource(R.drawable.style5);
//            money -= 65;
//            moneyButton.setText("Rs. " + money.toString());
//
//        }
//    }
//
//    public void clicked3(View view) {
//        if (b3 == 0) {
//            b3 = 1;
//            button3.setBackgroundResource(R.drawable.style3);
//            money += 15;
//            moneyButton.setText("Rs. " + money.toString());
//
//        } else {
//            b3 = 0;
//            button3.setBackgroundResource(R.drawable.style5);
//            money -= 15;
//            moneyButton.setText("Rs. " + money.toString());
//
//        }
//    }
//    public void clicked4(View view) {
//        if (b4 == 0) {
//            b4 = 1;
//            button4.setBackgroundResource(R.drawable.style3);
//            money += 15;
//            moneyButton.setText("Rs. " + money.toString());
//
//        } else {
//            b4 = 0;
//            button4.setBackgroundResource(R.drawable.style5);
//            money -= 15;
//            moneyButton.setText("Rs. " + money.toString());
//
//        }
//    }

    public void increase1(View view)
    {


        if(quantity1==0)
        {
            quantity1=1;
            button1.setBackgroundResource(R.drawable.style3);
            money += 55;
            moneyButton.setText("Rs. " + money.toString());
            quan1.setText(Integer.toString(quantity1));

        }
        else
        {
            quantity1++;
            quan1.setText(Integer.toString(quantity1));
            money += 55;
            moneyButton.setText("Rs. " + money.toString());

        }
    }

    public void decrease1(View view)
    {
        if(quantity1==0)
        {
            Toast.makeText(getApplicationContext(),"Quantity Already zero",Toast.LENGTH_SHORT).show();
        }
        else if (quantity1==1)
        {
            button1.setBackgroundResource(R.drawable.style5);
            quantity1=0;
            money-=55;
            moneyButton.setText("Rs. " + money.toString());
            quan1.setText("0");

        }
        else
        {
            quantity1--;
            money-=55;
            moneyButton.setText("Rs. " + money.toString());
            quan1.setText(Integer.toString(quantity1));
        }
    }


    public void increase2(View view)
    {


        if(quantity2==0)
        {
            quantity2=1;
            button2.setBackgroundResource(R.drawable.style3);
            money += 65;
            moneyButton.setText("Rs. " + money.toString());
            quan2.setText(Integer.toString(quantity2));

        }
        else
        {
            quantity2++;
            quan2.setText(Integer.toString(quantity2));
            money += 65;
            moneyButton.setText("Rs. " + money.toString());

        }
    }

    public void decrease2(View view)
    {
        if(quantity2==0)
        {
            Toast.makeText(getApplicationContext(),"Quantity Already zero",Toast.LENGTH_SHORT).show();
        }
        else if (quantity2==1)
        {
            button2.setBackgroundResource(R.drawable.style5);
            quantity2=0;
            money-=65;
            moneyButton.setText("Rs. " + money.toString());
            quan2.setText("0");

        }
        else
        {
            quantity2--;
            money-=65;
            moneyButton.setText("Rs. " + money.toString());
            quan2.setText(Integer.toString(quantity2));
        }
    }



    public void increase3(View view)
    {


        if(quantity3==0)
        {
            quantity3=1;
            button3.setBackgroundResource(R.drawable.style3);
            money += 15;
            moneyButton.setText("Rs. " + money.toString());
            quan3.setText(Integer.toString(quantity3));

        }
        else
        {
            quantity3++;
            quan3.setText(Integer.toString(quantity3));
            money += 15;
            moneyButton.setText("Rs. " + money.toString());

        }
    }

    public void decrease3(View view)
    {
        if(quantity3==0)
        {
            Toast.makeText(getApplicationContext(),"Quantity Already zero",Toast.LENGTH_SHORT).show();
        }
        else if (quantity3==1)
        {
            button3.setBackgroundResource(R.drawable.style5);
            quantity3=0;
            money-=15;
            moneyButton.setText("Rs. " + money.toString());
            quan3.setText("0");

        }
        else
        {
            quantity3--;
            money-=15;
            moneyButton.setText("Rs. " + money.toString());
            quan3.setText(Integer.toString(quantity3));
        }
    }



    public void increase4(View view)
    {


        if(quantity4==0)
        {
            quantity4=1;
            button4.setBackgroundResource(R.drawable.style3);
            money += 15;
            moneyButton.setText("Rs. " + money.toString());
            quan4.setText(Integer.toString(quantity4));

        }
        else
        {
            quantity4++;
            quan4.setText(Integer.toString(quantity4));
            money += 55;
            moneyButton.setText("Rs. " + money.toString());

        }
    }

    public void decrease4(View view)
    {
        if(quantity4==0)
        {
            Toast.makeText(getApplicationContext(),"Quantity Already zero",Toast.LENGTH_SHORT).show();
        }
        else if (quantity4==1)
        {
            button4.setBackgroundResource(R.drawable.style5);
            quantity4=0;
            money-=15;
            moneyButton.setText("Rs. " + money.toString());
            quan4.setText("0");

        }
        else
        {
            quantity4--;
            money-=15;
            moneyButton.setText("Rs. " + money.toString());
            quan4.setText(Integer.toString(quantity4));
        }
    }



    public void submit(View view) {

        String transaction = "";
        if (quantity1>0) {

            transaction += Integer.toString(quantity1)+"lunch";
        }
        if (quantity2>0) {

            transaction += Integer.toString(quantity2)+"lunchwithfruits";
        }
        if (quantity3>0) {

            transaction += Integer.toString(quantity3)+"tea";
        }
        if (quantity4>0) {

            transaction += Integer.toString(quantity4)+"coffee";
        }



        showpDialog();
        transaction.replaceAll(" ", "_");


        Date date=new Date();
        Toast.makeText(getApplicationContext(),"Order Successful",Toast.LENGTH_SHORT).show();
        transdata.InsertTrans("Without_Login",transaction,0,0,0,date.toString());
        Intent int1 = new Intent(WithoutLogin.this, JSR268Client.class);
        startActivity(int1);
        cancelPDialog();
        finish();

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

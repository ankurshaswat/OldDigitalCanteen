package com.example.jsr268client;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.smartcardio.ATR;
import android.smartcardio.Card;
import android.smartcardio.CardException;
import android.smartcardio.CardTerminal;
import android.smartcardio.TerminalFactory;
import android.smartcardio.ipc.CardService;
import android.smartcardio.ipc.ICardService;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.logging.FileHandler;


class Logger {

	public static FileHandler logger = null;
	private static String filename = "ProjectName_Log";

	static boolean isExternalStorageAvailable = false;
	static boolean isExternalStorageWriteable = false;
	static String state = Environment.getExternalStorageState();


	public static void addRecordToLog(String message) {

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			isExternalStorageAvailable = isExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// We can only read the media
			isExternalStorageAvailable = true;
			isExternalStorageWriteable = false;
		} else {
			// Something else is wrong. It may be one of many other states, but all we need
			//  to know is we can neither read nor write
			isExternalStorageAvailable = isExternalStorageWriteable = false;
		}

		File dir = new File("/sdcard/Files/Project_Name");
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			if(!dir.exists()) {
				Log.d("Dir created ", "Dir created ");
				dir.mkdirs();
			}

			File logFile = new File("/sdcard/Files/Project_Name/"+filename+".txt");

			if (!logFile.exists())  {
				try  {
					Log.d("File created ", "File created ");
					logFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				//BufferedWriter for performance, true to set append to file flag
				BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));

				buf.write(message + "\r\n");
				//buf.append(message);
				buf.newLine();
				buf.flush();
				buf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}


public class 	JSR268Client extends Activity {

	private static final String TAG = JSR268Client.class.getName();
	private static final String MANAGEMENT_APP = "CardReaderManager.apk";
	private static final String MANAGEMENT_APP_Inst = "CardReaderManager-release.apk";

	private static final String MANAGEMENT_PACKAGE =
			"com.hidglobal.cardreadermanager";
	private static final int REQUEST_APP_INSTALL = 0xbeef;
	private static final String GET_STRING = "Get Card Status in Loop";
	private static final String CANCEL_STRING = "Cancel Get Card Status";
	private static final int STATE_GET = 1;
	private static final int STATE_CANCEL = 2;

	private ICardService mService = null;
	private TerminalFactory mFactory = null;
	private CardTerminal mReader = null;
	
	private TextView mATR = null;
	//private Button mCardStatusButton = null;
	private TextView mCardStatus = null;
	//private TextView mAppStatus = null;
	private EditText code;
	private String setAtr;
	private AsyncTask<Void, String, Void> mGetCardStatusTask = null;
	private int mCardStatusButtonState = STATE_GET;
	Logger logger;
	private DatabaseHandler mydata;
	private ProgressDialog pDialog;
	int year_x, month_x, date_x, newdate, newmonth;
	String da;

	@Override
	protected void onResume() {
		super.onResume();


	}



	@Override
	public void onCreate(Bundle savedInstanceState) {



		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jsr268_client);
		code=(EditText)findViewById(R.id.e_code);
		pDialog = new ProgressDialog(this);
		mATR = (TextView) findViewById(R.id.textView_atr);
		//mCardStatusButton = (Button) findViewById(R.id.button1);
		mCardStatus = (TextView) findViewById(R.id.textView_cardStatus);
		final Calendar cal = Calendar.getInstance();
		year_x = cal.get(Calendar.YEAR);
		month_x = cal.get(Calendar.MONTH);
		date_x = cal.get(Calendar.DATE);
		newdate =cal.get(Calendar.DATE) + 1;
		newmonth =cal.get(Calendar.MONTH) + 1;
		da=newdate + "/" + newmonth + "/" + year_x;

		mydata=new DatabaseHandler(this);



		//getApplicationContext().deleteDatabase("Digita.db");
		//getApplicationContext().deleteDatabase("Digita2.db");

		//mAppStatus = (TextView) findViewById(R.id.textView_appStatus);
		//logger.addRecordToLog("----------- " );
		/* Set text view default values, set button to default text. */
		resetTextViews();
		//mCardStatusButton.setText(GET_STRING);

		/* If the management App is already installed, the service connection
		 * can be established here. Otherwise we have to delay this until the
		 * installation is finished. */
		if (alreadyInstalled(MANAGEMENT_PACKAGE)) {
			//mAppStatus.setText("installed");
			mService = CardService.getInstance(this);
			//logger.addRecordToLog(" already installed the management App " );
		} else {
			//logger.addRecordToLog(" not found management App " );

			/* If the management App cannot be installed, further processing
			 * is impossible. */
			if (!installManagementApp()) {
				//mAppStatus.setText("not installed");
				showToast("Error: unable to install the management App");
				//logger.addRecordToLog("Error: unable to install the management App " );
				this.finish();
			}
			else{
			//logger.addRecordToLog("installed app " );
				//
				}
		}
		mGetCardStatusTask = new GetCardStatusTask();
		//mGetCardStatusTask.execute();
	}

	@Override
	public void onStop() {
		super.onStop();

		/* Cancel task if not already done by button click. */
		if (mGetCardStatusTask != null) {

			mGetCardStatusTask.cancel(true);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (mService != null) {
			mService.releaseService();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_APP_INSTALL) {
			mService = CardService.getInstance(this);
			logger.addRecordToLog("Got mService" );
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_jsr268_client, menu);
		return true;
	}

	/* Button onClick implementation. */

//	public void getCardStatus(View view) {
//		/* The button either shows "get" or "cancel", so do the
//		 * corresponding work. */
//		logger.addRecordToLog("Button pressed " );
//		switch (mCardStatusButtonState) {
//		case STATE_GET:
//			logger.addRecordToLog("state_get" );
//			logger.addRecordToLog("creating new class " );
//			mGetCardStatusTask = new GetCardStatusTask();
//			mGetCardStatusTask.execute();
//			logger.addRecordToLog("changing button text to cancel " );
//			mCardStatusButton.setText(CANCEL_STRING);
//			mCardStatusButtonState = STATE_CANCEL;
//			break;
//		case STATE_CANCEL:
//			mGetCardStatusTask.cancel(true);
//			mATR.setText("new");
//			mCardStatusButton.setText(GET_STRING);
//			mCardStatusButtonState = STATE_GET;
//			break;
//		}
//	}

	private void resetTextViews() {
		mATR.setText("");
		mCardStatus.setText("");
	}

	private void setATRString(String string) {
		mATR.setText(string);
	}
	
	private void setStatusString(String string) {
		mCardStatus.setText(string);
	}
	
	private CardTerminal getFirstReader() {
		if (mFactory == null) {
			try {
				mFactory = mService.getTerminalFactory();
				//logger.addRecordToLog("mFactory found " );
			} catch (Exception e) {
				Log.e(TAG, "unable to get terminal factory");
				showToast("Error: unable to get terminal factory");
				//logger.addRecordToLog("mFactory is null " );
				return null;
			}
		}

		CardTerminal firstReader = null;
		try {
			/* Get the available card readers as list. */
			List<CardTerminal> readerList = mFactory.terminals().list();
			//logger.addRecordToLog("Looking for readers" );
			//logger.addRecordToLog("number : "+readerList.size() );
			if (readerList.size() == 0) {
				//logger.addRecordToLog("returning null " );
				return null;
			}

			/* Establish a connection with the first reader from the list. */
			firstReader = readerList.get(0);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
			//showToast("Error: " + e.toString());
		}
		return firstReader;
	}

	private String byteArrayToString(byte[] array) {
		//logger.addRecordToLog("Success " );
		String hex = "";
		for (int i = 0; i < array.length; i++) {
			hex +=  Integer.toHexString(array[i] & 0x000000ff) + "_";
		}
		return hex;
	}

	private void showToast(String message) {
		Toast toast = Toast.makeText(getApplicationContext(), message,
				Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	private boolean alreadyInstalled(String packageName) {
		PackageManager pm1 = getPackageManager();
		List<ApplicationInfo> apps = pm1.getInstalledApplications(0);

		for(ApplicationInfo app : apps) {
			String label = (String)pm1.getApplicationLabel(app);
			//logger.addRecordToLog(label);
			if (label.equals("CardReaderManager"))
			{
				return true;
			}



		}
		/* To check whether a package is already installed, to PackageManager
		 * is used, which can be queried about package information. An
		 * exception is thrown if the desired package name is not found.
		 * Package names are fully-qualified, e.g.
		 * "com.example.anExamplePackage". */
		//logger.addRecordToLog("Checking for package"+packageName );

		try {
			PackageManager pm = getPackageManager();
			pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
			//logger.addRecordToLog(packageName+"found" );
			return true;

		} catch (PackageManager.NameNotFoundException e) {
			//logger.addRecordToLog(packageName+"Not found" );
			return false;
		}
	}

	private boolean installManagementApp() {
		//logger.addRecordToLog("installing app " );
		String cachePath = null;
		try {
			/* Copy the .apk file from the assets directory to the external
			 * cache, from where it can be installed. */
			File temp = File.createTempFile("CardReaderManager", "apk",
					getExternalCacheDir());
			temp.setWritable(true);
			FileOutputStream out = new FileOutputStream(temp);
			InputStream in = getAssets().open(MANAGEMENT_APP_Inst);
			byte[] buffer = new byte[1024];
			int bytes = 0;

			while ((bytes = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytes);
			}
			in.close();
			out.close();
			cachePath = temp.getPath();
		} catch (IOException e) {
			Log.e(TAG, e.toString());
			//logger.addRecordToLog(e.toString() );
			return false;
		}

		/* Actual installation, calls external Activity that is shown to the
		 * user and returns with call to onActivityResult() to this Activity. */
		Intent promptInstall = new Intent(Intent.ACTION_VIEW);
		promptInstall.setDataAndType(Uri.fromFile(new File(cachePath)),
				"application/vnd.android.package-archive");
		startActivityForResult(promptInstall, REQUEST_APP_INSTALL);
		return true;
	}

	/* This work is done in an AsyncTask (= simple Interface for using Threads
	 * under Android), as the UI must not be blocked, but the status querying
	 * is done in a loop. */
	private class GetCardStatusTask extends AsyncTask<Void, String, Void> {

		@Override
		public Void doInBackground(Void... params) {
			/* Wait until we have the reader instance. */

			while (mReader == null) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
				//mReader = getFirstReader();
				break;
			}

			/* This is done until the button is clicked, which cancels this
			 * AsyncTask. */
			for (; !isCancelled();) {
				try {
					if (mReader.isCardPresent()) {
						/* Connect to the reader. This returns a card object.
						 * "*" indicates that either protocol T=0 or T=1 can be
						 * used. */

						Card card = mReader.connect("*");
						ATR atr = card.getATR();
						String s=atr.toString();
						publishProgress("Card present",
								byteArrayToString(atr.getBytes()));

						card.disconnect(true);
						//mGetCardStatusTask.cancel(true);
						return null;
						//Intent intent1=new Intent(JSR268Client.this,MainPage.class);
						//intent1.putExtra("ATR",byteArrayToString(atr.getBytes()));

						//startActivity(intent1);

					} else {
						//publishProgress("No card present",
						//		"Waiting for card...");
					}
					try {
						/* Don't overtax the USB/Bluetooth connection.*/
						Thread.sleep(500);


					} catch (InterruptedException e) {
					//publishProgress("bin","bin");
					}

				} catch (CardException e) {
					Log.e(TAG, e.toString());
					publishProgress("Error: " + e.toString(), "");
				}
			}
			return null;
		}

		@Override
		public void onProgressUpdate(String... params) {
			code.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
			code.setTransformationMethod(PasswordTransformationMethod.getInstance());
			code.setText(params[1]);
			setAtr=params[1];
			//setATRString(params[1]);
			//logger.addRecordToLog(params[0]+" "+params[1] );
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);

			showpDialog();
			final String sen=setAtr;

			showpDialog();
			Cursor res=mydata.checkUserwithATR(sen);
			if (res.moveToFirst()==false) {
				Intent toreg=new Intent(JSR268Client.this,register.class);
				toreg.putExtra("atr",sen);
				cancelPDialog();
				startActivity(toreg);
				finish();

			} else {
				String code=res.getString(2);
				String name=res.getString(3);
				Integer balance=res.getInt(4);
				String nxtdate=res.getString(5);
				Intent tohome=new Intent(JSR268Client.this,MainPage.class);
				tohome.putExtra("code",code);
				tohome.putExtra("name",name);
				tohome.putExtra("bal",balance.toString());
				tohome.putExtra("date",nxtdate);
				cancelPDialog();
				startActivity(tohome);
				finish();



			}



//			RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//			String url = getString(R.string.domain)+"/digita/loginauto.php?atr=" +sen;
//			//Log.d(TAG, url);
//			Logger.addRecordToLog(url);
//			Logger.addRecordToLog(sen);
//			StringRequest myReq = new StringRequest(Request.Method.GET,
//					url,
//					new Response.Listener<String>() {
//						@Override
//						public void onResponse(String response) {
//							cancelPDialog();
//							try {
//								//parsing the json response from the request
//								JSONObject response1 = new JSONObject(response);
//								String Success = response1.getString("success");
//								if (Success.equals("2")){
//									Intent main = new Intent(JSR268Client.this,MainPage.class);
//									String person_name=response1.getString("username");
//									String person_code=response1.getString("usercode");
//									Integer person_balance=response1.getInt("balance");
//
//									//passing the data to next activity
//
//									main.putExtra("code",person_code);
//									main.putExtra("name",person_name);
//									main.putExtra("bal",person_balance.toString());
//									startActivity(main);
//									finish();
//								}
//
//								else if(Success.equals("1")){
//									//alertdialog if the credentials are invalid
//									Intent main_1=new Intent(JSR268Client.this,register.class);
//
//									main_1.putExtra("atr",sen);
//									startActivity(main_1);
//									finish();
//								}
//								else
//								{
//									AlertDialog.Builder alert = new AlertDialog.Builder(JSR268Client.this);
//									alert.setTitle("Unable to connect!!");
//
//									final String MessageToSubmit="Please enter the details again...";
//									alert.setMessage(MessageToSubmit);
//
//									alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
//										public void onClick(DialogInterface dialog, int whichButton) {
//											dialog.cancel();
//										}
//									});
//									alert.show();
//
//								}
//								//exception handling
//							} catch (JSONException ex) {
//								//Log.e(TAG, "Cannot parse response!!");
//							}
//						}
//
//					}, new Response.ErrorListener() {
//
//				@Override
//				public void onErrorResponse(VolleyError error) {
//					cancelPDialog();
//					//VolleyLog.d(TAG, "Error: " + error.getMessage());
//					// hide the progress dialog
//
//					Toast.makeText(getApplicationContext(), "Unable to Access Server!",Toast.LENGTH_LONG).show();
//
//				}
//			});
//			queue.add(myReq);
//




		}
		//		@Override
//		public void onCancelled(Void unused) {
//			Intent intent1=new Intent(JSR268Client.this,MainPage.class);
//
//
//			startActivity(intent1);
//		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	public void regis(View view)
	{
		Intent int1=new Intent(JSR268Client.this,register.class);
		int1.putExtra("atr","Notavailable");

		startActivity(int1);
	}

	public void login(View view) {


		String to_send = code.getText().toString();
		if (to_send.trim().isEmpty()) {
			Toast.makeText(getApplicationContext(), "Employee code cannot be empty", Toast.LENGTH_SHORT).show();

		} else {
			showpDialog();

			Cursor res=mydata.checkUserwithcode(to_send);


			if (res.moveToFirst()==false) {
				cancelPDialog();
				AlertDialog.Builder alert = new AlertDialog.Builder(JSR268Client.this);
									alert.setTitle("Invalid Credential!!");

									final String MessageToSubmit = "Please enter the details again...";
									alert.setMessage(MessageToSubmit);

									alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int whichButton) {
											dialog.cancel();
											code.setText("");
										}
									});
									alert.show();
				code.setText("");


			} else {

				String code=res.getString(2);
				String name=res.getString(3);
				Double balance=res.getDouble(4);
				String nxt=res.getString(5);
				Intent tohome=new Intent(JSR268Client.this,MainPage.class);
				tohome.putExtra("code",code);
				tohome.putExtra("name",name);
				tohome.putExtra("bal",balance.toString());
				tohome.putExtra("date",nxt);
				cancelPDialog();
				startActivity(tohome);
				finish();




			}





//			RequestQueue queue = Volley.newRequestQueue(this);
//
//			String url = getString(R.string.domain) + "/digita/login.php?empcode=" + to_send;
//			//Log.d(TAG, url);
//
//			StringRequest myReq = new StringRequest(Request.Method.GET,
//					url,
//					new Response.Listener<String>() {
//						@Override
//						public void onResponse(String response) {
//							cancelPDialog();
//							try {
//								//parsing the json response from the request
//								JSONObject response1 = new JSONObject(response);
//								String Success = response1.getString("success");
//								if (Success.equals("2")) {
//									Intent main = new Intent(JSR268Client.this, MainPage.class);
//									String person_name = response1.getString("username");
//									String person_code = response1.getString("usercode");
//									Integer person_balance = response1.getInt("balance");
//									Logger.addRecordToLog(person_name + person_code + person_balance.toString());
//									//passing the data to next activity
//
//									main.putExtra("code", person_code);
//									main.putExtra("name", person_name);
//									main.putExtra("bal", person_balance.toString());
//									startActivity(main);
//									finish();
//								} else if (Success.equals("1")) {
//									//alertdialog if the credentials are invalid
//									AlertDialog.Builder alert = new AlertDialog.Builder(JSR268Client.this);
//									alert.setTitle("Invalid UserCode or Username!!");
//
//									final String MessageToSubmit = "Please enter the details again...";
//									alert.setMessage(MessageToSubmit);
//
//									alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
//										public void onClick(DialogInterface dialog, int whichButton) {
//											dialog.cancel();
//										}
//									});
//									alert.show();
//								} else {
//									AlertDialog.Builder alert = new AlertDialog.Builder(JSR268Client.this);
//									alert.setTitle("Unable to connect!!");
//
//									final String MessageToSubmit = "Please enter the details again...";
//									alert.setMessage(MessageToSubmit);
//
//									alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
//										public void onClick(DialogInterface dialog, int whichButton) {
//											dialog.cancel();
//										}
//									});
//									alert.show();
//
//								}
//								//exception handling
//							} catch (JSONException ex) {
//								//Log.e(TAG, "Cannot parse response!!");
//							}
//						}
//
//					}, new Response.ErrorListener() {
//
//				@Override
//				public void onErrorResponse(VolleyError error) {
//					cancelPDialog();
//					//VolleyLog.d(TAG, "Error: " + error.getMessage());
//					// hide the progress dialog
//
//					Toast.makeText(getApplicationContext(), "Unable to Access Server!", Toast.LENGTH_LONG).show();
//
//				}
//			});
//			queue.add(myReq);
		}
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
	public void without(View view)
	{
		Intent intt1=new Intent(JSR268Client.this,WithoutLogin.class);
		startActivity(intt1);
		finish();
	}
	public void openadmin(View view)
	{
		String number="";
		showpDialog();
		int numpeople=mydata.findpeople(da);
		Intent int1=new Intent(JSR268Client.this,Admin.class);
		int1.putExtra("num",numpeople);
		cancelPDialog();
		startActivity(int1);
		finish();






//		RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//		String url = getString(R.string.domain) + "/digita/number.php?date="+da;
//		//Log.d(TAG, url);
//		StringRequest myReq = new StringRequest(Request.Method.GET,
//				url,
//				new Response.Listener<String>() {
//					@Override
//					public void onResponse(String response) {
//						cancelPDialog();
//						try {
//							//parsing the json response from the request
//							JSONObject response1 = new JSONObject(response);
//							String Success = response1.getString("success");
//							if (Success.equals("1")) {
//
//
//								String person = response1.getString("number");
//								Intent int1=new Intent(JSR268Client.this,Admin.class);
//								int1.putExtra("num",person);
//								startActivity(int1);
//								finish();
//
//							}
//							else
//							{
//								Intent int1=new Intent(JSR268Client.this,Admin.class);
//								int1.putExtra("num","Data not available");
//								startActivity(int1);
//								finish();
//							}
//						} catch (JSONException ex) {
//							//Log.e(TAG, "Cannot parse response!!");
//
//						}
//					}
//
//				}, new Response.ErrorListener() {
//
//			@Override
//			public void onErrorResponse(VolleyError error) {
//				cancelPDialog();
//				//VolleyLog.d(TAG, "Error: " + error.getMessage());
//				// hide the progress dialog
//
//				Intent int1=new Intent(JSR268Client.this,Admin.class);
//				int1.putExtra("num","Data not available");
//				startActivity(int1);
//				finish();
//
//			}
//		});
//		queue.add(myReq);


	}

}


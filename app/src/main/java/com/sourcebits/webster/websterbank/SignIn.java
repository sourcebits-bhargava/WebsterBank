
package com.sourcebits.webster.websterbank;


import android.app.Activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public class SignIn extends Activity implements View.OnClickListener {
    //---------------------Login---------
    private EditText mUsername;
    private EditText mPassword;
    private Button mSignIn;
    private Button mSavings;
    private String uname;
    private String pass;
    private Authenticator taskAuthenticator;
    private TextView savingCont;

/*
    //variables for soap start-----------

    private static final String SOAP_NAMESPACE = "http://schemas.wbstpoc.com/2015/01/WBSTMobileInterface";
    private static final String SOAP_URL = "http://12.216.193.170/mobilePOC/?WSDL";
    private static final String SOAP_ACTION = "http://schemas.wbstpoc.com/2015/01/WBSTMobileInterface/";
    private static final String SOAP_METHOD_NAME = "getAccountHistory";
    private String response;
    private TextView mSignTv;

    //method names getAccountHistory, getAccounts,loginUser,authenticate
    private PropertyInfo pi1;
    private PropertyInfo pi2;  /// variables for SOAP

    // variables for soap completed---------
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mSignIn = (Button) findViewById(R.id.SignIn);
        mSavings = (Button) findViewById(R.id.Savings);

/*
//-----------soap
        mUsername = (EditText) findViewById(R.id.username_Et);
        mPassword = (EditText) findViewById(R.id.password_Et);
        // String  uname = mUsername.getText().toString();



        //  String pass = mPassword.getText().toString();
        pass = mPassword.getText().toString();
        mSignTv = (TextView) findViewById(R.id.signin_Tv);
// --------------soap
*/
/*
//----------SOAP object request with instance

        // SoapObject Request = new SoapObject(SOAP_NAMESPACE, SOAP_METHOD_NAME); //SOAP object for username
        //pi1 = new PropertyInfo();
        //   pi1.setValue(uname.getText().toString());//get the string that is to be sent to the web service

        */
    }

    @Override
    public void onClick(View v) {

        //-------------------------------------open savings screen


        switch (v.getId()) {


            case R.id.SignIn:
                mUsername = (EditText) findViewById(R.id.username_Et);
                mPassword = (EditText) findViewById(R.id.password_Et);

                String uname = mUsername.getText().toString();
                String pass = mPassword.getText().toString();


                if (uname.equals("") || uname == null) {
                    Toast.makeText(getApplicationContext(), "Username Empty", Toast.LENGTH_SHORT).show();
                } else if (pass.equals("") || pass == null) {
                    Toast.makeText(getApplicationContext(), "Password Empty", Toast.LENGTH_SHORT).show();
                } else {

                 /*
                    boolean validLogin = validateLogin(uname, pass, SignIn.this);
                    if (validLogin) {
                        System.out.println("In Valid");
                        //Soap Object request using async task
*/

                    // myAsyncTask myRequest = new myAsyncTask();
                    // myRequest.execute();

                    taskAuthenticator = new Authenticator(uname, pass);
                    taskAuthenticator.execute();

                }
                break;


            case R.id.Savings: {
                Toast.makeText(getApplicationContext(), "Savings Activity", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignIn.this, SavingsActivity.class);
                startActivity(intent);
                finish();
            }
            break;

/*
            case R.id.2:
                Intent i = new Intent(SignIn.this, NewUserActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.3:
                Intent i_admin = new Intent(SignIn.this, AdminPage.class);
                startActivity(i_admin);
                finish();
                break;*/
        }


    }

//Soap-------------------------
/*
    private class FirstAsyncTask extends AsyncTask<String,String,String>
    {
        private String mUserName, password;

        private static final String NAMESPACE = "http://schemas.wbstpoc.com/2015/01/WBSTMobileInterface";
        private static final String URL = "http://12.216.193.170/mobilePOC/?WSDL";
        private static final String SOAP_ACTION = "http://schemas.wbstpoc.com/2015/01/WBSTMobileInterface/";
        private static final String METHOD_NAME = "loginUser";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

         //  ProgressDialog dialog;
           // dialog = ProgressDialog.show(SignIn.this, "",
              // "Loading... Please wait...", true);

           Log.d("App", "Pre execute");
        }


        @Override
        protected String doInBackground(String... strings) {

            Log.d("App", "doing in background");
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("userName", this.mUserName);
            request.addProperty("password", this.password);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.implicitTypes = true;

            // Says that the soap webservice is a .Net service
            envelope.dotNet = true;
            envelope.dotNet = true;

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                String responsexml = androidHttpTransport.responseDump;
                String requestXml = androidHttpTransport.requestDump;
// Log.i("envelope", "" + envelope);
                Log.i("xml", "" + responsexml);
                Log.i("xml1", "" + requestXml);

                //SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
                // SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
                SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;

                System.out.println("resultsRequestSOAP" + resultsRequestSOAP);
                // savingCont.setText(resultsRequestSOAP.toString());
                System.out.println("Response::" + resultsRequestSOAP.toString());

            } catch (Exception e) {
                System.out.println("Error" + e);
            }



          return resultsRequestSOAP.toString();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("App", "post execute");

 //if (dialog.isShowing()) {
          //  dialog.dismiss();
        }


            Intent i = new Intent(SignIn.this, SavingsActivity.class);
            startActivity(i);

            finish();
        }

    }
*/
    private class Authenticator extends AsyncTask {

        private String mUserName, password;

        private static final String NAMESPACE = "http://schemas.wbstpoc.com/2015/01/WBSTMobileInterface";
        private static final String URL = "http://12.216.193.170/mobilePOC/?WSDL";
        private static final String SOAP_ACTION = "http://schemas.wbstpoc.com/2015/01/WBSTMobileInterface/";
        private static final String METHOD_NAME = "loginUser";

        public Authenticator(String user, String password) {
            this.mUserName = user;
            this.password = password;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //   ProgressDialog dialog;
            // dialog = ProgressDialog.show(SignIn.this, "",
            //       "Loading... Please wait...", true);
            // Log.d("App", "Pre execute");
        }





        @Override
        protected Object doInBackground(Object[] objects) {

            String responseStorage = null;

            Log.d("App", "doing in background");
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("userName", this.mUserName);
            request.addProperty("password", this.password);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.implicitTypes = true;

            // Says that the soap webservice is a .Net service
            envelope.dotNet = true;

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);


            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);


//                List reqHeaders = null;
//                List respHeaders = androidHttpTransport.call(SOAP_ACTION, envelope, reqHeaders);
                //Parsing the HTTP response. HTTP response comes as Key/Value Pair. First Key contains the HTTP response 200 OK.
//
//                for (int ix = 0; ix < respHeaders.size(); ix++) {
//                    HeaderProperty hp = (HeaderProperty) respHeaders.get(ix);
//                    System.out.println("Header" + ix + "=" + hp.getKey() + " / " + hp.getValue());
////Looking for HTTP response HTTP 200 OK from the HTTP response. If OK, setting the response status flag as true.
//                    if (hp.getValue().contains("200 OK"))
//                        resstatus = true;
//
//                }

                String responsexml = androidHttpTransport.responseDump;
                String requestXml = androidHttpTransport.requestDump;
// Log.i("envelope", "" + envelope);
                Log.i("xml", "" + responsexml);
                 Log.i("xml1", "" + requestXml);

                //SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
                // SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
                SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;

                System.out.println("resultsRequestSOAP" + resultsRequestSOAP);
                // savingCont.setText(resultsRequestSOAP.toString());
                  System.out.println("Response::"+resultsRequestSOAP.toString());

            } catch (Exception e) {
                System.out.println("Error" + e);
            }

            return responseStorage;
        }

//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//         //   ProgressDialog dialog;
//           // dialog = ProgressDialog.show(SignIn.this, "",
//             //       "Loading... Please wait...", true);
//           // Log.d("App", "Pre execute");
//        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Log.d("App", "post execute");
/*
 if (dialog.isShowing()) {
            dialog.dismiss();
        }
 */
            if(responseStorage)

             Intent i = new Intent(SignIn.this, SavingsActivity.class);
            startActivity(i);

            finish();
        }
    }
/*
    private class myAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
//            mSignTv.setText(response);
        }

        @Override
        protected void onPreExecute() {


            super.onPreExecute();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
        */
/*
            // Call the webservices with two parameters property info

            SoapObject Request = new SoapObject(SOAP_NAMESPACE, SOAP_METHOD_NAME); //SOAP object for username
            //  pi1.setValue(uname.getText().toString());
            //request.addProperty("username", "uname");
            pi1 = new PropertyInfo();
            pi1.setValue(uname);
            //get the string that is to be sent to the web service
            // pi2.setValue(pass.getText().toString());
            pi2 = new PropertyInfo();
            pi2.setValue(pass);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(Request);

            AndroidHttpTransport httpTransport = new AndroidHttpTransport(SOAP_URL);
            httpTransport.debug = true;


            try {
                httpTransport.call(SOAP_ACTION, envelope);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            //send request
            SoapObject result = null;
            try {
                result = (SoapObject) envelope.getResponse();


            } catch (SoapFault e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }



            Log.d("App", "" + result.getProperty(1).toString());
            response = result.getProperty(1).toString();
            return null;

        }
    }
*/


//soap

/*
    SoapSerializationEnvelope soapEnvolpe = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        SoapEnvelope.dotNet = false;

    AndroidHttpTransport aht =new AndroidHttpTransport(SOAP_URL);
        try
        {
          //  aht.call(SOAP_ACTION, soapEnvolpe);
            SoapPrimitive resultString =(SoapPrimitive)SoapEnvelope.getResponse();
        }
    }
*/





    /* arraylist
    private void AddUser() {

		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


	   	nameValuePairs.add(new BasicNameValuePair("name", uname.getText().toString()));
	   	nameValuePairs.add(new BasicNameValuePair("pass", pwd.getText().toString()));
   */

/*
    public boolean validateLogin(String uname, String pass, Context context) {

        if (uname == "myuser")

        {
            return true;
        }
        else
            return false;
        */

/*

        DbHelper mydb = null;
        mydb = new DbHelper(context);
        SQLiteDatabase db = mydb.getReadableDatabase();
        //SELECT
        String[] columns = {"_id"};

        //WHERE clause
        String selection = "username=? AND password=?";

        //WHERE clause arguments
        String[] selectionArgs = {uname, pass};

        Cursor cursor = null;
        try {
            //SELECT _id FROM login WHERE username=uname AND password=pass
            cursor = db.query(DbHelper.WEBSTER_TABLE_NAME, columns, selection, selectionArgs, null, null, null);

            startManagingCursor(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int numberOfRows = cursor.getCount();

        if (numberOfRows <= 0) {

            Toast.makeText(getApplicationContext(), "Login Failed..\nTry Again", Toast.LENGTH_SHORT).show();
            return false;
        }


            return true;
              }
        */



/*
    public void onDestroy() {
        super.onDestroy();
        if (mydb != null)
            mydb.close();
    }
*/
}




package com.sourcebits.webster.websterbank;


import android.app.Activity;


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
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class SignIn extends Activity implements View.OnClickListener {
    //---------------------Login---------
    private EditText mUsername;
    private EditText mPassword;
    private Button mSignIn;
    private Button mSavings;

    //variables for soap start-----------

    private static final String SOAP_NAMESPACE = "http://schemas.wbstpoc.com/2015/01/WBSTMobileInterface";
    private static final String SOAP_URL = "http://12.216.193.170/mobilePOC/?WSDL";
    private static final String SOAP_ACTION = "http://schemas.wbstpoc.com/2015/01/WBSTMobileInterface/";
    private static final String SOAP_METHOD_NAME = "getAccountHistory";
    //method names getAccountHistory, getAccounts,loginUser,authenticate
    private PropertyInfo pi1;
    private PropertyInfo pi2;  /// variables for SOAP

    // variables for soap completed---------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mSignIn = (Button) findViewById(R.id.SignIn);
        mSavings = (Button) findViewById(R.id.Savings);


//-----------soap
        mUsername = (EditText) findViewById(R.id.username_Et);
        mPassword = (EditText) findViewById(R.id.password_Et);
        String uname = mUsername.getText().toString();
        String pass = mPassword.getText().toString();
        tv = (TextView) findViewById(R.id.txt2);
// --------------soap


        //Soap Object request using async task

        myAsyncTask myRequest = new myAsyncTask();
        myRequest.execute();


//----------SOAP object request with instance

       // SoapObject Request = new SoapObject(SOAP_NAMESPACE, SOAP_METHOD_NAME); //SOAP object for username
        //pi1 = new PropertyInfo();
        //   pi1.setValue(uname.getText().toString());//get the string that is to be sent to the web service
    }

    private class myAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            tv.setText(response);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {



            // Call the webservices with two parameters

            SoapObject Request = new SoapObject(SOAP_NAMESPACE, SOAP_METHOD_NAME); //SOAP object for username
             //  pi1.setValue(uname.getText().toString());
            pi1.setValue(uname); //get the string that is to be sent to the web service
           // pi2.setValue(pass.getText().toString());
            pi2.setValue(pass);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            AndroidHttpTransport httpTransport = new AndroidHttpTransport(SOAP_URL);
            httpTransport.debug = true;


            try {
                httpTransport.call(SOAP_ACTION, envelope);
            } catch (HttpResponseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } //send request
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
                    boolean validLogin = validateLogin(uname, pass, SignIn.this);
                    if (validLogin) {
                        System.out.println("In Valid");
                        Intent i = new Intent(SignIn.this, SavingsActivity.class);
                        startActivity(i);
                        finish();
                    }
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



    /* arraylist
    private void AddUser() {

		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


	   	nameValuePairs.add(new BasicNameValuePair("name", uname.getText().toString()));
	   	nameValuePairs.add(new BasicNameValuePair("pass", pwd.getText().toString()));
   */


    public boolean validateLogin(String uname, String pass, Context context) {
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

    public void onDestroy() {
        super.onDestroy();
        if (mydb != null)
            mydb.close();
    }
}


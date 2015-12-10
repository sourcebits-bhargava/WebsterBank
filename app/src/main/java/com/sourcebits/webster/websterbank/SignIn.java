
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

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
//import org.ksoap2.transport.AndroidHttpTransport;
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
    private String userid;
    private Authenticator taskAuthenticator;
    private LoginAuthenticator loginAuthenticator;

    private TextView savingCont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mSignIn = (Button) findViewById(R.id.SignIn);
        mSavings = (Button) findViewById(R.id.Savings);
    }

    @Override
    public void onClick(View v) {

        //-------------------------------------open savings screen
        switch (v.getId()) {

            case R.id.SignIn:
                mUsername = (EditText) findViewById(R.id.username_Et);
                mPassword = (EditText) findViewById(R.id.password_Et);

                uname = mUsername.getText().toString();
                pass = mPassword.getText().toString();


                if (uname.equals("") || uname == null) {
                    Toast.makeText(getApplicationContext(), "Username Empty", Toast.LENGTH_SHORT).show();
                } else if (pass.equals("") || pass == null) {
                    Toast.makeText(getApplicationContext(), "Password Empty", Toast.LENGTH_SHORT).show();
                } else {


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


        }

    }


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
             //dialog = ProgressDialog.show(SignIn.this, "",
               //    "Loading... Please wait...", true);
            Toast.makeText(getApplicationContext(), "Please wait... Validating login", Toast.LENGTH_SHORT).show();
             Log.d("App", "Pre execute");
        }


        @Override
        protected Object doInBackground(Object[] objects) {
            //  final  ProgressDialog dialog;
            //dialog = ProgressDialog.show(SignIn.this, "",
              // "Loading... Please wait...", true);

            String responseStorage = null;
            boolean resstatus;

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

                List reqHeaders = null;
                List respHeaders = androidHttpTransport.call(SOAP_ACTION, envelope, reqHeaders);
                //Parsing the HTTP response. HTTP response comes as Key/Value Pair. First Key contains the HTTP response 200 OK.

                for (int ix = 0; ix < respHeaders.size(); ix++) {
                    HeaderProperty hp = (HeaderProperty) respHeaders.get(ix);
                    System.out.println("Header" + ix + "=" + hp.getKey() + " / " + hp.getValue());
////Looking for HTTP response HTTP 200 OK from the HTTP response. If OK, setting the response status flag as true.
                    if (hp.getValue().contains("200 OK"))
                        resstatus = true;


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
                    responseStorage=(resultsRequestSOAP.toString());

                    if (responseStorage== "status=SUCCESS")
                    {
//

                        Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();




                    }


                }

                return responseStorage;
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } return responseStorage;


        }
        @Override
        protected void onPostExecute(Object responseStorage) {
            super.onPostExecute(responseStorage);
            Log.d("App", "post execute");
            loginAuthenticator = new LoginAuthenticator(mUserName, password,userid);
            loginAuthenticator.execute();

            /*
 if (dialog.isShowing()) {
            dialog.dismiss();
        }
 */
            //  if (responseStorage == "SUCCESS")

           // {

             //   Intent i = new Intent(SignIn.this, SavingsActivity.class);
             //   startActivity(i);
               // finish();
           // }


        }




    }



    public class LoginAuthenticator extends AsyncTask{
        private String mUserName, password,userId;

        private static final String NAMESPACE = "http://schemas.wbstpoc.com/2015/01/WBSTMobileInterface";
        private static final String URL = "http://12.216.193.170/mobilePOC/?WSDL";
        private static final String SOAP_ACTION = "http://schemas.wbstpoc.com/2015/01/WBSTMobileInterface/";
        private static final String METHOD_NAME = "authenticate";

        public LoginAuthenticator(String user, String password,String userId) {
            this.mUserName = uname;
            this.password = pass;
            this.userId=userId;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Please wait... Authenticating login details", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            String responseStatus = null;
            String responseUserId=null;
            String userId=null;
            boolean resstatus;

            Log.d("App", "doing in background");
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("userName", this.mUserName);
            request.addProperty("password", this.password);
            request.addProperty("userId",this.userId);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.implicitTypes = true;

            // Says that the soap webservice is a .Net service
            envelope.dotNet = true;

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);


            try {

                List reqHeaders = null;
                List respHeaders = androidHttpTransport.call(SOAP_ACTION, envelope, reqHeaders);
                //Parsing the HTTP response. HTTP response comes as Key/Value Pair. First Key contains the HTTP response 200 OK.

                for (int ix = 0; ix < respHeaders.size(); ix++) {
                    HeaderProperty hp = (HeaderProperty) respHeaders.get(ix);
                    System.out.println("Header" + ix + "=" + hp.getKey() + " / " + hp.getValue());
////Looking for HTTP response HTTP 200 OK from the HTTP response. If OK, setting the response status flag as true.
                    if (hp.getValue().contains("200 OK"))
                        resstatus = true;


                    String responsexml = androidHttpTransport.responseDump;
                    String requestXml = androidHttpTransport.requestDump;
// Log.i("envelope", "" + envelope);
                    Log.i("xml", "" + responsexml);
                    Log.i("xml1", "" + requestXml);


                    SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;

                    System.out.println("resultsRequestSOAP" + resultsRequestSOAP);
                    // savingCont.setText(resultsRequestSOAP.toString());
                    System.out.println("Response::" + resultsRequestSOAP.toString());
                    responseUserId=(resultsRequestSOAP.toString());

                  //  if ((responseUserId== "authenticateResponse{status=SUCCEES; userId=1001; }"))
                //    {
///*
                 /*   Intent i = new Intent(SignIn.this, SavingsActivity.class);
                    startActivity(i);
                    finish();
*/
                     //   Toast.makeText(getApplicationContext(), "Authentication success", Toast.LENGTH_SHORT).show();




                //    }
                    //else
                      //  break;



                }

               // return responseStatus;
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } return responseStatus;





        }

        @Override
        protected void onPostExecute(Object responseStatus) {
            super.onPostExecute(responseStatus);

          //  if (responseStatus == "1001")

            {

               Intent i = new Intent(SignIn.this, SavingsActivity.class);
             //   i.putExtra("userId", 1001);
              startActivity(i);
             finish();
             }

        }


    }
}



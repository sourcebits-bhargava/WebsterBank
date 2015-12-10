package com.sourcebits.webster.websterbank;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.Authenticator;
import java.util.List;

/**
 * Created by Bhargava Gugamsetty on 11/27/2015.
 */
public class SavingsFragment extends Fragment {
    private SavingsAuthenticator savingsAuthenticator;
    private String userId;
//    private
    //  private


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_savings, container, false);
        savingsAuthenticator = new SavingsAuthenticator(userId);
        savingsAuthenticator.execute();

        return rootView;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //savingsAuthenticator = new savingsAuthenticator(uname, pass);
        //  savingsAuthenticator.execute();
        /*
        getAccounts
          */
     //   Intent i = getIntent();
       // i.getStringExtra("userId");


    }

    private class SavingsAuthenticator extends AsyncTask {
        private String userId="1001";

        private static final String NAMESPACE = "http://schemas.wbstpoc.com/2015/01/WBSTMobileInterface";
        private static final String URL = "http://12.216.193.170/mobilePOC/?WSDL";
        private static final String SOAP_ACTION = "http://schemas.wbstpoc.com/2015/01/WBSTMobileInterface/";
        private static final String METHOD_NAME = "getAccounts";

        public SavingsAuthenticator(String userId) {
            this.userId=userId;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String responseAccounts = null;
            boolean resstatus;
            Log.d("App", "doing in background");
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("userID", this.userId);



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
                    responseAccounts = (resultsRequestSOAP.toString());

                    if (responseAccounts == "status=SUCCESS") {
//

                        // Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();


                    }


                }



            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }return responseAccounts;


        }
        @Override
        protected void onPostExecute (Object o){
            super.onPostExecute(o);

        }
    }
}

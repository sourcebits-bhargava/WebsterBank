package com.sourcebits.webster.websterbank;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by Bhargava Gugamsetty on 12/4/2015.
 */
public class SoapRequestResponse extends Activity {



        private static final String NAMESPACE = "http://schemas.wbstpoc.com/2015/01/WBSTMobileInterface";
        private static final String URL = "http://12.216.193.170/mobilePOC/?WSDL";
        private static final String SOAP_ACTION = "http://schemas.wbstpoc.com/2015/01/WBSTMobileInterface/";
        private static final String METHOD_NAME = "getAccountHistory";

        private TextView savingCont;



        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_savings_screen);

            savingCont = (TextView) findViewById(R.id.savings_cont1);
//Start of SOAP request
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("userName","uname");
            request.addProperty("password","pass");

            SoapObject requesta = new SoapObject(NAMESPACE, METHOD_NAME);
            requesta.addProperty("userId","userid");


                    SoapObject requestah = new SoapObject(NAMESPACE, METHOD_NAME);
            requestah.addProperty("number","number");

                    SoapObject requestga = new SoapObject(NAMESPACE, METHOD_NAME);
            requestga.addProperty("AccountsHistory","account history");




            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            // Says that the soap webservice is a .Net service
            envelope.dotNet = true;

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);

                //SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
                // SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
                SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;


                savingCont.setText(resultsRequestSOAP.toString());
                System.out.println("Response::"+resultsRequestSOAP.toString());

            } catch (Exception e) {
                System.out.println("Error"+e);
            }

        }


    //end of SOAP request



    }




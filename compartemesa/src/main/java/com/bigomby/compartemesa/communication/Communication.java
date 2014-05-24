package com.bigomby.compartemesa.communication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.bigomby.compartemesa.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Communication implements Runnable{

    private String METHOD_NAME = "test"; // our webservice method name
    private String NAMESPACE = "http://192.168.2.188"; // Here package name in webservice with reverse order.
    private String SOAP_ACTION = NAMESPACE + METHOD_NAME; // NAMESPACE + method name
    private static final String URL = "http://192.168.2.188:8080/axis/services/mesas-ave"; // you must use ipaddress here, don’t use Hostname or localhost

    public void run () {

        Log.d("AXIS", "Intentando comunicación");

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("name", "Diego es el mejor");
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);
            Object result = envelope.getResponse();
            Log.d("AXIS", "Result:" + result.toString());
        } catch (Exception E) {
            E.printStackTrace();
        }
    }
}
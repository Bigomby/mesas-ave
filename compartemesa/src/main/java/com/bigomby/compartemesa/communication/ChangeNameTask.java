package com.bigomby.compartemesa.communication;

import android.os.AsyncTask;

import com.bigomby.compartemesa.ComparteMesaApplication;
import com.bigomby.compartemesa.interfaces.TableOperationCallback;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class ChangeNameTask extends AsyncTask<String, Void, Void> {

    private String METHOD_NAME = "changeName";
    private String NAMESPACE = "http://192.168.2.188";
    private String SOAP_ACTION = NAMESPACE + METHOD_NAME;
    private static final String URL = "http://192.168.2.188:8080/axis/services/mesas-ave";

    @Override
    protected Void doInBackground(String... name) {

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("userUUID", ComparteMesaApplication.getMyUUID());
            request.addProperty("newName", name[0]);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);
        } catch (Exception E) {
            E.printStackTrace();
        }

        return null;
    }
}

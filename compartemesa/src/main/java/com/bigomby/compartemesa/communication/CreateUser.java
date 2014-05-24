package com.bigomby.compartemesa.communication;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.bigomby.compartemesa.ComparteMesaApplication;
import com.bigomby.compartemesa.data.User;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class CreateUser extends AsyncTask<SharedPreferences, Void, String>{
    private String METHOD_NAME = "createUser";
    private String NAMESPACE = "http://192.168.2.188";
    private String SOAP_ACTION = NAMESPACE + METHOD_NAME;
    private static final String URL = "http://192.168.2.188:8080/axis/services/mesas-ave";


    @Override
    protected String doInBackground(SharedPreferences... pref) {

        Log.d("AXIS", "Intentando crear usuario");

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("name", "Diego Fernández");
            request.addProperty("email", "bigomby@gmail.com");
            request.addProperty("passwd", "123456");
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);
            envelope.bodyIn.getClass();
            SoapObject obj = (SoapObject) envelope.getResponse();
            String userUUID = obj.getPropertyAsString(0);

            SharedPreferences.Editor editor = pref[0].edit();
            editor.putString("userUUID", userUUID);
            editor.commit();

            Log.d("CREATE_USER", "Creado usuario: " + obj.getPropertyAsString(1));

        } catch (Exception E) {
            E.printStackTrace();
        }
        return null;
    }
}

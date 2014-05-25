package com.bigomby.compartemesa.communication;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.bigomby.compartemesa.ComparteMesaApplication;
import com.bigomby.compartemesa.data.Table;
import com.bigomby.compartemesa.data.User;
import com.bigomby.compartemesa.interfaces.TableOperationCallback;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class CreateUser extends AsyncTask<String, Void, Void> {

    private String METHOD_NAME = "createUser";
    private String NAMESPACE = ComparteMesaApplication.NAMESPACE;
    private String SOAP_ACTION = NAMESPACE + METHOD_NAME;
    private static final String URL = ComparteMesaApplication.URL;

    private TableOperationCallback tableOperationCallback;

    public CreateUser(TableOperationCallback tableOperationCallback) {
        this.tableOperationCallback = tableOperationCallback;
    }

    @Override
    protected Void doInBackground(String... params) {

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("name", params[0]);
            request.addProperty("email", "null");
            request.addProperty("passwd", "null");
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);
            envelope.bodyIn.getClass();
            SoapObject obj = (SoapObject) envelope.getResponse();
            String userUUID = obj.getPropertyAsString(0);

            ComparteMesaApplication.setMyUUID(userUUID);
            Log.d("MAIN", "Creado nuevo usuario con UUID: " + ComparteMesaApplication.getMyUUID());

        } catch (Exception E) {
            E.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void param) {
        tableOperationCallback.onTaskDone();
    }
}

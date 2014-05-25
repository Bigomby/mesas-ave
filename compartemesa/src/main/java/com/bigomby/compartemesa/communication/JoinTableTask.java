package com.bigomby.compartemesa.communication;

import android.os.AsyncTask;
import android.util.Log;

import com.bigomby.compartemesa.ComparteMesaApplication;
import com.bigomby.compartemesa.data.Table;
import com.bigomby.compartemesa.interfaces.TableOperationCallback;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class JoinTableTask extends AsyncTask<String, Void, Void> {

    private String METHOD_NAME = "joinTable";
    private String NAMESPACE = ComparteMesaApplication.NAMESPACE;
    private String SOAP_ACTION = NAMESPACE + METHOD_NAME;
    private static final String URL = ComparteMesaApplication.URL;

    TableOperationCallback tableOperationCallback;

    public JoinTableTask(TableOperationCallback tableOperationCallback) {
        this.tableOperationCallback = tableOperationCallback;
    }

    @Override
    protected Void doInBackground(String... uuid) {

        Log.d("AXIS", "Intentando salvar la mesa");

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("userUUID", ComparteMesaApplication.getMyUUID());
            request.addProperty("tableUUID", uuid[0]);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);
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

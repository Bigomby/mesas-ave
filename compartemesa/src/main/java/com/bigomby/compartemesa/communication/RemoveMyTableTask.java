package com.bigomby.compartemesa.communication;

import android.os.AsyncTask;

import com.bigomby.compartemesa.ComparteMesaApplication;
import com.bigomby.compartemesa.data.Table;
import com.bigomby.compartemesa.interfaces.TableOperationCallback;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class RemoveMyTableTask extends AsyncTask<Void, Void, Integer> {

    private String METHOD_NAME = "removeTable";
    private String NAMESPACE = "http://192.168.2.188";
    private String SOAP_ACTION = NAMESPACE + METHOD_NAME;
    private static final String URL = "http://192.168.2.188:8080/axis/services/mesas-ave";

    private TableOperationCallback tableOperationCallback;

    public RemoveMyTableTask(TableOperationCallback tableOperationCallback) {
        this.tableOperationCallback = tableOperationCallback;
    }

    protected Integer doInBackground(Void... uuid) {

        Integer error = 0;

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("userUUID", ComparteMesaApplication.getMyUUID());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);

            error = (Integer) envelope.getResponse();

        } catch (Exception E) {
            E.printStackTrace();
        }

        return error;
    }

    @Override
    protected void onPostExecute(Integer error) {
        tableOperationCallback.onTaskDone(error);
    }
}

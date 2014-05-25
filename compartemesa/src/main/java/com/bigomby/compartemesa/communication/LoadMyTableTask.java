package com.bigomby.compartemesa.communication;

import android.os.AsyncTask;

import com.bigomby.compartemesa.ComparteMesaApplication;
import com.bigomby.compartemesa.data.Table;
import com.bigomby.compartemesa.interfaces.TableOperationCallback;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class LoadMyTableTask extends AsyncTask<String, Void, Table> {

    private String METHOD_NAME = "findTableByUser";
    private String NAMESPACE = "http://192.168.2.188";
    private String SOAP_ACTION = NAMESPACE + METHOD_NAME;
    private static final String URL = "http://192.168.2.188:8080/axis/services/mesas-ave";

    private TableOperationCallback tableOperationCallback;

    public LoadMyTableTask(TableOperationCallback tableOperationCallback) {
        this.tableOperationCallback = tableOperationCallback;
    }

    @Override
    protected Table doInBackground(String... uuid) {

        Table myTable = null;


        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("userUUID", ComparteMesaApplication.getMyUUID());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);

            myTable = new Table((SoapObject) envelope.getResponse());

        } catch (Exception E) {
            E.printStackTrace();
        }

        return myTable;
    }

    @Override
    protected void onPostExecute(Table myTable) {
        tableOperationCallback.onTaskDone(myTable);
    }
}

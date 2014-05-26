package com.bigomby.compartemesa.communication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigomby.compartemesa.ComparteMesaApplication;
import com.bigomby.compartemesa.data.Table;
import com.bigomby.compartemesa.interfaces.TableOperationCallback;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class LoadTablesTask extends AsyncTask<Void, Void, List<Table>> {

    private String METHOD_NAME = "getTables";
    private String NAMESPACE = ComparteMesaApplication.NAMESPACE;
    private String SOAP_ACTION = NAMESPACE + METHOD_NAME;
    private static final String URL = ComparteMesaApplication.URL;

    List<Table> tables;
    private View view;
    private LayoutInflater inflater;
    private ViewGroup container;
    private Context context;

    TableOperationCallback tableOperationCallback;

    public LoadTablesTask(TableOperationCallback tableOperationCallback) {
        this.tableOperationCallback = tableOperationCallback;
    }

    @Override
    protected List<Table> doInBackground(Void... params) {

        tables = new ArrayList<Table>();
        Log.d("AXIS", "Intentando cargar las mesas");

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);

            Vector vector = (Vector) envelope.getResponse();

            for (int i = 0 ; i < vector.size() ; i++) {
                tables.add(new Table((SoapObject) vector.get(i)));
            }

        } catch (Exception E) {
            E.printStackTrace();
        }

        return tables;
    }

    @Override
    protected void onPostExecute(List<Table> tables) {
        tableOperationCallback.onTaskDone(tables);
    }
}

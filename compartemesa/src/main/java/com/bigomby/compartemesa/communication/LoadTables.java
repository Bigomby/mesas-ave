package com.bigomby.compartemesa.communication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.bigomby.compartemesa.ComparteMesaApplication;
import com.bigomby.compartemesa.R;
import com.bigomby.compartemesa.data.Table;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LoadTables extends AsyncTask<Void, Void, List<Table>> {

    private String METHOD_NAME = "getTables";
    private String NAMESPACE = "http://192.168.2.188";
    private String SOAP_ACTION = NAMESPACE + METHOD_NAME;
    private static final String URL = "http://192.168.2.188:8080/axis/services/mesas-ave";

    List<Table> tables;
    private View view;
    private LayoutInflater inflater;
    private ViewGroup container;
    private Context context;

    public LoadTables(View view, LayoutInflater inflater, ViewGroup container, Context context) {
        this.view = view;
        this.inflater = inflater;
        this.container = container;
        this.context = context;
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
            Object[] result = (Object[]) envelope.getResponse();

            for (int i = 0; i < result.length; i++) {
                Log.d("AXI", "Descargada mesa: " + ((Table) result[i]).getUUID());
                tables.add((Table) result[i]);
            }

            Log.d("AXIS", "Result:" + result.toString());
        } catch (Exception E) {
            E.printStackTrace();
        }

        return tables;
    }

    @Override
    protected void onPostExecute(List<Table> tables) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        if (tables != null && !tables.isEmpty()) {
            List<String> titles = new ArrayList<String>();

            Iterator<Table> it = tables.iterator();

            while (it.hasNext()) {
                Table table = it.next();
                String originName = ComparteMesaApplication.cities.getCityName(table.getOrigin());
                String destinyName = ComparteMesaApplication.cities.getCityName(table.getDestiny());
                titles.add(originName + " --> " + destinyName);
            }

            ListAdapter adapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_list_item_1, titles);

            ListView lv = (ListView) view.findViewById(R.id.tablesList);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    // TODO
                }
            });

        }
    }
}

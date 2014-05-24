package com.bigomby.compartemesa.communication;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigomby.compartemesa.ComparteMesaApplication;
import com.bigomby.compartemesa.R;
import com.bigomby.compartemesa.data.Table;
import com.bigomby.compartemesa.tables.TableFragment;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;

public class LoadMyTable extends AsyncTask<String, Void, Table> {

    private Fragment tableFragment;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private String METHOD_NAME = "findTableByUser";
    private String NAMESPACE = "http://192.168.2.188";
    private String SOAP_ACTION = NAMESPACE + METHOD_NAME;
    private static final String URL = "http://192.168.2.188:8080/axis/services/mesas-ave";

    List<Table> tables;
    private View view;
    private LayoutInflater inflater;
    private ViewGroup container;
    private Context context;

    public LoadMyTable(Context context, Fragment fragment, Fragment tableFragment, FragmentManager fragmentManager) {
        this.context = context;
        this.fragment = fragment;
        this.tableFragment = tableFragment;
        this.fragmentManager = fragmentManager;
    }

    @Override
    protected Table doInBackground(String... uuid) {

        Log.d("AXIS", "Intentando cargar mi mesa");
        Table myTable = null;


        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("userUUID", uuid[0]);
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

        ComparteMesaApplication app = (ComparteMesaApplication) context.getApplicationContext();
        app.setMyTable(myTable);

        if (fragment == null || fragment instanceof TableFragment) {
            tableFragment = new TableFragment();
            fragment = tableFragment;
        }

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }
}

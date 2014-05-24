package com.bigomby.compartemesa.communication;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class CreateTable extends AsyncTask<Void, Void, Void> {

    private String METHOD_NAME = "createTable";
    private String NAMESPACE = "http://192.168.2.188";
    private String SOAP_ACTION = NAMESPACE + METHOD_NAME;
    private static final String URL = "http://192.168.2.188:8080/axis/services/mesas-ave";

    private int origin;
    private int destiny;
    private String userUUID;

    public CreateTable(int origin, int destiny, String userUUID) {
        this.origin = origin;
        this.destiny = destiny;
        this.userUUID = userUUID;
    }

    @Override
    protected Void doInBackground(Void... params) {

        Log.d("AXIS", "Intentando salvar la mesa");

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("origin", origin);
            request.addProperty("destiny", destiny);
            request.addProperty("myUuid", userUUID);
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

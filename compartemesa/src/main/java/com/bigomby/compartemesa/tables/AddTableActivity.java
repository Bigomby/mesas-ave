package com.bigomby.compartemesa.tables;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.bigomby.compartemesa.ComparteMesaApplication;
import com.bigomby.compartemesa.R;
import com.bigomby.compartemesa.communication.CreateTable;

import java.util.List;

public class AddTableActivity extends Activity implements AdapterView.OnItemSelectedListener {

    int origin;
    int destiny;
    SharedPreferences pref = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  Configuro la ActionBar para esta actividad
        configActionBar();

        //  Ajusto el layout a usar y relleno los datos de los Views
        setContentView(R.layout.add_table);

        List<String> stringCities = ComparteMesaApplication.cities.toListString();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, stringCities);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner originSpinner = (Spinner) findViewById(R.id.origin_spinner);
        originSpinner.setAdapter(adapter);
        originSpinner.setOnItemSelectedListener(this);

        final Spinner destinySpinner = (Spinner) findViewById(R.id.destiny_spinner);
        destinySpinner.setAdapter(adapter);
        destinySpinner.setOnItemSelectedListener(this);
    }

    /*
     * Función de callback para los spinners
     */
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        if (parent.getId() == R.id.origin_spinner) {
            origin = pos;
        } else if (parent.getId() == R.id.destiny_spinner) {
            destiny = pos;
        }
    }

    /*
     *  Función de callback para los spinners
     */
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    /*
     *  Función para cargar el nombre de usuario de las preferencias
     */
    private String loadName() {
        String name;
        int mode = Activity.MODE_PRIVATE;
        pref = getSharedPreferences("prefs", mode);
        return pref.getString("name", "Usuario");
    }

    /*
     *  Función para configurar los botones de la ActionBar
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void configActionBar() {
        final LayoutInflater inflater = (LayoutInflater) getActionBar().getThemedContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);

        final View customActionBarView = inflater.inflate(
                R.layout.actionbar_custom_view_done_cancel, null);
        customActionBarView.findViewById(R.id.actionbar_done).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addTable(((ComparteMesaApplication) getApplication()).getMyUUID());
                        finish();
                    }
                }
        );

        customActionBarView.findViewById(R.id.actionbar_cancel).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(
                ActionBar.DISPLAY_SHOW_CUSTOM,
                ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
                        | ActionBar.DISPLAY_SHOW_TITLE
        );
        actionBar.setCustomView(customActionBarView,
                new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT)
        );
    }

    private void addTable(String userUUID) {
        CreateTable createTable = new CreateTable(origin, destiny, userUUID);
        createTable.execute();
    }
}
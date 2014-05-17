package com.bigomby.compartemesa.tables;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.bigomby.compartemesa.R;
import com.bigomby.compartemesa.data.Cities;
import com.bigomby.compartemesa.data.City;
import com.bigomby.compartemesa.data.Table;
import com.bigomby.compartemesa.data.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diego on 15/05/14.
 */
public class AddTableActivity extends Activity implements AdapterView.OnItemSelectedListener {

    Spinner originSpinner;
    Spinner destinySpinner;
    City origin;
    City destiny;
    Table table = null;

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onCreate(Bundle outState) {
        super.onCreate(outState);

        this.table = table;
        // BEGIN_INCLUDE (inflate_set_custom_view)
        // Inflate a "Done/Cancel" custom action bar view.
        final LayoutInflater inflater = (LayoutInflater) getActionBar().getThemedContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View customActionBarView = inflater.inflate(
                R.layout.actionbar_custom_view_done_cancel, null);
        customActionBarView.findViewById(R.id.actionbar_done).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        table = new Table(origin , destiny, new User("Diego"));
                        Log.d("DIEGO:", "La tabla creada tiene origen " + table.getOrigin());
                        Log.d("DIEGO:", "La tabla creada tiene destino " + table.getDestiny());
                        Log.d("DIEGO:", "La tabla creada tiene el usuario " + table.getUsers().get(0));
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("table", table);
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    }
                }
        );
        customActionBarView.findViewById(R.id.actionbar_cancel).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent returnIntent = new Intent();
                        setResult(RESULT_CANCELED, returnIntent);
                        finish();
                    }
                }
        );
        // Show the custom action bar view and hide the normal Home icon and title.
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
        // END_INCLUDE (inflate_set_custom_view)
        setContentView(R.layout.add_table);

        Cities cities = new Cities();
        List<String> mCities = cities.toListString();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, mCities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        originSpinner = (Spinner) findViewById(R.id.origin_spinner);
        originSpinner.setAdapter(adapter);
        originSpinner.setOnItemSelectedListener(this);

        destinySpinner = (Spinner) findViewById(R.id.destiny_spinner);
        destinySpinner.setAdapter(adapter);
        destinySpinner.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        Log.d("MESA", "Click en " + parent.getId());
        if (parent.getId() == R.id.origin_spinner) {
            origin = new City((String) parent.getItemAtPosition(pos));
            Log.d("MESA", "Origen configurado a: " + origin.getName());
        } else if (parent.getId() == R.id.destiny_spinner) {
            destiny = new City((String) parent.getItemAtPosition(pos));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
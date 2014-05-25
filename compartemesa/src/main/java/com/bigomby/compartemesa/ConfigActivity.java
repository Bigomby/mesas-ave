package com.bigomby.compartemesa;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

import com.bigomby.compartemesa.communication.ChangeNameTask;


public class ConfigActivity extends ActionBarActivity {

    SharedPreferences pref = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        getSupportActionBar().setTitle("Configuración");
        EditText nombre = (EditText) findViewById(R.id.username);
        nombre.setText(loadUserName());
    }

    /*
     *  Se uliliza en el Onclick del xml para obtener el nuevo nombre
    */

    public void saveUserName(View view) {
        int mode = Activity.MODE_PRIVATE;
        pref = getSharedPreferences("prefs", mode);

        EditText nombre = (EditText) findViewById(R.id.username);
        String nuevonombre = nombre.getText().toString();

        ChangeNameTask changeNameTask = new ChangeNameTask();
        changeNameTask.execute(nuevonombre);

        SharedPreferences.Editor editor = pref.edit();
        editor.putString("myName", nuevonombre);
        editor.commit();
        finish();
    }

    public String loadUserName() {
        int mode = Activity.MODE_PRIVATE;
        pref = getSharedPreferences("prefs", mode);
        return pref.getString("myName", "Usuario");
    }
}
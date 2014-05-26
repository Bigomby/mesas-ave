package com.bigomby.compartemesa;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bigomby.compartemesa.communication.ChangeNameTask;
import com.bigomby.compartemesa.communication.CreateUser;
import com.bigomby.compartemesa.communication.LoadMyTableTask;
import com.bigomby.compartemesa.communication.LoadTablesTask;
import com.bigomby.compartemesa.communication.RemoveMyTableTask;
import com.bigomby.compartemesa.data.Table;
import com.bigomby.compartemesa.interfaces.TableOperationCallback;
import com.bigomby.compartemesa.search.SearchFragment;
import com.bigomby.compartemesa.tables.AddTableActivity;
import com.bigomby.compartemesa.tables.TableFragment;

import java.util.List;

public class MainActivity extends ActionBarActivity {

    private CharSequence tituloSeccion;
    private ActionBarDrawerToggle drawerToggle;
    public List<Table> tables;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);

        // Cargo las preferencias
        loadPrefs();

        // Inicializo el NavigationDrawer y la ActionBar
        init();

    }

    /**
     * Carga las preferencias de la aplicación. Si no hay un usuario configurado
     * pedirá login o registro.
     */
    private void loadPrefs() {
        int mode = Activity.MODE_PRIVATE;
        final SharedPreferences pref = getSharedPreferences("prefs", mode);
        String myUUID = pref.getString("myUUID", "null");
        String myName = pref.getString("myName", "Usuario");

        Log.d("PREFS", "Cargada UUID: " + myUUID);

        // Cargo el UUID almacenado, si no hay ninguno pido uno nuevo al servidor

        if (myUUID.contentEquals("null")) {
            CreateUser createUser = new CreateUser(new TableOperationCallback() {
                @Override
                public void onTaskDone(Object... params) {
                    String myName = pref.getString("myName", "User");

                    ChangeNameTask changeNameTask = new ChangeNameTask();
                    changeNameTask.execute(myName);
                }
            });
            createUser.execute(myName);
        } else {
            ComparteMesaApplication.setMyUUID(myUUID);
            Log.d("MAIN", "Iniciada aplicación con UUID: " + ComparteMesaApplication.getMyUUID());
        }
    }

    /**
     * Función de callback para el NavigationDrawer y la ActionBar
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    /**
     * Función de callback para el NavigationDrawer y la ActionBar
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Función de callback para el NavigationDrawer y la ActionBar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle presses on the action bar items
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_add:
                intent = new Intent(this, AddTableActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_discard:
                deleteTable();
                onResume();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Elimina la mesa a la que pertenecemos
     */
    private void deleteTable() {


        int mode = Activity.MODE_PRIVATE;
        SharedPreferences pref = getSharedPreferences("prefs", mode);

        SharedPreferences.Editor editor = pref.edit();
        editor.remove("myTableUUID");
        editor.commit();
        setSupportProgressBarIndeterminateVisibility(false);

        RemoveMyTableTask removeMyTableTask = new RemoveMyTableTask(new TableOperationCallback() {

            @Override
            public void onTaskDone(Object... object) {

                Integer error = (Integer) object[0];

                if (error != null && error == 0) {
                    ComparteMesaApplication.setMyTable(new Table());
                } else {
                    ComparteMesaApplication.setMyTable(null);
                }

                setSupportProgressBarIndeterminateVisibility(false);

            }
        });
        removeMyTableTask.execute();
    }

    /**
     * Función de callback para el botón añadir cuando no hay mesas
     */
    public void onClick(View view) {
        if (view.getId() == R.id.create_table) {
            Intent intent = new Intent(this, AddTableActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Inicializo el NavigatonDrawer y la ActionBar
     */
    private void init() {
        final String[] opcionesMenu = getResources().getStringArray(R.array.navigation_drawer_elements);
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ListView drawerList = (ListView) findViewById(R.id.left_drawer);

        drawerList.setAdapter(new ArrayAdapter<String>(
                getSupportActionBar().getThemedContext(),
                android.R.layout.simple_list_item_1, opcionesMenu));

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view,
                                    int position, long id) {

                switch (position) {
                    case 0:
                        loadFragmentTable();
                        break;
                    case 1:
                        loadSearch();
                        break;
                    case 2:
                        configActivity();
                        break;
                }

                drawerList.setItemChecked(position, true);
                drawerLayout.closeDrawer(drawerList);
            }

        });

        drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                R.drawable.ic_navigation_drawer,
                R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(tituloSeccion);
                ActivityCompat.invalidateOptionsMenu(MainActivity.this);
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(getTitle());
                ActivityCompat.invalidateOptionsMenu(MainActivity.this);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public void configActivity() {
        Intent intent = new Intent(this, ConfigActivity.class);
        startActivity(intent);
    }

    private void loadSearch() {

        LoadTablesTask loadTables = new LoadTablesTask(new TableOperationCallback() {
            @Override
            public void onTaskDone(Object... loadedTables) {


                tables = (List<Table>) loadedTables[0];

                FragmentManager fragmentManager =
                        getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, new SearchFragment())
                        .commit();

                tituloSeccion = "Lista de mesas";
                getSupportActionBar().setTitle(tituloSeccion);
                setSupportProgressBarIndeterminateVisibility(false);
            }
        });
        setSupportProgressBarIndeterminateVisibility(true);
        loadTables.execute();
    }

    /**
     * Cargo el fragmento al inicio de la aplicación o lo recargo si vengo de otra
     * actividad posterior.
     */

    private void loadFragmentTable() {
        setSupportProgressBarIndeterminateVisibility(true);

        LoadMyTableTask loadMyTableTask = new LoadMyTableTask(new TableOperationCallback() {

            @Override
            public void onTaskDone(Object... object) {

                Table myTable = (Table) object[0];

                if (myTable != null)
                    ComparteMesaApplication.setMyTable(myTable);

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, new TableFragment())
                        .commit();

                tituloSeccion = getTitle();
                getSupportActionBar().setTitle(tituloSeccion);
                setSupportProgressBarIndeterminateVisibility(false);

            }
        });
        loadMyTableTask.execute();
    }

    public List<Table> getTables() {
        return tables;
    }

    @Override
    public void onResume() {
        super.onResume();

        loadFragmentTable();
    }

    @Override
    public void onPause() {
        super.onPause();

        // Guardo las preferencias en los estados de pausa

        String myUUID = ComparteMesaApplication.getMyUUID();

        if (myUUID != null) {
            int mode = Activity.MODE_PRIVATE;
            SharedPreferences pref = getSharedPreferences("prefs", mode);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("myUUID", myUUID);
            editor.commit();
            Log.d("PREFS", "Guardada UUID: " + myUUID);
        }
    }
}
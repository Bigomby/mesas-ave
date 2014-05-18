package com.bigomby.compartemesa;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bigomby.compartemesa.data.myTableSQLConfigManager;
import com.bigomby.compartemesa.search.SearchFragment;
import com.bigomby.compartemesa.tables.AddTableActivity;
import com.bigomby.compartemesa.tables.TableFragment;

public class MainActivity extends ActionBarActivity {

    private String tituloSeccion;
    private ActionBarDrawerToggle drawerToggle;
    private Fragment fragment = null;
    private Fragment tableFragment;
    private Fragment searchFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchFragment = new SearchFragment();

        //  Inicializo el NavigationDrawer y la ActionBar
        init();

    }

    /**
     * ***************************************************************
     * Función de callback para el NavigationDrawer y la ActionBar    *
     * ****************************************************************
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    /**
     * ***************************************************************
     * Función de callback para el NavigationDrawer y la ActionBar    *
     * ****************************************************************
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * ***************************************************************
     * Función de callback para el NavigationDrawer y la ActionBar    *
     * ****************************************************************
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
                removeMyTable();
                fragment = tableFragment;
                FragmentManager fragmentManager =
                        getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * ***************************************************************
     * Función de callback para el NavigationDrawer y la ActionBar    *
     * ****************************************************************
     */
    public void onClick(View view) {
        if (view.getId() == R.id.create_table) {
            Intent intent = new Intent(this, AddTableActivity.class);
            startActivity(intent);
        }
    }

    /**
     * ********************************************
     * Inicializo el NavigatonDrawer y la ActionBar *
     * **********************************************
     */
    private void init() {
        final String[] opcionesMenu = getResources().getStringArray(R.array.navigation_drawer_elements);
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ListView drawerList = (ListView) findViewById(R.id.left_drawer);
        final CharSequence tituloApp = getTitle();

        drawerList.setAdapter(new ArrayAdapter<String>(
                getSupportActionBar().getThemedContext(),
                android.R.layout.simple_list_item_1, opcionesMenu));

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view,
                                    int position, long id) {

                switch (position) {
                    case 0:
                        fragment = tableFragment;
                        break;
                    case 1:
                        fragment = searchFragment;
                        break;
                    case 2:
                        Intent intent = new Intent(view.getContext(), ConfigActivity.class);
                        startActivity(intent);
                        break;
                }

                FragmentManager fragmentManager =
                        getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();

                drawerList.setItemChecked(position, true);

                if (position != 0) {
                    tituloSeccion = opcionesMenu[position];
                    getSupportActionBar().setTitle(tituloSeccion);
                } else {
                    tituloSeccion = (String) getTitle();
                }


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
                getSupportActionBar().setTitle(tituloApp);
                ActivityCompat.invalidateOptionsMenu(MainActivity.this);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Actualizo el fragmento
        ComparteMesaApplication app = (ComparteMesaApplication) getApplication();
        if (app.isDatabaseUpdate()) {
            if (fragment == null || fragment instanceof TableFragment) {
                tableFragment = new TableFragment();
                fragment = tableFragment;
            }

            FragmentManager fragmentManager =
                    getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }
    }

    private void removeMyTable() {

        myTableSQLConfigManager myTableDb = new myTableSQLConfigManager(this, "myTableDb", null, 1);
        myTableDb.removeMyTable();

        onResume();
    }
}
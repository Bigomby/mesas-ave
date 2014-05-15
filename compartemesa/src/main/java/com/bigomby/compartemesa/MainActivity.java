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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bigomby.compartemesa.tables.AddTableActivity;
import com.bigomby.compartemesa.tables.TableFragment;

public class MainActivity extends ActionBarActivity {

    private String[] opcionesMenu;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private String tituloSeccion;
    private CharSequence tituloApp;
    private ActionBarDrawerToggle drawerToggle;
    Fragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager =
                getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, new Home())
                .commit();

        tituloSeccion = (String) getTitle();

        opcionesMenu = getResources().getStringArray(R.array.navigation_drawer_elements);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        drawerList.setAdapter(new ArrayAdapter<String>(
                getSupportActionBar().getThemedContext(),
                android.R.layout.simple_list_item_1, opcionesMenu));


        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view,
                                    int position, long id) {

                fragment = null;

                switch (position) {
                    case 0:
                        fragment = new Home();
                        break;
                    case 1:
                        fragment = new TableFragment();
                        break;
                    case 2:
                        fragment = new Search();
                        break;
                    case 3:
                        fragment = new Config();
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

        tituloApp = getTitle();

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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(this, AddTableActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
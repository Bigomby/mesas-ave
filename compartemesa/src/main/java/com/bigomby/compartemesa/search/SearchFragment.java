package com.bigomby.compartemesa.search;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import com.bigomby.compartemesa.data.myTableSQLConfigManager;
import com.bigomby.compartemesa.tables.TableFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SearchFragment extends Fragment {

    private FragmentActivity myContext;
    myTableSQLConfigManager myTableDb;
    List<Table> tables;
    int position;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        myTableDb = new myTableSQLConfigManager(getActivity(), "myTableDb", null, 1);
        tables = myTableDb.loadTables();

        if (!tables.isEmpty()) {
            List<String> titles = new ArrayList<String>();

            Iterator<Table> it = tables.iterator();

            while (it.hasNext()) {
                Table table = it.next();
                String originName = ComparteMesaApplication.cities.getCityName(table.getOrigin());
                String destinyName = ComparteMesaApplication.cities.getCityName(table.getDestiny());
                titles.add(originName + " --> " + destinyName);
            }

            ListAdapter adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, titles);

            ListView lv = (ListView) view.findViewById(R.id.tablesList);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    myTableDb.saveMyTable(tables.get(position));

                    Fragment fragment = new TableFragment();

                    android.support.v4.app.FragmentManager fragmentManager =
                            myContext.getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .commit();

                }
            });
        }

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

}
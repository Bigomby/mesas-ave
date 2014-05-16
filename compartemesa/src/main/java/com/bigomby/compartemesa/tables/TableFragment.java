package com.bigomby.compartemesa.tables;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.bigomby.compartemesa.R;
import com.bigomby.compartemesa.data.Table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TableFragment extends Fragment {

    private Table myTable = null;

    // This is the Adapter being used to display the list's data
    SimpleCursorAdapter mAdapter;

    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle outState) {
        //super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        View view = null;
        Bundle bundle = getArguments();

        if (bundle != null) {
            myTable = (Table) this.getArguments().getSerializable("table");
            view = inflater.inflate(R.layout.my_table, container, false);
        } else {
            view = inflater.inflate(R.layout.no_table, container, false);
        }

        return view;

    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_activity_actions, menu);
    }

}
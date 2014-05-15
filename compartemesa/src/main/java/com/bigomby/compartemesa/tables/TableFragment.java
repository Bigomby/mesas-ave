package com.bigomby.compartemesa.tables;

import android.graphics.drawable.Drawable;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TableFragment extends Fragment {

    // This is the Adapter being used to display the list's data
    SimpleCursorAdapter mAdapter;
    private List<Table> tables;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onCreateView(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Iterator<Table> it = tables.iterator();
        List<String> titles = new ArrayList<String>();
        int i = 0;

        while (it.hasNext()) {
            Table table = it.next();
            String originName = table.getOrigin().getName();
            String destinyName = table.getDestiny().getName();
            titles.add(originName + " --> " + destinyName);
        }

        ListAdapter adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, titles);

        ListView lv = (ListView) getActivity().findViewById(R.id.tablesList);
        lv.setAdapter(adapter);

    }

    private void createTable() {
        // Llamar a actividad de crear mesa
    }

    private void removeTable() {
        // Acción para eliminar mesa
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_activity_actions, menu);
    }
}
package com.bigomby.compartemesa.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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

public class SearchFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    /*
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
*/
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
}
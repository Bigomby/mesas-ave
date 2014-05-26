package com.bigomby.compartemesa.search;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.bigomby.compartemesa.ComparteMesaApplication;
import com.bigomby.compartemesa.MainActivity;
import com.bigomby.compartemesa.R;
import com.bigomby.compartemesa.communication.JoinTableTask;
import com.bigomby.compartemesa.data.Table;
import com.bigomby.compartemesa.interfaces.TableOperationCallback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SearchFragment extends Fragment {

    private FragmentActivity myContext;
    List<Table> tables;
    int position;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        tables = ((MainActivity) myContext).getTables();

        if (tables != null && !tables.isEmpty()) {
            List<String> titles = new ArrayList<String>();

            Iterator<Table> it = tables.iterator();

            while (it.hasNext()) {
                Table table = it.next();
                String originName = ComparteMesaApplication.cities.getCityName(table.getOrigin());
                String destinyName = ComparteMesaApplication.cities.getCityName(table.getDestiny());
                titles.add(originName + " --> " + destinyName);
            }

            ListAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, titles);

            ListView lv = (ListView) view.findViewById(R.id.tablesList);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Table table = tables.get(position);
                    Table myTable = ComparteMesaApplication.getMyTable();

                    if (table.getUsers().size() > 3) {
                        Toast toast1 = Toast.makeText(myContext, "La mesa está llena", Toast.LENGTH_SHORT);
                        toast1.show();
                    } else if (!myTable.getUUID().contentEquals("0")) {
                        Toast toast2 = Toast.makeText(myContext, "Ya perteneces a una mesa", Toast.LENGTH_SHORT);
                        toast2.show();
                    } else {

                        String tableUUID = tables.get(position).getUUID();
                        JoinTableTask joinTableTask = new JoinTableTask(new TableOperationCallback() {
                            @Override
                            public void onTaskDone(Object... params) {
                                ((MainActivity) myContext).onResume();
                            }
                        });
                        joinTableTask.execute(tableUUID);
                    }
                }
            });
        }

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }
}
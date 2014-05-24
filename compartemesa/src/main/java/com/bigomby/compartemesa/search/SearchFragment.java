package com.bigomby.compartemesa.search;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigomby.compartemesa.R;
import com.bigomby.compartemesa.communication.LoadTables;
import com.bigomby.compartemesa.data.Table;

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

        LoadTables loadTables = new LoadTables(view, inflater, container, getActivity());
        loadTables.execute();

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

}
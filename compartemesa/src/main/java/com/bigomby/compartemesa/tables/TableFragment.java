package com.bigomby.compartemesa.tables;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bigomby.compartemesa.R;
import com.bigomby.compartemesa.data.Table;
import com.bigomby.compartemesa.data.User;

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
        View view = inflater.inflate(R.layout.table_empty, container, false);


        Bundle bundle = getArguments();

        if (bundle != null) {
            myTable = (Table) this.getArguments().getSerializable("table");
            view = inflater.inflate(R.layout.table_fragment, container, false);

            int[] userNamesId = {
                    R.id.user_1_name,
                    R.id.user_2_name,
                    R.id.user_3_name,
                    R.id.user_4_name
            };

            int[] userImagesId = {
                    R.id.user_1_image,
                    R.id.user_2_image,
                    R.id.user_3_image,
                    R.id.user_4_image
            };


            Log.d("TableFragment", "" + myTable.getUsers().size());
            for (int i = 0 ; i < myTable.getUsers().size() ; i++) {
                TextView userName = (TextView) view.findViewById(userNamesId[i]);
                ImageView userImage = (ImageView) view.findViewById(userImagesId[i]);
                Log.d("TableFragment:", myTable.getUsers().get(0).getName());
                userName.setText(myTable.getUsers().get(i).getName());
                String uri = "@drawable/ic_action_person";
                int imageResource = getResources().getIdentifier(uri, null, getActivity().getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                userImage.setImageDrawable(res);
                i++;
            }

            TextView originCity = (TextView) view.findViewById(R.id.city_origin);
            originCity.setText((CharSequence) myTable.getOrigin().getName());

            TextView destinyCity = (TextView) view.findViewById(R.id.city_destiny);
            destinyCity.setText((CharSequence) myTable.getDestiny().getName());

        } else

        {

        }

        return view;

    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_activity_actions, menu);
    }

}
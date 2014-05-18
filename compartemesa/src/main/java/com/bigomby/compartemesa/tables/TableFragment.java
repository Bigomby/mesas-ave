package com.bigomby.compartemesa.tables;

import android.app.Application;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bigomby.compartemesa.ComparteMesaApplication;
import com.bigomby.compartemesa.R;
import com.bigomby.compartemesa.data.Table;
import com.bigomby.compartemesa.data.User;
import com.bigomby.compartemesa.data.myTableSQLConfigManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TableFragment extends Fragment {

    View view;

    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        // Añade a la ActionBar los botones correspondientes al fragmento
        setHasOptionsMenu(true);

        // Obtiene la mesa de la clase Application
        ComparteMesaApplication app = (ComparteMesaApplication) getActivity().getApplication();
        Table myTable = app.getMyTable();

        //  Si pertenecemos a una mesa mostramos el layout correspondiente y rellenamos los
        //  elementos de la interfaz.
        if (myTable != null) {

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

            for (int i = 0; i < myTable.getUsers().size(); i++) {
                TextView userName = (TextView) view.findViewById(userNamesId[i]);
                ImageView userImage = (ImageView) view.findViewById(userImagesId[i]);

                userName.setText(myTable.getUsers().get(i).getName());

                String uri = "@drawable/ic_action_person";
                int imageResource = getResources().getIdentifier(uri, null, getActivity().getPackageName());
                Drawable res = getResources().getDrawable(imageResource);

                userImage.setImageDrawable(res);
            }

            final TextView originCity = (TextView) view.findViewById(R.id.city_origin);
            originCity.setText(ComparteMesaApplication.cities.getCityName(myTable.getOrigin()));

            final TextView destinyCity = (TextView) view.findViewById(R.id.city_destiny);
            destinyCity.setText(ComparteMesaApplication.cities.getCityName(myTable.getDestiny()));

            final ListView msgView = (ListView) view.findViewById(R.id.message_container);

            final ArrayAdapter<String> msgList = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1);

            msgView.setAdapter(msgList);

            final ImageButton btnSend = (ImageButton) view.findViewById(R.id.btn_send);

            btnSend.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final EditText txtEdit = (EditText) view.findViewById(R.id.text_send);
                    msgList.add(txtEdit.getText().toString());
                    msgView.smoothScrollToPosition(msgList.getCount() - 1);
                    txtEdit.setText("");

                }
            });
        }

        // En caso de no pertenecer a una mesa mostramos el layout para agregar una mesa

        else {
            view = inflater.inflate(R.layout.table_empty, container, false);
        }

        return view;
    }

    /**
     * ************************************************************
     * Función de callback para mostrar iconos en la ActionBar    *
     * ************************************************************
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        ComparteMesaApplication app = (ComparteMesaApplication) getActivity().getApplication();

        if (app.getMyTable() == null)
            inflater.inflate(R.menu.main_activity_actions_add, menu);
        else
            inflater.inflate(R.menu.main_activity_actions_remove, menu);
    }
}
package com.bigomby.compartemesa.tables;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigomby.compartemesa.ComparteMesaApplication;
import com.bigomby.compartemesa.R;
import com.bigomby.compartemesa.communication.LoadMyTable;
import com.bigomby.compartemesa.data.Table;

public class TableFragment extends Fragment {

    View view;

    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        // Añade a la ActionBar los botones correspondientes al fragmento
        setHasOptionsMenu(true);

        Context context = getActivity();
        ComparteMesaApplication app = (ComparteMesaApplication) context.getApplicationContext();
        Table myTable = app.getMyTable();

        if (myTable != null) {

            Log.d("UI", "Voy a inflar el layout");
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
                int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
                Drawable res = context.getResources().getDrawable(imageResource);

                userImage.setImageDrawable(res);
            }

            final TextView originCity = (TextView) view.findViewById(R.id.city_origin);
            originCity.setText(ComparteMesaApplication.cities.getCityName(myTable.getOrigin()));

            final TextView destinyCity = (TextView) view.findViewById(R.id.city_destiny);
            destinyCity.setText(ComparteMesaApplication.cities.getCityName(myTable.getDestiny()));

            final ListView msgView = (ListView) view.findViewById(R.id.message_container);

            final ArrayAdapter<String> msgList = new ArrayAdapter<String>(context,
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
        } else {
            view = inflater.inflate(R.layout.table_empty, container, false);
        }
        getActivity().setProgressBarIndeterminateVisibility(false);
        return view;
    }

    /**
     * ************************************************************
     * Función de callback para mostrar iconos en la ActionBar    *
     * ************************************************************
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        int mode = Activity.MODE_PRIVATE;
        SharedPreferences pref = getActivity().getSharedPreferences("prefs", mode);

        ComparteMesaApplication app = (ComparteMesaApplication) getActivity().getApplication();
        if (pref.getString("myTableUUID", "null").contentEquals("null")) {
            inflater.inflate(R.menu.main_activity_actions_add, menu);
        } else {
            inflater.inflate(R.menu.main_activity_actions_remove, menu);
        }
    }
}
package com.bigomby.compartemesa;

import android.app.Application;

import com.bigomby.compartemesa.data.Cities;
import com.bigomby.compartemesa.data.Table;

public class ComparteMesaApplication extends Application {

    public static Cities cities;
    public static String myUUID;
    public static Table myTable;

    static {
        cities = new Cities();
    }

    public static Cities getCities() {
        return cities;
    }

    public static void setMyUUID(String newMyUUID) {
        myUUID = newMyUUID;
    }

    public static String getMyUUID() {
        return myUUID;
    }

    public static void setMyTable(Table myNewTable) {
        myTable = myNewTable;
    }

    public static Table getMyTable() {
        return myTable;
    }
}
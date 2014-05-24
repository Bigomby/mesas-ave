package com.bigomby.compartemesa;

import android.app.Application;

import com.bigomby.compartemesa.data.Cities;
import com.bigomby.compartemesa.data.Table;

public class ComparteMesaApplication extends Application {

    public static Cities cities;
    public String myUUID;
    public String myName;
    public String myTableUUID;
    public Table myTable;

    static {
        cities = new Cities();
    }

    public Cities getCities() {
        return cities;
    }

    public void setMyUUID(String myUUID) {
        this.myUUID = myUUID;
    }

    public String getMyUUID() {
        return myUUID;
    }

    public void setMyName(String name) {
        this.myName = name;
    }

    public void setMyTableUUID(String tableUUID) {
        this.myTableUUID = tableUUID;
    }

    public String getMyTableUUID() {
        return myTableUUID;
    }

    public void setMyTable(Table myTable) {
        this.myTable = myTable;
    }

    public Table getMyTable() {
        return myTable;
    }
}
package com.bigomby.compartemesa;

import android.app.Application;

import com.bigomby.compartemesa.data.Cities;
import com.bigomby.compartemesa.data.Table;
import com.bigomby.compartemesa.data.myTableSQLConfigManager;

public class ComparteMesaApplication extends Application {

    public static Cities cities;
    private boolean dbUpdated = true;
    private Table myTable = null;

    static {
        cities = new Cities();
    }

    public Table getMyTable() {

        if (dbUpdated) {
            myTableSQLConfigManager myTableDb = new myTableSQLConfigManager(this, "myTableDb", null, 1);
            myTable = myTableDb.loadMyTable();
            dbUpdated = false;
        }
        return myTable;
    }

    public Cities getCities() {
        return cities;
    }

    public void databaseUpdated() {
        dbUpdated = true;
    }

    public boolean isDatabaseUpdate() {
        return dbUpdated;
    }
}
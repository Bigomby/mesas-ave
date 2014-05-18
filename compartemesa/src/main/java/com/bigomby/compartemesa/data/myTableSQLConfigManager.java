package com.bigomby.compartemesa.data;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bigomby.compartemesa.ComparteMesaApplication;

public class myTableSQLConfigManager extends SQLiteOpenHelper {

    private static final String SQL_CREATE_CONFIG = "create table mytabletable(" +
            "uuid text primary key," +
            "origin int," +
            "destiny int," +
            "departure datetime," +
            "user1 text," +
            "user2 text," +
            "user3 text," +
            "user4 text)";
    private ComparteMesaApplication app;


    public myTableSQLConfigManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        app = (ComparteMesaApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CONFIG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Table loadMyTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        Table myTable = null;

        if (db != null) {
            Cursor cSelect = db.rawQuery("SELECT * FROM mytabletable ORDER BY ROWID ASC LIMIT 1", null);

            if (cSelect.getCount() > 0) {

                cSelect.moveToFirst();

                myTable = new Table(cSelect.getInt(1), cSelect.getInt(2), cSelect.getString(0));

                for (int i = 4; i <= 7; i++) {
                    if (cSelect.getString(i) != null) {
                        myTable.addUser(cSelect.getString(i),"Usuario");
                    }
                }
            }
        }
        return myTable;
    }

    public void saveMyTable(Table myTable) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (db != null) {
            ContentValues newValues = new ContentValues();
            newValues.put("uuid", myTable.getUUID());
            newValues.put("origin", myTable.getOrigin());
            newValues.put("destiny", myTable.getDestiny());

            for(int i = 1 ; i == myTable.getUsers().size() ; i++) {
                newValues.put("user" + i, myTable.getUsers().get(i - 1).getUUID());
            }
            db.insert("mytabletable", null, newValues);
            app.databaseUpdated();
        }
    }

    public void removeMyTable() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("mytabletable", null, null);
        app.databaseUpdated();
    }
}
package com.bigomby.compartemesa.data;

import android.content.Context;
import android.util.Log;
import android.webkit.WebStorage;

import com.bigomby.compartemesa.ComparteMesaApplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Table implements Serializable {

    private UUID uuid;
    private int destiny;
    private int origin;
    private Departure departure;
    private List<User> users;

    public Table(int origin, int destiny) {
        uuid = UUID.randomUUID();
        users = new ArrayList<User>();
        this.origin = origin;
        this.destiny = destiny;
    }

    public Table(int origin, int destiny, String uuid) {
        try {
            this.uuid = UUID.fromString(uuid);
        } catch(Exception e) {
            this.uuid = UUID.randomUUID();
        }
        users = new ArrayList<User>();
        this.origin = origin;
        this.destiny = destiny;
    }

    public String getUUID() {
        return uuid.toString();
    }

    public int getOrigin() {
        return origin;
    }

    public int getDestiny() {
        return destiny;
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(String name) {
        if (users.size() < 4) {
            User newUser = new User(name);
            users.add(newUser);
        }
    }

    public void addUser(String uuid, String name) {
        if (users.size() < 4) {
            User newUser = new User(uuid, name);
            users.add(newUser);
        }
    }
}
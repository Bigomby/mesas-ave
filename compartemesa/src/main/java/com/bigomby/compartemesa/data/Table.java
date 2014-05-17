package com.bigomby.compartemesa.data;

import android.util.Log;
import android.webkit.WebStorage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Table implements Serializable {

    private List<User> users;
    private City destiny;
    private City origin;
    private Departure departure;

    public Table(City origin, City destiny, User user) {
        users = new ArrayList<User>();
        users.add(user);
        this.origin = origin;
        this.destiny = destiny;
    }

    public City getOrigin() {
        return origin;
    }

    public City getDestiny() {
        return destiny;
    }

    public void setOrigin(City origin) {
        this.origin = origin;
    }

    public void setDestiny(City destiny) {
        this.destiny = destiny;
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        if (users.size() < 4) {
            users.add(user);
            Log.d("USER:", "Añadido usuario " + user.getName() + " a la mesa.");
        }
    }
}
package com.bigomby.compartemesa.tables;

import com.bigomby.compartemesa.User;

import java.util.ArrayList;
import java.util.List;


public class Table {

    List<User> users;
    City destiny;
    City origin;
    Departure departure;

    Table(){
        users = new ArrayList<User>();
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }

    City getOrigin() {
        return origin;
    }

    City getDestiny() {
        return destiny;
    }
}
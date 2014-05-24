package com.bigomby.compartemesa.data;

import android.util.Log;

import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

public class Table implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    private int destiny;
    private int origin;
    private List<User> users;

    public Table(SoapObject obj) {

        this.uuid = UUID.fromString(obj.getPropertyAsString(0).toString());
        this.origin = Integer.parseInt(obj.getPropertyAsString(1).toString());
        this.destiny = Integer.parseInt(obj.getPropertyAsString(2).toString());

        users = new LinkedList<User>();
        Vector<SoapObject> vectorUsers = (Vector) obj.getProperty(3);

        for (int i = 0 ; i < vectorUsers.size() ; i++) {
            users.add(new User((SoapObject) vectorUsers.get(i)));
        }

        Log.d("TABLE", "Creada mesa con UUID: " + this.uuid.toString());
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

    public void addUser(User user) throws Exception {
        if (users.size() < 4)
            users.add(user);
        else
            throw new Exception("Error: Mesa llena");
    }
}
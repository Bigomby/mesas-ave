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
    private String uuid;
    private int destiny;
    private int origin;
    private List<User> users;

    public Table(SoapObject obj) throws Exception {

        if (obj != null) {
            String uuid = obj.getPropertyAsString(0);

            if (uuid.contentEquals("0")){
                this.uuid = uuid;
            } else {

                this.uuid = UUID.fromString(uuid).toString();
                this.origin = Integer.parseInt(obj.getPropertyAsString(2));
                this.destiny = Integer.parseInt(obj.getPropertyAsString(1));

                users = new LinkedList<User>();
                Vector<SoapObject> vectorUsers = (Vector) obj.getProperty(3);

                for (int i = 0; i < vectorUsers.size(); i++) {
                    users.add(new User((SoapObject) vectorUsers.get(i)));
                }
            }
        } else {
            throw new Exception("No se ha recibido ninguna mesa del servidor");
        }
    }

    public Table () {
        this.uuid = "0";
        this.origin = -1;
        this.destiny = -1;

        users = new LinkedList<User>();
    }

    public String getUUID() {
        return uuid;
    }

    public String getAdminUUID() {
        return users.get(0).getUUID();
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
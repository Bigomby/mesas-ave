package com.bigomby.compartemesa.data;

import android.util.Log;

import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {

    private static final long serialVersionUID = 24052014;
    private UUID uuid;
    private String email;
    private String name;
    private String passwd;

    public User(SoapObject obj) {

        this.uuid = UUID.fromString(obj.getPropertyAsString(0));
        this.email = obj.getPropertyAsString(1);
        this.name = obj.getPropertyAsString(2);
        this.passwd = obj.getPropertyAsString(3);

        Log.d("USER", "Creado usuario: " + this.name);
    }

    public String getUUID() {
        return uuid.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswd() {
        return passwd;
    }
}
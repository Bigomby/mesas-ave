package com.bigomby.compartemesa.data;

import java.io.Serializable;

/**
 * Created by diego on 14/05/14.
 */
public class User implements Serializable{

    private String id;
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
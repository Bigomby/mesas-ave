package com.bigomby.compartemesa.data;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by diego on 14/05/14.
 */
public class User implements Serializable {

    private UUID uuid;
    private String name;

    public User(String uuid, String name) {
        try {
            this.uuid = UUID.fromString(uuid);
        } catch(Exception e) {
            this.uuid = UUID.randomUUID();
        }
        this.name = name;
    }

    public User(String name) {
        this.uuid = UUID.randomUUID();
        this.name = name;
    }

    public String getUUID() {
        return uuid.toString();
    }

    public String getName() {
        return name;
    }

}
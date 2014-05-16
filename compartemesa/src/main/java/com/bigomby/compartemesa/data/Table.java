package com.bigomby.compartemesa.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Table implements Serializable{

    private List<User> users;
    private City destiny;
    private City origin;
    private Departure departure;

    public Table(){
        users = new ArrayList<User>();
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }

    public City getOrigin() {
        return origin;
    }

    public City getDestiny() {
        return destiny;
    }

    public void setOrigin(City origin){
        this.origin = origin;
    }

    public void setDestiny(City destiny){
        this.destiny = destiny;
    }
}
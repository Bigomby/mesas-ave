package com.bigomby.compartemesa.data;

import java.io.Serializable;

/**
 * Created by diego on 14/05/14.
 */
public class City implements Serializable{

    private String cityName;

    public City(String cityName) {
        this.cityName = cityName;
    }

    public String getName() {
        return cityName;
    }
}
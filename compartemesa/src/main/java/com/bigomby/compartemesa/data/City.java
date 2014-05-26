package com.bigomby.compartemesa.data;

import java.io.Serializable;

public class City implements Serializable {

    private String cityName;
    private int[] allowedDestiniesIds;

    public City(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

}
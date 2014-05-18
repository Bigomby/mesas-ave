package com.bigomby.compartemesa.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Cities extends ArrayList<City> {

    public Cities() {
        this.add(new City("Madrid"));
        this.add(new City("Barcelona"));
        this.add(new City("Valencia"));
        this.add(new City("Sevilla"));
        this.add(new City("Málaga"));
        this.add(new City("Valladolid"));
        this.add(new City("Córdoba"));
    }

    public String getCityName(int id) {
        return this.get(id).getCityName();
    }

    public List<String> toListString() {
        List<String> stringsCities = new ArrayList<String>();

        for (int i = 0; i < this.size(); i++) {
            stringsCities.add(this.get(i).getCityName());
        }

        return stringsCities;
    }
}

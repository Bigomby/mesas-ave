package com.bigomby.compartemesa.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by diego on 16/05/14.
 */
public class Cities {

    List<City> cities = new ArrayList<City>();

    // TODO Cargar las ciudadades de la BBDD

    public Cities(){
        cities.add(new City("Sevilla"));
        cities.add(new City("Madrid"));
        cities.add(new City("Barcelona"));
        cities.add(new City("Valencia"));
        cities.add(new City("Málaga"));
        cities.add(new City("Córdoba"));
        cities.add(new City("Valladolid"));
    }

    public List<String> toListString() {
        Iterator<City> it = cities.iterator();
        List<String> stringsCities = new ArrayList<String>();

        while (it.hasNext()){
            stringsCities.add(it.next().getName());
        }
        return stringsCities;
    }
}

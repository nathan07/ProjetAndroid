package com.grenoble.miage.metrobilite;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by prinsacn on 11/04/18.
 */

public class Arret {

    private String code;
    private String city;
    private String name;
    private double lon;
    private double lat;

    public Arret(JSONObject jsonObject) {
        try {
            this.code = jsonObject.getString("code");
            this.city = jsonObject.getString("city");
            this.name = jsonObject.getString("name");
            this.lon = jsonObject.getDouble("lon");
            this.lat = jsonObject.getDouble("lat");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Arret(String[] arret) {
        this.code = arret[0];
        this.city = arret[1];
        this.name = arret[2];
        this.lon = new Double(arret[3]);
        this.lat = new Double(arret[4]);
    }

    public String getCode() {
        return code;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    public String[] getTableau() {
        String[] tab = new String[5];
        tab[0] = this.getCode();
        tab[1] = this.getCity();
        tab[2] = this.getName();
        tab[3] = Double.toString(this.getLon());
        tab[4] = Double.toString(this.getLat());
        return tab;
    }
}

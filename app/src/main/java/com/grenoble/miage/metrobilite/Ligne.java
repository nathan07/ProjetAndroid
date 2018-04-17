package com.grenoble.miage.metrobilite;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by prinsacn on 11/04/18.
 */

public class Ligne {

    private String id;
    private String shortName;
    private String longName;
    private String color;
    private String textColor;
    private String mode;
    private String type;

    public Ligne(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getString("id");
            this.shortName = jsonObject.getString("shorName");
            this.longName = jsonObject.getString("longName");
            this.color = jsonObject.getString("color");
            this.textColor = jsonObject.getString("textColor");
            this.mode = jsonObject.getString("mode");
            this.type = jsonObject.getString("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Ligne(String[] ligne) {
            this.id = ligne[0];
            this.shortName = ligne[1];
            this.longName = ligne[2];
            this.color = ligne[3];
            this.textColor = ligne[4];
            this.mode = ligne[5];
            this.type = ligne[6];
    }

    public String getType() {
        return type;
    }

    public String getMode() {
        return mode;
    }

    public String getId() {
        return id;
    }

    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }

    public String getColor() {
        return color;
    }

    public String getTextColor() {
        return textColor;
    }

    public String[] getTableau() {
        String[] tab = new String[7];
        tab[0] = this.getId();
        tab[1] = this.getShortName();
        tab[2] = this.getLongName();
        tab[3] = this.getColor();
        tab[4] = this.getTextColor();
        tab[5] = this.getMode();
        tab[6] = this.getType();
        return tab;
    }
}

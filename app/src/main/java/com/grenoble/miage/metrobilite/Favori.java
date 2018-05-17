package com.grenoble.miage.metrobilite;

import org.json.JSONObject;

public class Favori {

    private int id;
    private String idLigne;
    private String nomLigne;
    private String codeArret;
    private String nomArret;
    private String destination;
    private int direction;
    private int notifActive;

    private JSONObject[] horaires;


    public Favori(int anInt, String idLigne, String nomLigne, String codeArret, String nomArret, String dest, int dir, int notifActive) {
        this.id=anInt;
        this.idLigne=idLigne;
        this.nomLigne=nomLigne;
        this.codeArret=codeArret;
        this.nomArret=nomArret;
        this.destination=dest;
        this.direction=dir;
        this.horaires = null;
        this.notifActive = notifActive;
    }

    public Favori(String idLigne, String nomLigne, String codeArret, String nomArret, String dest, int dir) {
        this.id=-1;
        this.idLigne=idLigne;
        this.nomLigne=nomLigne;
        this.codeArret=codeArret;
        this.nomArret=nomArret;
        this.destination=dest;
        this.direction=dir;
        this.horaires = null;
    }

    public int getId() { return this.id;}

    public String getIdLigne() {
        return idLigne;
    }

    public String getNomLigne() {
        return nomLigne;
    }

    public String getCodeArret() {
        return codeArret;
    }

    public String getNomArret() {
        return nomArret;
    }

    public String getDestination() {
        return destination;
    }

    public JSONObject[] getHoraires() {
        return horaires;
    }

    public int getDirection() {
        return direction;
    }

    public int getNotifActive() {
        return notifActive;
    }

    public void setHoraires(JSONObject[] h) { this.horaires = h; }

    public void setNotifActive(int b) { this.notifActive = b; }
}

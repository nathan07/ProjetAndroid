package com.grenoble.miage.metrobilite;

public class Favori {

    private int id;
    private String idLigne;
    private String nomLigne;
    private String nomArret;
    private String destination;


    public Favori(int anInt, String idLigne, String nomLigne, String nomArret, String dest) {
        this.id=anInt;
        this.idLigne=idLigne;
        this.nomLigne=nomLigne;
        this.nomArret=nomArret;
        this.destination=dest;
    }

    public Favori(String idLigne, String nomLigne, String nomArret, String dest) {
        this.id=-1;
        this.idLigne=idLigne;
        this.nomLigne=nomLigne;
        this.nomArret=nomArret;
        this.destination=dest;
    }

    public int getId() { return this.id;}

    public String getIdLigne() {
        return idLigne;
    }

    public String getNomLigne() {
        return nomLigne;
    }

    public String getNomArret() {
        return nomArret;
    }

    public String getDestination() {
        return destination;
    }
}

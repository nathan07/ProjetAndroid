package com.grenoble.miage.metrobilite;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by prinsacn on 28/03/18.
 */

public class ThreadRecupLignes extends Thread {

    private JSONObject[] tab;
    private DataCollector data;
    private String url;

    public ThreadRecupLignes(JSONObject[] tab, DataCollector data, String url) {
        this.tab = tab;
        this.data = data;
        this.url = url;
    }

    public void run() {
        try {
            tab = data.getDataLignes(url);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject[] getTab() {
        return this.tab;
    }
}

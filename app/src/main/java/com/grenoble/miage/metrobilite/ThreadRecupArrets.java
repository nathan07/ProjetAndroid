package com.grenoble.miage.metrobilite;

import org.json.JSONObject;

import java.io.IOException;

public class ThreadRecupArrets extends Thread {
    private JSONObject[] tab;
    private DataCollector data;
    private String url;

    public ThreadRecupArrets(JSONObject[] tab, DataCollector data, String url) {
        this.tab = tab;
        this.data = data;
        this.url = url;
    }

    public void run() {
        try {
            tab = data.getDataArrets(url);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject[] getTab() {
        return this.tab;
    }
}

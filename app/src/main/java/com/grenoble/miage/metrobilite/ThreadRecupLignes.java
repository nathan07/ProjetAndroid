package com.grenoble.miage.metrobilite;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by prinsacn on 28/03/18.
 */

public class ThreadRecupLignes extends Thread {

    private JSONObject[] tab;
    private DataCollector data;

    public ThreadRecupLignes(JSONObject[] tab, DataCollector data) {
        this.tab = tab;
        this.data = data;
    }

    public void run() {
        try {
            tab = data.getData();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject[] getTab() {
        return this.tab;
    }
}

package com.grenoble.miage.metrobilite;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by U Mag on 21/03/2018.
 */

public class DataCollector {

    URL url;
    HttpURLConnection conn;

    public DataCollector(){

    }

    public void getData() throws IOException {
        url = new URL("https://data.metromobilite.fr/api/routers/default/index/routes");
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();

        System.out.println("response : "+conn.getResponseCode());

    }
}

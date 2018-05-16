package com.grenoble.miage.metrobilite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by prinsacn on 21/03/2018.
 */

public class DataCollector {

    URL url;
    HttpURLConnection conn;

    public DataCollector(){

    }

    public JSONObject[] getDataLignes(String link) throws IOException {
        url = new URL(link);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();

       // System.out.println("response : "+conn.getResponseCode());

        InputStream inputStream = new BufferedInputStream(conn.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder result = new StringBuilder();

        String line;
        JSONObject[] lignes = null;

        while((line = reader.readLine()) != null) {
            result.append(line);
        }

        try {
            JSONArray json = new JSONArray(result.toString());
          //  System.out.println("json: "+json);
            int nbLignesSemitag = compterLignesSemitag(json);
            lignes = new JSONObject[nbLignesSemitag];
            int indexLignes = 0;
            for(int i = 0;i<json.length();i++) {
                if(json.getJSONObject(i).getString("id").contains("SEM")) {
                    lignes[indexLignes] = json.getJSONObject(i);
                    indexLignes++;
                }

            }

            for(int i = 0;i<indexLignes;i++) {
                System.out.println("lignes["+indexLignes+"] : "+ lignes[i]);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lignes;
    }

    private int compterLignesSemitag(JSONArray json) {
        int count = 0;
        for(int i = 0;i<json.length();i++) {
            try {
                if(json.getJSONObject(i).getString("id").contains("SEM")) {
                    count++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return count;
    }

    public JSONObject[] getDataArrets(String link) throws IOException {
        url = new URL(link);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();

     //   System.out.println("response : "+conn.getResponseCode());

        InputStream inputStream = new BufferedInputStream(conn.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder result = new StringBuilder();

        String line;
        JSONObject[] arrets = null;

        while((line = reader.readLine()) != null) {
            result.append(line);
        }

        try {
            JSONArray json = new JSONArray(result.toString());
     //       System.out.println("json: "+json);
            arrets = new JSONObject[json.length()];
            for(int i = 0;i<json.length();i++) {
                    arrets[i] = json.getJSONObject(i);
            }

            for(int i = 0;i<json.length();i++) {
                System.out.println("lignes["+i+"] : "+ arrets[i]);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arrets;
    }
}

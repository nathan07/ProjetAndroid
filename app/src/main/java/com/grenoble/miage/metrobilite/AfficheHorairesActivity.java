package com.grenoble.miage.metrobilite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AfficheHorairesActivity extends AppCompatActivity {

    private JSONObject[] horaires;
    private TextView hor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_horaires);
        Bundle extras = getIntent().getExtras();
        JSONObject arret = null;
        try {
            arret = new JSONObject(extras.getString("JSON"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("objet recupere : "+arret);

        String link = "";
        try {
            link = "https://data.metromobilite.fr/api/routers/default/index/clusters/"+arret.getString("code")+"/stoptimes";
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final DataCollector dataCollector = new DataCollector();
        try {
            ThreadRecupArrets thread = new ThreadRecupArrets(horaires, dataCollector, link);
            thread.start();
            thread.join();
            horaires = thread.getTab();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        hor = (TextView) findViewById(R.id.horaire);
        try {
            JSONArray array = horaires[0].getJSONArray("times");
            hor.setText(array.getString(4));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
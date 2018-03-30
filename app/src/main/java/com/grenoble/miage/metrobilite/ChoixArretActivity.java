package com.grenoble.miage.metrobilite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by U Mag on 30/03/2018.
 */

public class ChoixArretActivity extends AppCompatActivity {

    private ListView choixArrets;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_arret);
        Bundle extras = getIntent().getExtras();
        JSONObject ligne = null;
        try {
            ligne = new JSONObject(extras.getString("JSON"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("objet recupere : "+ligne);
    }
}

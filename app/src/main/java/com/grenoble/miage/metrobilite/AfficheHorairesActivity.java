package com.grenoble.miage.metrobilite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AfficheHorairesActivity extends AppCompatActivity {

    private JSONObject[] horaires;
    private TextView hor;
    private TextView ligne;
    private TextView destination;
    private TextView arretChoisi;
    private Switch choixDirection;
    private int direction = 0;
    private int hours;
    private int minutes;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_horaires);
        Bundle extras = getIntent().getExtras();
        Arret arret = null;
        arret = new Arret(extras.getStringArray("ARRET"));

        System.out.println("objet recupere : "+arret.getCode());

        String link = "https://data.metromobilite.fr/api/routers/default/index/clusters/"+arret.getCode()+"/stoptimes";

        final DataCollector dataCollector = new DataCollector();
        try {
            ThreadRecupArrets thread = new ThreadRecupArrets(horaires, dataCollector, link);
            thread.start();
            thread.join();
            horaires = thread.getTab();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ligne = (TextView) findViewById(R.id.rappelLigne);
        destination = (TextView) findViewById(R.id.rappelDestination);
        arretChoisi = (TextView) findViewById(R.id.rappelArret);
        choixDirection = (Switch) findViewById(R.id.choixDirection);

        choixDirection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    direction = 1;
                }
                else {
                    direction = 0;
                }
                rafraichirAffichage();
            }
        });

        hor = (TextView) findViewById(R.id.horaire);
        rafraichirAffichage();

    }

    private void rafraichirAffichage() {
        try {
            ligne.setText(horaires[direction].getJSONObject("pattern").getString("id"));
            destination.setText(horaires[direction].getJSONObject("pattern").getString("desc"));
            arretChoisi.setText(horaires[direction].getJSONArray("times").getJSONObject(0).getString("stopName"));

            String nextArrival = horaires[direction].getJSONArray("times").getJSONObject(0).getString("scheduledArrival");
            int test = Integer.parseInt(nextArrival);
            hours = test/3600;
            minutes = (test % 3600) / 60;
            nextArrival = hours+"h"+minutes;
            hor.setText(nextArrival);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

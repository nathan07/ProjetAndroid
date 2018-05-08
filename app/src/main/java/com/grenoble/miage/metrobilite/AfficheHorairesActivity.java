package com.grenoble.miage.metrobilite;

import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class AfficheHorairesActivity extends AppCompatActivity {

    private JSONObject[] horaires;
    private TextView hor;
    private TextView ligne;
    private TextView destination;
    private TextView arretChoisi;
    private Switch choixDirection;
    private int direction = 1;
    private Arret arret = null;
    private String ligneId = null;
    private int hours = 0;
    private int minutes = 0;
    private static int NOTIFICATION_ID = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_horaires);
        Bundle extras = getIntent().getExtras();
        System.out.println("recupere : "+extras.getStringArray("ARRET")[5]);
        ligneId = extras.getStringArray("ARRET")[5];
        String[] paramsArret = new String[extras.getStringArray("ARRET").length-1];
        for(int i = 0;i<extras.getStringArray("ARRET").length-1;i++) {
            paramsArret[i] = extras.getStringArray("ARRET")[i];
        }
        arret = new Arret(paramsArret);

        final String link = "https://data.metromobilite.fr/api/routers/default/index/clusters/"+arret.getCode()+"/stoptimes";

        final DataCollector dataCollector = new DataCollector();
        recupererHoraires(dataCollector, link);

        ligne = (TextView) findViewById(R.id.rappelLigne);
        destination = (TextView) findViewById(R.id.rappelDestination);
        arretChoisi = (TextView) findViewById(R.id.rappelArret);
        choixDirection = (Switch) findViewById(R.id.choixDirection);

        choixDirection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    direction = 2;
                }
                else {
                    direction = 1;
                }
                rafraichirAffichage();
            }
        });

        hor = (TextView) findViewById(R.id.horaire);
        rafraichirAffichage();

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recupererHoraires(dataCollector, link);
                        rafraichirAffichage();
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 30000);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Date currentTime = Calendar.getInstance().getTime();
        if(!hasFocus && hours == currentTime.getHours() && minutes-currentTime.getMinutes() < 5) {
            final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(AfficheHorairesActivity.this)
                    .setSmallIcon(android.R.drawable.sym_def_app_icon)
                    .setContentTitle("Ligne : "+ligne.getText()+", Arret : "+arret.getName())
                    .setContentText("Heure de passage : "+hours+"h"+minutes)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(AfficheHorairesActivity.this);
            notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        }
    }

    private void rafraichirAffichage() {
        try {
            int indicePattern = 0;
            while((!horaires[indicePattern].getJSONObject("pattern").getString("id").startsWith(ligneId)) || (!horaires[indicePattern].getJSONObject("pattern").getString("dir").equals(String.valueOf(direction)))) {
                indicePattern++;
            }
            ligne.setText(horaires[indicePattern].getJSONObject("pattern").getString("id"));
            destination.setText(horaires[indicePattern].getJSONObject("pattern").getString("desc"));
            arretChoisi.setText(arret.getName());
            String nextArrival = horaires[indicePattern].getJSONArray("times").getJSONObject(0).getString("scheduledArrival");
            int nextArrivalInt = Integer.parseInt(nextArrival);
            hours = nextArrivalInt / 3600;
            minutes = (nextArrivalInt % 3600) / 60;
            if(minutes < 10) {
                nextArrival = hours +"h0"+ minutes;
            }
            else {
                nextArrival = hours +"h"+ minutes;
            }
            hor.setText(nextArrival);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void recupererHoraires(DataCollector dataCollector, String link) {
        try {
            ThreadRecupArrets thread = new ThreadRecupArrets(horaires, dataCollector, link);
            thread.start();
            thread.join();
            horaires = thread.getTab();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package com.grenoble.miage.metrobilite;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.grenoble.miage.metrobilite.Persistence.DAOFavori;

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
    private ImageButton favoris;
    private int direction = 1;
    private Arret arret = null;
    private String ligneId = null;
    private int hours = 0;
    private int minutes = 0;
    private static int NOTIFICATION_ID = 1;
    private Favori infoFav;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_horaires);
        Bundle extras = getIntent().getExtras();
        //System.out.println("recupere : "+extras.getStringArray("ARRET")[5]);
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
        favoris = (ImageButton) findViewById(R.id.fav);


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
                        if(!hasWindowFocus()) {
                            //afficherNotification();
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 30000);

        favoris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new StockageFavoris().add(AfficheHorairesActivity.this,infoFav);
                DAOFavori dbase = DAOFavori.getDAOFavori(AfficheHorairesActivity.this);
                if(dbase.ifExiste(infoFav)==false) {
                    dbase.ajouter(infoFav);
                    System.out.println("Heeeeeeeeeeeeeeeeeeeeeeeeuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
                }
                else{
                    Context context = getApplicationContext();
                    CharSequence text = "Déja ajouté aux favoris!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
    }

    private void afficherNotification() {
        Date currentTime = Calendar.getInstance().getTime();
        if(hours == currentTime.getHours() && minutes-currentTime.getMinutes() <= 5) {
            // Create an explicit intent for an Activity in your app
            Intent intent = new Intent(this, FavorisActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(AfficheHorairesActivity.this)
                    .setSmallIcon(android.R.drawable.sym_def_app_icon)
                    .setContentTitle("Ligne : "+ligne.getText()+", Arret : "+arret.getName())
                    .setContentText("Heure de passage : "+hours+"h"+minutes)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

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

            infoFav= new Favori(this.ligneId,horaires[indicePattern].getJSONObject("pattern").getString("id"),arret.getCode(), arret.getName(),horaires[indicePattern].getJSONObject("pattern").getString("desc"), direction);


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

package com.grenoble.miage.metrobilite;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;

import com.grenoble.miage.metrobilite.Persistence.DAOFavori;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by prinsacn on 02/05/18.
 */

public class FavorisActivity extends AppCompatActivity {

    private RecyclerView FavListView;
    private JSONObject[] horaires;
    private ArrayList<Favori> favoris;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);

        FavListView = (RecyclerView) findViewById(R.id.listFavori);

        final DAOFavori dbase = DAOFavori.getDAOFavori(FavorisActivity.this);

        favoris = new ArrayList<Favori>();

        favoris = dbase.selectionnerTous();

        final FavoriAdapter adapter = new FavoriAdapter(favoris, this.getApplicationContext());

        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getApplicationContext());

        FavListView.setLayoutManager(mLayoutManager);
        FavListView.setItemAnimator(new DefaultItemAnimator());


        FavListView.setAdapter(adapter);

        final ArrayList<Favori> finalFavoris = favoris;

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Remove item from backing list here
                Favori f = finalFavoris.remove(viewHolder.getAdapterPosition());
                adapter.notifyDataSetChanged();
                dbase.supprimer(f.getId());

            }

        });
        itemTouchHelper.attachToRecyclerView(FavListView);

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                favoris = dbase.selectionnerTous();
                for (Favori f: favoris) {
                    if(f.getNotifActive()==1) {
                        String link = "https://data.metromobilite.fr/api/routers/default/index/clusters/"+f.getCodeArret()+"/stoptimes";
                        DataCollector dataCollector = new DataCollector();
                        recupererHoraires(dataCollector, link);
                        if ((horaires!=null || horaires.length!=0) && !hasWindowFocus()) {
                            int test = recupHoraire(f);
                            int hours = test / 3600;
                            int minutes = (test % 3600) / 60;
                            afficherNotification(f, hours, minutes);
                        }
                    }
                }
            }
        };
        timer.schedule(timerTask, 0, 5000);
    }

    private void afficherNotification(Favori f, int hours, int minutes) {
        Date currentTime = Calendar.getInstance().getTime();
        if (hours == currentTime.getHours() && minutes - currentTime.getMinutes() <= 5) {
            // Create an explicit intent for an Activity in your app
                        Intent intent = new Intent(this, FavorisActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(FavorisActivity.this)
                                        .setSmallIcon(android.R.drawable.sym_def_app_icon)
                                        .setContentTitle("Ligne : "+f.getNomLigne()+", Arret : "+f.getNomArret())
                                        .setContentText("Heure de passage : "+hours+"h"+minutes)
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                        // Set the intent that will fire when the user taps the notification
                                        .setContentIntent(pendingIntent)
                                        .setAutoCancel(true);

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(FavorisActivity.this);
                        notificationManager.notify(f.getId(), mBuilder.build());

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

    private int recupHoraire(Favori f) {
        int nextArrivalInt = 0;
        try {
            int indicePattern = 0;
            while((!horaires[indicePattern].getJSONObject("pattern").getString("id").startsWith(f.getIdLigne())) || (!horaires[indicePattern].getJSONObject("pattern").getString("dir").equals(String.valueOf(f.getDirection())))) {
                indicePattern++;
            }

            String nextArrival = horaires[indicePattern].getJSONArray("times").getJSONObject(0).getString("scheduledArrival");
            nextArrivalInt = Integer.parseInt(nextArrival);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return nextArrivalInt;
    }

}
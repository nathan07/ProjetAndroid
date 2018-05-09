package com.grenoble.miage.metrobilite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by prinsacn on 30/03/2018.
 */

public class ChoixArretActivity extends AppCompatActivity {

    private ListView choixArrets;
    private JSONObject[] arrets = null;
    private Arret[] arretTab = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_arret);
        Bundle extras = getIntent().getExtras();
        final Ligne ligne = new Ligne(extras.getStringArray("LIGNE"));


        String link = "https://data.metromobilite.fr/api/routers/default/index/routes/" + ligne.getId() + "/clusters";


        final DataCollector dataCollector = new DataCollector();

        try {
            ThreadRecupArrets thread = new ThreadRecupArrets(arrets, dataCollector, link);
            thread.start();
            thread.join();
            arrets = thread.getTab();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (arrets != null && arrets.length != 0) {
            choixArrets = (ListView) findViewById(R.id.listeArrets);
            final String[] namesArrets = new String[arrets.length];

            arretTab = new Arret[arrets.length];
            for (int i = 0; i < arrets.length; i++) {
                arretTab[i] = new Arret(arrets[i]);
            }

            for (int i = 0; i < arrets.length; i++) {
                namesArrets[i] = arretTab[i].getName();
            }
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChoixArretActivity.this,
                    android.R.layout.simple_list_item_1, namesArrets);
            choixArrets.setAdapter(adapter);

            choixArrets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent horaires = new Intent(ChoixArretActivity.this, AfficheHorairesActivity.class);
                    String[] extra = new String[arretTab[i].getTableau().length + 1];
                    for (int y = 0; y < arretTab[i].getTableau().length; y++) {
                        extra[y] = arretTab[i].getTableau()[y];
                    }
                    extra[arretTab[i].getTableau().length] = ligne.getId();
                    horaires.putExtra("ARRET", extra);
                    startActivity(horaires);
                }
            });
        }
        else
        {
            setContentView(R.layout.erreur_recup_donnee);
        }
    }

}

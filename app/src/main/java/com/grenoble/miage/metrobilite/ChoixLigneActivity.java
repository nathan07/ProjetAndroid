package com.grenoble.miage.metrobilite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by prinsacn on 21/03/18.
 */

public class ChoixLigneActivity extends AppCompatActivity {

    private Button retour;
    private ListView listeLignesTram;
    private ListView listeLignesChrono;
    private ListView listeLignesFlexo;
    private ListView listeLignesProximo;
    private JSONObject[] lignes = null;
    //private JSONObject[] lignesTram = null;
    private Ligne[] lignesTram = null;
    private Ligne[] lignesChrono = null;
    private Ligne[] lignesFlexo = null;
    private Ligne[] lignesProximo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_ligne);

        final DataCollector dataCollector = new DataCollector();
        try {
            ThreadRecupLignes thread = new ThreadRecupLignes(lignes, dataCollector, "https://data.metromobilite.fr/api/routers/default/index/routes");
            thread.start();
            thread.join();
            lignes = thread.getTab();

        } catch (Exception e) {
            e.printStackTrace();
        }

        /* TRAM */
        listeLignesTram = (ListView) findViewById(R.id.listeLignesTram);
        listeLignesChrono = (ListView) findViewById(R.id.listeLignesChrono);
        listeLignesFlexo = (ListView) findViewById(R.id.listeLignesFlexo);
        listeLignesProximo = (ListView) findViewById(R.id.listeLignesProximo);

        final String[] shortNamesLignes = new String[compterLignes("TRAM", lignes)];
        final String[] shortNamesLignesChrono = new String[compterLignes("CHRONO", lignes)];
        final String[] shortNamesLignesFlexo = new String[compterLignes("FLEXO", lignes)];
        final String[] shortNamesLignesProximo = new String[compterLignes("PROXIMO", lignes)];

        //lignesTram = new JSONObject[compterLignes("TRAM", lignes)];
        lignesTram = new Ligne[compterLignes("TRAM", lignes)];
        lignesChrono = new Ligne[compterLignes("CHRONO", lignes)];
        lignesFlexo = new Ligne[compterLignes("FLEXO", lignes)];
        lignesProximo = new Ligne[compterLignes("PROXIMO", lignes)];

        int indexLignesTram = 0;
        int indexLignesChrono = 0;
        int indexLignesFlexo = 0;
        int indexLignesProximo = 0;
        for(int i = 0; i< lignes.length; i++) {
            try {
                if(lignes[i].getString("type").contains("TRAM")) {
                    shortNamesLignes[indexLignesTram] = lignes[i].getString("shortName");
                    lignesTram[indexLignesTram] = new Ligne(lignes[i]);
                    indexLignesTram++;
                }
                else if(lignes[i].getString("type").contains("CHRONO")) {
                    shortNamesLignesChrono[indexLignesChrono] = lignes[i].getString("shortName");
                    lignesChrono[indexLignesChrono] = new Ligne(lignes[i]);
                    indexLignesChrono++;
                }
                else if(lignes[i].getString("type").contains("FLEXO")) {
                    shortNamesLignesFlexo[indexLignesFlexo] = lignes[i].getString("shortName");
                    lignesFlexo[indexLignesFlexo] = new Ligne(lignes[i]);
                    indexLignesFlexo++;
                }
                else if(lignes[i].getString("type").contains("PROXIMO")) {
                    shortNamesLignesProximo[indexLignesProximo] = lignes[i].getString("shortName");
                    lignesProximo[indexLignesProximo] = new Ligne(lignes[i]);
                    indexLignesProximo++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /* adapter tram */
        final ArrayAdapter<String> adapterTram = new ArrayAdapter<String>(ChoixLigneActivity.this,
                android.R.layout.simple_list_item_1, shortNamesLignes);
        listeLignesTram.setAdapter(adapterTram);

        listeLignesTram.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //JSONObject jsonObject = lignesTram[i];
                //System.out.println("click on : "+jsonObject.toString());
                Intent choixArret = new Intent(ChoixLigneActivity.this, ChoixArretActivity.class);
                //choixArret.putExtra("JSON", jsonObject.toString());
                choixArret.putExtra("LIGNE", lignesTram[i].getTableau());
                startActivity(choixArret);
            }
        });

        /* adapter chrono */
        final ArrayAdapter<String> adapterChrono = new ArrayAdapter<String>(ChoixLigneActivity.this,
                android.R.layout.simple_list_item_1, shortNamesLignesChrono);
        listeLignesChrono.setAdapter(adapterChrono);

        listeLignesChrono.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent choixArret = new Intent(ChoixLigneActivity.this, ChoixArretActivity.class);
                choixArret.putExtra("LIGNE", lignesChrono[i].getTableau());
                startActivity(choixArret);
            }
        });

        /* adapter flexo */
        final ArrayAdapter<String> adapterFlexo = new ArrayAdapter<String>(ChoixLigneActivity.this,
                android.R.layout.simple_list_item_1, shortNamesLignesFlexo);
        listeLignesFlexo.setAdapter(adapterFlexo);

        listeLignesFlexo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent choixArret = new Intent(ChoixLigneActivity.this, ChoixArretActivity.class);
                choixArret.putExtra("LIGNE", lignesFlexo[i].getTableau());
                startActivity(choixArret);
            }
        });

        /* adapter proximo */
        final ArrayAdapter<String> adapterProximo = new ArrayAdapter<String>(ChoixLigneActivity.this,
                android.R.layout.simple_list_item_1, shortNamesLignesProximo);
        listeLignesProximo.setAdapter(adapterProximo);

        listeLignesProximo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent choixArret = new Intent(ChoixLigneActivity.this, ChoixArretActivity.class);
                choixArret.putExtra("LIGNE", lignesProximo[i].getTableau());
                startActivity(choixArret);
            }
        });


        retour = (Button) findViewById(R.id.retour_button);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent choixLigne = new Intent(ChoixLigneActivity.this, AccueilActivity.class);
                startActivity(choixLigne);
            }
        });
    }

    private int compterLignes(String l, JSONObject[] lignes) {
        int res = 0;
        for(int i = 0;i<lignes.length;i++) {
            try {
                if(lignes[i].getString("type").contains(l)) {
                    res++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return res;
    }
}

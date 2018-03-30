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

import java.io.IOException;

/**
 * Created by prinsacn on 21/03/18.
 */

public class ChoixLigneActivity extends AppCompatActivity {

    private Button retour;
    private Button ligneA;
    private Button ligneB;
    private Button ligneC;
    private ListView listeLignes;
    private JSONObject[] lignes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_ligne);

        final DataCollector dataCollector = new DataCollector();
        try {
            ThreadRecupLignes thread = new ThreadRecupLignes(lignes, dataCollector);
            thread.start();
            thread.join();
            lignes = thread.getTab();

        } catch (Exception e) {
            e.printStackTrace();
        }

        listeLignes = (ListView) findViewById(R.id.listeLignes);
        final String[] shortNamesLignes = new String[lignes.length];
        for(int i = 0;i<lignes.length;i++) {
            try {
                shortNamesLignes[i] = lignes[i].getString("shortName");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChoixLigneActivity.this,
                android.R.layout.simple_list_item_1, shortNamesLignes);
        listeLignes.setAdapter(adapter);

        listeLignes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                JSONObject jsonObject = lignes[i];
                System.out.println("click on : "+jsonObject.toString());
            }
        });
        ligneA = (Button) findViewById(R.id.ligneA_button);
        try {
            ligneA.setText(lignes[0].getString("shortName"));
            ligneB = (Button) findViewById(R.id.ligneB_button);
            ligneB.setText(lignes[1].getString("shortName"));
            ligneC = (Button) findViewById(R.id.ligneC_button);
            ligneC.setText(lignes[2].getString("shortName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        retour = (Button) findViewById(R.id.retour_button);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent choixLigne = new Intent(ChoixLigneActivity.this, AccueilActivity.class);
                startActivity(choixLigne);
            }
        });
    }
}

package com.grenoble.miage.metrobilite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

/**
 * Created by prinsacn on 21/03/18.
 */

public class ChoixLigneActivity extends AppCompatActivity {

    private Button retour;
    private Button ligneA;
    private Button ligneB;
    private Button ligneC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_ligne);

        final DataCollector dataCollector = new DataCollector();
        try {
            new Thread() {
                public void run() {
                    try {
                        dataCollector.getData();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        } catch (Exception e) {
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

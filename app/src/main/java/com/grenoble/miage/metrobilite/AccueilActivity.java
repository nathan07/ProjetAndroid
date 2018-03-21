package com.grenoble.miage.metrobilite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AccueilActivity extends AppCompatActivity {


    private Button horaires;
    private Button favoris;
    private Button geolocalisation;
    private Button about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        horaires = (Button) findViewById(R.id.horaires_button);
        favoris = (Button) findViewById(R.id.favoris_button);
        geolocalisation = (Button) findViewById(R.id.geolocalisation_button);
        about = (Button) findViewById(R.id.about_button);

        horaires.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent choixLigne = new Intent(AccueilActivity.this, ChoixLigneActivity.class);
                startActivity(choixLigne);
            }
        });
    }
}

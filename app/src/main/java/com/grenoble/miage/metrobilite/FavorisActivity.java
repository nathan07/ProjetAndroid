package com.grenoble.miage.metrobilite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.grenoble.miage.metrobilite.Persistence.DAOFavori;
import com.grenoble.miage.metrobilite.Persistence.StockageFavoris;

/**
 * Created by prinsacn on 02/05/18.
 */

public class FavorisActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);
        DAOFavori dbase = DAOFavori.getDAOFavori(FavorisActivity.this);
        Favori f = dbase.selectionnerTous();

        System.out.println(f.getNomArret());
    }

}
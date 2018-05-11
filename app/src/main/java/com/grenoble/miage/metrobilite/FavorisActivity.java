package com.grenoble.miage.metrobilite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.grenoble.miage.metrobilite.Persistence.DAOFavori;
import com.grenoble.miage.metrobilite.Persistence.StockageFavoris;

import java.util.ArrayList;

/**
 * Created by prinsacn on 02/05/18.
 */

public class FavorisActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);

        ListView FavListView = (ListView) findViewById(R.id.listFavori);

        DAOFavori dbase = DAOFavori.getDAOFavori(FavorisActivity.this);

        ArrayList<Favori> favoris = new ArrayList<Favori>();

        favoris = dbase.selectionnerTous();

        FavoriAdapter adapter = new FavoriAdapter(FavorisActivity.this, favoris);
        FavListView.setAdapter(adapter);

       // System.out.println(f.getNomArret());
    }

}
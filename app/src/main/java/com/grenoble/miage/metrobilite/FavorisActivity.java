package com.grenoble.miage.metrobilite;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.grenoble.miage.metrobilite.Persistence.DAOFavori;
import com.grenoble.miage.metrobilite.Persistence.StockageFavoris;

import java.util.ArrayList;

/**
 * Created by prinsacn on 02/05/18.
 */

public class FavorisActivity extends AppCompatActivity {

    private ListView FavListView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);

        FavListView = (ListView) findViewById(R.id.listFavori);

        final DAOFavori dbase = DAOFavori.getDAOFavori(FavorisActivity.this);

        ArrayList<Favori> favoris = new ArrayList<Favori>();

        favoris = dbase.selectionnerTous();

        final FavoriAdapter adapter = new FavoriAdapter(FavorisActivity.this, favoris);
        FavListView.setAdapter(adapter);

        final ArrayList<Favori> finalFavoris = favoris;

        FavListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder adb=new AlertDialog.Builder(FavorisActivity.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + i);
                final int positionToRemove = i;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Favori f = finalFavoris.remove(positionToRemove);
                        adapter.notifyDataSetChanged();

                        dbase.supprimer(f.getId());
                    }});
                adb.show();
            }
        });

       // System.out.println(f.getNomArret());
    }

}
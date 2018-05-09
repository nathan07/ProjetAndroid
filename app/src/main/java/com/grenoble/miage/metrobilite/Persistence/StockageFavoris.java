package com.grenoble.miage.metrobilite.Persistence;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class StockageFavoris implements StockageService {

    int numFavo;

    @Override
    public List<ArrayList<String>> store(Context context, List<ArrayList<String>> favories) {
        SharedPreferences preferences = context.getSharedPreferences("Favoris", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        for(this.numFavo=0; numFavo<favories.size(); numFavo++) {
            String keyName = "favoris"+numFavo;
            HashSet<String> hS = new HashSet<String>();
            hS.addAll(favories.get(numFavo));
            editor.putStringSet(keyName,hS);
        }
        editor.commit();

        return null;
    }

    @Override
    public List<ArrayList<String>> restore(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("Favoris",Context.MODE_PRIVATE);
        List<ArrayList<String>> favories = new ArrayList<ArrayList<String>>();

        for(int i=0; i<numFavo; i++) {
            String keyName = "favoris"+i;
            HashSet<String> hS = new HashSet<String>();
            preferences.getStringSet(keyName,hS);
            favories.add(new ArrayList<String>(hS));
        }

        return favories;
    }

    @Override
    public void clear(Context context) {
    }

    @Override
    public void add(Context context, ArrayList<String> favorie) {
        List<ArrayList<String>> favo = this.restore(context);
        favo.add(favorie);
        this.store(context,favo);

    }
}

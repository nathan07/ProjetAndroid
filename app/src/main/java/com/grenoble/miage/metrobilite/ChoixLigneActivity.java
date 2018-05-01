package com.grenoble.miage.metrobilite;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;

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
    private ArrayList<Ligne> lignesTram = null;
    private Ligne[] lignesChrono = null;
    private Ligne[] lignesFlexo = null;
    private Ligne[] lignesProximo = null;

    @RequiresApi(api = Build.VERSION_CODES.N)
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

        //recup
        TableLayout linTram =(TableLayout) findViewById(R.id.trameLayout);
        ArrayList<TableRow> TabTram = new ArrayList<TableRow>();
        TabTram.add((TableRow) findViewById(R.id.Rowtram1));
        TabTram.add((TableRow) findViewById(R.id.Rowtram2));
        TabTram.add((TableRow) findViewById(R.id.Rowtram3));
        TabTram.add((TableRow) findViewById(R.id.Rowtram4));
        TabTram.add((TableRow) findViewById(R.id.Rowtram5));
        TableLayout linchrono =(TableLayout) findViewById(R.id.chronoLayout);
        TableLayout linProximo =(TableLayout) findViewById(R.id.proximoLayout);
        LinearLayout linFlexo =(LinearLayout) findViewById(R.id.flexoLayout);



        final String[] shortNamesLignes = new String[compterLignes("TRAM", lignes)];
        final String[] shortNamesLignesChrono = new String[compterLignes("CHRONO", lignes)];
        final String[] shortNamesLignesFlexo = new String[compterLignes("FLEXO", lignes)];
        final String[] shortNamesLignesProximo = new String[compterLignes("PROXIMO", lignes)];

        //lignesTram = new JSONObject[compterLignes("TRAM", lignes)];
        lignesTram = new ArrayList<Ligne>();
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
                    lignesTram.add(new Ligne(lignes[i]));
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

        lignesTram.sort(new Comparator<Ligne>() {
            @Override
            public int compare(Ligne ligne, Ligne t1) {
                return ligne.getShortName().compareTo(t1.getShortName());
            }
        }) ;
        for(int i = 0; i< indexLignesTram; i++) {
            /*int buttonStyle = R.style.Widget_AppCompat_Button;
            Button bt=new Button (new ContextThemeWrapper(this, buttonStyle), null, buttonStyle);*/
            Button bt = new Button(this);
            bt.setText(lignesTram.get(i).getShortName());
            bt.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.buttoncircle));
            GradientDrawable backgroundGradient = (GradientDrawable)bt.getBackground();
            backgroundGradient.setColor(Color.parseColor("#"+lignesTram.get(i).getColor()));
            final int finalI = i;
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent choixArret = new Intent(ChoixLigneActivity.this, ChoixArretActivity.class);
                    choixArret.putExtra("LIGNE", lignesTram.get(finalI).getTableau());
                    startActivity(choixArret);
                }
            });
            //bt.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
System.out.println(i%5);
            TabTram.get(0).addView(bt);
        }

        for(int i = 0; i< indexLignesChrono; i++) {
            /*int buttonStyle = R.style.Widget_AppCompat_ImageButton;
            Button bt=new Button (new ContextThemeWrapper(this, buttonStyle), null, buttonStyle);*/
            Button bt = new Button(this);
            bt.setText(lignesChrono[i].getShortName());
            bt.setBackgroundColor(Color.parseColor("#"+lignesChrono[i].getColor()));
            final int finalI = i;
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent choixArret = new Intent(ChoixLigneActivity.this, ChoixArretActivity.class);
                    choixArret.putExtra("LIGNE", lignesChrono[finalI].getTableau());
                    startActivity(choixArret);
                }
            });
            linchrono.addView(bt);
        }

        for(int i = 0; i< indexLignesProximo; i++) {
            /*int buttonStyle = R.style.Widget_AppCompat_ImageButton;
            Button bt=new Button (new ContextThemeWrapper(this, buttonStyle), null, buttonStyle);*/
            Button bt = new Button(this);
            bt.setText(lignesProximo[i].getShortName());
            bt.setBackgroundColor(Color.parseColor("#"+lignesProximo[i].getColor()));
            final int finalI = i;
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent choixArret = new Intent(ChoixLigneActivity.this, ChoixArretActivity.class);
                    choixArret.putExtra("LIGNE", lignesProximo[finalI].getTableau());
                    startActivity(choixArret);
                }
            });
            linProximo.addView(bt);
        }

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

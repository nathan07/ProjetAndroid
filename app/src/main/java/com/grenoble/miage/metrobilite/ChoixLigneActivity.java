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
import android.widget.GridLayout;
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
    private ArrayList<Ligne> lignesChrono = null;
    private ArrayList<Ligne> lignesFlexo = null;
    private ArrayList<Ligne> lignesProximo = null;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chargement_spinner);

        final DataCollector dataCollector = new DataCollector();
        try {
            ThreadRecupLignes thread = new ThreadRecupLignes(lignes, dataCollector, "https://data.metromobilite.fr/api/routers/default/index/routes");
            thread.start();
            thread.join();
            lignes = thread.getTab();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (lignes!=null && lignes.length != 0) {



            //lignesTram = new JSONObject[compterLignes("TRAM", lignes)];
            lignesTram = new ArrayList<Ligne>();
            lignesChrono = new ArrayList<Ligne>();
            lignesFlexo = new ArrayList<Ligne>();
            lignesProximo = new ArrayList<Ligne>();

            int indexLignesChrono = 0;
            int indexLignesFlexo = 0;
            int indexLignesProximo = 0;
            for (int i = 0; i < lignes.length; i++) {
                try {
                    if (lignes[i].getString("type").contains("TRAM")) {
                        lignesTram.add(new Ligne(lignes[i]));
                    } else if (lignes[i].getString("type").contains("CHRONO")) {
                        lignesChrono.add(new Ligne(lignes[i]));
                    } else if (lignes[i].getString("type").contains("FLEXO")) {
                        lignesFlexo.add(new Ligne(lignes[i]));
                    } else if (lignes[i].getString("type").contains("PROXIMO")) {
                        lignesProximo.add(new Ligne(lignes[i]));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (Build.VERSION.SDK_INT > 23) {
                lignesTram.sort(new Comparator<Ligne>() {
                    @Override
                    public int compare(Ligne ligne, Ligne t1) {
                        return ligne.getShortName().compareTo(t1.getShortName());
                    }

                });
                lignesChrono.sort(new Comparator<Ligne>() {
                    @Override
                    public int compare(Ligne ligne, Ligne t1) {
                        return ligne.getShortName().compareTo(t1.getShortName());
                    }

                });
                lignesFlexo.sort(new Comparator<Ligne>() {
                    @Override
                    public int compare(Ligne ligne, Ligne t1) {
                        return ligne.getShortName().compareTo(t1.getShortName());
                    }

                });
                lignesProximo.sort(new Comparator<Ligne>() {
                    @Override
                    public int compare(Ligne ligne, Ligne t1) {
                        return ligne.getShortName().compareTo(t1.getShortName());
                    }

                });


            }
            setContentView(R.layout.activity_choix_ligne);

            //recup
            GridLayout linTram = (GridLayout) findViewById(R.id.trameLayout);
            GridLayout linchrono = (GridLayout) findViewById(R.id.chronoLayout);
            GridLayout linProximo = (GridLayout) findViewById(R.id.proximoLayout);
            GridLayout linFlexo = (GridLayout) findViewById(R.id.flexoLayout);

            addbuttonArret(linTram,lignesTram);

            addbuttonArret(linchrono,lignesChrono);

            addbuttonArret(linProximo,lignesProximo);

            addbuttonArret(linFlexo,lignesFlexo);
            }
        else {
            setContentView(R.layout.erreur_recup_donnee);
        }
    }

    private void addbuttonArret(GridLayout linTram, final ArrayList<Ligne> lin) {
        for (int i = 0; i < lin.size(); i++) {
        /*int buttonStyle = R.style.Widget_AppCompat_Button;
        Button bt=new Button (new ContextThemeWrapper(this, buttonStyle), null, buttonStyle);*/
            Button bt = new Button(this);
            bt.setText(lin.get(i).getShortName());
            bt.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.buttoncircle));
            GradientDrawable backgroundGradient = (GradientDrawable) bt.getBackground();
            backgroundGradient.setColor(Color.parseColor("#" + lin.get(i).getColor()));

            final int finalI = i;
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent choixArret = new Intent(ChoixLigneActivity.this, ChoixArretActivity.class);
                    choixArret.putExtra("LIGNE", lin.get(finalI).getTableau());
                    startActivity(choixArret);
                }
            });
            linTram.addView(bt);
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

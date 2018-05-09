package com.grenoble.miage.metrobilite.Persistence;

import android.content.Context;

import com.grenoble.miage.metrobilite.Ligne;

import java.util.ArrayList;
import java.util.List;

public interface StockageService {
    /**
     * Enregistre la liste des favoris passés en paramètre.
     *@param context contexte de l'activité
     *@param favories liste des articles
     *@return liste des Favoris sauvegardés par ordre alphabétique
     */
    public List<ArrayList<String>> store(Context context, List<ArrayList<String>> favories);
    /**
     * Récupère la liste des favoris sauvegardés.
     *@param context contexte de l'activité
     *@return liste des de Favoris sauvegardés par ordre alphabétique
     */
    public List<ArrayList<String>> restore(Context context);
/**
 * Vide la liste des Favoris.
 *@param context contexte de l'activité
 *@return liste des Favoris vide.
 */
    public void clear(Context context);
    /**
     * Enregistre un nouvel favoris passé en paramètre.
     *@param context contexte de l'activité
     *@param favorie favoris
     */
    public void add(Context context, ArrayList<String> favorie);
}

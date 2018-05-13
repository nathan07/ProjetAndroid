package com.grenoble.miage.metrobilite.Persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.grenoble.miage.metrobilite.Favori;

import java.util.ArrayList;

public class DAOFavori extends DAOBase {

    public static final String FAVORI_KEY = "id";
    public static final String FAVORI_IDLIGNE = "IdLigne";
    public static final String FAVORI_NOMLIGNE = "NomLigne";
    public static final String FAVORI_CODEARRET = "CodeArret";
    public static final String FAVORI_NOMARRET = "NomArret";
    public static final String FAVORI_DESTINATION = "Destination";
    public static final String FAVORI_DIRECTION = "Direction";

    public static final String FAVORI_TABLE_NAME = "FAVORI";

    private static DAOFavori DaoF;

    private DAOFavori(Context pContext) {
        super(pContext);
        this.DaoF=this;
    }

    public static DAOFavori getDAOFavori(Context context)
        {
            if(DaoF==null)
            {
                DaoF= new DAOFavori(context);
            }
            return DaoF;
        }


    /**
     * @param m le métier à ajouter à la base
     */
    public void ajouter(Favori m) {
        this.open();
        ContentValues value = new ContentValues();
        value.put(FAVORI_IDLIGNE, m.getIdLigne());
        value.put(FAVORI_NOMLIGNE, m.getNomLigne());
        value.put(FAVORI_CODEARRET, m.getCodeArret());
        value.put(FAVORI_NOMARRET, m.getNomArret());
        value.put(FAVORI_DESTINATION, m.getDestination());
        value.put(FAVORI_DIRECTION, m.getDirection());
        this.mDb.insert(FAVORI_TABLE_NAME,null, value);
        this.close();
    }

    /**
     * @param id l'identifiant du métier à supprimer
     */
    public void supprimer(long id) {
        this.open();
        mDb.delete(FAVORI_TABLE_NAME, FAVORI_KEY + " = ?", new String[] {String.valueOf(id)});
        this.close();
    }

    /**
     * @param m le métier modifié
     */
    public void modifier(Favori m) {
    }

    /**
     * @param id l'identifiant du métier à récupérer
     */
    public Favori selectionner(long id) {
        this.open();
        Cursor c = mDb.rawQuery("select * from " + FAVORI_TABLE_NAME + " where id=?", new String[]{String.valueOf(id)});
        System.out.println(c.getCount());
        Favori f=new Favori(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getInt(6));
        c.close();
        this.close();

        return f;
    }

    public ArrayList<Favori> selectionnerTous() {
        ArrayList<Favori> ArrayFavori= new ArrayList<Favori>();
        this.open();
        Cursor c =  this.mDb.query(FAVORI_TABLE_NAME,null,null,null,null,null,null);
        while(c.moveToNext()) {
            ArrayFavori.add(new Favori(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4),c.getString(5),c.getInt(6)));
        }
        c.close();
        this.close();
        return ArrayFavori;

    }

    public boolean ifExiste(Favori f) {
        this.open();
        Cursor c = mDb.rawQuery("select * from " + FAVORI_TABLE_NAME + " where IdLigne = ? And NomLigne = ? AND CodeArret = ? AND NomArret = ? AND Destination = ? AND Direction = ?", new String[]{f.getIdLigne(), f.getNomLigne(), f.getCodeArret(), f.getNomArret(), f.getDestination(), String.valueOf(f.getDirection())});
        while(c.moveToNext())
        {
            if (f.getIdLigne() != c.getString(1) && f.getNomLigne() != c.getString(2) && f.getCodeArret() != c.getString(3) && f.getNomArret() != c.getString(4) && f.getDestination() != c.getString(5) && f.getDirection() != c.getInt(6)) {
                return true;
            }
        }
        return false;
    }
}

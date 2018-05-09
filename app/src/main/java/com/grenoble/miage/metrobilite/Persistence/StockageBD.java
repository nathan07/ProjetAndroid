package com.grenoble.miage.metrobilite.Persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StockageBD extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "FAVORI_DB";

    public static final String FAVORI_KEY = "id";
    public static final String FAVORI_IDLIGNE = "IdLigne";
    public static final String FAVORI_NOMLIGNE = "NomLigne";
    public static final String FAVORI_NOMARRET = "NomArret";
    public static final String FAVORI_DESTINATION = "Destination";

    public static final String FAVORI_TABLE_NAME = "FAVORI";
    public static final int DATABASE_VERSION =1;

    public static final String FAVORI_TABLE_CREATE =

            "CREATE TABLE " + FAVORI_TABLE_NAME + " (" +

                    FAVORI_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FAVORI_IDLIGNE + " TEXT, " +
                    FAVORI_NOMLIGNE + " TEXT, " +
                    FAVORI_NOMARRET + " TEXT, " +
                    FAVORI_DESTINATION + " TEXT);";



    public static final String FAVORI_TABLE_DROP = "DROP TABLE IF EXISTS " + FAVORI_TABLE_NAME + ";";



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(FAVORI_TABLE_CREATE);
    }

    public StockageBD(Context context) {

        super(context, "FAVORI_DB", null, 1);

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(FAVORI_TABLE_DROP);
            onCreate(sqLiteDatabase);

    }
}

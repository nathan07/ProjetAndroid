package com.grenoble.miage.metrobilite.Persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class DAOBase {

    protected SQLiteDatabase mDb = null;
    protected StockageBD mHandler = null;

    public DAOBase(Context pContext) {

        this.mHandler = new StockageBD(pContext);

    }
    public SQLiteDatabase open() {
        mDb = mHandler.getWritableDatabase();
        return mDb;
    }
    public void close() {
        mDb.close();
    }

    public SQLiteDatabase getDb() {

        return mDb;
    }

}

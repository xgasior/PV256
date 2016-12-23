package cz.muni.fi.pv256.movio2.a448273.Peristance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cz.muni.fi.pv256.movio2.a448273.Entity.Type;

import static cz.muni.fi.pv256.movio2.a448273.Peristance.MovioContract.*;

/**
 * Created by gasior on 04.12.2016.
 */

public class MovioDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movio.db";
    private static final int DATABASE_VERSION = 12;

    public MovioDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TYPE_TABLE = "CREATE TABLE " + TypeEntry.TABLE_NAME + " (" +
                TypeEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
                TypeEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                TypeEntry.COLUMN_URL_PARAMETERS + " TEXT NOT NULL" +
                " );";

        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                MovieEntry.COLUMN_ID + " INT UNIQUE, " +
                MovieEntry.COLUMN_BACK_DROP + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_COVER_PATH + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_POPULARITY + " REAL NOT NULL, " +
                MovieEntry.COLUMN_RELEASE_DATA + " INTEGER NOT NULL, " +
                MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_TYPE_ID + " INTEGER " + " , FOREIGN KEY ("+ MovieEntry.COLUMN_TYPE_ID + ") REFERENCES "+TypeEntry.TABLE_NAME+"("+TypeEntry.COLUMN_ID +")" +
                " );";

        db.execSQL(SQL_CREATE_TYPE_TABLE);
        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TypeEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
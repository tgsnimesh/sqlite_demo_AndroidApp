package com.example.sqlitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {

    public static final String DB_NAME = "todo";
    public static final int VERSION = 1;

    public static final String TABLE_NAME = "todo";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String STARTED = "started";
    public static final String FINISHED = "finished";

    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /*
         *  CREATE TABLE todo
         *      ( id INTEGER PRIMARY KEY AUTOINCREMENT
         *      , title TEXT
         *      , description TEXT
         *      , started TEXT
         *      , finished TEXT );
         */
        final String TABLE_CREATE_QUERY = "CREATE TABLE " + TABLE_NAME
                + "("
                + ID + " PRIMARY KEY AUTOINCREMENT, "
                + TITLE + " TEXT, "
                + DESCRIPTION + " TEXT, "
                + STARTED + " TEXT, "
                + FINISHED + " TEXT"
                + ");";

        // execute SQL query
        db.execSQL(TABLE_CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // drop table if database version has benn updated
        String TABLE_DROP_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
        // execute SQL query
        db.execSQL(TABLE_DROP_QUERY);
        // crete again new database
        onCreate(db);
    }
}

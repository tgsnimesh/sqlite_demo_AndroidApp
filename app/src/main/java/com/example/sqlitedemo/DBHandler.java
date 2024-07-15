package com.example.sqlitedemo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
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

    // add new todo to the todo table
    public void insertData(TODOModel todoModel) {

        // get writable access from database
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        // struct user data data inside of content values object
        ContentValues todoContentValues = new ContentValues();
        todoContentValues.put(TITLE, todoModel.getTitle());
        todoContentValues.put(DESCRIPTION, todoModel.getDescription());
        todoContentValues.put(STARTED, todoModel.getStarted());
        todoContentValues.put(FINISHED, todoModel.getFinished());

        // insert data to the table
        sqLiteDatabase.insert(TABLE_NAME, null, todoContentValues);
        //close database  connection
        sqLiteDatabase.close();
    }

    // get todos count
    public int getTODOCount() {

        // get readable access from the database
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        // define select all query from the database
        final String SELECT_ALL_QUERY = "SELECT * FROM " + TABLE_NAME;
        // execute sql query
        @SuppressLint("Recycle")
        Cursor allTODOCursor = sqLiteDatabase.rawQuery(SELECT_ALL_QUERY, null);
        // get count
        return allTODOCursor.getCount();
    }
}

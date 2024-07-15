package com.example.sqlitedemo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

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

    // get all todo from the todo table
    public List<TODOModel> getAllTODOS() {

        // define empty todos array list and database readable object
        ArrayList<TODOModel> todos = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        // define sqlite select query and execute it
        final String SELECT_ALL_QUERY = "SELECT * FROM " + TABLE_NAME;
        Cursor allTODOCursor = sqLiteDatabase.rawQuery(SELECT_ALL_QUERY, null);

        // get first row and check if it's null table or not
        if (allTODOCursor.moveToFirst()) {
            // if it is not null so, get cursor object have data column by column and parse it in to the model class
            do {

                TODOModel todoModel = new TODOModel(
                        allTODOCursor.getInt(0),
                        allTODOCursor.getString(1),
                        allTODOCursor.getString(2),
                        allTODOCursor.getLong(3),
                        allTODOCursor.getLong(4)
                );

                // add model object in to the array list
                todos.add(todoModel);
            } while (allTODOCursor.moveToNext());
        }

        // return all todo array list
        return todos;
    }

    // delete single todo
    public void deleteSingleTodo(int id) {

        // get writable access from the database
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        // delete row is equal to the id
        sqLiteDatabase.delete(TABLE_NAME, ID + " = ?", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

    // select single todo by id
    public TODOModel selectSingleTodo(int id) {

        // get reference to the database, write select query and execute
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor selectedTodo = sqLiteDatabase.query(
                TABLE_NAME
                , new String[]{ID, TITLE, DESCRIPTION, STARTED, FINISHED}
                , ID + " = ? "
                , new String[]{String.valueOf(id)}
                , null, null, null);

        // set selected row to the model class and return if it isn't null
        TODOModel todo;
        if (selectedTodo.moveToFirst()) {
            todo = new TODOModel(
                    selectedTodo.getInt(0),
                    selectedTodo.getString(1),
                    selectedTodo.getString(2),
                    selectedTodo.getLong(3),
                    selectedTodo.getLong(4)
            );

            return todo;
        }

        return null;
    }
}

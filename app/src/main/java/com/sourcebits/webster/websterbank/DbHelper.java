package com.sourcebits.webster.websterbank;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bhargava Gugamsetty on 11/30/2015.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "webster.db";
    private static final int DATABASE_VERSION = 1;
    public static final String WEBSTER_TABLE_NAME = "login";
    private static final String WEBSTER_TABLE_CREATE =
            "CREATE TABLE " + WEBSTER_TABLE_NAME + "(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT NOT NULL, password TEXT NOT NULL, email TEXT NOT NULL);";
    private static final String WEBSTER_DB_ADMIN = "INSERT INTO " + WEBSTER_TABLE_NAME + "values(1, admin, password, admin@gmail.com);";


    public DbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        System.out.println("In constructor");

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //Create Database
            db.execSQL(WEBSTER_TABLE_CREATE);

            //create admin account
            db.execSQL(WEBSTER_DB_ADMIN);
            System.out.println("In onCreate");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase arg0, int oldVersion, int newVersion) {


    }

}

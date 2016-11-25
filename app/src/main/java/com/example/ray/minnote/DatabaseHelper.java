package com.example.ray.minnote;
/**
 * Created by Ray on 16/03/16.
 * * @Author：Rui(Ray) Min u5679105
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper
{
    // Database version
    private static final int DATABASE_VERSION = 1;
    // Database name
    private static final String DATABASE_NAME = "MinNoteDB.db";
    // Database table name
    public static final String TABLE_NAME = "NoteTable";
    // Construct function
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version, DatabaseErrorHandler errorHandler)
    {
        super(context, name, factory, version, errorHandler);

    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version)
    {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(AppConstants.LOG_TAG, "DatabaseHelper Constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // It will be called when the database is first created.
        Log.d(AppConstants.LOG_TAG, "DatabaseHelper onCreate");
        StringBuffer sb = new StringBuffer();

        sb.append("CREATE TABLE [" + TABLE_NAME + "] (");
        sb.append("[_id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sb.append("[title] TEXT,");
        sb.append("[context] TEXT)");
        db.execSQL(sb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // It will be called when DATABASE_VERSION changes.

        Log.d(AppConstants.LOG_TAG, "DatabaseHelper onUpgrade");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db)
    {
        super.onOpen(db);
        Log.d(AppConstants.LOG_TAG, "DatabaseHelper onOpen");
    }

}


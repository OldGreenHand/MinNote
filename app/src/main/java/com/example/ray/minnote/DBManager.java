package com.example.ray.minnote;

/**
 * Created by Ray on 18/03/16.
 * * @Author：Rui(Ray) Min u5679105
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context)
    {
        Log.d(AppConstants.LOG_TAG, "DBManager --> Constructor");
        helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    /**
     * add notes
     *
     * @param note
     */
    public void add(Note note)
    {
        Log.d(AppConstants.LOG_TAG, "DBManager --> add");
        db.beginTransaction();
        try
        {
            db.execSQL("INSERT INTO " + DatabaseHelper.TABLE_NAME
                        + " VALUES(null, ?, ?)", new Object[] { note.title,
                        note.context });
            db.setTransactionSuccessful();
        }
        finally
        {
            db.endTransaction();
        }
    }

    /**
     * update note's title and context
     *
     * @param note
     */
    public void update(Note note)
    {
        Log.d(AppConstants.LOG_TAG, "DBManager --> update");
        ContentValues cv = new ContentValues();
        cv.put("title", note.title);
        cv.put("context", note.context);
        db.update(DatabaseHelper.TABLE_NAME, cv, "_id = ?",
                new String[]{String.valueOf(note._id)});

    }

    /**
     * delete old note
     *
     * @param note
     */
    public void deleteOldNote(Note note)
    {
        Log.d(AppConstants.LOG_TAG, "DBManager --> deleteNote");
        System.out.println("The deleting note's id is " + note._id);
        db.delete(DatabaseHelper.TABLE_NAME, "_id = ?",
                new String[]{String.valueOf(note._id)});
    }

    public void deleteTable()
    {
        Log.d(AppConstants.LOG_TAG, "DBManager --> deleteAllNote");
        db.execSQL("DROP TABLE IF EXISTS " + helper.TABLE_NAME);
        helper.onCreate(db);
    }

    /**
     * query all notes, return list
     *
     * @return List<Note>
     */

    public List<Note> query()
    {
        Log.d(AppConstants.LOG_TAG, "DBManager --> query");
        ArrayList<Note> notes = new ArrayList<Note>();
        Cursor c = queryTheCursor();
        while (c.moveToNext())
        {
            Note note = new Note();
            note._id = c.getInt(c.getColumnIndex("_id"));
            note.title = c.getString(c.getColumnIndex("title"));
            note.context = c.getString(c.getColumnIndex("context"));
            notes.add(note);
        }
        c.close();
        return notes;
    }

    /**
     * query all notes, return cursor
     *
     * @return Cursor
     */
    public Cursor queryTheCursor()
    {
        Log.d(AppConstants.LOG_TAG, "DBManager --> queryTheCursor");
        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME,
                null);
        return c;
    }

}

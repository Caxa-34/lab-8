package com.example.notewithdb.orlov;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper {
    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE notes (id INT, txt TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public int getMaxId() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT MAX(id) FROM notes";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst()) return cur.getInt(0);
        return 0;
    }

    public void addNote(int id, String txt) {
        String sId = String.valueOf(id);
        String sql = "INSERT INTO notes VALUES('" + sId + "', '" + txt + "');";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    public String getNote(int id){
        String sId = String.valueOf(id);
        String sql = "SELECT txt FROM notes WHERE id = '" + sId + "';";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst()) return cur.getString(0);
        return "";
    }

    public void Update(int id, String txt) {
        String sId = String.valueOf(id);
        String sql = "UPDATE notes SET txt = '" + txt + "' WHERE id='" + sId + "';";

        SQLiteDatabase db = getWritableDatabase();

        db.execSQL(sql);
    }

    public void Delete(int id) {
        String sId = String.valueOf(id);
        String sql = "DELETE FROM notes WHERE id='" + sId + "';";

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    public boolean SearchKey(int id) {
        String sId = String.valueOf(id);
        String sql = "SELECT EXISTS(SELECT * FROM notes WHERE id = '" + sId + "' LIMIT 1);";
        SQLiteDatabase db = getReadableDatabase();

        Cursor cur = db.rawQuery(sql, null);
        cur.moveToFirst();

        if (cur.getInt(0) == 1) {
            cur.close();
            return true;
        }
        cur.close();
        return false;
    }

    public void getAllNotes(ArrayList<Note> list) {
        String sql = "SELECT * FROM notes;";
        SQLiteDatabase db = getWritableDatabase();

        Cursor cur = db.rawQuery(sql, null);


        if (cur.moveToFirst()) {
            do {
                Note note = new Note();
                note.id = cur.getInt(0);
                note.txt = cur.getString(1);
                list.add(note);
            }
            while (cur.moveToNext());
        }
        cur.close();
    }
}
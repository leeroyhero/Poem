package ru.bogdanov.poem.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Владимир on 15.09.2016.
 */
public class DBHelper extends SQLiteOpenHelper{
    private final static String DB_NAME="db";
    private final static String LOG="db_info";
    private final static int DB_VERSION=1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + DBNamespace.TABLE_NAME +
                "(" + "id" + " integer primary key," +
                DBNamespace.POEM_NAME + " text" + ")");
    }

    public void addToDB(SQLiteDatabase db, String text){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBNamespace.POEM_NAME,text);
        Log.i(LOG,"Inserted");
        db.insert(DBNamespace.TABLE_NAME,null,contentValues);
    }

    public ArrayList<String> getPoemArray(SQLiteDatabase db){
        ArrayList<String> list=new ArrayList<>();
        Cursor cursor=db.query(DBNamespace.TABLE_NAME,new String[]{DBNamespace.POEM_NAME},null,null,null,null,null);
        cursor.moveToFirst();
        do {
            String text=cursor.getString(cursor.getColumnIndex(DBNamespace.POEM_NAME));
            list.add(text);
            Log.i(LOG,"Getted");
        } while (cursor.moveToNext());
        cursor.close();
        return list;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

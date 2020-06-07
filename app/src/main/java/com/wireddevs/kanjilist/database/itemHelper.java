package com.wireddevs.kanjilist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class itemHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "kanjipositionlist_db";

    public itemHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create notes table
        db.execSQL(item.CREATE_TABLE);
        for(int i=0;i<2136;i++){
            ContentValues values = new ContentValues();
            // `id` and `timestamp` will be inserted automatically.
            // no need to add them
            values.put(item.COLUMN_ITEM_POSITION, i+1);
            values.put(item.COLUMN_ITEM_STATUS, 0);
            db.insert(item.TABLE_NAME, null, values);
        }
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + item.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public int getItemCount() {
        String countQuery = "SELECT  * FROM " + item.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }

    public void deleteAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("delete from "+ item.TABLE_NAME);
        db.close();
    }

    public Cursor getAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM "+item.TABLE_NAME,null);
    }


    public int getStatus(int position) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(item.TABLE_NAME,
                new String[]{item.COLUMN_ITEM_POSITION,item.COLUMN_ITEM_STATUS},
                item.COLUMN_ITEM_POSITION + "=?",
                new String[]{String.valueOf(position)}, null, null, null, null);
            cursor.moveToFirst();
            int statusget = cursor.getInt(1);
            // close the db connection
            cursor.close();
            db.close();
            return statusget;
    }

    public void updateStatus(int position, int status){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();

        values.put(item.COLUMN_ITEM_STATUS,status);

        db.update(item.TABLE_NAME,values,item.COLUMN_ITEM_POSITION + " = ? ", new String[]{String.valueOf(position)});
        db.close();
    }
    public int getCountByStatus(int status){
        String countQuery = "SELECT * FROM " + item.TABLE_NAME + " WHERE "+ item.COLUMN_ITEM_STATUS + " = " +status;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }
}

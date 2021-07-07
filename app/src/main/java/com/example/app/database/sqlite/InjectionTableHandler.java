package com.example.app.database.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.app.database.pojo.Injection;
import com.example.app.database.pojo.Patient;

import java.util.LinkedList;
import java.util.List;

public class InjectionTableHandler {
    Context context;

    public InjectionTableHandler(Context context){
        this.context = context;
    }
    public List<Injection> getInjections(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                Contract.FeedInjection.COLUMN_NAME_BOTTLE_ID,
                Contract.FeedInjection.COLUMN_NAME_PATIENT_ID,
                Contract.FeedInjection.COLUMN_NAME_DEVICE_NAME,
                Contract.FeedInjection.COLUMN_NAME_START_TIME,
                Contract.FeedInjection.COLUMN_NAME_STOP_TIME
        };


        Cursor cursor = db.query(
                Contract.FeedInjection.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                Contract.FeedInjection._ID + " DESC"               // The sort order
        );

        List<Injection> injections = new LinkedList<>();
        while(cursor.moveToNext()) {
            Injection injection = new Injection(
                    cursor.getInt(cursor.getColumnIndexOrThrow(Contract.FeedInjection._ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Contract.FeedInjection.COLUMN_NAME_BOTTLE_ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Contract.FeedInjection.COLUMN_NAME_PATIENT_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedInjection.COLUMN_NAME_DEVICE_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedInjection.COLUMN_NAME_START_TIME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedInjection.COLUMN_NAME_STOP_TIME)));
            injections.add(injection);
        }
        cursor.close();

        return injections;
    }

    public int updateStopTime(int ID){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(Contract.FeedInjection.COLUMN_NAME_STOP_TIME, "datetime('now','localtime')");


        // Which row to update, based on the title
        String selection = Contract.FeedInjection._ID + " LIKE ?";
        String[] selectionArgs = { Integer.toString(ID) };

        int count = db.update(
                Contract.FeedInjection.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }


    public int addInjection(int patientID, int bottleID, String deviceName){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put(Contract.FeedInjection.COLUMN_NAME_BOTTLE_ID, bottleID);
        values.put(Contract.FeedInjection.COLUMN_NAME_PATIENT_ID, patientID);
        values.put(Contract.FeedInjection.COLUMN_NAME_DEVICE_NAME, deviceName);
        values.put(Contract.FeedInjection.COLUMN_NAME_START_TIME, "datetime('now','localtime')");
        //values.put(Contract.FeedInjection.COLUMN_NAME_STOP_TIME, null);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(Contract.FeedPatient.TABLE_NAME, null, values);

        if(newRowId > -1)
            return 1;
        return 0;
    }

}

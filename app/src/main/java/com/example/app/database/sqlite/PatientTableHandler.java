package com.example.app.database.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.app.database.pojo.Patient;

import java.util.LinkedList;
import java.util.List;

/**
 * All methods return 1 if success, 0 if an error occurred
 */
public class PatientTableHandler {
    Context context;
    public static Patient selectedPatient;
    public PatientTableHandler(Context context){
        this.context = context;
    }
    public List<Patient> getPatients(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                Contract.FeedPatient.COLUMN_NAME_FIRST_NAME,
                Contract.FeedPatient.COLUMN_NAME_LAST_NAME,
                Contract.FeedPatient.COLUMN_NAME_AGE,
                Contract.FeedPatient.COLUMN_NAME_CITY
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = Contract.FeedPatient.COLUMN_IS_DELETED + " = ?";
        String[] selectionArgs = { "0" };

        Cursor cursor = db.query(
                Contract.FeedPatient.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List<Patient> patients = new LinkedList<>();
        while(cursor.moveToNext()) {
            Patient patient = new Patient(
                    cursor.getInt(cursor.getColumnIndexOrThrow(Contract.FeedPatient._ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedPatient.COLUMN_NAME_FIRST_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedPatient.COLUMN_NAME_LAST_NAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Contract.FeedPatient.COLUMN_NAME_AGE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedPatient.COLUMN_NAME_CITY)));
            patients.add(patient);
        }
        cursor.close();

        return patients;
    }

    public List<Patient> getAllPatients(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                Contract.FeedPatient.COLUMN_NAME_FIRST_NAME,
                Contract.FeedPatient.COLUMN_NAME_LAST_NAME,
                Contract.FeedPatient.COLUMN_NAME_AGE,
                Contract.FeedPatient.COLUMN_NAME_CITY
        };


        Cursor cursor = db.query(
                Contract.FeedPatient.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List<Patient> patients = new LinkedList<>();
        while(cursor.moveToNext()) {
            Patient patient = new Patient(
                    cursor.getInt(cursor.getColumnIndexOrThrow(Contract.FeedPatient._ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedPatient.COLUMN_NAME_FIRST_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedPatient.COLUMN_NAME_LAST_NAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Contract.FeedPatient.COLUMN_NAME_AGE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedPatient.COLUMN_NAME_CITY)));
            patients.add(patient);
        }
        cursor.close();

        return patients;
    }

    public int updatePatient(int ID, String name, String surname, int age, String city){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(Contract.FeedPatient.COLUMN_NAME_FIRST_NAME, name);
        values.put(Contract.FeedPatient.COLUMN_NAME_LAST_NAME, surname);
        values.put(Contract.FeedPatient.COLUMN_NAME_AGE, age);
        values.put(Contract.FeedPatient.COLUMN_NAME_CITY, city);

        // Which row to update, based on the title
        String selection = Contract.FeedPatient._ID + " LIKE ?";
        String[] selectionArgs = { Integer.toString(ID) };

        int count = db.update(
                Contract.FeedPatient.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public int deletePatient(int ID){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(Contract.FeedPatient.COLUMN_IS_DELETED, 1);

        // Which row to update, based on the title
        String selection = Contract.FeedPatient._ID + " LIKE ?";
        String[] selectionArgs = { Integer.toString(ID) };

        int count = db.update(
                Contract.FeedPatient.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;

    }

    public int addPatient(String name, String surname, int age, String city){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put(Contract.FeedPatient.COLUMN_NAME_FIRST_NAME, name);
        values.put(Contract.FeedPatient.COLUMN_NAME_LAST_NAME, surname);
        values.put(Contract.FeedPatient.COLUMN_NAME_AGE, age);
        values.put(Contract.FeedPatient.COLUMN_NAME_CITY, city);
        values.put(Contract.FeedPatient.COLUMN_IS_DELETED, 0);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(Contract.FeedPatient.TABLE_NAME, null, values);

        if(newRowId > -1)
            return 1;
        return 0;
    }
    public void addTestData(){
        DbHelper dbHelper = new DbHelper(context);
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for(int i = 0; i < 15; i++){
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();

            values.put(Contract.FeedPatient.COLUMN_NAME_FIRST_NAME, "name");
            values.put(Contract.FeedPatient.COLUMN_NAME_LAST_NAME, "surname");
            values.put(Contract.FeedPatient.COLUMN_NAME_AGE, 20);
            values.put(Contract.FeedPatient.COLUMN_NAME_CITY, "LA");
            values.put(Contract.FeedPatient.COLUMN_IS_DELETED, 0);

            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(Contract.FeedPatient.TABLE_NAME, null, values);
            Log.i("ID", Long.toString(newRowId));
        }

    }

    public Patient getSelectedPatient() {
        return selectedPatient;
    }

    public void setSelectedPatient(Patient selectedPatient) {
        this.selectedPatient = selectedPatient;
    }
}

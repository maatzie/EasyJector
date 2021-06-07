package com.example.app.database.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.app.database.pojo.Bottle;
import com.example.app.database.pojo.Patient;

import java.util.LinkedList;
import java.util.List;

/**
 * All methods return 1 if success, 0 if an error occurred
 */
public class BottleTableHandler {
    Context context;
    private static Bottle selectedBottle;
    public BottleTableHandler(Context context){
        this.context = context;
    }
    public List<Bottle> getBottles(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                Contract.FeedBottle.COLUMN_NAME_BOTTLE_ID,
                Contract.FeedBottle.COLUMN_NAME_BOTTLE_NAME,
                Contract.FeedBottle.COLUMN_NAME_VOLUME,
                Contract.FeedBottle.COLUMN_NAME_QUANTITY
        };

        Cursor cursor = db.query(
                Contract.FeedBottle.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List<Bottle> bottles = new LinkedList<>();
        while(cursor.moveToNext()) {
            Bottle bottle = new Bottle(
                    cursor.getInt(cursor.getColumnIndexOrThrow(Contract.FeedPatient._ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedBottle.COLUMN_NAME_BOTTLE_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedBottle.COLUMN_NAME_BOTTLE_NAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Contract.FeedBottle.COLUMN_NAME_VOLUME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Contract.FeedBottle.COLUMN_NAME_QUANTITY)));
            bottles.add(bottle);
        }
        cursor.close();

        return bottles;
    }

    public int updateBottle(int ID, String bottleID, String name, int volume, int quantity){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(Contract.FeedBottle.COLUMN_NAME_BOTTLE_ID, bottleID);
        values.put(Contract.FeedBottle.COLUMN_NAME_BOTTLE_NAME, name);
        values.put(Contract.FeedBottle.COLUMN_NAME_VOLUME, volume);
        values.put(Contract.FeedBottle.COLUMN_NAME_QUANTITY, quantity);

        // Which row to update, based on the title
        String selection = Contract.FeedBottle._ID + " LIKE ?";
        String[] selectionArgs = { Integer.toString(ID) };

        int count = db.update(
                Contract.FeedBottle.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public int deleteBottle(int ID){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Define 'where' part of query.
        String selection = Contract.FeedBottle._ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { Integer.toString(ID)};
        // Issue SQL statement.
        int deletedRows = db.delete(Contract.FeedBottle.TABLE_NAME, selection, selectionArgs);
        return deletedRows;
    }

    public int addBottle(String bottleID, String name, int volume, int quantity){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put(Contract.FeedBottle.COLUMN_NAME_BOTTLE_ID, bottleID);
        values.put(Contract.FeedBottle.COLUMN_NAME_BOTTLE_NAME, name);
        values.put(Contract.FeedBottle.COLUMN_NAME_VOLUME, volume);
        values.put(Contract.FeedBottle.COLUMN_NAME_QUANTITY, quantity);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(Contract.FeedBottle.TABLE_NAME, null, values);

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

            values.put(Contract.FeedBottle.COLUMN_NAME_BOTTLE_ID, "1234");
            values.put(Contract.FeedBottle.COLUMN_NAME_BOTTLE_NAME, "INKA");
            values.put(Contract.FeedBottle.COLUMN_NAME_VOLUME, 10);
            values.put(Contract.FeedBottle.COLUMN_NAME_QUANTITY, -14);

            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(Contract.FeedBottle.TABLE_NAME, null, values);
            Log.i("ID", Long.toString(newRowId));
        }

    }

    public Bottle getSelectedBottle() {
        return selectedBottle;
    }

    public void setSelectedBottle(Bottle selectedBottle) {
        this.selectedBottle = selectedBottle;
    }
}

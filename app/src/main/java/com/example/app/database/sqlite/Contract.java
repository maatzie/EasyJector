package com.example.app.database.sqlite;

import android.provider.BaseColumns;

public final class Contract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private Contract() {}

    /* Inner class that defines the patient table contents */
    public static class FeedPatient implements BaseColumns {
        public static final String TABLE_NAME = "Patient";
        public static final String COLUMN_NAME_FIRST_NAME = "FirstName";
        public static final String COLUMN_NAME_LAST_NAME = "LastName";
        public static final String COLUMN_NAME_AGE = "Age";
        public static final String COLUMN_NAME_CITY = "City";
        public static final String COLUMN_IS_DELETED = "IsDeleted";
    }

    /* Inner class that defines the bottle table contents */
    public static class FeedBottle implements BaseColumns {
        public static final String TABLE_NAME = "Bottle";
        public static final String COLUMN_NAME_BOTTLE_ID = "BottleID";
        public static final String COLUMN_NAME_BOTTLE_NAME = "BottleName";
        public static final String COLUMN_NAME_VOLUME = "Volume";
        public static final String COLUMN_NAME_QUANTITY = "Quantity";
        public static final String COLUMN_IS_DELETED = "IsDeleted";
    }

    static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedPatient.TABLE_NAME + " (" +
                    FeedPatient._ID + " INTEGER PRIMARY KEY," +
                    FeedPatient.COLUMN_NAME_FIRST_NAME + " VARCHAR," +
                    FeedPatient.COLUMN_NAME_LAST_NAME + " VARCHAR," +
                    FeedPatient.COLUMN_NAME_AGE + " INTEGER," +
                    FeedPatient.COLUMN_NAME_CITY + " VARCHAR," +
                    FeedPatient.COLUMN_IS_DELETED + " INTEGER DEFAULT 0) ; \n" +

                    "CREATE TABLE " + FeedBottle.TABLE_NAME + " (" +
                    FeedBottle._ID + " INTEGER PRIMARY KEY," +
                    FeedBottle.COLUMN_NAME_BOTTLE_ID + " VARCHAR," +
                    FeedBottle.COLUMN_NAME_BOTTLE_NAME + " VARCHAR," +
                    FeedBottle.COLUMN_NAME_VOLUME + " INTEGER," +
                   // FeedBottle.COLUMN_IS_DELETED + " INTEGER DEFAULT 0," +
                    FeedBottle.COLUMN_NAME_QUANTITY + " INTEGER); ";

    static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedPatient.TABLE_NAME + "; " +
                    "DROP TABLE IF EXISTS " + FeedBottle.TABLE_NAME + "; ";
}

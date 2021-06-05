package com.example.app.database.sqlite;

import android.provider.BaseColumns;

public final class Contract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private Contract() {}

    /* Inner class that defines the table contents */
    public static class FeedPatient implements BaseColumns {
        public static final String TABLE_NAME = "Patient";
        public static final String COLUMN_NAME_FIRST_NAME = "FirstName";
        public static final String COLUMN_NAME_LAST_NAME = "LastName";
        public static final String COLUMN_NAME_AGE = "Age";
        public static final String COLUMN_NAME_CITY = "City";
    }

    static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedPatient.TABLE_NAME + " (" +
                    FeedPatient._ID + " INTEGER PRIMARY KEY," +
                    FeedPatient.COLUMN_NAME_FIRST_NAME + " VARCHAR," +
                    FeedPatient.COLUMN_NAME_LAST_NAME + " VARCHAR," +
                    FeedPatient.COLUMN_NAME_AGE + " INTEGER," +
                    FeedPatient.COLUMN_NAME_CITY + " VARCHAR)";

    static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedPatient.TABLE_NAME;
}

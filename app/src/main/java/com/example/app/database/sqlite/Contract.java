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

    /* Inner class that defines the injection table contents */
    public static class FeedInjection implements BaseColumns {
        public static final String TABLE_NAME = "Injection";
        public static final String COLUMN_NAME_BOTTLE_ID = "BottleID";
        public static final String COLUMN_NAME_PATIENT_ID = "PatientID";
        public static final String COLUMN_NAME_DEVICE_NAME = "DeviceName";
        public static final String COLUMN_NAME_START_TIME = "StartTime";
        public static final String COLUMN_NAME_STOP_TIME = "StopTime";
    }

    static final String[] SQL_CREATE_ENTITIES =
            { "CREATE TABLE " + FeedPatient.TABLE_NAME + " (" +
                    FeedPatient._ID + " INTEGER PRIMARY KEY," +
                    FeedPatient.COLUMN_NAME_FIRST_NAME + " VARCHAR," +
                    FeedPatient.COLUMN_NAME_LAST_NAME + " VARCHAR," +
                    FeedPatient.COLUMN_NAME_AGE + " INTEGER," +
                    FeedPatient.COLUMN_NAME_CITY + " VARCHAR," +
                    FeedPatient.COLUMN_IS_DELETED + " INTEGER DEFAULT 0) ; \n" ,

                    "CREATE TABLE " + FeedBottle.TABLE_NAME + " (" +
                    FeedBottle._ID + " INTEGER PRIMARY KEY," +
                    FeedBottle.COLUMN_NAME_BOTTLE_ID + " VARCHAR," +
                    FeedBottle.COLUMN_NAME_BOTTLE_NAME + " VARCHAR," +
                    FeedBottle.COLUMN_NAME_VOLUME + " INTEGER," +
                    FeedBottle.COLUMN_IS_DELETED + " INTEGER DEFAULT 0," +
                    FeedBottle.COLUMN_NAME_QUANTITY + " INTEGER); \n" ,

                    "CREATE TABLE " + FeedInjection.TABLE_NAME + " (" +
                    FeedInjection._ID + " INTEGER PRIMARY KEY," +
                    FeedInjection.COLUMN_NAME_BOTTLE_ID + " INTEGER," +
                    FeedInjection.COLUMN_NAME_PATIENT_ID + " INTEGER," +
                    FeedInjection.COLUMN_NAME_DEVICE_NAME + " VARCHAR," +
                    FeedInjection.COLUMN_NAME_START_TIME + " DATETIME," +
                    FeedInjection.COLUMN_NAME_STOP_TIME + " DATETIME);  "};



    static final String[] SQL_DELETE_ENTITIES =
            {"DROP TABLE IF EXISTS " + FeedPatient.TABLE_NAME + "; ",
                    "DROP TABLE IF EXISTS " + FeedInjection.TABLE_NAME + "; ",
                    "DROP TABLE IF EXISTS " + FeedBottle.TABLE_NAME + "; "};
}

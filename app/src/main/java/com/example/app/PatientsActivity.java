package com.example.app;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.app.adapters.PatientsRecyclerViewAdapter;
import com.example.app.database.pojo.Patient;
import com.example.app.database.sqlite.Contract;
import com.example.app.database.sqlite.DbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.BaseColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.app.ui.main.SectionsPagerAdapter;

import java.util.LinkedList;
import java.util.List;


public class PatientsActivity extends AppCompatActivity {

    private RecyclerView patientsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);


        //addData();
        getData();
        initRecyclerView();
    }

    private void initRecyclerView(){
        List<String> example = new LinkedList<>();
        for (int i = 0; i < 100; i++)
            example.add("test");
        patientsRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_patients);
        patientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        PatientsRecyclerViewAdapter adapter = new PatientsRecyclerViewAdapter(example, getSupportFragmentManager(), this);
        patientsRecyclerView.setAdapter(adapter);
        //patientsRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this.getActivity()));

    }
    private void addData(){
        DbHelper dbHelper = new DbHelper(getBaseContext());
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for(int i = 0; i < 15; i++){
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();

            values.put(Contract.FeedPatient.COLUMN_NAME_FIRST_NAME, "name");
            values.put(Contract.FeedPatient.COLUMN_NAME_LAST_NAME, "surname");
            values.put(Contract.FeedPatient.COLUMN_NAME_AGE, 20);
            values.put(Contract.FeedPatient.COLUMN_NAME_CITY, "LA");

            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(Contract.FeedPatient.TABLE_NAME, null, values);
            Log.i("ID", Long.toString(newRowId));
        }

    }

    private void getData(){
        DbHelper dbHelper = new DbHelper(getBaseContext());
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
        //String selection = FeedEntry.COLUMN_NAME_TITLE + " = ?";
        //String[] selectionArgs = { "My Title" };

        // How you want the results sorted in the resulting Cursor
       // String sortOrder =
       //         FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

        Cursor cursor = db.query(
                Contract.FeedPatient.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List<Patient> items = new LinkedList();
        while(cursor.moveToNext()) {
            //long itemId = cursor.getLong(
            //        cursor.getColumnIndexOrThrow(FeedEntry._ID));
            Patient patient = new Patient(cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedPatient.COLUMN_NAME_FIRST_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedPatient.COLUMN_NAME_LAST_NAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Contract.FeedPatient.COLUMN_NAME_AGE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedPatient.COLUMN_NAME_CITY)));
            items.add(patient);
        }
        cursor.close();

        for(Patient p : items){
            Log.i("PATIENT: ", p.toString());
        }
        Log.i("SIZE ", Integer.toString(items.size()));
    }
}
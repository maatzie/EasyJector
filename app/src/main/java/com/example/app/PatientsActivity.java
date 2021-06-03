package com.example.app;

import android.os.Bundle;

import com.example.app.adapters.PatientsRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

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
}
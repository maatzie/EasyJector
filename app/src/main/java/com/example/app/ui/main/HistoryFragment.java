package com.example.app.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.adapters.HistoryRecyclerViewAdapter;
import com.example.app.adapters.PatientsRecyclerViewAdapter;
import com.example.app.database.pojo.Bottle;
import com.example.app.database.pojo.Injection;
import com.example.app.database.pojo.Patient;
import com.example.app.database.sqlite.BottleTableHandler;
import com.example.app.database.sqlite.InjectionTableHandler;
import com.example.app.database.sqlite.PatientTableHandler;

import java.util.List;


public class HistoryFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private InjectionTableHandler injectionTableHandler;
    private RecyclerView injectionsRecyclerView;

    public static HistoryFragment newInstance(int index) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);

        injectionTableHandler = new InjectionTableHandler(root.getContext());
        injectionsRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerView_history);
        List<Injection> injections = injectionTableHandler.getInjections();
        List<Patient> patients = new PatientTableHandler(root.getContext()).getAllPatients();
        List<Bottle> bottles = new BottleTableHandler(root.getContext()).getAllBottles();
        initRecyclerView(injections, patients, bottles);

        return root;
    }

    public void initRecyclerView(List<Injection> injections, List<Patient> patients, List<Bottle> bottles){
        injectionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        HistoryRecyclerViewAdapter adapter = new HistoryRecyclerViewAdapter(injections, patients, bottles);
        injectionsRecyclerView.setAdapter(adapter);
        injectionsRecyclerView.setNestedScrollingEnabled(false);
        //patientsRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this.getActivity()));

    }


}
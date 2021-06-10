package com.example.app.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.BottlesActivity;
import com.example.app.PatientsActivity;
import com.example.app.R;
import com.example.app.adapters.PatientsRecyclerViewAdapter;
import com.example.app.database.sqlite.BottleTableHandler;
import com.example.app.database.sqlite.PatientTableHandler;

import java.util.LinkedList;
import java.util.List;


public class OperationFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;


    public static OperationFragment newInstance(int index) {
        OperationFragment fragment = new OperationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);


    }


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_operation, container, false);

        setButtonOnClickListener((Button) root.findViewById(R.id.button_patient), new PatientsActivity());
        setButtonOnClickListener((Button) root.findViewById(R.id.button_bottle), new BottlesActivity());

        PatientTableHandler patientTableHandler = new PatientTableHandler(root.getContext());
        if(patientTableHandler.getSelectedPatient() != null){
            Button patientButton = (Button) root.findViewById(R.id.button_patient);
            String text = patientTableHandler.getSelectedPatient().toString() +  getString(R.string.button_text_selected);
            patientButton.setText(text);
        }

        BottleTableHandler bottleTableHandler = new BottleTableHandler(root.getContext());
        if(bottleTableHandler.getSelectedBottle() != null){
            Button bottleButton = (Button) root.findViewById(R.id.button_bottle);
            String text = bottleTableHandler.getSelectedBottle().toString() +  getString(R.string.button_text_selected);
            bottleButton.setText(text);
        }

        return root;
    }
    private void setButtonOnClickListener(Button button, final Activity activity) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),activity.getClass());
                view.getContext().startActivity(intent);
            }
        });
    }

}
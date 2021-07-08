package com.example.app.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.app.R;
import com.example.app.database.pojo.Bottle;
import com.example.app.database.pojo.Injection;
import com.example.app.database.pojo.Patient;
import java.util.List;


public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder> {

    private final List<Injection> mValues;
    private final List<Patient> mPatients;
    private final List<Bottle> mBottles;


    public HistoryRecyclerViewAdapter(List<Injection> items, List<Patient> patients, List<Bottle> bottles) {
        mValues = items;
        mPatients = patients;
        mBottles = bottles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_history, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);

        for(Patient p : mPatients){
            if(p.getID() == holder.mItem.getPatientID()){
                holder.mContentViewPatient.setText(p.toString());
            }
        }

        for(Bottle b: mBottles){
            if(b.getID() == holder.mItem.getBottleID()){
                holder.mContentViewBottle.setText(b.toString());
            }
        }

        if(holder.mItem.getStartTime() != null)
            holder.mContentViewStartTime.setText(holder.mItem.getStartTime().toString());
        if(holder.mItem.getStopTime() != null)
            holder.mContentViewStopTime.setText(holder.mItem.getStopTime().toString());


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentViewPatient;
        public final TextView mContentViewBottle;
        public final TextView mContentViewStartTime;
        public final TextView mContentViewStopTime;

        public Injection mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentViewPatient = (TextView) view.findViewById(R.id.textView_history_patient);
            mContentViewBottle = (TextView) view.findViewById(R.id.textView_history_bottle);
            mContentViewStartTime = (TextView) view.findViewById(R.id.textView_history_startTime);
            mContentViewStopTime = (TextView) view.findViewById(R.id.textView_history_stopTime);

        }


        @Override
        public String toString() {
            return super.toString();
        }

    }
}

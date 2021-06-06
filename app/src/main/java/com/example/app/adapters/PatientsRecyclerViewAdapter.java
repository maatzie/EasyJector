package com.example.app.adapters;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.example.app.PatientsActivity;
import com.example.app.R;
import com.example.app.database.pojo.Patient;
import com.example.app.database.sqlite.PatientTableHandler;

import java.util.List;

/**
 * Class is user for displaying a list of resources. If resource is clicked, activity is changed to
 * ResourceInformationActivity ind resource information is shown.
 */
public class PatientsRecyclerViewAdapter extends RecyclerView.Adapter<PatientsRecyclerViewAdapter.ViewHolder> {

    private final List<Patient> mValues;
    private FragmentManager fragmentManager;
    private PatientsActivity activity;

    public PatientsRecyclerViewAdapter(List<Patient> items, FragmentManager fragmentManager, PatientsActivity activity) {
        mValues = items;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_patient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mContentViewName.setText(holder.mItem.getFirstName());
        holder.mContentViewSurname.setText(holder.mItem.getLastName());
        holder.mContentViewAge.setText(Integer.toString(holder.mItem.getAge()));
        holder.mContentViewCity.setText(holder.mItem.getCity());
        holder.mButtonSelect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.i("CLICK", "SELECT");
            }
        });
        holder.mButtonEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.i("CLICK", "EDIT");
            }
        });
        holder.mButtonDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                PatientTableHandler patientTableHandler = new PatientTableHandler(activity.getBaseContext());
                patientTableHandler.deletePatient(mValues.get(position).getID());
                patientTableHandler.getPatients();
                activity.initRecyclerView(patientTableHandler.getPatients());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentViewName;
        public final TextView mContentViewSurname;
        public final TextView mContentViewAge;
        public final TextView mContentViewCity;
        public final Button mButtonSelect;
        public final Button mButtonEdit;
        public final Button mButtonDelete;
        public Patient mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentViewName = (TextView) view.findViewById(R.id.textView_name);
            mContentViewSurname = (TextView) view.findViewById(R.id.textView_surname);
            mContentViewAge = (TextView) view.findViewById(R.id.textView_age);
            mContentViewCity = (TextView) view.findViewById(R.id.textView_city);
            mButtonSelect = (Button) view.findViewById(R.id.button_select);
            mButtonEdit = (Button) view.findViewById(R.id.button_edit);
            mButtonDelete = (Button) view.findViewById(R.id.button_delete);
        }


        @Override
        public String toString() {
            return super.toString() + " '" + mContentViewSurname.getText() + "'";
        }

    }
}
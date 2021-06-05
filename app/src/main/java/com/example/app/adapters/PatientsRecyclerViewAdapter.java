package com.example.app.adapters;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.app.R;
import com.example.app.database.pojo.Patient;

import java.util.List;

/**
 * Class is user for displaying a list of resources. If resource is clicked, activity is changed to
 * ResourceInformationActivity ind resource information is shown.
 */
public class PatientsRecyclerViewAdapter extends RecyclerView.Adapter<PatientsRecyclerViewAdapter.ViewHolder> {

    private final List<Patient> mValues;
    private FragmentManager fragmentManager;
    //private Activity activity;

    public PatientsRecyclerViewAdapter(List<Patient> items, FragmentManager fragmentManager, Activity activity) {
        mValues = items;
        this.fragmentManager = fragmentManager;
        //this.activity = activity;
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
//        holder.mContentView.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View view) {
//                ResourcesFragment.currentResource = mValues.get(position);
//                Intent intent = new Intent(fragment.getContext(), ResourceInformationActivity.class);
//                intent.putExtra(ResourcesFragment.EXTRA_MESSAGE, mValues.get(position).getId());
//                fragment.startActivity(intent);
//            }
//        });

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
        public Patient mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentViewName = (TextView) view.findViewById(R.id.textView_name);
            mContentViewSurname = (TextView) view.findViewById(R.id.textView_surname);
            mContentViewAge = (TextView) view.findViewById(R.id.textView_age);
            mContentViewCity = (TextView) view.findViewById(R.id.textView_city);
        }


        @Override
        public String toString() {
            return super.toString() + " '" + mContentViewSurname.getText() + "'";
        }

    }
}
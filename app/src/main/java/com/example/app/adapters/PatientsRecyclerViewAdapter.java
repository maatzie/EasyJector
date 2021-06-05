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

import java.util.List;

/**
 * Class is user for displaying a list of resources. If resource is clicked, activity is changed to
 * ResourceInformationActivity ind resource information is shown.
 */
public class PatientsRecyclerViewAdapter extends RecyclerView.Adapter<PatientsRecyclerViewAdapter.ViewHolder> {

    private final List<String> mValues;
    private FragmentManager fragmentManager;
    //private Activity activity;

    public PatientsRecyclerViewAdapter(List<String> items, FragmentManager fragmentManager, Activity activity) {
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
        public final TextView mContentView;
        public String mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);
        }


        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

    }
}
package com.example.app.adapters;


import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.app.MainActivity;
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
                Patient selectedPatient = mValues.get(position);
                PatientTableHandler patientTableHandler = new PatientTableHandler(activity.getBaseContext());
                patientTableHandler.setSelectedPatient(selectedPatient);

                // open main view
                Intent intent = new Intent(view.getContext(), new MainActivity().getClass());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                view.getContext().startActivity(intent);
            }
        });

        holder.mButtonDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                PatientTableHandler patientTableHandler = new PatientTableHandler(activity.getBaseContext());
                int exit = patientTableHandler.deletePatient(mValues.get(position).getID());
                patientTableHandler.getPatients();
                activity.initRecyclerView(patientTableHandler.getPatients());

                if(exit == 1) //success
                    Toast.makeText(activity.getApplicationContext(),"Patient has been deleted!", Toast.LENGTH_SHORT).show();
                else if(exit == 0) //error
                    Toast.makeText(activity.getApplicationContext(),"Error occurred while deleting a patient!", Toast.LENGTH_SHORT).show();
            }
        });

        holder.mButtonEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // set First Name to input
                EditText editText_Name = (EditText) activity.findViewById(R.id.editTextT_Name);
                editText_Name.setText(mValues.get(position).getFirstName());
                // set Last Name to input
                EditText editText_Surname = (EditText) activity.findViewById(R.id.editText_Surname);
                editText_Surname.setText(mValues.get(position).getLastName());
                // set Age to input
                EditText editText_Age = (EditText) activity.findViewById(R.id.editText_Age);
                editText_Age.setText(Integer.toString(mValues.get(position).getAge()));
                // set City to input
                EditText editText_City = (EditText) activity.findViewById(R.id.editText_City);
                editText_City.setText(mValues.get(position).getCity());

                // scroll to the bottom of views
                NestedScrollView scrollView = (NestedScrollView) activity.findViewById(R.id.nestedScrollView_Patients);
                //scrollView.smoothScrollTo(0, scrollView.getBottom());
                scrollView.fullScroll(View.FOCUS_DOWN);

                // set to edit mode
                activity.editPatientID = mValues.get(position).getID();
                Button cancelButton = (Button) activity.findViewById(R.id.button_cancelEditingPatient);
                cancelButton.setVisibility(View.VISIBLE);
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
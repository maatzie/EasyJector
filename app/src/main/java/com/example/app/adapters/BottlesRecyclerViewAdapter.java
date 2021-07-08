package com.example.app.adapters;


import androidx.core.content.ContextCompat;
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


import com.example.app.BottlesActivity;
import com.example.app.MainActivity;
import com.example.app.R;
import com.example.app.database.pojo.Bottle;
import com.example.app.database.sqlite.BottleTableHandler;
import com.example.app.database.sqlite.PatientTableHandler;

import java.util.List;

import static com.example.app.database.sqlite.BottleTableHandler.selectedBottle;



public class BottlesRecyclerViewAdapter extends RecyclerView.Adapter<BottlesRecyclerViewAdapter.ViewHolder> {

    private final List<Bottle> mValues;
    private FragmentManager fragmentManager;
    private BottlesActivity activity;

    public BottlesRecyclerViewAdapter(List<Bottle> items, FragmentManager fragmentManager, BottlesActivity activity) {
        mValues = items;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_bottle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mContentViewBottleID.setText(holder.mItem.getBottleID());
        holder.mContentViewName.setText(holder.mItem.getName());
        holder.mContentViewVolume.setText(Integer.toString(holder.mItem.getVolume()));
        holder.mContentViewQuantity.setText(Integer.toString(holder.mItem.getQuantity()));
        if(selectedBottle != null && holder.mItem.getID() == selectedBottle.getID()){
            holder.mButtonSelect.setText(R.string.button_selected);
            holder.mButtonSelect.setTextColor(ContextCompat.getColor(activity.getBaseContext(),
                    R.color.colorButtonSelected));
        }

        holder.mButtonSelect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                BottleTableHandler.selectedBottle = mValues.get(position);

                // open main view
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                view.getContext().startActivity(intent);

            }
        });

        holder.mButtonDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(selectedBottle != null && selectedBottle.getID() == mValues.get(position).getID()){
                    Toast.makeText(activity.getApplicationContext(),R.string.edit_delete_alert_bottle, Toast.LENGTH_SHORT).show();
                }
                else{
                    BottleTableHandler bottleTableHandler = new BottleTableHandler(activity.getBaseContext());
                    int exit = bottleTableHandler.deleteBottle(mValues.get(position).getID());
                    activity.initRecyclerView(bottleTableHandler.getBottles());

                    if(exit == 1) //success
                        Toast.makeText(activity.getApplicationContext(),"Bottle has been deleted!", Toast.LENGTH_SHORT).show();
                    else if(exit == 0) //error
                        Toast.makeText(activity.getApplicationContext(),"Error occurred while deleting a bottle!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.mButtonEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(selectedBottle != null && selectedBottle.getID() == mValues.get(position).getID()){
                    Toast.makeText(activity.getApplicationContext(),R.string.edit_delete_alert_bottle, Toast.LENGTH_SHORT).show();
                }
                else {
                    // set Bottle ID to input
                    EditText editText_bottleID = (EditText) activity.findViewById(R.id.editText_bottleID);
                    editText_bottleID.setText(mValues.get(position).getBottleID());
                    // set Bottle Name to input
                    EditText editText_bottleName = (EditText) activity.findViewById(R.id.editText_bottleName);
                    editText_bottleName.setText(mValues.get(position).getName());
                    // set Volume to input
                    EditText editText_bottleVolume = (EditText) activity.findViewById(R.id.editText_bottleVolume);
                    editText_bottleVolume.setText(Integer.toString(mValues.get(position).getVolume()));
                    // set Quantity to input
                    EditText editText_BottleQuantity = (EditText) activity.findViewById(R.id.editText_BottleQuantity);
                    editText_BottleQuantity.setText(Integer.toString(mValues.get(position).getQuantity()));

                    // scroll to the bottom of views
                    NestedScrollView scrollView = (NestedScrollView) activity.findViewById(R.id.nestedScrollView_Bottles);
                    //scrollView.smoothScrollTo(0, scrollView.getBottom());
                    scrollView.fullScroll(View.FOCUS_DOWN);

                    // set to edit mode
                    activity.editBottleID = mValues.get(position).getID();
                    Button cancelButton = (Button) activity.findViewById(R.id.button_cancelEditingBottle);
                    cancelButton.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentViewBottleID;
        public final TextView mContentViewName;
        public final TextView mContentViewVolume;
        public final TextView mContentViewQuantity;
        public final Button mButtonSelect;
        public final Button mButtonEdit;
        public final Button mButtonDelete;
        public Bottle mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentViewBottleID = (TextView) view.findViewById(R.id.textView_bottleID);
            mContentViewName = (TextView) view.findViewById(R.id.textView_bottleName);
            mContentViewVolume = (TextView) view.findViewById(R.id.textView_bottleVolume);
            mContentViewQuantity = (TextView) view.findViewById(R.id.textView_bottleQuantity);
            mButtonSelect = (Button) view.findViewById(R.id.button_select_bottle);
            mButtonEdit = (Button) view.findViewById(R.id.button_edit_bottle);
            mButtonDelete = (Button) view.findViewById(R.id.button_delete_bottle);
        }


        @Override
        public String toString() {
            return super.toString() + " '" + mContentViewBottleID.getText() + "'";
        }

    }
}
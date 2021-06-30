package com.example.app.adapters;


import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.app.NetworksActivity;
import com.example.app.NetworkActivity;
import com.example.app.R;
import com.example.app.util.WifiHandler;

import java.util.List;

/**
 * Class is user for displaying a list of resources. If resource is clicked, activity is changed to
 * ResourceInformationActivity ind resource information is shown.
 */
public class NetworksRecyclerViewAdapter extends RecyclerView.Adapter<NetworksRecyclerViewAdapter.ViewHolder> {

    private final List<ScanResult> mValues;
    private FragmentManager fragmentManager;
    private NetworksActivity activity;

    public NetworksRecyclerViewAdapter(List<ScanResult> items, FragmentManager fragmentManager, NetworksActivity activity) {
        mValues = items;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_network, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mContentViewName.setText(holder.mItem.SSID);
        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                WifiHandler.selectedNetwork = mValues.get(position);

                // open main view
                Intent intent = new Intent(view.getContext(), NetworkActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                view.getContext().startActivity(intent);

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
        public ScanResult mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentViewName = (TextView) view.findViewById(R.id.textView_network);
        }


        @Override
        public String toString() {
            return super.toString() + " '" + mContentViewName.getText() + "'";
        }

    }
}
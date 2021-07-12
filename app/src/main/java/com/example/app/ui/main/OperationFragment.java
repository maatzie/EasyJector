package com.example.app.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.app.BottlesActivity;
import com.example.app.ConnectionActivity;

import com.example.app.PatientsActivity;
import com.example.app.R;
import com.example.app.database.sqlite.BottleTableHandler;
import com.example.app.database.sqlite.InjectionTableHandler;
import com.example.app.database.sqlite.PatientTableHandler;
import com.example.app.util.ConnectionHandler;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class OperationFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";


    private TextView mCounterTextView;

    private Timer mTimer;
    private MyTimerTask mMyTimerTask;
    private Date startTime;

    Button patientButton;
    Button bottleButton;

    Button startButton;
    Button stopButton;


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

    }


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_operation, container, false);

        patientButton = (Button) root.findViewById(R.id.button_patient);
        bottleButton = (Button) root.findViewById(R.id.button_bottle);

        startButton = (Button) root.findViewById(R.id.button_start);
        stopButton = (Button) root.findViewById(R.id.button_stop);

        setButtonOnClickListener(patientButton, new PatientsActivity());
        setButtonOnClickListener(bottleButton, new BottlesActivity());
        setButtonOnClickListener((Button) root.findViewById(R.id.button_connection), new ConnectionActivity());


        setStartButtonOnClickListener(startButton);
        setStopButtonOnClickListener(stopButton);


        // connectionHandler needs to be initialized
        ConnectionHandler connectionHandler = new ConnectionHandler();

        PatientTableHandler patientTableHandler = new PatientTableHandler(root.getContext());
        if(patientTableHandler.getSelectedPatient() != null){
            String text = patientTableHandler.getSelectedPatient().toString() +  getString(R.string.button_text_selected);
            patientButton.setText(text);
        }

        BottleTableHandler bottleTableHandler = new BottleTableHandler(root.getContext());
        if(bottleTableHandler.getSelectedBottle() != null){
            String text = bottleTableHandler.getSelectedBottle().toString() +  getString(R.string.button_text_selected);
            bottleButton.setText(text);
        }

        if(bottleTableHandler.getSelectedBottle() != null && patientTableHandler.getSelectedPatient() != null
        && ConnectionHandler.isConnectionEstablished){
            TextView alertText = (TextView) root.findViewById(R.id.text_alert);
            alertText.setVisibility(View.INVISIBLE);

            LinearLayout layout = (LinearLayout) root.findViewById(R.id.layout_Injection);
            layout.setVisibility(View.VISIBLE);
        }

        mCounterTextView = (TextView) root.findViewById(R.id.textView_timer);

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

    private void setStartButtonOnClickListener(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectionHandler handler = new ConnectionHandler();
                InjectionTableHandler injectionTableHandler = new InjectionTableHandler(getContext());
                if(handler.startInjection()){
                    startTimer();
                    long currentID = injectionTableHandler.addInjection(PatientTableHandler.selectedPatient.getID(),
                            BottleTableHandler.selectedBottle.getID(),
                            ConnectionHandler.deviceName);
                    ConnectionHandler.currentID = currentID;

                    patientButton.setEnabled(false);
                    bottleButton.setEnabled(false);
                }
                else{
                    Toast.makeText(getContext(),"Error occurred while starting injection!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void setStopButtonOnClickListener(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectionHandler handler = new ConnectionHandler();
                InjectionTableHandler injectionTableHandler = new InjectionTableHandler(getContext());
                if(handler.stopInjection()){
                    stopTimer();
                    injectionTableHandler.updateStopTime((int)ConnectionHandler.currentID);
                    ConnectionHandler.currentID = -1;

                    patientButton.setEnabled(true);
                    bottleButton.setEnabled(true);
                }
                else{
                    Toast.makeText(getContext(),"Error occurred while stopping injection!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void startTimer(){
        if (mTimer != null) {
            mTimer.cancel();
        }

        // re-schedule timer here
        // otherwise, IllegalStateException of
        // "TimerTask is scheduled already"
        // will be thrown
        Calendar calendar = Calendar.getInstance();
        startTime = calendar.getTime();

        mTimer = new Timer();
        mMyTimerTask = new MyTimerTask();

        mTimer.schedule(mMyTimerTask, 1000, 1000);
    }

    private void stopTimer(){
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    "HH:mm:ss", Locale.getDefault());

            long diff = calendar.getTime().getTime() - startTime.getTime();


            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;

            calendar.set(0, 0, 0, (int)diffHours, (int)diffMinutes, (int)diffSeconds);
            final String strDate = simpleDateFormat.format(calendar.getTime());

            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    mCounterTextView.setText(strDate);
                }
            });
        }
    }
}
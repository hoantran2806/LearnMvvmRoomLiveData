package com.example.androidlocalboundservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "HoanNT9";

    BoundService myServices;
    boolean isServicesStarted = false;
    private TextView mytextView;
    private Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, BoundService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
        mytextView = findViewById(R.id.textView);
        myButton = findViewById(R.id.bindServices);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTime();
            }
        });
    }

    public void showTime() {
        String text = myServices.getCurrentTime();
        mytextView.setText(text);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected: ");
            BoundService.LocalBinder binder = (BoundService.LocalBinder) service;
            myServices = binder.getServices();
            isServicesStarted = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected: ");
            isServicesStarted = false;
        }
    };
}

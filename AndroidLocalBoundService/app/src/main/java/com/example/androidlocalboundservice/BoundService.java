package com.example.androidlocalboundservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BoundService extends Service {

    private final IBinder binder = new LocalBinder();

    public BoundService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss MM/dd/yyyy", Locale.US);
        return simpleDateFormat.format(new Date());
    }

    public class LocalBinder extends Binder {
        BoundService getServices() {
            return BoundService.this;
        }
    }
}

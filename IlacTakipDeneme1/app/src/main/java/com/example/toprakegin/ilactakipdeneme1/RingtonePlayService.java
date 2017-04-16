package com.example.toprakegin.ilactakipdeneme1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;

public class RingtonePlayService extends Service {

    MediaPlayer mediaPlayer;
    boolean isRunning;
    int startId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        String state = intent.getExtras().getString("extra");

        Log.e("Ringtone key dondu?", state);

        assert state != null;
        switch (state){
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }

        if (!this.isRunning && startId == 1){                                                       // Müziği başlat
            mediaPlayer= MediaPlayer.create(this, R.raw.emergency_alert);
            mediaPlayer.start();

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            Intent intent_main_activity = new Intent(this.getApplicationContext(), MainActivity.class);
            PendingIntent pending_intent_main = PendingIntent.getActivity(this, 0, intent_main_activity, 0);

            Notification notification_popup = new Notification.Builder(this).setContentTitle("İlaç Alma Zamanı!")
                    .setContentText("Alarmı durdurmak için tıklayın")
                    .setContentIntent(pending_intent_main)
                    .setSmallIcon(android.R.drawable.ic_dialog_alert)
                    .setAutoCancel(true)
                    .build();
            notificationManager.notify(0, notification_popup);

            this.isRunning = true;
            this.startId = 0;
        }
        else if (this.isRunning && startId == 0) {                                                  // Devam eden müziği durdur
            mediaPlayer.stop();
            mediaPlayer.reset();

            this.isRunning = false;
            this.startId = 0;
        }
        else if (!this.isRunning && startId == 0){                                                  // Müzik yokken durdur !!!
            this.isRunning = false;
            this.startId = 0;
        }
        else if (this.isRunning && startId == 1){                                                   // Müzik varken başlat !!!
            this.isRunning = true;
            this.startId = 1;
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // Tell the user we stopped.
        Toast.makeText(this, "onDestroyed Called", Toast.LENGTH_SHORT).show();
    }
}

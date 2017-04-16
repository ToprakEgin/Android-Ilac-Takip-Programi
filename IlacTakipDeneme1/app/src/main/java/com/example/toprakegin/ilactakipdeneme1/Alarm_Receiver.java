package com.example.toprakegin.ilactakipdeneme1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

public class Alarm_Receiver extends BroadcastReceiver{
    String get_your_string;

    @Override
    public void onReceive(Context context, Intent intent){
        Log.e("We are in the receiver.", "Yay!");

        get_your_string = intent.getExtras().getString("extra");


        Log.e("Hangi Key Dondu?", get_your_string);

        Intent service_Intent = new Intent(context, RingtonePlayService.class);
        service_Intent.putExtra("extra", get_your_string);                      //IlacEklemeden gelen intent burada takılı kalmasın diye RingtoneService e paslıyoruz

        context.startService(service_Intent);
    }
}

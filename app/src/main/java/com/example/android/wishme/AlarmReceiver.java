package com.example.android.wishme;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "com.example.android.wishme.channelId";

    @Override
    @TargetApi(26)
    public void onReceive(Context context, Intent intent) {
       // Log.v("kn","yearmonthday alaramreceiver");

        Intent notificationIntent = new Intent(context, NotificationActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(NotificationActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context);

        Notification notification = builder.setContentTitle("WISH ME")
                .setContentText("New Notification")
                .setTicker("SOMEONE'S WAITING TO BE WISHED!!")
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentIntent(pendingIntent).build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "NotificationDemo",
                    IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notification);
       // Log.v("kn","yearmonthday alaramreceiver last 1");
    }

}

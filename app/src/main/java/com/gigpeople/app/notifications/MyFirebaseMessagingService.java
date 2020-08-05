package com.gigpeople.app.notifications;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.gigpeople.app.MainActivity;
import com.gigpeople.app.R;
import com.gigpeople.app.utils.PrefConnect;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;


/**
 * Created by KARUPPUSAMY on 5/1/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final int MESSAGE_NOTIFICATION_ID = 435340;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {
            //handle the data message here
        }
        //getting the title and the
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");
        String status = remoteMessage.getData().get("status");
        Log.e("FCM Push Notification","\nTitle: "+title+"\nMessage: "+body+""+"\nStatus: "+status);
        sendNotification(body,title,status);

    }

    private void sendNotification(String messageBody,String title,String status) {

        PendingIntent pendingIntent = null;

        Intent intent = null;
        String page="0",ids="";

        if (status.contains("-")){
            String TEMP[]=status.split("-");
            status=TEMP[0];
            ids=TEMP[1];

            if (status.equals("2")) {
                PrefConnect.writeString(getApplicationContext(), PrefConnect.FCM_GIG_ID, ids);
                PrefConnect.writeString(getApplicationContext(), PrefConnect.FCM_GIG_STATUS, TEMP[2]);

            }else if (status.equals("4")) {
                PrefConnect.writeString(getApplicationContext(), PrefConnect.FCM_ORDER_ID, ids);
                PrefConnect.writeString(getApplicationContext(), PrefConnect.FCM_ORDER_STATUS, TEMP[2]);

            }else if (status.equals("7")) {
                PrefConnect.writeString(getApplicationContext(), PrefConnect.FCM_SALES_ID, ids);
                PrefConnect.writeString(getApplicationContext(), PrefConnect.FCM_SALES_STATUS, TEMP[2]);

            }
        }else {
            PrefConnect.writeString(getApplicationContext(),PrefConnect.FCM_GIG_ID,"");
            PrefConnect.writeString(getApplicationContext(),PrefConnect.FCM_GIG_STATUS,"");
            PrefConnect.writeString(getApplicationContext(), PrefConnect.FCM_ORDER_ID, "");
            PrefConnect.writeString(getApplicationContext(), PrefConnect.FCM_ORDER_STATUS,"");
            PrefConnect.writeString(getApplicationContext(), PrefConnect.FCM_SALES_ID, "");
            PrefConnect.writeString(getApplicationContext(), PrefConnect.FCM_SALES_STATUS, "");
        }

        switch (status){
            case "0"://Gig Request
                page="0";
                break;
            case "1"://Gig Request
                page="1";
                break;
            case "2"://My Gig Fragment
                page="2";
                break;
            case "3"://Manage Request
                page="4";
                break;
            case "4"://My Orders
                page="5";
                break;
            case "5"://Favourite
                page="6";
                break;
            case "6"://Chat
                page="11";
                break;
            case "7"://My sale
                page="3";
                break;
        }
        intent = new Intent(this, MainActivity.class);
        intent.putExtra("page", page);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        pendingIntent = PendingIntent.getActivity(this, 0  /*Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Log.e("PAGE FCM",page+" nandhini");

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher_logo)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0  /*ID of notification*/ , notificationBuilder.build());

        /*intent = new Intent(this, MainActivity.class);
        intent.putExtra("page", page);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_logo)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(alarmSound)
                .setContentIntent(pendingIntent);

        notificationBuilder.setSound(alarmSound);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());*/
    }



    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }


}

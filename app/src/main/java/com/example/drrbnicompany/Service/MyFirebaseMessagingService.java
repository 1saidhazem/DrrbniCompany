package com.example.drrbnicompany.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.drrbnicompany.Fragments.BottomNavigationScreens.NotificationsFragment;
import com.example.drrbnicompany.R;
import com.example.drrbnicompany.ViewModels.Repository;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        String senderId = message.getData().get("senderUid");
        String title = message.getData().get("title");
        String body = message.getData().get("body");
        String adsId = message.getData().get("adsId");

        Repository repository = new Repository(getApplication());
        repository.storeNotification(senderId,title,body,adsId);

        showNotification(title , body );

    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        Repository repository = new Repository(getApplication());
        repository.updateToken(token);

    }

    private void showNotification(String title , String body ){

        //اي دي التشينل اللى بتظهر في الاعدادات
        String channelId = "App Channel";
        //بيلدر ديزاين باترن يرجع الاوبجكت بعد التعديل عليه
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this , channelId);

        //نستعمل pendingIntent بدلا من الانتنت لانها تعمل في تطبيق اخر
        Intent intent = new Intent(this , NotificationsFragment.class);
        PendingIntent pendingIntent =PendingIntent.getActivity(this , 0 , intent ,0);

        builder.setOngoing(false)
                .setVibrate(new long[]{100 , 200 , 100 , 200})
                .setOnlyAlertOnce(true)
                .setContentText(body)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentIntent(pendingIntent);
        // .setContent(التصميم الخاص بنا)

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){

            NotificationChannel channel =
                    new NotificationChannel(channelId ,"الأشعارات" , NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0 , builder.build());
    }

}
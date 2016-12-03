package cz.muni.fi.pv256.movio2.a448273.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import cz.muni.fi.pv256.movio2.a448273.Activities.MainActivity;
import cz.muni.fi.pv256.movio2.a448273.R;

/**
 * Created by gasior on 29.11.2016.
 */

public class NotificationHelper {

    private Context context;
    private NotificationManager notificationManager;

    public NotificationHelper(Context context, NotificationManager notificationManager) {
        this.context = context;
        this.notificationManager = notificationManager;
    }

    public NotificationCompat.Builder getMainActivityNotificationBuilder(String message) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);

        return new NotificationCompat.Builder(context)
                .setContentTitle("Movio")
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true);
    }
    public void showNotification(int id, NotificationCompat.Builder builder) {
        notificationManager.notify(id, builder.build());
    }

    public void cancelNotification(int id) {
        notificationManager.cancel(id);
    }
    public void showNotification(int id, String message) {
        showNotification(id, getMainActivityNotificationBuilder(message));
    }
    public void showNotification(int id, String message, boolean strong) {
        showNotification(id, getMainActivityNotificationBuilder(message), strong);
    }

    public void showNotification(int id, NotificationCompat.Builder builder, boolean strong) {
        if (strong) {
            builder.setDefaults(Notification.DEFAULT_ALL);
        }
        showNotification(id, builder);
    }

}

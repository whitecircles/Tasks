package by.home.white.tasks;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import java.util.Calendar;
import java.util.List;

public class NotificationForBtn {

    private static final String CHANNEL_ID = "101";

    public void makeNotification (Context context, Note note)
    {
        //-------------notification


        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        //if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My channel",
                NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("My channel description");
        channel.enableLights(true);
        //channel.setLightColor(Color.RED);
        channel.enableVibration(false);
        notificationManager.createNotificationChannel(channel);


        /*Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, note.getPendingDate().getYear());
        calendar.set(Calendar.MONTH, note.getPendingDate().getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, note.getPendingDate().getDay());
        calendar.set(Calendar.HOUR_OF_DAY, note.getPendingDate().getHours());
        calendar.set(Calendar.MINUTE, note.getPendingDate().getMinutes());*/

        String task = note.getNote();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018,12,28,16,50,0);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("task")
                        .setContentText(task)
                        .setWhen(calendar.getTimeInMillis());

        notificationManager.notify(1, builder.build());
    }
}

package vishnu.rai.booknician;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class notificationHelper {

    public static void displayNotification(Context context, String title, String body){

        // Building notification builder

        // TO GET A ACTIVITY ON CLICKING NOTIFICATION WE USE PENDING INTENT
        // Creating a pending intent

        Intent intent = new Intent(context, Loginpage.class);

        PendingIntent pendingIntent =  PendingIntent.getActivity(
                context,              // first parameter
                100,         // 2nd parameter is request code which is use to update the intent or modify it and we can pass any number
                intent,             // 3rd parameter is the intent which we want to execute
                PendingIntent.FLAG_CANCEL_CURRENT       // If a pending intent is already created it will cancel current before creating new
        );




        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "NOTIFICATION").
                setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)     // It is use to only to add pending intent
                .setAutoCancel(true)                 // This method remove the notification once it is opened
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        // Creating notification manager

        NotificationManagerCompat notificationManagerCompat  = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(1, mBuilder.build());

        // Id is used to upadte notification

    }
}

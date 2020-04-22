package com.example.simplifiednotification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.simplifiednotification.MainActivity.CHANNEL_ID;

public class NotificatonHelper {

    public static void displayNotification(Context context, String title, String  body) {
        Intent intent = new Intent( context,Main2Activity.class );
        PendingIntent intent1 = PendingIntent.getActivity( 
                context,100, intent, PendingIntent.FLAG_CANCEL_CURRENT );
        
        
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder( context, CHANNEL_ID )
                        .setSmallIcon( R.drawable.ic_notifications_black_24dp )
                        .setContentTitle( title )
                        .setContentText( body )
                        .setAutoCancel( true )
                        .setPriority( NotificationCompat.PRIORITY_DEFAULT );
        
        NotificationManagerCompat compat = NotificationManagerCompat.from( context );
        compat.notify( 1, builder.build() );
    }

}

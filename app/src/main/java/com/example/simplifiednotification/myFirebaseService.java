package com.example.simplifiednotification;
import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class myFirebaseService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived( remoteMessage );
        
        if (remoteMessage.getNotification() != null){
            String title = remoteMessage.getNotification().getTitle();
            String text = remoteMessage.getNotification().getBody();
            
            NotificatonHelper.displayNotification( getApplicationContext(), title, text );
        }
        
    }
}

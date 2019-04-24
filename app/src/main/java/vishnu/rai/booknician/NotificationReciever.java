package vishnu.rai.booknician;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationReciever extends FirebaseMessagingService {// extending this class it will receive incoming messages



    // It's a new class which will be service to get notification

    @Override                            // Overiding onMessageReceived Method. this method is called when a message is received
    public void onMessageReceived(RemoteMessage remoteMessage) {      // remoteMessage is used to get message
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getNotification() != null)
        {
            String title = remoteMessage.getNotification().getTitle();
            String  body = remoteMessage.getNotification().getBody();

            notificationHelper.displayNotification(getApplicationContext(),title,body);
        }
    }
}

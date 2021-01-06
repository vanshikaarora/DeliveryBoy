package vanshika.android.com.deliveryboy.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import com.google.firebase.messaging.RemoteMessage;
import vanshika.android.com.deliveryboy.MainActivity;
import vanshika.android.com.deliveryboy.R;

public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
  @Override public void onMessageReceived(RemoteMessage remoteMessage) {
    super.onMessageReceived(remoteMessage);
    showNotification(remoteMessage.getData().get("message"));
  }

  private void showNotification(String message) {

    Intent i = new Intent(this,MainActivity.class);
    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

    PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
        .setAutoCancel(true)
        .setContentTitle("New order")
        .setContentText(message)
        .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
        .setContentIntent(pendingIntent)
        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        .setPriority(NotificationManager.IMPORTANCE_HIGH);


    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

    manager.notify(0,builder.build());
  }

}

package vanshika.android.com.deliveryboy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import vanshika.android.com.deliveryboy.OrderDetails;
import vanshika.android.com.deliveryboy.R;

public class ChildEventListener extends Service {
  @Nullable @Override public IBinder onBind(Intent intent) {
    return null;
  }//yeh waala //deskh ra hu ruko to

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    final FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref=database.getReference();
    ref.child("orders").addValueEventListener(new ValueEventListener() {
      @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        for (DataSnapshot singleSnapshot:dataSnapshot.getChildren()){
          if (singleSnapshot != null) {
            OrderDetails newOrder = dataSnapshot.getValue(OrderDetails.class);
            if (newOrder != null) {
              Notification notification=new Notification.Builder(getApplicationContext())
                  .setContentTitle("New Order")
                  .setContentText("Restaurant pick up at "+newOrder.getRestaurant())
                  .setSmallIcon(R.drawable.ic_launcher_background)
                  .setPriority(Notification.PRIORITY_HIGH)
                  .setAutoCancel(true)
                  .build();
              notification.contentIntent= PendingIntent.getActivity(getApplicationContext(),0,new Intent(getApplicationContext(),MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
              notification.flags |= Notification.FLAG_ONLY_ALERT_ONCE | Notification.FLAG_SHOW_LIGHTS;
              NotificationManager notificationManager = (NotificationManager)
                  getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
              notificationManager.notify(1, notification);
              //FirebaseMessaging.getInstance().subscribeToTopic("test");
              //adapter.notifyDataSetChanged();
            }
          }
        }
      }

      @Override public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
    //return super.onStartCommand(intent, flags, startId);
    return START_STICKY;
  }
  @Override
  public void onDestroy() {
    super.onDestroy();

  }
}

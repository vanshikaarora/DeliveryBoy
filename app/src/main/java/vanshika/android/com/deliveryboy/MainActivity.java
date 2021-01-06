package vanshika.android.com.deliveryboy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    //implements NavigationView.OnNavigationItemSelectedListener {
  String orderId;
  RecyclerView recyclerView;
  RecyclerView.LayoutManager mLayoutManager;
  FirebaseRecyclerAdapter adapter;
  private ProgressBar spinner;
  FirebaseRecyclerOptions <OrderDetails> list;
  int childCount; String setId;
  private LinearLayout llProgress;


  @Override
  protected void onStart() {
    super.onStart();
    adapter.startListening();
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    //spinner = (ProgressBar)findViewById(R.id.progressBar1);
    //spinner.setVisibility(View.VISIBLE);

    llProgress = (LinearLayout) findViewById(R.id.llProgress);
    showProgress("Loading...");

    //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    //fab.setOnClickListener(new View.OnClickListener() {
    //  @Override
    //  public void onClick(View view) {
    //    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
    //        .setAction("Action", null).show();
    //  }
    //
    //});

    startService(new Intent(getBaseContext(), vanshika.android.com.deliveryboy.ChildEventListener.class));

    //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
    //    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    //drawer.addDrawerListener(toggle);
    //toggle.syncState();

    //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    //navigationView.setNavigationItemSelectedListener(this);



    final FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref=database.getReference();
    DatabaseReference dRef=ref.child("orders");



    DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
    Query query=reference.child("orders");

    //DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
    Query query1=reference.child("orders");
    query1.addValueEventListener(new ValueEventListener() {
      @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        childCount= (int) dataSnapshot.getChildrenCount();
        for (DataSnapshot singleSnapshot:dataSnapshot.getChildren()){
          if (singleSnapshot!=null){
            Log.v("mainactivity",singleSnapshot.getValue().toString());
            if (singleSnapshot.getChildrenCount()>=7){
              OrderDetails newOrder =  singleSnapshot.getValue(OrderDetails.class);
              if (newOrder!=null){
                newOrder.setOrderid(singleSnapshot.getKey());
                setId=singleSnapshot.getKey();
                orderId=singleSnapshot.getKey();
                adapter.notifyDataSetChanged();
              }
            }

            //if (singleSnapshot.hasChild("Delivered")){
            //  listViewHolder holder=new listViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.card_view,null,false));
            //  holder.button.setText("Delivered");
            //
            //}else
            if (singleSnapshot.hasChild("Picked up")){
              listViewHolder holder=new listViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.card_view,null,false));
              holder.button.setText("Delivered");

            }else
            if (singleSnapshot.hasChild("restaurantConfirmation")){
              listViewHolder holder=new listViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.card_view,null,false));
              holder.button.setText("Picked up");

            }else
            if (singleSnapshot.hasChild("foodHauntConfirmation")){
              listViewHolder holder=new listViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.card_view,null,false));
              holder.button.setText("Order confirmed by Restaurant");

            }/*else
            {
              listViewHolder holder=new listViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.card_view,null,false));
              holder.button.setText("Order confirmed by Food Haunt");

            }*/
          }
        }//spinner.setVisibility(View.GONE);
        ((TextView) llProgress.findViewById(R.id.tvMessage)).setText("");
        llProgress.setVisibility(View.GONE);
      }

      @Override public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });

    query.addChildEventListener(new ChildEventListener() {
      @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
         for (DataSnapshot singleSnapshot:dataSnapshot.getChildren()){
          if (singleSnapshot != null) {
            OrderDetails newOrder = dataSnapshot.getValue(OrderDetails.class);
            if (newOrder != null) {
              Notification notification=new Notification.Builder(getApplicationContext())
                  .setContentTitle("New Order")
                  .setContentText("Restaurant pick up at"+newOrder.getRestaurant())
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
              adapter.notifyDataSetChanged();
            }
        }
       }
      }


      @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {

      }

      @Override public void onChildRemoved(DataSnapshot dataSnapshot) {

      }

      @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {

      }

      @Override public void onCancelled(DatabaseError databaseError) {

      }
    });
     list=new FirebaseRecyclerOptions.Builder<OrderDetails>()
        .setQuery(query,OrderDetails.class)
        .build();

    recyclerView=findViewById(R.id.recyclerView);
    mLayoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    adapter = new FirebaseRecyclerAdapter<OrderDetails,listViewHolder>(list) {
      @Override
      protected void onBindViewHolder(@NonNull final listViewHolder holder, int position,
          @NonNull final OrderDetails model) {
        holder.tId.setText(model.getOrderid());
        holder.tName.setText(model.getName());
        holder.tEmail.setText(model.getEmail());
        holder.tMobile.setText(model.getMobile());
        holder.tAddress.setText(model.getAddress());
        holder.tRestaurant.setText(model.getRestaurant());
        holder.tSummary.setText(model.getSummary());
        holder.button.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View view) {
            final FirebaseDatabase database=FirebaseDatabase.getInstance();//todo:orderid
            DatabaseReference ref=database.getReference();//HAve you made this reference already in the database?
            DatabaseReference dRef=ref.child("orders");
            if (holder.button.getText().toString().equals("Order Confirmed by Food Haunt")){
              holder.button.setText("Order confirmed by Restaurant");
//<<<<<<< HEAD
              dRef.child(model.getOrderid()).child("foodHauntConfirmation").setValue("successful");
            }
            else if (holder.button.getText().toString().equals("Order confirmed by Restaurant")){
              holder.button.setText("Picked up");
              dRef.child(model.getOrderid()).child("restaurantConfirmation").setValue("successful");
            }
            else if (holder.button.getText().toString().equals("Picked up")){
              holder.button.setText("Delivered");
              dRef.child(model.getOrderid()).child("pickup").setValue("successful");
            }
            else if (holder.button.getText().toString().equals("Delivered")){
              dRef.child(model.getOrderid()).child("delivery").setValue("successful");
              Toast.makeText(MainActivity.this,"Successfully delivered",Toast.LENGTH_SHORT).show();
              //ToDO:remove the cardView and delete from firebase and change child value from 4 to orderId
              //holder.button.setText("Order Confirmed by Food Haunt");
              holder.cardViewObject.removeView(view);
              //dRef.child(model.getOrderId()).child("foodHauntConfirmation").setValue("successful");

              dRef.child(model.getOrderid()).removeValue();
              adapter.notifyDataSetChanged();
//=======

            }
//            else if (holder.button.getText().toString().equals("Order confirmed by Restaurant")){
//              holder.button.setText("Picked up");
//              dRef.child(orderId).child("restaurantConfirmation").setValue("successful");
//            }
//            else if (holder.button.getText().toString().equals("Picked up")){
//              holder.button.setText("Delivered");
//              dRef.child(orderId).child("pickup").setValue("successful");
//            }
//            else if (holder.button.getText().toString().equals("Delivered")){
//              dRef.child(orderId).child("delivery").setValue("successful");
//              Toast.makeText(MainActivity.this,"Successfully delivered",Toast.LENGTH_SHORT).show();
//              //ToDO:remove the cardView and delete from firebase and change child value from 4 to orderId
//              holder.cardViewObject.removeView(view);
//              dRef.child(orderId).removeValue();
////>>>>>>> ac8305ce99b3f1c70b70ea02bb23e9faddeac71c
//            }
          }
        });

        holder.cardViewObject.setOnClickListener(new View.OnClickListener() {

          @Override public void onClick(View view) {
            Intent intent=new Intent(MainActivity.this,customer.class);
            intent.putExtra("orderid",model.getOrderid());
            intent.putExtra("name",model.getName());
            intent.putExtra("mobile",model.getMobile());
            intent.putExtra("address",model.getAddress());
            intent.putExtra("email",model.getEmail());
            intent.putExtra("restaurant",model.getRestaurant());
            intent.putExtra("summary",model.getSummary());
            startActivity(intent);
          }
        });
      }


      @NonNull @Override
      public listViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        listViewHolder holder =
            new listViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.card_view, parent, false));
        return holder;
      }

      @Override public int getItemCount() {
        //return super.getItemCount();
        return childCount;
      }
    };
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(mLayoutManager);


  }



  private void showProgress(String s) {
    ((TextView) llProgress.findViewById(R.id.tvMessage)).setText(s);
    llProgress.setVisibility(View.VISIBLE);
  }

  public class listViewHolder extends RecyclerView.ViewHolder{
    TextView tId,tEmail,tName,tMobile,tAddress,tRestaurant,tSummary,button;
    CardView cardViewObject;
    public listViewHolder(View itemView) {
      super(itemView);
      tId=itemView.findViewById(R.id.orderid);
      tName=itemView.findViewById(R.id.name);
      tEmail=itemView.findViewById(R.id.email);
      tMobile=itemView.findViewById(R.id.mobile);
      tAddress=itemView.findViewById(R.id.address);
      tRestaurant=itemView.findViewById(R.id.restaurant);
      tSummary=itemView.findViewById(R.id.summary);
      cardViewObject=itemView.findViewById(R.id.cardView);
      button=itemView.findViewById(R.id.textButton);

    }
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }

  }

  //@Override
  //public boolean onCreateOptionsMenu(Menu menu) {
  //  // Inflate the menu; this adds items to the action bar if it is present.
  //  getMenuInflater().inflate(R.menu.main, menu);
  //  return true;
  //}
  //
  //@Override
  //public boolean onOptionsItemSelected(MenuItem item) {
  //  // Handle action bar item clicks here. The action bar will
  //  // automatically handle clicks on the Home/Up button, so long
  //  // as you specify a parent activity in AndroidManifest.xml.
  //  int id = item.getItemId();
  //
  //  //noinspection SimplifiableIfStatement
  //  if (id == R.id.action_settings) {
  //    return true;
  //  }
  //
  //  return super.onOptionsItemSelected(item);
  //}


}

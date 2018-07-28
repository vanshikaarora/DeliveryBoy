package vanshika.android.com.deliveryboy;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class customer extends AppCompatActivity implements View.OnClickListener {
  private String orderId, name, mobile, address, mail, restaurant, summary;
  TextView tOrderid, tName, tMobile, tMail, tAddress, tRestaurant, tSummary;
  private static final int REQUEST_PHONE_CALL = 1;
  private RelativeLayout updateCustomerView;
  private CardView updateCustomerLayout;
  private ImageView arrow;
  private EditText setTime,msgText;
  private Button sendTime,sendNoti;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_customer);
    android.support.v7.widget.Toolbar toolbar =
        (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
    //toolbar.setTitle("Customer Info");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);

    arrow = findViewById(R.id.arrow);
    updateCustomerLayout = findViewById(R.id.updateCustomer);
    updateCustomerView = findViewById(R.id.updateCustomerText);
    updateCustomerView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (updateCustomerLayout.getVisibility() == View.VISIBLE) {
          updateCustomerLayout.setVisibility(View.GONE);
          arrow.setImageDrawable(ContextCompat.getDrawable(customer.this, R.drawable.downarrow));
        } else {
          updateCustomerLayout.setVisibility(View.VISIBLE);
          arrow.setImageDrawable(ContextCompat.getDrawable(customer.this, R.drawable.upwardsarrow));
        }
      }
    });

    setTime=findViewById(R.id.timeToGo);
    sendTime=findViewById(R.id.sendTime);
    sendTime.setOnClickListener(this);

    msgText=findViewById(R.id.customerMessage);
    sendNoti=findViewById(R.id.sendMsg);
    sendNoti.setOnClickListener(this);

    Intent intent = getIntent();
    orderId = intent.getStringExtra("orderid");
    name = intent.getStringExtra("name");
    mobile = intent.getStringExtra("mobile");
    address = intent.getStringExtra("address");
    mail = intent.getStringExtra("email");
    restaurant = intent.getStringExtra("restaurant");
    summary = intent.getStringExtra("summary");

    tOrderid = findViewById(R.id.cOrderid);
    tName = findViewById(R.id.cName);
    tMobile = findViewById(R.id.cPhone);
    tAddress = findViewById(R.id.cAddress);
    tMail = findViewById(R.id.cMail);
    tRestaurant = findViewById(R.id.cRestaurant);
    tSummary = findViewById(R.id.cSummary);

    tOrderid.setText(orderId);
    tName.setText(name);
    tMobile.setText(mobile);
    tAddress.setText(address);
    tMail.setText(mail);
    tRestaurant.setText(restaurant);
    tSummary.setText(summary);

    Button fab1 = findViewById(R.id.fab1);
    //FloatingActionButton fab2 = findViewById(R.id.fab2);
    fab1.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        call();
      }
    });
    //fab2.setOnClickListener(new View.OnClickListener() {
    //  @Override public void onClick(View view) {
    //    navigate();
    //  }
    //});
  }


  private void call() {
    Intent callIntent = new Intent(Intent.ACTION_CALL);
    callIntent.setData(Uri.parse("tel:"+mobile));
    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(customer.this, new String[]{android.Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
      return;
    }
    startActivity(callIntent);
  }
  //@Override
  //public void onRequestPermissionsResult(int requestCode,
  //    String permissions[], int[] grantResults) {
  //  switch (requestCode) {
  //    case REQUEST_PHONE_CALL: {
  //      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
  //        startActivity(intent);
  //      }
  //      else
  //      {
  //
  //      }
  //      return;
  //    }
  //  }
  //}

  @Override
  public boolean onSupportNavigateUp() {
    onBackPressed();
    return true;
  }

  @Override public void onClick(View view) {
    int id=view.getId();
    final FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref=database.getReference();//TODO:change child from to orderId
    DatabaseReference dRef=ref.child("orders");
    switch (id){
      case R.id.sendTime:dRef.child(orderId).child("time").setValue(setTime.getText().toString());
      setTime.setText(setTime.getText().toString());
      setTime.setEnabled(false);
        break;
      case R.id.sendMsg:dRef.child(orderId).child("message").setValue(msgText.getText().toString());
      msgText.setText(msgText.getText().toString());
      setTime.setEnabled(false);
        break;
    }

  }
}

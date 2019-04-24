package vishnu.rai.booknician;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class addressphone extends AppCompatActivity implements View.OnClickListener {

    TextView addnewaddress_tv;

    ImageView home_button, order_button, profile_button;

    Button confirmorder_btn;

    public static boolean newaddress = false;

    final FirebaseDatabase orderlist = FirebaseDatabase.getInstance();

    DatabaseReference order = orderlist.getReference();

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference myRef = database.getReference();

    DatabaseReference datewiseorder = orderlist.getReference();

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    RadioButton default_btn;

    public String book_image, book_name, datewiseorder_name, final_address, final_phone, numberofbookinstock;

    public int  numberofbookinstock_int;

    public Intent i;

    public String userid, order_date;

    public String email, subject, message,Bookcount;

    public String currentDateTimeString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addressphone);

        addnewaddress_tv = findViewById(R.id.addnewaddress_tv);

        home_button=findViewById(R.id.home_button);
        profile_button=findViewById(R.id.profile_button);
        order_button=findViewById(R.id.order_button);


        order_button.setOnClickListener(this);
        profile_button.setOnClickListener(this);
        home_button.setOnClickListener(this);


        default_btn = findViewById(R.id.default_btn);

        confirmorder_btn = findViewById(R.id.confirmorder_btn);

        confirmorder_btn.setOnClickListener(this);

        addnewaddress_tv.setOnClickListener(this);

        i = getIntent();

        book_name = i.getStringExtra("Book name");
        Bookcount = i.getStringExtra("Bookcount");


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.addnewaddress_tv:

                Intent intent = new Intent(addressphone.this, newaddressphone.class);
                intent.putExtra("Book name", Allbookshowpage.allbook_clicked_name);
                startActivity(intent);

                break;

            case R.id.confirmorder_btn:

                bookdeatil();

                numberofbookinstock_int= Integer.parseInt(Bookcount);
                numberofbookinstock_int--;

                FirebaseDatabase countdata= FirebaseDatabase.getInstance();
                DatabaseReference bookcountdata= countdata.getReference();

                bookcountdata.child("Books").child(book_name).child("Book In Stock").setValue(String.valueOf(numberofbookinstock_int));

                Toast.makeText(getApplicationContext(), "Order Placed", Toast.LENGTH_LONG).show();


                intent = new Intent(addressphone.this, home_page.class);
                startActivity(intent);

                break;


            case R.id.home_button:
                intent = new Intent(getApplicationContext(), home_page.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                break;


            case R.id.order_button:

                intent =  new Intent(getApplicationContext(), user_order_page.class);
                startActivity(intent);

                break;

            case R.id.profile_button:

                intent =  new Intent(getApplicationContext(), profile_page.class);
                startActivity(intent);

                break;
        }
    }

    private void sendEmail() {

        FirebaseDatabase emailData = FirebaseDatabase.getInstance();

        emailData.getReference("Users").child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                email= dataSnapshot.child("Email").getValue(String.class);

                subject = "ORDER PLACED";

                message = "Your book "+book_name+" is placed and you will receive it within 24 hours. " +
                        "For any queries contact:" +
                        "7578968856";

                //Creating SendMail object
                sendmail_java sm = new sendmail_java(addressphone.this, email, subject, message);

                //Executing sendmail to send email
                sm.execute();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void bookdeatil() {

        FirebaseDatabase book_deatil = FirebaseDatabase.getInstance();

        book_deatil.getReference("Books").child(book_name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                book_image = dataSnapshot.child("image").getValue(String.class);

                confirmorder();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void confirmorder() {

        userid = mAuth.getCurrentUser().getUid();

        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");


        Date c = Calendar.getInstance().getTime();

        order_date = df.format(c);

        currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        if (newaddress == false || default_btn.isChecked()) {

            FirebaseDatabase address_deatil = FirebaseDatabase.getInstance();

            address_deatil.getReference("Users").child(userid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    final_address = dataSnapshot.child("Address").getValue(String.class);
                    final_phone = dataSnapshot.child("Phone no").getValue(String.class);
                    datewiseorder_name = dataSnapshot.child("Name").getValue(String.class);

                    final String Date_child = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                    order = FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child("Orders").child(book_name + currentDateTimeString);

                    Map saveData_book_auth = new HashMap();
                    {

                        saveData_book_auth.put("order_date", order_date);
                        saveData_book_auth.put("admin_order_date", Date_child);
                        saveData_book_auth.put("time_date", currentDateTimeString);
                        saveData_book_auth.put("book_name", book_name);
                        saveData_book_auth.put("book_image", book_image);
                        saveData_book_auth.put("address", final_address);
                        saveData_book_auth.put("return_status", "false");
                        saveData_book_auth.put("phone_no", final_phone);

                    }
                    order.setValue(saveData_book_auth);


                    datewiseorder = FirebaseDatabase.getInstance().getReference().child("Datewiseorder").child(Date_child).child(userid + book_name + currentDateTimeString);

                    Map order_date_child = new HashMap();
                    {

                        order_date_child.put("Name", datewiseorder_name);
                        order_date_child.put("admin_order_date", Date_child);
                        order_date_child.put("order_date", order_date);
                        order_date_child.put("book_name", book_name);
                        order_date_child.put("book_image", book_image);
                        order_date_child.put("address", final_address);
                        order_date_child.put("return_status", "false");
                        order_date_child.put("phone_no", final_phone);

                    }
                    datewiseorder.setValue(order_date_child);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else {

            final_address = newaddressphone.newaddress_string;
            final_phone = newaddressphone.newphonenumber_string;

            final String Date_child = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            order = FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child("Orders").child(book_name + currentDateTimeString);

            Map saveData_book_auth = new HashMap();
            {

                saveData_book_auth.put("order_date", order_date);
                saveData_book_auth.put("time_date", currentDateTimeString);
                saveData_book_auth.put("admin_order_date", Date_child);
                saveData_book_auth.put("book_name", book_name);
                saveData_book_auth.put("return_status", "false");
                saveData_book_auth.put("book_image", book_image);
                saveData_book_auth.put("address", final_address);
                saveData_book_auth.put("phone_no", final_phone);


            }
            order.setValue(saveData_book_auth);


            FirebaseDatabase datewise_name = FirebaseDatabase.getInstance();

            datewise_name.getReference("Users").child(userid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    datewiseorder_name = dataSnapshot.child("Name").getValue(String.class);

                    datewiseorder = FirebaseDatabase.getInstance().getReference().child("Datewiseorder").child(Date_child).child(userid + book_name + currentDateTimeString);

                    Map order_date_child = new HashMap();
                    {

                        order_date_child.put("Name", datewiseorder_name);
                        order_date_child.put("order_date", order_date);
                        order_date_child.put("admin_order_date", Date_child);
                        order_date_child.put("return_status", "false");
                        order_date_child.put("book_name", book_name);
                        order_date_child.put("book_image", book_image);
                        order_date_child.put("address", final_address);
                        order_date_child.put("phone_no", final_phone);

                    }
                    datewiseorder.setValue(order_date_child);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });


        }

        //sendEmail();

    }
}


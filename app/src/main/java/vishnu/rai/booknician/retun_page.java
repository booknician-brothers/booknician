package vishnu.rai.booknician;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class retun_page extends AppCompatActivity implements View.OnClickListener {

    ImageView book_image_rp;

    TextView book_name_rp, order_date_rp, return_date_rp, day_count_rp, total_price_rp;

    Button return_btn_rp;

    public  String str_book_name_rp, str_order_date_rp, str_return_date_rp, str_day_count_rp, str_total_price_rp, str_book_image_rp;

    ImageView home_button, order_button, profile_button;

    Intent i;

    FirebaseAuth mAuth= FirebaseAuth.getInstance();

    public String User_key, Admin_key, Book_name, Fix_price, Daily_price, userid, return_status,return_date,order_date,admin_order_date;

    FirebaseDatabase database= FirebaseDatabase.getInstance();

    DatabaseReference rp_data = database.getReference();

    DatabaseReference order_admin,order;

    ProgressDialog progressdialog;

    public int numberOfDays,total_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retun_page);

        progressdialog = new ProgressDialog(retun_page.this);
        progressdialog.setMessage("Please Wait....");
        progressdialog.show();

        i = getIntent();

        Admin_key = i.getStringExtra("Admin_key");
        User_key = i.getStringExtra("User_key");
        Fix_price = i.getStringExtra("Fix_price");
        Daily_price = i.getStringExtra("Daily_price");
        Book_name = i.getStringExtra("Book_name");



        book_name_rp= findViewById(R.id.book_name_rp);
        book_image_rp= findViewById(R.id.book_image_rp);
        order_date_rp= findViewById(R.id.order_date_rp);
        return_date_rp= findViewById(R.id.return_date_rp);
        day_count_rp= findViewById(R.id.day_count_rp);
        total_price_rp= findViewById(R.id.total_price_rp);
        return_btn_rp= findViewById(R.id.return_btn_rp);


        home_button=findViewById(R.id.home_button);
        order_button=findViewById(R.id.order_button);
        profile_button=findViewById(R.id.profile_button);

        home_button.setOnClickListener(this);
        profile_button.setOnClickListener(this);
        order_button.setOnClickListener(this);

        userid= mAuth.getCurrentUser().getUid();


        return_date_rp.setText("NULL");
        total_price_rp.setText("NULL");


        rp_data.child("Users").child(userid).child("Orders").child(User_key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                str_book_name_rp= dataSnapshot.child("book_name").getValue(String.class);
                str_order_date_rp= dataSnapshot.child("order_date").getValue(String.class);
                str_book_image_rp= dataSnapshot.child("book_image").getValue(String.class);
                order_date= dataSnapshot.child("order_date").getValue(String.class);
                admin_order_date= dataSnapshot.child("admin_order_date").getValue(String.class);


                book_name_rp.setText(str_book_name_rp);
                order_date_rp.setText(str_order_date_rp);

                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

                Date c = Calendar.getInstance().getTime();

                String dateStr = str_order_date_rp;

                try {
                    Date date1 = df.parse(dateStr);
                    long diff =  c.getTime() - date1.getTime();
                    int numOfDays = (int) (diff / (1000 * 60 * 60 * 24));


                    day_count_rp.setText(String.valueOf(numOfDays));

                } catch (ParseException e) {
                    e.printStackTrace();
                }



                Picasso.with(getApplicationContext()).load(str_book_image_rp).into(book_image_rp, new Callback() {

                    @Override
                    public void onSuccess() {

                        FirebaseDatabase return_detail = FirebaseDatabase.getInstance();

                        final DatabaseReference return_data= return_detail.getReference();

                        return_data.child("Users").child(userid).child("Orders").child(User_key).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                return_status= dataSnapshot.child("return_status").getValue(String.class);

                                if(return_status.equalsIgnoreCase("false")) {
                                    return_btn_rp.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            return_process();
                                        }
                                    });
                                    return_btn_rp.setText("Return");
                                    progressdialog.dismiss();

                                }

                                else if(return_status.equalsIgnoreCase("true")){

                                    FirebaseDatabase return_placed_detail = FirebaseDatabase.getInstance();

                                    final DatabaseReference return_placed_data= return_placed_detail.getReference();

                                    return_placed_data.child("Users").child(userid).child("Orders").child(User_key).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            return_date_rp.setText(dataSnapshot.child("return_date").getValue(String.class));
                                            day_count_rp.setText(dataSnapshot.child("days_count").getValue(String.class));
                                            total_price_rp.setText(dataSnapshot.child("total_price").getValue(String.class));

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });



                                    return_btn_rp.setText("Return Placed");
                                    progressdialog.dismiss();

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });

                    }

                    @Override
                    public void onError() {

                        progressdialog.dismiss();


                        Toast.makeText(getApplicationContext(),"Image Not Loading", Toast.LENGTH_LONG).show();

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void return_process() {

        numberOfDays=0;

        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

        try {
            Date c = Calendar.getInstance().getTime();

            return_date = df.format(c);

            return_date_rp.setText(return_date);


            String dateStr = str_order_date_rp;
            Date date1 = df.parse(dateStr);

            long diff =  c.getTime() - date1.getTime();
            numberOfDays = (int) (diff / (1000 * 60 * 60 * 24));

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Catch", Toast.LENGTH_LONG).show();

        }

         total_price=0;

        if(numberOfDays>4)
        {
            total_price= total_price+(Integer.parseInt(Fix_price))+((numberOfDays-4)*Integer.parseInt(Daily_price));
        }
        else if(numberOfDays<=4&&numberOfDays>0)
            total_price=Integer.parseInt(Fix_price);
        else if(numberOfDays==0)
            total_price=0;


        total_price_rp.setText(String.valueOf(total_price));

        order = FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child("Orders").child(User_key);

        order.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                order.child("total_price").setValue(String.valueOf(total_price));
                order.child("return_status").setValue("true");
                order.child("return_date").setValue(String.valueOf(return_date));
                order.child("days_count").setValue(String.valueOf(numberOfDays));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();

            }
        });


        order_admin = FirebaseDatabase.getInstance().getReference().child("Datewiseorder").child(admin_order_date).child(Admin_key);

        order_admin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                order_admin.child("total_price").setValue(String.valueOf(total_price));
                order_admin.child("return_status").setValue("true");
                order_admin.child("return_date").setValue(String.valueOf(return_date));
                order_admin.child("days_count").setValue(String.valueOf(numberOfDays));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.home_button:
                Intent intent = new Intent(getApplicationContext(), home_page.class);
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
}

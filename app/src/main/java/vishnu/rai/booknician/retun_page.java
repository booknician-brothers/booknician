package vishnu.rai.booknician;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.LocaleData;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

public class retun_page extends AppCompatActivity implements View.OnClickListener {

    ImageView book_image_rp;

    TextView book_name_rp, order_date_rp, return_date_rp, day_count_rp, total_price_rp;

    Button return_btn_rp;

    public  String str_book_name_rp, str_order_date_rp, str_return_date_rp, str_day_count_rp, str_total_price_rp, str_book_image_rp;

    ImageView home_button, order_button, profile_button;

    Intent i;

    FirebaseAuth mAuth= FirebaseAuth.getInstance();

    public String User_key, Admin_key;

    FirebaseDatabase database= FirebaseDatabase.getInstance();
    DatabaseReference rp_data = database.getReference();

    ProgressDialog progressdialog;

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


        return_btn_rp.setOnClickListener(this);

        return_date_rp.setText("NULL");
        total_price_rp.setText("NULL");

        String userid= mAuth.getCurrentUser().getUid();

        rp_data.child("Users").child(userid).child("Orders").child(User_key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                str_book_name_rp= dataSnapshot.child("book_name").getValue(String.class);
                str_order_date_rp= dataSnapshot.child("order_date").getValue(String.class);
                str_book_image_rp= dataSnapshot.child("book_image").getValue(String.class);

                book_name_rp.setText(str_book_name_rp);
                order_date_rp.setText(str_order_date_rp);

                final String today_date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


                Picasso.with(getApplicationContext()).load(str_book_image_rp).into(book_image_rp, new Callback() {

                    @Override
                    public void onSuccess() {

                        progressdialog.dismiss();


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

                //intent =  new Intent(home_page.this, profile_page.class);
                //startActivity(intent);

                break;
        }

    }
}

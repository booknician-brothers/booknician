package vishnu.rai.booknician;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
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

public class profile_page extends AppCompatActivity implements View.OnClickListener {

    TextView profile_phone_show, profile_address_show, profile_emailid_show, profile_user_name;

    ImageView  home_button, profile_button, order_button;

    Button profile_contactus_btn, profile_logout_btn, profile_editprofile_btn;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    ProgressDialog progressdialog;

    public  String uid, user_name, user_email, user_phoneno, user_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        profile_phone_show=findViewById(R.id.profile_phone_show);
        profile_address_show=findViewById(R.id.profile_address_show);
        profile_emailid_show=findViewById(R.id.profile_emailid_show);
        profile_user_name=findViewById(R.id.profile_user_name);
        home_button=findViewById(R.id.home_button);
        profile_button=findViewById(R.id.profile_button);
        order_button=findViewById(R.id.order_button);
        profile_contactus_btn=findViewById(R.id.profile_contactus_btn);
        profile_logout_btn=findViewById(R.id.profile_logout_btn);
        profile_editprofile_btn=findViewById(R.id.profile_editprofile_btn);


        profile_editprofile_btn.setOnClickListener(this);
        home_button.setOnClickListener(this);
        profile_button.setOnClickListener(this);
        order_button.setOnClickListener(this);
        profile_contactus_btn.setOnClickListener(this);
        profile_logout_btn.setOnClickListener(this);


        progressdialog = new ProgressDialog(profile_page.this);
        progressdialog.setMessage("Please Wait....");
        progressdialog.show();

        getData();



    }

    private void getData() {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        uid=  mAuth.getUid();

        database.getReference("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                user_name= dataSnapshot.child("Name").getValue(String.class);
                user_email= dataSnapshot.child("Email").getValue(String.class);
                user_phoneno= dataSnapshot.child("Phone no").getValue(String.class);
                user_address= dataSnapshot.child("Address").getValue(String.class);

                profile_phone_show.setText(user_phoneno);
                profile_address_show.setText(user_address);
                profile_emailid_show.setText(user_email);
                profile_user_name.setText(user_name);

                progressdialog.dismiss();


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


            case R.id.profile_editprofile_btn:

                intent =  new Intent(getApplicationContext(), edit_profile.class);
                startActivity(intent);

                break;


            case R.id.profile_contactus_btn:

                contactus();

                intent =  new Intent(getApplicationContext(), profile_page.class);
                startActivity(intent);

                break;


            case R.id.profile_logout_btn:

                FirebaseAuth.getInstance().signOut();

                intent =  new Intent(getApplicationContext(), Loginpage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                break;
        }

    }

    private void contactus() {

    }

}

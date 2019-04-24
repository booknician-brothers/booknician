package vishnu.rai.booknician;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class edit_profile extends AppCompatActivity implements View.OnClickListener {

    EditText edit_name, edit_address;

    Button save_changes, change_password;

    ImageView home_button, order_button, profile_button;

    public String change_name, change_address, uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        edit_name=findViewById(R.id.edit_name);
        edit_address=findViewById(R.id.edit_address);
        save_changes=findViewById(R.id.save_changes);
        change_password=findViewById(R.id.change_password);

        home_button=findViewById(R.id.home_button);
        order_button=findViewById(R.id.order_button);
        profile_button=findViewById(R.id.profile_button);

        home_button.setOnClickListener(this);
        order_button.setOnClickListener(this);
        profile_button.setOnClickListener(this);


        change_password.setOnClickListener(this);
        save_changes.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.save_changes:

                saveEditChanges();

                Toast.makeText(getApplicationContext(),"Change saved", Toast.LENGTH_LONG).show();

                Intent intent= new Intent(edit_profile.this, home_page.class);
                startActivity(intent);

                break;

            case R.id.change_password:

                intent= new Intent(edit_profile.this, forgotpassactivity.class);
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

    private void saveEditChanges() {

        change_name= edit_name.getText().toString();
        change_address= edit_address.getText().toString();

        FirebaseDatabase changedata= FirebaseDatabase.getInstance();

        DatabaseReference savedatachanges= changedata.getReference();

        FirebaseAuth mAuth= FirebaseAuth.getInstance();

        uId= mAuth.getUid();

        if(change_name!="")
        {
            savedatachanges.child("Users").child(uId).child("Name").setValue(change_name);

        }

        if(change_address!="")
        {
            savedatachanges.child("Users").child(uId).child("Address").setValue(change_address);

        }


    }
}

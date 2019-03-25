package vishnu.rai.booknician;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Signuppage extends AppCompatActivity implements View.OnClickListener {


    EditText namesignup_et, phonesignup_et, emailsignup_et, passwordsignup_et, confirmpasswordsignup_et;
    RadioButton radiosignup_btn;
    Button finalsignup_btn, numberverify_btn;
    private FirebaseAuth mAuth;

    String email, password, name, phone, confirmpassword, userid;

    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        namesignup_et = findViewById(R.id.namesignup_et);
        emailsignup_et = findViewById(R.id.emailsignup_et);
        phonesignup_et = findViewById(R.id.phonesignup_et);
        passwordsignup_et = findViewById(R.id.passwordsignup_et);
        confirmpasswordsignup_et = findViewById(R.id.confirmpasswordsignup_et);
        radiosignup_btn = findViewById(R.id.radiosignup_btn);
        finalsignup_btn = findViewById(R.id.finalsignup_btn);
        numberverify_btn = findViewById(R.id.numberverify_btn);

        numberverify_btn.setOnClickListener(this);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();


        finalsignup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                register();

            }
        });

    }

    private void register() {
        Boolean radioCheck = false;
        Boolean isEqual = false;
        email = emailsignup_et.getText().toString();
        password = passwordsignup_et.getText().toString();
        name = namesignup_et.getText().toString();
        phone = phonesignup_et.getText().toString();
        confirmpassword = confirmpasswordsignup_et.getText().toString();


        if (radiosignup_btn.isChecked())
            radioCheck = true;

        isEqual = password.equals(confirmpassword);


        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmpassword) || TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || radioCheck == false || isEqual == false) {
            Toast.makeText(Signuppage.this, "INVALID CREDENTIALS", Toast.LENGTH_LONG).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        userid = mAuth.getCurrentUser().getUid();

                        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                        Map saveData = new HashMap();
                        {
                            saveData.put("Name", name);
                            //saveData.put("Phone no.", phone);
                            saveData.put("Email", email);
                            saveData.put("Password", password);
                        }
                        current_user_db.setValue(saveData);

                        startActivity(new Intent(Signuppage.this, home_page.class));


                    } else {
                        Toast.makeText(Signuppage.this, "Registration Unsucessful", Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.numberverify_btn:

                intent = new Intent(Signuppage.this, phoneverifypopup.class);
                startActivity(intent);

                break;


        }
    }
}

package vishnu.rai.booknician;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Loginpage extends AppCompatActivity implements View.OnClickListener {

    EditText email_et , password_et;
    Button  login_btn, signup_btn, googlesignup_btn;
    TextView skip_tv;
    private FirebaseAuth.AuthStateListener AuthListner;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        mAuth = FirebaseAuth.getInstance();



        email_et=  findViewById(R.id.email_et);
        password_et= findViewById(R.id.password_et);
        login_btn= findViewById(R.id.login_btn);
        signup_btn= findViewById(R.id.signup_btn);
        googlesignup_btn= findViewById(R.id.googlesignup_btn);
        skip_tv= findViewById(R.id.skip_tv);

        signup_btn.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        googlesignup_btn.setOnClickListener(this);
        skip_tv.setOnClickListener(this);



        AuthListner= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(Loginpage.this, home_page.class));
                }

            }
        };



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.signup_btn:
                startActivity(new Intent(Loginpage.this, Signuppage.class));
                break;

            case R.id.login_btn:
                login();
                break;

        }

    }

    @Override
    protected  void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(AuthListner);
    }


    private void  login(){

        String Email = email_et.getText().toString();
        String Password = password_et.getText().toString();

        if(TextUtils.isEmpty(Email)|| TextUtils.isEmpty(Password)){
            Toast.makeText(Loginpage.this, "Feild empty", Toast.LENGTH_LONG).show();

        }
        else {

            mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {
                        Toast.makeText(Loginpage.this, "Sign in problem", Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
    }
}


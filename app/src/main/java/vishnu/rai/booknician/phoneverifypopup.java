package vishnu.rai.booknician;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class phoneverifypopup extends Activity {


    /*EditText otp_et;
    Button submit_otp, resend_otp;

    Intent i;

    public String OTP;

    String otp_entered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneverifypopup);

        OTP = i.getStringExtra("OTP");

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.3));

        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x=0;
        params.y=-20;
        getWindow().setAttributes(params);

        otp_et= findViewById(R.id.otp_et);
        submit_otp = findViewById(R.id.submit_otp);
        resend_otp = findViewById(R.id.resend_otp);

        submit_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                otp_entered =  otp_et.getText().toString();

                verification();

            }
        });

    }




    private void verification() {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(OTP, otp_entered);
        signInWithPhoneAuthCredential(credential);

    }
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Signuppage.verified= true;
                            Intent intent = new Intent(getApplicationContext(), Signuppage.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);



                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Signuppage.verified= false;
                                Toast.makeText(getApplicationContext(),"Wrong OTP", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), Signuppage.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        }
                    }
                });
    }*/
}

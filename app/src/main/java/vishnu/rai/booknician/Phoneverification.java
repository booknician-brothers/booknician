package vishnu.rai.booknician;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class Phoneverification extends AppCompatActivity {

    EditText enter_otp, phone_number;

    Button submit, send_otp;

    String phoneNumber, code_sent, code_entered;

    FirebaseAuth mAuth;

    ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_verification);

        enter_otp=findViewById(R.id.enter_otp);
        phone_number=findViewById(R.id.phone_number);
        submit=findViewById(R.id.submit);
        send_otp=findViewById(R.id.send_otp);

        mAuth=FirebaseAuth.getInstance();

       send_otp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               progressdialog = new ProgressDialog(Phoneverification.this);
               progressdialog.setMessage("Please Wait....");
               progressdialog.show();
               sendVerificationCode();
           }
       });
       
       submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               verifySignInCode();
           }
       });
    }

    private void verifySignInCode(){
        String code = enter_otp.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(code_sent, code);

        if(code_sent.equals(code)){

            Intent intent= new Intent(getApplicationContext(),Signuppage.class);
            intent.putExtra("Phone number",phone_number.getText().toString());
            startActivity(intent);

        }

        else
            Toast.makeText(getApplicationContext(), "Wrong code", Toast.LENGTH_LONG).show();

    }





    private void sendVerificationCode(){

        String phone = phone_number.getText().toString();

        phone= "+91"+phone;

        if(phone.isEmpty()){
            phone_number.setError("Phone number is required");
            phone_number.requestFocus();
            return;
        }

        if(phone.length() < 10 ){
            phone_number.setError("Please enter a valid phone");
            phone_number.requestFocus();
            return;
        }


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }



    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            code_sent = s;
            progressdialog.dismiss();
        }
    };

}

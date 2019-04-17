package vishnu.rai.booknician;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class phoneverifypopup extends Activity {

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    EditText otp_et;
    Button submit_otp, resend_otp;

    String otp_entered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneverifypopup);

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

        /*resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        Signuppage.phone,        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        this,               // Activity (for callback binding)
                        mCallbacks);        // OnVerificationStateChangedCallbacks

                mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                    }

                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);

                        Signuppage.otp_send = s;
                    }
                };
            }
        });*/


    }

    private void verification() {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(Signuppage.otp_send, otp_entered);

        if(Signuppage.otp_send.equals(otp_entered))
            Signuppage.verified = true;

    }
}

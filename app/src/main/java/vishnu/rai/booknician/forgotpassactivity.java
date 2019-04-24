package vishnu.rai.booknician;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpassactivity extends AppCompatActivity {

    Button reset_link_otp;
    Intent i;
    EditText reset_email_et;
    String reset_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassactivity);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height=dm.heightPixels;


        getWindow().setLayout((int)(width*.85),(int)(height*.6));

        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x=0;
        params.y=-20;
        getWindow().setAttributes(params);

        reset_email_et= findViewById(R.id.reset_email);
        reset_link_otp= findViewById(R.id.reset_link_otp);

        reset_link_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reset_email= reset_email_et.getText().toString();

                if(reset_email.isEmpty())
                {
                    reset_email_et.setError("Empty");
                    reset_email_et.requestFocus();
                }

                else
                {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(reset_email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(forgotpassactivity.this, "Link sent", Toast.LENGTH_LONG).show();
                                        onBackPressed();
                                    }
                                    else
                                    {
                                        Toast.makeText(forgotpassactivity.this, "Invalid email", Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                }
            }
        });


    }
}

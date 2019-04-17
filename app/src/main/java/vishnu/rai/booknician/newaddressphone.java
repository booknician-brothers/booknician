package vishnu.rai.booknician;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class newaddressphone extends Activity  {


    EditText newaddress_et, pincode_et, newphonenumber_et;
    Button addnewaddress_btn;

    public static String newaddress_string, pincode_string, newphonenumber_string, book_name;

    public Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newaddressphone);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.5));

        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x=0;
        params.y=-20;
        getWindow().setAttributes(params);

        i=getIntent();

        book_name = i.getStringExtra("Book name");


        newaddress_et=findViewById(R.id.newaddress_et);
        pincode_et=findViewById(R.id.pincode_et);
        newphonenumber_et=findViewById(R.id.newphonenumber_et);
        addnewaddress_btn=findViewById(R.id.addnewaddress_btn);


        addnewaddress_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newaddress_string= newaddress_et.getText().toString();
                pincode_string= pincode_et.getText().toString();
                newphonenumber_string=newphonenumber_et.getText().toString();

                if(newphonenumber_string.isEmpty()||newphonenumber_string.length()!=10)
                {
                    newphonenumber_et.setError("Empty");
                    newphonenumber_et.requestFocus();

                    newaddress_string="";
                    pincode_string="";
                    newphonenumber_string="";

                    return;
                }

                else if(pincode_string.isEmpty()||pincode_string.length()!=6)
                {
                    pincode_et.setError("Invalid");
                    pincode_et.requestFocus();

                    newaddress_string="";
                    pincode_string="";
                    newphonenumber_string="";

                    return;
                }

                else if(newaddress_string.isEmpty())
                {
                    newaddress_et.setError("Empty");
                    newaddress_et.requestFocus();

                    newaddress_string="";
                    pincode_string="";
                    newphonenumber_string="";


                    return;
                }

                else
                {

                    Toast.makeText(getApplicationContext(),"Address added", Toast.LENGTH_SHORT).show();

                    addressphone.newaddress=true;

                    Intent intent = new Intent(newaddressphone.this, addressphone.class);
                    intent.putExtra("Book name",Allbookshowpage.allbook_clicked_name );
                    startActivity(intent);
                }
            }
        });

    }

}

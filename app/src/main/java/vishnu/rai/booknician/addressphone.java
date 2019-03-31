package vishnu.rai.booknician;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class addressphone extends AppCompatActivity implements View.OnClickListener {

    TextView addnewaddress_tv;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addressphone);

        addnewaddress_tv = findViewById(R.id.addnewaddress_tv);

        addnewaddress_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.addnewaddress_tv:


                intent = new Intent(addressphone.this, newaddressphone.class);
                startActivity(intent);

                break;
        }
    }
}

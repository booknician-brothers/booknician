package vishnu.rai.booknician;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class home_page extends AppCompatActivity implements View.OnClickListener {


    Button genre_btn, author_btn, bestseller_btn, allbooks_btn;
    ImageView home_button, order_button, profile_button;

    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        genre_btn = findViewById(R.id.genre_btn);
        author_btn = findViewById(R.id.author_btn);
        bestseller_btn = findViewById(R.id.bestseller_btn);
        allbooks_btn = findViewById(R.id.allbooks_btn);

        home_button = findViewById(R.id.home_button);
        order_button = findViewById(R.id.order_button);
        profile_button = findViewById(R.id.profile_button);
        allbooks_btn.setOnClickListener(this);

        genre_btn.setOnClickListener(this);
        author_btn.setOnClickListener(this);
        bestseller_btn.setOnClickListener(this);
        home_button.setOnClickListener(this);
        order_button.setOnClickListener(this);
        profile_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.genre_btn:

                genre_btn.getTextColors();

                intent = new Intent(home_page.this, Genrepage.class);
                startActivity(intent);

                break;

            case R.id.author_btn:

                intent = new Intent(home_page.this, Authorpage.class);
                startActivity(intent);

                break;

            case R.id.bestseller_btn:

                intent = new Intent(home_page.this, Bestsellerbookpage.class);
                startActivity(intent);

                break;

            case R.id.allbooks_btn:

                intent = new Intent(home_page.this, Allbookshowpage.class);
                startActivity(intent);

                break;


            case R.id.order_button:

                intent =  new Intent(getApplicationContext(), user_order_page.class);
                startActivity(intent);

                break;

            case R.id.profile_button:

                intent =  new Intent(home_page.this, profile_page.class);
                startActivity(intent);

                break;

        }


    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        home_page.super.onBackPressed();
                    }
                }).create().show();
    }
}

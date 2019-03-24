package vishnu.rai.booknician;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class home_page extends AppCompatActivity implements View.OnClickListener {


    Button genre_btn, author_btn, bestseller_btn;
    ImageView home_btn,myorder_btn,profile_btn;

    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        genre_btn=findViewById(R.id.genre_btn);
        author_btn=findViewById(R.id.author_btn);
        bestseller_btn=findViewById(R.id.bestseller_btn);

        home_btn=findViewById(R.id.home_btn);
        myorder_btn=findViewById(R.id.myorder_btn);
        profile_btn=findViewById(R.id.profile_btn);

        genre_btn.setOnClickListener(this);
        author_btn.setOnClickListener(this);
        bestseller_btn.setOnClickListener(this);
        home_btn.setOnClickListener(this);
        myorder_btn.setOnClickListener(this);
        profile_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.genre_btn:

                intent =  new Intent(home_page.this, Genrepage.class);
                startActivity(intent);

                break;

            case R.id.author_btn:

                intent =  new Intent(home_page.this, Authorpage.class);
                startActivity(intent);

                break;

            case R.id.bestseller_btn:

                intent =  new Intent(home_page.this, Bestsellerbookpage.class);
                startActivity(intent);

                break;

            case  R.id.myorder_btn:

                //intent =  new Intent(home_page.this, myorder_page.class);
                //startActivity(intent);

                break;

            case R.id.profile_btn:

                //intent =  new Intent(home_page.this, profile_page.class);
                //startActivity(intent);

                break;

        }




    }
}

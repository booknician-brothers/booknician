package vishnu.rai.booknician;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Genrepage extends AppCompatActivity implements View.OnClickListener {

    TextView horror_tv, romantic_tv, suspense_tv, fantasy_tv, women_tv, biography_tv, science_tv;
    ImageView home_btn,myorder_btn,profile_btn;

    public static String genre_name;


    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.genre_page);

        horror_tv=findViewById(R.id.horror_tv);
        romantic_tv=findViewById(R.id.romantic_tv);
        suspense_tv=findViewById(R.id.suspense_tv);
        fantasy_tv=findViewById(R.id.fantasy_tv);
        women_tv=findViewById(R.id.womenn_tv);
        biography_tv=findViewById(R.id.biography_tv);
        science_tv=findViewById(R.id.science_tv);


        horror_tv.setOnClickListener(this);
        romantic_tv.setOnClickListener(this);
        science_tv.setOnClickListener(this);
        fantasy_tv.setOnClickListener(this);
        women_tv.setOnClickListener(this);
        biography_tv.setOnClickListener(this);
        science_tv.setOnClickListener(this);




        home_btn=findViewById(R.id.home_btn);
        myorder_btn=findViewById(R.id.myorder_btn);
        profile_btn=findViewById(R.id.profile_btn);


        home_btn.setOnClickListener(this);
        myorder_btn.setOnClickListener(this);
        profile_btn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.romantic_tv:

                genre_name="Romantic";

                intent= new Intent(Genrepage.this, Genrebooktype.class);
                startActivity(intent);

                break;


            case R.id.suspense_tv:

                genre_name="Suspense";

                intent= new Intent(Genrepage.this, Genrebooktype.class);
                startActivity(intent);

                break;

            case R.id.science_tv:

                genre_name="Science";

                intent= new Intent(Genrepage.this, Genrebooktype.class);
                startActivity(intent);

                break;

            case R.id.horror_tv:

                genre_name="Horror";

                intent= new Intent(Genrepage.this, Genrebooktype.class);
                startActivity(intent);

                break;

            case R.id.womenn_tv:

                genre_name="Women";

                intent= new Intent(Genrepage.this, Genrebooktype.class);
                startActivity(intent);

                break;

            case R.id.fantasy_tv:

                genre_name="Fantasy";

                intent= new Intent(Genrepage.this, Genrebooktype.class);
                startActivity(intent);

                break;

            case R.id.biography_tv:

                genre_name="Biography";

                intent= new Intent(Genrepage.this, Genrebooktype.class);
                startActivity(intent);

                break;

        }

    }
}

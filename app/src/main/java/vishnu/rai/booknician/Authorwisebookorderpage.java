package vishnu.rai.booknician;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Authorwisebookorderpage extends AppCompatActivity implements View.OnClickListener {


    TextView orderpage_bookname_aw_tv, orderpage_authorname_aw_tv, orderpage_priceforfixday_aw_tv, orderpage_bookdailyprice_aw_tv
            ,orderpage_bookdescription_aw_tv,orderpage_orderbutton_aw_tv;

    ImageView orderpage_bookimage_aw_iv;

    ImageView home_button, order_button, profile_button;



    String orderpage_bookname, orderpage_authorname, orderpage_bookinstock,orderpage_priceforfixday,
            orderpage_bookdailyprice,orderpage_bookdescription;

    String orderpage_bookimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorwise_book_order_page);

        final FirebaseDatabase database =  FirebaseDatabase.getInstance();
        final DatabaseReference Myref = database.getReference();

        FirebaseDatabase database_book = FirebaseDatabase.getInstance();

        home_button=findViewById(R.id.home_button);
        order_button=findViewById(R.id.order_button);
        profile_button=findViewById(R.id.profile_button);

        home_button.setOnClickListener(this);
        profile_button.setOnClickListener(this);
        order_button.setOnClickListener(this);


        orderpage_authorname_aw_tv=findViewById(R.id.orderpage_authorname_aw_tv);
        orderpage_bookdailyprice_aw_tv=findViewById(R.id.orderpage_bookdailyprice_aw_tv);
        orderpage_bookdescription_aw_tv= findViewById(R.id.orderpage_bookdescription_aw_tv);
        orderpage_bookname_aw_tv=findViewById(R.id.orderpage_bookname_aw_tv);
        orderpage_priceforfixday_aw_tv=findViewById(R.id.orderpage_priceforfixday_aw_tv);
        orderpage_orderbutton_aw_tv=findViewById(R.id.orderpage_orderbutton_aw_tv);
        orderpage_bookimage_aw_iv=findViewById(R.id.orderpage_bookimage_aw_iv);



        database.getReference("Book author wise").child(Authorpage.authorname_clicked_name).child(String.valueOf(Authorwisebooknamepage.authorwise_book_position)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                orderpage_bookname = dataSnapshot.child("name").getValue(String.class);
                orderpage_bookimage=dataSnapshot.child("image").getValue(String.class);

                orderpage_bookname_aw_tv.setText(orderpage_bookname);


                //adding image

                Picasso.with(Authorwisebookorderpage.this)
                        .load(orderpage_bookimage)
                        .into(orderpage_bookimage_aw_iv);

                //image added



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        database_book.getReference("Books").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                orderpage_bookinstock = dataSnapshot.child(orderpage_bookname).child("Book In Stock").getValue(String.class);

                if(orderpage_bookinstock.equalsIgnoreCase("0"))
                    orderpage_orderbutton_aw_tv.setText("Out of Stock");

                else
                    orderpage_orderbutton_aw_tv.setText("Order Now");


                orderpage_bookdailyprice = dataSnapshot.child(orderpage_bookname).child("Daily Price").getValue(String.class);
                orderpage_bookdailyprice_aw_tv.setText(orderpage_bookdailyprice);


                orderpage_priceforfixday = dataSnapshot.child(orderpage_bookname).child("Fix Price").getValue(String.class);
                orderpage_priceforfixday_aw_tv.setText(orderpage_priceforfixday);


                orderpage_bookdescription = dataSnapshot.child(orderpage_bookname).child("Description").getValue(String.class);
                orderpage_bookdescription_aw_tv.setText(orderpage_bookdescription);


                orderpage_authorname = dataSnapshot.child(orderpage_bookname).child("Author").getValue(String.class);
                orderpage_authorname_aw_tv.setText(orderpage_authorname);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.home_button:
                Intent intent = new Intent(getApplicationContext(), home_page.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                break;

            case R.id.profile_button:

                break;

            case R.id.order_button:

                break;
        }

    }
}

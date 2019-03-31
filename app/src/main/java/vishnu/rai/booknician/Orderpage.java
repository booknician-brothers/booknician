package vishnu.rai.booknician;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Orderpage extends AppCompatActivity implements View.OnClickListener{


    TextView orderpage_bookname_tv, orderpage_authorname_tv, orderpage_bookinstock_tv ,orderpage_priceforfixday_tv, orderpage_bookdailyprice_tv
            ,orderpage_bookdescription_tv,orderpage_orderbutton_tv;

    ImageView orderpage_bookimage_iv;

    ImageView home_button, order_button, profile_button;




    String orderpage_bookname, orderpage_authorname, orderpage_bookinstock,orderpage_priceforfixday,
            orderpage_bookdailyprice,orderpage_bookdescription;

    String orderpage_bookimage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_page);

        home_button=findViewById(R.id.home_button);
        order_button=findViewById(R.id.order_button);
        profile_button=findViewById(R.id.profile_button);

        home_button.setOnClickListener(this);
        profile_button.setOnClickListener(this);
        order_button.setOnClickListener(this);



        final FirebaseDatabase database =  FirebaseDatabase.getInstance();
        final DatabaseReference Myref = database.getReference();

        FirebaseDatabase database_book = FirebaseDatabase.getInstance();


        orderpage_authorname_tv=findViewById(R.id.orderpage_authorname_tv);
        orderpage_bookdailyprice_tv=findViewById(R.id.orderpage_bookdailyprice_tv);
        orderpage_bookdescription_tv= findViewById(R.id.orderpage_bookdescription_tv);
        orderpage_bookname_tv=findViewById(R.id.orderpage_bookname_tv);
        orderpage_priceforfixday_tv=findViewById(R.id.orderpage_priceforfixday_tv);
        orderpage_orderbutton_tv=findViewById(R.id.orderpage_orderbutton_tv);
        orderpage_bookimage_iv=findViewById(R.id.orderpage_bookimage_iv);


                database.getReference("Genre").child(Genrepage.genre_name).child(String.valueOf(Genrebooktype.genre_book_position)).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        orderpage_bookname = dataSnapshot.child("name").getValue(String.class);
                        orderpage_bookimage=dataSnapshot.child("image").getValue(String.class);

                        orderpage_bookname_tv.setText(orderpage_bookname);

                        //adding image

                        Picasso.with(Orderpage.this)
                                .load(orderpage_bookimage)
                                .into(orderpage_bookimage_iv);

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
                    orderpage_orderbutton_tv.setText("Out of Stock");

                else {
                    orderpage_orderbutton_tv.setText("Order Now");

                    orderpage_orderbutton_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Orderpage.this, addressphone.class);
                            startActivity(intent);
                        }
                    });
                }


                orderpage_bookdailyprice = dataSnapshot.child(orderpage_bookname).child("Daily Price").getValue(String.class);
                orderpage_bookdailyprice_tv.setText(orderpage_bookdailyprice);


                orderpage_priceforfixday = dataSnapshot.child(orderpage_bookname).child("Fix Price").getValue(String.class);
                orderpage_priceforfixday_tv.setText(orderpage_priceforfixday);


                orderpage_bookdescription = dataSnapshot.child(orderpage_bookname).child("Description").getValue(String.class);
                orderpage_bookdescription_tv.setText(orderpage_bookdescription);


                orderpage_authorname = dataSnapshot.child(orderpage_bookname).child("Author").getValue(String.class);
                orderpage_authorname_tv.setText(orderpage_authorname);


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

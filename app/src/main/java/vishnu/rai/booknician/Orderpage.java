package vishnu.rai.booknician;

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

public class Orderpage extends AppCompatActivity {


    TextView orderpage_bookname_tv, orderpage_authorname_tv, orderpage_bookinstock_tv ,orderpage_priceforfixday_tv, orderpage_bookdailyprice_tv
            ,orderpage_bookdescription_tv,orderpage_orderbutton_tv;

    ImageView orderpage_bookimage_iv;



     String orderpage_bookname, orderpage_authorname, orderpage_bookinstock,orderpage_priceforfixday,
            orderpage_bookdailyprice,orderpage_bookdescription;

    String orderpage_bookimage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_page);

        final FirebaseDatabase database =  FirebaseDatabase.getInstance();
        final DatabaseReference Myref = database.getReference();


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
                        orderpage_authorname= dataSnapshot.child("author").getValue(String.class);
                        orderpage_bookinstock=dataSnapshot.child("book in stock").getValue(String.class);
                        orderpage_priceforfixday=dataSnapshot.child("fix price").getValue(String.class);
                        orderpage_bookdailyprice=dataSnapshot.child("daily price").getValue(String.class);
                        orderpage_bookdescription=dataSnapshot.child("description").getValue(String.class);
                        orderpage_bookimage=dataSnapshot.child("image").getValue(String.class);

                        orderpage_bookname_tv.setText(orderpage_bookname);
                        orderpage_authorname_tv.setText(orderpage_authorname);
                        orderpage_bookdescription_tv.setText(orderpage_bookdescription);
                        orderpage_bookdailyprice_tv.setText(orderpage_bookdailyprice);
                        orderpage_priceforfixday_tv.setText(orderpage_priceforfixday);

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


        database.getReference("Book in stock").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                orderpage_bookinstock = dataSnapshot.child(orderpage_bookname).getValue(String.class);

                if(orderpage_bookinstock.equalsIgnoreCase("0"))
                    orderpage_orderbutton_tv.setText("Out of Stock");

                else
                    orderpage_orderbutton_tv.setText("Order Now");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

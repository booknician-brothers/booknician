package vishnu.rai.booknician;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class Allbookorderpage extends AppCompatActivity implements View.OnClickListener {


    TextView orderpage_bookname_ab_tv, orderpage_authorname_ab_tv, orderpage_bookinstock_ab_tv ,orderpage_priceforfixday_ab_tv, orderpage_bookdailyprice_ab_tv
            ,orderpage_bookdescription_ab_tv,orderpage_orderbutton_ab_tv;

    ImageView orderpage_bookimage_ab_iv;

    ProgressDialog progressdialog;

    ImageView home_button, order_button, profile_button;

    String orderpage_bookname, orderpage_authorname, orderpage_bookinstock,orderpage_priceforfixday,
            orderpage_bookdailyprice,orderpage_bookdescription;

    String orderpage_bookimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.allbook_order_page);



        home_button=findViewById(R.id.home_button);
        order_button=findViewById(R.id.order_button);
        profile_button=findViewById(R.id.profile_button);

        home_button.setOnClickListener(this);
        profile_button.setOnClickListener(this);
        order_button.setOnClickListener(this);

        FirebaseDatabase database_book = FirebaseDatabase.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        orderpage_authorname_ab_tv=findViewById(R.id.orderpage_authorname_ab_tv);
        orderpage_bookdailyprice_ab_tv=findViewById(R.id.orderpage_bookdailyprice_ab_tv);
        orderpage_bookdescription_ab_tv= findViewById(R.id.orderpage_bookdescription_ab_tv);
        orderpage_bookname_ab_tv=findViewById(R.id.orderpage_bookname_ab_tv);
        orderpage_priceforfixday_ab_tv=findViewById(R.id.orderpage_priceforfixday_ab_tv);
        orderpage_orderbutton_ab_tv=findViewById(R.id.orderpage_orderbutton_ab_tv);
        orderpage_bookimage_ab_iv=findViewById(R.id.orderpage_bookimage_ab_iv);


        progressdialog = new ProgressDialog(Allbookorderpage.this);
        progressdialog.setMessage("Please Wait....");
        progressdialog.show();

        /*database.getReference("Books").child(Allbookshowpage.allbook_clicked_name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                orderpage_bookname = dataSnapshot.child("name").getValue(String.class);

                orderpage_bookname_ab_tv.setText(orderpage_bookname);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


        database_book.getReference("Books").child(Allbookshowpage.allbook_clicked_name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                orderpage_bookname = Allbookshowpage.allbook_clicked_name;

                orderpage_bookname_ab_tv.setText(orderpage_bookname);


                orderpage_bookinstock = dataSnapshot.child("Book In Stock").getValue(String.class);

                if(orderpage_bookinstock.equalsIgnoreCase("0"))

                    orderpage_orderbutton_ab_tv.setText("Out of Stock");

                else {
                    orderpage_orderbutton_ab_tv.setText("Order Now");

                    orderpage_orderbutton_ab_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Allbookorderpage.this, addressphone.class);
                            intent.putExtra("Book name",Allbookshowpage.allbook_clicked_name );
                            intent.putExtra("Bookcount",orderpage_bookinstock );
                            startActivity(intent);
                        }
                    });
                }


                orderpage_bookdailyprice = dataSnapshot.child("Daily Price").getValue(String.class);
                orderpage_bookdailyprice_ab_tv.setText(orderpage_bookdailyprice);


                orderpage_priceforfixday = dataSnapshot.child("Fix Price").getValue(String.class);
                orderpage_priceforfixday_ab_tv.setText(orderpage_priceforfixday);


                orderpage_bookdescription = dataSnapshot.child("Description").getValue(String.class);
                orderpage_bookdescription_ab_tv.setText(orderpage_bookdescription);


                orderpage_authorname = dataSnapshot.child("Author").getValue(String.class);
                orderpage_authorname_ab_tv.setText(orderpage_authorname);

                orderpage_bookimage=dataSnapshot.child("image").getValue(String.class);

                Picasso.with(Allbookorderpage.this)
                        .load(orderpage_bookimage)
                        .into(orderpage_bookimage_ab_iv , new Callback() {

                            @Override
                            public void onSuccess() {

                                progressdialog.dismiss();


                            }

                            @Override
                            public void onError() {

                                progressdialog.dismiss();


                                Toast.makeText(getApplicationContext(),"Image Not Loading", Toast.LENGTH_LONG).show();

                            }
                        });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {

            case R.id.home_button:
                Intent intent = new Intent(getApplicationContext(), home_page.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                break;


            case R.id.order_button:

                intent =  new Intent(getApplicationContext(), user_order_page.class);
                startActivity(intent);

                break;

            case R.id.profile_button:

                intent =  new Intent(getApplicationContext(), profile_page.class);
                startActivity(intent);

                break;
        }

    }
}

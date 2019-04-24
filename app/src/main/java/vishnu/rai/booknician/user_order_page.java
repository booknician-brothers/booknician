package vishnu.rai.booknician;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import vishnu.rai.booknician.View_holder.item_view_holder;
import vishnu.rai.booknician.View_holder.orderpage_item_view_holder;
import vishnu.rai.booknician.model.orderpage_recyclerview_item;
import vishnu.rai.booknician.model.recyclerview_item;

public class user_order_page extends AppCompatActivity implements View.OnClickListener {



    RecyclerView order_page_recycler_view;

    public static String allbook_clicked_name;

    public String userid;

    ImageView home_button, order_button, profile_button;

    ProgressDialog progressdialog;

    DatabaseReference mdatabaseReference;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference dayscount = database.getReference();

    FirebaseRecyclerOptions<orderpage_recyclerview_item> options;

    FirebaseRecyclerAdapter<orderpage_recyclerview_item, orderpage_item_view_holder> adapter;

    FirebaseAuth mAuth= FirebaseAuth.getInstance();

    public  String Fix_price, Daily_price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_order_page);


        home_button=findViewById(R.id.home_button);
        order_button=findViewById(R.id.order_button);
        profile_button=findViewById(R.id.profile_button);

        home_button.setOnClickListener(this);
        profile_button.setOnClickListener(this);
        order_button.setOnClickListener(this);



        order_page_recycler_view=findViewById(R.id.order_page_recycler_view);

        progressdialog = new ProgressDialog(user_order_page.this);
        progressdialog.setMessage("Please Wait....");
        progressdialog.show();

        order_page_recycler_view.hasFixedSize();

        userid = mAuth.getCurrentUser().getUid();

        mdatabaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child("Orders");

        options = new FirebaseRecyclerOptions.Builder<orderpage_recyclerview_item>().setQuery(mdatabaseReference,orderpage_recyclerview_item.class).build();


        adapter= new FirebaseRecyclerAdapter<orderpage_recyclerview_item, orderpage_item_view_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final orderpage_item_view_holder holder, final int position, @NonNull final orderpage_recyclerview_item model) {



                Picasso.with(getApplicationContext()).load(model.getBook_image()).into(holder.op_book_image, new Callback() {

                    @Override
                    public void onSuccess() {


                        FirebaseDatabase return_detail = FirebaseDatabase.getInstance();

                        DatabaseReference return_data= return_detail.getReference();

                        return_data.child("Books").child(model.getBook_name()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                Fix_price= dataSnapshot.child("Fix Price").getValue(String.class);
                                Daily_price= dataSnapshot.child("Daily Price").getValue(String.class);

                                progressdialog.dismiss();


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {


                            }
                        });

                    }

                    @Override
                    public void onError() {

                        progressdialog.dismiss();


                        Toast.makeText(getApplicationContext(),"Image Not Loading", Toast.LENGTH_LONG).show();

                    }
                });


                holder.op_book_name.setText(model.getBook_name());

                holder.order_date.setText(model.getOrder_date());


                holder.op_book_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        database.getReference().child("Books").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                Intent intent= new Intent(user_order_page.this, retun_page.class);
                                intent.putExtra("Admin_key", userid+model.getBook_name()+model.getTime_date());
                                intent.putExtra("User_key", model.getBook_name()+model.getTime_date());
                                intent.putExtra("Book_name", model.getBook_name());
                                intent.putExtra("Fix_price", Fix_price);
                                intent.putExtra("Daily_price", Daily_price);

                                startActivity(intent);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                });
            }

            @NonNull
            @Override
            public orderpage_item_view_holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_order_page_layout, viewGroup,  false);
                return new orderpage_item_view_holder(view);
            }
        };

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        order_page_recycler_view.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        order_page_recycler_view.setAdapter(adapter);

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

                intent =  new Intent(getApplicationContext(), profile_page.class);
                startActivity(intent);

                break;
        }

    }
}

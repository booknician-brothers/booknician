package vishnu.rai.booknician;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import vishnu.rai.booknician.View_holder.item_view_holder;
import vishnu.rai.booknician.model.recyclerview_item;

public class Bestsellerbookpage extends AppCompatActivity implements View.OnClickListener{

    RecyclerView bestseller_recycler_view;

    public static int genre_book_position;

    ImageView home_button, order_button, profile_button;

    ProgressDialog progressdialog;

    DatabaseReference mdatabaseReference;
    FirebaseRecyclerOptions<recyclerview_item> options;
    FirebaseRecyclerAdapter<recyclerview_item, item_view_holder> adapter;

    FirebaseDatabase image_database = FirebaseDatabase.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bestseller_book_page);


        home_button=findViewById(R.id.home_button);
        order_button=findViewById(R.id.order_button);
        profile_button=findViewById(R.id.profile_button);

        home_button.setOnClickListener(this);
        profile_button.setOnClickListener(this);
        order_button.setOnClickListener(this);

        progressdialog = new ProgressDialog(Bestsellerbookpage.this);
        progressdialog.setMessage("Please Wait....");
        progressdialog.show();

        bestseller_recycler_view=findViewById(R.id.bestseller_recycler_view);

        bestseller_recycler_view.hasFixedSize();

        mdatabaseReference= FirebaseDatabase.getInstance().getReference().child("Best Seller");

        options = new FirebaseRecyclerOptions.Builder<recyclerview_item>().setQuery(mdatabaseReference,recyclerview_item.class).build();


        adapter= new FirebaseRecyclerAdapter<recyclerview_item, item_view_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final item_view_holder holder, final int position, @NonNull recyclerview_item model) {


                image_database.getReference().child("Books").child(model.getName()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        String image_url =  dataSnapshot.child("image").getValue(String.class);

                        Picasso.with(getApplicationContext()).load(image_url).into(holder.book_image, new Callback() {

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


                holder.book_name.setText(model.getName());


                holder.book_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        genre_book_position = position+1;

                        Intent intent= new Intent(Bestsellerbookpage.this, bestseller_orderpage.class);
                        startActivity(intent);


                    }
                });


            }



            @NonNull
            @Override
            public item_view_holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.books_layout, viewGroup,  false);
                return new item_view_holder(view);
            }
        };


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        bestseller_recycler_view.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        bestseller_recycler_view.setAdapter(adapter);

    }

    @Override
    protected void onStart() {

        if(adapter!=null)
            adapter.startListening();
        super.onStart();

    }

    @Override
    protected void onStop() {
        if(adapter!=null)
            adapter.stopListening();
        super.onStop();
    }


    @Override
    protected void onPostResume() {
        if(adapter!=null)
            adapter.startListening();
        super.onPostResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

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

                //intent =  new Intent(home_page.this, profile_page.class);
                //startActivity(intent);

                break;
        }
    }
}

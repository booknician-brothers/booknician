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

public class Allbookshowpage extends AppCompatActivity implements View.OnClickListener {

    RecyclerView allbooks_recycler_view;

    public static String allbook_clicked_name;

    ImageView home_button, order_button, profile_button;

    ProgressDialog progressdialog;

    DatabaseReference mdatabaseReference;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    FirebaseRecyclerOptions<recyclerview_item> options;

    FirebaseRecyclerAdapter<recyclerview_item, item_view_holder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_book_page);



        home_button=findViewById(R.id.home_button);
        order_button=findViewById(R.id.order_button);
        profile_button=findViewById(R.id.profile_button);

        home_button.setOnClickListener(this);
        profile_button.setOnClickListener(this);
        order_button.setOnClickListener(this);



        allbooks_recycler_view=findViewById(R.id.allbooks_recycler_view);

        progressdialog = new ProgressDialog(Allbookshowpage.this);
        progressdialog.setMessage("Please Wait....");
        progressdialog.show();

        allbooks_recycler_view.hasFixedSize();

        mdatabaseReference= FirebaseDatabase.getInstance().getReference().child("Books");

        options = new FirebaseRecyclerOptions.Builder<recyclerview_item>().setQuery(mdatabaseReference,recyclerview_item.class).build();


        adapter= new FirebaseRecyclerAdapter<recyclerview_item, item_view_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final item_view_holder holder, final int position, @NonNull final recyclerview_item model) {



                Picasso.with(getApplicationContext()).load(model.getImage()).into(holder.book_image, new Callback() {

                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                        Toast.makeText(getApplicationContext(),"Image Not Loading", Toast.LENGTH_LONG).show();

                    }
                });


                holder.book_name.setText(model.getName());


                holder.book_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        database.getReference().child("Books").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            allbook_clicked_name = model.getName();

                            Intent intent= new Intent(Allbookshowpage.this, bestseller_orderpage.class);
                            startActivity(intent);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                });

                progressdialog.dismiss();

            }



            @NonNull
            @Override
            public item_view_holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.books_layout, viewGroup,  false);
                return new item_view_holder(view);
            }
        };


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        allbooks_recycler_view.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        allbooks_recycler_view.setAdapter(adapter);


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

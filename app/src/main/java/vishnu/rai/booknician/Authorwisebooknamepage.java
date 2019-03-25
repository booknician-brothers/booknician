package vishnu.rai.booknician;

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
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import vishnu.rai.booknician.View_holder.item_view_holder;
import vishnu.rai.booknician.model.recyclerview_item;

public class Authorwisebooknamepage extends AppCompatActivity implements View.OnClickListener {

    RecyclerView authorwise_bookname_recycler_view;

    ImageView home_button, order_button, profile_button;

    public static int  authorwise_book_position;

    DatabaseReference mdatabaseReference;
    FirebaseRecyclerOptions<recyclerview_item> options;
    FirebaseRecyclerAdapter<recyclerview_item, item_view_holder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorwise_bookname_page);


        home_button=findViewById(R.id.home_button);
        order_button=findViewById(R.id.order_button);
        profile_button=findViewById(R.id.profile_button);

        home_button.setOnClickListener(this);
        profile_button.setOnClickListener(this);
        order_button.setOnClickListener(this);



        authorwise_bookname_recycler_view=findViewById(R.id.authorwise_bookname_recycler_view);

        authorwise_bookname_recycler_view.hasFixedSize();

        mdatabaseReference= FirebaseDatabase.getInstance().getReference().child("Book author wise").child(Authorpage.authorname_clicked_name);

        options = new FirebaseRecyclerOptions.Builder<recyclerview_item>().setQuery(mdatabaseReference,recyclerview_item.class).build();


        adapter= new FirebaseRecyclerAdapter<recyclerview_item, item_view_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull item_view_holder holder, final int position, @NonNull recyclerview_item model) {



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

                       authorwise_book_position = position;

                        Intent intent= new Intent(Authorwisebooknamepage.this, Authorwisebookorderpage.class);
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
        authorwise_bookname_recycler_view.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        authorwise_bookname_recycler_view.setAdapter(adapter);




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
        switch (v.getId()) {

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

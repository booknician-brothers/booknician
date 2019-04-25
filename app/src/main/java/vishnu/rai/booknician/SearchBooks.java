package vishnu.rai.booknician;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import vishnu.rai.booknician.model.SearchAdapter;

public class SearchBooks extends AppCompatActivity {

    EditText searchtype;
    RecyclerView searchrecyclerview;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    ArrayList<String> booknamelist;
    ArrayList<String> authornamelist;
    ArrayList<String> imagelist;

    SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_books);

        searchtype=findViewById(R.id.searchtype);
        searchrecyclerview=findViewById(R.id.searchrecyclerview);

        databaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        searchrecyclerview.setHasFixedSize(true);
        searchrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        searchrecyclerview.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        booknamelist=new ArrayList<>();
        authornamelist=new ArrayList<>();
        imagelist=new ArrayList<>();

        searchtype.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty()){
                    setAdapter(s.toString());
                }else {
                    booknamelist.clear();
                    authornamelist.clear();
                    imagelist.clear();
                    searchrecyclerview.removeAllViews();
                }

            }
        });
    }

    private void setAdapter(final String searchedString) {

        databaseReference.child("Books").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                booknamelist.clear();
                authornamelist.clear();
                imagelist.clear();
                searchrecyclerview.removeAllViews();

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String nme=snapshot.getKey();
                    String name=snapshot.child("name").getValue(String.class);
                    String Author=snapshot.child("Author").getValue(String.class);
                    String image=snapshot.child("image").getValue(String.class);

                    if(name.toLowerCase().contains(searchedString.toLowerCase()))
                    {
                        booknamelist.add(name);
                        authornamelist.add(Author);
                        imagelist.add(image);
                    }
                    else if(Author.toLowerCase().contains(searchedString.toLowerCase()))
                    {
                        booknamelist.add(name);
                        authornamelist.add(Author);
                        imagelist.add(image);
                    }
                }

                searchAdapter=new SearchAdapter(SearchBooks.this,booknamelist,authornamelist,imagelist);
                searchrecyclerview.setAdapter(searchAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

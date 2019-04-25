package vishnu.rai.booknician.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vishnu.rai.booknician.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    Context context;
    ArrayList<String> booknamelist;
    ArrayList<String> authornamelist;
    ArrayList<String> imagelist;

    class SearchViewHolder extends RecyclerView.ViewHolder{
        ImageView bookimage;
        TextView bookname;
        TextView authorname;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            bookimage=(ImageView) itemView.findViewById(R.id.bookimage);
            bookname=(TextView) itemView.findViewById(R.id.bookname);
            authorname=(TextView) itemView.findViewById(R.id.authorname);

        }
    }

    public SearchAdapter(Context context, ArrayList<String> booknamelist, ArrayList<String> authornamelist, ArrayList<String> imagelist) {
        this.context = context;
        this.booknamelist = booknamelist;
        this.authornamelist = authornamelist;
        this.imagelist = imagelist;
    }

    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.search_list_items,parent,false);
        return new SearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.bookname.setText(booknamelist.get(position));
        holder.authorname.setText(authornamelist.get(position));

        Picasso.with(context).load(imagelist.get(position)).into(holder.bookimage);


        }

    @Override
    public int getItemCount() {
        return booknamelist.size();
    }
}

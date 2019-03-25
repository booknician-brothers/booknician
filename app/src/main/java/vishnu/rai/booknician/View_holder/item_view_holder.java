package vishnu.rai.booknician.View_holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import vishnu.rai.booknician.R;


public class item_view_holder extends RecyclerView.ViewHolder {

    public  TextView book_name , author_name;
    public  ImageView book_image;


    public item_view_holder(@NonNull View itemView) {
        super(itemView);

        book_name=itemView.findViewById(R.id.book_name_iv);
        book_image= itemView.findViewById(R.id.book_image_iv);
        author_name= itemView.findViewById(R.id.author_name_tv);
    }
}

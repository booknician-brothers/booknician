package vishnu.rai.booknician.View_holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import vishnu.rai.booknician.R;

public class orderpage_item_view_holder extends RecyclerView.ViewHolder{

    public TextView op_book_name, order_date;
    public ImageView op_book_image;

    public orderpage_item_view_holder(@NonNull View itemView) {
        super(itemView);

        op_book_name=itemView.findViewById(R.id.op_book_name);
        order_date=itemView.findViewById(R.id.order_date);
        op_book_image=itemView.findViewById(R.id.op_book_image);

    }
}

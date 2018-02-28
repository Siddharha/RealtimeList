package in.creativelizard.realtimelist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by siddhartha on 23/2/18.
 */

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.Viewholder>   {

    private ArrayList<ListItem> arrayList;
    private int itemLayout;

    public ItemListAdapter(ArrayList<ListItem> arrayList, int itemLayout) {
        this.arrayList = arrayList;
        this.itemLayout = itemLayout;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        holder.tvContent.setText(arrayList.get(position).getContent());

        if(arrayList.get(position).isImportent()) {
            holder.imgImp.setVisibility(View.VISIBLE);
        }else {
            holder.imgImp.setVisibility(View.GONE);
        }

        holder.tvdtm.setText(arrayList.get(position).getDatetime());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {
        private TextView tvContent,tvdtm;
        private ImageView imgImp;
        public Viewholder(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvdtm = itemView.findViewById(R.id.tvdtm);
            imgImp = itemView.findViewById(R.id.imgImp);
        }
    }
}


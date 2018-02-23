package in.creativelizard.realtimelist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
            holder.tvIsImp.setText("Important");
            holder.tvIsImp.setVisibility(View.VISIBLE);
        }else {
            holder.tvIsImp.setText("");
            holder.tvIsImp.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {
        private TextView tvContent,tvIsImp;
        public Viewholder(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvIsImp = itemView.findViewById(R.id.tvIsImp);
        }
    }
}


package com.example.shoppinglist.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.DB.Category;
import com.example.shoppinglist.DB.Items;
import com.example.shoppinglist.R;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {
    private Context context;
    private List<Items> itemsList;
    private HandleItemClick clickListener;

    public ItemListAdapter(Context context, HandleItemClick clickListener){
        this.context=context;
        this.clickListener=clickListener;
    }

    public void setItemsList(List<Items> itemsList){
        this.itemsList=itemsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recyclerview_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListAdapter.ViewHolder holder, int position) {
        holder.textviewItemName.setText(this.itemsList.get(position).itemName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.itemClick(itemsList.get(position));
            }
        });
        holder.removeCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.removeItemClick(itemsList.get(position));
            }
        });

        holder.editCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.editItemClick(itemsList.get(position));
            }
        });
        if(this.itemsList.get(position).completed){
            holder.textviewItemName.setPaintFlags(holder.textviewItemName.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else{
            holder.textviewItemName.setPaintFlags(0);

        }
    }

    @Override
    public int getItemCount() {
        if(itemsList==null || itemsList.size()==0)
        return 0;
        else
        return itemsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textviewItemName;
        ImageView removeCategory;
        ImageView editCategory;
        public ViewHolder(View view){
            super(view);
            textviewItemName=view.findViewById(R.id.textviewCategoryName);
            removeCategory=view.findViewById(R.id.removeCategory);
            editCategory=view.findViewById(R.id.editCategory);
        }
    }
    public interface HandleItemClick{
        void itemClick(Items items);
        void removeItemClick(Items items);
        void editItemClick(Items items);
    }
}

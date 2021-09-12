package com.example.shoppinglist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.DB.Category;
import com.example.shoppinglist.R;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {
    private Context context;
    private List<Category> categoryList;
    private HandleCategoryClick clickListener;

    public CategoryListAdapter(Context context,HandleCategoryClick clickListener){
        this.context=context;
        this.clickListener=clickListener;
    }

    public void setCategoryList(List<Category> categoryList){
        this.categoryList=categoryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recyclerview_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListAdapter.ViewHolder holder, int position) {
        holder.textviewCategoryName.setText(this.categoryList.get(position).categoryName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.itemClick(categoryList.get(position));
            }
        });
        holder.removeCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.removeItemClick(categoryList.get(position));
            }
        });

        holder.editCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.editItemClick(categoryList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(categoryList==null || categoryList.size()==0)
        return 0;
        else
        return categoryList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textviewCategoryName;
        ImageView removeCategory;
        ImageView editCategory;
        public ViewHolder(View view){
            super(view);
            textviewCategoryName=view.findViewById(R.id.textviewCategoryName);
            removeCategory=view.findViewById(R.id.removeCategory);
            editCategory=view.findViewById(R.id.editCategory);
        }
    }
    public interface HandleCategoryClick{
        void itemClick(Category category);
        void removeItemClick(Category category);
        void editItemClick(Category category);
    }
}

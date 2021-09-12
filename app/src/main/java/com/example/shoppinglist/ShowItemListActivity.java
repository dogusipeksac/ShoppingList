package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppinglist.Adapter.ItemListAdapter;
import com.example.shoppinglist.DB.Items;
import com.example.shoppinglist.ViewModel.ShowItemListActivityViewModel;

import java.util.List;

public class ShowItemListActivity extends AppCompatActivity implements ItemListAdapter.HandleItemClick {
    private int category_id;
    private String category_name;
    private EditText addNewItemInput;
    private ImageView save_button;
    private RecyclerView recyclerView;
    private TextView noResultItem;
    private ItemListAdapter itemListAdapter;
    private ShowItemListActivityViewModel viewModel;
    private Items itemForUpdate=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item_list);
        category_id=getIntent().getIntExtra("category_id",0);
        category_name=getIntent().getStringExtra("category_name");
        getSupportActionBar().setTitle(category_name);
        getSupportActionBar(). setDisplayHomeAsUpEnabled(true);
        addNewItemInput=findViewById(R.id.addNewItemInput);
        save_button=findViewById(R.id.save_button);
        noResultItem=findViewById(R.id.noResultItem);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName=addNewItemInput.getText().toString();
                if (TextUtils.isEmpty(itemName)){
                    Toast.makeText(ShowItemListActivity.this, "Enter item name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (itemForUpdate==null){
                    saveNewItem(itemName);
                }
                else{
                    updateNewItem(itemName);
                }


            }
        });
        initRecyclerView();
        initViewModel();
        viewModel.getAllItemsList(category_id);

    }



    private void initViewModel(){
        viewModel=new ViewModelProvider(this).get(ShowItemListActivityViewModel.class);
        viewModel.getItemsListObserver().observe(this, new Observer<List<Items>>() {
            @Override
            public void onChanged(List<Items> items) {
                if(items==null){
                    noResultItem.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                else{
                    noResultItem.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    itemListAdapter.setItemsList(items);
                }
            }
        });
    }
    private void initRecyclerView(){
        recyclerView=findViewById(R.id.recyclerView_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemListAdapter=new ItemListAdapter(this,this);
        recyclerView.setAdapter(itemListAdapter);
    }

    private void saveNewItem(String itemName){
        Items item=new Items();
        item.itemName=itemName;
        item.categoryId=category_id;
        viewModel.insertItem(item);
        addNewItemInput.setText("");

    }

    @Override
    public void itemClick(Items item) {
        if (item.completed){
            item.completed=false;
        }
        else {
            item.completed=true;
        }
        viewModel.updateItem(item);
    }

    @Override
    public void removeItemClick(Items item) {
        viewModel.deleteItem(item);
    }

    @Override
    public void editItemClick(Items item) {
        this.itemForUpdate=item;
        addNewItemInput.setText(item.itemName);
        viewModel.updateItem(itemForUpdate);
    }
    private void updateNewItem(String newName) {
        itemForUpdate.itemName=newName;
        viewModel.updateItem(itemForUpdate);
        addNewItemInput.setText("");
        itemForUpdate=null;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
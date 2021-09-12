package com.example.shoppinglist.ViewModel;

import android.app.Application;
import android.content.ClipData;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.shoppinglist.DB.AppDatabase;
import com.example.shoppinglist.DB.Category;
import com.example.shoppinglist.DB.Items;

import java.util.List;

public class ShowItemListActivityViewModel extends AndroidViewModel {
    private MutableLiveData<List<Items>> listOfItems;
    private AppDatabase appDatabase;


    public ShowItemListActivityViewModel(Application application){
        super(application);
        listOfItems=new MutableLiveData<>();
        appDatabase=AppDatabase.getInstance(getApplication().getApplicationContext());

    }
    public MutableLiveData<List<Items>> getItemsListObserver(){ return listOfItems; }

    public void getAllItemsList(int category_id){
        List<Items> itemsList=appDatabase.shoppingListDao().getAllItemList(category_id);
        if(itemsList.size()>0){
            listOfItems.postValue(itemsList);
        }
        else{
            listOfItems.postValue(null);
        }
    }
    public void insertItem(Items item){
        appDatabase.shoppingListDao().insertItems(item);
        getAllItemsList(item.categoryId);
    }
    public void updateItem(Items item){
        appDatabase.shoppingListDao().updateItems(item);
        getAllItemsList(item.categoryId);
    }
    public void deleteItem(Items item){
        appDatabase.shoppingListDao().deleteItems(item);
        getAllItemsList(item.categoryId);
    }
}

package com.h2m.carassistance;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

public class CarItemsViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = CarItemsViewModel.class.getSimpleName();

    private LiveData<List<CarItemsEntry>> list;
    AppDatabase database;

    public CarItemsViewModel(@NonNull Application application) {
        super(application);

        database = AppDatabase.getsInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");

    }

    public LiveData<List<CarItemsEntry>> getCarItems(int id) {
        list = database.carItemsDAO().loadAllCarItems(Integer.toString(id));
        return list;
    }

//    public List<CarItemsEntry> getItems(int id){
//        AppDatabase database = AppDatabase.getsInstance(this.getApplication());
//        List<CarItemsEntry> mList = database.carItemsDAO().loadCarItem(Integer.toString(id));
//        return mList;
//    }
}

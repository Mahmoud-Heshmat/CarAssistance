package com.h2m.carassistance;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

public class CarsViewModel extends AndroidViewModel{

    // Constant for logging
    private static final String TAG = CarsViewModel.class.getSimpleName();

    private LiveData<List<CarEntry>> list;


    public CarsViewModel(@NonNull Application application) {
        super(application);

        AppDatabase database = AppDatabase.getsInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        list = database.carDAO().loadAllCars();
    }

    public LiveData<List<CarEntry>> getCars() {
        return list;
    }

}

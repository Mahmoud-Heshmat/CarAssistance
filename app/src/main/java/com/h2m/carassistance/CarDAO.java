package com.h2m.carassistance;



import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CarDAO {

    @Query("SELECT * FROM Car ORDER BY id DESC")
    LiveData<List<CarEntry>> loadAllCars();

    @Query("SELECT * FROM Car ORDER BY id DESC")
    List<CarEntry> loadCars();

    @Insert
    void insertCar(CarEntry carEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCar(CarEntry carEntry);

    @Delete
    void deleteCar(CarEntry carEntry);

}

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
public interface CarItemsDAO {

    @Query("SELECT * FROM CarItems WHERE carId = :carItemID ORDER BY id DESC")
    LiveData<List<CarItemsEntry>> loadAllCarItems(String carItemID);

    @Query("SELECT * FROM CarItems WHERE carId = :carItemID ORDER BY id DESC")
    List<CarItemsEntry> loadCarItem(String carItemID);

    @Insert
    void insertCarItem(CarItemsEntry itemsEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCarItem(CarItemsEntry itemsEntry);

    @Delete
    void deleteCarItem(CarItemsEntry itemsEntry);


//    @Query("SELECT * FROM CarItems ORDER BY id DESC")
//    LiveData<List<CarItemsEntry>> loadAllCarItems();

}

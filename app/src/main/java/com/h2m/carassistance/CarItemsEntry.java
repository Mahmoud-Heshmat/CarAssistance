package com.h2m.carassistance;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "CarItems")
public class CarItemsEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    public int carId;
    public String itemName;
    public String cKilo;
    public String addedDate;


    @Ignore
    public CarItemsEntry(int carId, String itemName, String addedDate, String cKilo){
        this.carId = carId;
        this.itemName = itemName;
        this.addedDate = addedDate;
        this.cKilo = cKilo;
    }

    public CarItemsEntry(int id, int carId, String itemName, String addedDate, String cKilo){
        this.id = id;
        this.carId = carId;
        this.itemName = itemName;
        this.addedDate = addedDate;
        this.cKilo = cKilo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getcKilo() {
        return cKilo;
    }

    public void setcKilo(String cKilo) {
        this.cKilo = cKilo;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }
}

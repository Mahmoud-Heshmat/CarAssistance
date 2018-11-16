package com.h2m.carassistance;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Car")
public class CarEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    public String cName;
    public String cModel;
    public String cYear;
    public String cKilo;
    @ColumnInfo(name = "addedDate")
    public Date addedDate;

    @Ignore
    public CarEntry(String cName, String cModel, String cYear, Date addedDate, String cKilo){

        this.cName = cName;
        this.cModel = cModel;
        this.cYear = cYear;
        this.addedDate = addedDate;
        this.cKilo = cKilo;
    }

    public CarEntry(int id, String cName, String cModel, String cYear, Date addedDate, String cKilo){
        this.id = id;
        this.cName = cName;
        this.cModel = cModel;
        this.cYear = cYear;
        this.addedDate = addedDate;
        this.cKilo = cKilo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return cName;
    }

    public void setName(String cName) {
        this.cName = cName;
    }

    public String getModel() {
        return cModel;
    }

    public void setModel(String cModel) {
        this.cModel = cModel;
    }

    public String getYear() {
        return cYear;
    }

    public void setYear(String cYear) {
        this.cYear = cYear;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public String getcKilo() {
        return cKilo;
    }

    public void setcKilo(String cKilo) {
        this.cKilo = cKilo;
    }
}

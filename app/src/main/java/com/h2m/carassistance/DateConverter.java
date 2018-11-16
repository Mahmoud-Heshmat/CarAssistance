package com.h2m.carassistance;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;


public class DateConverter {

    //Used when convert read database
    @TypeConverter
    public static Date toDate(Long timestamp){
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date){
        return date == null ? null : date.getTime();
    }

}

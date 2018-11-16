package com.h2m.carassistance;

import android.content.Context;
import android.content.SharedPreferences;


import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;


public class Prefs {
    public static final String PREFS_NAME = "prefs";


    public static void saveRecipe(Context context, List<CarItemsEntry> items) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(items);
        prefs.putString(context.getString(R.string.widget_key), json);
        prefs.putString(context.getString(R.string.car_name), json);
        prefs.apply();
    }

    public static List<CarItemsEntry> loadRecipe(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(context.getString(R.string.widget_key), "");
        //List<CarItemsEntry> items = gson.fromJson(json, CarItemsEntry.class);
        List<CarItemsEntry> items = gson.fromJson(json, new TypeToken<List<CarItemsEntry>>() {
        }.getType());
        return items;
    }


}

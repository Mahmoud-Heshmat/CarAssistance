package com.h2m.carassistance;


import android.content.Context;
import android.content.SharedPreferences;
import com.h2m.carassistance.Utitls.*;

public class sharedPreferences {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    public static final String user_data_preference = "user_data" ;
    private static Context context;

    private static SharedPreferences sharedPreferencesCar;
    private static SharedPreferences.Editor editorCar;
    public static final String Car_preference = "car" ;


    public sharedPreferences(Context context){
        this.context = context;
    }


    public void user_data(String id, String name, String email, String phone){
        sharedPreferences = context.getSharedPreferences(user_data_preference, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit().clear();

        editor.putString(Constant.user_id, id);
        editor.putString(Constant.phone, phone);
        editor.putString(Constant.username, name);
        editor.putString(Constant.email, email);
        editor.apply();
    }

    public void user_data(String name, String email, String phone){
        sharedPreferences = context.getSharedPreferences(user_data_preference, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit().clear();

        editor.putString(Constant.phone, phone);
        editor.putString(Constant.username, name);
        editor.putString(Constant.email, email);
        editor.apply();
    }

    public void setSelectedCar(String carName, int carId){
        sharedPreferencesCar = context.getSharedPreferences(Car_preference, Context.MODE_PRIVATE);
        editorCar = sharedPreferencesCar.edit().clear();
        editorCar.putInt(Constant.cardId, carId);
        editorCar.putString(Constant.carName, carName);
        editorCar.apply();
    }


    public static String get_user_id(Context context){
        sharedPreferences = context.getSharedPreferences(user_data_preference, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.user_id, "0");
    }

    public static Integer get_car_id(Context context){
        sharedPreferencesCar = context.getSharedPreferences(Car_preference, Context.MODE_PRIVATE);
        return sharedPreferencesCar.getInt(Constant.cardId, -1);
    }

    public static String get_car_name(Context context){
        sharedPreferencesCar = context.getSharedPreferences(Car_preference, Context.MODE_PRIVATE);
        return sharedPreferencesCar.getString(Constant.carName, " ");
    }

    public static String get_user_name(Context context){
        sharedPreferences = context.getSharedPreferences(user_data_preference, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.username, "0");
    }

    public static String get_user_email(Context context){
        sharedPreferences = context.getSharedPreferences(user_data_preference, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.email, "0");
    }

    public static String get_user_phone(Context context){
        sharedPreferences = context.getSharedPreferences(user_data_preference, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.phone, "0");
    }

    public static void log_out(Context context){
        sharedPreferences = context.getSharedPreferences(user_data_preference, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }


}

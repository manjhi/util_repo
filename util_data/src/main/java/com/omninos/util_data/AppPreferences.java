package com.omninos.util_data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class AppPreferences {
    private static AppPreferences appPreference;
    private SharedPreferences sharedPreferences;

    private AppPreferences(Context context, String DATABASE_NAME) {
        sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
    }

    public static AppPreferences init(Context context, String DATABASE_NAME) {
        if (appPreference == null) {
            appPreference = new AppPreferences(context, DATABASE_NAME);
        }
        return appPreference;
    }


    public void SaveString(String key, String value) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public String GetString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void Logout() {
        sharedPreferences.edit().clear().apply();
    }

    public void saveDetails(String key, Object registerModelClass) {
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, gson.toJson(registerModelClass));
        editor.apply();
    }

    public <T> T getDetails(String key, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(sharedPreferences.getString(key, ""), type);
    }
}

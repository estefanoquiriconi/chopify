package com.chopify.app.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.chopify.app.data.entities.Business;
import com.google.gson.Gson;

public class SessionManager {
        private static final String PREF_NAME = "ChopifyPrefs";
        private static final String KEY_BUSINESS = "BussinesLogged";
        private SharedPreferences prefs;
        private SharedPreferences.Editor editor;

        public SessionManager(Context context) {
            prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            editor = prefs.edit();
        }

        public void saveBusiness(Business business) {
            Gson gson = new Gson();
            String json = gson.toJson(business);
            editor.putString(KEY_BUSINESS, json);
            editor.apply();
        }

        public Business getBusiness() {
            String json = prefs.getString(KEY_BUSINESS, null);
            return json != null ? new Gson().fromJson(json, Business.class) : null;
        }

        public void deleteBusiness() {
            editor.remove(KEY_BUSINESS);
            editor.apply();
        }
    }



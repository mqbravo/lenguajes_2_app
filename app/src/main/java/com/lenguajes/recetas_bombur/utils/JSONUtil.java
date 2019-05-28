package com.lenguajes.recetas_bombur.utils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


public class JSONUtil {


    public static String jsonStringFromObject(Object object){

        Gson gson = new Gson();

        return gson.toJson(object);
    }

    public static JSONObject JSONObjectFromString(String jsonString){

        try {
            return new JSONObject(jsonString);

        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }

    }
}

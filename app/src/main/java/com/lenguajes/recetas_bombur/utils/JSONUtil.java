package com.lenguajes.recetas_bombur.utils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


public class JSONUtil {


    public static JSONObject JSONObjectFromObject(Object object){

        Gson gson = new Gson();

        String jsonString = gson.toJson(object);

        try {
            return new JSONObject(jsonString);

        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }

    }
}

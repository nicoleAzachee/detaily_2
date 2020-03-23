package sampledetaily.sample.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class InputUtils {

    public static int generateRandomIndex (int min, int max){
        int randomIndex = new Random().nextInt((max - min) + 1) + min;
        return randomIndex;
    }

    public static String checkFieldExists(String field, JSONObject jsonObject){
        String result = "";
        try{
            result = jsonObject.getString(field);
            return result;
        }catch(JSONException e){
            e.printStackTrace();
        }
        return result;
    }
}

package sampledetaily.sample.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class InputUtils {

    /**
     * Method to generate random number
     * @param min minimum number to randomize
     * @param max maximum number to randomize
     * @return result
     */
    public static int generateRandomIndex (int min, int max){
        int randomIndex = new Random().nextInt((max - min) + 1) + min;
        return randomIndex;
    }

    /**
     * Checks if field is valid or existing from json object
     * @param field field to check
     * @param jsonObject json to use
     * @return result if field is valid
     */
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

package sampledetaily.sample.utils;

import java.util.Random;

public class InputUtils {

    public static int generateRandomIndex (int min, int max){
        int randomIndex = new Random().nextInt((max - min) + 1) + min;
        return randomIndex;
    }

}

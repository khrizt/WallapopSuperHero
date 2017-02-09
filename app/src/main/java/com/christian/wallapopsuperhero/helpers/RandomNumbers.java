package com.christian.wallapopsuperhero.helpers;

import android.os.Build;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomNumbers {
    public static int getRandomNumber(int min, int max) {
        int randomNum = 0;

        //if (BuildConfig.)
        if (Build.VERSION.SDK_INT >= 21) {
            randomNum = ThreadLocalRandom.current().nextInt(min, max);
        } else {
            Random rand = new Random();
            randomNum = rand.nextInt((max - min) + 1) + min;
        }

        return randomNum;
    }
}

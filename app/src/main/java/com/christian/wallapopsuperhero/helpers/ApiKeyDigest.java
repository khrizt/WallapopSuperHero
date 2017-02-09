package com.christian.wallapopsuperhero.helpers;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by christian on 09/02/2017.
 */

public class ApiKeyDigest {
    public static String getDigest(long timestamp, String publicApiKey, String privateApiKey) {
        String data = timestamp + privateApiKey + publicApiKey;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(data.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String md5 = number.toString(16);

            while (md5.length() < 32)
                md5 = "0" + md5;

            return md5;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }

    }
}

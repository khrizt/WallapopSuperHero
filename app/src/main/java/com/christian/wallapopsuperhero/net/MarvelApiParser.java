package com.christian.wallapopsuperhero.net;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.christian.wallapopsuperhero.Settings;
import com.christian.wallapopsuperhero.helpers.ApiKeyDigest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class MarvelApiParser extends AsyncTask<Void, Void, Void> {

    private String baseUrl = "http://gateway.marvel.com";
    protected String apiMethod = "";
    protected String method = "GET";
    protected HashMap<String, String> params = new HashMap<>();
    protected JSONObject apiResponse;

    protected MarvelApiParser setApiMethod(String apiMethod) {
        this.apiMethod = apiMethod;

        return this;
    }

    protected MarvelApiParser setMethod(String method) {
        this.method = method;

        return this;
    }

    protected MarvelApiParser addParam(String key, String value) {
        params.put(key, value);

        return this;
    }

    private String getUri() {
        Uri.Builder builder = Uri.parse(baseUrl + apiMethod).buildUpon();

        long millis = System.currentTimeMillis() % 1000;
        String hash = ApiKeyDigest.getDigest(millis, Settings.MARVEL_PUBLIC_API_KEY, Settings.MARVEL_PRIVATE_API_KEY);
        builder.appendQueryParameter("ts", millis+"")
                .appendQueryParameter("apikey", Settings.MARVEL_PUBLIC_API_KEY)
                .appendQueryParameter("hash", hash);

        Iterator it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            builder.appendQueryParameter((String) pair.getKey(), (String) pair.getValue());
        }

        return builder.build().toString();
    }

    @Override
    protected Void doInBackground(Void... params) {

        Log.e("API", "Marvel URL: " + getUri());
        OkHttpClient client = getClient();
        Request request = new Request.Builder()
                .cacheControl(CacheControl.FORCE_NETWORK)
                .url(getUri())
                .build();

        Response responses = null;

        try {
            responses = client.newCall(request).execute();
            String jsonData = responses.body().string();
            Log.e("API", "Marvel API response: " + jsonData);
            apiResponse = new JSONObject(jsonData);
            responses.body().close();

            responses = null;
        } catch (IOException | JSONException e) {
            apiResponse = null;
        }

        return null;
    }

    protected OkHttpClient getClient() {
        return new OkHttpClient();
    }
}

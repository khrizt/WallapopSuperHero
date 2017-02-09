package com.christian.wallapopsuperhero.net;

import com.christian.wallapopsuperhero.data.Comic;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Async task to get new comics from the Marvel API.
 */
public class CapitainAmericaComicsParser extends MarvelApiParser {
    private CapitainAmericaComicsResults listener;
    private ArrayList<Comic> results = new ArrayList<>();
    private int totalItems = 0;

    /**
     * Constructor. Sets the API method call.
     */
    public CapitainAmericaComicsParser() {
        setApiMethod("/v1/public/characters/1009220/comics");
        params.put("limit", 20+"");
    }

    /**
     * Sets the listener where the response will be sent
     *
     * @param listener CapitainAmericaComicsResults
     *
     * @return this
     */
    public CapitainAmericaComicsParser setListener(CapitainAmericaComicsResults listener) {
        this.listener = listener;

        return this;
    }

    /**
     * Sets the offset to get new comics
     *
     * @param offset int
     *
     * @return this
     */
    public CapitainAmericaComicsParser setOffset(int offset) {
        params.put("offset", offset+"");

        return this;
    }

    @Override
    protected Void doInBackground(Void... params) {
        super.doInBackground(params);

        JSONArray comics;
        if (apiResponse == null || !apiResponse.has("data") || apiResponse.isNull("data")) {
            results = null;
            // no results
            return null;
        }
        try {
            if (!apiResponse.getJSONObject("data").has("results") ||
                    apiResponse.getJSONObject("data").isNull("results")) {
                // no results
                return null;
            }
            comics = apiResponse.getJSONObject("data").getJSONArray("results");

            for (int i = 0; i < comics.length(); i++) {
                Comic comic = Comic.parse(comics.getJSONObject(i));
                if (comic != null) {
                    results.add(comic);
                }
            }

            totalItems = apiResponse.getJSONObject("data").getInt("total");
        } catch (JSONException e) {
            return null;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (listener != null) {
            listener.loadResults(results, totalItems);
        }
    }

}


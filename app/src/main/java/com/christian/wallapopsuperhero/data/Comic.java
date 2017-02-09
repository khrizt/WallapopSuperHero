package com.christian.wallapopsuperhero.data;

import android.util.Log;

import com.christian.wallapopsuperhero.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Comic {

    private int id;
    private int digitalId;
    private String title;
    private String variantDescription;
    private String description;
    private String upcCode;
    private int pageCount;
    private Image thumbnail;
    private ArrayList<Image> images;
    private String url;

    public Comic() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDigitalId() {
        return digitalId;
    }

    public void setDigitalId(int digitalId) {
        this.digitalId = digitalId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVariantDescription() {
        return variantDescription;
    }

    public void setVariantDescription(String variantDescription) {
        this.variantDescription = variantDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpcCode() {
        return upcCode;
    }

    public void setUpcCode(String upcCode) {
        this.upcCode = upcCode;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public Image getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Image thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public static Comic parse(JSONObject object) {
        Comic comic = new Comic();

        try {
            if (object.has("title") && !object.isNull("title")) {
                comic.setTitle(object.getString("title"));
            }
            if (object.has("variantDescription") && !object.isNull("variantDescription")) {
                comic.setVariantDescription(object.getString("variantDescription"));
            }
            if (object.has("description") && !object.isNull("description")) {
                comic.setDescription(object.getString("description"));
            }
            if (object.has("upc") && !object.isNull("upc")) {
                comic.setUpcCode(object.getString("upc"));
            }
            if (object.has("id") && !object.isNull("id")) {
                comic.setId(object.getInt("id"));
            }
            if (object.has("digitalId") && !object.isNull("digitalId")) {
                comic.setDigitalId(object.getInt("digitalId"));
            }
            if (object.has("pageCount") && !object.isNull("pageCount")) {
                comic.setPageCount(object.getInt("pageCount"));
            }
            if (object.has("thumbnail") && !object.isNull("thumbnail")) {
                comic.setThumbnail(Image.parse(object.getJSONObject("thumbnail")));
            }
            comic.setImages(new ArrayList<Image>());
            if (object.has("images") && !object.isNull("images")) {
                JSONArray images = object.getJSONArray("images");
                for (int i = 0; i < images.length(); i++) {
                    try {
                        comic.getImages().add(Image.parse(images.getJSONObject(i)));
                    } catch (JSONException | NullPointerException e) {
                        if (BuildConfig.DEBUG) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if (object.has("urls") && !object.isNull("urls")) {
                JSONArray urls = object.getJSONArray("urls");
                for (int i = 0; i < urls.length(); i++) {
                    try {
                        if (urls.getJSONObject(i).getString("type").equals("detail")) {
                            comic.setUrl(urls.getJSONObject(i).getString("url"));
                        }
                    } catch (JSONException | NullPointerException e) {
                        if (BuildConfig.DEBUG) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (JSONException e) {
            comic = null;
        }

        return comic;
    }

}

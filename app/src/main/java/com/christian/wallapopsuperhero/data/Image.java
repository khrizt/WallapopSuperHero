package com.christian.wallapopsuperhero.data;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Image {
    private String path;
    private String extension;

    public Image() {

    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUri() {
        return path + "." + extension;
    }

    public static Image parse(JSONObject object) {
        Image image = new Image();

        try {
            if (object.has("path") && !object.isNull("path")) {
                image.setPath(object.getString("path"));
            }
            if (object.has("extension") && !object.isNull("extension")) {
                image.setExtension(object.getString("extension"));
            }
        } catch (JSONException e) {
            image = null;
        }

        return image;
    }
}

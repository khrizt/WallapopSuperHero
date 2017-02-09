package com.christian.wallapopsuperhero.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class ComicTest {
    private int id = 1234;
    private int digitalId = 1234;
    private String title = "Fake title by Stan Lee";
    private String variantDescription = "Fake variant description";
    private String description = "Fake description";
    private String upcCode = "123456789";
    private int pageCount = 23;
    private String imagePath = "image-path";
    private String imageExtension = "png";
    private String url = "fake://url";

    protected JSONObject getDefinitionJsonObject() {
        return Mockito.mock(JSONObject.class);
    }

    protected JSONArray getDefinitionJsonArray() {
        return Mockito.mock(JSONArray.class);
    }

    protected JSONObject getCorrectComicJsonObject() {
        JSONObject jsonObjectMock = getDefinitionJsonObject();
        try {
            when(jsonObjectMock.has("id")).thenReturn(true);
            when(jsonObjectMock.isNull("id")).thenReturn(false);
            when(jsonObjectMock.getInt("id")).thenReturn(id);
            when(jsonObjectMock.has("digitalId")).thenReturn(true);
            when(jsonObjectMock.isNull("digitalId")).thenReturn(false);
            when(jsonObjectMock.getInt("digitalId")).thenReturn(digitalId);
            when(jsonObjectMock.has("title")).thenReturn(true);
            when(jsonObjectMock.isNull("title")).thenReturn(false);
            when(jsonObjectMock.getString("title")).thenReturn(title);
            when(jsonObjectMock.has("variantDescription")).thenReturn(true);
            when(jsonObjectMock.isNull("variantDescription")).thenReturn(false);
            when(jsonObjectMock.getString("variantDescription")).thenReturn(variantDescription);
            when(jsonObjectMock.has("description")).thenReturn(true);
            when(jsonObjectMock.isNull("description")).thenReturn(false);
            when(jsonObjectMock.getString("description")).thenReturn(description);
            when(jsonObjectMock.has("upc")).thenReturn(true);
            when(jsonObjectMock.isNull("upc")).thenReturn(false);
            when(jsonObjectMock.getString("upc")).thenReturn(upcCode);
            when(jsonObjectMock.has("pageCount")).thenReturn(true);
            when(jsonObjectMock.isNull("pageCount")).thenReturn(false);
            when(jsonObjectMock.getInt("pageCount")).thenReturn(pageCount);

            JSONObject thumbnailJsonObject = getDefinitionJsonObject();
            when(thumbnailJsonObject.has("path")).thenReturn(true);
            when(thumbnailJsonObject.isNull("path")).thenReturn(false);
            when(thumbnailJsonObject.getString("path")).thenReturn(imagePath);
            when(thumbnailJsonObject.has("extension")).thenReturn(true);
            when(thumbnailJsonObject.isNull("extension")).thenReturn(false);
            when(thumbnailJsonObject.getString("extension")).thenReturn(imageExtension);
            when(jsonObjectMock.has("thumbnail")).thenReturn(true);
            when(jsonObjectMock.isNull("thumbnail")).thenReturn(false);
            when(jsonObjectMock.getJSONObject("thumbnail")).thenReturn(thumbnailJsonObject);

            JSONArray imagesJsonArray = getDefinitionJsonArray();
            when(imagesJsonArray.length()).thenReturn(2);
            JSONObject imageJsonObject = getDefinitionJsonObject();
            when(imageJsonObject.has("path")).thenReturn(true);
            when(imageJsonObject.isNull("path")).thenReturn(false);
            when(imageJsonObject.getString("path")).thenReturn(imagePath);
            when(imageJsonObject.has("extension")).thenReturn(true);
            when(imageJsonObject.isNull("extension")).thenReturn(false);
            when(imageJsonObject.getString("extension")).thenReturn(imageExtension);
            when(imagesJsonArray.getJSONObject(0)).thenReturn(imageJsonObject);
            when(imagesJsonArray.getJSONObject(1)).thenReturn(imageJsonObject);
            when(jsonObjectMock.has("images")).thenReturn(true);
            when(jsonObjectMock.isNull("images")).thenReturn(false);
            when(jsonObjectMock.getJSONArray("images")).thenReturn(imagesJsonArray);

            JSONArray urlsJsonArray = getDefinitionJsonArray();
            when(urlsJsonArray.length()).thenReturn(2);
            JSONObject urlJsonObject1 = getDefinitionJsonObject();
            when(urlJsonObject1.getString("type")).thenReturn("non_detail");
            JSONObject urlJsonObject2 = getDefinitionJsonObject();
            when(urlJsonObject2.getString("type")).thenReturn("detail");
            when(urlJsonObject2.getString("url")).thenReturn(url);
            when(urlsJsonArray.getJSONObject(0)).thenReturn(urlJsonObject1);
            when(urlsJsonArray.getJSONObject(1)).thenReturn(urlJsonObject2);
            when(jsonObjectMock.has("urls")).thenReturn(true);
            when(jsonObjectMock.isNull("urls")).thenReturn(false);
            when(jsonObjectMock.getJSONArray("urls")).thenReturn(urlsJsonArray);

        } catch (JSONException e) {
            assertFalse("Parse method throwed an unexpected exception: "+e.getMessage(), true);
        }

        return jsonObjectMock;
    }

    @Test
    public void testParseComicCorrect() {
        JSONObject object = getCorrectComicJsonObject();
        Comic comic = Comic.parse(object);

        assertEquals(id, comic.getId());
        assertEquals(digitalId, comic.getDigitalId());
        assertEquals(title, comic.getTitle());
        assertEquals(variantDescription, comic.getVariantDescription());
        assertEquals(description, comic.getDescription());
        assertEquals(upcCode, comic.getUpcCode());
        assertEquals(pageCount, comic.getPageCount());

        assertEquals(imagePath, comic.getThumbnail().getPath());
        assertEquals(imageExtension, comic.getThumbnail().getExtension());

        assertEquals(2, comic.getImages().size());
        assertEquals(imagePath, comic.getImages().get(0).getPath());
        assertEquals(imageExtension, comic.getImages().get(0).getExtension());
        assertEquals(imagePath, comic.getImages().get(1).getPath());
        assertEquals(imageExtension, comic.getImages().get(1).getExtension());

        assertEquals(url, comic.getUrl());
    }

}

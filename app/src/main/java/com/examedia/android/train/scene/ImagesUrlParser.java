package com.examedia.android.train.scene;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by shoma2da on 2014/10/19.
 */
public class ImagesUrlParser {

    public ArrayList<Uri> parse(String jsonText) throws JSONException {
        JSONObject json = new JSONObject(jsonText);

        final JSONArray urlList = json.getJSONArray("urlList");

        ArrayList<Uri> parsedList = new ArrayList<Uri>();
        for (int i = 0; i < urlList.length(); i++) {
            String urlString = urlList.getString(i);
            parsedList.add(Uri.parse(urlString));
        }

        return parsedList;
    }

}

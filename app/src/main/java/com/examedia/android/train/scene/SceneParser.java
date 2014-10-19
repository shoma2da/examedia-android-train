package com.examedia.android.train.scene;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by shoma2da on 2014/10/19.
 */
public class SceneParser {

    public void parse(String jsonText, final OnParseCallback callback) throws JSONException, IOException {
        JSONObject json = new JSONObject(jsonText);

        final JSONArray urlList = json.getJSONArray("urlList");

        //画像を非同期に読み込む
        new AsyncTask<Void, Void, Scene>() {

            @Override
            protected Scene doInBackground(Void... params) {
                try {
                    ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
                    for (int i = 0; i < urlList.length(); i++) {
                        String urlString = urlList.getString(i);

                        //HTTPリクエスト
                        InputStream inputStream = new URL(urlString).openStream();
                        bitmaps.add(BitmapFactory.decodeStream(inputStream));
                    }
                    return new Scene(bitmaps);
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Scene scene) {
                if (scene == null) {
                    callback.onError();
                } else {
                    callback.onParse(scene);
                }
            }
        }.execute();
    }

    public interface OnParseCallback {
        void onParse(Scene scene);
        void onError();
    }

}

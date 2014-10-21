package com.examedia.android.train.scene;

import android.net.Uri;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by shoma2da on 2014/10/21.
 */
public class ImagesUrlLoader {

    public static final String URL_BASE = "http://examedia-sample-train-picture.herokuapp.com/api/v1/pictures?from=%s&to=%s&division=%d";

    public void load(String depature, String arrival, final int time, int imageNumber, final OnLoadCallback callback) {
        //HTTPリクエスト
        String urlString = String.format(Locale.JAPAN, URL_BASE, depature, arrival, imageNumber);
        new AsyncHttpClient().get(urlString, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    ArrayList<Uri> uriList = new ImagesUrlParser().parse(new String(responseBody));
                    callback.onLoad(uriList);
                } catch (JSONException e) {
                    onFailure(0, null, null, e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("train", "status code is " + statusCode);
                Log.d("train", "headers are " + headers);
                Log.d("train", "responseBody is " + new String(responseBody));
                if (error != null) {
                    error.printStackTrace();
                }
                callback.onFailure();
            }
        });
    }

    /** 読み込み時用のコールバック */
    public interface  OnLoadCallback {
        public void onLoad(List<Uri> uriList);
        public void onFailure();
    }

}

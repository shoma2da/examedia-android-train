package com.examedia.android.train.scene;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by shoma2da on 2014/10/19.
 */
public class Scene {

    /** 景色を読み込む */
    public static void load(String depature, String arrival, int time, int imageNumber, final OnLoadSceneCallback callback) {
        //HTTPリクエスト
        String urlBase = "http://examedia-sample-train-picture.herokuapp.com/api/v1/pictures?from=%s&to=%s&division=%d";
        String url = String.format(Locale.JAPAN, urlBase, depature, arrival, imageNumber);
        new AsyncHttpClient().get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    new SceneParser().parse(new String(responseBody), new SceneParser.OnParseCallback() {
                        @Override
                        public void onParse(Scene scene) {
                            callback.onLoad(scene);
                        }
                        @Override
                        public void onError() {
                            onFailure(0, null, null, null);
                        }
                    });
                } catch (JSONException e) {
                    onFailure(0, null, null, e);
                    e.printStackTrace();
                } catch (IOException e) {
                    onFailure(0, null, null, e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                callback.onFailure();
            }
        });
    }

    private List<Bitmap> mBitmaps;

    Scene(List<Bitmap> bitmaps) { //パッケージ内からのみインスタンス化できる→テストできるようにprivateにはしない

    }

    /** 景色を再生する */
    public void play(Activity activity) {

    }

    /** 読み込み時用のコールバック */
    public interface  OnLoadSceneCallback {
        public void onLoad(Scene scene);
        public void onFailure();
    }

}

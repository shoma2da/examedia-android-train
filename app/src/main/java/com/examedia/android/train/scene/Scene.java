package com.examedia.android.train.scene;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

/**
 * Created by shoma2da on 2014/10/19.
 */
public class Scene {

    /** 景色を読み込む */
    public static void load(String depature, String arrival, int time, int imageNumber, final OnLoadSceneCallback callback) {
        //HTTPリクエスト
        String url = "http://examedia-sample-train-picture.herokuapp.com/api/v1/pictures?from=%E6%98%8E%E6%B2%BB%E7%A5%9E%E5%AE%AE%E5%89%8D&to=%E8%A1%A8%E5%8F%82%E9%81%93";
        new AsyncHttpClient().get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                callback.onLoad(new Scene());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                callback.onFailure();
            }
        });
    }

    Scene(/*画像オブジェクト、流す間隔*/) {} //パッケージ内からのみインスタンス化できる→テストできるようにprivateにはしない

    /** 景色を再生する */
    public void play(Activity activity) {

    }

    /** 読み込み時用のコールバック */
    public interface  OnLoadSceneCallback {
        public void onLoad(Scene scene);
        public void onFailure();
    }

}

package com.examedia.android.train.scene;

import android.app.Activity;

/**
 * Created by shoma2da on 2014/10/19.
 */
public class Scene {

    /** 景色を読み込む */
    public static void load(String depature, String arrival, int time, int imageNumber, OnLoadSceneCallback callback) {
        callback.onLoad(null);
    }

    Scene(/*画像オブジェクト、流す間隔*/) {} //パッケージ内からのみインスタンス化できる→テストできるようにprivateにはしない

    /** 景色を再生する */
    public void play(Activity activity) {

    }

    /** 読み込み時用のコールバックinterface */
    public static interface OnLoadSceneCallback {
        void onLoad(Scene scene);
    }

}

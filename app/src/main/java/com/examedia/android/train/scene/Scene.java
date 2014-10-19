package com.examedia.android.train.scene;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

/**
 * Created by shoma2da on 2014/10/19.
 */
public class Scene {

    /** 景色を読み込む */
    public static void load(String depature, String arrival, int time, int imageNumber, LoaderManager loaderManager, OnLoadSceneCallback callback) {
        //Loader側に渡すパラメータを準備
        Bundle bundle = new Bundle();
        bundle.putString(SceneInfoLoader.PARAM_DEPATURE, depature);
        bundle.putString(SceneInfoLoader.PARAM_ARRIVAL, arrival);
        bundle.putInt(SceneInfoLoader.PARAM_TIME, time);
        bundle.putInt(SceneInfoLoader.PARAM_IMAGE_NUMBER, imageNumber);

        //非同期読み込みの開始
        loaderManager.initLoader(0, bundle, callback);
    }

    Scene(/*画像オブジェクト、流す間隔*/) {} //パッケージ内からのみインスタンス化できる→テストできるようにprivateにはしない

    /** 景色を再生する */
    public void play(Activity activity) {

    }

    /** 読み込み時用のコールバック */
    public static abstract class OnLoadSceneCallback implements LoaderManager.LoaderCallbacks<Scene> {

        private Context mContext;

        public OnLoadSceneCallback(Context context) {
            mContext = context;
        }

        public abstract void onLoad(Scene scene);

        @Override
        public Loader<Scene> onCreateLoader(int id, Bundle args) {
            SceneInfoLoader sceneInfoLoader = new SceneInfoLoader(mContext, args);
            sceneInfoLoader.forceLoad();
            return sceneInfoLoader;
        }

        @Override public void onLoaderReset(Loader<Scene> loader) { /*nothing*/ }

        @Override
        public void onLoadFinished(Loader<Scene> loader, Scene scene) {
            onLoad(scene);
        }
    }

}

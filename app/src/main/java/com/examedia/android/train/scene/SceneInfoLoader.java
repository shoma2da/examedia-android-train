package com.examedia.android.train.scene;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by shoma2da on 2014/10/19.
 */
public class SceneInfoLoader extends AsyncTaskLoader<Scene> {

    public static final String PARAM_DEPATURE = "param_depature";
    public static final String PARAM_ARRIVAL = "param_arrival";
    public static final String PARAM_TIME = "param_time"; //FIXME ほんとはAPIから取れる？
    public static final String PARAM_IMAGE_NUMBER = "param_image_number"; //FIXME ほんとはAPIが良い感じの枚数を返す？

    private Bundle mArgs;

    public SceneInfoLoader(Context context, Bundle args) {
        super(context);
        mArgs = args;
    }

    @Override
    public Scene loadInBackground() {
        String depature = mArgs.getString(PARAM_DEPATURE);
        String arrival = mArgs.getString(PARAM_ARRIVAL);
        int time = mArgs.getInt(PARAM_TIME, 60);
        int imageNumber = mArgs.getInt(PARAM_IMAGE_NUMBER, 60);

        Log.d("debug", "***" + depature);
        Log.d("debug", "***" + arrival);
        Log.d("debug", "***" + time);
        Log.d("debug", "***" + imageNumber);

        return new Scene();
    }

}

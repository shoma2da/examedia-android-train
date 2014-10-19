package com.examedia.android.train.scene;

import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * Created by shoma2da on 2014/10/19.
 */
public class SceneInfoLoader extends AsyncTaskLoader<Scene> {

    public SceneInfoLoader(Context context) {
        super(context);
    }

    @Override
    public Scene loadInBackground() {
        return new Scene();
    }

}

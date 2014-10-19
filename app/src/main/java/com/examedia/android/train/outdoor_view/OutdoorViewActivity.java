package com.examedia.android.train.outdoor_view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.examedia.android.train.R;
import com.examedia.android.train.scene.Scene;

/**
 * Created by shoma2da on 2014/10/19.
 */
public class OutdoorViewActivity extends Activity {

    public static final String PARAM_DEPATURE = "param_depature";
    public static final String PARAM_ARRIVAL = "param_arrival";
    public static final String PARAM_TIME = "param_time"; //FIXME ほんとはAPIから取れる？
    public static final String PARAM_IMAGE_NUMBER = "param_image_number"; //FIXME ほんとはAPIが良い感じの枚数を返す？

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdoorview);

        String depature = getIntent().getStringExtra(PARAM_DEPATURE);
        String arrival = getIntent().getStringExtra(PARAM_ARRIVAL);
        int time = getIntent().getIntExtra(PARAM_TIME, 60);
        int imageNumber = getIntent().getIntExtra(PARAM_IMAGE_NUMBER, 60);

        ((TextView)findViewById(R.id.text_description)).setText(
            String.format("%sから%sまでの景色を\n%d秒の長さ、%d枚の画像で\nお楽しみください...",
            depature, arrival, time, imageNumber)
        );

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("読み込み中...");
        progressDialog.show();
        Scene.load(depature, arrival, time, imageNumber, new Scene.OnLoadSceneCallback() {
            @Override
            public void onLoad(Scene scene) {
                progressDialog.dismiss();
                scene.play(OutdoorViewActivity.this);
            }

            @Override
            public void onFailure() {
                progressDialog.dismiss();
                Toast.makeText(OutdoorViewActivity.this, "エラーです", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

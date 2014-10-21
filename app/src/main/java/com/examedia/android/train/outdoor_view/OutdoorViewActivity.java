package com.examedia.android.train.outdoor_view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.examedia.android.train.R;
import com.examedia.android.train.scene.ImagesUrlLoader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

        //どの画像を取得したらいいかを問い合わせる
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("読み込み中...");
        progressDialog.show();
        new ImagesUrlLoader().load(depature, arrival, time, imageNumber, new ImagesUrlLoader.OnLoadCallback() {
            @Override
            public void onLoad(List<Uri> uriList) {
                final Iterator<Uri> uriIterator = uriList.iterator();

                //最初の１枚を先に取得しておいてから画像の再生に入る
                new AsyncTask<Void, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(Void... params) {
                        try {
                            return BitmapFactory.decodeStream(new URL(uriIterator.next().toString()).openStream());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);
                        progressDialog.dismiss();
                        if (bitmap != null) {
                            play(uriIterator, bitmap);
                        }
                    }
                }.execute();
            }

            @Override
            public void onFailure() {
                progressDialog.dismiss();
                Toast.makeText(OutdoorViewActivity.this, "エラーが発生しました", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 画像を読み込んで表示する
     */
    private void play(final Iterator<Uri> iterator, final Bitmap firstBitmap) {
        final Handler handler = new Handler();
        final ImageView imageView = (ImageView)findViewById(R.id.imageView);

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            private Bitmap nextBitmap = firstBitmap;
            private int count = 0;

            @Override
            public void run() {
                Log.d("train", "start timer task : " + count++);

                //画像を切替える
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(nextBitmap);
                    }
                });

                //次々回の画像を取得しておく
                new AsyncTask<Void, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(Void... params) {
                        try {
                            if (iterator.hasNext() == false) {
                                return null;
                            }
                            return BitmapFactory.decodeStream(new URL(iterator.next().toString()).openStream());
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                            return null;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);
                        if (bitmap == null) {
                            timer.cancel();
                            return;
                        }
                        nextBitmap = bitmap;
                    }
                }.execute();
            }
        }, 0, 1000);
    }
}

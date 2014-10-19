package com.examedia.android.train;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.examedia.android.train.outdoor_view.OutdoorViewActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ボタンを押した時の動作を指定
        findViewById(R.id.button_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //テキストの内容を取得
                String depature = ((TextView)findViewById(R.id.text_depature)).getText().toString();
                String arrival = ((TextView)findViewById(R.id.text_arrival)).getText().toString();
                int time = Integer.valueOf(((TextView) findViewById(R.id.text_time)).getText().toString());
                int imageNumber = Integer.valueOf(((TextView)findViewById(R.id.text_image_number)).getText().toString());

                //画面遷移に向けたIntentを生成
                Intent intent = new Intent(MainActivity.this, OutdoorViewActivity.class);
                intent.putExtra(OutdoorViewActivity.PARAM_ARRIVAL, arrival);
                intent.putExtra(OutdoorViewActivity.PARAM_DEPATURE, depature);
                intent.putExtra(OutdoorViewActivity.PARAM_IMAGE_NUMBER, imageNumber);
                intent.putExtra(OutdoorViewActivity.PARAM_TIME, time);

                //画面遷移する
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.dragon.myreadhub.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import com.dragon.myreadhub.R;

public class SplashActivity extends BaseCompatActivity
{

    private Handler handler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

            handler.removeMessages(-1);
        };

    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler.sendMessageDelayed(handler.obtainMessage(-1), 3000);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        if (event.getAction() == MotionEvent.ACTION_UP)
        {
            handler.sendMessage(handler.obtainMessage(-1));
            finish();
        }

        return super.onTouchEvent(event);
    }
}

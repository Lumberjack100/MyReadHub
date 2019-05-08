package com.dragon.myreadhub.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.dragon.myreadhub.R;

public class LayoutTestActivity extends AppCompatActivity
{

    public static void startActivity(Context context)
    {
        Intent intent = new Intent(context, LayoutTestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
    }
}

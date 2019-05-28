package com.dragon.myreadhub.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
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

        Log.d("MyDensity", "Before: \n\n" );
        getDensity();

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        displayMetrics.density=7;
        displayMetrics.densityDpi=7*160;

        Log.d("MyDensity", "After: \n\n" );
        getDensity();

    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void getDensity()
    {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        Log.d("MyDensity", "Density is " + displayMetrics.density + " densityDpi is " + displayMetrics.densityDpi + " height: " + displayMetrics.heightPixels + " width: " + displayMetrics.widthPixels);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(point);
        double x = Math.pow(point.x / displayMetrics.xdpi, 2);
        double y = Math.pow(point.y / displayMetrics.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        Log.d("MyDensity", "Screen inches : " + screenInches);
    }
}

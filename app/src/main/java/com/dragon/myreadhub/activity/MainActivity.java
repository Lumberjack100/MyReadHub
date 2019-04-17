package com.dragon.myreadhub.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dragon.myreadhub.R;
import com.dragon.myreadhub.fragment.MyFragmentPageAdapter;
import com.dragon.myreadhub.fragment.NewsFragment;
import com.dragon.myreadhub.fragment.ThirdFragment;
import com.dragon.myreadhub.fragment.WeatherFragment;
import com.dragon.myreadhub.Interfaces.Extras;

import java.util.LinkedHashMap;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener
{

    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private MyFragmentPageAdapter pagerAdapter;

    private Fragment fragmentNews, fragmentWeather, fragmentThird;

    private LinkedHashMap<String, Fragment> titleFragmentMap = new LinkedHashMap<>();


    public static void startActivity(Context context, int currentItem)
    {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Extras.EXTRA_DATA, currentItem);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    private void initView()
    {
        fragmentNews = new NewsFragment();
        fragmentWeather = new WeatherFragment();
        fragmentThird = new ThirdFragment();

        titleFragmentMap.put("资讯", fragmentNews);
        titleFragmentMap.put("天气", fragmentWeather);
        titleFragmentMap.put("其他", fragmentThird);

        pagerAdapter = new MyFragmentPageAdapter(getSupportFragmentManager(), titleFragmentMap);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);

        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void onPageScrolled(int i, float v, int i1)
    {

    }

    @Override
    public void onPageSelected(int i)
    {

    }

    @Override
    public void onPageScrollStateChanged(int i)
    {

    }
}

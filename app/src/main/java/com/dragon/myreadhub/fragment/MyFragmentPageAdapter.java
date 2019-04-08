package com.dragon.myreadhub.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MyFragmentPageAdapter extends FragmentPagerAdapter
{
    private LinkedHashMap<String, Fragment> titleFragmentMap = new LinkedHashMap<>();
    private List<String> titleList = new ArrayList<>();


    public MyFragmentPageAdapter(FragmentManager fm, LinkedHashMap<String, Fragment> titleFragmentMap)
    {
        super(fm);
        this.titleFragmentMap = titleFragmentMap;
        titleList.addAll(titleFragmentMap.keySet());
    }


    @Override
    public CharSequence getPageTitle(int position)
    {
        return titleList.get(position).toUpperCase();
    }


    @Override
    public Fragment getItem(int position)
    {
        return titleFragmentMap.get(titleList.get(position));
    }

    @Override
    public int getCount()
    {
        return titleList.size();
    }

}


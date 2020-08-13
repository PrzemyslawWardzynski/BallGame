package com.example.ballgame;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter {

    int noOfTabs = 3;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0 :
                InfoFragment f = new InfoFragment();
                return f;
            case 1 :
                MainFragment f1 = new MainFragment();
                return f1;
            case 2 :
                StatListFragment f2 = new StatListFragment();
                return f2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "Info";
            case 1:
                return "Gra";
            case 2:
                return "Wyniki";
            default:
                return "Blad";
        }
    }
}

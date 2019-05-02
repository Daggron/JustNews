package com.example.news;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class fragmentAdapter extends FragmentPagerAdapter {
    String title[]={"Highlights","India","Tech-India","World-Tech"};

    public fragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new hightlights_fragment();
            case 1:
                return new apple_Fragment();

            case 2:
                return new techIndia();

            case 3:
                return new worldTechFragment();
                default:
                    return null;

        }
    }

    @Override
    public int getCount() {
        return 4;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}

package com.matrixdeveloper.tajika.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.matrixdeveloper.tajika.BookedFragment;
import com.matrixdeveloper.tajika.RequestedFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new BookedFragment();
        }
        else if (position == 1)
        {
            fragment = new RequestedFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Booked";
        }
        else if (position == 1)
        {
            title = "Requested";
        }
        return title;
    }
}


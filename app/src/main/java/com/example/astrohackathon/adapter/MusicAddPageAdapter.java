package com.example.astrohackathon.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.astrohackathon.fragment.MusicRecordFragment;
import com.example.astrohackathon.fragment.MusicSummaryFragment;

/**
 * Created by chunqhai on 9/24/2014.
 */
public class MusicAddPageAdapter extends FragmentStatePagerAdapter{
    Context c;
    final String[] titles = {"Record","Lyrics","Summary"};

    public MusicAddPageAdapter(FragmentManager fm, Context c) {
        super(fm);
        this.c = c;

    }


    @Override
    public Fragment getItem(int pos) {
        /*MusicAdderFragment frag = new MusicAdderFragment();
        frag.setMode(pos);*/
        switch(pos){
            case 0 :
                return Fragment.instantiate(c, MusicRecordFragment.class.getName());
            case 1 :
                return Fragment.instantiate(c, MusicRecordFragment.class.getName());
            case 2:
                return Fragment.instantiate(c, MusicSummaryFragment.class.getName());
            default:
                return Fragment.instantiate(c, MusicRecordFragment.class.getName());

        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }


}

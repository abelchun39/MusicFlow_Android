package com.example.astrohackathon.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.astrohackathon.R;
import com.example.astrohackathon.adapter.MusicAddPageAdapter;
import com.viewpagerindicator.TitlePageIndicator;

/**
 * Created by chunqhai on 9/24/2014.
 */

public class MusicAdderFragment extends Fragment {

    int mode;
   MusicAddPageAdapter adapter;
    ViewPager pager;
    TitlePageIndicator titles;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v= inflater.inflate(R.layout.fragment_musicadder, container, false);

        pager = (ViewPager) v.findViewById(R.id.vp);
        titles = (TitlePageIndicator) v.findViewById(R.id.title_indicator);

        titles.setTextColor(Color.BLACK);
        titles.setSelectedColor(getResources().getColor(R.color.green));
        titles.setFooterColor(getResources().getColor(R.color.green));

        return v;
    }



    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new MusicAddPageAdapter(getFragmentManager(),getActivity());
        pager.setAdapter(adapter);
        titles.setViewPager(pager);


    }
}

package com.example.astrohackathon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.astrohackathon.R;
import com.example.astrohackathon.lib.adapters.AbstractWheelTextAdapter;

/**
 * Created by chunqhai on 9/23/2014.
 */
public class LyricsListAdapter extends AbstractWheelTextAdapter {

   String lyrics[];

    /*private String lyrics[] = new String[] {
            "I wish I was strong enough to lift not one but",
            "both of us",
            "Someday I will be strong enough to lift not one",
            "but both of us"
    };*/

    public LyricsListAdapter(Context c, String lyrics[])
    {
        super(c, R.layout.item_lyrics, NO_RESOURCE );
        this.lyrics = lyrics;


    }

    @Override
    public View getItem(int index, View convertView, ViewGroup parent) {
        return super.getItem(index, convertView, parent);
    }

    @Override
    protected CharSequence getItemText(int index) {
        return lyrics[index];
    }

    @Override
    public int getItemsCount() {
        return lyrics.length;
    }
}

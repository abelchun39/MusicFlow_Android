package com.example.astrohackathon.utils;

import android.content.Context;

import com.example.astrohackathon.R;
import com.example.astrohackathon.model.MainMusicRecord;
import com.example.astrohackathon.model.TopMusicRecord;

import java.util.ArrayList;

/**
 * Created by chunqhai on 9/27/2014.
 */
public class DummyDataHelper {

    Context _context;

    public DummyDataHelper(Context c)
    {
        this._context = c;
    }


    public void repeatAdding(int j, ArrayList<MainMusicRecord> musics) {
        for (int i = 0 ; i < j; i ++)
        {
            MainMusicRecord tm1 = new MainMusicRecord(R.drawable.bos,"Both of Us",R.drawable.ic_home,"Henry Chew",3,1,0);
            musics.add(tm1);
        }
    }

}

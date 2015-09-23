package com.example.astrohackathon.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.devsmart.android.ui.HorizontalListView;

import com.example.astrohackathon.R;
import com.example.astrohackathon.model.MainMusicRecord;
import com.example.astrohackathon.model.Music;
import com.example.astrohackathon.utils.DummyDataHelper;
import com.example.astrohackathon.webservice.HomeWS;

import java.util.ArrayList;

/**
 * Created by chunqhai on 9/30/2014.
 */

public class VerticalAdapter extends BaseAdapter {

    Context c;
    DummyDataHelper dummyData;
    HorizantalAdapter adapter;

    ListView verticalView;
    LinearLayout centerLinearLayout;

    String [] timeLine = new String[]{"Rock","Jazz","Pop"};

    HomeWS homeWs;
    ArrayList<Music> listMusic;
    ArrayList mainMusics;

    public VerticalAdapter(Context c)
    {
        this.c = c;
    }

    public VerticalAdapter(Context c, ListView verticalView, LinearLayout centerLinearLayout) {
        this(c);
        this.verticalView = verticalView;
        this.centerLinearLayout = centerLinearLayout;

        System.out.println( "VerticalAdapter: " + this.verticalView);

        mainMusics = new ArrayList<MainMusicRecord>();

        homeWs = new HomeWS(c);
        listMusic = new ArrayList<Music>();
        homeWs.setAdapter(this);

        homeWs.execute();
    }

    @Override
    public int getCount() {
        return listMusic.size();
    }

    @Override
    public Object getItem(int i) {
        return listMusic.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class ViewHolder {
        HorizontalListView horizonView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder vh;

        if ( v == null)
        {
            v = LayoutInflater.from(c).inflate(R.layout.horizontal_list_layout, parent, false);
            vh = new ViewHolder();

            vh.horizonView = (HorizontalListView) v.findViewById(R.id.main_category);

            v.setTag(vh);
        }
        else
        {
            vh = (ViewHolder) v.getTag();
        }

        System.out.println("Test");

        adapter = new HorizantalAdapter(c,listMusic);

        vh.horizonView.setAdapter(adapter);

        final HorizontalListView finalHorizonView = vh.horizonView;
        final View currentView = v;

        if(centerLinearLayout.getHeight() == 0||verticalView.getHeight()==0) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {

                    while (centerLinearLayout.getHeight() == 0 || verticalView.getHeight() == 0) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {

                    int height = (verticalView.getHeight() - centerLinearLayout.getHeight()) / 2;
                    ViewGroup.LayoutParams layoutParams = currentView.getLayoutParams();
                    layoutParams.height = height;
                    currentView.setLayoutParams(layoutParams);
//                currentView.invalidate();
//                finalHorizonView.invalidate();
//                 verticalView.invalidateViews();
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

//        vh.horizonView.setOnDragListener(new View.OnDragListener() {
//            @Override
//            public boolean onDrag(View view, DragEvent dragEvent) {
//                Log.d("setOnDragListener", "dragEvent: " + dragEvent.getAction());
//                return false;
//            }
//        });
        }
        else {
            int height = (verticalView.getHeight() - centerLinearLayout.getHeight()) / 2;
            ViewGroup.LayoutParams layoutParams = currentView.getLayoutParams();
            layoutParams.height = height;
            currentView.setLayoutParams(layoutParams);
        }
        return v;
    }

    public ArrayList<Music> getListMusic()
    {
        return this.listMusic;
    }

    public ListView getVerticalView() {
        return this.verticalView;
    }

    public ListView getHorizontalView() {
        return this.getHorizontalView();
    }

}

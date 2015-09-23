package com.example.astrohackathon.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.astrohackathon.R;
import com.example.astrohackathon.model.NavDrawerItem;

import java.util.ArrayList;

/**
 * Created by chunqhai on 9/22/2014.
 */
public class DrawerListAdapter extends BaseAdapter {

    Context c;
    ArrayList<NavDrawerItem> navDrawerItems;

    public DrawerListAdapter(Context c, ArrayList<NavDrawerItem> navDrawerItems)
    {
        this.c = c;
        this.navDrawerItems = navDrawerItems;

        if ( navDrawerItems == null)
            navDrawerItems =  new ArrayList<NavDrawerItem>();
    }

    @Override
    public int getCount() {
        return navDrawerItems.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View v = convertView;
        ViewHolder viewHolder;
        if( v == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = (LayoutInflater)c.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            
            v = mInflater.inflate(R.layout.item_list_drawer, null);
            viewHolder.imgIcon = (ImageView) v.findViewById(R.id.icon);
            viewHolder.txtTitle = (TextView) v.findViewById(R.id.title);
            viewHolder.txtCount = (TextView) v.findViewById(R.id.counter);
            v.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)v.getTag();
        }

        NavDrawerItem item = navDrawerItems.get(position);
        viewHolder.imgIcon.setImageResource(item.getIcon());
        viewHolder.txtTitle.setText(item.getTitle());

        // Displaying count
        // Check whether it set visible or not
        if (item.getCounterVisibility()) {
            viewHolder.txtCount.setText(item.getCount());
        }else {
            viewHolder.txtCount.setVisibility(View.GONE);
        }
        return v;
    }

    private class ViewHolder{
        ImageView imgIcon;
        TextView txtTitle;
        TextView txtCount;
    }
}

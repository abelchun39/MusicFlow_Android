package com.example.astrohackathon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

import com.example.astrohackathon.R;
import com.example.astrohackathon.model.Contributor;
import com.makeramen.RoundedImageView;

import java.util.ArrayList;

/**
 * Created by chunqhai on 10/11/2014.
 */
public class ContributorSelectedAdapter extends BaseAdapter
{
    ArrayList<Contributor> contributors;
    Context c;

    public ContributorSelectedAdapter(Context c, ArrayList<Contributor> contributors)
    {
        this.c = c;
        this.contributors = contributors;

        if (contributors == null)
            contributors = new ArrayList<Contributor>();

    }


    @Override
    public int getCount() {
        return contributors.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
}

    class ViewHolder
    {
        RoundedImageView image;
        ImageButton mDelete;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder vh;
        if (v == null)
        {
            v = LayoutInflater.from(c).inflate(R.layout.item_contributor_selected, parent, false);
            vh = new ViewHolder();

            vh.image = (RoundedImageView) v.findViewById(R.id.contributor_image);
            vh.mDelete = (ImageButton) v.findViewById(R.id.contributor_action_delete);
            v.setTag(vh);
        }
        else
        {
            vh = (ViewHolder) v.getTag();
        }

        Contributor c = contributors.get(position);
        vh.image.setImageResource(c.getImage());
        vh.mDelete.setOnClickListener(c.getDeleteImage());


        return v;
    }
}

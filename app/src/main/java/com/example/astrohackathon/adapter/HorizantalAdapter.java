package com.example.astrohackathon.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.astrohackathon.ItemPlayerActivity;
import com.example.astrohackathon.R;
import com.example.astrohackathon.helper.ImageLoader;
import com.example.astrohackathon.model.Music;

import java.util.ArrayList;

/**
 * Created by chunqhai on 9/26/2014.
 */
public class HorizantalAdapter extends BaseAdapter {

    //ArrayList<MainMusicRecord> musics = new ArrayList<MainMusicRecord>();
    ArrayList<Music> listMusic;
    Context c;

    public HorizantalAdapter(Context c, ArrayList<Music> musics)
    {
        this.c = c;
        this.listMusic = musics;

    }
    @Override
    public int getCount() {
        return listMusic.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    class ViewHolder {
        ImageView musicImage;
        TextView musicName;
        ImageView userImage;
        TextView userName;
        TextView musicLike;
        TextView musicComment;
        TextView musicContributor;
        ImageView favouriteImage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder vh;
        if( v == null)
        {
            vh = new ViewHolder();
            v = LayoutInflater.from(c).inflate(R.layout.main_music_item, parent, false);

            vh.musicImage = (ImageView) v.findViewById(R.id.music_image);

            vh.userImage = (ImageView) v.findViewById(R.id.userImage);
            vh.userName = (TextView) v.findViewById(R.id.userName);
            vh.userName.setTextColor(Color.WHITE);
            vh.favouriteImage = (ImageView) v.findViewById(R.id.favouriteImg);

            vh.musicName =(TextView) v.findViewById(R.id.music_name);
            vh.musicName.setBackgroundColor( Color.argb(70, 6, 6, 6) );

            vh.musicLike = (TextView) v.findViewById(R.id.txt_like);
            vh.musicContributor = (TextView) v.findViewById(R.id.txt_add);
            vh.musicComment = (TextView) v.findViewById(R.id.txt_comment);

            v.setTag(vh);
        }
        else{
            vh = (ViewHolder) v.getTag();
        }

        final Music tMusic = listMusic.get(position);

        ImageLoader imageLoader = new ImageLoader(this.c, true);

        imageLoader.DisplayImage(tMusic.getMusic_cover(), vh.musicImage);
        imageLoader.DisplayImage(tMusic.getUser().getAvatar(), vh.userImage);

        vh.favouriteImage.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        v.setSelected(!v.isSelected());
                        ImageView tempView = (ImageView) v;

                        if (v.isSelected()) {
                            tempView.setImageDrawable(v.getContext().getResources().getDrawable(R.drawable.home_favorite_yellowstar));

                        } else {
                            tempView.setImageDrawable(v.getContext().getResources().getDrawable(R.drawable.home_favorite));

                        }
                    }
                }
        );

        v.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View v) {
                    ItemPlayerActivity.music = tMusic;

                    Intent intent = new Intent(c, ItemPlayerActivity.class);

                    c.startActivity(intent);
                }
            }

        );

        vh.musicName.setText(tMusic.getTitle());
        vh.musicLike.setText(""+tMusic.getLike_count());
        vh.musicComment.setText(""+tMusic.getMessage_count());
        vh.musicContributor.setText(""+tMusic.getContributer_count());
        vh.userName.setText(tMusic.getUser().getFirst_name() + " " + tMusic.getUser().getLast_name());


        return v;
    }
}

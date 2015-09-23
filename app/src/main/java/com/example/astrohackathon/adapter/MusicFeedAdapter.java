package com.example.astrohackathon.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.astrohackathon.MusicPlayerActivity;
import com.example.astrohackathon.R;
import com.example.astrohackathon.model.Music;

import java.util.ArrayList;


public class MusicFeedAdapter extends BaseAdapter {
	
	Context c;
	ArrayList<Music> musics = new ArrayList<Music>();


    public MusicFeedAdapter(Context c, ArrayList<Music> musics)
	{
		this.c = c;
		this.musics = musics;

		if (musics == null)
			musics = new ArrayList<Music>();
	}

    public void addMusic(Music s)
    {
        if ( musics == null)
            return;
        musics.add(s);
        notifyDataSetChanged();
    }

	
	@Override
	public int getCount() {
		return musics.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

    @SuppressWarnings({"PackageVisibleField", "InstanceVariableNamingConvention"})
    class ViewHolder {
        ImageView musicPic;
		TextView musicName;
        TextView musicGenre;
        RatingBar musicRating;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;
        ViewHolder vh;

		if( v == null)
		{
            vh = new ViewHolder();
			v = LayoutInflater.from(c).inflate(R.layout.item_card, parent, false);

            vh.musicPic = (ImageView) v.findViewById(R.id.music_pic);
			vh.musicName =(TextView) v.findViewById(R.id.music_name);
			vh.musicGenre = (TextView) v.findViewById(R.id.music_genre);
			vh.musicRating = (RatingBar) v.findViewById(R.id.music_rating);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(c, "HELLO", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(c, MusicPlayerActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("music_name", musics.get(position).getMusicName());
                    c.startActivity(intent);
                }
            });



			v.setTag(vh);
		}
		else{
			vh = (ViewHolder) v.getTag();
		}
		
		Music s = musics.get(position);

        vh.musicPic.setImageResource(s.getMusicImage());
		vh.musicName.setText(s.getMusicName());
        vh.musicGenre.setText(s.getMusicGenre());
        vh.musicRating.setRating(s.getMusicRating());




		return v;
	}







}

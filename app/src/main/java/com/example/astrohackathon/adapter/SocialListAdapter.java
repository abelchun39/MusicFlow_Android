package com.example.astrohackathon.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.DialogInterface;
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
import com.example.astrohackathon.model.Social;


public class SocialListAdapter extends BaseAdapter {
	
	Context c;
	ArrayList<Social> socials = new ArrayList<Social>();


    public SocialListAdapter(Context c, ArrayList<Social> socials)
	{
		this.c = c;
		this.socials = socials;

		if (socials == null)
			socials = new ArrayList<Social>();
	}
	
	@Override
	public int getCount() {
		return socials.size();
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
                    intent.putExtra("music_name",socials.get(position).getMusicName());
                    c.startActivity(intent);
                }
            });



			v.setTag(vh);
		}
		else{
			vh = (ViewHolder) v.getTag();
		}
		
		Social s = socials.get(position);

        vh.musicPic.setImageResource(s.getMusicImage());
		vh.musicName.setText(s.getMusicName());
        vh.musicGenre.setText(s.getMusicGenre());
        vh.musicRating.setRating(s.getMusicRating());




		return v;
	}







}

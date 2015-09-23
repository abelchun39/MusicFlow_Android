package com.example.astrohackathon.model;

import android.view.View.OnClickListener;
import android.widget.SeekBar.OnSeekBarChangeListener;


public class Social {
    int musicImage;
	String musicName;
	String musicGenre;
	float musicRating;

	public Social (int musicImage, String musicName, String musicGenre,int musicRating)
	{
		this.musicImage = musicImage;
        this.musicName = musicName;
        this.musicGenre = musicGenre;
        this.musicRating = musicRating;
	}

    public int getMusicImage() {
        return musicImage;
    }

    public void setMusicImage(int musicImage) {
        this.musicImage = musicImage;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getMusicGenre() {
        return musicGenre;
    }

    public void setMusicGenre(String musicGenre) {
        this.musicGenre = musicGenre;
    }

    public float getMusicRating() {
        return musicRating;
    }

    public void setMusicRating(float musicRating) {
        this.musicRating = musicRating;
    }
}

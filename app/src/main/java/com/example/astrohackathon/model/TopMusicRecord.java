package com.example.astrohackathon.model;

/**
 * Created by chunqhai on 9/26/2014.
 */
public class TopMusicRecord {
    int musicImage;
    String musicName;

    public TopMusicRecord(int musicImage, String musicName)
    {
        this.musicImage = musicImage;
        this.musicName = musicName;

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
}

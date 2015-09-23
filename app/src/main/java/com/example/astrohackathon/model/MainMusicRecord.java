package com.example.astrohackathon.model;

/**
 * Created by chunqhai on 10/1/2014.
 */
public class MainMusicRecord {

    String musicName;
    int musicImage;
    int musicLike;
    int musicComment;
    int musicShare;

    String userName;
    int userImage;

    public MainMusicRecord(int musicImage,
                           String musicName,
                           int userImage,
                           String userName,
                           int musicLike,
                           int musicComment,
                           int musicShare)
    {
        this.musicImage = musicImage;
        this.musicName = musicName;
        this.userImage = userImage;
        this.userName = userName;
        this.musicLike = musicLike;
        this.musicComment = musicComment;
        this.musicShare = musicShare;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public int getMusicImage() {
        return musicImage;
    }

    public void setMusicImage(int musicImage) {
        this.musicImage = musicImage;
    }

    public int getMusicLike() {
        return musicLike;
    }

    public void setMusicLike(int musicLike) {
        this.musicLike = musicLike;
    }

    public int getMusicComment() {
        return musicComment;
    }

    public void setMusicComment(int musicComment) {
        this.musicComment = musicComment;
    }

    public int getMusicShare() {
        return musicShare;
    }

    public void setMusicShare(int musicShare) {
        this.musicShare = musicShare;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserImage() {
        return userImage;
    }

    public void setUserImage(int userImage) {
        this.userImage = userImage;
    }



}

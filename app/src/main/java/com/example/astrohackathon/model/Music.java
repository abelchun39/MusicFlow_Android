package com.example.astrohackathon.model;

import org.json.JSONObject;


public class Music {
    String id;
    String user_id;
	String title;
    String description;
    String music_cover;
    String music_audio;
    String created_at;
    int like_count = 0;
    int message_count = 0;
    int contributer_count = 0;
    User user;

    //Obsolete
    int musicImage;
    String musicName;
    String musicGenre;
    float musicRating;

	public Music()
	{

	}

    public Music(int musicImage, String musicName, String musicGenre, int musicRating)
    {
        this.musicImage = musicImage;
        this.musicName = musicName;
        this.musicGenre = musicGenre;
        this.musicRating = musicRating;
    }

    public Music(JSONObject json) {
        try {
            if ( json.has("id") ) {
                id = json.getString("id");

            }

            if ( json.has("user_id") ) {
                user_id = json.getString("user_id");

            }

            if ( json.has("title") ) {
                title = json.getString("title");

            }

            if ( json.has("description") ) {
                description = json.getString("description");

            }

            if ( json.has("music_cover") ) {
                music_cover = json.getString("music_cover");

            }

            if ( json.has("music_audio") ) {
                music_audio = json.getString("music_audio");

            }

            if ( json.has("music_like") ) {
                like_count = json.getInt("music_like");

            }

            if ( json.has("message_count") ) {
                message_count = json.getInt("message_count");

            }

            if ( json.has("contributor_count") ) {
                message_count = json.getInt("contributor_count");

            }

            if ( json.has("created_at") ) {
                created_at = json.getString("created_at");

            }

            if ( json.has("user") ) {
                user = new User(json.getJSONObject("user"));
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMusic_cover() {
        return music_cover;
    }

    public void setMusic_cover(String music_cover) {
        this.music_cover = music_cover;
    }

    public String getMusic_audio() {
        return music_audio;
    }

    public void setMusic_audio(String music_audio) {
        this.music_audio = music_audio;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
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

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getMessage_count() {
        return message_count;
    }

    public void setMessage_count(int message_count) {
        this.message_count = message_count;
    }

    public int getContributer_count() {
        return contributer_count;
    }

    public void setContributer_count(int contributer_count) {
        this.contributer_count = contributer_count;
    }

    public User getUser() {
        return this.user;

    }
}

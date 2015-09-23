package com.example.astrohackathon;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MusicPlayerActivity extends Activity
{
    VideoView vidView;
    String vidAddress;
    MediaController vidControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicplayer);

        vidView = (VideoView) findViewById(R.id.video_flow);
        vidAddress = "http://128.199.173.244/uploads/users/1/audio/141270199954341f2f107f8.mp3";
        Uri vidUri = Uri.parse(vidAddress);
        vidView.setVideoURI(vidUri);
        vidView.start();

        vidControl = new MediaController(this);
        vidControl.setAnchorView(vidView);
        vidView.setMediaController(vidControl);



    }
}
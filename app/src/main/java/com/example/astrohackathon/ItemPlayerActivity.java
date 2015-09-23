package com.example.astrohackathon;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.astrohackathon.fragment.MusicAdderFragment;
import com.example.astrohackathon.helper.ImageLoader;
import com.example.astrohackathon.helper.JSONParser;
import com.example.astrohackathon.lib.visualizer.VisualizerView;
import com.example.astrohackathon.model.Music;
import com.example.astrohackathon.utils.WaveHelper;


/**
 * Created by chunqhai on 9/24/2014.
 */
public class ItemPlayerActivity extends FragmentActivity{

    public static Music music;
    TextView tvTitle;
    TextView tvUserName;
    ImageView ivMusicCover;
    ImageLoader imageLoader;
    VideoView vv_music;
    ImageView btn_play;
    TextView currentTimeTextView;
    TextView totalTimeTextView;
    VisualizerView mVisualizerView;

    SeekBar seekBar;
    Handler handler;
    Runnable moveSeekBarThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_item);
        System.out.println("IMCALLED");

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(true);

        getActionBar().setTitle("Player");

        tvTitle = (TextView)findViewById(R.id.music_title);
        tvUserName = (TextView)findViewById(R.id.user_name);
        ivMusicCover = (ImageView)findViewById(R.id.music_cover);
        vv_music = (VideoView)findViewById(R.id.vv_music);
        btn_play = (ImageView)findViewById(R.id.btn_play);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        currentTimeTextView = (TextView) findViewById(R.id.currentTime);
        totalTimeTextView =(TextView) findViewById(R.id.totalTime);
        mVisualizerView = (VisualizerView) findViewById(R.id.visualizerView);


        imageLoader = new ImageLoader(this, true);

        Uri uri = Uri.parse(music.getMusic_audio());
        vv_music.setVideoURI(uri);
        MediaController mediacontroller = new MediaController( ItemPlayerActivity.this );
        mediacontroller.setAnchorView(vv_music);

        vv_music.setMediaController(mediacontroller);

                vv_music.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        try {
                            Thread.sleep(1000);
                            mVisualizerView.link(mediaPlayer);

                            WaveHelper wh = new WaveHelper(getApplicationContext(),mediaPlayer,mVisualizerView);
                            wh.addLineRenderer();
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });





        moveSeekBarThread = new Runnable() {
            @Override
            public void run() {
                if(vv_music.isPlaying()){

                    int mediaPos_new = vv_music.getCurrentPosition();
                    int mediaMax_new = vv_music.getDuration();
                    seekBar.setMax(mediaMax_new);
                    seekBar.setProgress(mediaPos_new);


                    handler.postDelayed(moveSeekBarThread,500);

                    currentTimeTextView.setText(""+mediaPos_new/1000/60+"."+(mediaPos_new/1000%60>=10?mediaPos_new/1000%60:"0"+mediaPos_new/1000%60));
                    totalTimeTextView.setText(""+mediaMax_new/1000/60+"."+(mediaMax_new/1000%60>=10?mediaMax_new/1000%60:"0"+mediaMax_new/1000%60));
                }
                else if( vv_music.getCurrentPosition() == vv_music.getDuration()) {
                    seekBar.setProgress(0);
                    vv_music.seekTo(0);
                    btn_play.setImageDrawable(getResources().getDrawable(R.drawable.play));
                }
                Log.d("music", "music play: " + vv_music.getCurrentPosition());
            }
        };

        btn_play.setOnClickListener( new View.OnClickListener() {
                 @Override
                 public void onClick(final View view) {


                         final ImageView viewImage = (ImageView)view;
                         if(viewImage.getDrawable().getConstantState().equals(getApplication().getResources().getDrawable(R.drawable.play).getConstantState())){

                         viewImage.setImageDrawable(view.getContext().getResources().getDrawable(R.drawable.add_music_record_pause));

                            vv_music.start();

                             handler  = view.getHandler();
                             handler.removeCallbacks(moveSeekBarThread);
                             handler.postDelayed(moveSeekBarThread,500);

                     } else {
                         viewImage.setImageDrawable( view.getContext().getResources().getDrawable(R.drawable.play) );
                         vv_music.pause();
                             handler.removeCallbacks(moveSeekBarThread);
                     }

                 }
             }

        );

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    vv_music.seekTo(i);
                    currentTimeTextView.setText(""+i/1000/60+"."+(i/1000%60>=10?i/1000%60:"0"+i/1000%60));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        System.out.println("Music: " + music.toString());

        if ( music !=null ) {
            tvTitle.setText(music.getTitle());
            tvUserName.setText(music.getUser().getFirst_name() + " " + music.getUser().getLast_name());
            imageLoader.DisplayImage(music.getMusic_cover(), ivMusicCover);

        }
    }

    @Override
    public void onPause()
    {
        super.onPause();

        if( vv_music.isPlaying() ) {
            vv_music.stopPlayback();
            btn_play.setImageDrawable(getResources().getDrawable(R.drawable.play));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch( item.getItemId())
        {
            case android.R.id.home:
//                Intent intent = new Intent(ItemPlayerActivity.this,MainActivity.class);
//                startActivity(intent);
                finish();
                return true;
            case R.id.action_like:
                return true;
            case R.id.action_comment:
                return true;
            case R.id.action_add:
                Intent intent = new Intent(ItemPlayerActivity.this,ItemPlayerAddActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

package com.example.astrohackathon;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.astrohackathon.helper.ImageLoader;
import com.example.astrohackathon.lib.RealDoubleFFT;
import com.example.astrohackathon.lib.visualizer.VisualizerView;
import com.example.astrohackathon.model.Music;
import com.example.astrohackathon.utils.RecordHelper;
import com.example.astrohackathon.utils.WaveHelper;

/**
 * Created by chunqhai on 9/24/2014.
 */
public class ItemPlayerAddActivity extends FragmentActivity{

    VisualizerView mVisualizerView;
    VideoView vv_music;
    Music music;
    RecordHelper recordHelper;
    ImageView coverImage ,recordProcessImageView;
    ImageButton recordImageButton,musicPlayButton;
    Button mergeButton;

    int frequency = 8000;
    int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
    private RealDoubleFFT transformer;
    int blockSize = 256;
    boolean started = false;
    Bitmap bitmap;
    Canvas canvas;
    Paint paint;
    RecordAudio recordTask;

    boolean hasRecordClicked = false,hasMusicPlayed =false;
    boolean isMergeGone = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_item_add);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(true);

        getActionBar().setTitle("Add Music");



        music = ItemPlayerActivity.music;

        mVisualizerView = (VisualizerView) findViewById(R.id.visualizerView);
        vv_music = (VideoView)findViewById(R.id.vv_music);
        recordImageButton = (ImageButton) findViewById(R.id.btn_record);
        musicPlayButton = (ImageButton) findViewById(R.id.musicPlayButton);
        coverImage = (ImageView)findViewById(R.id.music_cover);
        recordProcessImageView = (ImageView)findViewById(R.id.recordProcessImage);

        transformer = new RealDoubleFFT(blockSize);

        bitmap = Bitmap.createBitmap(BaseActivity.screenWitdhPixel,80,Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        recordProcessImageView.setImageBitmap(bitmap);

        new ImageLoader(this, true).DisplayImage(music.getMusic_cover(), coverImage);
        Uri uri = Uri.parse(music.getMusic_audio());
        vv_music.setVideoURI(uri);
        MediaController mediacontroller = new MediaController( ItemPlayerAddActivity.this );
        mediacontroller.setAnchorView(vv_music);
        vv_music.setMediaController(mediacontroller);

        vv_music.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                try {
                    Thread.sleep(1000);
                    mVisualizerView.link(mediaPlayer);

                    WaveHelper wh = new WaveHelper(getApplicationContext(), mediaPlayer, mVisualizerView);
                    wh.addLineRenderer();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        musicPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musicPlayButton.getDrawable().getConstantState().equals(getApplication().getResources().getDrawable(R.drawable.play).getConstantState())) {

                    musicPlayButton.setImageDrawable(view.getContext().getResources().getDrawable(R.drawable.add_music_record_pause));

                    vv_music.start();

                    hasMusicPlayed = true;

                } else {
                    musicPlayButton.setImageDrawable(view.getContext().getResources().getDrawable(R.drawable.play));
                    vv_music.pause();
                }
            }
        });


        recordImageButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(final View view) {

                   if(isMergeGone == true) {

                       if (recordImageButton.getDrawable().getConstantState().equals(getApplication().getResources().getDrawable(R.drawable.btn_play_pressed).getConstantState())) {
                           recordImageButton.setImageDrawable(view.getContext().getResources().getDrawable(R.drawable.btn_record_pause));

                           try {
                               recordHelper.startPlayRecording();
                           }    catch(Exception ex) {
                               ex.printStackTrace();
                           }
                       } else {
                           recordImageButton.setImageDrawable(view.getContext().getResources().getDrawable(R.drawable.btn_pause_pressed));
                       }
                   }
                   else if (recordImageButton.getDrawable().getConstantState().equals(getApplication().getResources().getDrawable(R.drawable.music_record).getConstantState())) {

                       hasRecordClicked = true;
                       recordHelper = new RecordHelper(view.getContext());

                       try {
                           recordHelper.initRecorder();
//                           recordHelper.initPlayer();

                           if( !recordHelper.isRecording() ) {
                               recordHelper.startRecording();

                           } else {
                               recordHelper.stopRecording();

                           }

                       }    catch(Exception ex) {
                           ex.printStackTrace();

                       }

                       if (started) {
                           started = false;
                           recordTask.cancel(true);
                       } else {
                           started = true;
                           recordTask = new RecordAudio();
                           recordTask.execute();
                       }

                       recordImageButton.setImageDrawable(view.getContext().getResources().getDrawable(R.drawable.btn_record_pause));

                   } else {
                       recordImageButton.setImageDrawable(view.getContext().getResources().getDrawable(R.drawable.music_record));
                   }

               }
           }

        );

        mergeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hasRecordClicked && hasMusicPlayed){
                    mergeButton.setVisibility(View.GONE);

                    try{
                    if( !recordHelper.isRecording() ) {

                    } else {
                        recordHelper.stopRecording();
                    }
                    }    catch(Exception ex) {
                        ex.printStackTrace();

                    }

                    vv_music.pause();

                    musicPlayButton.setVisibility(View.GONE);

                    recordImageButton.setImageDrawable(getApplication().getResources().getDrawable(R.drawable.btn_play_pressed));
                    isMergeGone = true;
                }

            }
        });


    }
    private class RecordAudio extends AsyncTask<Void, double[], Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                int bufferSize = AudioRecord.getMinBufferSize(frequency,
                        channelConfiguration, audioEncoding);
                AudioRecord audioRecord = new AudioRecord(
                        MediaRecorder.AudioSource.DEFAULT, frequency,
                        channelConfiguration, audioEncoding, bufferSize);

                short[] buffer = new short[blockSize];
                double[] toTransform = new double[blockSize];
                audioRecord.startRecording();
                while (started) {
                    int bufferReadResult = audioRecord.read(buffer, 0, blockSize);

                    for (int i = 0; i < blockSize && i < bufferReadResult; i++) {
                        toTransform[i] = (double) buffer[i] / 32768.0; // signed 16 bit
                    }

                    transformer.ft(toTransform);
                    publishProgress(toTransform);
                }
                audioRecord.stop();
            } catch (Throwable t) {
                Log.e("AudioRecord", "Recording Failed");
            }
            return null;
        }
    }

    protected void onProgressUpdate(double[]... toTransform) {
        canvas.drawColor(Color.TRANSPARENT);
        for (int i = 0; i < toTransform[0].length; i++) {
            int x = i;
            int downy = (int) (100 - (toTransform[0][i] * 10));
            int upy = 100;
            canvas.drawLine(x, downy, x, upy, paint);
        }
        recordProcessImageView.invalidate();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch( item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

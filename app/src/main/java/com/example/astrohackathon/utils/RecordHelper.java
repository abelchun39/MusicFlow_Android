package com.example.astrohackathon.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
* Created by RoMY on 9/27/2014.
*/

public class RecordHelper {

        // Variable for storing state
        private boolean _isRecording;
        private boolean _isPlaying;

        // Variable that can be manipulated to affect the behaviour of this class
        private Context _context;
        private StringBuilder _uri;
        private int _audioSource;
        private int _outputFormat;
        private int _encoder;

        private MediaPlayer mPlayer;
        private MediaRecorder mRecorder;
        private ThreadStartRecord threadStartRecord;

        private TextView tRecorder;
        Handler handler;

        //TODO: validation

        public RecordHelper(Context c) {
            this(c, getDefaultUri() + "default.3gpp");
        }

        public RecordHelper(Context c, String newUri) {
            _context = c;
            System.out.print(getDefaultUri());
            _uri = new StringBuilder();
            _uri.append(newUri);

            //Init default behaviour for this class
            _isRecording = false;
            _isPlaying = false;
            _audioSource = MediaRecorder.AudioSource.MIC;
            _outputFormat = MediaRecorder.OutputFormat.THREE_GPP;
            _encoder = MediaRecorder.OutputFormat.AMR_NB;

            handler = new Handler();
        }


        public RecordHelper(Context c, TextView tv)
        {
            this(c);
            this.tRecorder = tv;
        }

        public void initRecorder() throws Exception {
            if(mRecorder != null) {
                mRecorder = null;
            }

            PackageManager pmanager = _context.getPackageManager();
            if (pmanager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) {
                Toast.makeText(_context,"GOT MIC!",Toast.LENGTH_SHORT).show();
                mRecorder = new MediaRecorder();
                mRecorder.reset();
                mRecorder.setAudioSource(_audioSource);
                mRecorder.setOutputFormat(_outputFormat);
                mRecorder.setAudioEncoder(_encoder);
                mRecorder.setOutputFile(_uri.toString());
                System.out.print(_uri.toString());

            }else
            {
                Toast.makeText(_context,"NO MIC!",Toast.LENGTH_SHORT).show();
            }
        }

        public void initPlayer() throws Exception {
            if( mPlayer != null ) {
                mPlayer = null;
            }

            mPlayer = new MediaPlayer();
            mPlayer.setDataSource(_uri.toString());

        }

        public void toggleRecording() throws Exception {
            if(mRecorder == null) {
                throw new NullPointerException("RecordHelper initRecorder() method is not called");
            }

            if( !_isRecording ) {
                startRecording();

            } else {
                stopRecording();

            }
        }

        public void startRecording() throws Exception {
            _isRecording = true;

            initRecorder();

            threadStartRecord = new ThreadStartRecord();
            handler.post(threadStartRecord);


        }

        public void stopRecording() throws Exception {
            _isRecording = false;

            mRecorder.stop();
            mRecorder.reset();
            mRecorder.release();
            mRecorder = null;
        }

        public void togglePlayRecording() throws Exception {
            if(mPlayer == null) {
                throw new NullPointerException("RecordHelper initPlayer() method is not called");
            }

            if( !_isPlaying ) {
                startPlayRecording();

            } else {
                stopPlayRecording();

            }

        }

        public void startPlayRecording() throws Exception {
            //TODO: handle exception if the provided uri does not exist, can be written in initPlayer
            //      but that would result in initPlayer to be tightly coupled with recording feature.
            initPlayer();

            _isPlaying = true;

            mPlayer.prepare();
            mPlayer.start();
        }

        public void stopPlayRecording() throws Exception {
            _isPlaying = false;

            mPlayer.stop();
            mPlayer.reset();
            mPlayer.release();
            mPlayer = null;
        }

        // Generate default uri
        // TODO: provide parameter to customize defaultUri
        public static String getDefaultUri() {
            return Environment.getExternalStorageDirectory().
                    getAbsolutePath() + "/";
        }

        public void destroy() {
            mRecorder = null;
            mPlayer = null;
        }

        public boolean isPlaying() {
            return _isPlaying;
        }

        public boolean isRecording() {
            return _isRecording;
        }

        public String getUri() {
            return _uri.toString();
        }

        class ThreadStartRecord implements Runnable {
            public void run() {
                try {
                    mRecorder.prepare();
                    mRecorder.start();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    Toast.makeText(_context, "Unable to start recording", Toast.LENGTH_LONG).show();
                }
            }
        }

    Runnable UpdateTimerTask = new Runnable() {

        @Override
        public void run() {
            updateTimerText();
        }
    };

    public void updateTimerText() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdfDate = new SimpleDateFormat("mm:ss.SS");
        String formattedDate = sdfDate.format(c.getTime());
        tRecorder.setText(formattedDate);
        handler.post(UpdateTimerTask);
    }

    public void stopTimerText() {
        handler.removeCallbacks(UpdateTimerTask);
    }

}


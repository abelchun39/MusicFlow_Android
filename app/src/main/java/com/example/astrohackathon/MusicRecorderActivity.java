package com.example.astrohackathon;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.astrohackathon.lib.audiovisualizer.AudioMonitor;
import com.example.astrohackathon.lib.audiovisualizer.MySurfaceView;
import com.example.astrohackathon.lib.visualizer.VisualizerView;
import com.example.astrohackathon.model.Music;
import com.example.astrohackathon.utils.RecordHelper;
import com.example.astrohackathon.utils.WaveHelper;
import com.example.astrohackathon.webservice.AddMusicWS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chunqhai on 10/11/2014.
 */
public class MusicRecorderActivity extends Activity implements AudioMonitor.OnUpdateListener, AdapterView.OnItemSelectedListener, View.OnClickListener {

    public static int REQUEST_ADD_CONTRIBUTOR = 0;
    protected static final int PICK_IMAGE = 0;
    protected static final int CANCEL_IMAGE =1;

    Music music;

    Spinner genreSpinner;
    ImageButton mAddContributor;
    ImageButton mBtnRecord;
    TextView mRecordTime;
    RecordHelper recordHelper;
    Button mBtnUpload;
    Button mSave;

    VisualizerView mVisualizerView;


    MySurfaceView mView;
    AudioMonitor r;
    Thread mThread;
    LinearLayout contributorLayout;
    TextView tvTitle;
    TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordmusic);
        mView = new MySurfaceView(this);

        mRecordTime = (TextView) findViewById(R.id.music_recording_time);

        mBtnRecord = (ImageButton) findViewById(R.id.btn_record);
        mBtnRecord.setOnClickListener(this);

        mBtnUpload = (Button) findViewById(R.id.btn_upload);
        mBtnUpload.setOnClickListener(this);

        genreSpinner = (Spinner) findViewById(R.id.spinner_genre);
        addSpinnerItems();
        genreSpinner.setOnItemSelectedListener(this);

        mAddContributor = (ImageButton) findViewById(R.id.btn_action_add_contributor);
        mAddContributor.setOnClickListener(this);

        mSave = (Button) findViewById(R.id.btn_save);
        mSave.setOnClickListener(this);

        tvTitle = (TextView)findViewById(R.id.music_title);
        tvDescription = (TextView)findViewById(R.id.music_description);

        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(parentView.getChildCount()>0) {
                    TextView textView = (TextView) parentView.getChildAt(0);
                    textView.setTextColor(Color.WHITE);
                    textView.setTextSize(12);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(true);

        getActionBar().setTitle("Record");

        music = new Music();

        recordHelper = new RecordHelper(this, mRecordTime);

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


    private void addSpinnerItems() {
        List<String> lists = new ArrayList<String>();
        lists.add("Rock");
        lists.add("Band");
        lists.add("Guitar");
        lists.add("Piano");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lists);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        genreSpinner.setSelection(position);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id)
        {
            case R.id.btn_action_add_contributor :
                Intent intent = new Intent(MusicRecorderActivity.this, MusicContributorActivity.class);
                startActivityForResult(intent,REQUEST_ADD_CONTRIBUTOR);

                break;

            case R.id.btn_save :
                System.out.println("OnClick");
                music.setTitle(tvTitle.getText().toString());
                music.setDescription(tvDescription.getText().toString());
                music.setMusic_audio( recordHelper.getUri() );

                new AddMusicWS(view.getContext(), music).execute();
                break;

            case R.id.btn_record :
                onClickBtnRecord();

                break;

            case R.id.btn_upload :
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, ""), PICK_IMAGE);
                break;
            default:

                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == PICK_IMAGE) {
                Uri selectedImageUri = data.getData();
                Toast.makeText(getApplicationContext(),"You choose one image file from "+ getRealPathFromURI(selectedImageUri), Toast.LENGTH_LONG).show();

                /* WHAT YOU DO WITH URI ??? */
            }
            else if (requestCode == REQUEST_ADD_CONTRIBUTOR){

            }
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void onClickBtnRecord() {

        try {
            if( !recordHelper.isRecording() ) {
                Toast.makeText(getApplicationContext(), "Start Recording", Toast.LENGTH_SHORT).show();
                mBtnRecord.setImageResource(R.drawable.btn_record_pause);
                startRecording();

            } else {
                Toast.makeText(getApplicationContext(), "Stop Recording", Toast.LENGTH_SHORT).show();
                mBtnRecord.setImageResource(R.drawable.btn_record_start);
                stopRecording();

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void stopRecording() {
        try {
            recordHelper.stopRecording();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void startRecording() {
        try {
            recordHelper = new RecordHelper(this, mRecordTime);

            recordHelper.startRecording();

        } catch ( Exception ex ) {
            ex.printStackTrace();

        }

    }

    @Override
    public void update(final short[] bytes, final int length, final float sampleLength) {
        runOnUiThread(new Runnable() {
            public void run() {
                mView.setData(bytes, length, sampleLength);
            }
        });
    }
}

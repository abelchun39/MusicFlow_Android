package com.example.astrohackathon.fragment;



import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.astrohackathon.R;
import com.example.astrohackathon.model.Music;
import com.example.astrohackathon.utils.RecordHelper;
import com.example.astrohackathon.webservice.AddMusicWS;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by chunqhai on 9/24/2014.
 */

public class MusicRecordFragment extends Fragment implements View.OnClickListener {
    RecordHelper recordHelper;
    ImageButton btnRecord;
    TextView tRecordTime;
    TextView tvTitle;
    TextView tvDesc;
    Button btnSave;
    Music music;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_musicrecord, container, false);

        //btnRecord = (ImageButton)v.findViewById(R.id.btn_record);
//        btnRecord.setOnClickListener(this);

        //tRecordTime = (TextView) v.findViewById(R.id.record_time);
        tRecordTime.setText("00:00.00");

        recordHelper = new RecordHelper(getActivity());

        recordHelper = new RecordHelper(getActivity(),tRecordTime);

        music = new Music();

        tvTitle = (TextView)v.findViewById(R.id.music_title);
        tvDesc = (TextView)v.findViewById(R.id.music_description);
        btnSave = (Button)v.findViewById(R.id.btn_save);
        btnSave.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("OnClick");
                music.setTitle(tvTitle.getText().toString());
                music.setDescription(tvDesc.getText().toString());
                music.setMusic_audio( recordHelper.getUri() );

                new AddMusicWS(view.getContext(), music).execute();
            }
        });



        return v;

    }




    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btn_record:
                onClickBtnRecord();

                break;
            case R.id.btn_save :
                music.setTitle(tvTitle.getText().toString());
                music.setDescription(tvDesc.getText().toString());
                music.setMusic_audio( recordHelper.getUri() );

                new AddMusicWS(view.getContext(), music);

                break;
            default:
                Toast.makeText(getActivity(), "Not implemented yet...", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void onClickBtnRecord() {
        try {
            if( !recordHelper.isRecording()  ) {
                Toast.makeText(getActivity(), "Start Recording", Toast.LENGTH_SHORT).show();
                /*btnRecord.setImageResource(R.drawable.btn_record_pause);*/
                recordHelper.startRecording();
                recordHelper.updateTimerText();

            } else {
                Toast.makeText(getActivity(), "Stop Recording", Toast.LENGTH_SHORT).show();
                btnRecord.setImageResource(R.drawable.btn_record_start);
                recordHelper.stopRecording();
                recordHelper.stopTimerText();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void onClickBtnPlay() {
        try {
            if( !recordHelper.isPlaying() ) {
                Toast.makeText(getActivity(), "Start Playing Recording", Toast.LENGTH_SHORT).show();
                recordHelper.startPlayRecording();
            } else {
                Toast.makeText(getActivity(), "Stop Playing Recording", Toast.LENGTH_SHORT).show();
                recordHelper.stopPlayRecording();
            }

        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}

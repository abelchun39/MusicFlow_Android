package com.example.astrohackathon.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.astrohackathon.MainActivity;
import com.example.astrohackathon.R;

/**
 * Created by chunqhai on 9/24/2014.
 */

public class MusicSummaryFragment extends Fragment implements View.OnClickListener{

    public static int CREATE_MUSIC = 1;

    Button mPost;
    Button mCancel;
    EditText mMusicName;
    EditText mMusicGenre;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_musicsummary, container, false);

        mPost = (Button) v.findViewById(R.id.btn_post);
        mPost.setOnClickListener(this);

        mCancel = (Button) v.findViewById(R.id.btn_cancel);
        mCancel.setOnClickListener(this);

        mMusicName = (EditText) v.findViewById(R.id.edit_musicname);
        mMusicGenre = (EditText) v.findViewById(R.id.edit_genre);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private void postToMusicFeed(String musicName, String musicGenre)
    {
        Bundle bundle = new Bundle();
        bundle.putString("musicname",musicName);
        bundle.putString("musicgenre",musicGenre);

        //Music Feed Fragment
        MusicFeedFragment fragMusicFeed = new MusicFeedFragment();
        fragMusicFeed.setArguments(bundle);

        Intent intent = new Intent(getActivity(),MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("CREATE_MUSIC", CREATE_MUSIC);

        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        int v = view.getId();
        switch(v)
        {
            case R.id.btn_post:
                String musicName = mMusicName.getText().toString();
                String musicGenre = mMusicName.getText().toString();

                postToMusicFeed(musicName, musicGenre);

                break;
            case R.id.btn_cancel:

                break;
            default:

                break;
        }
    }
}

package com.example.astrohackathon.fragment;

import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.astrohackathon.R;
import com.example.astrohackathon.adapter.MusicFeedAdapter;
import com.example.astrohackathon.model.Music;
import com.nhaarman.listviewanimations.appearance.simple.SwingRightInAnimationAdapter;


public class MusicFeedFragment extends ListFragment{


    private static final int INITIAL_DELAY_MILLIS = 300;
    private static final int CREATE_MUSIC = 1;

	ListView lv;
	MusicFeedAdapter adapter;
	ArrayList<Music> musics;
    SwingRightInAnimationAdapter swingRightInAnimationAdapter;

    TextView musicName;
    ImageView musicImage;
    ImageButton mAddMusic;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_musicfeed, container, false);





        /*mAddMusic = (ImageButton) v.findViewById(R.id.btn_addmusic);z
        mAddMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MusicAddActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
            }
        });*/
		lv = (ListView) v.findViewById(android.R.id.list);

        musicName = (TextView) v.findViewById(R.id.music_name);
        musicImage = (ImageView) v.findViewById(R.id.music_pic);


        Bundle extras = getActivity().getIntent().getExtras();
        if ( extras != null)
        {
                Toast.makeText(getActivity(),"CREATE MUSIC", Toast.LENGTH_SHORT).show();
                String createMusicName = getArguments().getString("musicname");
                String createMusicGenre = getArguments().getString("musicgenre");
                Music m = new Music (R.drawable.bestday,createMusicName,createMusicGenre,2);
                adapter.addMusic(m);
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
               /* lv.post(new Runnable() {

                    @Override
                    public void run() {
                        lv.setSelection(adapter.getCount());
                    }
                });*/

        }

		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	
		musics = new ArrayList<Music>();
		repeatAdding(2, musics);


		
		adapter = new MusicFeedAdapter(getActivity(), musics);

        SwingRightInAnimationAdapter swingRightInAnimationAdapter = new SwingRightInAnimationAdapter(adapter);
        swingRightInAnimationAdapter.setAbsListView(lv);

        assert swingRightInAnimationAdapter.getViewAnimator() != null;
        swingRightInAnimationAdapter.getViewAnimator().setInitialDelayMillis(INITIAL_DELAY_MILLIS);

        lv.addHeaderView(new View(getActivity()));
        lv.addFooterView(new View(getActivity()));

		lv.setAdapter(swingRightInAnimationAdapter);

        lv.setOnItemClickListener(onCardItemClickListener);
		
	}

    private void repeatAdding(int j, ArrayList<Music> musics) {
        for (int i = 0; i < j; i++)
        {
            Music s1 = new Music(R.drawable.bestday,"Best Day Of My Life","Rocks",1);
            Music s2 = new Music(R.drawable.bestdayofmylife,"Best Day Ever","Classic",4);
            /*Music s3 = new Music(R.drawable.timetosleep,"Time To SLeep","Relax",3);
            Music s4 = new Music(R.drawable.bos, "Both Of Us", "Romance",2);
            Music s5 = new Music(R.drawable.bestday,"Best Day Of My Life","Rocks",1);
            Music s6 = new Music(R.drawable.bestdayofmylife,"Best Day Ever","Classic",4);
            Music s7 = new Music(R.drawable.timetosleep,"Time To SLeep","Relax",3);
            Music s8 = new Music(R.drawable.bos, "Both Of Us", "Romance",2);
            Music s9 = new Music(R.drawable.bestday,"Best Day Of My Life","Rocks",1);
            Music s10 = new Music(R.drawable.bestdayofmylife,"Best Day Ever","Classic",4);
            Music s11 = new Music(R.drawable.timetosleep,"Time To SLeep","Relax",3);
            Music s12 = new Music(R.drawable.bos, "Both Of Us", "Romance",2);*/

            musics.add(s1);
            musics.add(s2);
           /* musics.add(s3);
            musics.add(s4);
            musics.add(s5);
            musics.add(s6);
            musics.add(s7);
            musics.add(s8);
            musics.add(s9);
            musics.add(s10);
            musics.add(s11);
            musics.add(s12);*/
        }
    }

    AdapterView.OnItemClickListener onCardItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getActivity(), "HELLO", Toast.LENGTH_SHORT).show();

            /*Intent intent = new Intent(getActivity(), MusicPlayerActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("music_name",musicName.getText());


            getActivity().overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);*/

        }
    };
}
	

	

	

	
	
	
	
	
	


	

	


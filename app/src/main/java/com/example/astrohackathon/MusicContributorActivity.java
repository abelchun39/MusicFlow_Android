package com.example.astrohackathon;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.astrohackathon.adapter.ContributorListAdapter;
import com.example.astrohackathon.adapter.ContributorSelectedAdapter;
import com.example.astrohackathon.model.Contributor;
import com.nhaarman.listviewanimations.appearance.simple.SwingRightInAnimationAdapter;

import java.util.ArrayList;

/**
 * Created by chunqhai on 10/11/2014.
 */

public class MusicContributorActivity extends Activity implements AdapterView.OnItemClickListener{

    ContributorListAdapter contributerAdapter;
    ContributorSelectedAdapter selectionAdapter;


    ArrayList<Contributor> contributors;
    ListView lv;
    LinearLayout ll;
    RelativeLayout rl;

    int [] dummyIcon = {R.drawable.ic_home,R.drawable.ic_launcher,R.drawable.ic_communities,R.drawable.ic_communities,R.drawable.ic_whats_hot,R.drawable.ic_people,R.drawable.ic_photos,R.drawable.ic_people,R.drawable.ic_communities};
    String [] dummyUser = new String[]{"Henry","Darren Yaw","Vishen Lakshini","Taylor Swift", "Alex Wong Wai Chun","Daniel Ong Zhi Yong","Melissa Yeoh Shu Fan","Chew Han Lim","Henry Chew"};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributeradder);
        lv = (ListView) findViewById(R.id.lv);
        ll = (LinearLayout) findViewById(R.id.list_contributor_selected);

/*
        rl = (RelativeLayout) findViewById(R.id.item_contributor);
*/

        Contributor c1 = new Contributor(R.drawable.contributors_profile_add_musicslyrics, "Henry");
        Contributor c2 = new Contributor(R.drawable.contributors_profile_add_musicslyrics, "Darren Yaw");
        Contributor c3 = new Contributor(R.drawable.contributors_profile_add_musicslyrics, "Vishen Lakshini");
        Contributor c4 = new Contributor(R.drawable.contributors_profile_add_musicslyrics, "Taylor Swift");
        Contributor c5 = new Contributor(R.drawable.contributors_profile_add_musicslyrics, "Alex Wong Wai Chun");
        Contributor c6 = new Contributor(R.drawable.contributors_profile_add_musicslyrics, "Daniel Ong Zhi Yong");
        Contributor c7 = new Contributor(R.drawable.contributors_profile_add_musicslyrics, "Melissa Yeoh Shu Fan");
        Contributor c8 = new Contributor(R.drawable.contributors_profile_add_musicslyrics, "Chew Han Lim");
        Contributor c9 = new Contributor(R.drawable.ic_communities, "Henry Chew");

        contributors = new ArrayList<Contributor>();

        for(int i = 0;i<dummyIcon.length;i++){
            contributors.add(new Contributor(dummyIcon[i],dummyUser[i]));
        }

        contributerAdapter = new ContributorListAdapter(MusicContributorActivity.this,contributors);


        SwingRightInAnimationAdapter swingRightInAnimationAdapter = new SwingRightInAnimationAdapter(contributerAdapter);
        swingRightInAnimationAdapter.setAbsListView(lv);

        assert swingRightInAnimationAdapter.getViewAnimator() != null;
        swingRightInAnimationAdapter.getViewAnimator().setInitialDelayMillis(300);

        lv.addHeaderView(new View((MusicContributorActivity.this)));
        lv.addFooterView(new View((MusicContributorActivity.this)));

        lv.setAdapter(swingRightInAnimationAdapter);


        lv.setOnItemClickListener(this);


        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(true);

        getActionBar().setTitle("Contributor List");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch( item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_done:
                Intent intent = new Intent();
                setResult(MusicRecorderActivity.REQUEST_ADD_CONTRIBUTOR,intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.contributor, menu);
        return super.onCreateOptionsMenu(menu);
    }




    @Override
    public void onItemClick(AdapterView<?> adapterView, View v, int position, long l) {
//        Toast.makeText(MusicContributorActivity.this,"Clicked !",Toast.LENGTH_SHORT).show();
        int pos  = lv.getPositionForView(v);
        final Drawable selectedImage = ((ImageView) v.findViewById(R.id.contributor_image)).getDrawable();
        final String selectedName = ((TextView)v.findViewById(R.id.contributor_name)).getText().toString();


        View view = getLayoutInflater().inflate(R.layout.item_contributor_selected,null);
        ImageView image = (ImageView) view.findViewById(R.id.contributor_image);
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.contributor_action_delete);

        image.setImageDrawable(selectedImage);



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View listView = getLayoutInflater().inflate(R.layout.item_contributor_list,null);
                ImageView listImage = (ImageView) listView.findViewById(R.id.contributor_image);
                TextView listText = (TextView) listView.findViewById(R.id.contributor_name);

                listImage.setImageDrawable(selectedImage);
                listText.setText(selectedName);

                for(int i =0; i<dummyIcon.length;i++){
                    if(dummyUser[i].equals(selectedName)){
                        contributors.add(new Contributor(dummyIcon[i],selectedName));
                        break;
                    }
                }

                contributerAdapter.notifyDataSetChanged();
                lv.invalidate();

                ll.removeView(view);
            }
        });
        ll.addView(view);
        contributors.remove(position - 1);
        contributerAdapter.notifyDataSetChanged();
        ll.invalidate();

    }

}

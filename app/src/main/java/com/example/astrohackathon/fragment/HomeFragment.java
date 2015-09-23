package com.example.astrohackathon.fragment;

import android.app.Fragment;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.astrohackathon.BaseActivity;
import com.example.astrohackathon.Constants;
import com.example.astrohackathon.R;
import com.example.astrohackathon.adapter.VerticalAdapter;
import com.example.astrohackathon.model.MainMusicRecord;
import com.example.astrohackathon.utils.DummyDataHelper;
import com.example.astrohackathon.webservice.AuthWS;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by chunqhai on 9/27/2014.
 */

public class HomeFragment extends Fragment {

    public static boolean isHorizontalMoved = false;

    String TAG = "HomeFragment";
    DummyDataHelper dummyData;
    ArrayList<MainMusicRecord> mainMusics;
    VerticalAdapter adapter;
    ListView verticalView;
    HorizontalScrollView horizontalScrollView;
    LinearLayout centerLinearLayout;
    TextView middleTitleView;
    Animation fadeInAnimation,fadeOutAnimation;
    RelativeLayout centerWrapper;

    String [] middleTitles = new String[]{"Songs","Event","Lyrics","TimeLine","Music","Artists","Explore",""};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_new_home, container, false);


       /* HorizontalListView horizontalListView = (HorizontalListView) v.findViewById(R.id.toplist);
        HorizontalListView bottomListView  = (HorizontalListView) v.findViewById(R.id.bottomlist);*/
        verticalView = (ListView) v.findViewById(R.id.listview);
        horizontalScrollView =  (HorizontalScrollView)v.findViewById(R.id.horizontalScrollView);
        centerLinearLayout =  (LinearLayout)v.findViewById(R.id.centerLinearLayout);
        middleTitleView = (TextView) v.findViewById(R.id.middleTitleView);
        centerWrapper = (RelativeLayout) v.findViewById(R.id.centerWrapper);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int duration = 300;
        fadeInAnimation = new AlphaAnimation(0,1);
        fadeInAnimation.setDuration(duration*2);
        fadeInAnimation.setStartOffset(duration);
        fadeOutAnimation = new AlphaAnimation(1,0);
        fadeOutAnimation.setDuration(duration);

        adapter = new VerticalAdapter(getActivity(),verticalView,centerLinearLayout);
        verticalView.setAdapter(adapter);

        verticalView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if(scrollState != AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){

                    if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                        verticalView.post(new Runnable() {
                            @Override
                            public void run() {
                                verticalView.smoothScrollToPosition(verticalView.getFirstVisiblePosition(), 0);
                            }
                        });


                        if (centerWrapper.getVisibility() != View.VISIBLE) {
                            centerWrapper.startAnimation(fadeInAnimation);
                            centerWrapper.setVisibility(View.VISIBLE);
                        }
                    }
                }
                else {
                    if(centerWrapper.getVisibility()== View.VISIBLE) {
                        centerWrapper.startAnimation(fadeOutAnimation);
                        centerWrapper.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

//        verticalView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return true;
//            }
//        });
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {

                while (centerLinearLayout.getWidth() == 0 || verticalView.getHeight()==0){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                ViewGroup.LayoutParams layoutParams = middleTitleView.getLayoutParams();

                verticalView.setDividerHeight(middleTitleView.getHeight());


                int width = BaseActivity.screenWitdhPixel/3;
                layoutParams.width = width;

                for(int i=0;i<middleTitles.length;i++){
                    TextView textView = new TextView(getActivity());
                    textView.setLayoutParams(layoutParams);
                    textView.setText(middleTitles[i]);
                    textView.setTextColor(Color.WHITE);
                    textView.setTextSize(15);
                    textView.setGravity(Gravity.CENTER);

                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            smoothScrollCenterLayout(view);
                        }
                    });

                    centerLinearLayout.addView(textView);
                }

                verticalView.invalidateViews();

//                for(int i =0;i<centerLinearLayout.getChildCount();i++){
//                    TextView textView = (TextView) centerLinearLayout.getChildAt(i);
//
//                    Log.d("verticalView", "textView.getWidth(): "+i+" " + textView.getWidth());
//                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        horizontalScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d("onDrag","horizontalScrollView: "+motionEvent.getAction());

                if(motionEvent.getAction() == MotionEvent.ACTION_UP ||motionEvent.getAction() == MotionEvent.ACTION_CANCEL)
                    smoothScrollCenterLayout(null);

                return false;
            }
        });

//        ;DragListener(new View.OnDragListener() {
//            @Override
//            public boolean onDrag(View view, DragEvent dragEvent) {
//                Log.d("onDrag","dragEvent: "+dragEvent);
//
//                return false;
//            }
//        });


        /*new getMusicListTask().execute();*/

    }

    private void smoothScrollCenterLayout(final View view){

        Log.d("HorizontalScrollView", "scrollView.getMaxScrollAmount(): " + horizontalScrollView.getMaxScrollAmount());
        Log.d("HorizontalScrollView", "scrollView.getScrollX(): " + horizontalScrollView.getScrollX());



        horizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                int width = BaseActivity.screenWitdhPixel/3;
                int position = horizontalScrollView.getScrollX()/width;

                if(view != null){
//                    Toast.makeText(getActivity(),"view.getX: "+view.getX(),Toast.LENGTH_SHORT).show();

                    if(view.getX()-horizontalScrollView.getScrollX()+width<BaseActivity.screenWitdhPixel*0.4)
                        position--;
                    else if(view.getX()-horizontalScrollView.getScrollX()+width>BaseActivity.screenWitdhPixel*0.4)
                        position++;
                }
                else if(horizontalScrollView.getScrollX()%width > width/2)
                    position++;

                horizontalScrollView.smoothScrollTo(position * width, 0);
            }
        });

    }

    public class getMusicListTask extends AsyncTask<String, Integer, String>
    {
        public getMusicListTask(){}

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            String result = "";
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(Constants.WEB_SERVICE_URL + "users/ajax/gethome");

            try {
                HttpResponse response = client.execute(post);

                InputStream is = null;

                try {
                    if (response == null) {

                    }else {
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();
                    }
                }catch (Exception e ){
                    Log.e(TAG, "Error in http connection " + e.toString());
                }

                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                }catch (Exception e ){
                    Log.e(TAG, "Error converting result " + e.toString());
                }
            }
            catch( UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
            catch(Exception e) {}
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e(TAG, result);

            mainMusics = new ArrayList<MainMusicRecord>();

            try {
                JSONObject obj = new JSONObject(result);
                JSONArray contentObject= obj.getJSONArray("content");
                JSONArray resultParsed = (JSONArray) contentObject.get(0);

                if (resultParsed.length() == 0) return;
                for (int i = 0; i < resultParsed.length(); i++) {
                    JSONObject resultItem = (JSONObject) resultParsed.get(i);

                    int id = Integer.parseInt(resultItem.get("id").toString());
                    int user_id = Integer.parseInt(resultItem.get("user_id").toString());
                    String description = resultItem.get("description").toString();
                    String music_cover = resultItem.get("music_cover").toString();
                    String music_audio = resultItem.get("music_audio").toString();
                    String created_at = resultItem.get("created_at").toString();
                    String updated_at = resultItem.get("updated_at").toString();
                    String deleted_at = resultItem.get("deleted_at").toString();
                    int active = Integer.parseInt(resultItem.get("active").toString());

                    Toast.makeText(getActivity(),
                            "id: " + id +
                                    "\nuser_id: " + user_id +
                                    "\ndescription: " + description +
                                    "\nmusic_cover: " + music_cover +
                                    "\nmusic_audio: " + music_audio +
                                    "\ncreated_at: " + created_at +
                                    "\nupdated_at: " + updated_at +
                                    "\ndeletd_at: " + deleted_at +
                                    "\nactive: " + active,
                            Toast.LENGTH_LONG).show();
                }



            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


}





/*LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

// Create LinearLayout Element
LinearLayout ll = new LinearLayout(getActivity());
ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.MATCH_PARENT));

        // Create Image
        ImageView img = new ImageView(getActivity());
        float pixel = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        int pixels = (int) pixel;
        img.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,pixels));
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        img.setImageResource(R.drawable.ic_home);

        //Create FrameLayout
        FrameLayout fl = new FrameLayout(getActivity());
        fl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.MATCH_PARENT));

        //Create View
        View view = new View(getActivity());
        float viewPixel = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
        int viewHeight = (int)viewPixel;
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,viewHeight));

        //Create TextView
        TextView tView = new TextView(getActivity());
        float textPixel = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());

        tView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT,Gravity.CENTER));
        tView.setTextSize(textPixel);
        tView.setText("Hello");
        tView.setTypeface(Typeface.SANS_SERIF);

        fl.addView(view);
        fl.addView(tView);

        ll.addView(img);
        ll.addView(fl);*/

/*
// ItemHorizontal
Resources r = getResources();
float pxLeftMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, r.getDisplayMetrics());
float pxTopMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics());
float pxRightMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, r.getDisplayMetrics());
float pxBottomMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics());

HorizontalListView hv = new HorizontalListView(getActivity(),null);
float pixel = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 110, getResources().getDisplayMetrics());

int pixels = (int) pixel;
int horizontalId = R.id.horizontal_item;

hv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, pixels, 0.45f));
        hv.setId(horizontalId);

        params.setMargins((int)pxLeftMargin,(int)pxTopMargin,(int)pxRightMargin,(int)pxBottomMargin);


        ll.addView(hv);

        scrollView.addView(ll);



        for (int i = 0; i < adapterCount; i++) {

        View item = adapter.getView(i, null,null);
        ll.addView(item);
        }*/

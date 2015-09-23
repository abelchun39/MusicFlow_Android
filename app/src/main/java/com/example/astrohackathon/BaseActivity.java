package com.example.astrohackathon;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.astrohackathon.adapter.DrawerListAdapter;
import com.example.astrohackathon.fragment.HomeFragment;
import com.example.astrohackathon.fragment.MusicFeedFragment;
import com.example.astrohackathon.model.NavDrawerItem;

public class BaseActivity extends ActionBarActivity {
	
	DrawerLayout mDrawerLayout;
	ListView mDrawerList;
	ActionBarDrawerToggle mDrawerToggle;
	
	
	// Nav Drawer Title
	CharSequence mDrawerTitle;
	
	// Used to store app title
	CharSequence mTitle;
	
	// Slide Menu Items
	String[] navMenuTitles;
	TypedArray navMenuIcons;
	
	ArrayList<NavDrawerItem> navDrawerItems;
	DrawerListAdapter adapter;

    public static int screenWitdhPixel,screenHeightPixel;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle = mDrawerTitle = getTitle();

//        getSupportActionBar().setBackgroundDrawable(null);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWitdhPixel = metrics.widthPixels;
        screenHeightPixel = metrics.heightPixels;

        //Load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        
        // nav drawer icons from resources
        navMenuIcons = getResources()
        		.obtainTypedArray(R.array.nav_drawer_icons);
        
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        mDrawerList.setOnItemClickListener(SlideMenuClickListener);


        navDrawerItems = new ArrayList<NavDrawerItem>();

        for(int i =0; i<navMenuTitles.length;i++){
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[i], navMenuIcons.getResourceId(i, -1)));
        }

        // Recycle the typed array
        navMenuIcons.recycle();
        
        // list_slidermenuting the nav drawer list adapter
        adapter = new DrawerListAdapter(getApplicationContext(),
        		navDrawerItems);
        mDrawerList.setAdapter(adapter);
        
        //Enabling action bar app icon and behaving it as toggle
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
        			R.drawable.ic_drawer,// nav menu toggle icon
        			R.string.app_name, // nav drawer open
        			R.string.app_name // nav close)
        ){
        	public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
        		//calling onPreparedOptionsMenu() to show action bar
        		invalidateOptionsMenu();
        	}
        	
        	public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
        		//calling onPreparedOptionsMenu() to hide action bar
        		invalidateOptionsMenu();
        	}
        };
        
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        if (savedInstanceState == null) {
        	//on first time display view for first nav item
        	displayView(0);
        }
        
    }
	
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.

         getMenuInflater().inflate(R.menu.main, menu);
	        return super.onCreateOptionsMenu(menu);
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle action bar item clicks here. The action bar will
	        // automatically handle clicks on the Home/Up button, so long
	        // as you specify a parent activity in AndroidManifest.xml.
	    	if (mDrawerToggle.onOptionsItemSelected(item))
	    		return true;
	    	
	    	int id = item.getItemId();
            switch (id){
                case R.id.action_lyric:
                    Intent i = new Intent(this,MusicLyricsActivity.class);
                    startActivity(i);

                    break;
                case R.id.action_record:
                    Intent intent = new Intent(this,MusicRecorderActivity.class);
                    startActivity(intent);
                    break;
                case R.id.action_sing:
                    break;
            }
	        return super.onOptionsItemSelected(item);
	    }
	    
	OnItemClickListener SlideMenuClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			displayView(position);
			
		}

		
	};
	
	
	private void displayView(int position) {
		// Update Main Content by replacing fragments
		Fragment fragment = null;
		switch(position)
		{
		case 0:
            fragment = new HomeFragment();

            break;
        case 1:
            fragment = new MusicFeedFragment();

            break;
        case 2:


            break;
        case 3:
            Intent IntentDetail = new Intent(this, MusicPlayerActivity.class);
            IntentDetail.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(IntentDetail);
            overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);



            break;
        case 4:



            break;
        case 5:

            break;
 
        default:
            break;
		}
		
		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
			.replace(R.id.frame_container, fragment).commit();
		
			//update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(Gravity.START);
		}else {
			//error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
		
	}
	
	@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	// if nav drawer is opened, hide the action items
    	boolean drawerOpen = mDrawerLayout.isDrawerOpen(Gravity.START);
    	menu.findItem(R.id.action_settings).setVisible(!drawerOpen);

    	return super.onPrepareOptionsMenu(menu);
    }
    
    @Override
    public void setTitle(CharSequence title) {
    	mTitle = title;
    	super.setTitle(title);
        getSupportActionBar().setTitle(mTitle);
    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
    	super.onPostCreate(savedInstanceState);
    	// Sync the toggle state after onRestoreInstance has occured.
    	mDrawerToggle.syncState();
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    	super.onConfigurationChanged(newConfig);
    	// Pass any configuration change to the drawer toggle
    	mDrawerToggle.onConfigurationChanged(newConfig);
    }
}

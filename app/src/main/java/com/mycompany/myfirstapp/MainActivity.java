package com.mycompany.myfirstapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private String[] mPlanetTitles;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);

        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);



        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };


        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        /*---------------------------------------------------*/


        Typeface titleTF = Typeface.createFromAsset(getAssets(), "PFDinDisplayPro-Light.ttf");
        SpannableStringBuilder ss = new SpannableStringBuilder("Notes List");
        ss.setSpan(new CustomTypefaceSpan("", titleTF), 0, ss.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);



        /*----------ActionBar------------------------------------*/
        // Update the action bar title with the TypefaceSpan instance
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(ss);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        /*--------------------------------------------------------*/



        final SharedPreferences sharedPref = getSharedPreferences("eventsFile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int numOfNotes = sharedPref.getInt("NumberOfNotes", 0);

        if(numOfNotes == 0) {
            editor.putInt("NumberOfNotes", 0);
            editor.commit();
        }



       // List<String> listItems = new ArrayList<>();

//        for(int i = 1; i <= numOfNotes; i++)
//        {
//            String string = sharedPref.getString(Integer.toString(i)+"title", "");
//            if(!string.equals("")) {
//                listItems.add(string);
//            }
//        }

        Map<String, Object> map = (Map<String, Object>)sharedPref.getAll();

        mAdapter = new MyAdapter(map);


        mAdapter.setOnItemClickListener(new MyAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, String data) {
                String key = data.split(":")[0];

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove(key+"title");
                editor.remove(key+"note");
                editor.commit();

            }
        });

        Typeface tf = Typeface.createFromAsset(getAssets(), "PFDinDisplayPro-Thin.ttf");
        mAdapter.setTypeface(tf);
        mRecyclerView.setAdapter(mAdapter);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
//            case R.id.action_add:
//                addEvent(null);
//                return true;
            case R.id.action_settings:
                //openSettings();
                return true;



            default:
                return super.onOptionsItemSelected(item);
        }
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

     //   return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        setContentView(R.layout.recycler_view);


          /*----------ActionBar------------------------------------*/
        Typeface titleTF = Typeface.createFromAsset(getAssets(), "PFDinDisplayPro-Light.ttf");
        SpannableStringBuilder ss = new SpannableStringBuilder("Notes");
        ss.setSpan(new CustomTypefaceSpan("", titleTF), 0, ss.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // Update the action bar title with the TypefaceSpan instance
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(ss);
        /*--------------------------------------------------------*/


        /*------------DrawerList------------------------------*/
        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };



        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);


        /*---------------------------------------------------*/

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final SharedPreferences sharedPref = getSharedPreferences("eventsFile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int numOfNotes = sharedPref.getInt("NumberOfNotes", 0);

        if(numOfNotes == 0) {
            editor.putInt("NumberOfNotes", 0);
            editor.commit();
        }



        List<String> listItems = new ArrayList<>();

        for(int i = 1; i <= numOfNotes; i++)
        {
            String string = sharedPref.getString(Integer.toString(i)+"title", "");
            if(!string.equals("")) {
                listItems.add(string);
            }
        }

        Map<String, Object> map = (Map<String, Object>)sharedPref.getAll();

        mAdapter = new MyAdapter(map);

        mAdapter.setOnItemClickListener(new MyAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, String data) {
                String key = data.split(":")[0];
//
//                SharedPreferences.Editor editor = sharedPref.edit();
//                editor.remove(key + "title");
//                editor.remove(key + "note");
//                editor.commit();

                checkDetail(view, key);


            }
        });

        Typeface tf = Typeface.createFromAsset(getAssets(), "PFDinDisplayPro-Thin.ttf");
        mAdapter.setTypeface(tf);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }




    public void addEvent(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        startActivityForResult(intent, 0);

    }

    public void checkDetail(View view, String key) {
        Intent intent = new Intent(this, DetailActivity.class);

        intent.putExtra("DETAIL_KEY", key);
        startActivity(intent);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        //Fragment fragment = new PlanetFragment();
        //Bundle args = new Bundle();
        //args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        //fragment.setArguments(args);

        ////FragmentManager fragmentManager = getFragmentManager();
        //fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }


}



package com.mycompany.myfirstapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {


    private ListView lv;


    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);

//        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
//        mRecyclerView.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//
//        //lv = (ListView) findViewById(R.id.lv);//得到ListView对象的引用
//
//
//        SharedPreferences sharedPref = getSharedPreferences("eventsFile", Context.MODE_PRIVATE);
//        int numOfNotes = sharedPref.getInt("NumberOfNotes", 0);
//
//        //ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
//
//        List<String> listItems = new ArrayList<>();
//
//        for(int i = 1; i <= numOfNotes; i++)
//        {
//            listItems.add(sharedPref.getString(Integer.toString(i), "WRONG!"));
//        }
//
//        mAdapter = new MyAdapter(listItems);
//        mRecyclerView.setAdapter(mAdapter);





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

        switch (item.getItemId()) {
            case R.id.action_add:
                addEvent();
                return true;
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
        //setContentView(R.layout.activity_main);

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
            String string = sharedPref.getString(Integer.toString(i), "");
            if(!string.equals("")) {
                listItems.add(string);
            }
        }

        Map<String, Object> map = (Map<String, Object>)sharedPref.getAll();

        mAdapter = new MyAdapter(map);
        mRecyclerView.setAdapter(mAdapter);

        //mAdapter.setKeyValueMap(map);
        mAdapter.setOnItemClickListener(new MyAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, String data) {
                String key = data.split(":")[0];

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove(key);
                editor.commit();

            }
        });

        //lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems));



    }


    public void addEvent() {
        Intent intent = new Intent(this, DisplayMessageActivity.class);

        startActivityForResult(intent, 0);

    }



}



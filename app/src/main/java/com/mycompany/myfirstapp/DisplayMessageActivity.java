package com.mycompany.myfirstapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class DisplayMessageActivity extends Activity {

    private ListView lv;

    public final static String EVENT = "com.mycompany.myfirstapp.EVENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, MainActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();

        SharedPreferences sharedPref = getSharedPreferences("eventsFile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        System.out.println("Here: " + message);

        if(!message.isEmpty()) {
            int numOfNotes = sharedPref.getInt("NumberOfNotes", 0);
            editor.putInt("NumberOfNotes", numOfNotes + 1);
            editor.commit();
            editor.putString(Integer.toString(numOfNotes + 1), message);
            editor.commit();
        }


        startActivity(intent);
        finish();
    }
}

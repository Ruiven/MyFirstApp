package com.mycompany.myfirstapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class DisplayMessageActivity extends Activity {

    EditText editText;
    private Button  date_dialog;
    private static  int  DATE_PICKER=0;
    private int mYear = -1;
    private int mMonth = -1;
    private int mDate = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
    }

    @Override
    public void onResume() {
        super.onResume();
        editText = (EditText) findViewById(R.id.edit_message);


        Typeface titleTF = Typeface.createFromAsset(getAssets(), "PFDinDisplayPro-Light.ttf");
        SpannableStringBuilder ss = new SpannableStringBuilder("New Note");
        ss.setSpan(new CustomTypefaceSpan("", titleTF), 0, ss.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(ss);

        View view = findViewById(R.id.activity_line);
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        date_dialog=(Button)findViewById(R.id.date_dialog);
        date_dialog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_PICKER);
            }
        });
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

        if(id == R.id.action_confirm) {
            sendMessage(null);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub

        Calendar calendar= Calendar.getInstance();
        Dialog dateDialog=new DatePickerDialog(DisplayMessageActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        //Toast.makeText(DisplayMessageActivity.this,year + "年" + (monthOfYear+1) + "月" + dayOfMonth + "日" , Toast.LENGTH_SHORT).show();
                        mYear = year;
                        mMonth = monthOfYear;
                        mDate = dayOfMonth;


                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));


        return dateDialog;
    }


    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, MainActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        EditText title_editText = (EditText) findViewById(R.id.edit_title);
        String title = title_editText.getText().toString();
        String message = editText.getText().toString();

        SharedPreferences sharedPref = getSharedPreferences("eventsFile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        if(!title.isEmpty() || !message.isEmpty()) {
            int numOfNotes = sharedPref.getInt("NumberOfNotes", 0);
            editor.putInt("NumberOfNotes", numOfNotes + 1);
            editor.putString(Integer.toString(numOfNotes + 1) + "title", title);
            editor.putString(Integer.toString(numOfNotes+1) + "note", message);


            String textDate = String.format("%02d%02d%04d", mMonth, mDate, mYear);
            editor.putString(Integer.toString(numOfNotes+1) + "date", textDate);
            editor.commit();
        }



        startActivity(intent);
        finish();
    }



}



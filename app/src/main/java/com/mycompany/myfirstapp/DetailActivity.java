package com.mycompany.myfirstapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DetailActivity extends Activity {

    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }


    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String key = intent.getStringExtra("DETAIL_KEY");
        this.key = key;
        final SharedPreferences sharedPref = getSharedPreferences("eventsFile", Context.MODE_PRIVATE);


        String title = sharedPref.getString(key + "title", "");
        String note = sharedPref.getString(key + "note", "");
        String date = sharedPref.getString(key + "date", "");


        TextView titleTextView = (TextView) findViewById(R.id.detail_title);
        titleTextView.setText(title);
        TextView noteTextView = (TextView) findViewById(R.id.detail_note);
        noteTextView.setText(note);
        if(!date.startsWith("-1")) {
            TextView dateTextView = (TextView) findViewById(R.id.detail_date);
            dateTextView.setText("Due Date: " + convertDate(date));
        }
        else {
            TextView dateTextView = (TextView) findViewById(R.id.detail_date);
            dateTextView.setText("");
        }

        Typeface titleTF = Typeface.createFromAsset(getAssets(), "PFDinDisplayPro-Light.ttf");
        SpannableStringBuilder ss = new SpannableStringBuilder("Detail");
        ss.setSpan(new CustomTypefaceSpan("", titleTF), 0, ss.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(ss);

        View view = findViewById(R.id.activity_line);
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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

        if(id == R.id.action_delete) {
            final SharedPreferences sharedPref = getSharedPreferences("eventsFile", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.remove(key+"title");
            editor.remove(key+"note");
            editor.commit();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(DetailActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    private String convertDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
        String out = null;
        try {
            Date dateD = sdf.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateD);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int date = cal.get(Calendar.DATE);

            String monthStr = null;

            switch (month) {
                case 1:
                    monthStr = "Jan.";
                    break;
                case 2:
                    monthStr = "Feb.";
                    break;
                case 3:
                    monthStr = "Mar.";
                    break;
                case 4:
                    monthStr = "Apr.";
                    break;
                case 5:
                    monthStr = "May";
                    break;
                case 6:
                    monthStr = "Jun.";
                    break;
                case 7:
                    monthStr = "Jul.";
                    break;
                case 8:
                    monthStr = "Aug.";
                    break;
                case 9:
                    monthStr = "Sept.";
                    break;
                case 10:
                    monthStr = "Oct.";
                    break;
                case 11:
                    monthStr = "Nov.";
                    break;
                case 12:
                    monthStr = "Dec.";
                    break;
                default:
                    break;
            }

            out = monthStr + " " + Integer.toString(date) + ", " + Integer.toString(year);

        }catch (Exception e) {
            //TODO
        }
        return out;
    }
}

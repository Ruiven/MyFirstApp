package com.mycompany.myfirstapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.provider.CalendarContract;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Ruiwen on 2/18/15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener{


    private Map<String, Object> map = new HashMap<>();
    private List<String> mdataSet = new ArrayList<>();
    private List<String> mNoteSet = new ArrayList<>();
    private List<Integer> keyList = new ArrayList<>();

    private static Typeface typeface;

    public MyAdapter(Map<String, Object> map) {



        this.map =  map;

        int max =  (int) map.get("NumberOfNotes");


        for(int i = max ; i >= 1; i--) {

            String event = (String) map.get(Integer.toString(i) + "title");
            String note = (String) map.get(Integer.toString(i) + "note");
            if(event != null) {
                Integer hash = new Random().nextInt(100000);

                String hashStr = String.format("%05d", hash);
                event = event + "=" + hashStr;
                mdataSet.add(event);
                mNoteSet.add(note);
                keyList.add(i);
            }
        }
    }


    public void setTypeface(Typeface tf) {
        typeface = tf;
    }

    public static interface OnRecyclerViewItemClickListener {
        void onClick(View view , String data);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //creating new row by inflating new row in xml
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);

        //set view to the view holder
        ViewHolder vh = new ViewHolder(v);
        v.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String event = mdataSet.get(position);
        String note = mNoteSet.get(position);
        holder.mTextView.setText(event.split("=")[0] + " " + note.substring(0,(10<note.length()?10:note.length())));
        holder.mCardView.setTag(Integer.toString(keyList.get(position)) + ":" +event);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            System.out.println("hahahah");
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onClick(v,(String)v.getTag());
            //String event = (String) v.getTag();

            //System.out.println(event.split(":")[1]);
            //removeItem(event.split(":")[1]);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onClick(v,(String)v.getTag());
            String event = (String) v.getTag();

            //System.out.println(event.split(":")[1]);
            removeItem(event.split(":")[1]);
        }
        return true;
    }

    @Override
    public int getItemCount() {
        return mdataSet.size();
    }

    public void removeItem(String data) {
        //System.out.println(data);
        int position = mdataSet.indexOf(data);
        mdataSet.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;
        public CardView mCardView;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView)v.findViewById(R.id.event_item);
            mTextView.setTypeface(typeface);
            //mTextView.setTextSize(27);
            mCardView = (CardView)v.findViewById(R.id.event_card);
        }
    }

}

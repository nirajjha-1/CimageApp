package com.stout.cimageapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stout.cimageapp.R;
import com.stout.cimageapp.models.Event_Model;

import java.util.List;

public class Event_Adapter extends BaseAdapter {

    private Context context;
    List<Event_Model> event_list;

    public Event_Adapter(Context context, List<Event_Model> event_list) {
        this.context = context;
        this.event_list = event_list;
    }





    @Override
    public int getCount() {
        return event_list.size();
    }

    @Override
    public Event_Model getItem(int i) {
        return event_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class EventViewHolder{
        TextView event_title, event_dscr;
        ImageView event_img;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        EventViewHolder event_holder;
        if(view == null){
            LayoutInflater mInflate = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflate.inflate(R.layout.event_items,null);

            event_holder = new EventViewHolder();
            event_holder.event_title = (TextView) view.findViewById(R.id.event_item_title);
            event_holder.event_dscr = (TextView) view.findViewById(R.id.event_item_dscr);
            event_holder.event_img = (ImageView) view.findViewById(R.id.event_item_img);

            view.setTag(event_holder);
        }
        else {
            event_holder = (EventViewHolder) view.getTag();
        }

        Event_Model news = event_list.get(i);

        event_holder.event_title.setText(news.getEvent_title());
        event_holder.event_dscr.setText(news.getEvent_dscr());
        Glide.with(context).load(news.getEvent_img()).into(event_holder.event_img);
        return view;
    }
}

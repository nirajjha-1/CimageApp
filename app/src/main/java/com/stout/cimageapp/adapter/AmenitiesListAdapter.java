package com.stout.cimageapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.stout.cimageapp.R;

import java.util.List;

public class AmenitiesListAdapter extends BaseAdapter {
    Context c;
    List<String> arrayData;
    public AmenitiesListAdapter(Context c,List<String> arrayData) {
        this.c =c;
        this.arrayData=arrayData;
    }

    @Override
    public int getCount() {
        return arrayData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View grid;
        LayoutInflater lf=(LayoutInflater)c.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(view==null){
            grid=new View(c);
            grid=lf.inflate(R.layout.home_amenities_card, null);
            TextView txt_info = grid.findViewById(R.id.txt_title);
            txt_info.setText(arrayData.get(i));
        }
        else {
            grid=(View)view;
        }
        return grid;
    }
}

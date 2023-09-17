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
import com.stout.cimageapp.models.University_Model;

import java.util.List;

public class University_List_Adapter extends BaseAdapter {

    private Context context;
    List<University_Model> itemList;

    public University_List_Adapter(Context context, List<University_Model> requestList) {
        this.context = context;
        this.itemList = requestList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public University_Model getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class UniversityViewHolder{
        TextView tv_id, tv_name;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        UniversityViewHolder holder;
        if(view == null){
            LayoutInflater mInflate = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflate.inflate(R.layout.spinner_two_item,null);

            holder = new UniversityViewHolder();
            holder.tv_id = (TextView) view.findViewById(R.id.tv_id);
            holder.tv_name = (TextView) view.findViewById(R.id.tv_name);

            view.setTag(holder);
        }
        else {
            holder = (UniversityViewHolder) view.getTag();
        }

        University_Model obj = itemList.get(i);
        holder.tv_id.setText(obj.getUniversityId().toString());
        holder.tv_name.setText(obj.getUniversityName().toString());
        return view;
    }
}

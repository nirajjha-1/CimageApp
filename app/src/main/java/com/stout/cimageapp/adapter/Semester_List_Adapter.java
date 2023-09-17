package com.stout.cimageapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.stout.cimageapp.R;
import com.stout.cimageapp.models.Course_Model;
import com.stout.cimageapp.models.Semester_Model;

import java.util.List;

public class Semester_List_Adapter extends BaseAdapter {

    private Context context;
    List<Semester_Model> itemList;

    public Semester_List_Adapter(Context context, List<Semester_Model> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Semester_Model getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class CourseViewHolder{
        TextView tv_id, tv_name;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CourseViewHolder holder;
        if(view == null){
            LayoutInflater mInflate = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflate.inflate(R.layout.spinner_two_item,null);

            holder = new CourseViewHolder();
            holder.tv_id = (TextView) view.findViewById(R.id.tv_id);
            holder.tv_name = (TextView) view.findViewById(R.id.tv_name);

            view.setTag(holder);
        }
        else {
            holder = (CourseViewHolder) view.getTag();
        }

        Semester_Model obj = itemList.get(i);
        holder.tv_id.setText(obj.getSemesterId().toString());
        holder.tv_name.setText(obj.getSemesterName().toString());
        return view;
    }
}

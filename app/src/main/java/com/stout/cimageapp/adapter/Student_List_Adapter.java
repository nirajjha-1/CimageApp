package com.stout.cimageapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.stout.cimageapp.R;
import com.stout.cimageapp.models.Semester_Model;
import com.stout.cimageapp.models.Student_List_Model;

import java.util.List;

public class Student_List_Adapter extends BaseAdapter {
    private Context context;
    List<Student_List_Model> itemList;

    public Student_List_Adapter(Context context, List<Student_List_Model> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Student_List_Model getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class CourseViewHolder{
        TextView tv_stud_name, tv_stud_id, tv_stud_course, tv_stud_university, tv_stud_semester, tv_stud_father_name, tv_stud_mob_number, tv_stud_address;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CourseViewHolder holder;
        if(view == null){
            LayoutInflater mInflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = mInflate.inflate(R.layout.show_student_list_items,null);

            holder = new CourseViewHolder();
            holder.tv_stud_name = (TextView) view.findViewById(R.id.tv_stud_name);
            holder.tv_stud_id = (TextView) view.findViewById(R.id.tv_stud_id);
            holder.tv_stud_course = (TextView) view.findViewById(R.id.tv_stud_course);
            holder.tv_stud_university = (TextView) view.findViewById(R.id.tv_stud_university);
            holder.tv_stud_semester = (TextView) view.findViewById(R.id.tv_stud_semester);
            holder.tv_stud_father_name = (TextView) view.findViewById(R.id.tv_stud_father_name);
            holder.tv_stud_mob_number = (TextView) view.findViewById(R.id.tv_stud_mob_number);
            holder.tv_stud_address = (TextView) view.findViewById(R.id.tv_stud_address);

            view.setTag(holder);
        }
        else {
            holder = (CourseViewHolder) view.getTag();
        }

        Student_List_Model obj = itemList.get(i);

        holder.tv_stud_name.setText(obj.getStud_name().toString());
        holder.tv_stud_id.setText(obj.getStud_id().toString());
        holder.tv_stud_course.setText(obj.getStud_course().toString());
        holder.tv_stud_university.setText(obj.getStud_university().toString());
        holder.tv_stud_semester.setText(obj.getStud_semester().toString());
        holder.tv_stud_father_name.setText(obj.getStud_father_name().toString());
        holder.tv_stud_mob_number.setText(obj.getStud_mob_number().toString());
        holder.tv_stud_address.setText(obj.getStud_address().toString());

        return view;
    }
}

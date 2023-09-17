package com.stout.cimageapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stout.cimageapp.R;
import com.stout.cimageapp.models.StudentAttendanceListModel;
import com.stout.cimageapp.models.Student_List_Model;

import java.util.ArrayList;
import java.util.List;

public class AttendanceRecyclerViewAdapter extends RecyclerView.Adapter<AttendanceRecyclerViewAdapter.ViewHolder> {
    Context context;
    List<StudentAttendanceListModel> getDataAdapter;

    public AttendanceRecyclerViewAdapter(Context context, List<StudentAttendanceListModel> getDataAdapter) {
        super();
        this.context = context;
        this.getDataAdapter = getDataAdapter;
    }

    @NonNull
    @Override
    public AttendanceRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_attendance_list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceRecyclerViewAdapter.ViewHolder holder, int position) {
        final StudentAttendanceListModel getDataAdapter1 = getDataAdapter.get(position);

        holder.tv_stud_id.setText(getDataAdapter1.getStudentId());
        holder.tv_stud_name.setText(getDataAdapter1.getStudent_name());
        holder.tv_attendance_status.setText(getDataAdapter1.getAttedance_status());

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.checkBox.isChecked()){
                    holder.tv_attendance_status.setText("Present");
                    getDataAdapter1.setAttedance_status("Present");
                }
                else if(!holder.checkBox.isChecked()){
                    holder.tv_attendance_status.setText("Absent");
                    getDataAdapter1.setAttedance_status("Absent");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return getDataAdapter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_stud_id, tv_stud_name, tv_attendance_status;
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_stud_id = (TextView) itemView.findViewById(R.id.attendance_txt_stud_id);
            tv_stud_name = (TextView) itemView.findViewById(R.id.attendance_txt_stud_name);
            tv_attendance_status = (TextView) itemView.findViewById(R.id.attendance_status);

            checkBox = (CheckBox) itemView.findViewById(R.id.attendance_checkbox);

            itemView.setTag(itemView);
        }
    }
}

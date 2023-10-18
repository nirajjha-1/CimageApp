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

import java.util.List;


public class AttendanceListAdapter extends RecyclerView.Adapter<AttendanceListAdapter.ViewHolder> {
    Context context;
    List<StudentAttendanceListModel> getDataAdapter;

    public AttendanceListAdapter(Context context, List<StudentAttendanceListModel> getDataAdapter) {
        super();
        this.context = context;
        this.getDataAdapter = getDataAdapter;
    }

    @NonNull
    @Override
    public AttendanceListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_attendance_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final StudentAttendanceListModel getDataAdapter1 = getDataAdapter.get(position);

        holder.tv_stud_id.setText(getDataAdapter1.getStudentId());
        holder.tv_stud_name.setText(getDataAdapter1.getStudent_name());
        holder.tv_attendance_status.setText(getDataAdapter1.getAttedance_status());

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
            checkBox.setVisibility(View.GONE);

            itemView.setTag(itemView);
        }
    }
}
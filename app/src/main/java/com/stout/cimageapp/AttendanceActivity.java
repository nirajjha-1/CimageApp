package com.stout.cimageapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;
import com.stout.cimageapp.adapter.AttendanceListAdapter;
import com.stout.cimageapp.adapter.AttendanceRecyclerViewAdapter;
import com.stout.cimageapp.models.StudentAttendanceListModel;
import com.stout.cimageapp.utils.Config;
import com.stout.cimageapp.utils.MasterFunction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AttendanceActivity extends AppCompatActivity {

    private static final String COURSE_LIST_URL = Config.baseUrl + "cimage/fetch_course_list.php";
    private static final String TAG = AttendanceActivity.class.getName();
    private Spinner spnr_course_name, spnr_subject_name;
    private EditText editTextDate;
    private Context mContext;
    RecyclerView attendanceRecyclerView;

    RecyclerView.LayoutManager layoutManager;
    List<StudentAttendanceListModel> studentAttendanceListModels ;
    String courseId = "", subjectId = "", LoginUserId="";
    private Button btn_submit_attendance;
    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        mContext = this;
        myCalendar = Calendar.getInstance();
        editTextDate = findViewById(R.id.editTextDate);


        studentAttendanceListModels = new ArrayList<>();

        spnr_course_name = findViewById(R.id.spnr_course_name);
        spnr_subject_name = findViewById(R.id.spnr_subject);

        attendanceRecyclerView = findViewById(R.id.recyclerView);
        attendanceRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(mContext);
        attendanceRecyclerView.setLayoutManager(layoutManager);

        btn_submit_attendance = findViewById(R.id.btn_show);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }
        };

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(mContext, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        spnr_course_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_id = view.findViewById(R.id.tv_id);
                TextView tv_name = view.findViewById(R.id.tv_name);
                courseId = tv_id.getText().toString();

                HashMap hashMap = new HashMap();
                hashMap.put("course_id",courseId);

                MasterFunction.fetchSubjectList(mContext,hashMap,spnr_subject_name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnr_subject_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv_id = view.findViewById(R.id.tv_id);
                TextView tv_name = view.findViewById(R.id.tv_name);
                subjectId = tv_id.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btn_submit_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = editTextDate.getText().toString();

                if(date.isEmpty()){
                    editTextDate.setError("Select date");
                }
                else {
                    HashMap hashMap = new HashMap();
                    hashMap.put("course_id", courseId);
                    hashMap.put("subjectId",subjectId);
                    hashMap.put("date",MasterFunction.parseDateToddMMyyyy(date,"yyyyMMdd","dd/MM/yy"));

                    fetchStudentList(hashMap);
                }
            }
        });


        MasterFunction.fetchCourseList(mContext,COURSE_LIST_URL,spnr_course_name);
    }

    private void updateDate() {
        String myFormat = "dd/MM/yy"; //put your date format in which you need to display
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        editTextDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void fetchStudentList(HashMap hashMap){
        String url = Config.baseUrl + "cimage/fetch_attendance_list.php";

        MasterFunction.request(mContext, url, hashMap, true, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                studentAttendanceListModels.clear();

                try {

                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("success");

                    if(jsonArray.length()==0){
                        MasterFunction.showDialog(mContext,"Attendance Report","Attendance Not found",0);
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String stud_name = jsonObject.getString("stud_name");
                        String stud_id = jsonObject.getString("stud_id");
                        String attendance_status = jsonObject.getString("attendance_status");
                        StudentAttendanceListModel listModel = new StudentAttendanceListModel();
                        listModel.setStudent_name(stud_name);
                        listModel.setStudentId(stud_id);
                        listModel.setAttedance_status(attendance_status);
                        studentAttendanceListModels.add(listModel);
                    }

                    AttendanceListAdapter attendanceListAdapter = new AttendanceListAdapter(mContext,studentAttendanceListModels);
                    attendanceRecyclerView.setAdapter(attendanceListAdapter);

                } catch (JSONException ex) {
                    Log.d("JSONException", "onResponse: "+ex.getMessage().toString());
                }
            }
        }, false);
    }
}
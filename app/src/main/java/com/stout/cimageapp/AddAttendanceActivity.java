package com.stout.cimageapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stout.cimageapp.adapter.AttendanceRecyclerViewAdapter;
import com.stout.cimageapp.adapter.Student_List_Adapter;
import com.stout.cimageapp.models.StudentAttendanceListModel;
import com.stout.cimageapp.models.Student_List_Model;
import com.stout.cimageapp.screens.ShowStudentsListActivity;
import com.stout.cimageapp.utils.Config;
import com.stout.cimageapp.utils.MasterFunction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddAttendanceActivity extends AppCompatActivity {
    // URL
    private static final String COURSE_LIST_URL = Config.baseUrl + "cimage/fetch_course_list.php";
    private static final String TAG = AddAttendanceActivity.class.getName();
    private Spinner spnr_course_name, spnr_subject_name;
    private Context mContext;
    RecyclerView attendanceRecyclerView;

    ListView list_student;
    private List<Student_List_Model> studentlistmodel;
    RecyclerView.LayoutManager layoutManager;
    AttendanceRecyclerViewAdapter attendanceRecyclerViewAdapter;

    List<StudentAttendanceListModel> studentAttendanceListModels ;
    String courseId = "", subjectId = "", LoginUserId="";
    private Button btn_submit_attendance;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attendance);

        mContext = this;

        SharedPreferences sp = getSharedPreferences("user_info",Context.MODE_PRIVATE);
        LoginUserId = sp.getString("userId","");

        studentAttendanceListModels = new ArrayList<>();

        spnr_course_name = findViewById(R.id.spnr_attendance_course_name);
        spnr_subject_name = findViewById(R.id.spnr_attendance_subject_name);

        // Recycler View Layout Set
        attendanceRecyclerView = findViewById(R.id.add_attendance_recyclerView);
        attendanceRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(AddAttendanceActivity.this);
        attendanceRecyclerView.setLayoutManager(layoutManager);

        btn_submit_attendance = findViewById(R.id.btn_submit_attendance);
        btn_submit_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<String> status = new ArrayList<>();
                final ArrayList<String> userId = new ArrayList<>();
                final ArrayList<String> name = new ArrayList<>();

                int presentstudent = 0, absentstudent = 0;
                for(StudentAttendanceListModel number : studentAttendanceListModels){
                    status.add(number.getAttedance_status());
                    userId.add(number.getStudentId());
                    name.add(number.getStudent_name());

                    if(number.getAttedance_status() == "absent"){
                        absentstudent += 1;
                    }else if(number.getAttedance_status() == "present"){
                        presentstudent += 1;
                    }
                }

                LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                // dialog box layout Inflate
                final View view = layoutInflater.inflate(R.layout.attendance_dialog,null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setView(view);

                AlertDialog dialog;
                dialog = builder.show();

                TextView txttotalstudent = (TextView) view.findViewById(R.id.txttotalstudent);
                txttotalstudent.setText("Total Student : "+studentAttendanceListModels.size());

                TextView txtpresentstudent = (TextView) view.findViewById(R.id.txtpresentstudent);
                txtpresentstudent.setText("Present Student : "+presentstudent);

                TextView txtabsentstudent = (TextView) view.findViewById(R.id.txtabsentstudent);
                txtabsentstudent.setText("Absent Student : "+absentstudent);

                Button btnsubmit = (Button) view.findViewById(R.id.btnsubmit);

                HashMap<String,String> params = new HashMap<>();

                params.put("userId",String.valueOf(userId));
                params.put("name",String.valueOf(name));
                params.put("status",String.valueOf(status));
                params.put("course",courseId);
                params.put("subject",subjectId);
                params.put("createdBy",LoginUserId);

                btnsubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        insertAttendance(mContext,params);
                    }
                });

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

                //fetchStudentList(hashMap);
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

                HashMap hashMap = new HashMap();
                hashMap.put("courseId",courseId);
                hashMap.put("subjectId",subjectId);

                fetchStudentList(hashMap);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        MasterFunction.fetchCourseList(mContext,COURSE_LIST_URL,spnr_course_name);
    }

    void insertAttendance(Context context, HashMap hashMap){
        String url = Config.baseUrl+"cimage/insert_attendance.php";
        MasterFunction.request(context, url, hashMap, true, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("onResponse"," : "+response);
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    int success = jsonObject.getInt("success");

                    if(success == 0){
                        MasterFunction.showDialog(mContext,"Failed",message,0);
                    }else if(success ==1){
                        MasterFunction.showDialog(mContext,"Success","Attendance added successfully",1);
                    }
                    else{
                        MasterFunction.showDialog(mContext,"Error Occurred",message,0);
                    }
                }catch (JSONException ex){

                }
            }
        },false);
    }

    void fetchStudentList(HashMap hashMap) {
        String url = Config.baseUrl + "cimage/fetch_student_at_list.php";


        MasterFunction.request(mContext, url, hashMap, true, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.has("success")) {
                        JSONArray array = object.getJSONArray("success");
                        ArrayList studentlistmodel = new ArrayList<>();

                        studentAttendanceListModels.clear();

                        for (int i = 0; i < array.length(); i++) {

                            StudentAttendanceListModel st=   new StudentAttendanceListModel();

                            JSONObject jsonObject = array.getJSONObject(i);
                            String stud_name = jsonObject.getString("stud_name");
                            String stud_id = jsonObject.getString("stud_id");
                            String stud_course = jsonObject.getString("course_id");
                            String stud_university = jsonObject.getString("university_id");
                            String stud_semester = jsonObject.getString("semester_id");
                            String stud_father_name = jsonObject.getString("stud_father_name");
                            String stud_mob_number = jsonObject.getString("stud_mob_number");
                            String stud_address = jsonObject.getString("stud_address");

                            st.setStudent_name(stud_name);
                            st.setStudentId(stud_id);
                            st.setCourseId(stud_course);
                            st.setAttedance_status("absent");
                            studentAttendanceListModels.add(st);

                            // studentlistmodel.add(new Student_List_Model(stud_name, stud_id, stud_course, stud_university, stud_semester, stud_father_name, stud_mob_number, stud_address));
                        }
                    }

//                    Student_List_Adapter student_list_adapter = new Student_List_Adapter(mContext,studentlistmodel);
//                    list_student.setAdapter(student_list_adapter);

                    AttendanceRecyclerViewAdapter  student_list_adapter = new AttendanceRecyclerViewAdapter(mContext,studentAttendanceListModels);

                    attendanceRecyclerView.setAdapter(student_list_adapter);
                } catch (JSONException ex) {
                    Log.e(TAG, "onResponse: "+ex.toString() );
                }
            }
        },false);

    }

}
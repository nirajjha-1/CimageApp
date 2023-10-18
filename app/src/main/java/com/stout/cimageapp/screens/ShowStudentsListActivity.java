package com.stout.cimageapp.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.stout.cimageapp.AdminDashboardActivity;
import com.stout.cimageapp.AdminLoginActivity;
import com.stout.cimageapp.EventActivity;
import com.stout.cimageapp.GalleryActivity;
import com.stout.cimageapp.HomeActivity;
import com.stout.cimageapp.R;
import com.stout.cimageapp.adapter.Student_List_Adapter;
import com.stout.cimageapp.models.Student_List_Model;
import com.stout.cimageapp.utils.Config;
import com.stout.cimageapp.utils.MasterFunction;
import com.stout.cimageapp.utils.URLS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowStudentsListActivity extends AppCompatActivity {
    private static final String TAG = ShowStudentsListActivity.class.getName();
    private List<Student_List_Model> studentlistmodel;
    private ListView list_student;
    private Spinner spnr_course_name,spnr_semester_name;
    Button btn_show_student;
    private Context mContext;

    private String courseId ="", semesterId = "";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_students_list);

        mContext = this;

        // list view of show student list
        list_student = findViewById(R.id.show_student_list);
        spnr_course_name = findViewById(R.id.spnr_course_name);
        spnr_semester_name = findViewById(R.id.spnr_semester_name);
        btn_show_student = findViewById(R.id.btn_show_student);

        MasterFunction.fetchCourseList(mContext,URLS.FETCH_COURSE_LIST_URL,spnr_course_name);

        spnr_course_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_id = view.findViewById(R.id.tv_id);
                TextView tv_name = view.findViewById(R.id.tv_name);
                courseId = tv_id.getText().toString();

                HashMap hashMap = new HashMap();
                hashMap.put("course_id",courseId);

                MasterFunction.fetchSemesterList(mContext,URLS.FETCH_SEMESTER_LIST_URL,spnr_semester_name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_show_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap hashMap = new HashMap();
                hashMap.put("courseId",courseId);
                hashMap.put("semesterId",semesterId);

                fetchStudentList(mContext,hashMap);
            }
        });

        spnr_semester_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_id = view.findViewById(R.id.tv_id);
                TextView tv_name = view.findViewById(R.id.tv_name);
                semesterId = tv_id.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public  void bindStudent(String response){

        try {
            JSONObject object = new JSONObject(response);
            if (object.has("success")) {
                JSONArray array = object.getJSONArray("success");

                if(array.length()==0){
                    MasterFunction.showDialog(mContext,"Not Found","Student list not found!",0);
                }
                studentlistmodel = new ArrayList<>();

                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    String stud_name = jsonObject.getString("stud_name");
                    String stud_id = jsonObject.getString("stud_id");
                    String stud_course = jsonObject.getString("course_id");
                    String stud_university = jsonObject.getString("university_id");
                    String stud_semester = jsonObject.getString("semester_id");
                    String stud_father_name = jsonObject.getString("stud_father_name");
                    String stud_mob_number = jsonObject.getString("stud_mob_number");
                    String stud_address = jsonObject.getString("stud_address");
                    studentlistmodel.add(new Student_List_Model(stud_name, stud_id, stud_course, stud_university, stud_semester, stud_father_name, stud_mob_number, stud_address));
                }
            }

            Student_List_Adapter student_list_adapter = new Student_List_Adapter(ShowStudentsListActivity.this, studentlistmodel);
            list_student.setAdapter(student_list_adapter);
        } catch (JSONException ex) {
        }
    }

    public  void fetchStudentList(Context context, HashMap hashMap){

        MasterFunction.request(context, URLS.FETCH_STUDENT_LIST_URL, hashMap, true, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();
                bindStudent(response);
               //
            }
        },false);
    }

}
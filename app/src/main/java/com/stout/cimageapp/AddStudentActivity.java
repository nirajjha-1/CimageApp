package com.stout.cimageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.stout.cimageapp.adapter.Course_List_Adapter;
import com.stout.cimageapp.adapter.Semester_List_Adapter;
import com.stout.cimageapp.adapter.University_List_Adapter;
import com.stout.cimageapp.models.Course_Model;
import com.stout.cimageapp.models.Semester_Model;
import com.stout.cimageapp.models.University_Model;
import com.stout.cimageapp.utils.Config;
import com.stout.cimageapp.utils.MasterFunction;
import com.stout.cimageapp.utils.URLS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddStudentActivity extends AppCompatActivity {

    // tool bar
//    DrawerLayout drawerLayout;
//    NavigationView navigationView;
//    Toolbar toolbar;
    Context mContext;

    // defination
    private EditText edt_stud_id, edt_stud_name, edt_stud_father_name, edt_stud_mob_number, edt_stud_address;
    private Spinner spnr_university_name, spnr_course_name, spnr_semester_name;
    private Button btn_add_student;

    // Id's
    private String courseId = "";
    private String universityId = "";
    private String semesterId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        mContext = this;

        // tool bar
//        setSupportActionBar(toolbar);
//
//        drawerLayout = findViewById(R.id.drawerlayout);
//        toolbar = findViewById(R.id.my_tool_bar);
//
//        // navigation bar
//        navigationView = findViewById(R.id.navigationView);
//
//        // navigation bar item select handle
//        MasterFunction.setNavigationView(navigationView,mContext);
//
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nevigation_open, R.string.nevigation_close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();

        // Id's find
        edt_stud_id = findViewById(R.id.edt_stud_id);
        edt_stud_name = findViewById(R.id.edt_stud_name);
        edt_stud_father_name = findViewById(R.id.edt_stud_father_name);
        spnr_university_name = findViewById(R.id.spnr_university_name);
        spnr_course_name = findViewById(R.id.spnr_course_name);
        spnr_semester_name = findViewById(R.id.spnr_semester_name);
        edt_stud_mob_number = findViewById(R.id.edt_stud_mob_number);
        edt_stud_address = findViewById(R.id.edt_stud_address);
        btn_add_student = findViewById(R.id.btn_add_student);

        // Add Student Set On Click Listener
        btn_add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stud_id = edt_stud_id.getText().toString();
                String stud_name = edt_stud_name.getText().toString();
                String stud_father_name = edt_stud_father_name.getText().toString();
                String university_id = universityId;
                String course_id = courseId;
                String semester_id = semesterId;
                String stud_mob_number = edt_stud_mob_number.getText().toString();
                String stud_address = edt_stud_address.getText().toString();

                if (stud_id.isEmpty()) {
                    Toast.makeText(AddStudentActivity.this, "Please Enter Student Id", Toast.LENGTH_SHORT).show();
                } else if (stud_name.isEmpty()) {
                    Toast.makeText(AddStudentActivity.this, "Please Enter Student Name", Toast.LENGTH_SHORT).show();
                } else if (stud_father_name.isEmpty()) {
                    Toast.makeText(AddStudentActivity.this, "Please Enter Student Father's Name", Toast.LENGTH_SHORT).show();
                } else if (university_id.isEmpty()) {
                    Toast.makeText(AddStudentActivity.this, "Please Enter Student University", Toast.LENGTH_SHORT).show();
                } else if (course_id.isEmpty()) {
                    Toast.makeText(AddStudentActivity.this, "Please Enter Student Course", Toast.LENGTH_SHORT).show();
                } else if (semester_id.isEmpty()) {
                    Toast.makeText(AddStudentActivity.this, "Please Enter Student Semester", Toast.LENGTH_SHORT).show();
                } else if (stud_mob_number.isEmpty()) {
                    Toast.makeText(AddStudentActivity.this, "Please Enter Student Mobile Number", Toast.LENGTH_SHORT).show();
                } else if (stud_address.isEmpty()) {
                    Toast.makeText(AddStudentActivity.this, "Please Enter Student Address", Toast.LENGTH_SHORT).show();
                } else {
                    RequestQueue mRequestQueue = null;

                    // Progress Dialog Box
                    ProgressDialog pDialog = new ProgressDialog(AddStudentActivity.this);
                    pDialog.setMessage("Loading... Please Wait");
                    pDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URLS.ADD_STUDENT_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    pDialog.dismiss();
//                                    showSuccessMessage(); // Implement this method to show a success message
                                    Toast.makeText(AddStudentActivity.this, "Response : " + response, Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pDialog.dismiss();
                            Toast.makeText(AddStudentActivity.this, "Error : " + error, Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map params = new HashMap();
                            params.put("stud_id", stud_id);
                            params.put("stud_name", stud_name);
                            params.put("university_id", university_id);
                            params.put("course_id", course_id);
                            params.put("semester_id", semester_id);
                            params.put("stud_father_name", stud_father_name);
                            params.put("stud_mob_number", stud_mob_number);
                            params.put("stud_address", stud_address);
                            return params;
                        }
                    };
                    mRequestQueue = Volley.newRequestQueue(AddStudentActivity.this);
                    mRequestQueue.add(stringRequest);
                }
            }
        });

        // spinner university
        spnr_university_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_id = view.findViewById(R.id.tv_id);
                TextView tv_name = view.findViewById(R.id.tv_name);

                universityId = tv_id.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // spinner course
        spnr_course_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_id = view.findViewById(R.id.tv_id);
                TextView tv_name = view.findViewById(R.id.tv_name);

                courseId = tv_id.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // spinner semester
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

        // functions calling
        MasterFunction.fetchCourseList(mContext, URLS.FETCH_COURSE_LIST_URL, spnr_course_name);
        MasterFunction.fetchUniversityList(mContext, URLS.FETCH_UNIVERSITY_LIST_URL, spnr_university_name);
        MasterFunction.fetchSemesterList(mContext, URLS.FETCH_SEMESTER_LIST_URL, spnr_semester_name);
    }

    // on back press while app drawer is open
//    @Override
//    public void onBackPressed() {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

}
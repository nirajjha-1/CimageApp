package com.stout.cimageapp.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowStudentsListActivity extends AppCompatActivity {
    private static final String TAG = ShowStudentsListActivity.class.getName();

    // tool bar
//    DrawerLayout drawerLayout;
//    NavigationView navigationView;
//    Toolbar toolbar;

    private RequestQueue mRequestQueue;
    StringRequest mStringRequest;

    // API URL
    private String url = Config.baseUrl + "cimage/fetch_student_list.php";

    private List<Student_List_Model> studentlistmodel;
    private ListView list_student;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_students_list);

        // tool bar
//        toolbar = findViewById(R.id.my_tool_bar);
//        setSupportActionBar(toolbar);
//
//        drawerLayout = findViewById(R.id.drawerlayout);
//
//        // navigation bar
//        navigationView = findViewById(R.id.navigationView);
//
//        // navigation bar item select handle
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int id = item.getItemId();
//                if (id == R.id.optHome) {
//                    startActivity(new Intent(ShowStudentsListActivity.this, HomeActivity.class));
//                    finish();
//                } else if (id == R.id.optNewsEvent) {
//                    startActivity(new Intent(ShowStudentsListActivity.this, EventActivity.class));
//                    finish();
//                } else if (id == R.id.optGallery) {
//                    startActivity(new Intent(ShowStudentsListActivity.this, GalleryActivity.class));
//                    finish();
//                } else if (id == R.id.optLogin) {
//                    startActivity(new Intent(ShowStudentsListActivity.this, AdminLoginActivity.class));
//                    finish();
//                }
//                return true;
//            }
//        });
//
//        // tool bar toggle
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nevigation_open, R.string.nevigation_close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();

        // list view of show student list
        list_student = findViewById(R.id.show_student_list);

        //RequestQueue Initialized
        mRequestQueue = Volley.newRequestQueue(this);

        // Request Initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
//                Toast.makeText(ShowStudentsListActivity.this, "" + response.length(), Toast.LENGTH_SHORT).show();
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.has("success")) {
                        JSONArray array = object.getJSONArray("success");
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

                    if (list_student != null) {
                        Student_List_Adapter student_list_adapter = new Student_List_Adapter(ShowStudentsListActivity.this, studentlistmodel);
                        list_student.setAdapter(student_list_adapter);
                    }
                } catch (JSONException ex) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error : " + error.toString());
            }
        });
        mRequestQueue.add(mStringRequest);
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
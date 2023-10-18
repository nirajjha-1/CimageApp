package com.stout.cimageapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.stout.cimageapp.screens.ShowStudentsListActivity;
import com.stout.cimageapp.utils.MasterFunction;

public class FacultyDashboardActivity extends AppCompatActivity {

    // tool bar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Context mContext;


    // dashboard items
    CardView cv_show_student_list, cv_add_attendance, cv_show_attendance, cv_show_schedule, cv_clubs, cv_fees;

    Button admin_btn_logout;

    TextView tv_dashboard_username;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_dashboard);

        // tool bar
        setSupportActionBar(toolbar);
        mContext = this;

        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.my_tool_bar);

        // navigation bar
        navigationView = findViewById(R.id.navigationView);

        // navigation bar item select handle
        MasterFunction.setNavigationView(navigationView, mContext);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nevigation_open, R.string.nevigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // dashboard items find id's
        cv_show_student_list = findViewById(R.id.cv_show_student_list);
        cv_add_attendance = findViewById(R.id.cv_add_attendance);
        cv_show_attendance = findViewById(R.id.cv_show_attendance);
        cv_show_schedule = findViewById(R.id.cv_show_schedule);
        cv_clubs = findViewById(R.id.cv_clubs);
        cv_fees = findViewById(R.id.cv_fees);
        tv_dashboard_username = findViewById(R.id.tv_dashboard_username);

        // Get SharedPreferences instance
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);

        // Retrieve data from SharedPreferences
        String username = sharedPreferences.getString("username", "Username");

        // Set data to TextView
        tv_dashboard_username.setText("Hello! "+username);

        // Logout Button
        admin_btn_logout = findViewById(R.id.admin_btn_logout);

        // Logout Button
        admin_btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle logout logic here
                logoutUser();
            }
        });

        // Show Student
        cv_show_student_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FacultyDashboardActivity.this, ShowStudentsListActivity.class));
            }
        });


        // Add Attendance
        cv_add_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FacultyDashboardActivity.this, AddAttendanceActivity.class));
            }
        });

        // Show Attendance
        cv_show_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FacultyDashboardActivity.this, AttendanceActivity.class));
            }
        });

        cv_show_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FacultyDashboardActivity.this, ScheduleActivity.class));
            }
        });

        cv_fees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FacultyDashboardActivity.this, FeePaymentActivity.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            SharedPreferences sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
            SharedPreferences.Editor spe = sp.edit();
            spe.putBoolean("islogin", false);
            spe.apply();
            Intent intent = new Intent(FacultyDashboardActivity.this, AdminLoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    // Implement the logout logic in a separate method
    private void logoutUser() {
        SharedPreferences sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.putBoolean("islogin", false);
        spe.apply();

        Intent intent = new Intent(FacultyDashboardActivity.this, AdminLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // on back press while app drawer is open
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
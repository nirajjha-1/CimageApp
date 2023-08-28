package com.stout.cimageapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    // API URL
    private String url = "http://192.168.1.3/cimage/insert_enquery.php";

    // tool bar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    // enquiry form
    EditText edt_fname, edt_lname, edt_email_id, edt_mobile_number;
    Spinner spnr_course_name;
    Button btn_send_enquiry;

    // Apply Button
    Button applyBtn;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setSupportActionBar(toolbar);

        // tool bar
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.my_tool_bar);




        // navigation bar
        navigationView = findViewById(R.id.navigationView);

        // navigation bar item select handle
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.optHome) {
                    startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                } else if (id == R.id.optEvent) {
                    startActivity(new Intent(HomeActivity.this, EventActivity.class));
                }else if (id == R.id.optGallery) {
                    startActivity(new Intent(HomeActivity.this, RegistrationForm.class));
                } else {
                    startActivity(new Intent(HomeActivity.this, RegistrationForm.class));
                }
                return true;
            }
        });

        // apply button
        applyBtn = findViewById(R.id.applyBtn);


        // tool bar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nevigation_open, R.string.nevigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // enquiry form
        edt_fname = findViewById(R.id.edt_fname);
        edt_lname = findViewById(R.id.edt_lname);
        edt_email_id = findViewById(R.id.edt_email_id);
        edt_mobile_number = findViewById(R.id.edt_mobile_number);
        spnr_course_name = findViewById(R.id.spnr_course_name);
        btn_send_enquiry = findViewById(R.id.btn_send_enquiry);

        if (btn_send_enquiry != null) {
            btn_send_enquiry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String fname = edt_fname.getText().toString();
                    String lname = edt_lname.getText().toString();
                    String email_id = edt_email_id.getText().toString();
                    String mobile_number = edt_mobile_number.getText().toString();

                    if (fname.isEmpty()) {
                        Toast.makeText(HomeActivity.this, "Please Enter Your First Name.", Toast.LENGTH_SHORT).show();
                    } else if (lname.isEmpty()) {
                        Toast.makeText(HomeActivity.this, "Please Enter Your Last Name.", Toast.LENGTH_SHORT).show();
                    } else if (email_id.isEmpty()) {
                        Toast.makeText(HomeActivity.this, "Please Enter Your Email Id.", Toast.LENGTH_SHORT).show();
                    } else if (mobile_number.isEmpty()) {
                        Toast.makeText(HomeActivity.this, "Please Enter Your Mobile Number.", Toast.LENGTH_SHORT).show();
                    } else {

                        RequestQueue mRequestQueue = null;

                        // Progress Dialog Box
                        ProgressDialog pDialog = new ProgressDialog(HomeActivity.this);
                        pDialog.setMessage("Loading... Please Wait");
                        pDialog.show();

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        pDialog.dismiss();
//                                        showSuccessMessage(); // Implement this method to show a success message
//                                        Toast.makeText(HomeActivity.this, "Response : " + response, Toast.LENGTH_SHORT).show();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pDialog.dismiss();
                                Toast.makeText(HomeActivity.this, "Error : " + error, Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map params = new HashMap();
                                params.put("fname", fname);
                                params.put("lname", lname);
                                params.put("mobile_number", mobile_number);
                                params.put("email_id", email_id);
                                params.put("course_name", spnr_course_name.getSelectedItem().toString());
                                return params;
                            }
                        };
                        mRequestQueue = Volley.newRequestQueue(HomeActivity.this);
                        mRequestQueue.add(stringRequest);
                    }

                }
            });
        }

        // apply btn
        if (applyBtn != null) {
            applyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(HomeActivity.this, RegistrationForm.class));
                }
            });
        }
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
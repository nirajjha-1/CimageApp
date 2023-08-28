package com.stout.cimageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Map;

public class RegistrationForm extends AppCompatActivity {

    // API URL
    private String url = "http://192.168.1.3/cimage/insert_registration_info.php";

    // tool bar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    // Registration Form
    EditText edt_email_id, edt_name, edt_mobile_number, edt_dob;
    Spinner spnr_counselling, spnr_course_name;
    Button btn_submit_registration;
    RadioGroup radioGroupGender, radioGroupCategory;
    RadioButton radioButtonCategory, radioButtonGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);

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
                    startActivity(new Intent(RegistrationForm.this, HomeActivity.class));
                } else if (id == R.id.optEvent) {
                    startActivity(new Intent(RegistrationForm.this, EventActivity.class));
                } else if (id == R.id.optGallery) {
                    startActivity(new Intent(RegistrationForm.this, RegistrationForm.class));
                } else {
                    startActivity(new Intent(RegistrationForm.this, RegistrationForm.class));
                }
                return true;
            }
        });

        // tool bar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nevigation_open, R.string.nevigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Registration Form
        edt_email_id = findViewById(R.id.edt_email_id);
        edt_name = findViewById(R.id.edt_name);
        edt_mobile_number = findViewById(R.id.edt_mobile_number);
        edt_dob = findViewById(R.id.edt_dob);

        spnr_counselling = findViewById(R.id.spnr_counselling);
        spnr_course_name = findViewById(R.id.spnr_course_name);

        radioGroupCategory = findViewById(R.id.radioGroupCategory);
        radioGroupGender = findViewById(R.id.radioGroupGender);

        btn_submit_registration = findViewById(R.id.btn_submit_registration);
        if (btn_submit_registration != null) {
            btn_submit_registration.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email_id = edt_email_id.getText().toString();
                    String name = edt_name.getText().toString();
                    String mobile_number = edt_mobile_number.getText().toString();
                    String dob = edt_dob.getText().toString();
                    String course_name = spnr_course_name.getSelectedItem().toString();
                    String counsellor_name = spnr_counselling.getSelectedItem().toString();

                    if (email_id.isEmpty()) {
                        Toast.makeText(RegistrationForm.this, "Please Enter Email Id.", Toast.LENGTH_SHORT).show();
                    } else if (name.isEmpty()) {
                        Toast.makeText(RegistrationForm.this, "Please Enter Your Name.", Toast.LENGTH_SHORT).show();
                    } else if (mobile_number.isEmpty()) {
                        Toast.makeText(RegistrationForm.this, "Please Enter Your Mobile Number.", Toast.LENGTH_SHORT).show();
                    } else if (dob.isEmpty()) {
                        Toast.makeText(RegistrationForm.this, "Please Enter Date Of Birth.", Toast.LENGTH_SHORT).show();
                    } else if (course_name.isEmpty()) {
                        Toast.makeText(RegistrationForm.this, "Please Select Course Name.", Toast.LENGTH_SHORT).show();
                    } else if (counsellor_name.isEmpty()) {
                        Toast.makeText(RegistrationForm.this, "Please Select Counsellor Name.", Toast.LENGTH_SHORT).show();
                    }else {

                        RequestQueue mRequestQueue = null;

                        // Progress Dialog Box
                        ProgressDialog pDialog = new ProgressDialog(RegistrationForm.this);
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
                                Toast.makeText(RegistrationForm.this, "Error : " + error, Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map params = new HashMap();
                                params.put("email_id", email_id);
                                params.put("name", name);
                                params.put("mobile_number", mobile_number);
                                params.put("dob", dob);
                                params.put("course",course_name);
                                params.put("counsellor_name",counsellor_name);
                                params.put("gender",radioButtonGender.getText());
                                params.put("category",radioButtonCategory.getText());
                                return params;
                            }
                        };
                        mRequestQueue = Volley.newRequestQueue(RegistrationForm.this);
                        mRequestQueue.add(stringRequest);
                    }

                }
            });
        }

    }

    // Check Category Radio button
    public void checkButtonCategory(View v){
        int radioBtnId = radioGroupCategory.getCheckedRadioButtonId();
        radioButtonCategory = findViewById(radioBtnId);
        Toast.makeText(this, "Selected Radio Btn : "+radioButtonCategory.getText(), Toast.LENGTH_SHORT).show();
    }

    // Check Gender Radio button
    public void checkButtonGender(View v){
        int radioBtnId = radioGroupGender.getCheckedRadioButtonId();
        radioButtonGender = findViewById(radioBtnId);
        Toast.makeText(this, "Selected Radio Btn : "+radioButtonGender.getText(), Toast.LENGTH_SHORT).show();
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
package com.stout.cimageapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.google.android.material.navigation.NavigationView;
import com.stout.cimageapp.adapter.AmenitiesListAdapter;
import com.stout.cimageapp.utils.Config;
import com.stout.cimageapp.utils.MasterFunction;
import com.stout.cimageapp.utils.URLS;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    // tool bar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Context mContext;

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
        mContext = this;

        // tool bar
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.my_tool_bar);

        // navigation bar
        navigationView = findViewById(R.id.navigationView);

        // navigation bar item select handle
        MasterFunction.setNavigationView(navigationView,mContext);

        // apply button
        applyBtn = findViewById(R.id.applyBtn);


        // tool bar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nevigation_open, R.string.nevigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        ListView lst_amenities = findViewById(R.id.lst_amenities);
        AmenitiesListAdapter amenitiesListAdapter = new AmenitiesListAdapter(HomeActivity.this, MasterFunction.getAmenities());
        lst_amenities.setAdapter(amenitiesListAdapter);

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

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLS.SEND_ENQUERY_URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        pDialog.dismiss();
                                        edt_fname.setText("");
                                        edt_lname.setText("");
                                        edt_email_id.setText("");
                                        edt_mobile_number.setText("");

                                        MasterFunction.showDialog(mContext,"Success","Enquiry submitted successfully!",0);
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
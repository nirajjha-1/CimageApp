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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.stout.cimageapp.utils.Config;
import com.stout.cimageapp.utils.MasterFunction;
import com.stout.cimageapp.utils.URLS;

import java.util.HashMap;
import java.util.Map;

public class AdminLoginActivity extends AppCompatActivity {
    // tool bar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    Context mContext;



    private String ADMIN_LOGIN_URL = Config.baseUrl+"cimage/admin_login.php";
    EditText edt_username,edt_password;
    Button btn_admin_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        // tool bar
        setSupportActionBar(toolbar);
        mContext = this;

        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.my_tool_bar);

        // navigation bar
        navigationView = findViewById(R.id.navigationView);

        // navigation bar item select handle
        MasterFunction.setNavigationView(navigationView,mContext);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nevigation_open, R.string.nevigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        btn_admin_login = findViewById(R.id.btn_admin_login);

        btn_admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edt_username.getText().toString();
                String password = edt_password.getText().toString();

                if(username.isEmpty()){
                    edt_username.setError("Write login user name");
                } else if (password.isEmpty()) {
                    edt_password.setError("Write login password");
                }else{
                    ProgressDialog progressDialog = new ProgressDialog(AdminLoginActivity.this);
                    progressDialog.setMessage("Login, Authenticate please wait...");
                    progressDialog.show();


                    RequestQueue mRequesetQueue = Volley.newRequestQueue(AdminLoginActivity.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URLS.ADMIN_LOGIN_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressDialog.dismiss();
//                                    Toast.makeText(AdminLoginActivity.this, "" + response, Toast.LENGTH_SHORT).show();

                                    if (response.trim().toString().equals("success")) {
                                        SharedPreferences sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor spe = sp.edit();
                                        spe.putBoolean("islogin", true);
                                        spe.apply();
                                        Intent intent = new Intent(AdminLoginActivity.this, AdminDashboardActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    } else if (response.equals("failed")) {
                                        Toast.makeText(AdminLoginActivity.this, "Invalid User ID/Password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(AdminLoginActivity.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String,String> getParams() throws AuthFailureError {
                            HashMap hashMap = new HashMap();
                            hashMap.put("user_name",username);
                            hashMap.put("password",password);
                            return hashMap;
                        }
                    };
                    mRequesetQueue.add(stringRequest);
                }
            }
        });
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
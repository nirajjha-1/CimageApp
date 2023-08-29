package com.stout.cimageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.stout.cimageapp.adapter.Event_Adapter;
import com.stout.cimageapp.models.Event_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {

    private static final String TAG = EventActivity.class.getName();

    private RequestQueue mRequestQueue;
    StringRequest mStringQueue;

    // API URL
    private String url = "http://192.168.188.120/cimage/fetch_event.php";

    private List<Event_Model> eventList;

    private ListView list_event;

    // tool bar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        // set custom toolbar/ action bar / app bar
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
                    startActivity(new Intent(EventActivity.this, HomeActivity.class));
                } else if (id == R.id.optEvent) {
                    startActivity(new Intent(EventActivity.this, EventActivity.class));
                } else if (id == R.id.optGallery) {
                    startActivity(new Intent(EventActivity.this, RegistrationForm.class));
                } else {
                    startActivity(new Intent(EventActivity.this, RegistrationForm.class));
                }
                return true;
            }
        });

        // tool bar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nevigation_open, R.string.nevigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        list_event = findViewById(R.id.list_event);

        //RequestQueue Initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //RequestQueue Initialized
        mStringQueue = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    eventList = new ArrayList<>();

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject jsonObject = array.getJSONObject(i);
                        String event_title = jsonObject.getString("event_title");
                        String event_disc = jsonObject.getString("event_disc");
                        String event_img_url = jsonObject.getString("event_img");
                        eventList.add(new Event_Model(event_title, event_disc, event_img_url));
                    }

                    Event_Adapter event_adapter = new Event_Adapter(EventActivity.this, eventList);
                    list_event.setAdapter(event_adapter);
                } catch (JSONException ex) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Erro : " + error.toString());
            }
        });
        mRequestQueue.add(mStringQueue);

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
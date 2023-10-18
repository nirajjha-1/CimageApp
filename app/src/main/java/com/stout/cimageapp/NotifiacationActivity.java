package com.stout.cimageapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.stout.cimageapp.adapter.Event_Adapter;
import com.stout.cimageapp.adapter.Notification_List_Adapter;
import com.stout.cimageapp.adapter.Student_List_Adapter;
import com.stout.cimageapp.models.Event_Model;
import com.stout.cimageapp.models.Notification_List_Model;
import com.stout.cimageapp.models.Student_List_Model;
import com.stout.cimageapp.screens.ShowStudentsListActivity;
import com.stout.cimageapp.utils.MasterFunction;
import com.stout.cimageapp.utils.URLS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotifiacationActivity extends AppCompatActivity {
    // tool bar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    private static final String TAG = NotifiacationActivity.class.getName();
    private List<Notification_List_Model> notification_list_models;
    private RecyclerView recyclerView;
    private RequestQueue mRequestQueue;
    StringRequest mStringQueue;
    private Context mContext;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifiacation);

        // tool bar
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.my_tool_bar);

        // navigation bar
        navigationView = findViewById(R.id.navigationView);

        // navigation bar item select handle
        MasterFunction.setNavigationView(navigationView,mContext);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nevigation_open, R.string.nevigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        mRequestQueue = Volley.newRequestQueue(this);

        mContext = this;

        recyclerView = findViewById(R.id.recyclerView_notif);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);

        mStringQueue = new StringRequest(Request.Method.GET, URLS.FETCH_NOTIFICATION_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("success");
                    if(jsonArray.length()==0){
                        MasterFunction.showDialog(mContext,"Notification","Notification Not found",0);
                    }

                    notification_list_models = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String notif_type = jsonObject.getString("notif_type");
                        String notif_date = jsonObject.getString("timestamp");
                        String notif_title = jsonObject.getString("notif_title");
                        String notif_message = jsonObject.getString("notif_message");
                        notification_list_models.add(new Notification_List_Model(notif_type, notif_date, notif_title,notif_message));
                    }

                    Notification_List_Adapter notification_list_adapter = new Notification_List_Adapter(mContext,notification_list_models);
                    recyclerView.setAdapter(notification_list_adapter);

                } catch (JSONException ex) {
                    Log.d("JSONException", "onResponse: "+ex.getMessage().toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error : " + error.toString());
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
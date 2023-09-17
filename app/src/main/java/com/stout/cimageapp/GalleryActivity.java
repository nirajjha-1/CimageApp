package com.stout.cimageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.stout.cimageapp.adapter.Event_Adapter;
import com.stout.cimageapp.adapter.Gallery_Adapter;
import com.stout.cimageapp.models.Event_Model;
import com.stout.cimageapp.models.Gallery_Model;
import com.stout.cimageapp.utils.Config;
import com.stout.cimageapp.utils.MasterFunction;
import com.stout.cimageapp.utils.URLS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    private static final String TAG = GalleryActivity.class.getName();

    private RequestQueue mRequestQueue;
    StringRequest mStringQueue;
    private List<Gallery_Model> galleryList;

    private ListView list_gallery;

    // tool bar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        // set custom toolbar/ action bar / app bar
        setSupportActionBar(toolbar);
        mContext = this;

        // tool bar
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.my_tool_bar);

        // navigation bar
        navigationView = findViewById(R.id.navigationView);

        // navigation bar item select handle
        MasterFunction.setNavigationView(navigationView,mContext);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nevigation_open, R.string.nevigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        list_gallery = findViewById(R.id.list_gallery);

        //RequestQueue Initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //RequestQueue Initialized
        mStringQueue = new StringRequest(Request.Method.GET, URLS.FETCH_GALLERY_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    galleryList = new ArrayList<>();

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject jsonObject = array.getJSONObject(i);
                        String gallery_title = jsonObject.getString("title");
                        String gallery_disc = jsonObject.getString("discription");
                        String gallery_img_url = jsonObject.getString("image_url");
                        galleryList.add(new Gallery_Model(gallery_title, gallery_disc, gallery_img_url));
                    }

                    if (list_gallery != null) {
                        Gallery_Adapter gallery_adapter = new Gallery_Adapter(GalleryActivity.this, galleryList);
                        list_gallery.setAdapter(gallery_adapter);
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
package com.stout.cimageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.stout.cimageapp.utils.MasterFunction;
import com.stout.cimageapp.utils.URLS;

import java.util.HashMap;

public class AddNewsAndEventActivity extends AppCompatActivity {
    EditText edt_news_event_name, edt_news_event_dscr, edt_news_event_image_url;
    Button btn_add_news_event;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news_and_event);
        mContext = this;

        edt_news_event_name = findViewById(R.id.edt_news_event_name);
        edt_news_event_dscr = findViewById(R.id.edt_news_event_dscr);
        edt_news_event_image_url = findViewById(R.id.edt_news_event_image_url);
        btn_add_news_event = findViewById(R.id.btn_add_news_event);

        btn_add_news_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String event_name = edt_news_event_name.getText().toString();
                String event_dscr = edt_news_event_dscr.getText().toString();
                String event_image_url = edt_news_event_image_url.getText().toString();

                if(event_name.isEmpty()){
                    edt_news_event_name.setError("Enter Title");
                }else if(event_dscr.isEmpty()){
                    edt_news_event_name.setError("Enter Title");
                }else if(event_image_url.isEmpty()){
                    edt_news_event_name.setError("Enter Title");
                }else{
                    HashMap hashMap = new HashMap();
                    hashMap.put("event_img",event_image_url);
                    hashMap.put("event_title",event_name);
                    hashMap.put("event_dscr",event_dscr);

                    MasterFunction.request(mContext, URLS.ADD_NEWS_EVENT_URL, hashMap, true, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(mContext, ""+response, Toast.LENGTH_SHORT).show();
                        }
                    },false);
                }
            }
        });
    }
}
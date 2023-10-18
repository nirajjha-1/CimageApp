package com.stout.cimageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.stout.cimageapp.utils.MasterFunction;
import com.stout.cimageapp.utils.URLS;

import java.util.HashMap;

public class AddAnnouncementActivity extends AppCompatActivity {
    Spinner spnr_notification_type;
    EditText edt_user_id,edt_notification_title, edt_notification;
    Button btn_add_announcement;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_announcement);
        context = this;

        edt_user_id = findViewById(R.id.user_id);
        edt_notification_title = findViewById(R.id.notification_title);
        edt_notification = findViewById(R.id.notification);
        spnr_notification_type = findViewById(R.id.spnr_notification_type);
        btn_add_announcement = findViewById(R.id.btn_add_announcement);

        btn_add_announcement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = edt_user_id.getText().toString();
                String notification_title = edt_notification_title.getText().toString();
                String notification_type = spnr_notification_type.getSelectedItem().toString();
                String notification = edt_notification.getText().toString();

                if(user_id.isEmpty()){
                    edt_user_id.setError("Enter User Id");
                }else if(notification_title.isEmpty()){
                    edt_notification_title.setError("Enter Notification Title");
                }else if(notification.isEmpty()){
                    edt_notification.setError("Enter Notification");
                }else{
                    HashMap hashMap = new HashMap();
                    hashMap.put("user_id",user_id);
                    hashMap.put("notification_title",notification_title);
                    hashMap.put("notification",notification);
                    hashMap.put("notification_type",notification_type);
                    MasterFunction.request(context, URLS.ADD_ANNOUNCEMENT_URL, hashMap, true, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();
                        }
                    },false);
                }
            }
        });

    }
}
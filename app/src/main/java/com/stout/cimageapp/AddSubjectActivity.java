package com.stout.cimageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.stout.cimageapp.utils.MasterFunction;
import com.stout.cimageapp.utils.URLS;

import java.util.HashMap;

public class AddSubjectActivity extends AppCompatActivity {

    private Spinner spnr_course_name, spnr_semester_name;
    private EditText edt_subject_name;
    private Button btn_add_subject;
    private Context mContext;
    private String courseId="", semesterId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        mContext = this;

        spnr_course_name = findViewById(R.id.spnr_course_name);
        spnr_semester_name = findViewById(R.id.spnr_semester_name);
        edt_subject_name = findViewById(R.id.edt_subject_name);
        btn_add_subject = findViewById(R.id.btn_add_subject);

        MasterFunction.fetchCourseList(mContext, URLS.FETCH_COURSE_LIST_URL,spnr_course_name);
        MasterFunction.fetchSemesterList(mContext,URLS.FETCH_SEMESTER_LIST_URL,spnr_semester_name);

        spnr_course_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_id =view.findViewById(R.id.tv_id);
                TextView tv_name =view.findViewById(R.id.tv_name);
                courseId = tv_id.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnr_semester_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_id = view.findViewById(R.id.tv_id);
                TextView tv_name = view.findViewById(R.id.tv_name);
                semesterId = tv_id.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_add_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subjectName = edt_subject_name.getText().toString();
                if(subjectName.isEmpty()){
                    edt_subject_name.setError("Enter Subject Name");
                }else{
                    HashMap hashMap = new HashMap();
                    hashMap.put("courseId",courseId);
                    hashMap.put("semesterId",semesterId);
                    hashMap.put("subjectName",subjectName);

                    MasterFunction.request(mContext, URLS.ADD_SUBJECT_URL, hashMap, true, new Response.Listener<String>() {
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
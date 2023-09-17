package com.stout.cimageapp.utils;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.stout.cimageapp.AddStudentActivity;
import com.stout.cimageapp.AdminLoginActivity;
import com.stout.cimageapp.EventActivity;
import com.stout.cimageapp.GalleryActivity;
import com.stout.cimageapp.HomeActivity;
import com.stout.cimageapp.R;
import com.stout.cimageapp.RegistrationForm;
import com.stout.cimageapp.adapter.Course_List_Adapter;
import com.stout.cimageapp.adapter.Semester_List_Adapter;
import com.stout.cimageapp.adapter.SubjectListAdapter;
import com.stout.cimageapp.adapter.University_List_Adapter;
import com.stout.cimageapp.models.Course_Model;
import com.stout.cimageapp.models.Semester_Model;
import com.stout.cimageapp.models.SubjectModel;
import com.stout.cimageapp.models.University_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterFunction {

    // Set Navigation View
    public static void setNavigationView(NavigationView navigationView, Context mContext) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.optHome) {
                    Intent intent = new Intent(mContext, HomeActivity.class);
                    mContext.startActivity(intent);
                } else if (id == R.id.optNewsEvent) {
                    Intent intent = new Intent(mContext, EventActivity.class);
                    mContext.startActivity(intent);
                    ((Activity) mContext).finish();
                } else if (id == R.id.optGallery) {
                    Intent intent = new Intent(mContext, GalleryActivity.class);
                    mContext.startActivity(intent);
                    ((Activity) mContext).finish();
                } else if (id == R.id.optLogin) {
                    Intent intent = new Intent(mContext, AdminLoginActivity.class);
                    mContext.startActivity(intent);
                    ((Activity) mContext).finish();
                }
                return true;
            }
        });
    }

    //fetch University Data
    public static void fetchUniversityList(Context context, String URL, Spinner spnr_university_name) {
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(AddStudentActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                        try {
                            List<University_Model> universityModelList = new ArrayList<>();
                            JSONObject object = new JSONObject(response);
                            if (object.has("success")) {
                                JSONArray array = object.getJSONArray("success");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    String university_id = jsonObject.getString("university_id");
                                    String university_name = jsonObject.getString("university_name");
                                    universityModelList.add(new University_Model(university_id, university_name));
                                }
                            }
//                            Toast.makeText(AddStudentActivity.this, "size" + universityModelList.size(), Toast.LENGTH_SHORT).show();

                            University_List_Adapter universityListAdapter = new University_List_Adapter(context, universityModelList);

                            spnr_university_name.setAdapter(universityListAdapter);

                        } catch (JSONException ex) {
                            Toast.makeText(context, "" + ex.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(stringRequest);
    }

    //fetch Course Data
    public static void fetchCourseList(Context context, String URL, Spinner spnr_course_name) {
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            List<Course_Model> course_models = new ArrayList<>();
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("success");
                            if (object.has("success")) {
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    String course_id = jsonObject.getString("course_id");
                                    String course_name = jsonObject.getString("course_name");
                                    course_models.add(new Course_Model(course_id, course_name));
                                }
                            }
                            Course_List_Adapter courseListAdapter = new Course_List_Adapter(context, course_models);
                            spnr_course_name.setAdapter(courseListAdapter);
                        } catch (JSONException ex) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(stringRequest);
    }

    // fetch subject data
    public static void fetchSubjectList(Context mContext, HashMap hashMap, Spinner spnr_subject) {

        MasterFunction.request(mContext, URLS.FETCH_SUBJECT_LIST_URL, hashMap, false, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    List<SubjectModel> subjectModels = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("success");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String subject_id = object.getString("subject_id");
                        String subject_name = object.getString("subject_name");

                        subjectModels.add(new SubjectModel(subject_id, subject_name));
                    }
                    SubjectListAdapter subjectListAdapter = new SubjectListAdapter(mContext, subjectModels);
                    spnr_subject.setAdapter(subjectListAdapter);
                } catch (JSONException ex) {
                }
            }
        }, false);

    }

    //fetch Semester Data
    public static void fetchSemesterList(Context context, String URL, Spinner spnr_semester_name) {
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(AddStudentActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                        try {
                            List<Semester_Model> semesterModelList = new ArrayList<>();
                            JSONObject object = new JSONObject(response);
                            if (object.has("success")) {
                                JSONArray array = object.getJSONArray("success");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    String university_id = jsonObject.getString("semester_id");
                                    String university_name = jsonObject.getString("semester_name");
                                    semesterModelList.add(new Semester_Model(university_id, university_name));
                                }
                            }

                            Semester_List_Adapter semester_list_adapter = new Semester_List_Adapter(context, semesterModelList);

                            spnr_semester_name.setAdapter(semester_list_adapter);

                        } catch (JSONException ex) {
                            Toast.makeText(context, "" + ex.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(stringRequest);
    }

    // Request
    public static void request(Context context, String url, final Map<String, String> params, boolean isprogress, final Response.Listener<String> listener, boolean is_cache) {
        ProgressDialog prog = null;
        if (isprogress) {
            prog = ProgressDialog.show(context, "", "Please wait...", false, false);
        }
        final ProgressDialog finalProg = prog;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (finalProg != null) {
                    if (finalProg.isShowing()) {
                        finalProg.dismiss();
                    }
                }
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (finalProg != null) {
                    if (finalProg.isShowing()) {
                        finalProg.dismiss();
                    }
                }
                Log.e("VolleyError", error.toString());
                String err = error.toString();
                if (err.contains("Failed to connect")) {
                    listener.onResponse("No Internet Connection detected please check internet connection");
                } else {
                    listener.onResponse(error.toString().replace("com.android.volley.", ""));
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        stringRequest.setShouldCache(is_cache);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    // show dialog

    public static void showDialog(Context context,String title,String msg,int status){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(status==1){
                    ((Activity)context).finish();
                }
            }
        });

        AlertDialog dialog =builder.create();
        dialog.show();
    }

}

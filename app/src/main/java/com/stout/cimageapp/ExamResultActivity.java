package com.stout.cimageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.stout.cimageapp.utils.MasterFunction;
import com.stout.cimageapp.utils.URLS;

import java.util.ArrayList;
import java.util.HashMap;

public class ExamResultActivity extends AppCompatActivity {

    private Spinner spnr_course_name,spnr_semester_name;
    private Context mContext;
    private String courseId="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_result);

        mContext = this;

        spnr_course_name = findViewById(R.id.spnr_course_name);
        spnr_semester_name = findViewById(R.id.spnr_semester_name);

        ListView listView = findViewById(R.id.list_view_subjects);

        MasterFunction.fetchCourseList(mContext, URLS.FETCH_COURSE_LIST_URL,spnr_course_name);

        spnr_course_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_id = view.findViewById(R.id.tv_id);
                TextView tv_name = view.findViewById(R.id.tv_name);
                courseId = tv_id.getText().toString();

                HashMap hashMap = new HashMap();
                hashMap.put("course_id",courseId);

                MasterFunction.fetchSemesterList(mContext,URLS.FETCH_SEMESTER_LIST_URL,spnr_semester_name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<SubjectModel> subjects = new ArrayList<>();
        subjects.add(new SubjectModel("C.S", 70, 100,"A"));
        subjects.add(new SubjectModel("Mathematics", 80, 100,"A+"));
        subjects.add(new SubjectModel("Physics", 75, 100,"A+"));
        subjects.add(new SubjectModel("D.S", 85, 100,"A+"));
        subjects.add(new SubjectModel("Algorithms", 90, 100,"A++"));

        SubjectAdapter adapter = new SubjectAdapter(this, subjects);
        listView.setAdapter(adapter);
    }

    public class SubjectModel {
        private String subjectName;
        private int passMarks;
        private int totalMarks;
        private String grade;

        public String getGrade() {
            return grade;
        }

        public SubjectModel(String subjectName, int passMarks, int totalMarks,String grade) {
            this.subjectName = subjectName;
            this.passMarks = passMarks;
            this.totalMarks = totalMarks;
            this.grade=grade;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public int getPassMarks() {
            return passMarks;
        }

        public int getTotalMarks() {
            return totalMarks;
        }
    }

    public class SubjectAdapter extends ArrayAdapter<SubjectModel> {

        public SubjectAdapter(Context context, ArrayList<SubjectModel> subjects) {
            super(context, 0, subjects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SubjectModel subject = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_exam_result, parent, false);
            }

            TextView subjectNameTextView = convertView.findViewById(R.id.text_subject_name);
            TextView passMarksTextView = convertView.findViewById(R.id.text_pass_marks);
            TextView totalMarksTextView = convertView.findViewById(R.id.text_total_marks);
            TextView text_grade = convertView.findViewById(R.id.text_grade);

            if (subject != null) {
                subjectNameTextView.setText( subject.getSubjectName());
                passMarksTextView.setText("" + subject.getPassMarks());
                totalMarksTextView.setText("" + subject.getTotalMarks());
                text_grade.setText(subject.getGrade());
            }

            return convertView;
        }
    }


}
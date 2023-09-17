package com.stout.cimageapp.models;

public class StudentAttendanceListModel {
    private String courseId;
    private String subjectId;
    private String studentId;
    private String student_name;
    private String attedance_status;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getAttedance_status() {
        return attedance_status;
    }

    public void setAttedance_status(String attedance_status) {
        this.attedance_status = attedance_status;
    }
}

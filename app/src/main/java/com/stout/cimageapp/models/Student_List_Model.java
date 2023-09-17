package com.stout.cimageapp.models;

public class Student_List_Model {
    private String stud_name, stud_id, stud_course, stud_university, stud_semester, stud_father_name, stud_mob_number, stud_address;

    public Student_List_Model(String stud_name, String stud_id, String stud_course, String stud_university, String stud_semester, String stud_father_name, String stud_mob_number, String stud_address) {
        this.stud_name = stud_name;
        this.stud_id = stud_id;
        this.stud_course = stud_course;
        this.stud_university = stud_university;
        this.stud_semester = stud_semester;
        this.stud_father_name = stud_father_name;
        this.stud_mob_number = stud_mob_number;
        this.stud_address = stud_address;
    }
    public String getStud_name() {
        return stud_name;
    }

    public void setStud_name(String stud_name) {
        this.stud_name = stud_name;
    }

    public String getStud_id() {
        return stud_id;
    }

    public void setStud_id(String stud_id) {
        this.stud_id = stud_id;
    }

    public String getStud_course() {
        return stud_course;
    }

    public void setStud_course(String stud_course) {
        this.stud_course = stud_course;
    }

    public String getStud_university() {
        return stud_university;
    }

    public void setStud_university(String stud_university) {
        this.stud_university = stud_university;
    }

    public String getStud_semester() {
        return stud_semester;
    }

    public void setStud_semester(String stud_semester) {
        this.stud_semester = stud_semester;
    }

    public String getStud_father_name() {
        return stud_father_name;
    }

    public void setStud_father_name(String stud_father_name) {
        this.stud_father_name = stud_father_name;
    }

    public String getStud_mob_number() {
        return stud_mob_number;
    }

    public void setStud_mob_number(String stud_mob_number) {
        this.stud_mob_number = stud_mob_number;
    }

    public String getStud_address() {
        return stud_address;
    }

    public void setStud_address(String stud_address) {
        this.stud_address = stud_address;
    }
}

package com.stout.cimageapp.models;

public class University_Model {
    private String universityId, universityName;

    public University_Model(String universityId, String universityName) {
        this.universityId = universityId;
        this.universityName = universityName;
    }

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }
}

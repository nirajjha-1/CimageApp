package com.stout.cimageapp.models;

public class Gallery_Model {

    private String gallery_title, gallery_dscr, gallery_img_url;

    public Gallery_Model(String gallery_title, String gallery_dscr, String gallery_img_url) {
        this.gallery_title = gallery_title;
        this.gallery_dscr = gallery_dscr;
        this.gallery_img_url = gallery_img_url;
    }

    public String getGallery_title() {
        return gallery_title;
    }

    public void setGallery_title(String gallery_title) {
        this.gallery_title = gallery_title;
    }

    public String getGallery_dscr() {
        return gallery_dscr;
    }

    public void setGallery_dscr(String gallery_dscr) {
        this.gallery_dscr = gallery_dscr;
    }

    public String getGallery_img_url() {
        return gallery_img_url;
    }

    public void setGallery_img_url(String gallery_img_url) {
        this.gallery_img_url = gallery_img_url;
    }
}

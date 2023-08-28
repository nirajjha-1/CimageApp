package com.stout.cimageapp.models;

public class Event_Model {
    private String event_title, event_dscr, event_img_url;

    public Event_Model(String event_title, String event_dscr, String event_img_url) {
        this.event_title = event_title;
        this.event_dscr = event_dscr;
        this.event_img_url = event_img_url;
    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getEvent_dscr() {
        return event_dscr;
    }

    public void setEvent_dscr(String event_dscr) {
        this.event_dscr = event_dscr;
    }

    public String getEvent_img() {
        return event_img_url;
    }

    public void setEvent_img(String event_img) {
        this.event_img_url = event_img;
    }
}

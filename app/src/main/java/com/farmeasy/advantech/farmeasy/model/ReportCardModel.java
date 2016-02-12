package com.farmeasy.advantech.farmeasy.model;

/**
 * Created by Advantech Ltd on 2/10/2016.
 */
public class ReportCardModel {
    private String title;
    private int photo;

    public ReportCardModel() {
    }

    public ReportCardModel(int photo, String title) {
        this.photo = photo;
        this.title = title;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

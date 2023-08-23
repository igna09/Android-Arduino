package com.example.carduino.homepage.models;

public class HomepageGridButtonModel {
    // string course_name for storing course_name
    // and imgid for storing image id.
    private String label;
    private int imgid;
    private Class clazz;

    public HomepageGridButtonModel(String label, int imgid) {
        this.label = label;
        this.imgid = imgid;
    }

    public HomepageGridButtonModel(String label, int imgid, Class clazz) {
        this.label = label;
        this.imgid = imgid;
        this.clazz = clazz;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}

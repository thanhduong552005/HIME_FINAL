package com.example.hime_droid.models;

public class ItemModel {
    public ItemModel(Integer id, String title, String shorttext, String html) {
        this.id = id;
        this.title = title;
        this.shorttext = shorttext;
        this.html = html;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShorttext() {
        return shorttext;
    }

    public void setShorttext(String shorttext) {
        this.shorttext = shorttext;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Integer id;
    public String title;
    public String shorttext;
    public String html;
}

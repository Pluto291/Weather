package com.pluto.weather.model;

public class Detail {
    private String item;
    private String info;

    public Detail(String item, String info) {
        this.item = item;
        this.info = info;
    }

    public String getItem() {
        return item;
    }

    public String getInfo() {
        return info;
    }
}

package com.akadatsky.musiccharts.model;

import java.util.List;

public class Artist {

    private String name;
    private int listeners;
    private List<Image> image;

    public String getName() {
        return name;
    }

    public int getListeners() {
        return listeners;
    }

    public List<Image> getImage() {
        return image;
    }
}

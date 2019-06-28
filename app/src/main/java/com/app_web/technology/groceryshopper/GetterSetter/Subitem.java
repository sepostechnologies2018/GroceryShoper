package com.app_web.technology.groceryshopper.GetterSetter;

/**
 * Created by Tejveer on 4/5/2017.
 */
public class Subitem {
    private String name;
    private String numOfSongs;
    private String thumbnail;
    private String id;


    public Subitem() {
    }

    public Subitem(String name, String numOfSongs, String thumbnail,String id) {
        this.name = name;
        this.numOfSongs = numOfSongs;
        this.thumbnail = thumbnail;
        this.id = id;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumOfSongs() {
        return numOfSongs;
    }

    public void setNumOfSongs(String numOfSongs) {
        this.numOfSongs = numOfSongs;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
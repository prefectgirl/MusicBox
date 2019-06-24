package com.example.a8_9baidumusicbox.Utils;

public class Song {
    public String name;
    public String singer;
    public String path;
    public int duration;
    public Long size;
    public int index = 0;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Song{" +
                "name='" + name + '\'' +
                ", singer='" + singer + '\'' +
                ", path='" + path + '\'' +
                ", duration=" + duration +
                '}';
    }
}

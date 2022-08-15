package com.flatcode.simplemultiapps.VideoPlayer;

public class VideoFiles {
    private String id, path, title, fileName, size, dateAdded, duration;

    public VideoFiles(String id, String path, String title, String fileName, String size, String dateAdded, String duration) {
        this.id = id;
        this.path = path;
        this.title = title;
        this.fileName = fileName;
        this.size = size;
        this.dateAdded = dateAdded;
        this.duration = duration;
    }

    public VideoFiles() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
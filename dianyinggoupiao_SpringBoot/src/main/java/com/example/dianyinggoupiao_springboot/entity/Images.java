package com.example.dianyinggoupiao_springboot.entity;

public class Images {
    private String id;
    private String filename;
    private String extension;
    private String path;

    public Images() {

    }

    public Images(String filename, String extension, String path) {
        this.filename = filename;
        this.extension = extension;
        this.path = path;
    }

    public Images(String id, String filename, String extension, String path) {
        this.id = id;
        this.filename = filename;
        this.extension = extension;
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Images{" +
                "id='" + id + '\'' +
                ", filename='" + filename + '\'' +
                ", extension='" + extension + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}

package com.dada.videstation.model;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by dada on 26/10/2015.
 */
public class Movie extends VideoFile implements Serializable {

    private int id;
    private Mapper mapper;
    private String title;
    private String tagLine;
    private int year;
    private Date originallyAvailable;
    private Date createDate;
    private Date modifyDate;
    private String rating;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Mapper getMapper() {
        return mapper;
    }

    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getOriginallyAvailable() {
        return originallyAvailable;
    }

    public void setOriginallyAvailable(Date originallyAvailable) {
        this.originallyAvailable = originallyAvailable;
    }

    @Override
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public Date getModifyDate() {
        return modifyDate;
    }

    @Override
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}

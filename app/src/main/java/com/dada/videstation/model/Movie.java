package com.dada.videstation.model;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by dada on 26/10/2015.
 */
public class Movie implements Serializable, Item
{

    private int id;
    private Mapper mapper;
    private String title;
    private String tagLine;
    private int year;
    private Date originallyAvailable;
    private Date createDate;
    private Date modifyDate;
    private String rating;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Mapper getMapper() {
        return mapper;
    }


    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
        this.setType(mapper.getType());
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getType() {
        return type;
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

    public Date getReleaseDate() {
        return originallyAvailable;
    }

    public void setReleaseDate(Date originallyAvailable) {
        this.originallyAvailable = originallyAvailable;
    }


    public Date getCreateDate() {
        return createDate;
    }


    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    public Date getModifyDate() {
        return modifyDate;
    }


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

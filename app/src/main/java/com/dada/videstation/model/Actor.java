package com.dada.videstation.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dada on 02/11/2015.
 */
public class Actor implements Serializable
{
    private int id, nbZod, nbMovie;
    private Mapper mapper;
    private String actor;
    private Date createDate, modifyDate;

    public Actor()
    {
        this.nbMovie = -1;
        this.nbZod = -1;
    }

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
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor.trim();
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

    public int getNbZod() {
        return nbZod;
    }

    public void setNbZod(int nbZod) {
        this.nbZod = nbZod;
    }

    public int getNbMovie() {
        return nbMovie;
    }

    public void setNbMovie(int nbMovie) {
        this.nbMovie = nbMovie;
    }

    @Override
    public String toString() {
        return getActor();
    }
}

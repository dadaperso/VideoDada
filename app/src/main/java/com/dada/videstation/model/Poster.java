package com.dada.videstation.model;

import java.util.Date;

/**
 * Created by dada on 24/12/2015.
 */
public class Poster
{
    private int id, lo_oid;
    private String md5;
    private Date createDate, modifyDate;
    private Mapper mapper;

    public int getId() {
        return id;
    }

    public Mapper getMapper() {
        return mapper;
    }

    public int getLo_oid() {
        return lo_oid;
    }

    public String getMd5() {
        return md5;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLo_oid(int lo_oid) {
        this.lo_oid = lo_oid;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }
}

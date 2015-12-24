package com.dada.videstation.model;

import java.text.DateFormat;

/**
 * Created by dada on 24/12/2015.
 */
public class Poster
{
    private int id, lo_oid;
    private String md5;
    private DateFormat createDate, modifyDate;

    public int getId() {
        return id;
    }

    public int getLo_oid() {
        return lo_oid;
    }

    public String getMd5() {
        return md5;
    }

    public DateFormat getCreateDate() {
        return createDate;
    }

    public DateFormat getModifyDate() {
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

    public void setCreateDate(DateFormat createDate) {
        this.createDate = createDate;
    }

    public void setModifyDate(DateFormat modifyDate) {
        this.modifyDate = modifyDate;
    }
}

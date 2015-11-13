package com.dada.videstation.model;

import java.io.Serializable;

/**
 * Created by dada on 26/10/2015.
 */
public class Mapper implements Serializable{

    private int id;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

package com.dada.videstation.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dada on 26/11/2015.
 */
public interface Item extends Serializable{

    void setType(String type);

    String getType();

    String getTitle();

    Date getReleaseDate();


}

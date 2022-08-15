package com.flatcode.simplemultiapps.NewsApp.Model;

import com.flatcode.simplemultiapps.Unit.DATA;

import java.io.Serializable;

public class Source implements Serializable {

    String id = DATA.EMPTY, name = DATA.EMPTY;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
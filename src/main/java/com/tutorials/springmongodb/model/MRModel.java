package com.tutorials.springmongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "mapreduce")
public class MRModel {

    @Id
    private String id;

    private String[] x;

    public MRModel(String[] x) {
        this.x = x;
    }

    public String[] getX() {
        return x;
    }

    public void setX(String[] x) {
        this.x = x;
    }
}

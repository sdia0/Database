package com.example.database;

import java.io.Serializable;

public class Person implements Serializable {
    private int id;
    private String name;
    private String email;
    private String image;

    public Person(String name, String email, String image) {
        this.name = name;
        this.email = email;
        this.image = image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
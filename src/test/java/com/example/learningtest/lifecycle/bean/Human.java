package com.example.learningtest.lifecycle.bean;

import jakarta.annotation.PostConstruct;

public class Human {
    private String name = "기본이름";

    public void setName(String name) {
        System.out.println("Human.setName");
        this.name = name;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Human.postConstruct");
    }

    public String getName() {
        return name;
    }
}

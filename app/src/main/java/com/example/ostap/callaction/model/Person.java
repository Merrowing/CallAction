package com.example.ostap.callaction.model;

import java.io.Serializable;

public class Person implements Serializable {
    String name;
    String phone;
    Boolean check;

    public Person(String name, String phone, Boolean check){
        this.name = name;
        this.phone = phone;
        this.check = check;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

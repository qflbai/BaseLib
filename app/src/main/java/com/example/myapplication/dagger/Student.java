package com.example.myapplication.dagger;

import javax.inject.Inject;

/**
 * @author: qflbai
 * @CreateDate: 2019/9/20 0020 11:00
 * @Version: 1.0
 * @description:
 */
public class Student {

    private String name;
    @Inject
    public Student(){

    }


    public Student(String s){
        name = s;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

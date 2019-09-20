package com.example.myapplication.dagger;

import android.app.Application;

import javax.inject.Inject;

/**
 * @author: qflbai
 * @CreateDate: 2019/9/20 0020 11:00
 * @Version: 1.0
 * @description:
 */
public class Student {

    private String name;
    private Application mApplication;

    public Student(Li li){
        name = li.getName();
    }
    @Inject
    public Student(Application application,Li li){
        name = li.getName();
        mApplication = application;
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

    public Application getmApplication() {
        return mApplication;
    }

}

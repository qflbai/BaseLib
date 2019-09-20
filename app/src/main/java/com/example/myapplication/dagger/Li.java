package com.example.myapplication.dagger;

import javax.inject.Inject;

/**
 * @author: qflbai
 * @CreateDate: 2019/9/20 0020 15:03
 * @Version: 1.0
 * @description:
 */
public class Li {

    private final String name;
    @Inject
    public Li(){
        name = "dadaf";
    }

    public String getName() {
        return name;
    }
}

package com.example.myapplication.dagger.id;

import com.example.myapplication.MainActivity;

import dagger.Component;

/**
 * @author: qflbai
 * @CreateDate: 2019/9/20 0020 11:02
 * @Version: 1.0
 * @description:
 */
@Component
public interface DraggerComponent {

    void inject(MainActivity activity);

}

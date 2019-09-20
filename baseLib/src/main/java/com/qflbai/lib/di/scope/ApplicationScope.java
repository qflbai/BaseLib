package com.qflbai.lib.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * @author: qflbai
 * @CreateDate: 2019/9/20 0020 9:14
 * @Version: 1.0
 * @description:
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationScope {
}

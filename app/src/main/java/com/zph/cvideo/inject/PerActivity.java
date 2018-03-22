package com.zph.cvideo.inject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 *
 * @author zph
 * @date 2018/3/21
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}

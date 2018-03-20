package com.zph.cvideo.di.component;


import com.zph.cvideo.di.PerActivity;
import com.zph.cvideo.di.module.ActivityModule;
import com.zph.cvideo.ui.main.MainActivity;

import dagger.Component;

/**
 * @author zph
 * @date 2018/3/20
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {


    void inject(MainActivity mainActivity);

}

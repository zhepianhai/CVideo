package com.zph.cvideo.inject.component;

import com.zph.cvideo.base.BaseMainFragment;
import com.zph.cvideo.frag.home.FragHome;
import com.zph.cvideo.frag.infomation.FragInfomation;
import com.zph.cvideo.frag.more.FragMore;
import com.zph.cvideo.inject.PerActivity;
import com.zph.cvideo.inject.model.ActivityModule;
import com.zph.cvideo.ui.main.MainActivity;

import dagger.Component;

/**
 * @author zph
 * @date 2018/3/21
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
    void inject(BaseMainFragment baseMainFragment);
    void inject(FragHome fragHome);
    void inject(FragInfomation fragInfomation);
    void inject(FragMore fragMore);
}

package com.example.mvp_demo.injector.component;

import com.example.mvp_demo.injector.modules.NavigationModule;
import com.example.mvp_demo.injector.scope.PerFragment;
import com.example.mvp_demo.ui.fragment.NavigationFragment;

import dagger.Component;

/**
 * Created by yangfan on 2017/5/2.
 */

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = NavigationModule.class)
public interface NavigationCpmponent {
    void inject(NavigationFragment fragment);

}

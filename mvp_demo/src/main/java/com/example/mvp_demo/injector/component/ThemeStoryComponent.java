package com.example.mvp_demo.injector.component;

import com.example.mvp_demo.injector.modules.ThemeStoryModule;
import com.example.mvp_demo.injector.scope.PerFragment;
import com.example.mvp_demo.ui.fragment.ThemeStoriesFragment;

import dagger.Component;

/**
 * Created by yangfan on 2017/7/23.
 */

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = ThemeStoryModule.class)
public interface ThemeStoryComponent {
    void inject(ThemeStoriesFragment fragment);
}

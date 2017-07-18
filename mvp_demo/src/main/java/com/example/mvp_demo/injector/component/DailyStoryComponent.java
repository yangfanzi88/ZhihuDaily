package com.example.mvp_demo.injector.component;

import com.example.mvp_demo.injector.modules.DailyStoryModule;
import com.example.mvp_demo.injector.scope.PerFragment;
import com.example.mvp_demo.ui.fragment.DailyStoriesFragment;

import dagger.Component;

/**
 * Created by yangfan on 2017/6/12.
 */

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = DailyStoryModule.class)
public interface DailyStoryComponent {
    void inject(DailyStoriesFragment fragment);
}

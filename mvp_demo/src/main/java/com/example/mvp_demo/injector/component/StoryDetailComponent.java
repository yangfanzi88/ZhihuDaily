package com.example.mvp_demo.injector.component;

import com.example.mvp_demo.injector.modules.StoryDetailModule;
import com.example.mvp_demo.injector.scope.PerActivity;
import com.example.mvp_demo.injector.scope.PerFragment;
import com.example.mvp_demo.ui.activity.StoryDetailActivity;
import com.example.mvp_demo.ui.fragment.StoryDetailFragment;

import dagger.Component;

/**
 * Created by yangfan on 2017/8/27.
 */

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = StoryDetailModule.class)
public interface StoryDetailComponent {
     void inject(StoryDetailFragment fragment);
}

package com.example.mvp_demo.injector.component;

import com.example.mvp_demo.injector.modules.StoryCommentModule;
import com.example.mvp_demo.injector.scope.PerFragment;
import com.example.mvp_demo.ui.fragment.StoryCommentFragment;

import dagger.Component;

/**
 * Created by yangfan on 2017/10/8.
 */

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = StoryCommentModule.class)
public interface StoryCommentComponent {
    void inject(StoryCommentFragment fragment);
}

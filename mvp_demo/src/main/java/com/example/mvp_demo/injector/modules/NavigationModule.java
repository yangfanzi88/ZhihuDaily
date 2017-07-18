package com.example.mvp_demo.injector.modules;

import com.example.mvp_demo.injector.scope.PerFragment;
import com.example.mvp_demo.mvpPresenter.IBasePresenter;
import com.example.mvp_demo.mvpPresenter.NavigationPresenter;
import com.example.mvp_demo.ui.adapter.NavigationAdapter;
import com.example.mvp_demo.ui.fragment.NavigationFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yangfan on 2017/5/2.
 */

@Module
public class NavigationModule {

    private final NavigationFragment mNavigationView;

    public NavigationModule(NavigationFragment mNavigationView) {
        this.mNavigationView = mNavigationView;
    }

    @PerFragment
    @Provides
    public IBasePresenter providerPresenter(){
        return new NavigationPresenter(mNavigationView);
    }

    @PerFragment
    @Provides
    public NavigationAdapter providerAdapter(){
        return new NavigationAdapter(mNavigationView.getActivity());
    }
}

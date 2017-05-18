package com.example.mvp_demo.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mvp_demo.DailyApplication;
import com.example.mvp_demo.R;
import com.example.mvp_demo.injector.component.DaggerNavigationCpmponent;
import com.example.mvp_demo.injector.modules.NavigationModule;
import com.example.mvp_demo.mode.beans.DailyThemes;
import com.example.mvp_demo.presenter.NavigationPresenter;
import com.example.mvp_demo.ui.activity.MainActivity;
import com.example.mvp_demo.ui.adapter.NavigationAdapter;
import com.example.mvp_demo.view.INavigationView;
import com.trello.rxlifecycle.LifecycleTransformer;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yangfan on 2017/4/24.
 */

public class NavigationFragment extends Fragment implements  INavigationView {
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    public static final String TAG = NavigationFragment.class.getSimpleName();

    @Inject
    NavigationPresenter navigationPresenter;
    @Inject
    NavigationAdapter mAdapter;
    @Inject
    NavigationPresenter mPresenter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    int mCurrentSelectedPosition = -1;
    List<DailyThemes.DailyTheme> mThemeList;
    private Context mContext;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
        }
        initInjector();
        mAdapter = new NavigationAdapter(this.getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_navigation, container, false);
        ButterKnife.bind(this, rootView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(onItemClickListener);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.requestData(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    private void initInjector(){
        DaggerNavigationCpmponent.builder()
                .applicationComponent(DailyApplication.getAppComponent())
                .navigationModule(new NavigationModule(this))
                .build()
                .inject(this);
    }


    NavigationAdapter.OnItemClickListener onItemClickListener = new NavigationAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            if (mCurrentSelectedPosition == position) {
                ((MainActivity)getActivity()).closeDrawer();
                return;
            }
            ((MainActivity)getActivity()).closeDrawer();
            mCurrentSelectedPosition = position;
        }
    };

    public String getTitle(int position){
        if(mThemeList == null || mThemeList.size() == 0){
            return getString(R.string.navigation_first_item);
        }
        return position == 0? getActivity().getString(R.string.navigation_first_item) : mThemeList.get(position-1).getName();
    }

    @Override
    public void showThemes(DailyThemes themes) {
        mThemeList = themes.getSubscribed();
        mThemeList.addAll(themes.getOthers());
        mAdapter.setThemes(themes);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return null;
    }
}

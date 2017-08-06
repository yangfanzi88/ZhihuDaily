package com.example.mvp_demo.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mvp_demo.R;
import com.example.mvp_demo.injector.component.DaggerNavigationComponent;
import com.example.mvp_demo.injector.modules.NavigationModule;
import com.example.mvp_demo.mvpMode.beans.DailyThemes;
import com.example.mvp_demo.mvpPresenter.IBasePresenter;
import com.example.mvp_demo.ui.activity.MainActivity;
import com.example.mvp_demo.ui.adapter.NavigationAdapter;
import com.example.mvp_demo.utils.Logger;
import com.example.mvp_demo.mvpView.INavigationView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


/**
 * Created by yangfan on 2017/4/24.
 */

public class NavigationFragment extends BaseFragment<IBasePresenter> implements  INavigationView,NavigationDrawerCallbacks{
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    public static final String TAG = NavigationFragment.class.getSimpleName();


    @Inject
    NavigationAdapter mAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    int mCurrentSelectedPosition = -1;
    List<DailyThemes.DailyTheme> mThemeList = new ArrayList<>();
    private Context mContext;
    private NavigationDrawerCallbacks mCallbacks;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
        }
    }

    /*生命周期回调*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallbacks = (NavigationDrawerCallbacks) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks!");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
//        mAdapter.setOnItemClickListener(onItemClickListener);
        mAdapter.setNavigationDrawerCallbacks(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.requestData(false);
        Logger.e(TAG, "updateView 调用一次");
    }

    @Override
    protected void initInjector() {
        DaggerNavigationComponent.builder()
                .applicationComponent(getAppComponent())
                .navigationModule(new NavigationModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showThemes(DailyThemes themes) {
        if(mThemeList!=null && mThemeList.size()>0 ){
            mThemeList.clear();
        }

        mThemeList.addAll(themes.getSubscribed());
        mThemeList.addAll(themes.getOthers());
        mAdapter.setThemes(themes);
    }

//    NavigationAdapter.OnItemClickListener onItemClickListener = new NavigationAdapter.OnItemClickListener() {
//        @Override
//        public void onItemClick(View view, int position) {
//            if (mCurrentSelectedPosition == position) {
//                ((MainActivity)getActivity()).closeDrawer();
//                return;
//            }
//            ((MainActivity)getActivity()).closeDrawer();
//            mCurrentSelectedPosition = position;
//
//            ((MainActivity)getActivity()).mTitle = getTitle(position);
//            ((MainActivity)getActivity()).setActionBar();
//        }
//    };

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        if (mCurrentSelectedPosition == position) {
            ((MainActivity)getActivity()).closeDrawer();
            return;
        }
        ((MainActivity)getActivity()).closeDrawer();
        mCurrentSelectedPosition = position;

//        ((MainActivity)getActivity()).mTitle = getTitle(position);
//        ((MainActivity)getActivity()).setActionBar();
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }

    public String getTitle(int position){
        if(mThemeList == null || mThemeList.size() == 0){
            return getString(R.string.navigation_first_item);
        }
        return position == 0? getActivity().getString(R.string.navigation_first_item) : mThemeList.get(position-1).getName();
    }

    public int getThemeId(int position){
        if(mThemeList == null || mThemeList.size() == 0){
            return 0;
        }
        return position == 0? 0 : mThemeList.get(position-1).getId();
    }

    public void selectItem(int position) {
        if (position == mCurrentSelectedPosition) {
            ((MainActivity)getActivity()).closeDrawer();
            return;
        }
        mAdapter.selectPosition(position);
    }
}

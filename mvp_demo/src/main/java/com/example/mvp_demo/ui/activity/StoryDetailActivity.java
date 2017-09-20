package com.example.mvp_demo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvp_demo.R;
import com.example.mvp_demo.ui.fragment.BaseFragment;
import com.example.mvp_demo.ui.fragment.StoryDetailFragment;

import butterknife.BindView;

/**
 * Created by yangfan on 2017/8/27.
 */

public class StoryDetailActivity extends BaseActivity{

    private static final String ARG_STORY_ID = "story_id";

    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.actionbarToolbar)
    Toolbar actionBar;

    private int mStoryId;

    public static void launcherActivity(Activity activity, Class<? extends BaseFragment> clazz, String args){
        Intent intent = new Intent(activity, StoryDetailActivity.class);
        intent.putExtra("className", clazz.getName());
        if (args != null)
            intent.putExtra(ARG_STORY_ID, args);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mStoryId = Integer.parseInt(getIntent().getStringExtra(ARG_STORY_ID));
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int inflateContentView() {
        return R.layout.activity_story;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        replaceFragment(R.id.container, StoryDetailFragment.newInstance(mStoryId), String.valueOf(mStoryId));
    }

    @Override
    protected void initInjector() {
        /*DaggerStoryDetailComponent.builder()
                .applicationComponent(DailyApplication.getAppComponent())
                .storyDetailModule(new StoryDetailModule(this, mStoryId))
                .build()
                .Inject(this);*/
    }

    @Override
    protected void updateUI(boolean isRefresh) {
//        mPresenter.requestData(false);
    }

//    @Override
//    public void showDetail(StoryDetail detail) {
//        Toast.makeText(this,"详情页请求成功",Toast.LENGTH_SHORT).show();
//    }

    public Toolbar getToolbar(){
        return actionBar;
    }

    @Override
    public void onBackPressed() {
        //当点击返回键的时候，获取fragment stack里面fragment数量
        final int stackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if(stackEntryCount == 1){
            //如果只剩下一个fragment，直接退出这个activity
            finish();
        }else {
            //获取上一个fragment的name，刷新主页
            super.onBackPressed();
        }
    }
}

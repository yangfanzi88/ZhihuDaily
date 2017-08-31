package com.example.mvp_demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mvp_demo.DailyApplication;
import com.example.mvp_demo.R;
import com.example.mvp_demo.injector.component.DaggerStoryDetailComponent;
import com.example.mvp_demo.injector.modules.StoryDetailModule;
import com.example.mvp_demo.mvpMode.beans.StoryDetail;
import com.example.mvp_demo.mvpView.IStoryDetailView;

import java.lang.ref.SoftReference;

import butterknife.BindView;

/**
 * Created by yangfan on 2017/8/27.
 */

public class StoryDetailFragment extends BaseFragment implements IStoryDetailView {

    private static final String ARG_STORY_ID = "story_id";

    @BindView(R.id.story_detail_layout)
    RelativeLayout relativeLayout;
    @BindView(R.id.story_detail_header_image)
    ImageView headerImage;
    @BindView(R.id.story_detail_image_source)
    TextView headerSource;
    @BindView(R.id.story_detail_title)
    TextView storyTitle;


    private SoftReference<WebView> mWebViewSoftReference;
    private int mStoryId;

    public static StoryDetailFragment newInstance(int storyId) {

        Bundle args = new Bundle();
        args.putInt(ARG_STORY_ID, storyId);
        StoryDetailFragment fragment = new StoryDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mStoryId = getArguments().getInt(ARG_STORY_ID);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_story_detail;
    }

    @Override
    protected void initInjector() {
        DaggerStoryDetailComponent.builder()
                .applicationComponent(getAppComponent())
                .storyDetailModule(new StoryDetailModule(this,mStoryId))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        setHasOptionsMenu(true);
        mWebViewSoftReference = new SoftReference<>(new WebView(getActivity()));
        if(isWebViewOK()){
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mWebViewSoftReference.get().setLayoutParams(layoutParams);
            relativeLayout.addView(mWebViewSoftReference.get());
        }

    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.requestData(false);
    }

    @Override
    public void showDetail(StoryDetail detail) {

    }

    private boolean isWebViewOK(){
        return mWebViewSoftReference != null && mWebViewSoftReference.get() != null;
    }
}

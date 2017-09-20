package com.example.mvp_demo.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.example.mvp_demo.R;
import com.example.mvp_demo.injector.component.DaggerStoryDetailComponent;
import com.example.mvp_demo.injector.modules.StoryDetailModule;
import com.example.mvp_demo.mvpMode.beans.Editor;
import com.example.mvp_demo.mvpMode.beans.StoryDetail;
import com.example.mvp_demo.mvpView.IStoryDetailView;
import com.example.mvp_demo.ui.activity.StoryDetailActivity;
import com.example.mvp_demo.utils.ScrollPullDownHelper;
import com.example.mvp_demo.utils.WebUtils;
import com.example.mvp_demo.widget.AvatarsView;
import com.example.mvp_demo.widget.StoryHeaderView;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yangfan on 2017/8/27.
 */

public class StoryDetailFragment extends BaseFragment implements IStoryDetailView {

    private static final String ARG_STORY_ID = "story_id";

    @BindView((R.id.rl_container_header))
    RelativeLayout rlStoryHeader;
    @BindView(R.id.story_detail_scroll)
    ScrollView scrollView;
    @BindView(R.id.story_detail_layout)
    LinearLayout detailLayout;
    @BindView(R.id.spaceView)
    View spaceView;

    private StoryHeaderView mStoryHeaderView;
    private AvatarsView mAvatarsView;
    private SoftReference<WebView> mWebViewSoftReference;
    private int mStoryId;
    private Toolbar mActionBarToolbar;
    private ScrollPullDownHelper mScrollPullDownHelper;

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
        mScrollPullDownHelper = new ScrollPullDownHelper();
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
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mWebViewSoftReference.get().setLayoutParams(layoutParams);
            detailLayout.addView(mWebViewSoftReference.get());
        }

        mStoryHeaderView = StoryHeaderView.newInstance(mContext);
        mAvatarsView = AvatarsView.newInstance(mContext);
        mActionBarToolbar = ((StoryDetailActivity)getActivity()).getToolbar();
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.requestData(false);
    }

    @Override
    public void showDetail(StoryDetail detail) {
        boolean hasImage = !TextUtils.isEmpty(detail.getImage());
        bindHeaderView(detail,hasImage);
        bindAvatarsView(detail);
        bindWebView(detail,hasImage);
    }

    private void bindHeaderView(StoryDetail detail,boolean hasImage){
        if(hasImage){
            if (mActionBarToolbar != null) {
//                mActionBarToolbar.getBackground().mutate().setAlpha(1);
            }
            spaceView.setVisibility(View.VISIBLE);
            rlStoryHeader.addView(mStoryHeaderView);
            mStoryHeaderView.bindData(detail.getTitle(), detail.getImage_source(), detail.getImage());
        }else {
            spaceView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    mActionBarToolbar.getHeight()));
        }
    }

    private void bindAvatarsView(StoryDetail detail){
        List<Editor> editors = detail.getRecommenders();
        if (editors != null && editors.size() > 0) {
            List<String> avatars = new ArrayList<>(editors.size());
            for (Editor editor : editors) {
                avatars.add(editor.getAvatar());
            }
            mAvatarsView.bindData(getString(R.string.avatar_title_editor), avatars);
            detailLayout.addView(mAvatarsView);
        }
    }

    private boolean isWebViewOK(){
        return mWebViewSoftReference != null && mWebViewSoftReference.get() != null;
    }

    private void bindWebView(StoryDetail detail,boolean hasImage) {
        if (TextUtils.isEmpty(detail.getBody())) {
            WebSettings settings = mWebViewSoftReference.get().getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setAppCacheEnabled(true);
            mWebViewSoftReference.get().setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }
            });
            mWebViewSoftReference.get().loadUrl(detail.getShare_url());
        } else {
//            boolean isNightMode = SharedPrefUtils.isNightMode(getContext());
            String data = WebUtils.buildHtmlWithCss(detail.getBody(), detail.getCss(), false);
            mWebViewSoftReference.get().loadDataWithBaseURL(WebUtils.BASE_URL, data, WebUtils.MIME_TYPE, WebUtils.ENCODING, WebUtils.FAIL_URL);
            if (hasImage) {
                addScrollListener();
            }
        }
    }

    private void addScrollListener() {
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (!isAdded()) {
                    return;
                }
                changeHeaderPosition();
                changeToolbarAlpha();
            }
        });
    }

    private void changeHeaderPosition() {
        int scrollY = scrollView.getScrollY();
        int headerScrollY = (scrollY > 0) ? (scrollY / 2) : 0;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            mStoryHeaderView.setScrollY(headerScrollY);
            mStoryHeaderView.requestLayout();
        }
    }

    /**
     * 1.如果滚动量小于图片和toolbar的差值,设置toolbar透明度,坐标为0,直接返回
     * 2.如果滚动两大于差值,则开始修改toolbar的坐标,y坐标为滚动量的1中差值的差值
     */
    private void changeToolbarAlpha() {
        int scrollY = scrollView.getScrollY();
        int storyHeaderViewHeight = getStoryHeaderViewHeight();
        float toolbarHeight = mActionBarToolbar.getHeight();
        float contentHeight = storyHeaderViewHeight - toolbarHeight;

        float ratio = Math.min(scrollY / contentHeight, 1.0f);
        mActionBarToolbar.getBackground().mutate().setAlpha((int) (ratio * 0xFF));

        if (scrollY <= contentHeight) {
            mActionBarToolbar.setY(0f);
            return;
        }

        boolean isPullingDown = mScrollPullDownHelper.onScrollChange(scrollY);
        float toolBarPositionY = isPullingDown ? 0 : (contentHeight - scrollY);
        mActionBarToolbar.setY(toolBarPositionY);

    }

    private int getStoryHeaderViewHeight() {
        return getResources().getDimensionPixelSize(R.dimen.view_header_story_height);
    }

    public void setToolBar(Toolbar toolbar) {
        this.mActionBarToolbar = toolbar;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (isWebViewOK()) {
            mWebViewSoftReference.get().onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isWebViewOK()) {
            mWebViewSoftReference.get().onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isWebViewOK()) {
            mWebViewSoftReference.get().removeAllViews();
            mWebViewSoftReference.get().destroy();
            detailLayout.removeView(mWebViewSoftReference.get());
            mWebViewSoftReference = null;
        }
    }

}

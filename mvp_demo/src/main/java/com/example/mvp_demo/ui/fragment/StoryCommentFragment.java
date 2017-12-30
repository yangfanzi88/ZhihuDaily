package com.example.mvp_demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.mvp_demo.DailyApplication;
import com.example.mvp_demo.R;
import com.example.mvp_demo.injector.component.DaggerStoryCommentComponent;
import com.example.mvp_demo.injector.modules.StoryCommentModule;
import com.example.mvp_demo.mvpMode.beans.StoryComment;
import com.example.mvp_demo.mvpMode.beans.StoryExtra;
import com.example.mvp_demo.mvpPresenter.IBasePresenter;
import com.example.mvp_demo.mvpPresenter.StoryCommentPresenter;
import com.example.mvp_demo.mvpView.IStoryCommentView;
import com.example.mvp_demo.ui.activity.StoryDetailActivity;
import com.example.mvp_demo.ui.adapter.OnItemClickListener;
import com.example.mvp_demo.ui.adapter.StoryCommentAdapter;
import com.example.mvp_demo.utils.OnRequestDataListener;
import com.example.mvp_demo.utils.SwipeLoadMoreHelper;
import com.example.mvp_demo.widget.MyDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by yangfan on 2017/10/3.
 */

public class StoryCommentFragment extends BaseFragment<IBasePresenter> implements IStoryCommentView, OnItemClickListener {

    private static String TAG = StoryCommentFragment.class.getSimpleName();
    private static int ONE_REQUEST_COUNT = 21;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Inject
    StoryCommentAdapter mCommentAdapter;

    private static final String ARG_STORY_ID = "story_id";
    private static final String ARG_STORY_EXTRA = "story_extra";
    private int mStoryId;
    private StoryExtra mStoryExtra;
    private int mLongCommentRequest = 0, mShortCommentRequest = 0;


    public static StoryCommentFragment newInstance(int storyId, StoryExtra extra) {
        Bundle args = new Bundle();
        args.putInt(ARG_STORY_ID, storyId);
        args.putSerializable(ARG_STORY_EXTRA, extra);
        StoryCommentFragment fragment = new StoryCommentFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStoryId = getArguments().getInt(ARG_STORY_ID);
        if(getArguments().getSerializable(ARG_STORY_EXTRA) != null){
            mStoryExtra = (StoryExtra) getArguments().getSerializable(ARG_STORY_EXTRA);
        }

        Toolbar mActionBarToolbar = ((StoryDetailActivity)getActivity()).getToolbar();
        if (mActionBarToolbar != null)
            mActionBarToolbar.setTitle(String.format(getContext().getResources().getString(R.string.story_detail_comment_toolbar_title), String.valueOf(mStoryExtra.getComments())));
    }

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_content;
    }

    @Override
    protected void initInjector() {
        DaggerStoryCommentComponent.builder()
                .applicationComponent(DailyApplication.getAppComponent())
                .storyCommentModule(new StoryCommentModule(this,mStoryId))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(mRecyclerView.getAdapter() == null){
            mRecyclerView.setAdapter(mCommentAdapter);
        }
        mCommentAdapter.setCommentExtra(mStoryExtra);
        mRecyclerView.addItemDecoration(new MyDecoration(getContext(), MyDecoration.VERTICAL_LIST));
        mCommentAdapter.setOnItemClickListener(this);

        final int longTimes = mStoryExtra.getLong_comments() / ONE_REQUEST_COUNT + 1;
        final int shortTimes = mStoryExtra.getShort_comments() / ONE_REQUEST_COUNT + 1;
        SwipeLoadMoreHelper helper = new SwipeLoadMoreHelper(mContext);
        helper.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                if(mLongCommentRequest < longTimes){
                    ((StoryCommentPresenter)mPresenter).requestMoreComment(true);
                }else if(mShortCommentRequest < shortTimes){
                    ((StoryCommentPresenter)mPresenter).requestMoreComment(false);
                }
            }
        });
        mRecyclerView.setOnScrollListener(helper);
    }



    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.requestData(isRefresh);
    }


    @Override
    public void showComment(StoryComment storyComment, boolean longComment) {
        if(longComment){
            mLongCommentRequest ++;
            mCommentAdapter.setCommentList(storyComment, StoryCommentAdapter.LONG_COMMENT);
        }else {
            mShortCommentRequest ++;
            mCommentAdapter.setCommentList(storyComment, StoryCommentAdapter.SHORT_COMMENT);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()){
            case R.id.story_comment_more:
                if(mShortCommentRequest == 0){
                    if(mStoryExtra.getShort_comments() > 0){
                        Toast.makeText(getContext(), "开始加载短评！", Toast.LENGTH_SHORT).show();
                        if(mPresenter instanceof StoryCommentPresenter ){
                            ((StoryCommentPresenter)mPresenter).requestShortComment();
                        }
                        mShortCommentRequest ++;
                    }
                }else {

                }
                break;
        }
    }
}

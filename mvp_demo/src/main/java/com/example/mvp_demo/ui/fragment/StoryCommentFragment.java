package com.example.mvp_demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mvp_demo.DailyApplication;
import com.example.mvp_demo.R;
import com.example.mvp_demo.injector.component.DaggerStoryCommentComponent;
import com.example.mvp_demo.injector.modules.StoryCommentModule;
import com.example.mvp_demo.mvpMode.beans.StoryComment;
import com.example.mvp_demo.mvpMode.beans.StoryExtra;
import com.example.mvp_demo.mvpPresenter.IBasePresenter;
import com.example.mvp_demo.mvpView.IStoryCommentView;
import com.example.mvp_demo.ui.adapter.StoryCommentAdapter;
import com.example.mvp_demo.widget.MyDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by yangfan on 2017/10/3.
 */

public class StoryCommentFragment extends BaseFragment<IBasePresenter> implements IStoryCommentView {

    private static String TAG = StoryCommentFragment.class.getSimpleName();

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Inject
    StoryCommentAdapter mCommentAdapter;

    private static final String ARG_STORY_ID = "story_id";
    private static final String ARG_STORY_EXTRA = "story_extra";
    private int mStoryId;
    private StoryExtra mStoryExtra;


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
    }



    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.requestData(isRefresh);
    }


    @Override
    public void showComment(StoryComment storyComment) {
        mCommentAdapter.setCommentList(storyComment, StoryCommentAdapter.LONG_COMMENT);
    }
}

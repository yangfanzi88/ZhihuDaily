package com.example.mvp_demo.mvpView;

import com.example.mvp_demo.mvpMode.beans.StoryComment;

/**
 * Created by yangfan on 2017/10/8.
 */

public interface IStoryCommentView extends IBaseView{
    void showComment(StoryComment storyComment);
}

package com.example.mvp_demo.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.icu.text.SimpleDateFormat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mvp_demo.R;
import com.example.mvp_demo.mvpMode.beans.StoryComment;
import com.example.mvp_demo.mvpMode.beans.StoryExtra;
import com.example.mvp_demo.widget.CircleTransform;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yangfan on 2017/10/12.
 */

public class StoryCommentAdapter extends RecyclerView.Adapter {

    public static final int TYPE_TITLE = 0;
    public static final int TYPE_CONTENT = 1;
    public static final int TYPE_EMPTY = 2;

    public static final String LONG_COMMENT = "long";
    public static final String SHORT_COMMENT = "short";
    public static final String EMPTY_COMMENT = "empty";

    private StoryExtra mStoryExtra;
    private List<StoryComment.Comment> commentList = new ArrayList<>();
    private int shortCommentPosition = 1;//记录短评title的位置

    public StoryCommentAdapter() {
        StoryComment.Comment longComment = new StoryComment.Comment(LONG_COMMENT);
        StoryComment.Comment shortComment = new StoryComment.Comment(SHORT_COMMENT);
        commentList.add(longComment);
        commentList.add(shortComment);
    }

    public void setCommentExtra(StoryExtra storyExtra){
        this.mStoryExtra = storyExtra;
    }

    public void setCommentList(StoryComment storyComment, String type){
        if(type.equals(LONG_COMMENT) && storyComment.getComments() != null){
            if (storyComment.getComments().size() == 0 && commentList.get(1).getType().equals(SHORT_COMMENT)){
                commentList.add(1,new StoryComment.Comment(EMPTY_COMMENT));
                shortCommentPosition += 1;
            }else {
                commentList.addAll(shortCommentPosition,storyComment.getComments());
                shortCommentPosition += storyComment.getComments().size();
            }
        }else {
            commentList.addAll(storyComment.getComments());
        }

        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View itemView;
        switch (viewType){
            case TYPE_TITLE:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_detail_comment_type, parent, false );
                holder = new CommentTypeHolder(itemView);
                break;
            case TYPE_CONTENT:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_detail_comment_item, parent, false);
                holder = new CommentItemHolder(itemView);
                break;
            case TYPE_EMPTY:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_detail_comment_empty, parent, false);
                holder = new CommentEmptyHolder(itemView);
                break;
            default:break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type){
            case TYPE_TITLE:
                Resources resources = holder.itemView.getResources();
                if(position == 0){
                    String name = String.format(resources.getString(R.string.story_detail_comment_title), mStoryExtra.getLong_comments(), resources.getString(R.string.story_detail_comment_long));
                    ((CommentTypeHolder)holder).bindTypeData(name);
                }else {
                    String name = String.format(resources.getString(R.string.story_detail_comment_title), mStoryExtra.getShort_comments(), resources.getString(R.string.story_detail_comment_short));
                    ((CommentTypeHolder)holder).bindTypeData(name);
                }
                break;
            case TYPE_CONTENT:
                /*if(position <= mLongCommentList.size()){
                    ((CommentItemHolder)holder).bindItemData(mLongCommentList.get(position));
                }else {
                    ((CommentItemHolder)holder).bindItemData(mShortCommentList.get(position));
                }*/
                ((CommentItemHolder)holder).bindItemData(commentList.get(position));
                break;
            case TYPE_EMPTY:
                ((CommentEmptyHolder)holder).bindEmptyDate();
                break;
            default:break;
        }
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    @Override
    public int getItemViewType(int position) {
        String type = commentList.get(position).getType();
        if(!TextUtils.isEmpty(type) && (type.equals(LONG_COMMENT) || type.equals(SHORT_COMMENT))){
            return TYPE_TITLE;
        }else if(!TextUtils.isEmpty(type) && type.equals(EMPTY_COMMENT)){
            return TYPE_EMPTY;
        }else {
            return TYPE_CONTENT;
        }
    }

    public static class CommentTypeHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.story_comment_type)
        TextView mCommentTypeName;
        @BindView(R.id.story_comment_more)
        ImageView mCommentMore;

        private Context mContext;

        public CommentTypeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mContext = itemView.getContext();
        }

        public void bindTypeData(String typeName){
            mCommentTypeName.setText(typeName);
            if(typeName.contains(mContext.getResources().getString(R.string.story_detail_comment_short))){
                mCommentMore.setVisibility(View.VISIBLE);
            }
        }
    }

    public static class CommentItemHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.story_detail_comment_item_avatar)
        ImageView mCommentAvatar;
        @BindView(R.id.story_detail_comment_item_name)
        TextView mCommentName;
        @BindView(R.id.story_detail_comment_item_like)
        TextView mCommentLike;
        @BindView(R.id.story_detail_comment_item_content)
        TextView mCommentContent;
        @BindView(R.id.story_detail_comment_item_date)
        TextView mCommentDate;
        @BindView(R.id.story_detail_comment_item_extend)
        TextView mCommentExtend;

        Context mContext;
        CircleTransform circleTransform;

        public CommentItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            circleTransform = new CircleTransform(mContext);
        }

        public void bindItemData(StoryComment.Comment storyComment){
            Glide.with(mContext).load(storyComment.getAvatar()).transform(circleTransform).into(mCommentAvatar);
            mCommentName.setText(storyComment.getAuthor());
            mCommentLike.setText(String.valueOf(storyComment.getLikes()));

            //内容
            String wholeContent;
            if(storyComment.getReply_to()!=null && storyComment.getReply_to().getStatus() == 0){
                wholeContent = storyComment.getContent() + "\n//" + storyComment.getReply_to().getAuthor()+": "+storyComment.getReply_to().getContent();
            }else {
                wholeContent = storyComment.getContent();
            }
            if(!TextUtils.isEmpty(storyComment.getType()) && storyComment.getType().equals("long")){
                mCommentContent.setText(wholeContent);
            }else {
                mCommentContent.setMaxLines(3);
                mCommentContent.setText(wholeContent);
            }

            mCommentLike.setText(String.valueOf(storyComment.getLikes()));
            try{
                Date dateOld = new Date(storyComment.getTime()); // 根据long类型的毫秒数生命一个date类型的时间
                String sDateTime = new SimpleDateFormat("MM-dd HH:mm").format(dateOld); // 把date类型的时间转换为string
                mCommentDate.setText(sDateTime);
            }catch (Exception e){e.printStackTrace();}

        }
    }

    public static class CommentEmptyHolder extends RecyclerView.ViewHolder{
        View mItemview;
        public CommentEmptyHolder(View itemView) {
            super(itemView);
            this.mItemview = itemView;
        }
        public void bindEmptyDate(){
        }
    }
}

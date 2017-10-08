package com.example.mvp_demo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
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
import com.example.mvp_demo.mvpMode.beans.DailyStory;
import com.example.mvp_demo.mvpMode.beans.DailyThemeStories;
import com.example.mvp_demo.mvpMode.beans.Editor;
import com.example.mvp_demo.widget.AvatarsView;
import com.example.mvp_demo.widget.StoryHeaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yangfan on 2017/7/23.
 */

public class ThemeStoryAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    private static final String TAG = ThemeStoryAdapter.class.getSimpleName();
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_AVATARS = 1;
    public static final int TYPE_ITEM = 2;
    private DailyThemeStories themeStories;
    private OnItemClickListener clickListener;
    public void setOnItemClickListener(OnItemClickListener clickListener){
        this.clickListener = clickListener;
    }

    public ThemeStoryAdapter() {
    }

    public void setTheme(DailyThemeStories themeStories){
        if(themeStories == null){
            return;
        }
        if(!TextUtils.isEmpty(themeStories.getBackground())){
            this.themeStories = themeStories;
        }else{
            this.themeStories.getStories().addAll(themeStories.getStories());
        }
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder mViewHolder = null;
        View mItemView = null;
        switch (viewType){
            case TYPE_HEADER:
                mItemView = StoryHeaderView.newInstance(parent.getContext());
                mViewHolder = new HeadViewHolder(mItemView);
                break;
            case TYPE_AVATARS:
                mItemView = AvatarsView.newInstance(parent.getContext());
                mViewHolder = new AvatarsHolder(mItemView);
                break;
            case TYPE_ITEM:
                mItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_content_item,parent,false);
                mViewHolder = new StoryContentHolder(mItemView);
        }
        if(mItemView != null && viewType != TYPE_HEADER){
            mItemView.setOnClickListener(this);
        }

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type){
            case TYPE_HEADER:
                ((StoryHeaderView)holder.itemView).bindData(themeStories.getDescription(),null,themeStories.getBackground());
                break;
            case TYPE_AVATARS:
                List<String> avatars = new ArrayList<String>();
                List<Editor> editors = themeStories.getEditors();
                if(editors!=null && editors.size()>0){
                    for (Editor editor: editors) {
                        avatars.add(editor.getAvatar());
                    }
                }
                ((AvatarsView)holder.itemView).bindData(holder.itemView.getResources().getString(R.string.avatar_title_editor), avatars);
                break;
            case TYPE_ITEM:
                final int storyPosition = hasEditors() ? position - 2 : position - 1;
                ((StoryContentHolder)holder).bindStoryView(themeStories.getStories().get(storyPosition));

                DailyStory story = themeStories.getStories().get(storyPosition);
                holder.itemView.setTag(story.getId());
                break;
            default:
                throw new IllegalArgumentException("error view type");
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if(themeStories != null){
            if (!TextUtils.isEmpty(themeStories.getBackground())) {
                count += 1;
            }
            if (hasEditors()) {
                count += 1;
            }
            if (themeStories.getStories() != null) {
                count += themeStories.getStories().size();
            }
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if(!TextUtils.isEmpty(themeStories.getBackground()) && position == 0){
            return TYPE_HEADER;
        }else if(themeStories.getEditors()!=null && themeStories.getEditors().size()>0 && position==1){
            return TYPE_AVATARS;
        }else {
            return TYPE_ITEM;
        }
    }

    private boolean hasEditors(){
        return themeStories.getEditors()!=null && themeStories.getEditors().size()>0;
    }

    @Override
    public void onClick(View v) {
        clickListener.onItemClick(v, (int)v.getTag());
    }

    private static class HeadViewHolder extends RecyclerView.ViewHolder{
        public HeadViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static class AvatarsHolder extends RecyclerView.ViewHolder{
        public AvatarsHolder(View itemView) {
            super(itemView);
        }
    }

    public static class StoryContentHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.story_content_cardView)
        CardView cardView;
        @BindView(R.id.story_content)
        RelativeLayout relativeLayout;
        @BindView(R.id.story_content_image)
        ImageView contentImage;
        @BindView(R.id.story_content_multiPic)
        ImageView multiImage;
        @BindView(R.id.story_content_text)
        TextView contentText;

        private Context mContext;

        public StoryContentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mContext = itemView.getContext();
        }

        public void bindStoryView(DailyStory story){
            contentText.setText(story.getTitle());
            String imageUrl = story.getImages() == null ? "" : story.getImages().get(0);
            if (TextUtils.isEmpty(imageUrl) || story.isMultipic()) {
                multiImage.setVisibility(View.GONE);
            } else if (story.isMultipic()) {
                multiImage.setVisibility(View.VISIBLE);
            }

            if (TextUtils.isEmpty(imageUrl)) {
                contentImage.setVisibility(View.GONE);
            } else {
                contentImage.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(imageUrl).centerCrop().into(contentImage);
            }
        }
    }

}

package com.example.mvp_demo.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mvp_demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yangfan on 2017/7/23
 */
public class StoryHeaderView extends RelativeLayout {
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tvAuthor)
    TextView author;

    public StoryHeaderView(Context context) {
        this(context, null);
    }

    public StoryHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StoryHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getResources().getDimensionPixelSize(R.dimen.view_header_story_height)));
        LayoutInflater.from(this.getContext()).inflate(R.layout.view_header_story, this, true);
        ButterKnife.bind(this);
    }

    public void bindData(String title, String author, String url) {
        this.title.setText(title);
        if (TextUtils.isEmpty(author)) {
            this.author.setVisibility(View.GONE);
        } else {
            this.author.setVisibility(View.VISIBLE);
            this.author.setText(author);
        }
        Glide.with(getContext()).load(url).centerCrop().into(image);
    }

    public static StoryHeaderView newInstance(ViewGroup container) {
        return new StoryHeaderView(container.getContext());
    }
}

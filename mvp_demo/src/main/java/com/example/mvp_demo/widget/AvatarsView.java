package com.example.mvp_demo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mvp_demo.R;
import com.example.mvp_demo.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hefuyi on 16/8/6.
 */
public class AvatarsView extends HorizontalScrollView {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.llAvatarContainer)
    LinearLayout llAvatarContainer;

    private CircleTransform mCircleTransform;

    public AvatarsView(Context context) {
        this(context, null);
    }

    public AvatarsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AvatarsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        setOverScrollMode(OVER_SCROLL_NEVER);
        mCircleTransform = new CircleTransform(getContext());
        LayoutInflater.from(getContext()).inflate(R.layout.view_avatars, this, true);
        ButterKnife.bind(this);
    }

    public void bindData(String name, List<String> images) {
        title.setText(name);
        int hw = Utils.dip2px(getContext(), 36);
        int margin = Utils.dip2px(getContext(), 8);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(hw, hw);
        layoutParams.setMargins(margin, margin, margin, margin);
        llAvatarContainer.removeAllViews();
        if (images != null && images.size() > 0) {
            for (String url : images) {
                ImageView imageView = new ImageView(getContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(layoutParams);
                llAvatarContainer.addView(imageView);
                Glide.with(getContext()).load(url).transform(mCircleTransform).into(imageView);
            }
        }
    }

    public static AvatarsView newInstance(ViewGroup container){
        return new AvatarsView(container.getContext());
    }
}

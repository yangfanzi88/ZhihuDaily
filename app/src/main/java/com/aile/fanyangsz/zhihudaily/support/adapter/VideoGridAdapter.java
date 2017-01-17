package com.aile.fanyangsz.zhihudaily.support.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aile.fanyangsz.zhihudaily.R;
import com.aile.fanyangsz.zhihudaily.support.beans.video.Item;
import com.aile.fanyangsz.zhihudaily.ui.activity.PlayVideoActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fanyang.sz on 2016/11/24.
 */

public class VideoGridAdapter extends BaseAdapter {

    private Context mContext;
    private List<Item> mItemList;

    public VideoGridAdapter(Context mContext, List<Item> mItemList) {
        this.mContext = mContext;
        this.mItemList = mItemList;
    }

    @Override
    public int getCount() {
//        return mItemList == null ? 0 : mItemList.size();
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.layout_third_grid_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mItemList.get(position).getType().equals("banner2")) {
            holder.tvTitle.setText("广告");
            String image = mItemList.get(position).getData().getImage();
            Uri coverUrl = Uri.parse(image);
            Picasso.with(mContext).load(coverUrl).placeholder(mContext.getDrawable(R.drawable.bg_holder_brown))
                    .into(holder.ivCover);

        } else {
            final String title = mItemList.get(position).getData().getTitle();
            holder.tvTitle.setText(title);
            String feed = mItemList.get(position).getData().getCover().getFeed();
            Uri coverUrl = Uri.parse(feed);
            Picasso.with(mContext).load(coverUrl).placeholder(mContext.getDrawable(R.drawable.bg_holder_brown))
                    .into(holder.ivCover);
            final String playUrl = mItemList.get(position).getData().getPlayUrl();


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlayVideoActivity.actionStart(mContext, playUrl, title);
                }
            });
        }
        return convertView;
    }

    public static class ViewHolder {
        @BindView(R.id.third_grid_item_cover)
        ImageView ivCover;
        @BindView(R.id.third_grid_item_title)
        TextView tvTitle;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}

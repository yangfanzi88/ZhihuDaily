package com.aile.fanyangsz.zhihudaily.support.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.aile.fanyangsz.zhihudaily.R;
import com.aile.fanyangsz.zhihudaily.support.beans.video.Item;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fanyang.sz on 2016/11/25.
 */

public class VideoAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mCategoryNameList;
    private List<List<Item>> mItemsList;

    public VideoAdapter(Context mContext, List<String> mCategoryNameList, List<List<Item>> mItemsList) {
        this.mContext = mContext;
        this.mCategoryNameList = mCategoryNameList;
        this.mItemsList = mItemsList;
    }

    @Override
    public int getCount() {
        return mCategoryNameList == null ? 0 : mCategoryNameList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCategoryNameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.layout_third_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String categoryName = mCategoryNameList.get(position);
        List<Item> itemList = mItemsList.get(position);

        holder.tvCategoryName.setText(categoryName);
        VideoGridAdapter gridAdapter = new VideoGridAdapter(mContext, itemList);
        holder.gvPreview.setAdapter(gridAdapter);
        return convertView;
    }

    public static class ViewHolder {
        @BindView(R.id.third_item_name)
        TextView tvCategoryName;
        @BindView(R.id.third_item_grid)
        GridView gvPreview;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}

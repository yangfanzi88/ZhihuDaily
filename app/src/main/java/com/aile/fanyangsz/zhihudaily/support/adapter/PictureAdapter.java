package com.aile.fanyangsz.zhihudaily.support.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aile.fanyangsz.zhihudaily.R;
import com.aile.fanyangsz.zhihudaily.support.beans.PictureBean;
import com.aile.fanyangsz.zhihudaily.support.beans.PictureBeans;
import com.squareup.picasso.Picasso;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fanyang.sz on 2016/11/14.
 */

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {

    private Context mContext;
    private PictureBeans mBeans;
    private BitmapTrans bitmapTrans;

    public PictureAdapter(Context context, PictureBeans mBeans) {
        mContext = context;
        this.mBeans = mBeans;
        bitmapTrans = new BitmapTrans();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.second_item_image)
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View girlView = LayoutInflater.from(context).inflate(R.layout.layout_second_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(girlView);
//        girlView.setOnClickListener(v -> {
//            PhotoActivity.actionStart(mContext, girls.get(viewHolder.getAdapterPosition()).getUrl());
//        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PictureAdapter.ViewHolder holder, int position) {
        PictureBean girl = mBeans.getResults().get(position);
        Picasso.with(mContext).load(girl.getUrl()).transform(bitmapTrans).config(Bitmap.Config.RGB_565).placeholder(mContext.getDrawable(R.drawable.bg_holder_brown)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mBeans.getResults().size();
    }
}

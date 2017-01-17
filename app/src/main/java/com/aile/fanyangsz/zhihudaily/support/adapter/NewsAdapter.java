package com.aile.fanyangsz.zhihudaily.support.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aile.fanyangsz.zhihudaily.R;
import com.aile.fanyangsz.zhihudaily.support.beans.NewsBeans;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fanyang.sz on 2016/11/8.
 */

public class NewsAdapter extends BaseAdapter {

    private Context mContext;
    private NewsBeans beans;

    public NewsAdapter(Context mContext, NewsBeans beans){
        this.mContext = mContext;
        this.beans = beans;
    }
    @Override
    public int getCount() {

        return beans==null?0:beans.getStories().size();
    }

    @Override
    public Object getItem(int position) {
        return beans.getStories().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView = View.inflate(mContext, R.layout.layout_first_item,null);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        if(!TextUtils.isEmpty(beans.getStories().get(position).getDayTime())){
            holder.textTime.setVisibility(View.VISIBLE);
            holder.cardView.setVisibility(View.GONE);
            holder.textTime.setText(beans.getStories().get(position).getDayTime());
        }else {
            holder.textTime.setVisibility(View.GONE);
            holder.cardView.setVisibility(View.VISIBLE);
            holder.textTitle.setText(beans.getStories().get(position).getTitle());
            Picasso.with(mContext).load(beans.getStories().get(position).getImages().get(0)).config(Bitmap.Config.RGB_565).
                    placeholder(mContext.getDrawable(R.drawable.bg_holder_brown)).into(holder.imageView);
        }

        return convertView;
    }



    public static class ViewHolder{
        @BindView(R.id.first_item_text_time)
        TextView textTime;
        @BindView(R.id.first_item_cardView)
        CardView cardView;
        @BindView(R.id.first_item_text)
        TextView textTitle;
        @BindView(R.id.first_item_image)
        ImageView imageView;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this,itemView);
        }
    }

    public NewsBeans getdatas(){
        return beans;
    }
}

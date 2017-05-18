package com.example.mvp_demo.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mvp_demo.R;
import com.example.mvp_demo.mode.beans.DailyThemes;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yangfan on 2017/4/26.
 */

public class NavigationAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private List<DailyThemes.DailyTheme> mThemeList;
    private Context mContext;
    private int mSelectedPosition = 0;
    private OnItemClickListener mOnItemClickListener = null;


    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public NavigationAdapter(Context context) {
        this.mContext = context;
        mThemeList = new ArrayList<>();
    }

    public void setThemes(DailyThemes themes){
        mThemeList.clear();
        mThemeList = themes.getSubscribed();
        mThemeList.addAll(themes.getOthers());
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_drawer_item, parent, false);
        itemView.setOnClickListener(this);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ItemViewHolder viewHolder = (ItemViewHolder)holder;
        Resources resources = viewHolder.itemView.getContext().getResources();
        viewHolder.itemView.setTag(position);
        if (position == 0) {
            viewHolder.ivItemIcon.setVisibility(View.VISIBLE);
            viewHolder.ivItemIcon.setBackgroundResource(R.mipmap.menu_home);
            viewHolder.tvItemName.setText(resources.getString(R.string.navigation_first_item));
            viewHolder.ivItemCheck.setVisibility(View.GONE);
        } else {
            viewHolder.ivItemIcon.setBackgroundDrawable(null);
            viewHolder.ivItemIcon.setVisibility(View.GONE);
            viewHolder.tvItemName.setText(mThemeList.get(position-1).getName());
        }

        if (mSelectedPosition == position) {
            viewHolder.itemView.setBackgroundColor(resources.getColor(R.color.navigation_item_selected));
            viewHolder.tvItemName.setTextColor(resources.getColor(R.color.navdrawer_text_color_selected));
        } else  {
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                TypedValue outValue = new TypedValue();
                viewHolder.itemView.getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
                viewHolder.itemView.setBackgroundResource(outValue.resourceId);
            }*/
            TypedValue textcolor = new TypedValue();
            TypedValue backgroundColor = new TypedValue();
            Resources.Theme theme = viewHolder.itemView.getContext().getTheme();
            theme.resolveAttribute(R.attr.nav_drawer_text_color, textcolor, true);
            theme.resolveAttribute(R.attr.nav_drawer_background, backgroundColor, true);
            viewHolder.tvItemName.setTextColor(resources.getColor(textcolor.resourceId));
            viewHolder.itemView.setBackgroundColor(resources.getColor(backgroundColor.resourceId));
        }
    }

    @Override
    public int getItemCount() {
        return mThemeList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @Override
    public void onClick(View v) {

        int position = (int)v.getTag();

        if(mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(v, position);
        }

        selectPosition(position);

    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    public void selectPosition(int position) {
        int lastPosition = mSelectedPosition;

        if (lastPosition != -1 && lastPosition != position) {
            notifyItemChanged(lastPosition);
        }

        if (mSelectedPosition != position) {
            mSelectedPosition = position;
            notifyItemChanged(mSelectedPosition);
        }
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvItemName)
        TextView tvItemName;
        @BindView(R.id.ivItemIcon)
        ImageView ivItemIcon;
        @BindView(R.id.ivItemCheck)
        ImageView ivItemCheck;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

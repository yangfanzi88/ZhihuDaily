package com.aile.fanyangsz.zhihudaily.support.bannerImageLoader;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by fanyang.sz on 2016/11/4.
 */

public class GlideImageLoader implements ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Picasso.with(context).load(Uri.parse(path.toString())).into(imageView);
    }

}

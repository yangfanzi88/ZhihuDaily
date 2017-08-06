package com.example.mvp_demo.utils;

import android.content.Context;

/**
 * Created by yangfan on 2017/7/23.
 */

public class Utils {

    public static int dip2px(Context context, float dpVale) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpVale * scale + 0.5f);
    }
}

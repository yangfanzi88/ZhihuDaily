package com.aile.fanyangsz.zhihudaily.utils;

import android.content.Context;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by fanyang.sz on 2016/12/15.
 */

public class AndroidUtils {

    public static String string2Date(String daytime){
        Calendar c = Calendar.getInstance();
        String newsDay = null;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
            c.setTime(sdf.parse(daytime));

            newsDay = (c.get(Calendar.MONTH)+1) + "月"//从0计算
                    + c.get(Calendar.DAY_OF_MONTH) + "日";
        }catch (Exception e){
            e.printStackTrace();
        }

        switch (c.get(Calendar.DAY_OF_WEEK)){
            case Calendar.SUNDAY: newsDay+=" 星期日"; break;
            case Calendar.MONDAY: newsDay+=" 星期一";break;
            case Calendar.TUESDAY: newsDay+=" 星期二";break;
            case Calendar.WEDNESDAY: newsDay+=" 星期三";break;
            case Calendar.THURSDAY: newsDay+=" 星期四";break;
            case Calendar.FRIDAY: newsDay+=" 星期五";break;
            case Calendar.SATURDAY: newsDay+=" 星期六";break;
            default:break;
        }
        return newsDay;
    }


    public static int getDisplayMetricsWidth(Context mContext) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

        return wm.getDefaultDisplay().getWidth();
    }

    public static int getDisplayMetricsHeight(Context mContext) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }
}

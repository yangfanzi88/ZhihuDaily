package com.aile.fanyangsz.zhihudaily.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;

import com.aile.fanyangsz.zhihudaily.R;
import com.aile.fanyangsz.zhihudaily.support.beans.WelcomeBean;
import com.aile.fanyangsz.zhihudaily.support.netWork.HttpSDK;
import com.aile.fanyangsz.zhihudaily.ui.base.BaseActivity;
import com.aile.fanyangsz.zhihudaily.utils.AndroidUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

/**
 * Created by fanyang.sz on 2016/12/15.
 */

public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.welcome_image_view)
    ImageView welcomImage;
    @BindView(R.id.welcome_image_title)
    TextView title;

    String screenWidth, screenHeight;
    Handler mHandler;

    @Override
    protected int inflateContentView() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void init() {
        mHandler = new Handler(Looper.getMainLooper());
        screenWidth = String.valueOf(AndroidUtils.getDisplayMetricsWidth(this));
        screenHeight = String.valueOf(AndroidUtils.getDisplayMetricsHeight(this));
    }

    @Override
    protected void requestData() {
        HttpSDK.getInstance().getWelcome(screenWidth+"*"+screenHeight,onCallBack);
    }

    HttpSDK.onWelcomeCallBack onCallBack = new HttpSDK.onWelcomeCallBack() {
        @Override
        public void onSuccess(WelcomeBean data) {
            Picasso.with(getBaseContext()).load(data.getImg()).into(welcomImage);
            title.setText(data.getText());
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onPostExecute();
                }
            },2500);

        }

        @Override
        public void onError(String s) {

        }
    };

    protected void onPostExecute() {
        // ... ...
        Intent intent = new Intent();
        intent.setClass(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        //两个参数分别表示进入的动画,退出的动画
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}

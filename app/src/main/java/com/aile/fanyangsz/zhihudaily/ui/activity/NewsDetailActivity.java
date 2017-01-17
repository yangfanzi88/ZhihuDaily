package com.aile.fanyangsz.zhihudaily.ui.activity;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.aile.fanyangsz.zhihudaily.R;
import com.aile.fanyangsz.zhihudaily.support.beans.NewsDetailBean;
import com.aile.fanyangsz.zhihudaily.support.netWork.HttpSDK;
import com.aile.fanyangsz.zhihudaily.ui.base.BaseActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

/**
 * Created by fanyang.sz on 2016/11/9.
 */

public class NewsDetailActivity extends BaseActivity {

    @BindView(R.id.iv_header_img)
    ImageView headerImage;
    @BindView(R.id.tv_img_source)
    TextView sourceText;
    @BindView(R.id.tv_header_title)
    TextView titleText;
    @BindView(R.id.wb_story_content)
    WebView contentWeb;


    Intent intent;
    int id;

    @Override
    protected int inflateContentView() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void init() {

        intent = getIntent();
        id = intent.getIntExtra("newsId",0);

        /*contentWeb.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });*/
    }

    @Override
    protected void requestData() {
        HttpSDK.getInstance().getNewsDetail(id,onDetailCallBack);
    }

    HttpSDK.onDetailCallBack onDetailCallBack = new HttpSDK.onDetailCallBack() {
        @Override
        public void onSuccess(NewsDetailBean detailBean) {
            titleText.setText(detailBean.getTitle());
            sourceText.setText(detailBean.getImage_source());
            Picasso.with(getBaseContext()).load(detailBean.getImage()).into(headerImage);
            String htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"zhihu.css\" />" + detailBean.getBody();
            contentWeb.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "utf-8", null);
        }

        @Override
        public void onError(String s) {

        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.hold, android.R.anim.fade_out);
    }
}

package com.aile.fanyangsz.zhihudaily.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.aile.fanyangsz.zhihudaily.R;
import com.aile.fanyangsz.zhihudaily.ui.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by fanyang.sz on 2016/11/28.
 */

public class PlayVideoActivity extends BaseActivity {

    @BindView(R.id.video_play)
    VideoView mVideoView;
    @BindView(R.id.video_title)
    TextView mVideoTitle;
    @BindView(R.id.video_progress)
    ProgressBar mVideoPro;

    Handler mHandler;

    public static void actionStart(Context context, String video, String title) {
        Intent intent = new Intent(context, PlayVideoActivity.class);
        intent.putExtra("video", video);
        intent.putExtra("title", title);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected int inflateContentView() {
        return R.layout.activity_video_player;
    }

    @Override
    protected void init() {
        // 定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        // 获得当前窗体对象
        Window window = PlayVideoActivity.this.getWindow();
        // 设置当前窗体为全屏显示
        window.setFlags(flag, flag);

        initData();
        mHandler = new Handler(this.getMainLooper());
    }

    @Override
    protected void requestData() {

    }

    private void initData() {
        String video = getIntent().getStringExtra("video");// 视频播放地址
        String title = getIntent().getStringExtra("title");// 视频标题
        Uri uri = Uri.parse(video);

        mVideoTitle.setText(title);
        mVideoView.setVideoURI(uri);
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.setOnCompletionListener(new MyPlayerOnCompletionListener());
        mVideoView.setOnPreparedListener(new MyPlayerOnPrepareListener());
        mVideoView.setOnTouchListener(new MyPlayerOnTouchListener());
        mVideoView.start();
    }



    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.suspend();// 将videoView所占用的资料释放掉
        }
    }

    class MyPlayerOnPrepareListener implements MediaPlayer.OnPreparedListener {
        @Override
        public void onPrepared(MediaPlayer mp) {
            mVideoPro.setVisibility(View.GONE);
            mVideoTitle.setVisibility(View.GONE);
        }
    }
    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText(PlayVideoActivity.this, "播放完成,没有更多视频了", Toast.LENGTH_SHORT).show();
        }
    }
    class MyPlayerOnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (mVideoTitle.getVisibility() != View.VISIBLE) {
                mVideoTitle.setVisibility(View.VISIBLE);
                mHandler.postDelayed(setTitleGone, 2000);
            } else {
                mVideoTitle.setVisibility(View.GONE);
                mHandler.removeCallbacks(setTitleGone);
            }
            return false;
        }
    }
    Runnable setTitleGone = new Runnable() {
        @Override
        public void run() {
            if (mVideoTitle.getVisibility() == View.VISIBLE) mVideoTitle.setVisibility(View.GONE);
        }
    };
}

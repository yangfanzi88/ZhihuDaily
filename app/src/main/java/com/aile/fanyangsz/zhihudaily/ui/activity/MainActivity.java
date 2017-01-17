package com.aile.fanyangsz.zhihudaily.ui.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.aile.fanyangsz.zhihudaily.R;
import com.aile.fanyangsz.zhihudaily.ui.fragment.FirstFragment;
import com.aile.fanyangsz.zhihudaily.ui.fragment.SecondFragment;
import com.aile.fanyangsz.zhihudaily.ui.fragment.ThirdFragment;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    FragmentManager fragmentManager;
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        fragmentManager = getFragmentManager();

        initView();
    }

    private void initView(){
        TypedValue typedValue = new TypedValue();
        this.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_CLASSIC);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .addItem(new BottomNavigationItem(R.mipmap.ic_news_24dp,R.string.first_fragment)).setActiveColor(typedValue.resourceId)
                .addItem(new BottomNavigationItem(R.mipmap.ic_photo_24dp,R.string.second_fragment).setActiveColor(typedValue.resourceId))
                .addItem(new BottomNavigationItem(R.mipmap.ic_video_24dp,R.string.third_fragment).setActiveColor(typedValue.resourceId))
                .addItem(new BottomNavigationItem(R.mipmap.ic_about_me,R.string.fourth_fragment).setActiveColor(typedValue.resourceId))
                .initialise();

        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                showFragment(position);
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });

        showFragment(0);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    private void showFragment(int position){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideAllFragment(transaction);
        switch (position){
            case 0:
                if (firstFragment != null) {
                    transaction.show(firstFragment);
                } else {
                    firstFragment = new FirstFragment();
                    transaction.add(R.id.fragment_frame_layout, firstFragment);
                }
                break;
            case 1:
                if(secondFragment != null){
                    transaction.show(secondFragment);
                }else {
                    secondFragment = new SecondFragment();
                    transaction.add(R.id.fragment_frame_layout, secondFragment);
                }
                break;
            case 2:
                if(thirdFragment != null){
                    transaction.show(thirdFragment);
                }else{
                    thirdFragment = new ThirdFragment();
                    transaction.add(R.id.fragment_frame_layout, thirdFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideAllFragment(FragmentTransaction ft) {
        if (firstFragment != null) {
            ft.hide(firstFragment);
        }
        if (secondFragment != null) {
            ft.hide(secondFragment);
        }
        if (thirdFragment != null) {
            ft.hide(thirdFragment);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

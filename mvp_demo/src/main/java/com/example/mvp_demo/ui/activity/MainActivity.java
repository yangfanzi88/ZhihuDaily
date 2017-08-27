package com.example.mvp_demo.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.mvp_demo.R;
import com.example.mvp_demo.ui.fragment.BaseFragment;
import com.example.mvp_demo.ui.fragment.NavigationDrawerCallbacks;
import com.example.mvp_demo.ui.fragment.NavigationFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements NavigationDrawerCallbacks {

    public static final String STATE_SELECT_POSITION = "state_select_position";
    private static final String STATE_SELECTED_POSITION = "state_selected_positioin";

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.content_main)
    FrameLayout mainContent;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigation_drawer)
    FrameLayout navigationView;


    NavigationFragment mNavigationFragment;
    ActionBarDrawerToggle toggle;
    public CharSequence mTitle = "";
    private int mLastPosition = 0;
    private long mLastExitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
    }

    @Override
    protected int inflateContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setUpDrawer();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        mNavigationFragment = new NavigationFragment();
        transaction.add(R.id.navigation_drawer, mNavigationFragment);
        transaction.commit();

        if(savedInstanceState == null){
            mTitle = getString(R.string.navigation_first_item);
            setActionBar();
            showFragment(0);
        }else {
            int position = savedInstanceState.getInt(STATE_SELECTED_POSITION);
//            mLastPosition = position;
            mTitle = mNavigationFragment.getTitle(position);
            setActionBar();
            mNavigationFragment.selectItem(position);
        }

    }

    @Override
    protected void initInjector() {
    }

    @Override
    protected void updateUI(boolean isRefresh) {

    }

    private void setUpDrawer() {
        mTitle = getTitle();
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void setActionBar() {
//        ActionBar actionBar = getSupportActionBar();
        toolbar.setTitle(mTitle);
    }

    private void showFragment(int position){
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        replaceFragment(R.id.content_main, BaseFragment.newInstance(position),mTitle.toString());
//        transaction.commit();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        mTitle = mNavigationFragment.getTitle(position);
        setActionBar();

        showFragment(mNavigationFragment.getThemeId(position));
    }

    @Override
    public void onBackPressed() {
        //当点击返回键的时候，获取fragment stack里面fragment数量
        final int stackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if(stackEntryCount == 1){
            //如果只剩下一个fragment，那么按两次，真正的退出
            if(System.currentTimeMillis() - mLastExitTime > 2000){
                Toast.makeText(this, R.string.app_exit_toast, Toast.LENGTH_SHORT).show();
                mLastExitTime = System.currentTimeMillis();
            }else {
                finish();
            }
        }else {
            //获取上一个fragment的name，刷新主页
            mTitle = getSupportFragmentManager().getBackStackEntryAt(stackEntryCount-2).getName();
            setActionBar();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(navigationView);
        }

        return super.onOptionsItemSelected(item);
    }


    public void closeDrawer() {
        drawerLayout.closeDrawer(navigationView);
    }

}

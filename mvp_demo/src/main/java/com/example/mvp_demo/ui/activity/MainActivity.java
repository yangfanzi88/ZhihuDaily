package com.example.mvp_demo.ui.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.mvp_demo.DailyApplication;
import com.example.mvp_demo.R;
import com.example.mvp_demo.injector.component.ApplicationComponent;
import com.example.mvp_demo.ui.fragment.NavigationFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    public static final String STATE_SELECT_POSITION = "state_select_position";

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.content_main)
    RelativeLayout mainContent;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigation_drawer)
    FrameLayout navigationView;


    NavigationFragment mNavigationFragment;
    ActionBarDrawerToggle toggle;
    CharSequence mTitle = "";
    private int mLastPosition = 0;

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
    protected void initViews() {
        setUpDrawer();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        mNavigationFragment = new NavigationFragment();
        transaction.add(R.id.navigation_drawer, mNavigationFragment);
        transaction.commit();

        mTitle = getString(R.string.navigation_first_item);
        setActionBar();
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

    private void setActionBar() {
//        ActionBar actionBar = getSupportActionBar();
        toolbar.setTitle(mTitle);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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

    public ApplicationComponent getApplicationComponent() {
        ApplicationComponent applicationComponent = DailyApplication.getAppComponent();
        return applicationComponent;
    }
}

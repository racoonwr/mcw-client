package com.mcw.demo.ui.activity;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.mcw.R;
import com.mcw.demo.DemoApplication;
import com.mcw.demo.config.AppConfig;
import com.mcw.demo.config.TabEnum;
import com.mcw.demo.ui.fragment.NavigationDrawerFragment;
import com.mcw.demo.ui.fragment.OnTabReselectListener;
import com.mcw.demo.ui.widght.CustomFragmentTabHost;
import com.mcw.demo.util.DoubleClickExitHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends AppCompatActivity implements
        NavigationDrawerFragment.NavigationDrawerCallbacks,
        TabHost.OnTabChangeListener, ActivityInterface, View.OnClickListener,
        View.OnTouchListener {

    private DoubleClickExitHelper mDoubleClickExit;

    /**
     * Fragment managing the behaviors, interactions and presentation of the
     * navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @BindView(android.R.id.tabhost)
    public CustomFragmentTabHost mTabHost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppBaseTheme_Light);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        mDoubleClickExit = new DoubleClickExitHelper(this);
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
                .findFragmentById(R.id.navigation_drawer);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        if (android.os.Build.VERSION.SDK_INT > 10) {
            mTabHost.getTabWidget().setShowDividers(0);
        }

        initTabs();

        mTabHost.setCurrentTab(0);
        mTabHost.setOnTabChangedListener(this);
    }

    @Override
    public void initData() {

    }

    private void initTabs() {
        TabEnum[] tabs = TabEnum.values();
        final int size = tabs.length;

        if (size > 0) {
            setTitle(tabs[0].getResName());
        }

        for (int i = 0; i < size; i++) {
            TabEnum mainTab = tabs[i];
            TabHost.TabSpec tab = mTabHost.newTabSpec(getString(mainTab.getResName()));
            View indicator = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.tab_indicator, null);
            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
            Drawable drawable = this.getResources().getDrawable(
                    mainTab.getResIcon());
            title.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            title.setText(getString(mainTab.getResName()));
            tab.setIndicator(indicator);
            tab.setContent(new TabHost.TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    return new View(MainActivity.this);
                }
            });

            mTabHost.addTab(tab, mainTab.getClz(), null);

            mTabHost.getTabWidget().getChildAt(i).setOnTouchListener(this);
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onTabChanged(String s) {
        final int size = mTabHost.getTabWidget().getTabCount();
        for (int i = 0; i < size; i++) {
            View v = mTabHost.getTabWidget().getChildAt(i);
            if (i == mTabHost.getCurrentTab()) {
                v.setSelected(true);
                setTitle(mTabHost.getCurrentTabTag());//设置标题
            } else {
                v.setSelected(false);
            }
        }
        supportInvalidateOptionsMenu();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouchEvent(event);
        boolean consumed = false;
        // use getTabHost().getCurrentTabView to decide if the current tab is
        // touched again
        if (event.getAction() == MotionEvent.ACTION_DOWN
                && v.equals(mTabHost.getCurrentTabView())) {
            // use getTabHost().getCurrentView() to get a handle to the view
            // which is displayed in the tab - and to get this views context
            Fragment currentFragment = getCurrentFragment();
            if (currentFragment != null
                    && currentFragment instanceof OnTabReselectListener) {
                OnTabReselectListener listener = (OnTabReselectListener) currentFragment;
                listener.onTabReselect();
                consumed = true;
            }
        }
        return consumed;
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentByTag(
                mTabHost.getCurrentTabTag());
    }

    /**
     * 监听返回--是否退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 是否退出应用
            if (DemoApplication.get(AppConfig.KEY_DOUBLE_CLICK_EXIT, true)) {
                return mDoubleClickExit.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

package com.fg.storage.ui.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.fg.storage.R;
import com.fg.storage.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private TabLayout mTablayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initViewPager();
    }

    private void initViewPager() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            private String[] mTitles = new String[]{"首页", "信息分类", "信息录入"};

            @Override
            public Fragment getItem(int position) {

                if (position == 1) {
                    return new ProductCategoryFragment();
                } else if (position == 2) {
                    return new SettingFragment();
                }
                return new HomeFragment();
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        });
        mTablayout.setupWithViewPager(mViewPager);
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("仓位物料管理系统");
        setSupportActionBar(toolbar);
        mTablayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
    }

}

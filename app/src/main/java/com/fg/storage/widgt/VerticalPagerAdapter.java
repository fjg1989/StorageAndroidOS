package com.fg.storage.widgt;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.TabView;

public class VerticalPagerAdapter extends FragmentPagerAdapter implements TabAdapter {
    private List<String> titles;
    private List<Fragment> fragments = new ArrayList<>();

    public VerticalPagerAdapter(FragmentManager fm, List<String> titles, List<Fragment> fragments) {
        super(fm);
        this.titles = titles;
        this.fragments = fragments;
    }

    public void setNewData(List<String> titles, List<Fragment> fragments) {
        this.titles.clear();
        this.fragments.clear();
        this.titles = titles;
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public TabView.TabBadge getBadge(int position) {

        return null;
    }

    @Override
    public TabView.TabIcon getIcon(int position) {
        return null;
    }

    @Override
    public TabView.TabTitle getTitle(int position) {

        return new TabView.TabTitle.Builder()
                .setContent(titles.get(position))
                .setTextSize(12)
                .setTextColor(Color.WHITE, 0xBBFFFFFF)
                .build();
    }

    @Override
    public int getBackground(int position) {
        return 0;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    public static class Holder {
        public final List<Fragment> fragments = new ArrayList<>();
        private FragmentManager manager;
        private VerticalPagerAdapter verticalPagerAdapter;

        public Holder(FragmentManager manager) {
            this.manager = manager;
        }

        public Holder add(Fragment f) {
            fragments.add(f);
            return this;
        }

        public VerticalPagerAdapter set(List<String> titles) {

            verticalPagerAdapter = new VerticalPagerAdapter(manager, titles, fragments);
            return verticalPagerAdapter;
        }

    }
}
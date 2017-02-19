package com.fg.storage.ui.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fg.storage.R;
import com.fg.storage.dao.ProductCateDao;
import com.fg.storage.model.ProductCate;
import com.fg.storage.ui.product.ContentFragment;
import com.fg.storage.widgt.VerticalPagerAdapter;
import com.fg.storage.widgt.VerticalViewPager;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.verticaltablayout.VerticalTabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductCategoryFragment extends Fragment {

    private VerticalViewPager mViewPager;
    private VerticalTabLayout mTablayout;
    private VerticalPagerAdapter.Holder holder;
    private List<ProductCate> products = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private ProductCateDao productDao;
    //控件是否已经初始化
    private boolean isCreateView = false;
    private int count;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pro_category, null, false);
        productDao = new ProductCateDao(getActivity());
        initView(view);
        initData();
        initViewPager();
        return view;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isCreateView) {
            onUserVisible();
        }
    }

    private void onUserVisible() {
        products = productDao.queryAllProductCate();
        if (count == 0 || (products.size() == count)) return;
        titles.clear();
        holder.fragments.clear();
        initData();
        initViewPager();
    }

    private void initData() {
        products.clear();
        holder = new VerticalPagerAdapter.Holder(getChildFragmentManager());
        products = productDao.queryAllProductCate();
        count = products.size();
        if (count == 0) return;
        for (int i = 0; i < products.size(); i++) {
            titles.add(products.get(i).getProductName());
            holder.add(ContentFragment.newInstance(products.get(i).getProductName(), i));
        }

    }

    private void initView(View view) {
        mTablayout = (VerticalTabLayout) view.findViewById(R.id.tablayout);
        mViewPager = (VerticalViewPager) view.findViewById(R.id.vertical_viewpager);
        isCreateView = true;//标记是否
    }

    private void initViewPager() {
        mViewPager.setAdapter(holder.set(titles));
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mTablayout.setupWithViewPager(mViewPager);

    }


}

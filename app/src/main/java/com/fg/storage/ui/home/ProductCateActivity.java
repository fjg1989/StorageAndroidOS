package com.fg.storage.ui.home;

import android.os.Bundle;
import android.view.View;

import com.fg.storage.R;
import com.fg.storage.dao.ProductCateDao;
import com.fg.storage.model.ProductCate;
import com.fg.storage.ui.base.BaseActivity;
import com.fg.storage.ui.product.ContentFragment;
import com.fg.storage.widgt.VerticalPagerAdapter;
import com.fg.storage.widgt.VerticalViewPager;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.verticaltablayout.VerticalTabLayout;

public class ProductCateActivity extends BaseActivity {
    private VerticalViewPager mViewPager;
    private VerticalTabLayout mTablayout;
    private VerticalPagerAdapter.Holder holder;
    private List<ProductCate> products = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private ProductCateDao productDao;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_cate);
        productDao = new ProductCateDao(this);
        initView();
        initData();
        initViewPager();
    }

    private void initData() {
        String queryKey = getIntent().getStringExtra("query");
        products.clear();
        holder = new VerticalPagerAdapter.Holder(getSupportFragmentManager());
        products = productDao.queryAllProductCateByName("productName",queryKey);
        count = products.size();
        if (count == 0) return;
        for (int i = 0; i < products.size(); i++) {
            titles.add(products.get(i).getProductName());
            holder.add(ContentFragment.newInstance(products.get(i).getProductName(), i));
        }

    }

    private void initView() {
        mTablayout = (VerticalTabLayout) findViewById(R.id.tablayout);
        mViewPager = (VerticalViewPager) findViewById(R.id.vertical_viewpager);
    }

    private void initViewPager() {
        mViewPager.setAdapter(holder.set(titles));
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mTablayout.setupWithViewPager(mViewPager);

    }
}

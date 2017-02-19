package com.fg.storage.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fg.storage.R;
import com.fg.storage.adapter.ProductCateRefreshAdapter;
import com.fg.storage.dao.ProductCateDao;
import com.fg.storage.model.ProductCate;
import com.fg.storage.ui.base.BaseActivity;
import com.fg.storage.util.Constant;
import com.othershe.baseadapter.ViewHolder;
import com.othershe.baseadapter.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends BaseActivity {

    private ProductCateRefreshAdapter mAdapter;

    private RecyclerView mRecyclerView;

    private boolean isFailed = true;
    private ProductCateDao mRealmHelper;
    private List<ProductCate> mStoreCells = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_layout);
        initData();
        initView();
    }

    private void initData() {
        mRealmHelper = new ProductCateDao(this);
        mStoreCells = mRealmHelper.queryAllProductCate();

    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setToolbar(toolbar, "物料列表");

        Button addButton = (Button) findViewById(R.id.add_cell);
        addButton.setText("增加物料");
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ProductListActivity.this, UpdateProductActivity.class).putExtra("addCell", 1), 101);
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        //初始化adapter
        mAdapter = new ProductCateRefreshAdapter(this, mStoreCells, true);

        //初始化EmptyView
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_layout, (ViewGroup) mRecyclerView.getParent(), false);
        mAdapter.setEmptyView(emptyView);
//        mAdapter.setLoadEndView(R.layout.load_end_layout);

        //初始化 开始加载更多的loading View
//        mAdapter.setLoadingView(R.layout.load_loading_layout);
//        mAdapter.setLoadFailedView(R.layout.load_failed_layout);

        //设置加载更多触发的事件监听
//        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(boolean isReload) {
//              loadMore();
//            }
//        });

        //设置item点击事件监听
        mAdapter.setOnItemClickListener(new OnItemClickListener<ProductCate>() {

            @Override
            public void onItemClick(ViewHolder viewHolder, ProductCate data, int position) {
                if (getIntent() != null && (getIntent().getIntExtra("proname", 0) == Constant.PRONAME)) {
                    setResult(RESULT_OK, new Intent().putExtra("proname", data.getProductName()));
                    finish();
                }
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && (requestCode == 100 || requestCode == 101)) {
            mStoreCells.clear();
            List<ProductCate> mCells = mRealmHelper.queryAllProductCate();
            mStoreCells.addAll(mCells);
            mAdapter.notifyDataSetChanged();

        }
    }
}

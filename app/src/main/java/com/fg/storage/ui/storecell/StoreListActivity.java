package com.fg.storage.ui.storecell;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fg.storage.R;
import com.fg.storage.adapter.StoreRefreshAdapter;
import com.fg.storage.dao.StoreCellDao;
import com.fg.storage.model.StoreCell;
import com.fg.storage.ui.base.BaseActivity;
import com.fg.storage.util.Constant;
import com.othershe.baseadapter.ViewHolder;
import com.othershe.baseadapter.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class StoreListActivity extends BaseActivity {

    private StoreRefreshAdapter mAdapter;

    private RecyclerView mRecyclerView;

    private boolean isFailed = true;
    private StoreCellDao mRealmHelper;
    private List<StoreCell> mStoreCells = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_layout);
        initData();
        initView();
    }

    private void initData() {
        mRealmHelper = new StoreCellDao(this);
        mStoreCells = mRealmHelper.queryAllStoreCell();

    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setToolbar(toolbar, "库位列表");

        Button addButton = (Button) findViewById(R.id.add_cell);
        addButton.setText("增加库位");
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(StoreListActivity.this, UpdateStoreActivity.class).putExtra("addCell", 1), 101);
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        //初始化adapter
        mAdapter = new StoreRefreshAdapter(this, mStoreCells, true);

        //初始化EmptyView
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_layout, (ViewGroup) mRecyclerView.getParent(), false);
        mAdapter.setEmptyView(emptyView);

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
        mAdapter.setOnItemClickListener(new OnItemClickListener<StoreCell>() {

            @Override
            public void onItemClick(ViewHolder viewHolder, StoreCell data, int position) {
                if (getIntent() != null && (getIntent().getIntExtra("store", 0) == Constant.STORE)) {
                    setResult(RESULT_OK, new Intent().putExtra("storeid", data.getStoreId()).putExtra("storename", data.getStoreName()));
                    finish();
                } else {
                    mAdapter.showDialog(data, position);
                }

            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mAdapter);

    }


    private void loadMore() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (mAdapter.getItemCount() > 15 && isFailed) {
                    isFailed = false;
                    //加载失败，更新footer view提示
                    mAdapter.setLoadFailedView(R.layout.load_failed_layout);
                } else if (mAdapter.getItemCount() > 17) {
                    //加载完成，更新footer view提示
                    mAdapter.setLoadEndView(R.layout.load_end_layout);
                } else {
                    final List<StoreCell> data = new ArrayList<>();
                    for (int i = 0; i < 12; i++) {
//                        data.add("item--" + (mAdapter.getItemCount() + i - 1));
                    }
                    //刷新数据
                    mAdapter.setLoadMoreData(data);
                }
            }
        }, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && (requestCode == 100 || requestCode == 101)) {
            mStoreCells.clear();
            List<StoreCell> mCells = mRealmHelper.queryAllStoreCell();
            mStoreCells.addAll(mCells);
            mAdapter.notifyDataSetChanged();

        }
    }
}

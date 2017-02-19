package com.fg.storage.ui.batch;

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
import com.fg.storage.adapter.BatchRefreshAdapter;
import com.fg.storage.dao.BatchDao;
import com.fg.storage.model.Batch;
import com.fg.storage.ui.base.BaseActivity;
import com.fg.storage.util.Constant;
import com.othershe.baseadapter.ViewHolder;
import com.othershe.baseadapter.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class BatchListActivity extends BaseActivity {

    private BatchRefreshAdapter mAdapter;

    private RecyclerView mRecyclerView;

    private boolean isFailed = true;
    private BatchDao mRealmHelper;
    private List<Batch> mStoreCells = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_layout);
        initData();
        initView();
    }

    private void initData() {
        mRealmHelper = new BatchDao(this);
        mStoreCells = mRealmHelper.queryAllSupply();

    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setToolbar(toolbar, "批次列表");

        Button addButton = (Button) findViewById(R.id.add_cell);
        addButton.setText("增加批次");
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(BatchListActivity.this, UpdateBatchActivity.class).putExtra("addCell", 1), 101);
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        //初始化adapter
        mAdapter = new BatchRefreshAdapter(this, mStoreCells, true);

        //初始化EmptyView
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_layout, (ViewGroup) mRecyclerView.getParent(), false);
        mAdapter.setEmptyView(emptyView);

        //设置item点击事件监听
        mAdapter.setOnItemClickListener(new OnItemClickListener<Batch>() {

            @Override
            public void onItemClick(ViewHolder viewHolder, Batch data, int position) {
                if (getIntent() != null && (getIntent().getIntExtra("batch", 0) == Constant.BATCH)) {
                    setResult(RESULT_OK, new Intent().putExtra("batch", data.getBatchName()));
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
            List<Batch> mCells = mRealmHelper.queryAllSupply();
            mStoreCells.addAll(mCells);
            mAdapter.notifyDataSetChanged();

        }
    }
}

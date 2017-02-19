package com.fg.storage.ui.suppliers;

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
import com.fg.storage.adapter.SupplyRefreshAdapter;
import com.fg.storage.dao.SupplyDao;
import com.fg.storage.model.Supply;
import com.fg.storage.ui.base.BaseActivity;
import com.fg.storage.util.Constant;
import com.othershe.baseadapter.ViewHolder;
import com.othershe.baseadapter.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class SupplyListActivity extends BaseActivity {

    private SupplyRefreshAdapter mAdapter;

    private RecyclerView mRecyclerView;

    private boolean isFailed = true;
    private SupplyDao mRealmHelper;
    private List<Supply> mStoreCells = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_layout);
        initData();
        initView();
    }

    private void initData() {
        mRealmHelper = new SupplyDao(this);
        mStoreCells = mRealmHelper.queryAllSupply();

    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setToolbar(toolbar, "供应商列表");

        Button addButton = (Button) findViewById(R.id.add_cell);
        addButton.setText("增加供应商");
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(SupplyListActivity.this, UpdateSupplyActivity.class).putExtra("addCell", 1), 101);
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        //初始化adapter
        mAdapter = new SupplyRefreshAdapter(this, mStoreCells, true);

        //初始化EmptyView
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_layout, (ViewGroup) mRecyclerView.getParent(), false);
        mAdapter.setEmptyView(emptyView);

        //设置item点击事件监听
        mAdapter.setOnItemClickListener(new OnItemClickListener<Supply>() {

            @Override
            public void onItemClick(ViewHolder viewHolder, Supply data, int position) {
                if (getIntent() != null && (getIntent().getIntExtra("supply", 0) == Constant.SUPPLY))
                    setResult(RESULT_OK, new Intent().putExtra("supply", data.getSupplyName()));
                finish();
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
            List<Supply> mCells = mRealmHelper.queryAllSupply();
            mStoreCells.addAll(mCells);
            mAdapter.notifyDataSetChanged();

        }
    }
}

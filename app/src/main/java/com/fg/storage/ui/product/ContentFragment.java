package com.fg.storage.ui.product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fg.storage.R;
import com.fg.storage.adapter.ProductRefreshAdapter;
import com.fg.storage.dao.ProductDao;
import com.fg.storage.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ContentFragment extends Fragment {

    private ProductRefreshAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ProductDao mRealmHelper;
    private List<Product> mStoreCells = new ArrayList<>();
    private TextView all_count;
    private long count;

    public ContentFragment() {
    }

    public static Fragment newInstance(String pName, int position) {
        Bundle args = new Bundle();
        args.putString("pName", pName);
        args.putInt("position", position);
        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        initData();
        initRecyclerView(view);
        return view;
    }

    private void initData() {
        mRealmHelper = new ProductDao(getActivity());
        mStoreCells = mRealmHelper.queryAllProduct(getProdName());
        count = mRealmHelper.getProductCountByName(getProdName());
    }


    private void initRecyclerView(View view) {
        all_count = (TextView) view.findViewById(R.id.all_count);
        all_count.setText("总数：" + count);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        //初始化adapter
        mAdapter = new ProductRefreshAdapter(getActivity(), getProdName(), mStoreCells, false, new ProductRefreshAdapter.NotifyAllCount() {
            @Override
            public void notify(String cou) {
                all_count.setText("总数：" + cou);
            }
        });

        //初始化EmptyView
        View emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_layout, (ViewGroup) mRecyclerView.getParent(), false);
        mAdapter.setEmptyView(emptyView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mAdapter);
    }

    public String getProdName() {
        return getArguments().getString("pName");
    }

    public int getPosition() {
        return getArguments().getInt("position");
    }
}

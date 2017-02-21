package com.fg.storage.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.fg.storage.R;
import com.fg.storage.dao.StoreCellDao;
import com.fg.storage.model.StoreCell;
import com.fg.storage.ui.storecell.UpdateStoreActivity;
import com.othershe.baseadapter.ViewHolder;

import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/29 15:40
 */
public class StoreRefreshAdapter extends CommonBaseAdapter<StoreCell> {
    private StoreCellDao mStoreCellDao;

    public StoreRefreshAdapter(Context context, List<StoreCell> datas, boolean isLoadMore) {
        super(context, datas, isLoadMore);
        mStoreCellDao = new StoreCellDao(context);
    }

    @Override
    protected void convert(ViewHolder holder, final StoreCell data, final int position) {
        holder.setText(R.id.item_title, data.getStoreName());
        holder.setOnClickListener(R.id.item_btn_del, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStoreCellDao.deleteStoreCell(data.getStoreId());
                mDatas.remove(position);
                Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                notifyItemRemoved(position);
            }
        });
        holder.setOnClickListener(R.id.item_btn_edit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UpdateStoreActivity.class);
                intent.putExtra("storeId", data.getStoreId()).putExtra("old",data.getStoreName());
                ((Activity) mContext).startActivityForResult(intent, 100);
            }

        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_layout;
    }
}

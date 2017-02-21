package com.fg.storage.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.fg.storage.R;
import com.fg.storage.dao.SupplyDao;
import com.fg.storage.model.Supply;
import com.fg.storage.ui.suppliers.UpdateSupplyActivity;
import com.othershe.baseadapter.ViewHolder;

import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/29 15:40
 */
public class SupplyRefreshAdapter extends CommonBaseAdapter<Supply> {
    private SupplyDao mStoreCellDao;

    public SupplyRefreshAdapter(Context context, List<Supply> datas, boolean isLoadMore) {
        super(context, datas, isLoadMore);
        mStoreCellDao = new SupplyDao(context);
    }

    @Override
    protected void convert(ViewHolder holder, final Supply data, final int position) {
        holder.setText(R.id.item_title, data.getSupplyName());
        holder.setOnClickListener(R.id.item_btn_del, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStoreCellDao.deleteSupply(data.getSupplyId());
                mDatas.remove(position);
                Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                notifyItemRemoved(position);
            }
        });
        holder.setOnClickListener(R.id.item_btn_edit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UpdateSupplyActivity.class);
                intent.putExtra("supplyId", data.getSupplyId()).putExtra("old",data.getSupplyName());
                ((Activity) mContext).startActivityForResult(intent, 100);
            }

        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_layout;
    }
}

package com.fg.storage.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.fg.storage.R;
import com.fg.storage.dao.BatchDao;
import com.fg.storage.model.Batch;
import com.fg.storage.ui.batch.UpdateBatchActivity;
import com.othershe.baseadapter.ViewHolder;

import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/29 15:40
 */
public class BatchRefreshAdapter extends CommonBaseAdapter<Batch> {
    private BatchDao mStoreCellDao;

    public BatchRefreshAdapter(Context context, List<Batch> datas, boolean isLoadMore) {
        super(context, datas, isLoadMore);
        mStoreCellDao = new BatchDao(context);
    }

    @Override
    protected void convert(ViewHolder holder, final Batch data, final int position) {
        holder.setText(R.id.item_title, data.getBatchName());
        holder.setOnClickListener(R.id.item_btn_del, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStoreCellDao.deleteBatch(data.getBatchId());
                mDatas.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
            }
        });
        holder.setOnClickListener(R.id.item_btn_edit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UpdateBatchActivity.class);
                intent.putExtra("batchId", data.getBatchId()).putExtra("old",data.getBatchName());
                ((Activity) mContext).startActivityForResult(intent, 100);
            }

        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_layout;
    }
}

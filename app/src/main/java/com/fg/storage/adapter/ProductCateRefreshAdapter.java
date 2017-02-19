package com.fg.storage.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.fg.storage.R;
import com.fg.storage.dao.ProductCateDao;
import com.fg.storage.model.ProductCate;
import com.fg.storage.ui.product.UpdateProductActivity;
import com.othershe.baseadapter.ViewHolder;

import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/29 15:40
 */
public class ProductCateRefreshAdapter extends CommonBaseAdapter<ProductCate> {
    private ProductCateDao mStoreCellDao;

    public ProductCateRefreshAdapter(Context context, List<ProductCate> datas, boolean isLoadMore) {
        super(context, datas, isLoadMore);
        mStoreCellDao = new ProductCateDao(context);
    }

    @Override
    protected void convert(ViewHolder holder, final ProductCate data, final int position) {
        holder.setText(R.id.item_title, data.getProductName());
        holder.setOnClickListener(R.id.item_btn_del, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStoreCellDao.deleteProductCate(data.getpId());
                mDatas.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
            }
        });
        holder.setOnClickListener(R.id.item_btn_edit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UpdateProductActivity.class);
                intent.putExtra("pId", data.getpId());
                ((Activity) mContext).startActivityForResult(intent, 100);
            }

        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.product_item_layout;
    }
}

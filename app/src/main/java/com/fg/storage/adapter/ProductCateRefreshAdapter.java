package com.fg.storage.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fg.storage.R;
import com.fg.storage.dao.ProductCateDao;
import com.fg.storage.dao.ProductDao;
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
    private ProductDao mProducDao;

    public ProductCateRefreshAdapter(Context context, List<ProductCate> datas, boolean isLoadMore) {
        super(context, datas, isLoadMore);
        mStoreCellDao = new ProductCateDao(context);
        mProducDao = new ProductDao(context);
    }

    @Override
    protected void convert(ViewHolder holder, final ProductCate data, final int position) {
        holder.setText(R.id.item_title, data.getProductName()+"");
        holder.setText(R.id.item_count, "总数：" + mProducDao.getProductCountByName(data.getProductName()));
        holder.setOnClickListener(R.id.item_btn_del, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSure(data, position);
            }
        });
        holder.setOnClickListener(R.id.item_btn_edit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UpdateProductActivity.class);
                intent.putExtra("pId", data.getpId()).putExtra("old", data.getProductName());
                ((Activity) mContext).startActivityForResult(intent, 100);
            }

        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.product_item_layout;
    }

    public void showSure(final ProductCate data, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = ((Activity) mContext).getLayoutInflater().inflate(R.layout.alert_layout, null);
        final AlertDialog alertDialog = builder.setTitle("操作")
                .setView(view)
                .create();
        final EditText editText = (EditText) view.findViewById(R.id.edit_num);
        editText.setVisibility(View.GONE);
        Button in = (Button) view.findViewById(R.id.in);
        Button out = (Button) view.findViewById(R.id.out);
        in.setText("确定");
        out.setText("取消");
        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStoreCellDao.deleteProductCate(data.getpId());
                mDatas.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}

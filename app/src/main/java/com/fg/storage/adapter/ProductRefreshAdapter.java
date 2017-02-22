package com.fg.storage.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fg.storage.R;
import com.fg.storage.dao.ProductDao;
import com.fg.storage.dao.StoreCellDao;
import com.fg.storage.model.Product;
import com.fg.storage.ui.product.StorageActivity;
import com.othershe.baseadapter.ViewHolder;

import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/29 15:40
 */
public class ProductRefreshAdapter extends CommonBaseAdapter<Product> {
    private ProductDao mStoreCellDao;
    private StoreCellDao mStoreDao;

    public ProductRefreshAdapter(Context context, List<Product> datas, boolean isLoadMore) {
        super(context, datas, isLoadMore);

        mStoreCellDao = new ProductDao(context);
        mStoreDao = new StoreCellDao(context);
    }

    @Override
    protected void convert(ViewHolder holder, final Product data, final int position) {
        ;
        holder.setText(R.id.tv_batch, "周期:" + data.getBatchNum());
        holder.setText(R.id.tv_store_id, "仓位:" + mStoreDao.queryStoreCellById(data.getStoreId()).getStoreName());
        holder.setText(R.id.tv_num, "数量:" + data.getCount());
        holder.setOnClickListener(R.id.btn_trans, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTransDialog(data, position);
            }
        });
        holder.setOnClickListener(R.id.btn_out, new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showOutOrInDialog(data, position);
            }
        });
        holder.setOnClickListener(R.id.ll_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, StorageActivity.class).putExtra("caterory", data.getProductName()));
            }
        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_content;
    }

    public void showOutOrInDialog(final Product data, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = ((Activity) mContext).getLayoutInflater().inflate(R.layout.alert_layout, null);
        final AlertDialog alertDialog = builder.setTitle("操作")
                .setView(view)
                .create();
        Button in = (Button) view.findViewById(R.id.in);
        Button out = (Button) view.findViewById(R.id.out);
        TextView supply_name = (TextView) view.findViewById(R.id.supply_name);
        supply_name.setText("供应商: " + data.getSupplier());
        final EditText editText = (EditText) view.findViewById(R.id.edit_num);
        final int count = data.getCount();
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStoreCellDao.updateProductByNum(data.getpId(), count - Integer.valueOf(editText.getText().toString().trim()));
                data.setCount(count - Integer.valueOf(editText.getText().toString().trim()));
                if (data.getCount() == 0) {

                    mStoreCellDao.deleteProduct(data.getpId());
                    notifyItemRemoved(position);
                } else {
                    notifyItemChanged(position, data);
                }

                alertDialog.dismiss();
            }
        });
        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStoreCellDao.updateProductByNum(data.getpId(), count + Integer.valueOf(editText.getText().toString().trim()));
                data.setCount(count + Integer.valueOf(editText.getText().toString().trim()));
                notifyItemChanged(position, data);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public void showTransDialog(final Product data, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = ((Activity) mContext).getLayoutInflater().inflate(R.layout.alert_trans_layout, null);
        final AlertDialog alertDialog = builder.setTitle("转仓")
                .setView(view)
                .create();
        Button in = (Button) view.findViewById(R.id.in);
        TextView supply_name = (TextView) view.findViewById(R.id.supply_name);
        supply_name.setText("供应商: " + data.getSupplier());
        final EditText editText = (EditText) view.findViewById(R.id.edit_num);
        final EditText editTrans = (EditText) view.findViewById(R.id.edit_trans);
        final int count = data.getCount();
        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editText.getText()) || TextUtils.isEmpty(editTrans.getText())) {
                    Toast.makeText(mContext, "请输入数量", Toast.LENGTH_SHORT);
                    return;
                }
                if (mStoreDao.queryStoreCellByName(editTrans.getText().toString()) == null) {
                    Toast.makeText(mContext, "转仓失败", Toast.LENGTH_SHORT);
                    return;
                }
                mStoreCellDao.updateProductByNum(data.getpId(), count - Integer.valueOf(editText.getText().toString().trim()));
                data.setCount(count - Integer.valueOf(editText.getText().toString().trim()));
                if (data.getCount() == 0) {
                    mStoreCellDao.deleteProduct(data.getpId());
                    notifyItemRemoved(position);
                } else {
                    notifyItemChanged(position, data);
                }
                insert(data, editTrans.getText().toString().trim());
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void insert(Product data, String storeName) {
        if (mStoreDao.queryStoreCellByName(storeName) == null) {
            Toast.makeText(mContext, "转仓失败", Toast.LENGTH_SHORT);
            return;
        }
        int storeId = mStoreDao.queryStoreCellByName(storeName).getStoreId();
        long count = mStoreCellDao.getProductCount();
        Product product = new Product();
        product.setpId((int) count);
        product.setProductName(data.getProductName());
        product.setStoreId(mStoreDao.queryStoreCellByName(storeName).getStoreId());
        product.setBatchNum(data.getBatchNum());
        product.setCount((int) count);
        product.setSupplier(product.getSupplier());
        product.setRemarks(product.getRemarks());
        mStoreCellDao.addProduct(product);
    }
}

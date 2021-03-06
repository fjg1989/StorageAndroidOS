package com.fg.storage.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    private NotifyAllCount notifyAllCount;
    private String prodName;
    public interface NotifyAllCount {
        void notify(String count);
    }

    public ProductRefreshAdapter(Context context,String prodName, List<Product> datas, boolean isLoadMore, NotifyAllCount notifyAllCount) {
        super(context, datas, isLoadMore);
        this.notifyAllCount = notifyAllCount;
        mStoreCellDao = new ProductDao(context);
        mStoreDao = new StoreCellDao(context);
        this.prodName=prodName;
    }

    @Override
    protected void convert(ViewHolder holder, final Product data, final int position) {
        ;
        holder.setText(R.id.tv_batch,  data.getBatchNum());
        holder.setText(R.id.tv_store_id, mStoreDao.queryStoreCellById(data.getStoreId()).getStoreName());
        holder.setText(R.id.tv_num, "数量:" + data.getCount());
        holder.setText(R.id.supplyname,  data.getSupplier());
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
                notifyAllCount.notify(mStoreCellDao.getProductCountByName(prodName)+"");
                alertDialog.dismiss();
            }
        });
        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStoreCellDao.updateProductByNum(data.getpId(), count + Integer.valueOf(editText.getText().toString().trim()));
                data.setCount(count + Integer.valueOf(editText.getText().toString().trim()));
                notifyItemChanged(position, data);
                notifyAllCount.notify(mStoreCellDao.getProductCountByName(prodName)+"");
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


}

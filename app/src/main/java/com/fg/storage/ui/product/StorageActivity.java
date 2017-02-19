package com.fg.storage.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fg.storage.R;
import com.fg.storage.dao.ProductDao;
import com.fg.storage.dao.RecordDao;
import com.fg.storage.model.Product;
import com.fg.storage.model.Record;
import com.fg.storage.ui.base.BaseActivity;
import com.fg.storage.ui.batch.BatchListActivity;
import com.fg.storage.ui.storecell.StoreListActivity;
import com.fg.storage.ui.suppliers.SupplyListActivity;
import com.fg.storage.util.Constant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class StorageActivity extends BaseActivity {
    private LinearLayout ll_batch;
    private LinearLayout ll_store;
    private LinearLayout ll_pname;
    private LinearLayout ll_supply;
    private TextView pname;
    private TextView batch;
    private TextView store;
    private TextView date;
    private TextView num;
    private TextView remark;
    private TextView supply;
    private Button button;
    private ProductDao mRealmHelper;
    private List<Product> mStoreCells = new ArrayList<>();
    private RecordDao mRecordDao;
    private int storeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        initData();
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setToolbar(toolbar, "入口信息录入");
        ll_batch = (LinearLayout) findViewById(R.id.ll_batch);
        ll_pname = (LinearLayout) findViewById(R.id.ll_pname);
        ll_store = (LinearLayout) findViewById(R.id.ll_store);
        ll_supply = (LinearLayout) findViewById(R.id.ll_supply);
        pname = (TextView) findViewById(R.id.pname);
        batch = (TextView) findViewById(R.id.batch);
        store = (TextView) findViewById(R.id.store);
        date = (TextView) findViewById(R.id.date);
        num = (TextView) findViewById(R.id.num);
        remark = (TextView) findViewById(R.id.remark);
        supply = (TextView) findViewById(R.id.supply);
        button = (Button) findViewById(R.id.submit);
        date.setText(getTDate());
        ll_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(StorageActivity.this, BatchListActivity.class).putExtra("batch", Constant.BATCH), Constant.BATCH);
            }
        });
        ll_pname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(StorageActivity.this, ProductListActivity.class).putExtra("proname", Constant.PRONAME), Constant.PRONAME);
            }
        });
        ll_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(StorageActivity.this, StoreListActivity.class).putExtra("store", Constant.STORE), Constant.STORE);
            }
        });
        ll_supply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(StorageActivity.this, SupplyListActivity.class).putExtra("supply", Constant.SUPPLY), Constant.SUPPLY);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(num.getText().toString().trim())) {
                    Toast.makeText(StorageActivity.this, "请输入数量", Toast.LENGTH_SHORT).show();
                    return;
                }
                long count = mRealmHelper.getProductCount();
                Product product = new Product();
                product.setpId((int) count);
                product.setProductName(pname.getText().toString().trim());
                product.setStoreId(storeId);
                product.setBatchNum(batch.getText().toString().trim());
                product.setCount(Integer.valueOf(num.getText().toString().trim()));
                product.setSupplier(supply.getText().toString().trim());
                product.setRemarks(remark.getText().toString().trim());
                mRealmHelper.addProduct(product);
                Record record = new Record();
                record.setRecordId((int) mRecordDao.getSupplyCount());
                record.setpId((int) count);
                record.setDate(getTDate());
                mRecordDao.addRecord(record);
                Toast.makeText(StorageActivity.this, "入库成功", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }

    private void initData() {
        mRealmHelper = new ProductDao(this);
        mRecordDao = new RecordDao(this);
        mStoreCells = mRealmHelper.queryAllProduct();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case Constant.SUPPLY:
                supply.setText(data.getStringExtra("supply"));
                break;
            case Constant.BATCH:
                batch.setText(data.getStringExtra("batch"));
                break;
            case Constant.PRONAME:
                pname.setText(data.getStringExtra("proname"));
                break;
            case Constant.STORE:
                store.setText(data.getStringExtra("storename"));
                storeId = data.getIntExtra("storeid", 0);
                break;
            default:
                break;
        }
    }

    public String getTDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = new Date();
        String t1 = format.format(d1);
        return t1;
    }
}

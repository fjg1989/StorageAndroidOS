package com.fg.storage.ui.product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fg.storage.R;
import com.fg.storage.dao.ProductCateDao;
import com.fg.storage.model.ProductCate;
import com.fg.storage.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateProductActivity extends BaseActivity {
    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.old)
    TextView old;
    private ProductCateDao mStoreCellDao;
    private int mId;
    private int addCell;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        etName.setHint("请输入新物料名字");
    }

    private void initData() {
        mStoreCellDao = new ProductCateDao(this);
        mId = getIntent().getIntExtra("pId", 0);
        if (getIntent().getStringExtra("old") != null && !getIntent().getStringExtra("old").isEmpty()) {
            old.setText("旧名字：  " + getIntent().getStringExtra("old"));
        }
        addCell = getIntent().getIntExtra("addCell", 0);
        switch (addCell) {
            case 0:
                setToolbar(mToolbar, "编辑物料名称");
                break;
            case 1:
                setToolbar(mToolbar, "增加物料");

                break;
            default:
                break;

        }
    }

    @OnClick(R.id.btn_update)
    void onClick(View v) {
        String name = etName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(UpdateProductActivity.this, "请输入名称", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (addCell) {
            case 0:
                mStoreCellDao.updateProductCate(mId, name);
                Toast.makeText(this, "更改成功", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
                break;
            case 1:
                long count = mStoreCellDao.getProductCateCount();
                int count1 = (int) count;
                ProductCate storeCell = new ProductCate();
                storeCell.setpId(count1);
                storeCell.setProductName(name);
                storeCell.setProductNameSort(Integer.valueOf(name));
                mStoreCellDao.addProduct(storeCell);
                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
                break;
            default:
                break;
        }

    }
}

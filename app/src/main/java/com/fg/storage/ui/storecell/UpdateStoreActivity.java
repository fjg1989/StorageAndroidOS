package com.fg.storage.ui.storecell;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fg.storage.R;
import com.fg.storage.dao.StoreCellDao;
import com.fg.storage.model.StoreCell;
import com.fg.storage.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateStoreActivity extends BaseActivity {
    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.old)
    TextView old;
    private StoreCellDao mStoreCellDao;
    private int mId;
    private int addCell;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);
        initData();
        etName.setHint("请输入新仓位名字");
    }

    private void initData() {
        mStoreCellDao = new StoreCellDao(this);
        mId = getIntent().getIntExtra("storeId", 0);
        if (getIntent().getStringExtra("old") != null && !getIntent().getStringExtra("old").isEmpty()) {
            old.setText("旧名字：  " + getIntent().getStringExtra("old"));
        }

        addCell = getIntent().getIntExtra("addCell", 0);
        switch (addCell) {
            case 0:
                setToolbar(mToolbar, "编辑库位名称");
                break;
            case 1:
                setToolbar(mToolbar, "增加库位");

                break;
            default:
                break;

        }
    }

    @OnClick(R.id.btn_update)
    void onClick(View v) {
        String name = etName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(UpdateStoreActivity.this, "请输入名称", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (addCell) {
            case 0:
                mStoreCellDao.updateStoreCell(mId, name);
                Toast.makeText(this, "更改成功", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
                break;
            case 1:
                long count = mStoreCellDao.getStoreCellCount();
                int count1 = (int) count;
                StoreCell storeCell = new StoreCell();
                storeCell.setStoreId(count1);
                storeCell.setStoreName(name);
                mStoreCellDao.addStoreCell(storeCell);
                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
                break;
            default:
                break;
        }

    }
}

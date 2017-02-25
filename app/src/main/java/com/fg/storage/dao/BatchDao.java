package com.fg.storage.dao;

import android.content.Context;
import android.widget.Toast;

import com.fg.storage.model.Batch;

import java.util.List;

import io.realm.RealmResults;

/**
 * 作者：fjg on 2017/2/18 20:30
 */

public class BatchDao extends RealmHelper {
    public BatchDao(Context context) {
        super(context);
    }

    /**
     * add （增）
     */
    public void addBatch(final Batch storeCell) {
        if (isSupplyExistByName(storeCell.getBatchName())) {
            Toast.makeText(mContext, "命名重复", Toast.LENGTH_SHORT).show();

            return;
        }
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(storeCell);
        mRealm.commitTransaction();

    }

    /**
     * delete （删）
     */
    public void deleteBatch(int id) {
        Batch storeCell = mRealm.where(Batch.class).equalTo("batchId", id).findFirst();
        ProductDao productDao = new ProductDao(mContext);
        if (productDao.isProductExistByFieldName("batchName", storeCell.getBatchName())) {
            productDao.deleteProductByName("batchName", storeCell.getBatchName());
        }
        mRealm.beginTransaction();
        storeCell.deleteFromRealm();
        mRealm.commitTransaction();
    }

    /**
     * update （改）
     */
    public void updateBatch(int id, String newName) {
        if (isSupplyExistByName(newName)) {
            Toast.makeText(mContext, "命名重复", Toast.LENGTH_SHORT).show();
            return;
        }
        Batch storeCell = mRealm.where(Batch.class).equalTo("batchId", id).findFirst();
        mRealm.beginTransaction();
        storeCell.setBatchName(newName);
        mRealm.commitTransaction();
    }

    /**
     * query （查询所有）
     */
    public List<Batch> queryAllSupply() {
        RealmResults<Batch> storeCells = mRealm.where(Batch.class).findAll();
        /**
         * 对查询结果，按Id进行排序，只能对查询结果进行排序
         */
        //增序排列
        storeCells = storeCells.sort("batchName");
        return mRealm.copyFromRealm(storeCells);
    }

    /**
     * query （根据Id（主键）查）
     */
    public Batch queryBatchById(int id) {

        Batch storeCell = mRealm.where(Batch.class).equalTo("batchId", id).findFirst();

        return storeCell;
    }

    public long getSupplyCount() {
        long storeCellCount = mRealm.where(Batch.class).count();
        return storeCellCount;

    }

    public boolean isSupplyExistByName(String batchName) {
        Batch storeCell = mRealm.where(Batch.class).equalTo("batchName", batchName).findFirst();
        if (storeCell == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isSupplyExist(int id) {
        Batch storeCell = mRealm.where(Batch.class).equalTo("batchId", id).findFirst();
        if (storeCell == null) {
            return false;
        } else {
            return true;
        }
    }
}

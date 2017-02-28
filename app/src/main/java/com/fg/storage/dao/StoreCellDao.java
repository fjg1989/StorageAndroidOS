package com.fg.storage.dao;

import android.content.Context;
import android.widget.Toast;

import com.fg.storage.model.StoreCell;

import java.util.List;

import io.realm.RealmResults;

/**
 * 作者：fjg on 2017/2/18 20:30
 */

public class StoreCellDao extends RealmHelper {
    public StoreCellDao(Context context) {
        super(context);
    }

    /**
     * add （增）
     */
    public void addStoreCell(final StoreCell storeCell) {
        if (isStoreCellExistByName(storeCell.getStoreName())) {
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
    public void deleteStoreCell(int id) {
        StoreCell storeCell = mRealm.where(StoreCell.class).equalTo("storeId", id).findFirst();
        ProductDao productDao = new ProductDao(mContext);
        if (productDao.isProductExistByFieldName("storeId", storeCell.getStoreId())) {
            productDao.deleteProductByName("storeId", storeCell.getStoreId());
        }

        mRealm.beginTransaction();
        storeCell.deleteFromRealm();
        mRealm.commitTransaction();

    }

    /**
     * update （改）
     */
    public void updateStoreCell(int id, String newName) {
        if (isStoreCellExistByName(newName)) {
            Toast.makeText(mContext, "命名重复", Toast.LENGTH_SHORT).show();
            return;
        }
        StoreCell storeCell = mRealm.where(StoreCell.class).equalTo("storeId", id).findFirst();
        mRealm.beginTransaction();
        storeCell.setStoreName(newName);
        mRealm.commitTransaction();
    }

    /**
     * query （查询所有）
     */
    public List<StoreCell> queryAllStoreCell() {
        RealmResults<StoreCell> storeCells = mRealm.where(StoreCell.class).findAll();
        /**
         * 对查询结果，按Id进行排序，只能对查询结果进行排序
         */
        //增序排列
        storeCells = storeCells.sort("storeName");

        return mRealm.copyFromRealm(storeCells);
    }

    /**
     * query （根据Id（主键）查）
     */
    public StoreCell queryStoreCellById(int id) {

        StoreCell storeCell = mRealm.where(StoreCell.class).equalTo("storeId", id).findFirst();

        return storeCell;
    }

    /**
     * query （根据Id（主键）查）
     */
    public StoreCell queryStoreCellByName(String storeName) {

        StoreCell storeCell = mRealm.where(StoreCell.class).equalTo("storeName", storeName).findFirst();

        return storeCell;
    }

    public long getStoreCellCount() {
        long storeCellCount=0;
        if (mRealm.where(StoreCell.class).count()==0){
            return storeCellCount;
        }else{
            storeCellCount = (long) mRealm.where(StoreCell.class).max("storeId")+1;
        }
        return storeCellCount + 1;

    }

    public boolean isStoreCellExistByName(String storeName) {
        StoreCell storeCell = mRealm.where(StoreCell.class).equalTo("storeName", storeName).findFirst();
        if (storeCell == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isStoreCellExist(int id) {
        StoreCell storeCell = mRealm.where(StoreCell.class).equalTo("storeId", id).findFirst();
        if (storeCell == null) {
            return false;
        } else {
            return true;
        }
    }
}

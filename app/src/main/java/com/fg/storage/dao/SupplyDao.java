package com.fg.storage.dao;

import android.content.Context;

import com.fg.storage.model.Supply;

import java.util.List;

import io.realm.RealmResults;

/**
 * 作者：fjg on 2017/2/18 20:30
 */

public class SupplyDao extends RealmHelper {
    public SupplyDao(Context context) {
        super(context);
    }

    /**
     * add （增）
     */
    public void addSupply(final Supply storeCell) {

        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(storeCell);
        mRealm.commitTransaction();

    }

    /**
     * delete （删）
     */
    public void deleteSupply(int id) {
        Supply storeCell = mRealm.where(Supply.class).equalTo("supplyId", id).findFirst();
        mRealm.beginTransaction();
        storeCell.deleteFromRealm();
        mRealm.commitTransaction();
    }

    /**
     * update （改）
     */
    public void updateSupply(int id, String newName) {
        Supply storeCell = mRealm.where(Supply.class).equalTo("supplyId", id).findFirst();
        mRealm.beginTransaction();
        storeCell.setSupplyName(newName);
        mRealm.commitTransaction();
    }

    /**
     * query （查询所有）
     */
    public List<Supply> queryAllSupply() {
        RealmResults<Supply> storeCells = mRealm.where(Supply.class).findAll();
        /**
         * 对查询结果，按Id进行排序，只能对查询结果进行排序
         */
        //增序排列
//        storeCells=storeCells.sort("storeId");
//        //降序排列
//        storeCells=storeCells.sort("id", Sort.DESCENDING);
        return mRealm.copyFromRealm(storeCells);
    }

    /**
     * query （根据Id（主键）查）
     */
    public Supply querySupplyById(int id) {

        Supply storeCell = mRealm.where(Supply.class).equalTo("supplyId", id).findFirst();

        return storeCell;
    }

    public long getSupplyCount() {
        long storeCellCount = mRealm.where(Supply.class).count();
        return storeCellCount;

    }

    public boolean isSupplyExist(int id) {
        Supply storeCell = mRealm.where(Supply.class).equalTo("supplyId", id).findFirst();
        if (storeCell == null) {
            return false;
        } else {
            return true;
        }
    }
}

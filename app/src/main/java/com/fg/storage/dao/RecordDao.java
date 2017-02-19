package com.fg.storage.dao;

import android.content.Context;

import com.fg.storage.model.Record;
import com.fg.storage.model.Supply;

import java.util.List;

import io.realm.RealmResults;

/**
 * 作者：fjg on 2017/2/18 20:30
 */

public class RecordDao extends RealmHelper {
    public RecordDao(Context context) {
        super(context);
    }

    /**
     * add （增）
     */
    public void addRecord(final Record storeCell) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(storeCell);
        mRealm.commitTransaction();

    }

    /**
     * delete （删）
     */
    public void deleteRecord(int id) {
        Record storeCell = mRealm.where(Record.class).equalTo("recordId", id).findFirst();
        mRealm.beginTransaction();
        storeCell.deleteFromRealm();
        mRealm.commitTransaction();
    }

    /**
     * update （改）
     */
    public void updateRecord(int id, int pid, String date) {
        Record storeCell = mRealm.where(Record.class).equalTo("recordId", id).findFirst();
        mRealm.beginTransaction();
        storeCell.setRecordId(id);
        storeCell.setDate(date);
        storeCell.setpId(pid);
        mRealm.commitTransaction();
    }

    /**
     * query （查询所有）
     */
    public List<Record> queryAllRecord() {
        RealmResults<Record> storeCells = mRealm.where(Record.class).findAll();
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
    public Record queryRecordById(int id) {

        Record storeCell = mRealm.where(Record.class).equalTo("recordId", id).findFirst();

        return storeCell;
    }

    public long getSupplyCount() {
        long storeCellCount = mRealm.where(Record.class).count();
        return storeCellCount;

    }

    public boolean isSupplyExist(int id) {
        Supply storeCell = mRealm.where(Supply.class).equalTo("recordId", id).findFirst();
        if (storeCell == null) {
            return false;
        } else {
            return true;
        }
    }
}

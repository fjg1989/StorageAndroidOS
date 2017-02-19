package com.fg.storage.model;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * 作者：fjg on 2017/2/19 08:21
 */

public class Record extends RealmObject {
    @PrimaryKey
    private int recordId;
    @Index
    private int pId;
    @Index
    private String date;
    public Record() {

    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

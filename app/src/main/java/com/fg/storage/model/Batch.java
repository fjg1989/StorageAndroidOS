package com.fg.storage.model;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * 作者：fjg on 2017/2/13 21:28
 */

public class Batch extends RealmObject {
    @PrimaryKey
    private int batchId;
    @Index
    private String batchName;

    public Batch() {
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }
}

package com.fg.storage.model;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * 作者：fjg on 2017/2/13 21:28
 */

public class StoreCell extends RealmObject{
    @PrimaryKey
    private int storeId;
    @Index
    private String storeName;

    public StoreCell() {
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}

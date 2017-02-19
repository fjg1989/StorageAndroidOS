package com.fg.storage.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * 作者：fjg on 2017/2/13 21:28
 */

public class Supply extends RealmObject {
    @PrimaryKey
    private int supplyId;
    private String supplyName;

    public Supply() {
    }

    public int getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(int storeId) {
        this.supplyId = storeId;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }
}

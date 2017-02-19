package com.fg.storage.model;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * 作者：fjg on 2017/2/13 21:32
 */

public class ProductCate extends RealmObject {
    @PrimaryKey
    private int pId;
    @Index
    private String productName;//物料号

    public ProductCate() {
    }
    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

}

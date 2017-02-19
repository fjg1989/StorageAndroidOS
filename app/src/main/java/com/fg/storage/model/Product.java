package com.fg.storage.model;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * 作者：fjg on 2017/2/13 21:32
 */

public class Product extends RealmObject{
    @PrimaryKey
    private int pId;
    @Index
    private String productName;//物料号
    private int storeId;//仓位号
    @Index
    private String batchNum;//批次 周期
    private int count;//数量
    private String supplier;//供应商
    private String remarks;//备注　

    public Product() {
    }

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int  pId) {
        this.pId = pId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}

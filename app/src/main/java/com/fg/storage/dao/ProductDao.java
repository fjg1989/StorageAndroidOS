package com.fg.storage.dao;

import android.content.Context;

import com.fg.storage.model.Product;

import java.util.List;

import io.realm.RealmResults;

/**
 * 作者：fjg on 2017/2/18 20:30
 */

public class ProductDao extends RealmHelper {
    public ProductDao(Context context) {
        super(context);
    }

    /**
     * add （增）
     */
    public void addProduct(final Product storeCell) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(storeCell);
        mRealm.commitTransaction();

    }

    /**
     * delete （删）
     */
    public void deleteProduct(int id) {
        Product storeCell = mRealm.where(Product.class).equalTo("pId", id).findFirst();
        mRealm.beginTransaction();
        storeCell.deleteFromRealm();
        mRealm.commitTransaction();
    }

    /**
     * update （改）
     */
    public void updateProduct(int id, String newName) {
        Product storeCell = mRealm.where(Product.class).equalTo("pId", id).findFirst();
        mRealm.beginTransaction();
        storeCell.setProductName(newName);
        mRealm.commitTransaction();
    }
    /**
     * update （改）
     */
    public void updateProductByNum(int id, int count) {
        Product storeCell = mRealm.where(Product.class).equalTo("pId", id).findFirst();
        mRealm.beginTransaction();
        storeCell.setCount(count);
        mRealm.commitTransaction();
    }
    /**
     * query （查询所有）
     */
    public List<Product> queryAllProduct() {
        RealmResults<Product> storeCells = mRealm.where(Product.class).findAll();
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
     * query （查询所有）
     */
    public List<Product> queryAllProduct(String productName) {
        RealmResults<Product> storeCells = mRealm.where(Product.class).equalTo("productName", productName).findAll();
        storeCells = storeCells.sort("batchNum");
        return mRealm.copyFromRealm(storeCells);
    }

    /**
     * query （根据Id（主键）查）
     */
    public Product queryProductById(int id) {

        Product storeCell = mRealm.where(Product.class).equalTo("pId", id).findFirst();

        return storeCell;
    }

    public long getProductCount() {
        long storeCellCount = mRealm.where(Product.class).count();
        return storeCellCount;

    }

    public boolean isProductExist(int id) {
        Product storeCell = mRealm.where(Product.class).equalTo("pId", id).findFirst();
        if (storeCell == null) {
            return false;
        } else {
            return true;
        }
    }
}

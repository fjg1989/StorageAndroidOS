package com.fg.storage.dao;

import android.content.Context;
import android.widget.Toast;

import com.fg.storage.model.ProductCate;

import java.util.List;

import io.realm.RealmResults;

/**
 * 作者：fjg on 2017/2/18 20:30
 */

public class ProductCateDao extends RealmHelper {
    public ProductCateDao(Context context) {
        super(context);
    }

    /**
     * add （增）
     */
    public void addProduct(final ProductCate storeCell) {
        if (isProductCateExist(storeCell.getProductName())) {
            Toast.makeText(mContext, "命名重复", Toast.LENGTH_SHORT);
            return;
        }
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(storeCell);
        mRealm.commitTransaction();

    }

    /**
     * delete （删）
     */
    public void deleteProductCate(int id) {
        ProductCate storeCell = mRealm.where(ProductCate.class).equalTo("pId", id).findFirst();
        ProductDao productDao = new ProductDao(mContext);
        if (productDao.isProductExistByFieldName("productName", storeCell.getProductName())) {
            productDao.deleteProductByName("productName", storeCell.getProductName());
        }
        mRealm.beginTransaction();
        storeCell.deleteFromRealm();
        mRealm.commitTransaction();
    }

    /**
     * update （改）
     */
    public void updateProductCate(int id, String newName) {
        if (isProductCateExist(newName)) {
            Toast.makeText(mContext, "命名重复", Toast.LENGTH_SHORT);
            return;
        }
        ProductCate storeCell = mRealm.where(ProductCate.class).equalTo("pId", id).findFirst();
        mRealm.beginTransaction();
        storeCell.setProductName(newName);
        storeCell.setProductNameSort(Integer.valueOf(newName));
        mRealm.commitTransaction();
    }

    /**
     * query （查询所有）
     */
    public List<ProductCate> queryAllProductCate() {
        RealmResults<ProductCate> storeCells = mRealm.where(ProductCate.class).findAll();
        /**
         * 对查询结果，按Id进行排序，只能对查询结果进行排序
         */
        //增序排列
        storeCells = storeCells.sort("productNameSort");
//        //降序排列
//        storeCells=storeCells.sort("id", Sort.DESCENDING);
        return mRealm.copyFromRealm(storeCells);
    }

    /**
     * query （查询所有）
     */
    public List<ProductCate> queryAllProductCateByName(String productName, int query) {
        RealmResults<ProductCate> storeCells = mRealm.where(ProductCate.class).endsWith(productName, query + "").findAll();
        return mRealm.copyFromRealm(storeCells);
    }

    /**
     * query （根据Id（主键）查）
     */
    public ProductCate queryProductCateById(int id) {

        ProductCate storeCell = mRealm.where(ProductCate.class).equalTo("pId", id).findFirst();

        return storeCell;
    }

    public long getProductCateCount() {
        long storeCellCount = 0;
        if (mRealm.where(ProductCate.class).count() == 0) {
            return storeCellCount;
        } else {
            storeCellCount = (long) mRealm.where(ProductCate.class).max("pId") + 1;
        }
        return storeCellCount;

    }

    public boolean isProductCateExist(String productName) {
        ProductCate storeCell = mRealm.where(ProductCate.class).equalTo("productName", productName).findFirst();
        if (storeCell == null) {
            return false;
        } else {
            return true;
        }
    }
}

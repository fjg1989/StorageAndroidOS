package com.fg.storage.dao;

import android.content.Context;

import io.realm.Realm;

/**
 * Created by matou0289 on 2016/10/20.
 */

public class RealmHelper {
    public static final String DB_NAME = "fg.realm";
    protected Realm mRealm;

    public Context mContext;

    public RealmHelper(Context context) {
        this.mContext = context;
        mRealm = Realm.getDefaultInstance();
    }



    public Realm getRealm() {

        return mRealm;
    }

    public void close() {
        if (mRealm != null) {
            mRealm.close();
        }
    }
}

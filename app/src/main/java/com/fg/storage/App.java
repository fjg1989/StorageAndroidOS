package com.fg.storage;

import android.app.Application;

import com.fg.storage.dao.RealmHelper;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * 作者：fjg on 2017/2/13 21:18
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initRealm();

    }

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(RealmHelper.DB_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
}

package com.fg.storage;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * 作者：fjg on 2017/2/13 21:18
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        initRealm(this);
    }

    private void initRealm(Context context) {
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("fg.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
}

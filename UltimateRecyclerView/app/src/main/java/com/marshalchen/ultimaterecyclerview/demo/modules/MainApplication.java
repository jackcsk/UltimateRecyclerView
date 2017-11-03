package com.marshalchen.ultimaterecyclerview.demo.modules;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.multidex.MultiDex;
<<<<<<< HEAD:UltimateRecyclerView/app/src/main/java/com/marshalchen/ultimaterecyclerview/demo/modules/MainWatcher.java

import com.squareup.leakcanary.LeakCanary;
=======
>>>>>>> Resolve UltimateRecyclerView padding issue:UltimateRecyclerView/app/src/main/java/com/marshalchen/ultimaterecyclerview/demo/modules/MainApplication.java


/**
 * Created by hesk on 2/10/15.
 */
public class MainApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}

package com.example.apk1;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import dalvik.system.DexClassLoader;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button button = (Button) findViewById(R.id.start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putBoolean("KEY_START_FROM_OTHER_ACTIVITY", true);
//                String dexPath = "/mnt/sdcard/apk2.apk";
////                String dexOutputPath = "/data/data/com.example.apk1/";
//                File dexOutputDir = getDir("dex", 0);
//                String absolutePath = dexOutputDir.getAbsolutePath();
//                LoadAPK(bundle, dexPath, absolutePath);
                LoadFragment();
            }
        });
    }

    private void LoadFragment() {
        String name = "apk2.apk";
        DexClassLoader loader = MyClassLoader.getClassLoader(name);
        Bundle bundle = new Bundle();
        bundle.putBoolean("KEY_START_FROM_OTHER_ACTIVITY", true);
        try {
            Class apk2Activity = loader.loadClass("com.example.apk2.Apk2Activity");
            Constructor constructor = apk2Activity.getConstructor(new Class[]{});
            Object instance= constructor.newInstance(new Object[]{});

            Method localMethodSetActivity = apk2Activity.getDeclaredMethod("setActivity", new Class[] {Activity.class});
            localMethodSetActivity.setAccessible(true);
            localMethodSetActivity.invoke(instance, this);

            Method methodCreate = apk2Activity.getDeclaredMethod("onCreate", new Class[] {Bundle.class});
            methodCreate.setAccessible(true);
            methodCreate.invoke(instance, bundle);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @TargetApi(9)
    private void LoadAPK(Bundle bundle, String dexPath, String dexOutputPath) {
        DexClassLoader localDexClassLoader = new DexClassLoader(dexPath, dexOutputPath, null, ClassLoader.getSystemClassLoader());
        PackageInfo packageInfo = getPackageManager().getPackageArchiveInfo(dexPath, PackageManager.GET_ACTIVITIES);
        if (packageInfo != null && packageInfo.activities != null && packageInfo.activities.length > 0) {
            String activityName = packageInfo.activities[0].name;
            Log.d("TAG", "Activity name: " + activityName);

            try {
                Class localClass = localDexClassLoader.loadClass(activityName);
                Constructor localConstructor = localClass.getConstructor(new Class[] {});
                Object instance = localConstructor.newInstance(new Object[] {});
                Log.d("TAG", "instance = " + instance);
                Method localMethodSetActivity = localClass.getDeclaredMethod("setActivity", new Class[] {Activity.class});
                localMethodSetActivity.setAccessible(true);
                localMethodSetActivity.invoke(instance, this);

                Method methodCreate = localClass.getDeclaredMethod("onCreate", new Class[] {Bundle.class});
                methodCreate.setAccessible(true);
                methodCreate.invoke(instance, bundle);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

//    public void LoadAPK(Bundle paramBundle, String dexpath, String dexoutputpath) {
//        DexClassLoader localDexClassLoader = new DexClassLoader(dexpath,
//                dexoutputpath, null, getClassLoader());
//        try {
//            PackageInfo plocalObject = getPackageManager()
//                    .getPackageArchiveInfo(dexpath, 1);
//
//            if ((plocalObject.activities != null)
//                    && (plocalObject.activities.length > 0)) {
//                String activityname = plocalObject.activities[0].name;
//                Log.d("TAG", "activityname = " + activityname);
//
//                Class localClass = localDexClassLoader.loadClass(activityname);
//                Constructor localConstructor = localClass
//                        .getConstructor(new Class[] {});
//                Object instance = localConstructor.newInstance(new Object[] {});
//                Log.d("TAG", "instance = " + instance);
//
//                Method localMethodSetActivity = localClass.getDeclaredMethod(
//                        "setActivity", new Class[] { Activity.class });
//                localMethodSetActivity.setAccessible(true);
//                localMethodSetActivity.invoke(instance, new Object[] { this });
//
//                Method methodonCreate = localClass.getDeclaredMethod(
//                        "onCreate", new Class[] { Bundle.class });
//                methodonCreate.setAccessible(true);
//                methodonCreate.invoke(instance, new Object[] { paramBundle });
//            }
//            return;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
}

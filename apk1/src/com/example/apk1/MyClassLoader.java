package com.example.apk1;

import android.util.Log;
import dalvik.system.DexClassLoader;

import java.io.File;
import java.util.*;

/**
 * Created by ttpod on 14-2-12.
 */
public class MyClassLoader extends DexClassLoader {

    private static final HashMap<String, MyClassLoader> mLoadersMap = new HashMap<String, MyClassLoader>();

    MyClassLoader(String dexPath, String optimizedDirectory,
                         String libraryPath, ClassLoader parent) {
        super(dexPath, optimizedDirectory, libraryPath, parent);
    }

    public static MyClassLoader getClassLoader(String name) {
        MyClassLoader myClassLoader = mLoadersMap.get(name);
        if (myClassLoader != null) {
            return myClassLoader;
        }

        String dexPath = "/mnt/sdcard/" + name;
        File outDexDir = MyApplication.instance().getDir("dex", 0);
        String optimizedDirectory = outDexDir.getAbsolutePath();
        myClassLoader = new MyClassLoader(dexPath, optimizedDirectory, null, ClassLoader.getSystemClassLoader());
        mLoadersMap.put(name, myClassLoader);
        return myClassLoader;
    }

//    @Override
//    protected Class<?> findClass(String className) throws ClassNotFoundException {
//        Class<?> clazz = findLoadedClass(className);
//        if (clazz != null) {
//            return clazz;
//        }
//
//        try {
//            clazz = getParent().loadClass(className);
//        } catch (ClassNotFoundException e) {
//            clazz = null;
//        }
//
//        if (clazz != null) {
//            return clazz;
//        }
//
//        return super.findClass(className);
//    }
}

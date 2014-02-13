package com.example.apk1;

import dalvik.system.DexClassLoader;

import java.util.HashMap;

/**
 * Created by ttpod on 14-2-12.
 */
public class MutiClassLoader extends DexClassLoader {


    MutiClassLoader(String dexPath, String optimizedDirectory,
                    String libraryPath, ClassLoader parent) {
        super(dexPath, optimizedDirectory, libraryPath, parent);
    }

//    public static MutiClassLoader getClassLoader(String name) {
//        String dexPath = "";
//        String optimizedDirectory = "";
////        return new MyClassLoader(dexPath, optimizedDirectory, null, getSystemClassLoader(), )
//    }


    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        Class<?> clazz = findLoadedClass(className);
        if (clazz != null) {
            return clazz;
        }

        try {
            clazz = getParent().loadClass(className);
        } catch (ClassNotFoundException e) {
            clazz = null;
        }

        if (clazz != null) {
            return clazz;
        }

//        if (mMyClassLoaders != null) {
//            for (MutiClassLoader c : mMyClassLoaders) {
//                try {
//                    clazz = c.findClass(className);
//                    break;
//                } catch (ClassNotFoundException e) {
//                    clazz = null;
//                }
//            }
//        }
        if (clazz != null) {
            return clazz;
        }

        return super.findClass(className);
    }
}

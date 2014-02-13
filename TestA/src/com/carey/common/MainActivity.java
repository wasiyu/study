package com.carey.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import dalvik.system.DexClassLoader;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button btn = (Button) findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle paramBundle = new Bundle();
				paramBundle.putBoolean("KEY_START_FROM_OTHER_ACTIVITY", true);
				String dexpath = "/mnt/sdcard/TestB.apk";
				String dexoutputpath = "/data/data/com.carey.common/";
				LoadAPK(paramBundle, dexpath, dexoutputpath);
			}
		});
	}

	public void LoadAPK(Bundle paramBundle, String dexpath, String dexoutputpath) {
		ClassLoader localClassLoader = ClassLoader.getSystemClassLoader();
		DexClassLoader localDexClassLoader = new DexClassLoader(dexpath,
				dexoutputpath, null, localClassLoader);
		try {
			PackageInfo plocalObject = getPackageManager()
					.getPackageArchiveInfo(dexpath, 1);

			if ((plocalObject.activities != null)
					&& (plocalObject.activities.length > 0)) {
				String activityname = plocalObject.activities[0].name;
				Log.d(TAG, "activityname = " + activityname);

				Class localClass = localDexClassLoader.loadClass(activityname);
				Constructor localConstructor = localClass
						.getConstructor(new Class[] {});
				Object instance = localConstructor.newInstance(new Object[] {});
				Log.d(TAG, "instance = " + instance);

				Method localMethodSetActivity = localClass.getDeclaredMethod(
						"setActivity", new Class[] { Activity.class });
				localMethodSetActivity.setAccessible(true);
				localMethodSetActivity.invoke(instance, new Object[] { this });

				Method methodonCreate = localClass.getDeclaredMethod(
						"onCreate", new Class[] { Bundle.class });
				methodonCreate.setAccessible(true);
				methodonCreate.invoke(instance, new Object[] { paramBundle });
			}
			return;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
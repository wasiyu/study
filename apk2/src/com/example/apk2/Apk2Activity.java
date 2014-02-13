package com.example.apk2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Apk2Activity extends Activity {
    private Activity mOtherActivity;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        boolean flag = false;
        if (savedInstanceState != null) {
            flag = savedInstanceState.getBoolean("KEY_START_FROM_OTHER_ACTIVITY", false);
            if (flag) {
                LinearLayout linearLayout = new LinearLayout(mOtherActivity);
                Button button = new Button(mOtherActivity);
                button.setText("打开别的fragment页面");
                button.setOnClickListener(new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                    @Override
                    public void onClick(View v) {
                        FragmentManager fragmentManager = mOtherActivity.getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(android.R.id.primary, new TestFragment1());
                        fragmentTransaction.commit();
                    }
                });
                linearLayout.addView(button);
                mOtherActivity.setContentView(linearLayout);
            }
        }
        if (!flag) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.main);
        }
    }

    public void setActivity(Activity activity) {
        mOtherActivity = activity;
        attachBaseContext(mOtherActivity);
    }

    @Override protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

}

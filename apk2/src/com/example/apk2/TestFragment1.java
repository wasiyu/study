package com.example.apk2;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ttpod on 14-2-13.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class TestFragment1 extends Fragment {
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout rootView = new LinearLayout(getActivity());
        rootView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView textView1 = new TextView(getActivity());
        textView1.setText("我是测试用的fragment ----> 1");
        rootView.addView(textView1);
        return rootView;
    }
}

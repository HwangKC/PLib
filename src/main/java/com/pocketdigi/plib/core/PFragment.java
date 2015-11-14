package com.pocketdigi.plib.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.EFragment;

/**
 * Created by fhp on 14-9-1.
 */
public abstract class PFragment extends Fragment implements OnBackPressedListener {
    PFragmentActivity parentActivity;
    boolean isFirstEnter=true;
    int enterCount=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PLog.d(this, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        PLog.d(this, "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        PLog.d(this, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        enterCount++;
    }

    /**
     * 是否首次进入
     * @return
     */
    public boolean isFirstEnter() {
        return enterCount==1;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PLog.d(this, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        PLog.d(this, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        PLog.d(this, "onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        PLog.d(this, "onPause");
        unregisterListerOrReceiver();
    }

    @Override
    public void onResume() {
        super.onResume();
        PLog.d(this, "onResume");
        registerListenerOrReceiver();
    }

    @Override
    public void onStop() {
        super.onStop();
        PLog.d(this, "onStop");
    }

    /**
     * 注册监听器以及接收器(包括Event),在Fragment被隐藏时，会调用unregisterListerOrReceiver
     * 所以，如果需要在其他页面通知当前页面修改，监听器不应该在这里注册，
     * 应该放到onViewCreated，onDestroyView
     */
    protected void registerListenerOrReceiver() {
        FragmentActivity activity = getActivity();
        if (activity instanceof PFragmentActivity) {
            parentActivity = (PFragmentActivity) activity;
            parentActivity.addBackPressedListener(this);
        }
    }

    /**
     * 解注册监听器及接收器(包括Event)*
     */
    protected void unregisterListerOrReceiver() {
        if (parentActivity != null) {
            parentActivity.removeBackPressedListener(this);
        }
        parentActivity = null;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
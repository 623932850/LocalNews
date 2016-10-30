package com.my.localnews.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.my.localnews.BaseFragment;
import com.my.localnews.R;

/**
 * Created by xiangjianhua on 16/10/9.
 */
public class UserFragment extends BaseFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, null);
        return view;
    }
}

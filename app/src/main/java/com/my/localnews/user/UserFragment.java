package com.my.localnews.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.my.localnews.BaseFragment;
import com.my.localnews.R;

/**
 * Created by xiangjianhua on 16/10/9.
 */
public class UserFragment extends BaseFragment{

    private RelativeLayout mRlFeedback;
    private RelativeLayout mRlAbout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, null);
        mRlFeedback = (RelativeLayout)view.findViewById(R.id.rl_feedback);
        mRlAbout = (RelativeLayout)view.findViewById(R.id.rl_about);
        mRlFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mRlAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }
}

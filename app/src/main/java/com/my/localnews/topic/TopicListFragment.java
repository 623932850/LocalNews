package com.my.localnews.topic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.my.localnews.BaseFragment;
import com.my.localnews.R;

/**
 * Created by xiangjianhua on 16/10/9.
 */
public class TopicListFragment extends BaseFragment {

    private RadioGroup mRadioGroup;
    private HotTopicFragment mHotTopicFragment;
    private MyAttentionFragment mMyAttendionFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHotTopicFragment = new HotTopicFragment();
        mMyAttendionFragment = new MyAttentionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic, null);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.radioBtn_hot_topic) {
                    switchHotTopicFragment();
                } else if (checkedId == R.id.radioBtn_attendtion_topic) {
                    switchMyAttendtionFragment();
                }
            }
        });
        return view;
    }

    public void switchHotTopicFragment() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (mMyAttendionFragment.isAdded()) {
            ft.hide(mMyAttendionFragment);
        }
        if (!mHotTopicFragment.isAdded()) {
            ft.add(R.id.topic_fragment_container, mHotTopicFragment);
        }
        ft.show(mHotTopicFragment);
        ft.commit();
    }

    public void switchMyAttendtionFragment() {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (mHotTopicFragment.isAdded()) {
            ft.hide(mHotTopicFragment);
        }
        if (!mMyAttendionFragment.isAdded()) {
            ft.add(R.id.topic_fragment_container, mMyAttendionFragment);
        }
        ft.show(mMyAttendionFragment);
        ft.commit();
    }



}

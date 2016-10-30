package com.my.localnews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.my.localnews.news.NewsListFragment;
import com.my.localnews.topic.AllTopicActivity;
import com.my.localnews.topic.TopicListFragment;
import com.my.localnews.user.UserFragment;

/**
 * Created by xiangjianhua on 16/10/8.
 */
public class MainActivity extends BaseActivity {

    private NewsListFragment newsListFragment;
    private TopicListFragment topicListFragment;
    private UserFragment userFragment;
    private TextView mTvNewsTab;
    private TextView mTvTopicTab;
    private TextView mTvUserTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvNewsTab = (TextView)findViewById(R.id.tv_news_tab);
        mTvTopicTab = (TextView)findViewById(R.id.tv_topic_tab);
        mTvUserTab = (TextView)findViewById(R.id.tv_user_tab);
        newsListFragment = new NewsListFragment();
        topicListFragment = new TopicListFragment();
        userFragment = new UserFragment();
        mTvNewsTab.setSelected(true);
        switchNewsFragment();
    }

    public void switchNewsFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (topicListFragment.isAdded()) {
            ft.hide(topicListFragment);
        }
        if (userFragment.isAdded()) {
            ft.hide(userFragment);
        }
        if (!newsListFragment.isAdded()) {
            ft.add(R.id.fragment_container, newsListFragment);
        }
        ft.show(newsListFragment);
        ft.commit();
    }

    public void switchTopicFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (newsListFragment.isAdded()) {
            ft.hide(newsListFragment);
        }
        if (userFragment.isAdded()) {
            ft.hide(userFragment);
        }
        if (!topicListFragment.isAdded()) {
            ft.add(R.id.fragment_container, topicListFragment);
        }
        ft.show(topicListFragment);
        ft.commit();
    }

    public void switchUserFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (newsListFragment.isAdded()) {
            ft.hide(newsListFragment);
        }
        if (topicListFragment.isAdded()) {
            ft.hide(topicListFragment);
        }
        if (!userFragment.isAdded()) {
            ft.add(R.id.fragment_container, userFragment);
        }
        ft.show(userFragment);
        ft.commit();
    }

    public void onClickNews(View view){
        switchNewsFragment();
        mTvNewsTab.setSelected(true);
        mTvTopicTab.setSelected(false);
        mTvUserTab.setSelected(false);
    }

    public void onClickTopic(View view){
        switchTopicFragment();
        mTvNewsTab.setSelected(false);
        mTvTopicTab.setSelected(true);
        mTvUserTab.setSelected(false);
    }

    public void onClickMy(View view){
//        switchUserFragment();
//        mTvNewsTab.setSelected(false);
//        mTvTopicTab.setSelected(false);
//        mTvUserTab.setSelected(true);

        LocalNewsApplication.getInstance().shareToFriendCircle();
    }

    public void onClickAllTopic(View view){
        Intent intent = new Intent(this, AllTopicActivity.class);
        startActivity(intent);
    }
}

package com.my.localnews.news;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jingchen.pulltorefresh.PullToRefreshLayout;
import com.my.localnews.BaseFragment;
import com.my.localnews.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xiangjianhua on 16/10/9.
 */
public class NewsListFragment extends BaseFragment implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {

    private PullToRefreshLayout mPullToRefreshLayout;
    private ListView mListView;
    private LayoutInflater mLayoutInflater;
    private NewsListAdapter mAdapter;
    private ImageView mIvEditTags;
    private HorizontalScrollView mTitleTabsView;
    private List<NewsListInfo> mNewsList = new ArrayList<>();
    private String[] mTags = {"推荐", "视频", "深圳", "财经", "社会", "法律", "美图", "段子"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutInflater = LayoutInflater.from(getActivity());
        mAdapter = new NewsListAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        mIvEditTags = (ImageView) view.findViewById(R.id.iv_set_tag);
        mListView = (ListView) view.findViewById(R.id.listview);
        mTitleTabsView = (HorizontalScrollView) view.findViewById(R.id.news_tabs_container);
        mPullToRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.pull_refresh_layout);
        mPullToRefreshLayout.setOnRefreshListener(this);
        for (int i = 0, len = mTags.length; i < len; i++) {
            TextView textview = (TextView) mLayoutInflater.inflate(R.layout.news_tabs_item, null);
            textview.setText(mTags[i]);
            mTitleTabsView.addView(textview);
            textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0, len = mTitleTabsView.getChildCount(); i < len; i++) {
                        View childView = mTitleTabsView.getChildAt(i);
                        if (childView == view) {
                            childView.setSelected(true);
                        } else {
                            childView.setSelected(false);
                        }
                    }
                }
            });
        }
        mListView.setAdapter(mAdapter);
        mIvEditTags.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static class TagsEntity {
        public long id;
        public String name;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_set_tag:
                Intent intent = new Intent(getActivity(), TagsActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        refreshTimeoutHandler.sendEmptyMessageDelayed(MSG_TIME_OUT, 5000);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        refreshTimeoutHandler.sendEmptyMessageDelayed(MSG_TIME_OUT, 5000);
    }

    public static final int MSG_TIME_OUT = 100;

    Handler refreshTimeoutHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_TIME_OUT) {
                mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
            }

        }
    };

    public static class NewsListInfo {
        public String newsTitle;
        public String address;
        public String source;
        public List<String> imageUrls;
    }

    public class NewsListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mNewsList == null ? 0 : mNewsList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 3;
        }

        @Override
        public int getItemViewType(int position) {
            NewsListInfo newsInfo = mNewsList.get(position);
            if (newsInfo.imageUrls == null || newsInfo.imageUrls.size() <= 0) {
                return 0;
            } else if (newsInfo.imageUrls.size() == 1) {
                return 1;
            } else {
                return 2;
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {

            if (convertView == null) {
                if (getItemViewType(position) == 0) {
                    convertView = mLayoutInflater.inflate(R.layout.news_list_item_type_0, null);
                } else if (getItemViewType(position) == 1) {
                    convertView = mLayoutInflater.inflate(R.layout.news_list_item_type_1, null);
                } else {
                    convertView = mLayoutInflater.inflate(R.layout.news_list_item_type_1, null);
                }
            }

            return convertView;
        }
    }

}

package com.my.localnews.topic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.my.localnews.BaseFragment;
import com.my.localnews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangjianhua on 16/10/15.
 */
public class HotTopicFragment extends BaseFragment{

    private ListView mListView;
    private List<Entity> mDatas;
    private HotTopicAdapter mAdapter;
    private LayoutInflater mLayoutInflater;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new HotTopicAdapter();
        mDatas = new ArrayList<>();
        mLayoutInflater = LayoutInflater.from(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_topic, null);
        mListView = (ListView)view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);
        return view;
    }

    public class Entity{

        public String name;
        public String createTime;
        public String headUrl;
        public List<String> imageUrls;
        public String content;
        public int goods;
        public int comments;

    }

    public static class ViewHolder{


    }

    public class HotTopicAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if(convertView == null){
                convertView = mLayoutInflater.inflate(R.layout.topic_hot_item, null);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder)convertView.getTag();
            return convertView;
        }
    }
}

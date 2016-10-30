package com.my.localnews.topic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baoyz.swipemenulistview.BaseSwipListAdapter;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.my.localnews.BaseFragment;
import com.my.localnews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangjianhua on 16/10/15.
 */
public class MyAttentionFragment extends BaseFragment{

    private SwipeMenuListView mSwipeMenuListView;
    private List<TopicInfo> mTopicList;
    private TopicListAdapter mTopicAdapter;
    private LayoutInflater mLayoutInflater;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutInflater = LayoutInflater.from(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.fragment_my_attendtion, null);
        mSwipeMenuListView = (SwipeMenuListView)view.findViewById(R.id.swipeListView);
        mTopicAdapter = new TopicListAdapter();
        mTopicList = new ArrayList<>();
        for(int i=0; i<20; i++){
            TopicInfo topic = new TopicInfo();
            topic.topicName = "支付宝";
            topic.topicDescription = "全球独立的第三方支付平台";
            mTopicList.add(topic);
        }
        mSwipeMenuListView.setAdapter(mTopicAdapter);
        return view;
    }

    public static class TopicInfo{

        public String topicName;
        public String topicDescription;

    }

    public static class ViewHolder{

        TextView mTvTopicName;
        TextView mTvTopicDescription;
    }

    class TopicListAdapter extends BaseSwipListAdapter{


        @Override
        public int getCount() {
            return mTopicList == null ? 0 : mTopicList.size();
        }

        @Override
        public TopicInfo getItem(int position) {
            return mTopicList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if(convertView == null){
                convertView = mLayoutInflater.inflate(R.layout.topic_my_attendtion_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.mTvTopicName = (TextView)convertView.findViewById(R.id.tv_topicName);
                viewHolder.mTvTopicDescription = (TextView)convertView.findViewById(R.id.tv_topicDescription);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder)convertView.getTag();
            TopicInfo topicInfo = mTopicList.get(position);
            viewHolder.mTvTopicName.setText(topicInfo.topicName);
            viewHolder.mTvTopicDescription.setText(topicInfo.topicDescription);
            return convertView;
        }
    }
}

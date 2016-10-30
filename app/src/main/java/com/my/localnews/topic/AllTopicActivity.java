package com.my.localnews.topic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.my.localnews.BaseActivity;
import com.my.localnews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangjianhua on 16/10/16.
 */
public class AllTopicActivity extends BaseActivity {

    private ListView mCategoryListView;
    private ListView mTopicListView;
    private CategoryAdapter mCategoryAdapter;
    private TopicAdapter mTopicAdapter;
    private List<CategoryEntity> mCategoryDatas;
    private List<TopicEntity> mTopicDatas;
    private LayoutInflater mLayoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_topic);
        mLayoutInflater = LayoutInflater.from(this);
        mCategoryListView = (ListView) findViewById(R.id.listview_topic_category);
        mTopicListView = (ListView) findViewById(R.id.listview_topics);
        mCategoryAdapter = new CategoryAdapter();
        mTopicAdapter = new TopicAdapter();
        mCategoryDatas = new ArrayList<>();
        mTopicDatas = new ArrayList<>();
        mCategoryListView.setAdapter(mCategoryAdapter);
        mTopicListView.setAdapter(mTopicAdapter);
    }

    static class CategoryEntity {

        String category;
    }

    static class TopicEntity {

        String topic;
        String topicDesc;
    }

    public class CategoryAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return 10;
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
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            CategoryViewHolder viewHolder = null;
            if(convertView == null){
                convertView = mLayoutInflater.inflate(R.layout.topic_category_item, null);
                viewHolder = new CategoryViewHolder();
                viewHolder.mTvCategoryName = (TextView)convertView.findViewById(R.id.tv_category);
                convertView.setTag(viewHolder);
            }
            viewHolder = (CategoryViewHolder)convertView.getTag();
            viewHolder.mTvCategoryName.setText("支付宝");
            return convertView;
        }
    }

    static class CategoryViewHolder{
        public TextView mTvCategoryName;
    }

    static class TopicViewholder{

        ImageView mIvTopicIcon;
        TextView mTvTopicName;
        TextView mTvTopicDesc;
        Button mBtnAttendtion;
    }

    public class TopicAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 10;
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
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            TopicViewholder viewHolder = null;
            if(convertView == null){
                convertView = mLayoutInflater.inflate(R.layout.topic_item, null);
                viewHolder = new TopicViewholder();
                viewHolder.mIvTopicIcon = (ImageView)convertView.findViewById(R.id.iv_topic_icon);
                viewHolder.mTvTopicName = (TextView)convertView.findViewById(R.id.tv_topic_name);
                viewHolder.mTvTopicDesc = (TextView)convertView.findViewById(R.id.tv_topic_desc);
                viewHolder.mBtnAttendtion = (Button)convertView.findViewById(R.id.btn_attendtion);
                convertView.setTag(viewHolder);
            }
            viewHolder = (TopicViewholder)convertView.getTag();
            viewHolder.mTvTopicName.setText("支付宝");
            viewHolder.mTvTopicDesc.setText("支付宝是一个独立支付平台");
            return convertView;
        }
    }
}

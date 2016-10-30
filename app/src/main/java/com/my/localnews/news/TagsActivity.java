package com.my.localnews.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.localnews.ArrayRequestCallback;
import com.my.localnews.BaseActivity;
import com.my.localnews.R;
import com.my.localnews.RequestManager;
import com.my.localnews.domain.NewsTags;
import com.my.localnews.utils.URLUtils;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangjianhua on 16/10/10.
 */
public class TagsActivity extends BaseActivity {

    private DragGridView mMyTagsGridView;
    private DragGridView mRecommendGridView;
    private TagsAdapter mMyTagsAdapter;
    private TagsAdapter mRecommendAdapter;
    private List<NewsTags> mMyTagsList;
    private List<NewsTags> mRecommendTagsList;
    private LayoutInflater mLayoutInflater;
    private TextView mTvEditToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);
        mLayoutInflater = LayoutInflater.from(this);
        mMyTagsGridView = (DragGridView) findViewById(R.id.gridview_mytags);
        mRecommendGridView = (DragGridView) findViewById(R.id.gridview_recommend_tags);
        mTvEditToggle = (TextView) findViewById(R.id.tv_edit_toggle);
        mMyTagsList = new ArrayList<NewsTags>();

        mMyTagsAdapter = new TagsAdapter(mMyTagsList);
        mMyTagsGridView.setAdapter(mMyTagsAdapter);

        mRecommendTagsList = new ArrayList<NewsTags>();
        mRecommendAdapter = new TagsAdapter(mRecommendTagsList);
        mRecommendGridView.setAdapter(mRecommendAdapter);

        mRecommendGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                NewsTags newsTags = mRecommendAdapter.getItem(position);
                RequestManager.getInstance().request(RequestManager.REQCODE_ADD_NEWS_TAGS, URLUtils.buildUrl(R.string.ADD_NEWS_TAGS, newsTags.id));
                mRecommendTagsList.remove(newsTags);
                mRecommendAdapter.notifyDataSetChanged();
                mMyTagsList.add(newsTags);
                mMyTagsAdapter.notifyDataSetChanged();
            }
        });

        RequestManager.getInstance().registerListener(handler);
        RequestManager.getInstance().request(RequestManager.REQCODE_MY_NEWS_TAGS);


    }

    public void onClickClose(View view){
        finish();
    }

    public void onClickEdit(View view){
        mMyTagsAdapter.enableEdit(!mMyTagsAdapter.enableEdit);
        mTvEditToggle.setText(mMyTagsAdapter.enableEdit ? R.string.finish : R.string.edit);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestManager.getInstance().unRegisterListener(handler);
    }

    public ArrayRequestCallback<NewsTags> handler = new ArrayRequestCallback<NewsTags>(NewsTags.class) {
        @Override
        public void onSuccess(int reqCode, List<NewsTags> beans) {
            if (reqCode == RequestManager.REQCODE_NEWS_TAGS) {
                beans.removeAll(mMyTagsList);
                mRecommendTagsList.addAll(beans);
                mRecommendAdapter.notifyDataSetChanged();
            } else if (reqCode == RequestManager.REQCODE_MY_NEWS_TAGS) {
                RequestManager.getInstance().request(RequestManager.REQCODE_NEWS_TAGS);
                mMyTagsList.addAll(beans);
                mMyTagsAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onBusinessFailure(int reqCode, int errorCode, String errorMsg) {

        }

        @Override
        public void onNetFailure(int reqCode, int statusCode, Header[] headers, String responseString, Throwable throwable) {

        }
    };

    public static class ViewHolder {
        TextView mTvTagsName;
        ImageView mIvDelete;
    }

    public class TagsAdapter extends BaseAdapter {

        private List<NewsTags> mTagsStrs;
        private boolean enableEdit = false;

        public TagsAdapter(List<NewsTags> tags) {
            mTagsStrs = tags;
        }

        @Override
        public int getCount() {
            return mTagsStrs == null ? 0 : mTagsStrs.size();
        }

        @Override
        public NewsTags getItem(int position) {
            return mTagsStrs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void enableEdit(boolean enable){
            enableEdit = enable;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.tags_item, null);
                viewHolder = new ViewHolder();
                viewHolder.mTvTagsName = (TextView) convertView.findViewById(R.id.tv_tags_name);
                viewHolder.mIvDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mTvTagsName.setText(mTagsStrs.get(position).name);
            viewHolder.mTvTagsName.setGravity(Gravity.CENTER);
            if(enableEdit){
                viewHolder.mIvDelete.setVisibility(View.VISIBLE);
            }else{
                viewHolder.mIvDelete.setVisibility(View.GONE);
            }
            return convertView;
        }
    }

    public void onSelectCity(View view) {
        Intent intent = new Intent(this, CityActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            int code = data.getIntExtra(CityActivity.EXTRA_CITY_ID, 0);
            String name = data.getStringExtra(CityActivity.EXTRA_CITY_NAME);
            NewsTags newsTags = new NewsTags();
            newsTags.id = (long)code;
            newsTags.name = name;
            RequestManager.getInstance().request(RequestManager.REQCODE_ADD_NEWS_TAGS, URLUtils.buildUrl(R.string.ADD_NEWS_TAGS, newsTags.id));
            mMyTagsList.add(newsTags);
            mMyTagsAdapter.notifyDataSetChanged();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

package com.my.localnews.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.my.localnews.BaseActivity;
import com.my.localnews.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangjianhua on 16/10/16.
 */
public class CityActivity extends BaseActivity implements ExpandableListView.OnChildClickListener,
        ExpandableListView.OnGroupClickListener{

    public static final String EXTRA_CITY_ID = "extra_city_id";
    public static final String EXTRA_CITY_NAME = "extra_city_name";

    private ExpandableListView mExpandableListView;
    private List<Province> mProvinces = new ArrayList<>();
    private CityAdapter mCityAdapter = new CityAdapter();
    private LayoutInflater mLayoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        parseCity();
        mLayoutInflater = LayoutInflater.from(this);
        mExpandableListView = (ExpandableListView) findViewById(R.id.expand_listview);
        mExpandableListView.setGroupIndicator(null);
        mExpandableListView.setAdapter(mCityAdapter);
        mExpandableListView.setOnChildClickListener(this);
        mExpandableListView.setOnGroupClickListener(this);
    }

    public void parseCity() {
        Gson gson = new Gson();
        try {
            InputStream is = getAssets().open("provinces.json");
            InputStreamReader reader = new InputStreamReader(is);
            JsonParser parser = new JsonParser();
            JsonObject jsonObj = parser.parse(reader).getAsJsonObject();
            JsonArray provinces = jsonObj.get("provinces").getAsJsonArray();
            List<Province> list = gson.fromJson(provinces, new TypeToken<List<Province>>() {
            }.getType());
            if (list != null) {
                mProvinces.addAll(list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onBackClick(View view) {
        finish();
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
        City city = mProvinces.get(groupPosition).citys.get(childPosition);
        Intent data =new Intent();
        data.putExtra(EXTRA_CITY_NAME, city.name);
        data.putExtra(EXTRA_CITY_ID, city.code);
        setResult(RESULT_OK, data);
        finish();
        return true;
    }

    @Override
    public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long id) {
        Province province = mProvinces.get(groupPosition);
        if(province.citys == null || province.citys.size() <= 0){
            Intent data =new Intent();
            data.putExtra(EXTRA_CITY_NAME, province.name);
            data.putExtra(EXTRA_CITY_ID, province.code);
            setResult(RESULT_OK, data);
            finish();
            return true;
        }
        return false;
    }

    public static class Province {
        public String name;
        public int code;
        public List<City> citys;
    }

    public static class City {
        public int code;
        public String name;
    }

    static class ViewHolder {
        TextView textView;
        ImageView mIvArrow;
    }

    public class CityAdapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return mProvinces.size();
        }

        @Override
        public int getChildrenCount(int position) {
            return mProvinces.get(position).citys == null ? 0 : mProvinces.get(position).citys.size();
        }

        @Override
        public Province getGroup(int position) {
            return mProvinces.get(position);
        }

        @Override
        public City getChild(int groupPos, int childPos) {
            Province province = mProvinces.get(groupPos);
            if (province != null && province.citys != null) {
                return province.citys.get(childPos);
            }
            return null;
        }

        @Override
        public long getGroupId(int position) {
            return position;
        }

        @Override
        public long getChildId(int groupPos, int childPos) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int position, boolean isExpanded, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.city_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_cityName);
                viewHolder.mIvArrow = (ImageView) convertView.findViewById(R.id.iv_arrow);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            Province province = mProvinces.get(position);
            viewHolder.textView.setText(province.name);
            if (isExpanded) {
                viewHolder.mIvArrow.setImageResource(R.mipmap.ic_bottom_arrow);
            } else {
                viewHolder.mIvArrow.setImageResource(R.mipmap.ic_right_arrow);
            }
            return convertView;
        }

        @Override
        public View getChildView(int groupPos, int childPos, boolean b, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.city_list_item_sub, null);
                viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_cityName);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            City city = mProvinces.get(groupPos).citys.get(childPos);
            viewHolder.textView.setText(city.name);
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPos, int childPos) {
            return true;
        }
    }
}

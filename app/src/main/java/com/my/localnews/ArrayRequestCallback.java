package com.my.localnews;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangjianhua on 16/10/30.
 */
public abstract class ArrayRequestCallback<T> implements RequestCallback {

    private Gson gson;
    private Class<T> clazz;

    public ArrayRequestCallback(Class<T> clazz) {
        gson = new Gson();
        this.clazz = clazz;
    }

    public abstract void onSuccess(int reqCode, List<T> beans);

    @Override
    public void onSuccess(int reqCode, String response) {
        List<T> beans = new ArrayList<>();
        JsonParser parser = new JsonParser();
        try {
            JsonElement jsonElement = parser.parse(response);
            if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                for (int i = 0, len = jsonArray.size(); i < len; i++) {
                    JsonElement element = jsonArray.get(i);
                    if (element.isJsonObject()) {
                        T bean = gson.fromJson(element, clazz);
                        beans.add(bean);
                    }
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        onSuccess(reqCode, beans);
    }

}

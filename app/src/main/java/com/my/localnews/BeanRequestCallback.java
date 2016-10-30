package com.my.localnews;

import com.google.gson.Gson;

/**
 * Created by xiangjianhua on 16/10/30.
 */
public abstract class BeanRequestCallback<T> implements RequestCallback {

    private Gson gson;
    private Class<T> clazz;

    public BeanRequestCallback(Class<T> clazz) {
        gson = new Gson();
        this.clazz = clazz;
    }

    @Override
    public void onSuccess(int reqCode, String response) {
        T bean = gson.fromJson(response, clazz);
        onSuccess(reqCode, bean);
    }

    public abstract void onSuccess(int reqCode, T bean);

}

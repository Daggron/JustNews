package com.example.news;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.NonNull;

import java.net.URL;
import java.util.List;

public class newsLoader extends android.support.v4.content.AsyncTaskLoader<List<news>>{

    private static final String LOG_TAG = newsLoader.class.getName();

    private String mUrl;

    public newsLoader(@NonNull Context context, String url) {
        super(context);
        mUrl=url;
    }


    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<news> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<news> result = queryUtils.fetchNewsData(mUrl);
        return result;
    }


}
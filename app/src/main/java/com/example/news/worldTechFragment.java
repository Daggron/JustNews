package com.example.news;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class worldTechFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<news>> {


    View rootview;

    public worldTechFragment() {
        // Required empty public constructor
    }

    newsAdapter adapter;

    public static final String LOG_TAG = MainActivity.class.getName();

    private static final String API = uriBuilder();//"https://newsapi.org/v2/everything?q=apple&from=2019-04-30&to=2019-04-30&sortBy=popularity&apiKey=fabb056ff8594a2c9cd1ea680aa83aa7";

    private TextView mEmptyStateTextView;

    private static final int LOADER_ID = 1;
    View loadingIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.list, container, false);

        final LoaderManager loaderManager = getLoaderManager();

        loadingIndicator = rootview.findViewById(R.id.loading_indicator);

        final GridView newsListView = (GridView) rootview.findViewById(R.id.recyclerView);

        adapter = new newsAdapter(getContext(), 0, new ArrayList<news>());


        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                news e = adapter.getItem(position);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(e.getUrl()));
                startActivity(i);


            }
        });

        newsListView.setAdapter(adapter);

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).

        final ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        final NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            loaderManager.initLoader(LOADER_ID, null, this);
            mEmptyStateTextView = (TextView) rootview.findViewById(R.id.empty_view);
            newsListView.setEmptyView(mEmptyStateTextView);

        } else {

            loadingIndicator = rootview.findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView = (TextView) rootview.findViewById(R.id.empty_view);
            newsListView.setEmptyView(mEmptyStateTextView);
            mEmptyStateTextView.setText(R.string.no_internet);
        }

        return rootview;



    }

    @NonNull
    @Override
    public Loader onCreateLoader(int i, @Nullable Bundle bundle) {
        return new newsLoader(getContext(),API);
    }


    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<List<news>> loader, List<news> data) {

        GridView newsListView = rootview.findViewById(R.id.recyclerView);

        mEmptyStateTextView = rootview.findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateTextView);

        loadingIndicator.setVisibility(View.GONE);
        // Clear the adapter of previous news data
        mEmptyStateTextView.setText(R.string.no_news);
        adapter.clear();
        // If there is a valid list of news then add
        if (data != null && !data.isEmpty()) {
            adapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        adapter.clear();

    }

    private static String uriBuilder() {

        //https://newsapi.org/v2/top-headlines?country=us&category=technology&apiKey=fabb056ff8594a2c9cd1ea680aa83aa7
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("newsapi.org")
                .appendPath("v2")
                .appendPath("top-headlines")
                .appendQueryParameter("country", "us")
                .appendQueryParameter("category","technology")
                .appendQueryParameter("apiKey", "fabb056ff8594a2c9cd1ea680aa83aa7");
        String myUrl = builder.build().toString();
        return myUrl;
    }




}

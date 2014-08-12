package com.akadatsky.musiccharts;

import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.akadatsky.musiccharts.model.Artist;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.List;

public abstract class BaseFragment extends Fragment {

    protected View progressView;
    protected TextView errorView;
    protected ListView listView;

    abstract String getUrl();

    abstract List<Artist> parseArtists(Gson gson, String response);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        progressView = view.findViewById(R.id.progress);
        errorView = (TextView) view.findViewById(R.id.error);
        listView = (ListView) view.findViewById(R.id.list);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new LoadArtistTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    private class LoadArtistTask extends AsyncTask<Void, Void, List<Artist>> {

        private String error;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressView.setVisibility(View.VISIBLE);
            errorView.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
        }

        @Override
        protected List<Artist> doInBackground(Void... params) {
            error = null;
            List<Artist> result = null;

            if (!isAdded()) {
                return null;
            }

            if (!isNetworkAvailable()) {
                error = getActivity().getString(R.string.no_network);
                return null;
            }

            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(getUrl());
                HttpResponse response = client.execute(post);
                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity, "UTF-8");
                Gson gson = new GsonBuilder().create();
                result = parseArtists(gson, responseString);
            } catch (Exception e) {
                error = String.valueOf(e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<Artist> artists) {
            super.onPostExecute(artists);
            if (!isAdded()) {
                return;
            }
            progressView.setVisibility(View.GONE);
            if (error == null) {
                listView.setVisibility(View.VISIBLE);
                showList(artists.subList(0, 9));
            } else {
                errorView.setVisibility(View.VISIBLE);
                errorView.setText(error);
            }
        }
    }

    private void showList(List<Artist> artists) {
        ArrayAdapter adapter = new ArrayAdapter<Artist>(getActivity(), 0, artists) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    convertView = inflater.inflate(R.layout.item, parent, false);
                }

                if (isAdded()) {
                    TextView nameView = (TextView) convertView.findViewById(R.id.name);
                    TextView listenersView = (TextView) convertView.findViewById(R.id.listeners);

                    nameView.setText(String.valueOf(position + 1) + ". " + getItem(position).getName());
                    listenersView.setText(String.valueOf(getItem(position).getListeners()) + " " +
                            getActivity().getString(R.string.listeners));
                }

                return convertView;
            }
        };
        listView.setAdapter(adapter);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}

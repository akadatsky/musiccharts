package com.akadatsky.musiccharts;

import com.akadatsky.musiccharts.model.Artist;
import com.akadatsky.musiccharts.model.TopArtists;
import com.akadatsky.musiccharts.util.Const;
import com.google.gson.Gson;

import java.util.List;

public class UkraineFragment extends BaseFragment {

    @Override
    String getUrl() {
        return Const.UKRAINE_URL;
    }

    @Override
    List<Artist> parseArtists(Gson gson, String response) {
        return gson.fromJson(response, TopArtists.class).getTopartists().getArtist();
    }
}

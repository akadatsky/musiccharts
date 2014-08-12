package com.akadatsky.musiccharts;

import com.akadatsky.musiccharts.model.Artist;
import com.akadatsky.musiccharts.model.Artists;
import com.akadatsky.musiccharts.util.Const;
import com.google.gson.Gson;

import java.util.List;

public class WorldFragment extends BaseFragment {

    @Override
    String getUrl() {
        return Const.WORLD_URL;
    }

    @Override
    List<Artist> parseArtists(Gson gson, String response) {
        return gson.fromJson(response, Artists.class).getArtists().getArtist();
    }

}

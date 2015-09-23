package com.example.astrohackathon.webservice;

import android.content.Context;
import android.os.AsyncTask;

import com.example.astrohackathon.helper.JSONParser;

import org.json.JSONObject;

/**
 * Created by RoMY on 10/12/2014.
 */
public class FavouriteWS extends AsyncTask<String, String, String> {
    private JSONParser jsonParser = new JSONParser();
    private Context mContext;
    private static final String WS_URL = "http://128.199.173.244/users/ajax/musicfavourite";

    public FavouriteWS()
    {

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... args) {
        JSONObject result = null;

        return null;

    }

    protected void onPostExecute(String flag) {


    }
}

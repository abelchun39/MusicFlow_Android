package com.example.astrohackathon.webservice;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.astrohackathon.adapter.VerticalAdapter;
import com.example.astrohackathon.helper.JSONParser;
import com.example.astrohackathon.model.Music;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RoMY on 10/11/2014.
 */
public class HomeWS extends AsyncTask<String, String, String> {
    private JSONParser jsonParser = new JSONParser();
    private Context mContext;
    private static final String WS_URL = "http://128.199.173.244/users/ajax/gethome";
    private VerticalAdapter verticalAdapter;

    public HomeWS(Context context)
    {
        this.mContext = context;
    }

    public void setAdapter(VerticalAdapter adapter) {
        verticalAdapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... args) {
        JSONObject result = null;

        try {

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            //Upload the advertisement
            result = jsonParser.makeHttpRequest(HomeWS.WS_URL,
                    "GET", params);

            int returnCode;
            String returnMessage;

            returnCode = result.getInt("returnCode");
            returnMessage = result.getString("returnMessage");

            System.out.println("ReturnCode: " + returnCode);
            System.out.println("ReturnMsg: " + returnMessage);

            if( returnCode == 200 ) {
                JSONArray content = result.getJSONArray("content");

                for(int i = 0; i < content.length(); i++) {
                    Music music = new Music( content.getJSONObject(i) );
                    verticalAdapter.getListMusic().add(music);

                }

            } else {
                return returnMessage;
            }
            result.getJSONArray("content");

        } catch (Exception ex) {
            Log.d(getClass().getName(), ex.toString());
        }

        return null;
    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String flag) {
        if(flag == null) {
            this.verticalAdapter.notifyDataSetChanged();

        } else {
            Toast.makeText(mContext
                    , "Fail"
                    , Toast.LENGTH_LONG).show();
        }

    }
}

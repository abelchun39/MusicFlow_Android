package com.example.astrohackathon.webservice;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.astrohackathon.helper.JSONParser;
import com.example.astrohackathon.utils.SessionInfo;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RoMY on 10/12/2014.
 */
public class AuthWS extends AsyncTask<String, String, String> {
    private static String WS_URL_LOGIN = "http://128.199.173.244/users/ajax/login";
    private static String WS_URL_LOGOUT = "http://128.199.173.244/users/ajax/logout";
    private static boolean authType;
    private JSONParser jsonParser;
    private Context mContext;

    public AuthWS(Context context, boolean authType) {
        this.mContext = context;
        this.authType = authType;
        jsonParser = new JSONParser();

    }

    public String login() {
        JSONObject result;

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", "romy@hotmail.com"));
            params.add(new BasicNameValuePair("password", "test123"));

            //Upload the advertisement
            result = jsonParser.makeHttpRequest(WS_URL_LOGIN,
                    "POST", params);

            int returnCode;
            String returnMessage;

            returnCode = result.getInt("returnCode");
            returnMessage = result.getString("returnMessage");

            System.out.println("ReturnCode: " + returnCode);
            System.out.println("ReturnMsg: " + returnMessage);

            if( returnCode == 200 && result.has("accessToken") ) {
                SessionInfo.setAccessToken( result.getString("accessToken") );
                return "success";
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public String logout() {
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("accessToken", SessionInfo.getAccessToken()));

            //Upload the advertisement
            jsonParser.makeHttpRequest(WS_URL_LOGIN,
                    "POST", params);


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... args) {
        if(  authType ) {
            return login();

        } else {
            return logout();

        }

    }

    protected void onPostExecute(String flag) {
        if( flag != null ) {
            Toast.makeText(this.mContext, "Successfully logged in: " + SessionInfo.getAccessToken(), Toast.LENGTH_LONG).show();
        }

    }
}

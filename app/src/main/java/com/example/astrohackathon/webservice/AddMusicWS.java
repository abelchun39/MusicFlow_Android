package com.example.astrohackathon.webservice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.example.astrohackathon.helper.JSONParser;
import com.example.astrohackathon.model.Music;
import com.example.astrohackathon.utils.MultipartUtility;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RoMY on 10/12/2014.
 */
public class AddMusicWS extends AsyncTask<String, String, String> {
    ProgressDialog progressDialog;
    Context mContext;
    Music music;
    JSONParser jsonParser;
    String WS_URL = "http://128.199.173.244/users/ajax/addmusic";
    String WS_URL_AUDIO = "http://128.199.173.244/users/ajax/addaudio/";
    String WS_URL_IMAGE = "http://128.199.173.244/users/ajax/addimage/";

    private String lineEnd = "\r\n";
    private String twoHyphens = "--";
    private String boundary = "*****";
    private int bytesRead, bytesAvailable, bufferSize;
    private byte[] buffer;
    private int maxBufferSize = 1 * 1024 * 1024;
    private int serverResponseCode = 0;

    public AddMusicWS(Context context, Music music) {
        this.mContext = context;
        this.music = music;
        jsonParser = new JSONParser();
        System.out.println("InitWs");

    }

    @Override
    protected void onPreExecute() {
        System.out.println("onPreExecuteWs");
        super.onPreExecute();

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Uploading music...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... args) {
        System.out.println("doInBackgroundWs");
        JSONObject result = null;

        try {
//            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair("user_id", "1"));
//            params.add(new BasicNameValuePair("title", music.getTitle()));
//            params.add(new BasicNameValuePair("description", music.getDescription()));
//
//            result = jsonParser.makeHttpRequest(WS_URL,
//                    "POST", params);
            String charset = "UTF-8";
            String requestURL = WS_URL;

            try {
                MultipartUtility multipart = new MultipartUtility(requestURL, charset);

                multipart.addHeaderField("User-Agent", "Android");

                multipart.addFormField("title", music.getTitle());
                multipart.addFormField("description", music.getDescription());

                if( music.getMusic_audio() != null  ) {
                    File music_audio = new File( Environment.getExternalStorageDirectory().
                            getAbsolutePath() + "/default.3gpp" );
                    multipart.addFilePart("music_audio", music_audio);
                }

                if( music.getMusic_cover() != null ) {
                    File music_audio = new File( Environment.getExternalStorageDirectory().
                            getAbsolutePath() + "/default.jpeg" );
                    multipart.addFilePart("music_cover", music_audio);

                }
                List<String> response = multipart.finish();

                System.out.println("SERVER REPLIED:");

                for (String line : response) {
                    System.out.println(line);
                }
            } catch (IOException ex) {
                System.err.println(ex);
            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return null;

    }

    protected void onPostExecute(String flag) {
        progressDialog.dismiss();
        ((Activity)this.mContext).finish();
    }
}

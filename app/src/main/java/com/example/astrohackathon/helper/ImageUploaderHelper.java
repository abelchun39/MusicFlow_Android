package com.example.astrohackathon.helper;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class ImageUploaderHelper extends AsyncTask<String, String, String>{
	private static final String UPLOAD_URL = "http://54.254.130.183:80/XChange/uploadImage.php";
	private Context mContext;
	private ByteArrayInputStream mBis;
	private HttpURLConnection conn = null;
    private DataOutputStream dos = null;
    private String mFilename = null;
    
    private String lineEnd = "\r\n";
    private String twoHyphens = "--";
    private String boundary = "*****";
    private int bytesRead, bytesAvailable, bufferSize;
    private byte[] buffer;
    private int maxBufferSize = 1 * 1024 * 1024;
    private int serverResponseCode = 0;
	
	private ProgressDialog pDialog;
	
	public ImageUploaderHelper(Context nContext, ByteArrayInputStream nBis
			, String nFileName){
		mContext = nContext;
		mBis = nBis;
		mFilename = nFileName;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		/*pDialog = new ProgressDialog(mContext);
		pDialog.setMessage("Uploading image...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();*/
	}
	
	@Override
	protected String doInBackground(String... args) {
		// TODO Auto-generated method stub
		// Check for success tag
		try {
			URL url = new URL(UPLOAD_URL);
			
			conn = (HttpURLConnection) url.openConnection(); 
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("uploaded_file", mFilename); 
            
            dos = new DataOutputStream(conn.getOutputStream());
            
            dos.writeBytes(twoHyphens + boundary + lineEnd); 
            dos.writeBytes(
            		"Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                    + mFilename + "\"" + lineEnd);
             
            dos.writeBytes(lineEnd);
   
            // create a buffer of  maximum size
            bytesAvailable = mBis.available(); 
            
            if(bytesAvailable > maxBufferSize){
            	throw new Exception("Unable to upload Image larger than 1 MB, " +
            			"please select another image");
            }
            
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
   
            // read file and write it into form...
            bytesRead = mBis.read(buffer, 0, bufferSize);  
            
            while (bytesRead > 0) {
            	dos.write(buffer, 0, bufferSize);
            	bytesAvailable = mBis.available();
            	bufferSize = Math.min(bytesAvailable, maxBufferSize);
            	bytesRead = mBis.read(buffer, 0, bufferSize);
            }
            Log.d("Image Uploader", "Remaining: " + mBis.available());
   
            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
   
            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();//conn.getResponseMessage();
            
              
            Log.i("uploadFile", "HTTP Response is : "
                    + serverResponseMessage + ": " + serverResponseCode);
             
            if(serverResponseCode != 200){
            	Log.i("uploadFile", "Failed");    
            } else{
            	Log.i("uploadFile", "Success");    
            }
             
            //close the streams //
            mBis.close();
            dos.flush();
            dos.close();
		} catch (Exception ex) {
			pDialog.setMessage(ex.getMessage());
			Log.d("My Custom Error", ex.toString());
		}
		return null;
	}

	/**
	 * After completing background task Dismiss the progress dialog
	 * **/
	protected void onPostExecute(String file_url) {
		// dismiss the dialog once product deleted
		//pDialog.dismiss();
	}
	
}

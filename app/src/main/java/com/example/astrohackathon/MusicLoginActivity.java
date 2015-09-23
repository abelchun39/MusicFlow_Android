package com.example.astrohackathon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by chunqhai on 10/12/2014.
 */
public class MusicLoginActivity extends Activity {

    Button mLogin;
    Button mLoginFacebook;
    View progress;
    TextView progressStatus;
    EditText mUsername;
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getActionBar().setTitle("Welcome | Login");

        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);

        progress = (View) findViewById(R.id.progress);
        progressStatus = (TextView) findViewById(R.id.loading);
        mLoginFacebook = (Button) findViewById(R.id.btn_loginfacebook);

        mLogin = (Button) findViewById(R.id.btn_login);
        mLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mLogin.setVisibility(View.GONE);
                mLoginFacebook.setVisibility(View.GONE);
                mUsername.setEnabled(false);
                mUsername.setClickable(false);
                mPassword.setEnabled(false);
                mPassword.setClickable(false);

                progress.setVisibility(View.VISIBLE);
                progressStatus.setVisibility(View.VISIBLE);
                progress.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
                        intentMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intentMain);
                        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);

                    }
                }, 500);
            }
        });
    }
}

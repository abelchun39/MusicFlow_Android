package com.example.astrohackathon;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by chunqhai on 10/12/2014.
 */

public class MusicLyricsActivity extends Activity implements View.OnClickListener{

    private static int REQUEST_ADD_CONTRIBUTOR = 0;
    protected static final int PICK_IMAGE = 0;
    protected static final int CANCEL_IMAGE =1;

    TextView viewCountText;
    EditText lyricsList;
    int textCount = 80;

    ImageButton mAddContributor;
    Button mBtnPost;
    Button mBtnUpload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyricsadder);

        viewCountText = (TextView) findViewById(R.id.music_text_count);
        mAddContributor =  (ImageButton) findViewById(R.id.btn_action_add_contributor);
        mAddContributor.setOnClickListener(this);

        mBtnPost = (Button) findViewById(R.id.btn_save);
        mBtnPost.setOnClickListener(this);

        mBtnUpload = (Button) findViewById(R.id.btn_upload);
        mBtnUpload.setOnClickListener(this);

        lyricsList = (EditText) findViewById(R.id.music_lyrics);
        lyricsList.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                textCount--;
                viewCountText.setText((String.valueOf(textCount)) + " words");
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id)
        {
            case R.id.btn_action_add_contributor :
                Intent intent = new Intent(getApplicationContext(), MusicContributorActivity.class);
                startActivityForResult(intent,REQUEST_ADD_CONTRIBUTOR);
                break;

            case R.id.btn_save:

                break;

            case R.id.btn_upload :
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, ""), PICK_IMAGE);
                break;

            default:

                break;

        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == PICK_IMAGE) {
                Uri selectedIamgeUri = data.getData();
                Toast.makeText(getApplicationContext(), "You choose one image file from " + getRealPathFromURI(selectedIamgeUri), Toast.LENGTH_LONG).show();

                /* WHAT YOU DO WITH URI ??? */
            }
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}

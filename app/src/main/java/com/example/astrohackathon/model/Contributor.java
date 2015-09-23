package com.example.astrohackathon.model;

import android.content.DialogInterface;
import android.view.View;

/**
 * Created by chunqhai on 10/11/2014.
 */
public class Contributor {
    int image;
    String name;
    View.OnClickListener deleteImage;


    public View.OnClickListener getDeleteImage() {
        return deleteImage;
    }

    public void setDeleteImage(View.OnClickListener deleteImage) {
        this.deleteImage = deleteImage;
    }

    public Contributor(int image, String name)
    {
        this.image = image;
        this.name = name;
        this.deleteImage = deleteImage;

    }


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

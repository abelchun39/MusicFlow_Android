/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.astrohackathon.lib;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Displays a video file.  The VideoView class
 * can load images from various sources (such as resources or content
 * providers), takes care of computing its measurement from the video so that
 * it can be used in any layout manager, and provides various display options
 * such as scaling and tinting.
 */
public class CustomizeVideoView extends VideoView {
    public CustomizeVideoView(Context context) {
        super(context);
    }

    public CustomizeVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomizeVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    /*private int wVideo;
    private int hVideo;

    public CustomizeVideoView(Context context) {
        super(context);
    }

    public CustomizeVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomizeVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (wVideo!=0 && hVideo!=0)
            setMeasuredDimension(wVideo,hVideo);
    }

    public void setVideoAspect( int w, int h )
    {
        wVideo = w;
        hVideo = h;
        onMeasure(w , h);
    }*/


}
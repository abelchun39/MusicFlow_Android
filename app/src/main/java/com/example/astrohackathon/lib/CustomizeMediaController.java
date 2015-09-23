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
import android.widget.MediaController;

/**
 * A view containing controls for a MediaPlayer. Typically contains the
 * buttons like "Play/Pause", "Rewind", "Fast Forward" and a progress
 * slider. It takes care of synchronizing the controls with the state
 * of the MediaPlayer.
 * <p>
 * The way to use this class is to instantiate it programatically.
 * The MediaController will create a default set of controls
 * and put them in a window floating above your application. Specifically,
 * the controls will float above the view specified with setAnchorView().
 * The window will disappear if left idle for three seconds and reappear
 * when the user touches the anchor view.
 * <p>
 * Functions like show() and hide() have no effect when MediaController
 * is created in an xml layout.
 *
 * MediaController will hide and
 * show the buttons according to these rules:
 * <ul>
 * <li> The "previous" and "next" buttons are hidden until setPrevNextListeners()
 *   has been called
 * <li> The "previous" and "next" buttons are visible but disabled if
 *   setPrevNextListeners() was called with null listeners
 * <li> The "rewind" and "fastforward" buttons are shown unless requested
 *   otherwise by using the MediaController(Context, boolean) constructor
 *   with the boolean set to false
 * </ul>
 */
public class CustomizeMediaController extends MediaController {


    public CustomizeMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomizeMediaController(Context context, boolean useFastForward) {
        super(context, useFastForward);
    }

    public CustomizeMediaController(Context context) {
        super(context);
    }

    @Override
    public void show(int timeout) {
        super.show(0);
    }
}
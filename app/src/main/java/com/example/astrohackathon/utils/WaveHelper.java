package com.example.astrohackathon.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.media.MediaPlayer;
import android.view.View;

import com.example.astrohackathon.lib.utils.TunnelPlayerWorkaround;
import com.example.astrohackathon.lib.visualizer.VisualizerView;
import com.example.astrohackathon.lib.visualizer.renderer.BarGraphRenderer;
import com.example.astrohackathon.lib.visualizer.renderer.CircleBarRenderer;
import com.example.astrohackathon.lib.visualizer.renderer.CircleRenderer;
import com.example.astrohackathon.lib.visualizer.renderer.LineRenderer;

import java.io.IOException;

/**
 * Created by chunqhai on 10/12/2014.
 */
public class WaveHelper {

    private MediaPlayer mPlayer;
    private MediaPlayer mSilentPlayer;
    private VisualizerView mVisualizerView;
    private Context c;


    public WaveHelper(Context c,MediaPlayer mPlayer, VisualizerView mVisualizerView)
    {
        this.c = c;
        this.mPlayer = mPlayer;
        this.mVisualizerView = mVisualizerView;

    }

    private void cleanUp()
    {
        if (mPlayer != null)
        {
            mVisualizerView.release();
            mPlayer.release();
            mPlayer = null;
        }

          if (mSilentPlayer != null)
        {
            mSilentPlayer.release();
            mSilentPlayer = null;
        }
    }

    // Workaround (for Galaxy S4)
    //
    // "Visualization does not work on the new Galaxy devices"
    //    https://github.com/felixpalmer/android-visualizer/issues/5
    //
    // NOTE:
    //   This code is not required for visualizing default "test.mp3" file,
    //   because tunnel player is used when duration is longer than 1 minute.
    //   (default "test.mp3" file: 8 seconds)
    //
     private void initTunnelPlayerWorkaround() {
        // Read "tunnel.decode" system property to determine
        // the workaround is needed
        if (TunnelPlayerWorkaround.isTunnelDecodeEnabled(c)) {
            mSilentPlayer = TunnelPlayerWorkaround.createSilentMediaPlayer(c);
        }
    }

    // Methods for adding renderers to visualizer
    public void addBarGraphRenderers()
    {
        Paint paint = new Paint();
        paint.setStrokeWidth(50f);
        paint.setAntiAlias(true);
        paint.setColor(Color.argb(200, 56, 138, 252));
        BarGraphRenderer barGraphRendererBottom = new BarGraphRenderer(16, paint, false);
        mVisualizerView.addRenderer(barGraphRendererBottom);

        Paint paint2 = new Paint();
        paint2.setStrokeWidth(12f);
        paint2.setAntiAlias(true);
        paint2.setColor(Color.argb(200, 181, 111, 233));
        BarGraphRenderer barGraphRendererTop = new BarGraphRenderer(4, paint2, true);
        mVisualizerView.addRenderer(barGraphRendererTop);
    }

    public void addCircleBarRenderer()
    {
        Paint paint = new Paint();
        paint.setStrokeWidth(8f);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
        paint.setColor(Color.argb(255, 222, 92, 143));
        CircleBarRenderer circleBarRenderer = new CircleBarRenderer(paint, 32, true);
        mVisualizerView.addRenderer(circleBarRenderer);
    }

    public void addCircleRenderer()
    {
        Paint paint = new Paint();
        paint.setStrokeWidth(3f);
        paint.setAntiAlias(true);
        paint.setColor(Color.argb(255, 222, 92, 143));
        CircleRenderer circleRenderer = new CircleRenderer(paint, true);
        mVisualizerView.addRenderer(circleRenderer);
    }

    public void addLineRenderer()
    {
        Paint linePaint = new Paint();
        linePaint.setStrokeWidth(1f);
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.argb(88, 0, 128, 255));

        Paint lineFlashPaint = new Paint();
        lineFlashPaint.setStrokeWidth(5f);
        lineFlashPaint.setAntiAlias(true);
        lineFlashPaint.setColor(Color.argb(188, 255, 255, 255));
        LineRenderer lineRenderer = new LineRenderer(linePaint, lineFlashPaint, true);
        mVisualizerView.addRenderer(lineRenderer);
    }

    // Actions for buttons defined in xml
    public void startPressed(View view) throws IllegalStateException, IOException
    {
        if(mPlayer.isPlaying())
        {
            return;
        }
        mPlayer.prepare();
        mPlayer.start();
    }

    public void stopPressed(View view)
    {
        mPlayer.stop();
    }

    public void barPressed(View view)
    {
        addBarGraphRenderers();
    }

    public void circlePressed(View view)
    {
        addCircleRenderer();
    }

    public void circleBarPressed(View view)
    {
        addCircleBarRenderer();
    }

    public void linePressed(View view)
    {
        addLineRenderer();
    }

    public void clearPressed(View view)
    {
        mVisualizerView.clearRenderers();
    }
}

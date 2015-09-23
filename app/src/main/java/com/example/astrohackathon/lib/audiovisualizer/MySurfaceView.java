package com.example.astrohackathon.lib.audiovisualizer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

import com.example.astrohackathon.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * TODO - Need to verify frequency calculation is accurate
 * 
 * List of musical notes and their frequencies - http://www.phy.mtu.edu/~suits/notefreqs.html
 * Tone generator - http://www.seventhstring.com/tuningfork/tuningfork.html
 *  
 * @author dwatling
 *
 */
public class MySurfaceView extends View {
	private static final String TAG = MySurfaceView.class.getSimpleName();
	SurfaceHolder mHolder;
	
	protected short[] mSampleData;
	protected int mSampleSize;
	protected float mSampleLength;
	protected float mTimePerSlot;
	
	protected Bitmap mBitmap;
	protected Paint mPaint;
	protected Map<Float, List<Integer>> mData;

	public MySurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public MySurfaceView(Context context) {
		super(context);
		init(context);
	}
	
	protected void init(Context ctx) {
		mPaint = new Paint();
		mPaint.setColor(Color.WHITE);
		mBitmap = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.ic_launcher);
		mData = new TreeMap<Float, List<Integer>>();
	}
	
	public void setData(short[] data, int sampleSize, float sampleLength) {
		this.mSampleData = data;
		this.mSampleSize = sampleSize;
		this.mSampleLength = sampleLength;
		this.mTimePerSlot = this.mSampleLength / this.mSampleSize;
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		mData.clear();
		
		if (mSampleData != null) {
			int numPoints = canvas.getWidth();
			int step = mSampleSize / numPoints;
			int halfHeight = canvas.getHeight() / 2;
	
			float[] points = new float[numPoints * 4];		// a line = 4 points: x0,y0,x1,y1
			float oldX = 0.0f;
			float oldY = halfHeight;
			int pointAboveZero = -1;
			
			for (int i = 0; i < numPoints; i ++) {
				short val = mSampleData[i*step];
				float y = (((float)val / AudioMonitor.PCM_MAXIMUM_VALUE) * halfHeight) + halfHeight;
				points[i*4+0] = oldX;
				points[i*4+1] = oldY;
				
				points[i*4+2] = i;
				points[i*4+3] = y;
				
				oldX = i;
				oldY = y;
			}
			canvas.drawLines(points, mPaint);

			for (int i = 0; i < mSampleSize; i ++) {
				short val = mSampleData[i];
				if (val > 0) {
					if (pointAboveZero < 0) {
						pointAboveZero = i;
					}
				} else if (val < 0) {
					if (pointAboveZero > 0) {
						int wavelength = i - pointAboveZero;
						if (wavelength > 3) {
							// wavelengths less than 3 should be considered "too high" 
							float freq = (1.0f / ((float)wavelength * 2.0f * this.mTimePerSlot));		// this is realy only half a wavelength, so just assume...
							int amplitude = (mSampleData[(i + pointAboveZero) / 2]);
							if (freq > 0.0f && freq < 5000.0f) {
								addData(freq, amplitude);
							}
						}
						pointAboveZero = -1;
					}
				}
				
			}
		}
		
		printMap();
	}
	
	private void printMap() {
		if (mData.size() > 0) {
			Float freq = 0.0f;
			List<Integer> amps = new ArrayList<Integer>();
			Iterator<Float> iter = mData.keySet().iterator();
			while (iter.hasNext()) {
				Float key = iter.next();
				List<Integer> values = mData.get(key);
				if (values.size() > amps.size()) {
					freq = key;
					amps = values;
				}
			}

			Iterator<Integer> i = amps.iterator();
			Integer bestAmp = 0;
			while (i.hasNext()) {
				Integer amp = i.next();
				if (amp > bestAmp) {
					bestAmp = amp;
				}
			}
			Log.d(TAG, "Frequency: " + freq + ", amplitude: " + bestAmp);
		}
	}
	
	private void addData(float frequency, int amplitude) {
		if (!mData.containsKey(frequency)) {
			List<Integer> list = new ArrayList<Integer>();
			list.add(amplitude);
			mData.put(frequency, list);
		} else {
			List<Integer> list = mData.get(frequency);
			list.add(amplitude);
		}
	}
}

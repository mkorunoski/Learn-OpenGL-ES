package com.android.opengles;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    public class SurfaceView extends GLSurfaceView {

        private final float TOUCH_SCALE_FACTOR = 180.0f / 160;
        private float mPreviousX;
        private float mPreviousY;

        public SurfaceView(Context context) {
            super(context);
        }

        @Override
        public boolean onTouchEvent(MotionEvent e) {
            float x = e.getX();
            float y = e.getY();

            switch (e.getAction()) {
                case MotionEvent.ACTION_MOVE: {
                    float dx = x - mPreviousX;
                    float dy = y - mPreviousY;

                    mRenderer.mAngle = mRenderer.mAngle + ((dx + dy) * TOUCH_SCALE_FACTOR);
                    requestRender();
                    break;
                }
                case MotionEvent.ACTION_UP: {

                    break;
                }
            }

            mPreviousX = x;
            mPreviousY = y;
            return true;
        }
    }

    private SurfaceView mGLSurfaceView;
    private MyRenderer mRenderer;
    private boolean mRenderSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRenderer = new MyRenderer(this);

        mGLSurfaceView = new SurfaceView(this);
        mGLSurfaceView.setEGLContextClientVersion(2);
        mGLSurfaceView.setRenderer(mRenderer);
        mRenderSet = true;
        setContentView(mGLSurfaceView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mRenderSet) {
            mGLSurfaceView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRenderSet) {
            mGLSurfaceView.onResume();
        }
    }
}

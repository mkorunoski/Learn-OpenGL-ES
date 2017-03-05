package com.android.opengles;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;

import com.android.opengles.Geometry.Cube;
import com.android.opengles.Geometry.Quad;
import com.android.opengles.Light.AmbientLight;
import com.android.opengles.Light.DiffuseLight;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_CULL_FACE;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glViewport;

public class MyRenderer implements Renderer {

    public float mAngle;

    private Context mContext;

    private Effect mEffect;
    private Cube mCube;
    private Quad mGround;
    private Transformation[] mCubeTransformation;
    private Transformation mGroundTransformation;
    private Texture mTilesTexture;
    private Texture mCrateTexture;
    private AmbientLight mAmbientLight;
    private DiffuseLight mDiffuseLight;

    private Timer timer;

    public MyRenderer(Context context) {
        mContext = context;
        timer = new Timer();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);

        mEffect = new Effect(Effect.Utils.loadShaderSource(mContext, R.raw.vs),
                Effect.Utils.loadShaderSource(mContext, R.raw.fs));

        mCube = new Cube();
        mGround = new Quad();

        mTilesTexture = new Texture(mContext, R.raw.tiles);
        mCrateTexture = new Texture(mContext, R.raw.crate);

        Transformation.initViewAndProjectionMatrix();
        mCubeTransformation = new Transformation[]{new Transformation(), new Transformation(), new Transformation()};
        mCubeTransformation[0].translate(-2.0f, 0.5f, -2.0f);
        mCubeTransformation[1].translate( 2.0f, 0.5f, -1.0f);
        mCubeTransformation[2].translate(-2.0f, 0.5f,  1.0f);
        mGroundTransformation = new Transformation();
        mGroundTransformation.scale(10.0f, 0.0f, 10.0f);

        mAmbientLight = new AmbientLight();
        mDiffuseLight = new DiffuseLight();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
        Transformation.updateProjectionMatrix((float)width, (float)height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);
        glClear(GL_DEPTH_BUFFER_BIT);

        float a = (float) Math.toRadians(timer.elapsed() / Math.pow(10, 8)) ;
        Transformation.updateViewMatrix(4.0f * (float)Math.sin(a), 3.0f, 4.0f * (float)Math.cos(a), 0.0f, 0.0f, 0.0f);

        mCubeTransformation[0].rotate(mAngle * 0.01f, 0.0f, 1.0f, 0.0f);
        mCubeTransformation[1].rotate(mAngle * 0.01f, 0.0f, 1.0f, 0.0f);
        mCubeTransformation[2].rotate(mAngle * 0.01f, 0.0f, 1.0f, 0.0f);
        if(mAngle < 0) {
            mAngle += 1;
        } else if(mAngle > 0) {
            mAngle -= 1;
        }

        glUseProgram(mEffect.getProgram());

        mAmbientLight.setAmbientLight(mEffect.getProgram());
        mDiffuseLight.setDiffuseLight(mEffect.getProgram());
        mCrateTexture.bind(mEffect.getProgram());
        mCube.draw(mEffect.getProgram(), mCubeTransformation[0]);
        mCube.draw(mEffect.getProgram(), mCubeTransformation[1]);
        mCube.draw(mEffect.getProgram(), mCubeTransformation[2]);
        mCrateTexture.unbind();

        mAmbientLight.setAmbientLight(mEffect.getProgram());
        mDiffuseLight.setDiffuseLight(mEffect.getProgram());
        mTilesTexture.bind(mEffect.getProgram());
        mGround.draw(mEffect.getProgram(), mGroundTransformation);
        mTilesTexture.unbind();
    }
}

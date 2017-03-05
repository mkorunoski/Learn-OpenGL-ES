package com.android.opengles;

import android.opengl.Matrix;

public class Transformation {

    private float[] mScalingMatrix;
    private float[] mRotationalMatrix;
    private float[] mTranslationalMatrix;
    public float[] mModelMatrix;
    public float[] mInverseTransposeMatrix;

    public static float[] mViewMatrix              = new float[16];
    public static float[] mProjectionMatrix        = new float[16];

    public Transformation() {
        mScalingMatrix          = new float[16];
        mRotationalMatrix       = new float[16];
        mTranslationalMatrix    = new float[16];

        mModelMatrix            = new float[16];
        mInverseTransposeMatrix = new float[16];

        Matrix.setIdentityM(mScalingMatrix, 0);
        Matrix.setIdentityM(mRotationalMatrix, 0);
        Matrix.setIdentityM(mTranslationalMatrix, 0);

        updateModelAndInverseTransposeMatrix();
    }

    public void scale(float x, float y, float z) {
        Matrix.scaleM(mScalingMatrix, 0, x, y, z);
        updateModelAndInverseTransposeMatrix();
    }
    public void rotate(float a, float x, float y, float z) {
        Matrix.rotateM(mRotationalMatrix, 0, a, x, y, z);
        updateModelAndInverseTransposeMatrix();
    }
    public void translate(float x, float y, float z) {
        Matrix.translateM(mTranslationalMatrix, 0, x, y, z);
        updateModelAndInverseTransposeMatrix();
    }
    private void updateModelAndInverseTransposeMatrix() {
        Matrix.multiplyMM(mModelMatrix, 0, mRotationalMatrix, 0, mScalingMatrix, 0);
        Matrix.multiplyMM(mModelMatrix, 0, mTranslationalMatrix, 0, mModelMatrix, 0);

        Matrix.invertM(mInverseTransposeMatrix, 0, mModelMatrix, 0);
        Matrix.transposeM(mInverseTransposeMatrix, 0, mInverseTransposeMatrix, 0);
    }

    public static void updateViewMatrix(float ex, float ey, float ez, float cx, float cy, float cz) {
        Matrix.setLookAtM(mViewMatrix, 0,
                ex, ey, ez,
                cx, cy, cz,
                0.0f, 1.0f, 0.0f);
    }

    public static void initViewAndProjectionMatrix() {
        Matrix.setLookAtM(mViewMatrix, 0,
                1.0f, 1.0f, 1.0f,
                0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f);
        Matrix.setIdentityM(mProjectionMatrix, 0);
    }

    public static void updateProjectionMatrix(float width, float height) {
        float ratio = width > height ? height / width : width / height;
        if (width > height) {
            Matrix.frustumM(mProjectionMatrix, 0, -1.0f, 1.0f, -ratio, ratio, 1.0f, 10.0f);
        } else {
            Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1.0f, 1.0f, 1.0f, 10.0f);
        }
    }
}

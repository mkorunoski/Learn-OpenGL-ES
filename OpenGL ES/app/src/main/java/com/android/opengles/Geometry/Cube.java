package com.android.opengles.Geometry;

import com.android.opengles.Transformation;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_STATIC_DRAW;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glBindBuffer;
import static android.opengl.GLES20.glBufferData;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGenBuffers;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniformMatrix4fv;

import static android.opengl.GLES20.glVertexAttribPointer;
import static com.android.opengles.Constants.*;

public class Cube {

    private static final float mVertices[] = {
//           x      y      z      nx    ny      nz     s     t
            -0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,  0.0f, 0.0f, // 0
             0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,  0.0f, 1.0f, // 1
             0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,  1.0f, 1.0f, // 2
            -0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,  0.0f, 0.0f, // 0
             0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,  1.0f, 1.0f, // 2
            -0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,  1.0f, 0.0f, // 3
            -0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,  0.0f, 0.0f, // 4
             0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,  0.0f, 1.0f, // 5
             0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,  1.0f, 1.0f, // 6
            -0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,  0.0f, 0.0f, // 4
             0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,  1.0f, 1.0f, // 6
            -0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,  1.0f, 0.0f, // 7
            -0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,  0.0f, 0.0f, // 0
            -0.5f, -0.5f,  0.5f, -1.0f,  0.0f,  0.0f,  0.0f, 1.0f, // 3
            -0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,  1.0f, 1.0f, // 4
            -0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,  0.0f, 0.0f, // 0
            -0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,  1.0f, 1.0f, // 4
            -0.5f,  0.5f, -0.5f, -1.0f,  0.0f,  0.0f,  1.0f, 0.0f, // 7
             0.5f, -0.5f,  0.5f,  1.0f,  0.0f,  0.0f,  0.0f, 0.0f, // 2
             0.5f, -0.5f, -0.5f,  1.0f,  0.0f,  0.0f,  0.0f, 1.0f, // 1
             0.5f,  0.5f, -0.5f,  1.0f,  0.0f,  0.0f,  1.0f, 1.0f, // 6
             0.5f, -0.5f,  0.5f,  1.0f,  0.0f,  0.0f,  0.0f, 0.0f, // 2
             0.5f,  0.5f, -0.5f,  1.0f,  0.0f,  0.0f,  1.0f, 1.0f, // 6
             0.5f,  0.5f,  0.5f,  1.0f,  0.0f,  0.0f,  1.0f, 0.0f, // 5
            -0.5f, -0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  0.0f, 0.0f, // 3
             0.5f, -0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  0.0f, 1.0f, // 2
             0.5f,  0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  1.0f, 1.0f, // 5
            -0.5f, -0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  0.0f, 0.0f, // 3
             0.5f,  0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  1.0f, 1.0f, // 5
            -0.5f,  0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  1.0f, 0.0f, // 4
             0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  0.0f, 0.0f, // 1
            -0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  0.0f, 1.0f, // 0
            -0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  1.0f, 1.0f, // 7
             0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  0.0f, 0.0f, // 1
            -0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  1.0f, 1.0f, // 7
             0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  1.0f, 0.0f  // 6
    };

    private FloatBuffer mVertexBuffer;
    private int[] mVBO;

    private int aPosition;
    private int aNormal;
    private int aTexCoord;

    private int uModelMatrix;
    private int uViewMatrix;
    private int uProjectionMatrix;
    private int uInverseTransposeMatrix;

    public Cube() {
        mVertexBuffer = ByteBuffer
                .allocateDirect(BYTES_PER_FLOAT * mVertices.length)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        mVertexBuffer.put(mVertices);
        mVertexBuffer.position(0);

        mVBO = new int[1];
        glGenBuffers(1, mVBO, 0);
        glBindBuffer(GL_ARRAY_BUFFER, mVBO[0]);
        glBufferData(GL_ARRAY_BUFFER, BYTES_PER_FLOAT * mVertices.length, mVertexBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void draw(int program, Transformation transformation) {
        aPosition   = glGetAttribLocation(program, "a_position");
        aNormal     = glGetAttribLocation(program, "a_normal");
        aTexCoord   = glGetAttribLocation(program, "a_texCoord");

        uModelMatrix            = glGetUniformLocation(program, "u_modelMatrix");
        uViewMatrix             = glGetUniformLocation(program, "u_viewMatrix");
        uProjectionMatrix       = glGetUniformLocation(program, "u_projectionMatrix");
        uInverseTransposeMatrix = glGetUniformLocation(program, "u_inverseTransposeMatrix");

        glUniformMatrix4fv(uModelMatrix, 1, false, transformation.mModelMatrix, 0);
        glUniformMatrix4fv(uViewMatrix, 1, false, transformation.mViewMatrix, 0);
        glUniformMatrix4fv(uProjectionMatrix, 1, false, transformation.mProjectionMatrix, 0);
        glUniformMatrix4fv(uInverseTransposeMatrix, 1, false, transformation.mInverseTransposeMatrix, 0);

        glBindBuffer(GL_ARRAY_BUFFER, mVBO[0]);

        glEnableVertexAttribArray(aPosition);
        glVertexAttribPointer(aPosition, POSITION_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, 0);
        glEnableVertexAttribArray(aNormal);
        glVertexAttribPointer(aNormal, NORMAL_COMPONENT_COUNT, GL_FLOAT, false, STRIDE,
                BYTES_PER_FLOAT * POSITION_COMPONENT_COUNT);
        glEnableVertexAttribArray(aTexCoord);
        glVertexAttribPointer(aTexCoord, TEXCOORD_COMPONENT_COUNT, GL_FLOAT, false,
                STRIDE, BYTES_PER_FLOAT * (POSITION_COMPONENT_COUNT + NORMAL_COMPONENT_COUNT));

        glDrawArrays(GL_TRIANGLES, 0, mVertices.length / COUNT);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

}

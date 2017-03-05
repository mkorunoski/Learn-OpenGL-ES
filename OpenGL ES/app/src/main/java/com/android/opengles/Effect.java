package com.android.opengles;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetProgramInfoLog;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderInfoLog;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;

public class Effect {

    private static final String TAG = "Effect";

    public static class Utils {
        public static String loadShaderSource(Context context, int res) {
            StringBuilder body = new StringBuilder();
            try {
                InputStream inputStream = context.getResources().openRawResource(res);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String nextLine;
                while ((nextLine = bufferedReader.readLine()) != null) {
                    body.append(nextLine);
                    body.append('\n');
                }
            } catch (IOException e) {
                throw new RuntimeException("Could not open resource: " + res, e);
            } catch (Resources.NotFoundException nfe) {
                throw new RuntimeException("Resource not found: " + res, nfe);
            }
            return body.toString();
        }
    }

    private int mVertexShaderObject;
    private int mFragmentShaderObject;
    private int mProgram;

    public int getProgram() {
        return mProgram;
    }

    public Effect(String vertexShaderSource, String fragmentShaderSource) {
        mVertexShaderObject = compileShaderSource(GL_VERTEX_SHADER, vertexShaderSource);
        mFragmentShaderObject = compileShaderSource(GL_FRAGMENT_SHADER, fragmentShaderSource);
        mProgram = createLinkProgram(new int[]{mVertexShaderObject, mFragmentShaderObject});
        glDeleteShader(mVertexShaderObject);
        glDeleteShader(mFragmentShaderObject);
    }

    private int compileShaderSource(int type, String source) {
        int object = glCreateShader(type);
        glShaderSource(object, source);
        glCompileShader(object);

        int[] compiled = new int[1];
        glGetShaderiv(object, GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == 0) {
//            Log.e(TAG, glGetShaderInfoLog(object));
            Log.e(TAG, "Shader compilation failed. Shader info log:\n" + glGetShaderInfoLog(object));
        }

        return object;
    }

    private int createLinkProgram(int[] shaderObjects) {
        final int program = glCreateProgram();
        for (int i = 0; i < shaderObjects.length; ++i) {
            glAttachShader(program, shaderObjects[i]);
        }
        glLinkProgram(program);

        int[] linked = new int[1];
        glGetProgramiv(program, GL_LINK_STATUS, linked, 0);
        if (linked[0] == 0) {
//            Log.e(TAG, glGetProgramInfoLog(program));
                Log.e(TAG, "Program linking failed.");
        }

        return program;
    }
}

package com.android.opengles.Light;

import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4fv;

public class AmbientLight {

    private final float[] COLOR = {
            0.1f, 0.1f, 0.1f, 1.0f
    };

    public void setAmbientLight(int program) {
        int colorLocation = glGetUniformLocation(program, "al_color");
        glUniform4fv(colorLocation, 1, COLOR, 0);
    }

}

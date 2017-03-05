package com.android.opengles.Light;

import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1f;
import static android.opengl.GLES20.glUniform4fv;

public class DiffuseLight {

    private static int COLOR_LOCATION;
    private static int POSITION_LOCATION;
    private static int ATTENUATION_LOCATION;

    private final float[] COLOR = {
            1.0f, 1.0f, 1.0f, 1.0f
    };
    private final float[] POSITION = {
            5.0f, 5.0f, 5.0f, 1.0f
    };
    private final float ATTENUATION = 0.001f;

    public void setDiffuseLight(int program) {
        COLOR_LOCATION       = glGetUniformLocation(program, "dl_color");
        POSITION_LOCATION    = glGetUniformLocation(program, "dl_position");
        ATTENUATION_LOCATION = glGetUniformLocation(program, "dl_attenuation");
        glUniform4fv(COLOR_LOCATION, 1, COLOR, 0);
        glUniform4fv(POSITION_LOCATION, 1, POSITION, 0);
        glUniform1f(ATTENUATION_LOCATION, ATTENUATION);
    }

}
